package com.example.noormart.Payloads;

import com.example.noormart.Model.Inventory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String image;
    private String shortDescription;
    private String longDescription;
    private Double price;
    private Integer available;
    private Date Created;
    private Date Updated;
}
