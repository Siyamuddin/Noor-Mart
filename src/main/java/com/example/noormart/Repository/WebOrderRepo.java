package com.example.noormart.Repository;

import com.example.noormart.Model.WebOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebOrderRepo extends JpaRepository<WebOrder,Long> {
}
