package com.galaxybck.model.repository;

import com.galaxybck.model.entity.Rider;
import com.galaxybck.model.entity.User;
import com.galaxybck.model.enums.RiderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Integer> {

    boolean existsByUniqueCode(Integer uniqueCode);

    Optional<Rider> findRiderByUser(User user);

    @Query("SELECT r FROM Rider r WHERE r.active = :status ORDER BY RANDOM() LIMIT 1")
    Optional<Rider> findRandomByStatus(@Param("status") RiderStatus status);
}
