package com.spring.transaction.demo.spring_transaction.handler;

import com.spring.transaction.demo.spring_transaction.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationHandler {

    @Transactional(propagation = Propagation.NEVER)
    public void sendOrderConfirmationNotification(Order order) {

        //send mail for order confirmation
        System.out.println("Order has been placed!!");
    }

}
