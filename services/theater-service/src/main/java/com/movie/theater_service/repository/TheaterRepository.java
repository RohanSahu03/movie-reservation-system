package com.movie.theater_service.repository;

import com.movie.theater_service.entity.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    boolean existsByNameAndCityAndActiveTrue(
            String name,
            String city
    );

    Optional<Theater> findByIdAndActiveTrue(
            Long id
    );

    Page<Theater> findByCityIgnoreCaseAndActiveTrue(
            String city,
            Pageable pageable
    );

    Page<Theater> findByNameContainingIgnoreCaseAndActiveTrue(
            String name,
            Pageable pageable
    );

    Page<Theater> findByActiveTrue(
            Pageable pageable
    );
}