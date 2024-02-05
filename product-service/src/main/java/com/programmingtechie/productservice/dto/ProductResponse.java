package com.programmingtechie.productservice.dto;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;

}
//u may be wondering why need to create 
//a ProductResponse while we already have 
//product class 
//that is cuz it is a good practise to seperate 
//the dto from the actula models (entities)
//u shouldnt expose ur model entities to the world 
