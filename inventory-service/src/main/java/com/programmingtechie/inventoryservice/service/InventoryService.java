package com.programmingtechie.inventoryservice.service;

import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmingtechie.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    
    private final InventoryRepository inventoryRepository;




    @Transactional(readOnly = true)/*The @Transactional annotation in Spring Boot is used to mark a method, class, or interface as transactional. When a method annotated with @Transactional is invoked, Spring Boot manages the transaction boundaries automatically. This annotation ensures that the method executes within a transactional context, where database operations are either committed if the method completes successfully or rolled back if an exception occurs. */
    public List<InventoryResponse> isInStock(List<String> skuCode){
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }



}
/*In Spring's @Transactional annotation, the readOnly attribute is used to specify whether the annotated method is read-only or not. When readOnly is set to true, it indicates that the annotated method will only perform read operations on the database and will not modify any data.

Setting readOnly to true can provide several benefits:

Optimization: When the transaction is marked as read-only, the underlying database provider (such as Hibernate) may optimize the transaction handling. For example, it might skip acquiring certain locks or perform other optimizations that are safe for read-only transactions.

Performance: Read-only transactions are typically faster because they don't involve any write operations that require database locks, data modifications, or transaction logging.

Concurrency: By marking certain methods as read-only, you can allow multiple transactions to read the same data concurrently without worrying about conflicts or locks. */
/*The @Transactional annotation in Spring Boot provides several attributes that allow you to customize the behavior of transactions. Here's an explanation of some commonly used attributes:

readOnly: Specifies whether the transaction is read-only. Setting this attribute to true indicates that the transaction only performs read operations and should not modify any data. Default is false.

timeout: Specifies the timeout for the transaction in seconds. If the transaction takes longer than the specified timeout value, it will be automatically rolled back. Default is -1, indicating no timeout.

rollbackFor and noRollbackFor: These attributes specify which exceptions should trigger a rollback and which exceptions should not trigger a rollback, respectively. You can specify one or more exception classes. For example:

rollbackFor = {Exception1.class, Exception2.class}
noRollbackFor = {Exception3.class, Exception4.class} */

/*While it's not strictly mandatory to annotate a service layer method with @Transactional if it performs a single database operation, it's generally considered a good practice to do so for these reasons:

1. Automatic Rollback on Exceptions:

Even if the method involves only one database interaction, unexpected exceptions can still occur.
Using @Transactional ensures that any partial changes made to the database are automatically rolled back, preventing data inconsistencies.
2. Consistency and Isolation:

The @Transactional annotation guarantees that the operation is executed as a single, atomic unit, even if it involves multiple underlying SQL statements.
This ensures data consistency and isolation from other concurrent transactions, preventing potential conflicts or dirty reads.
3. Encapsulation and Future-Proofing:

By annotating the method with @Transactional, you're clearly indicating its transactional nature and encapsulating the database interaction within a consistent boundary.
This makes the code more readable, maintainable, and easier to reason about.
It also prepares for potential future changes where additional database operations might be added to the method, ensuring they'll automatically participate in the transaction.
4. Potential Performance Benefits:

In some cases, using @Transactional can even lead to performance improvements, as the database might be able to optimize multiple operations within a single transaction more efficiently than when they're executed separately.
5. Consistency with Best Practices:

Using @Transactional consistently for service layer methods that interact with the database aligns with common Spring development practices and promotes a clear separation of concerns.
In conclusion, while it's not strictly necessary to annotate a method with @Transactional if it performs a single database operation, doing so offers several benefits in terms of data consistency, exception handling, encapsulation, and potential performance gains. It's generally recommended to follow this practice to ensure data integrity and maintainability in your Spring Boot applications. */