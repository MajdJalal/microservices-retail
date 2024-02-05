package com.programmingtechie.orderservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name ="inventory", fallbackMethod = "fallbackMethod") // resilience 4j will apply the circuitbreaker logic to this
    //whaterver api calls made inside this method  will apply  the circuit breaker pattern
    @TimeLimiter(name = "inventory") //note:name must match with name provided in the application.properties file//this makes an async call so return completblefuture
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
        //place order gonna be executed in a different thread and returns a timeout exception when timelimit reached
    }

    //the method to apply when we r in the open state(majd when u fail regardless of the state)
    //must have the same return type as the placeOrder method
    //we r gonna consume whatever exception we got into thsi method
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Ooops, something went wrong buddy, please order after some time");
    }

    
}
