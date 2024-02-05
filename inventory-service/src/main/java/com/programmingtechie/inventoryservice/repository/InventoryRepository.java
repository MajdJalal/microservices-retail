
package com.programmingtechie.inventoryservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmingtechie.inventoryservice.model.Inventory;

public interface  InventoryRepository extends JpaRepository<Inventory, Long>{
    //extension method//sping data jpa gonna automatically do the logic of this method at runTime (gonna generte a query to retrieve a query by this SkuCode)
    Optional<Inventory> findBySkuCode(String skuCode);

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
