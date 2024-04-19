package com.example.noormart.Repository;

import com.example.noormart.Model.Category;
import com.example.noormart.Model.LocalUser;
import com.example.noormart.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    Page<Product> findByName(String name, Pageable pageable);
    List<Product> findByCategory(Category category);
}
