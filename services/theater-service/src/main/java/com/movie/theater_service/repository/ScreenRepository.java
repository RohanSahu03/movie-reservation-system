package com.movie.theater_service.repository;


import com.movie.theater_service.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScreenRepository
        extends JpaRepository<Screen,Long> {


    List<Screen> findByTheaterIdAndActiveTrue(Long theaterId);

}