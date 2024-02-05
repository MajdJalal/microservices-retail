package com.programmingtechie.productservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*A Data Transfer Object is a design pattern that allows for the exchange of data between software application subsystems or layers, often between the business logic layer and the presentation or persistence layer. The primary goal of DTOs is to reduce the number of method calls between these layers by aggregating data into a single object.

In a Spring Boot application, DTOs are commonly used to encapsulate data transferred between the controller and service layers, or between the service layer and the persistence layer. This segregation helps in maintaining a clear separation of concerns and improves the overall modularity of the codebase. */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
}
