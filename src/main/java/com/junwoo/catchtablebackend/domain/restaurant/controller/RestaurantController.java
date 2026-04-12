package com.junwoo.catchtablebackend.domain.restaurant.controller;

import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantDetailResponse;
import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantSearchResponse;
import com.junwoo.catchtablebackend.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public ResponseEntity<RestaurantSearchResponse> search(@RequestParam String keywords,
                                                           @PageableDefault Pageable pageable) {
        var result = restaurantService.search(keywords, pageable);
        return ResponseEntity.ok(result);
    }
}
