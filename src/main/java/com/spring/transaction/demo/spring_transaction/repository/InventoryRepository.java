package com.spring.transaction.demo.spring_transaction.repository;

import com.spring.transaction.demo.spring_transaction.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Product, Integer> {

}
