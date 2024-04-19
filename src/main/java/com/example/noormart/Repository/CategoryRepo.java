package com.example.noormart.Repository;

import com.example.noormart.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findByTitle(String title);
}
