package com.junwoo.catchtablebackend.domain.restaurant.repository;

import java.math.BigDecimal;

public interface RestaurantDistanceProjection {
    Long getId();
    String getRestaurantName();
    Long getCategoryId();
    Integer getTotalSeats();
    Double getDistance();
    BigDecimal getLatitude();
    BigDecimal getLongitude();
}
