package com.programmingtechie.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.programmingtechie.productservice.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{//second generic refers to the type of the id field in our Product class 

     
}
