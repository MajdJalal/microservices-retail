package com.programmingtechie.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programmingtechie.productservice.dto.ProductRequest;
import com.programmingtechie.productservice.dto.ProductResponse;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    // @Autowired/*in Spring Boot (and in the broader Spring Framework), when you are using constructor injection, you should place the @Autowired annotation above the constructor.Here's why:Explicit Dependency Injection: Placing @Autowired above the constructor makes it explicit that the constructor is used for dependency injection. This is especially helpful when someone else is reading your code or when you come back to your code later.Constructor as Injection Point: In constructor injection, the constructor serves as the injection point for dependencies. By placing @Autowired above the constructor, you're indicating to the Spring container that it should inject the required dependencies into this constructor when creating instances of the class. */
    private final ProductRepository productRepository;

    // public ProductService(ProductRepository productRepository) {
    //     this.productRepository = productRepository;
    // }
    //u can remove the constrictor and replace it with annotation "@RequiredArgsConstructor"
    public void createProduct(ProductRequest productRequest) {
        //mapping the productRequest into a product object
        Product product = Product.builder()
        .name(productRequest.getName())
        .description(productRequest.getDescription())
        .price(productRequest.getPrice())
        .build();

        //to save 
        productRepository.save(product);
        log.info("Product {} is saved ", product.getId());
    }
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        //(product -> mapToProductResponse(product) )same as (this::mapToProductResponse)
        return products.stream().map(this::mapToProductResponse).toList();
    }
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }



}
/*Dependency Injection (DI) is a design pattern widely used in software development, including in the Spring Framework and Spring Boot, to manage the dependencies between components or classes in an application. At its core, DI is about decoupling components by providing the dependencies they need from external sources rather than having the components create or manage their dependencies themselves.

Here's a more detailed explanation of Dependency Injection:

Dependency: A dependency is an object that another object depends on to perform its work or provide some functionality. For example, in a web application, a UserService might depend on a UserRepository to retrieve user data from a database.

Injection: Injection refers to the process of providing dependencies to a component (class or object) from external sources, typically by an external entity such as a framework or a container.

Types of Dependency Injection:

Constructor Injection: Dependencies are provided to a class through its constructor. This is the most common and recommended form of dependency injection. It ensures that all dependencies required by a class are provided at the time of its creation. */
/* Dependency Injection is a powerful design pattern that promotes decoupling, testability, flexibility, and reusability in software development by providing dependencies to components from external sources. In Spring Boot applications, dependency injection is a fundamental concept facilitated by the Spring Framework to manage and wire together the various components of the application. */
/*As I explained in my previous articles about the SOLID design principles, their goal is to improve the reusability of your code. They also aim to reduce the frequency with which you need to change a class. Dependency injection supports these goals by decoupling the creation of the usage of an object. That enables you to replace dependencies without changing the class that uses them. It also reduces the risk that you have to change a class just because one of its dependencies changed. */