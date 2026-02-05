package com.junwoo.catchtablebackend.domain.menu.repository;

import com.junwoo.catchtablebackend.domain.menu.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    List<MenuEntity> findAllByRestaurantId(Long restaurantId);
}
