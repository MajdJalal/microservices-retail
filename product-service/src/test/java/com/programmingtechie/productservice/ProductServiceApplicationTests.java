package com.programmingtechie.productservice;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmingtechie.productservice.dto.ProductRequest;
import com.programmingtechie.productservice.repository.ProductRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers//so junit5 understands that we r gonna be using testcontainers to run this test 
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container//so junit5 can understand that this is a container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.2.2");//u have to provide the docker image name for whatever software u eanna use in the argument 

	@Autowired
	private MockMvc mockMvc;/*In the case of injecting MockMvc into a test class, you're using field injection, where the @Autowired annotation is applied directly to the field. Spring Boot will automatically inject the MockMvc instance into the field when the test class is instantiated. */
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository  productRepository;
	
	//we have to provide the uri of mongodb dynamically now in the test 
	/*The setProperties method annotated with @DynamicPropertySource will be called as part of the test setup process before any test methods are executed. This method is responsible for dynamically setting properties that are required for the test environment. */
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
		/*This dynamic property is used to set the MongoDB connection URI for the Spring application properties during test setup, allowing the application to connect to the MongoDB database provided by Testcontainers for testing. */
	}


	//writing intergration tests 
	//for get product
	@Test
	void shouldGetAllProducts() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")).andExpect(status().isOk());
	}

	//for creating product 
	@Test
	void shouldCreateProduct() throws Exception {

		//provided all input to call the api 
		ProductRequest productRequest = getProductRequest();
		String productRequestString =  objectMapper.writeValueAsString(productRequest);


		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
			   .contentType(MediaType.APPLICATION_JSON)
			   .content(productRequestString))//only takes a string so we have to convert the productRequest into a string 
			.andExpect(status().isCreated());
			   //now to verify the response 
		Assertions.assertEquals(1, productRepository.findAll().size());
		
	}


	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("iphone 12")
				.description("iphone 12")
				.price(BigDecimal.valueOf(1100))
				.build();

	}

}
/*there won't be any real insertion of values into a real database during the execution of this test.

In the test you provided, you're using Testcontainers to spin up a MongoDB container (MongoDBContainer) for testing purposes. This container runs in isolation and is separate from any production or development databases you may have.

When the test runs, the MongoDBContainer starts up and provides a MongoDB database instance for the test to use. However, this database instance is temporary and exists only for the duration of the test. It is destroyed once the test completes.

The @DynamicPropertySource method setProperties dynamically sets the URI of the MongoDB container as the data source for the Spring Data MongoDB repository (spring.data.mongodb.uri). This ensures that the application under test uses the MongoDB instance provided by the Testcontainers container.

During the test execution, the shouldCreateProduct() test method sends an HTTP POST request to the /api/product endpoint using MockMvc. This request is processed by the application, and a new product is created in the MongoDB database instance provided by Testcontainers.

However, since this is a test environment, the data inserted into the MongoDB container is isolated and does not affect any real databases. Once the test completes, the MongoDB container is stopped and removed, and any data inserted during the test is discarded.

In summary, the test interacts with a temporary MongoDB container provided by Testcontainers, and the data insertion occurs within this isolated environment, not affecting any real databases. */