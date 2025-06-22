package com.spring.transaction.demo.spring_transaction.repository;

import com.spring.transaction.demo.spring_transaction.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
