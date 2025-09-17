package com.spring.transaction.demo.spring_transaction.service;

import com.spring.transaction.demo.spring_transaction.entity.Order;
import com.spring.transaction.demo.spring_transaction.entity.Product;
import com.spring.transaction.demo.spring_transaction.handler.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessingService {

    private final InventoryHandler inventoryHandler;
    private final OrderHandler orderHandler;
    private final AuditLogHandler auditLogHandler;
    private final PaymentValidatorHandler paymentValidatorHandler;
    private final NotificationHandler notificationHandler;
    private final ProductRecommendationHandler productRecommendationHandler;

    public OrderProcessingService(InventoryHandler inventoryHandler, OrderHandler orderHandler,
                                  AuditLogHandler auditLogHandler, PaymentValidatorHandler paymentValidatorHandler,
                                  NotificationHandler notificationHandler, ProductRecommendationHandler productRecommendationHandler) {
        this.inventoryHandler = inventoryHandler;
        this.orderHandler = orderHandler;
        this.auditLogHandler = auditLogHandler;
        this.paymentValidatorHandler=paymentValidatorHandler;
        this.notificationHandler=notificationHandler;
        this.productRecommendationHandler=productRecommendationHandler;
    }

    /** REQUIRED:       Join an Existing Transaction or create a new one If NOT EXISTS */
    /** REQUIRES_NEW:   Always create a new Transaction, suspending if any existing transaction */
    /** MANDATORY:      When the propagation is MANDATORY, if there is an active transaction, then it will be used.
     *                  If there isn’t an active transaction, then Spring throws an exception: */
    /** NEVER:          Ensure that the method will run without transaction If found any tx will throw an exception */
    /** NOT_SUPPORTED:  Execute method without Transaction, suspending If found any transaction. */
    /** SUPPORTS:       Supports If there is any Active transaction, If not execute without transaction as well */
    /** NESTED:         Execute within a nested transaction, allowing nested transaction to rolled back independently
                        If there is any exception or error without impacting outer transaction */


    @Transactional(propagation = Propagation.REQUIRED)
    public Order placeAnOrder(Order order) {

        //get product inventory
        Product product = inventoryHandler.getProduct(order.getProductId());
        //validate product availability < (5)
        validateStockAvailability(order, product);
        //update total price in order entity
        double totalPrice = product.getPrice() * order.getQuantity();
        order.setTotalPrice(totalPrice);
        Order savedOrder=null;
        try {
            //save order
            savedOrder = orderHandler.saveOrder(order);
            //update stock in inventory
            updateInventoryStock(order, product);
            auditLogHandler.logAuditDetails(order, "order placement succeeded");
        } catch (Exception ex) {
            auditLogHandler.logAuditDetails(order, "order placement failed");
        }

        /** MANDATORY - uncomment below code to test MANDATORY usecase*/
        //  paymentValidatorHandler.validatePayments(order);

        /**NEVER- uncomment below code to see error Existing transaction found for transaction marked with propagation 'never'
                 because the reason is we are running NEVER within a transaction*/
        // notificationHandler.sendOrderConfirmationNotification(order);
        /**NEVER - To resolve from call below method from OrderProcessingController*/
        // return ResponseEntity.ok(orderProcessingService.processOrder(order));

        /**NOT_SUPPORTED - Execute method without Transaction, suspending If found any transaction and If any transaction found it will suspend and execute . I will not support Transaction so don't come in my way.*/
        // productRecommendationHandler.getRecommendation();

        /**SUPPORTS - If there is any Active transaction, If not execute without transaction as well*/
        // getCustomerDetails();

        return savedOrder;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void getCustomerDetails() {
        System.out.println("Customer details fetched !!");
    }

    /**NEVER Example - call this method after placeAnOrder is successfully completed */
    public Order processOrder(Order order){
        //Step 1: Place an Order & update Inventory
        Order order1  = placeAnOrder(order);
        //step2: send notification(non-transactional)
        notificationHandler.sendOrderConfirmationNotification(order1);
        return order1;
    }

    private void validateStockAvailability(Order order, Product product) {
        int availableProducts = product.getStockQuantity();
        if(order.getQuantity() > availableProducts) {
            throw new RuntimeException("Insufficient Stock !!");
        }
    }

    private void updateInventoryStock(Order order, Product product) {
        int availableStock = product.getStockQuantity()- order.getQuantity();
        product.setStockQuantity(availableStock);
        inventoryHandler.updateProductDetails(product);
    }
}
