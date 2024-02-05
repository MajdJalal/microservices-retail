package com.programmingtechie.productservice.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//to define this class as a MongoDB document //Records in MongoDB called documents(similar to JSON format)
//MongoDB is a NOSQL DataBase 
@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data/*@Data: This annotation is a shortcut for @ToString, @EqualsAndHashCode, @Getter, and @Setter combined. It generates methods like toString(), equals(), hashCode(), and getters/setters for all fields in the class */
@Builder/*@Builder: This annotation generates a builder pattern for your class. It is especially useful for constructing objects with a large number of optional parameters in a more readable and concise manner. */
/*When you use the @Builder annotation from Lombok, you don't need to explicitly define constructors with parameters. Instead, you can use the generated builder pattern to construct objects with specific fields. */
/*This approach is particularly handy when dealing with classes that have multiple fields, and you want to create instances with only a subset of those fields without having to write multiple constructors. */
public class Product {
    //id annotation to specify that the id field is a unique identifier for our product 
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;

}
