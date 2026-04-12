package com.junwoo.catchtablebackend.domain.restaurant.dto;

import com.junwoo.catchtablebackend.domain.restaurant.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public record RestaurantSearchResponse(
        List<SearchItem> restaurants,
        PageInfo pageInfo
) {
    public record SearchItem(
            Long restaurantId,
            String restaurantName,
            String categoryName,
            String address,
            BigDecimal latitude,
            BigDecimal longitude,
            Integer totalSeats,
            String matchSource // "RESTAURANT", "MENU", "CATEGORY"
    ) {
        public static SearchItem of(RestaurantEntity entity, String categoryName, String matchSource) {
            return new SearchItem(
                    entity.getId(),
                    entity.getRestaurantName(),
                    categoryName,
                    entity.getAddress(),
                    entity.getLatitude(),
                    entity.getLongitude(),
                    entity.getTotalSeats(),
                    matchSource
            );
        }
    }

    public record PageInfo(
            int currentPage,
            int pageSize,
            long totalElements,
            int totalPages,
            boolean hasNext,
            boolean hasPrevious
    ) {
        public static PageInfo from(Page<?> page) {
            return new PageInfo(
                    page.getNumber(),
                    page.getSize(),
                    page.getTotalElements(),
                    page.getTotalPages(),
                    page.hasNext(),
                    page.hasPrevious()
            );
        }
    }

    public static RestaurantSearchResponse from(
            Page<RestaurantEntity> page,
            Function<RestaurantEntity, SearchItem> converter) {
        return new RestaurantSearchResponse(
                page.getContent().stream()
                        .map(converter)
                        .toList(),
                PageInfo.from(page)
        );
    }

    public static RestaurantSearchResponse ofManual(List<SearchItem> items, Pageable pageable, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        return new RestaurantSearchResponse(
                items,
                new PageInfo(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        totalElements,
                        totalPages,
                        pageable.getPageNumber() < totalPages - 1,
                        pageable.getPageNumber() > 0
                )
        );
    }

    public static RestaurantSearchResponse empty(Pageable pageable) {
        return new RestaurantSearchResponse(
                List.of(),
                new PageInfo(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        0L,
                        0,
                        false,
                        false
                )
        );
    }
}
