package com.junwoo.catchtablebackend.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String restaurantName;

    @Column
    private Long categoryId;

    @Column
    private String address;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(nullable = false)
    private Integer totalSeats;

    @Builder
    public RestaurantEntity(String restaurantName, Long categoryId, String address, BigDecimal latitude,
                            BigDecimal longitude, Integer totalSeats) {
        this.restaurantName = restaurantName;
        this.categoryId = categoryId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalSeats = totalSeats;
    }
}


