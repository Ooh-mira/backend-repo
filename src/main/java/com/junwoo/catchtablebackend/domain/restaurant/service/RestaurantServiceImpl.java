package com.junwoo.catchtablebackend.domain.restaurant.service;

import com.junwoo.catchtablebackend.domain.category.entity.CategoryEntity;
import com.junwoo.catchtablebackend.domain.category.repository.CategoryRepository;
import com.junwoo.catchtablebackend.domain.menu.dto.MenuResponse;
import com.junwoo.catchtablebackend.domain.menu.entity.MenuEntity;
import com.junwoo.catchtablebackend.domain.menu.repository.MenuRepository;
import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantDetailResponse;
import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantMapResponse;
import com.junwoo.catchtablebackend.domain.restaurant.entity.RestaurantEntity;
import com.junwoo.catchtablebackend.domain.restaurant.repository.RestaurantDistanceProjection;
import com.junwoo.catchtablebackend.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<RestaurantMapResponse> getRestaurantMap(double userLat, double userLng, double radius, Pageable pageable){
        // radius(km)는 클라이언트에서 전달받되, 비정상 값만 최소한 방어
        double safeRadius = radius > 0 ? radius : 1.0;

        Page<RestaurantDistanceProjection> projections =
                restaurantRepository.findNearbyRestaurants(userLat, userLng, safeRadius, pageable);

        return projections.map(p -> new RestaurantMapResponse(
                p.getId(),
                p.getRestaurantName(),
                p.getCategoryId(),
                p.getTotalSeats(),
                p.getDistance(),
                p.getLatitude(),
                p.getLongitude()
        ));
    }

    @Override
    public RestaurantDetailResponse getRestaurantDetail(Long restaurantId) {
        // 1. 레스토랑 조회
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 레스토랑입니다: " + restaurantId));

        // 2. 카테고리명 조회
        String categoryName = "기타";
        if (restaurant.getCategoryId() != null) {
            categoryName = categoryRepository.findById(restaurant.getCategoryId())
                    .map(CategoryEntity::getCategoryName)
                    .orElse("기타");
        }

        // 3. 메뉴 목록 조회
        List<MenuEntity> menuEntities = menuRepository.findAllByRestaurantId(restaurantId);
        List<MenuResponse> menus = menuEntities.stream()
                .filter(m -> !"메뉴 정보 없음".equals(m.getMenuName())) // "메뉴 정보 없음" 제외
                .map(MenuResponse::from)
                .toList();

        // 4. 응답 생성
        return new RestaurantDetailResponse(
                restaurant.getId(),
                restaurant.getRestaurantName(),
                categoryName,
                restaurant.getAddress(),
                restaurant.getLatitude(),
                restaurant.getLongitude(),
                restaurant.getTotalSeats(),
                menus
        );
    }
}
