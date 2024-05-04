package com.example.noormart.Exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class ProductOutOfStockException extends RuntimeException{
    private Long productId;
    private Integer available;

    public ProductOutOfStockException(Long productId,Integer available) {
        super(String.format("Product with product ID:%d is out of stock, Only available: %d items.",productId,available));
        this.productId = productId;
        this.available=available;
    }
}
