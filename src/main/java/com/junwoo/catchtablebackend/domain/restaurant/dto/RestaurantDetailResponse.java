package com.junwoo.catchtablebackend.domain.restaurant.dto;

import com.junwoo.catchtablebackend.domain.menu.dto.MenuResponse;

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
}
