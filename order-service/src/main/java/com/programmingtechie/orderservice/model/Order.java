package com.programmingtechie.orderservice.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="t_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id/*The @Id annotation is used to specify the primary key of an entity class in JPA.
    It marks a field or property within the entity class as the unique identifier for instances of that entity.
    Only one field or property can be annotated with @Id within an entity class.
    The primary key uniquely identifies each record in the database table corresponding to the entity.
    The type of the field annotated with @Id can be any of the primitive data types (such as int, long, short) or their corresponding wrapper classes (such as Integer, Long, Short), as well as java.lang.String. */
    @GeneratedValue(strategy = GenerationType.AUTO)/*GenerationType.AUTO: This strategy allows the persistence provider (e.g., Hibernate) to automatically choose an appropriate strategy based on the underlying database. It may use identity columns, sequence generators, or other database-specific mechanisms to generate primary key values. */
    private Long id;
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)/*cascade = CascadeType.ALL  The cascade attribute of the @OneToMany annotation specifies the cascade behavior for the associated entities.  In this case, CascadeType.ALL indicates that any operations (e.g., persist, merge, remove) performed on the owning entity (Order) should be cascaded to the associated OrderLineItems. This means that if an Order is persisted, merged, or removed, the corresponding OrderLineItems will also be persisted, merged, or removed, respectively. */
    private List<OrderLineItems> OrderLineItemsList;
}
