package com.programming.techie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//it is recommended not to share classes between microservices so i had to recreate this class here in additioto its place in order service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private String orderNumber;
}