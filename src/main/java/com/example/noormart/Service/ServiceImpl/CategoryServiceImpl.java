package com.example.noormart.Service.ServiceImpl;

import com.example.noormart.Exceptions.ResourceNotFoundException;
import com.example.noormart.Model.Category;
import com.example.noormart.Payloads.CategoryDto;
import com.example.noormart.Payloads.Responses.PageableCategoryResponse;
import com.example.noormart.Repository.CategoryRepo;
import com.example.noormart.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=modelMapper.map(categoryDto,Category.class);
        List<Category> categories=categoryRepo.findByTitle(category.getTitle());
        if(categories.isEmpty())
        {
           Category savedCategory=categoryRepo.save(category);
            return modelMapper.map(savedCategory,CategoryDto.class);
        }
        else throw new RuntimeException("Category with this title is already exist.");
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category ID",categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        Category savedCategory=categoryRepo.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category ID",categoryId));

        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category ID",categoryId));
        categoryRepo.delete(category);
    }

    @Override
    public PageableCategoryResponse getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDirect) {
        Sort sort;
        if(sortDirect.equalsIgnoreCase("asc"))
        {
            sort=Sort.by(sortBy).ascending();
        }
        else sort=Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> categoryPage=categoryRepo.findAll(pageable);
        List<CategoryDto> categoryDtoList=categoryPage.stream().map((category)-> modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());

        PageableCategoryResponse pcr=new PageableCategoryResponse();

        pcr.setCategoryDtoList(categoryDtoList);
        pcr.setTotalElements((int) categoryPage.getTotalElements());
        pcr.setPageSize(categoryPage.getSize());
        pcr.setPageNumber(categoryPage.getNumber());
        pcr.setTotalPages(categoryPage.getTotalPages());
        pcr.setLast(categoryPage.isLast());

        return pcr;
    }
}
