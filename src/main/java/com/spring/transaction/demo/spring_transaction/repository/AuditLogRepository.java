package com.spring.transaction.demo.spring_transaction.repository;

import com.spring.transaction.demo.spring_transaction.entity.AuditLog;
import com.spring.transaction.demo.spring_transaction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
