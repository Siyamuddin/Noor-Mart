package com.example.noormart.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    private String image;
    @Size(max = 50)
    private String shortDescription;
    @Size(max=500)
    private String longDescription;
    private Double price;
    private Integer available;
    @ManyToOne
    private Category category;
    @CreationTimestamp
    private Date Created;
    @UpdateTimestamp
    private Date Updated;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private Inventory inventory;

}
