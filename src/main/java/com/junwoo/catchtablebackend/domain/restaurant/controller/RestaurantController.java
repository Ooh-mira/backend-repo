package com.junwoo.catchtablebackend.domain.restaurant.controller;

import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantDetailResponse;
import com.junwoo.catchtablebackend.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    /**
     * 레스토랑 상세 정보 조회 (메뉴 포함)
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDetailResponse> getRestaurantDetail(@PathVariable Long id) {
        var result = restaurantService.getRestaurantDetail(id);
        return ResponseEntity.ok(result);
    }
}
