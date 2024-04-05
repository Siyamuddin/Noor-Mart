package com.example.noormart.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 512)
    private String addressLine1;
    @Column(length = 512)
    private String addressLine2;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false,length = 70)
    private String country;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private LocalUser user;
    @CreationTimestamp
    private Date addressCreated;
    @UpdateTimestamp
    private Date addressUpdated;

}
