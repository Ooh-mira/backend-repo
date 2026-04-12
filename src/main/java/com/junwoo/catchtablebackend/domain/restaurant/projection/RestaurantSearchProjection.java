package com.junwoo.catchtablebackend.domain.restaurant.projection;

public interface RestaurantSearchProjection {
    Long getId();
    String getMatchSource();
    Double getRelevance();
}
