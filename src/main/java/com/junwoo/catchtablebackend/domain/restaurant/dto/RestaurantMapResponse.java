package com.junwoo.catchtablebackend.domain.restaurant.dto;


import java.math.BigDecimal;

public record RestaurantMapResponse (
    Long restaurantId,
    String restaurantName,
    Long categoryId,
    Integer totalSeats,
    Double distance,
    BigDecimal latitude,
    BigDecimal longitude,
    String thumbnailUrl
) {
}
