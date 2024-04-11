package com.example.noormart.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private String shortDescription;
    private String longDescription;
    private Double price;
    private Integer quantity;
    @OneToOne(mappedBy = "product",cascade = CascadeType.ALL,optional = false,orphanRemoval = true)
    private Inventory inventory;
    @CreationTimestamp
    private Date Created;
    @UpdateTimestamp
    private Date Updated;
}
