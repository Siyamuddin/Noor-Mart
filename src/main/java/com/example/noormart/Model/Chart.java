package com.example.noormart.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Chart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chart",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    List<Item> items=new ArrayList<>();
    private Double totalAmount=0.00;
    @CreationTimestamp
    private Date Created;
    @UpdateTimestamp
    private Date Updated;

}
