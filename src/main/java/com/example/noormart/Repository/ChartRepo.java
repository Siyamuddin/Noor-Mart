package com.example.noormart.Repository;

import com.example.noormart.Model.Chart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartRepo extends JpaRepository<Chart,Long> {
}
