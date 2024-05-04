package com.example.noormart.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean paid;
    private String paymentMethod;
    @CreationTimestamp
    private Date paymentTime;


    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private Buy buy;

}