package com.junwoo.catchtablebackend.domain.restaurant.service;

import com.junwoo.catchtablebackend.domain.category.entity.CategoryEntity;
import com.junwoo.catchtablebackend.domain.category.repository.CategoryRepository;
import com.junwoo.catchtablebackend.domain.menu.dto.MenuResponse;
import com.junwoo.catchtablebackend.domain.menu.entity.MenuEntity;
import com.junwoo.catchtablebackend.domain.menu.repository.MenuRepository;
import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantDetailResponse;
import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantMapResponse;
import com.junwoo.catchtablebackend.domain.restaurant.dto.RestaurantSearchResponse;
import com.junwoo.catchtablebackend.domain.restaurant.entity.RestaurantEntity;
import com.junwoo.catchtablebackend.domain.restaurant.projection.RestaurantDistanceProjection;
import com.junwoo.catchtablebackend.domain.restaurant.repository.RestaurantRepository;
import com.junwoo.catchtablebackend.domain.restaurant.projection.RestaurantSearchProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<RestaurantMapResponse> getRestaurantMap(double userLat, double userLng, double radius, Pageable pageable) {
        double safeRadius = radius > 0 ? radius : 1.0;

        Page<RestaurantDistanceProjection> projections =
                restaurantRepository.findNearbyRestaurants(userLat, userLng, safeRadius, pageable);

        // 페이지 내 식당 ID를 모아 대표 메뉴 이미지를 배치 조회
        Set<Long> restaurantIds = projections.getContent().stream()
                .map(RestaurantDistanceProjection::getId)
                .collect(Collectors.toSet());

        Map<Long, String> thumbnailMap = menuRepository.findAllByRestaurantIdIn(restaurantIds).stream()
                .filter(m -> m.getImageUrl() != null && !m.getImageUrl().isBlank())
                .collect(Collectors.toMap(
                        MenuEntity::getRestaurantId,
                        MenuEntity::getImageUrl,
                        (first, second) -> first // 같은 식당의 메뉴가 여러개면 첫 번째 이미지 사용
                ));

        return projections.map(p -> new RestaurantMapResponse(
                p.getId(),
                p.getRestaurantName(),
                p.getCategoryId(),
                p.getTotalSeats(),
                p.getDistance(),
                p.getLatitude(),
                p.getLongitude(),
                thumbnailMap.get(p.getId())
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
        return RestaurantDetailResponse.of(restaurant, categoryName, menus);
    }

    @Override
    public RestaurantSearchResponse search(String keywords, Pageable pageable) {

        String sanitizedKeyword = sanitizeKeyword(keywords);

        if (sanitizedKeyword.isBlank()) {
            return RestaurantSearchResponse.empty(pageable);
        }

        // 1. 검색 (DB 정렬 + 페이지네이션)
        Page<RestaurantSearchProjection> searchResult =
                restaurantRepository.searchUnified(sanitizedKeyword, pageable);

        if (!searchResult.hasContent()) {
            return RestaurantSearchResponse.empty(pageable);
        }

        // 2. 레스토랑 엔티티 조회
        List<Long> ids = searchResult.getContent().stream()
                .map(RestaurantSearchProjection::getId)
                .toList();

        Map<Long, RestaurantEntity> entityMap = restaurantRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(RestaurantEntity::getId, e -> e));

        // 3. 카테고리 배치 조회
        Set<Long> catIds = entityMap.values().stream()
                .map(RestaurantEntity::getCategoryId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Long, String> categoryNameMap = categoryRepository.findAllById(catIds).stream()
                .collect(Collectors.toMap(CategoryEntity::getId, CategoryEntity::getCategoryName));

        // 4. 검색 순서 유지하며 응답 생성
        Map<Long, RestaurantSearchProjection> projMap = searchResult.getContent().stream()
                .collect(Collectors.toMap(RestaurantSearchProjection::getId, p -> p));

        List<RestaurantSearchResponse. SearchItem> items = ids.stream()
                .map(id -> {
                    RestaurantEntity r = entityMap.get(id);
                    if (r == null) return null;
                    String catName = r.getCategoryId() != null
                            ? categoryNameMap.getOrDefault(r.getCategoryId(), "") : "";
                    return RestaurantSearchResponse.SearchItem.of(r, catName, projMap.get(id).getMatchSource());
                })
                .filter(Objects::nonNull)
                .toList();

        return RestaurantSearchResponse.ofManual(items, pageable, (int) searchResult.getTotalElements());
    }






    private String sanitizeKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return "";
        }

        String cleaned = keyword.replaceAll("[^\\w\\s가-힣ㄱ-ㅎㅏ-ㅣ]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        // ngram 파서: 각 단어에 +(필수) 적용, 와일드카드 불필요 (부분 문자열 매칭 지원)
        return Arrays.stream(cleaned.split("\\s+"))
                .filter(s -> !s.isBlank())
                .map(s -> "+" + s)
                .collect(Collectors.joining(" "));
    }
}
