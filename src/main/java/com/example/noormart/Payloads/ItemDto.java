package com.example.noormart.Payloads;

import com.example.noormart.Model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {
    private ProductDto product;
    private Integer quantity;
    private ChartDto chartDto;

}
