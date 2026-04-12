package com.junwoo.catchtablebackend.domain.restaurant.dto;

import com.junwoo.catchtablebackend.domain.menu.dto.MenuResponse;
import com.junwoo.catchtablebackend.domain.restaurant.entity.RestaurantEntity;

import java.math.BigDecimal;
import java.util.List;

public record RestaurantDetailResponse(
        Long restaurantId,
        String restaurantName,
        String categoryName,
        String address,
        BigDecimal latitude,
        BigDecimal longitude,
        Integer totalSeats,
        List<MenuResponse> menus
) {
    public static RestaurantDetailResponse of(
            RestaurantEntity entity,
            String categoryName,
            List<MenuResponse> menus) {
        return new RestaurantDetailResponse(
                entity.getId(),
                entity.getRestaurantName(),
                categoryName,
                entity.getAddress(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getTotalSeats(),
                menus
        );
    }

    public static RestaurantDetailResponse from(RestaurantEntity entity) {
        return new RestaurantDetailResponse(
                entity.getId(),
                entity.getRestaurantName(),
                "기타",
                entity.getAddress(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getTotalSeats(),
                List.of()
        );
    }
}
