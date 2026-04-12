package com.junwoo.catchtablebackend.domain.menu.repository;

import com.junwoo.catchtablebackend.domain.menu.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    List<MenuEntity> findAllByRestaurantId(Long restaurantId);

    @Query("SELECT m FROM MenuEntity m Where m.restaurantId In :restaurantIds")
    List<MenuEntity> findAllByRestaurantIdIn(@Param("restaurantIds") Set<Long> restaurantIds);
}
