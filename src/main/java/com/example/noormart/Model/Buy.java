package com.example.noormart.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
@Table(name = "buy")
@Getter
@Setter
@NoArgsConstructor
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyId;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private LocalUser localUser;
    private Double totalAmount;
    @CreationTimestamp
    private Date buyingTime;
}
