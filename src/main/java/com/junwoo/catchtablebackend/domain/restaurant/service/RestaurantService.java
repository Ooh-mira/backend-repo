package com.junwoo.catchtablebackend.domain.restaurant.service;

import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantDetailResponse;
import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantMapResponse;
import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    /**
     * 지도 탭에 표시 될 식당 목록(무한스크롤, 거리순으로 나열)
     */
    Page<RestaurantMapResponse> getRestaurantMap(double userLat, double userLng, double radius, Pageable pageable);

    /**
     * 레스토랑 상세 정보 조회 (메뉴 포함)
     */
    RestaurantDetailResponse getRestaurantDetail(Long restaurantId);

    RestaurantSearchResponse search(String keyword, Pageable pageable);
}
