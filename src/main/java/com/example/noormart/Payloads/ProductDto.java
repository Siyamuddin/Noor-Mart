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

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    @NotEmpty
    @Size(min = 2,message = "Product name must contain at least 2 characters")
    private String name;
    private String image;
    @NotEmpty
    @Size(max = 100,message = "Short description should be between 1 to 100 characters.")
    private String shortDescription;
    @Size(max = 500,message = "Long description should be between 1 to 500 characters.")
    private String longDescription;
    @NotEmpty(message = "Price can not be empty")
    private Double price;
    @NotEmpty
    private InventoryDto inventory;
}
