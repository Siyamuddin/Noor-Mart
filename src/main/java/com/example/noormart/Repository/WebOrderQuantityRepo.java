package com.example.noormart.Repository;

import com.example.noormart.Model.WebOrderQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebOrderQuantityRepo extends JpaRepository<WebOrderQuantity,Long> {
}
