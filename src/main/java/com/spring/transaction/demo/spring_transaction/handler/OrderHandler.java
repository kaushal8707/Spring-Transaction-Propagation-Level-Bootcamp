package com.spring.transaction.demo.spring_transaction.handler;

import com.spring.transaction.demo.spring_transaction.entity.Order;
import com.spring.transaction.demo.spring_transaction.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderHandler {

    private final OrderRepository orderRepository;

    public OrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order saveOrder(Order order) {

        return orderRepository.save(order);
    }
}
