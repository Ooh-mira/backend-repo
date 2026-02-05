package com.junwoo.catchtablebackend.domain.restaurant.repository;

import com.junwoo.catchtablebackend.domain.restaurant.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
