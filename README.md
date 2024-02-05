Overview:
Microservices Retail is a software built using microservices architecture, intended for any client who operates a market and seeks to establish an online presence. 

Features:

Product Service: This service serves as the repository for all products, exposing a Restful API for product owners to create new products and view all available products stored in a MongoDB.
Order Service: This service offers a Restful API for clients interacting with the software to place orders.
Inventory Service: As clients place orders, this service facilitates synchronous communication to determine the availability of products in the order.
Notification Service: Upon successful placement of an order, notifications are sent asynchronously using this service, with the data stored in a MySQL database.
API Gateway: All client requests are directed to this gateway and then routed to the appropriate service.

Technologies Used:

Spring Boot: Framework for building Java-based applications.
Netflix Eureka: Service discovery.
Spring Cloud Gateway: For API routing.
Circuit Breaker: Ensures resilience of the software.
Micrometer and Zipkin: Used for distributed tracing.
Kafka: Distributed streaming platform for building real-time data pipelines and streaming applications.

Let's Connect:
Email: majdjalkhawaja@gmail.com




