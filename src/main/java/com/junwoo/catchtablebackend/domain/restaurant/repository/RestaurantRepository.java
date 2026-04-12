package com.junwoo.catchtablebackend.domain.restaurant.repository;

import com.junwoo.catchtablebackend.domain.restaurant.entity.RestaurantEntity;
import com.junwoo.catchtablebackend.domain.restaurant.projection.RestaurantDistanceProjection;
import com.junwoo.catchtablebackend.domain.restaurant.projection.RestaurantSearchProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    @Query(value = """
        SELECT r.*,
        (6371 * acos(cos(radians(:userLat)) * cos(radians(r.latitude)) * cos(radians(r.longitude) - radians(:userLng))
        + sin(radians(:userLat)) * sin(radians(r.latitude)))) AS distance
        FROM restaurant r
        HAVING distance < :radius
        ORDER BY distance ASC
        """,
            countQuery = """
        SELECT count(*)
        FROM restaurant r
        WHERE (6371 * acos(cos(radians(:userLat)) * cos(radians(r.latitude)) * cos(radians(r.longitude)
        - radians(:userLng)) + sin(radians(:userLat)) * sin(radians(r.latitude)))) < :radius
        """,
            nativeQuery = true)
    Page<RestaurantDistanceProjection> findNearbyRestaurants(
            @Param("userLat") double userLat,
            @Param("userLng") double userLng,
            @Param("radius") double radius,
            Pageable pageable
    );


    @Query(value = """
        SELECT rs.restaurant_id AS id,
                MATCH(rs.search_text) AGAINST(:keyword IN BOOLEAN MODE) AS relevance,
                CASE WHEN MATCH(rs.restaurant_name) AGAINST(:keyword IN BOOLEAN MODE) > 0
                    THEN 'RESTAURANT' ELSE 'MENU' END AS matchSource
        FROM restaurant_search rs
        WHERE MATCH(rs.search_text) AGAINST(:keyword IN BOOLEAN MODE)
        ORDER BY
            MATCH(rs.restaurant_name) AGAINST(:keyword IN BOOLEAN MODE) DESC,
            relevance DESC
        """,
        countQuery = """
        SELECT COUNT(*) FROM restaurant_search
        WHERE MATCH(search_text) AGAINST(:keyword IN BOOLEAN MODE)
        """,
        nativeQuery = true)
    Page<RestaurantSearchProjection> searchUnified(@Param("keyword") String keyword, Pageable pageable);

    // ID 목록으로 레스토랑 조회
    List<RestaurantEntity> findAllByIdIn(Collection<Long> ids);
}
