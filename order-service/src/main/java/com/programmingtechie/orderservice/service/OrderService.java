package com.programmingtechie.orderservice.service;

import java.util.Arrays;
import java.util.UUID;
import java.util.List;

import com.programmingtechie.orderservice.dto.InventoryResponse;
import com.programmingtechie.orderservice.event.OrderPlacedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmingtechie.orderservice.dto.OrderLineItemsDto;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor//(majd)when spring boot creates the orderService bean it does dependency injection for all attributes when seeing this annotation
@Service
@Transactional/*Class-Level Transactional Annotation:When @Transactional is applied to a class, it indicates that all public methods of the class should execute within a transactional context unless overridden by a method-level annotation.Transactions are initiated and committed or rolled back at the method level as well, but the transactional behavior applies to all public methods of the class.If a method within the class is annotated with @Transactional, the method-level annotation takes precedence over the class-level annotation, allowing for method-specific transactional behavior. */
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;//wanna make a call to the kafka cluster whenever an order is placed

    public String placeOrder(OrderRequest orderRequest) {
        //from the order request i am going to be creating an order
        Order order  = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());/*hen generated using a properly implemented algorithm such as Java's UUID.randomUUID(), each UUID is intended to be unique among all UUIDs generated in the universe.The uniqueness of a UUID is based on its 128-bit value, which has a total of 2^128 (approximately 3.4 x 10^38) possible combinations. This immense number of possible combinations makes it extremely unlikely (but not impossible) for two randomly generated UUIDs to be the same. */
        //map orderLine items in the request to actula orderline items 
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                    .stream()
                    .map(this::mapToDto)
                    .toList();
        //set the items to the order 
        order.setOrderLineItemsList(orderLineItems);
        //get skuCodes of the order
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //call the inventory service to check if the order is in stock or not
        //and pplace only if avaiable
        //replace "localhost:8082" with the name of the service
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class) //to be able to read the data from the response//now type is inventoryResponse list (array  works)(need to define here in the orderService)
                .block();//make it synchronous

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(inventoryResponse -> inventoryResponse.isInStock());//if any is false we'll get back false
        /*here i am assuming that all orderes i get gonna be from the products avaiavle i am just checking quantity avaiability */
        /*If the inventoryResponseArray is empty, then the allProductsInStock variable will have a value of true.

In Java, the allMatch method returns true if all elements of the stream match the given predicate, and since there are no elements in an empty stream, the condition is vacuously true. Therefore, if inventoryResponseArray is empty, allProductsInStock will be true.*/
        //save in the dataBase 
        if(allProductsInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));//wanna send the orderNumber as the data //but instead we r gonna create a class called an orderplacedEvent and send this object as a json message to the notficationTopic
            return "order placed successfuly";
        }
        else throw new IllegalArgumentException("product isn't in the stock, try again later");
    }

    //Note: this method transforms Dto to normal
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        
        return orderLineItems;
    }
}
