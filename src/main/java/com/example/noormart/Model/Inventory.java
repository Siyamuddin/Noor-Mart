package com.example.noormart.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false,orphanRemoval = true)
    @JoinColumn(name = "product_id",unique = true,nullable = false)
    private Product product;
    @Column(nullable = false)
    private Integer quantity;
    @CreationTimestamp
    private Date Created;
    @UpdateTimestamp
    private Date Updated;
}
