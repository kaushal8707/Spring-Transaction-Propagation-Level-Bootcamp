package com.spring.transaction.demo.spring_transaction.handler;

import com.spring.transaction.demo.spring_transaction.entity.AuditLog;
import com.spring.transaction.demo.spring_transaction.entity.Order;
import com.spring.transaction.demo.spring_transaction.repository.AuditLogRepository;
import com.spring.transaction.demo.spring_transaction.repository.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AuditLogHandler {

    private final AuditLogRepository auditLogRepository;

    public AuditLogHandler(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /** log audit details ( runs in an independent transaction ) */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAuditDetails(Order order, String action) {

        AuditLog auditLog=new AuditLog();
        auditLog.setOrderId(Long.valueOf(order.getId()));
        auditLog.setAction(action);
        auditLog.setTimeStamp(LocalDateTime.now());

        //save audit log
        auditLogRepository.save(auditLog);
    }
}
