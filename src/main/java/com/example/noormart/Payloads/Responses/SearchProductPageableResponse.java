package com.example.noormart.Payloads.Responses;

import com.example.noormart.Payloads.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductPageableResponse {
    private List<ProductDto> SearchproductDtoList;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean isLast;
}
