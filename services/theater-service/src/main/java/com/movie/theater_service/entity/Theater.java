package com.movie.theater_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "theaters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theater extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 150)
    private String name;


    @Column(nullable = false, length = 100)
    private String ownerName;


    @Column(nullable = false, unique = true, length = 100)
    private String email;


    @Column(nullable = false, unique = true, length = 15)
    private String phoneNumber;


    @Column(nullable = false, length = 255)
    private String address;


    @Column(nullable = false, length = 100)
    private String city;


    @Column(nullable = false, length = 100)
    private String state;


    @Column(nullable = false, length = 100)
    private String country;


    @Column(nullable = false, length = 10)
    private String zipCode;


    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;


    @OneToMany(
            mappedBy = "theater",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private Set<Screen> screens = new HashSet<>();

}