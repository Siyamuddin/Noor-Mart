package com.example.noormart.Repository;

import com.example.noormart.Model.Inventory;
import com.example.noormart.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {
    List<Inventory> findByProductId(Long id);
}
