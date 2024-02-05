package com.programmingtechie.orderservice.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    //When you return a WebClient instance directly from a method annotated with @Bean, you are providing a fully configured and ready-to-use WebClient instance to other parts of your application. This WebClient instance can be injected into other beans or components where it's needed.
    //hen you return a WebClient.Builder instance, you are providing a builder object that allows further customization before building the WebClient instance. This approach is useful when you need to customize the WebClient instance based on runtime conditions or specific requirements.
    @Bean//now we r gonna have a WebClient bean with name webClient(method name)
    @LoadBalanced //to enable loadbalancing
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

}
