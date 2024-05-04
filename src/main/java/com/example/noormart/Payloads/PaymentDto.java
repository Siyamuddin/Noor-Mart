package com.example.noormart.Payloads;

import com.example.noormart.Model.Buy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class PaymentDto {
    private Long id;
    private boolean paid;
    private String paymentMethod;
    private Date paymentTime;
    private Buy buy;
}
