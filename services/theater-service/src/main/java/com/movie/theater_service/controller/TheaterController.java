package com.movie.theater_service.controller;

import com.movie.theater_service.dto.request.CreateTheaterRequest;
import com.movie.theater_service.dto.request.UpdateTheaterRequest;
import com.movie.theater_service.dto.response.TheaterResponse;
import com.movie.theater_service.service.TheaterService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/theaters")
@RequiredArgsConstructor
public class TheaterController {


    private final TheaterService theaterService;



    @PostMapping
    public ResponseEntity<TheaterResponse> createTheater(
            @Valid @RequestBody CreateTheaterRequest request) {


        TheaterResponse response =
                theaterService.createTheater(request);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



    @GetMapping("/{id}")
    public ResponseEntity<TheaterResponse> getTheaterById(
            @PathVariable Long id) {


        return ResponseEntity.ok(
                theaterService.getTheaterById(id)
        );
    }




    @GetMapping
    public ResponseEntity<Page<TheaterResponse>> getAllTheaters(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "name")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction

    ) {


        return ResponseEntity.ok(
                theaterService.getAllTheaters(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }




    @PutMapping("/{id}")
    public ResponseEntity<TheaterResponse> updateTheater(

            @PathVariable Long id,

            @Valid
            @RequestBody UpdateTheaterRequest request

    ) {


        return ResponseEntity.ok(
                theaterService.updateTheater(
                        id,
                        request
                )
        );
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(
            @PathVariable Long id) {


        theaterService.deleteTheater(id);


        return ResponseEntity.noContent()
                .build();
    }




    @GetMapping("/search/city")
    public ResponseEntity<Page<TheaterResponse>> searchByCity(

            @RequestParam String city,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size

    ) {


        return ResponseEntity.ok(
                theaterService.searchByCity(
                        city,
                        page,
                        size
                )
        );
    }





    @GetMapping("/search/name")
    public ResponseEntity<Page<TheaterResponse>> searchByName(

            @RequestParam String name,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size

    ) {


        return ResponseEntity.ok(
                theaterService.searchByName(
                        name,
                        page,
                        size
                )
        );
    }





    @GetMapping("/active")
    public ResponseEntity<Page<TheaterResponse>> getActiveTheaters(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size

    ) {


        return ResponseEntity.ok(
                theaterService.getActiveTheaters(
                        page,
                        size
                )
        );
    }





    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(

            @PathVariable Long id,

            @RequestParam boolean active

    ) {


        theaterService.changeStatus(
                id,
                active
        );


        return ResponseEntity.ok().build();
    }

}