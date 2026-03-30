package com.galaxybck.model.repository;

import com.galaxybck.model.entity.PriceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<PriceConfig, Integer> {

    @Query("SELECT p FROM PriceConfig p ORDER BY p.id DESC LIMIT 1")
    Optional<PriceConfig> findLatestById();

}
