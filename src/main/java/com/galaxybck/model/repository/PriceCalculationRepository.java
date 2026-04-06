package com.galaxybck.model.repository;

import com.galaxybck.model.entity.PriceCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceCalculationRepository extends JpaRepository<PriceCalculation, Integer> {
    Optional<PriceCalculation> getPriceCalculationsById(Integer id);
}
