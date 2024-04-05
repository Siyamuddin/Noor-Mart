package com.example.noormart.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    private String image;
    @Column(nullable = false)
    private String shortDescription;
    private String longDescription;
    @Column(nullable = false)
    private Double price;
    @OneToOne(mappedBy = "product",cascade = CascadeType.REMOVE,optional = false,orphanRemoval = true)
    private Inventory inventory;
    @CreationTimestamp
    private Date Created;
    @UpdateTimestamp
    private Date Updated;
}
