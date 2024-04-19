package com.example.noormart.Controller;

import com.example.noormart.Configuration.AppConstants;
import com.example.noormart.Payloads.ApiResponse;
import com.example.noormart.Payloads.CategoryDto;
import com.example.noormart.Payloads.PageableCategoryResponse;
import com.example.noormart.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto)
    {
        CategoryDto categoryDto1=categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateDto(@RequestBody CategoryDto categoryDto,@PathVariable Long categoryId){
        CategoryDto categoryDto1=categoryService.updateCategory(categoryId,categoryDto);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }
    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId)
    {
        CategoryDto categoryDto=categoryService.getCategory(categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId)
    {
        categoryService.deleteCategory(categoryId);
        ApiResponse apiResponse=new ApiResponse("The category has beed deleted successfully",true);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @GetMapping("/get/all")
    public ResponseEntity<PageableCategoryResponse> getAllCategory(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)int pageNumber,
                                                   @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
                                                   @RequestParam(value = "sortBy",defaultValue =AppConstants.SORT_BY,required = false) String sortBy,
                                                   @RequestParam(value = "sortDirect",defaultValue = AppConstants.SORT_DIRECT,required = false) String sortDirect){
        PageableCategoryResponse pageableCategoryResponse=categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortDirect);
        return new ResponseEntity<PageableCategoryResponse>(pageableCategoryResponse,HttpStatus.OK);
    }

}
