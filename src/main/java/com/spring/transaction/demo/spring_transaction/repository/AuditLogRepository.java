package com.spring.transaction.demo.spring_transaction.repository;

import com.spring.transaction.demo.spring_transaction.entity.AuditLog;
import org.springframework.data.repository.CrudRepository;

public interface AuditLogRepository extends CrudRepository<AuditLog, Long> {

}
