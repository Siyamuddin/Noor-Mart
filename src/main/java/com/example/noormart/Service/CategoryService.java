package com.example.noormart.Service;

import com.example.noormart.Payloads.CategoryDto;
import com.example.noormart.Payloads.PageableCategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long categoryId,CategoryDto categoryDto);
    CategoryDto getCategory(Long categoryId);
    void deleteCategory(Long categoryId);
    PageableCategoryResponse getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDirect);

}
