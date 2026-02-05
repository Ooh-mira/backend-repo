package com.junwoo.catchtablebackend.domain.restaurant.controller;

import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantMapResponse;
import com.junwoo.catchtablebackend.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapRestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public ResponseEntity<Page<RestaurantMapResponse>> getRestaurantMap(
            @RequestParam("userLat") double userLat,
            @RequestParam("userLng") double userLng,
            @RequestParam(defaultValue = "1.0") double radius,
            Pageable pageable
    ) {
        var result = restaurantService.getRestaurantMap(userLat, userLng, radius, pageable);
        return ResponseEntity.ok(result);
    }
}
