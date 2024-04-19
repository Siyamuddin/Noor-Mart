package com.example.noormart.Payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class PageableCategoryResponse {
    private List<CategoryDto> categoryDtoList;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean isLast;
}
