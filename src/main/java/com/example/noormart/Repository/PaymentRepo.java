package com.example.noormart.Repository;

import com.example.noormart.Model.Payment;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByPaidFalse();
    List<Payment> findAllByPaymentTimeAfterAndPaidFalse(Date date);
    List<Payment> findAllByPaymentTimeAfterAndPaidTrue(Date date);


}
