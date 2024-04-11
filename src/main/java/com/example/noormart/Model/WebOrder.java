package com.example.noormart.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "web_order")
public class WebOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private LocalUser user;
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "address_id",nullable = false)
//    private Address address;
    @OneToMany(mappedBy = "order",cascade = CascadeType.REMOVE,orphanRemoval = true)
    List<WebOrderQuantity> quantities=new ArrayList<>();
    @CreationTimestamp
    private Date Created;
    @UpdateTimestamp
    private Date Updated;

}
