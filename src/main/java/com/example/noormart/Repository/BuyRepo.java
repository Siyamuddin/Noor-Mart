package com.example.noormart.Repository;

import com.example.noormart.Model.Buy;
import com.example.noormart.Model.LocalUser;
import com.example.noormart.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Repository
public interface BuyRepo extends JpaRepository<Buy,Long> {
    List<Buy> findAllByLocalUser(LocalUser localUser);
    Buy findByPayment(Payment payment);
    List<Buy> findByBuyingTimeAfter(Date date);
}
