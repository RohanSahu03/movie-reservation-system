package com.movie.theater_service.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheaterResponse {

    private Long id;

    private String name;

    private String ownerName;

    private String email;

    private String phoneNumber;

    private String address;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private Boolean active;
}