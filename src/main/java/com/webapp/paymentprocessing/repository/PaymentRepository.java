package com.webapp.paymentprocessing.repository;

import com.webapp.paymentprocessing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

//    @Query("SELECT p FROM Payment p WHERE p.amount=?1 AND p.active=?2")
//    List<Payment> findByAmountAndByActive(Long amount,boolean active);
}

