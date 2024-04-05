package com.example.noormart.Payloads;

import com.example.noormart.Model.Product;
import com.example.noormart.Model.WebOrder;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WebOrderQuantityDto {
    private Product product;
    private Integer quantity;
    private WebOrder order;

}
