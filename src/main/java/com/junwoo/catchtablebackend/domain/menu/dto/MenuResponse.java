package com.junwoo.catchtablebackend.domain.menu.dto;

import com.junwoo.catchtablebackend.domain.menu.entity.MenuEntity;

import java.math.BigDecimal;

public record MenuResponse (
        Long menuId,
        String menuName,
        BigDecimal price,
        String imageUrl
        ){
    public static MenuResponse from(MenuEntity menuEntity) {
        return new MenuResponse(
                menuEntity.getId(),
                menuEntity.getMenuName(),
                menuEntity.getPrice(),
                menuEntity.getImageUrl()
        );
    }
}
