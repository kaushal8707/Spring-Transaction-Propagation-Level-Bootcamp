package com.spring.transaction.demo.spring_transaction.handler;

import com.spring.transaction.demo.spring_transaction.entity.Product;
import com.spring.transaction.demo.spring_transaction.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryHandler {

    private final InventoryRepository inventoryRepository;

    public InventoryHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Product updateProductDetails(Product product) {

        /** forcefully throwing an exception to simulate use of tx */
        if(product.getPrice() > 5000) {
            throw new RuntimeException("DB Crashed");
        }
        return inventoryRepository.save(product);
    }

    public Product getProduct(int id) {
        return inventoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not available with id - "+id));
    }
}
