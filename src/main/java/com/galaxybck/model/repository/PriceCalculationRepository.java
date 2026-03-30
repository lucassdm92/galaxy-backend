package com.galaxybck.model.repository;

import com.galaxybck.model.entity.PriceCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceCalculationRepository extends JpaRepository<PriceCalculation, Integer> {
}
