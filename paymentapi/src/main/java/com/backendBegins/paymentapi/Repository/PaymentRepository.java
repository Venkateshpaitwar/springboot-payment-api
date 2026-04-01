package com.backendBegins.paymentapi.Repository;

import com.backendBegins.paymentapi.Entity.PaymentEntity;
import com.backendBegins.paymentapi.Entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByUserEmail(String userEmail);
    List<PaymentEntity> findByStatus(PaymentStatus status);
    long countByStatus(PaymentStatus status);
}