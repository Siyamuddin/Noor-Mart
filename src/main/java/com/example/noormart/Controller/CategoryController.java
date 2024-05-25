package com.example.noormart.Controller;

import com.example.noormart.Configuration.AppConstants;
import com.example.noormart.Payloads.CategoryDto;
import com.example.noormart.Payloads.Responses.PageableCategoryResponse;
import com.example.noormart.Service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Category")
@SecurityRequirement(name = "JWT-Auth")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @Operation(
            summary = "Get a single category",
            description = "Get a single category by providing category ID.")
    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId)
    {
        CategoryDto categoryDto=categoryService.getCategory(categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }

    @Operation(
            summary = "Get all categories",
            description = "Get all categories")
    @GetMapping("/get/all")
    public ResponseEntity<PageableCategoryResponse> getAllCategory(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)int pageNumber,
                                                   @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
                                                   @RequestParam(value = "sortBy",defaultValue =AppConstants.SORT_BY,required = false) String sortBy,
                                                   @RequestParam(value = "sortDirect",defaultValue = AppConstants.SORT_DIRECT,required = false) String sortDirect){
        PageableCategoryResponse pageableCategoryResponse=categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortDirect);
        return new ResponseEntity<PageableCategoryResponse>(pageableCategoryResponse,HttpStatus.OK);
    }

}
