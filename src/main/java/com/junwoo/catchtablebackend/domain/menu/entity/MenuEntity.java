package com.junwoo.catchtablebackend.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "menu")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String imageUrl;

    @Builder
    public MenuEntity(String menuName, Long restaurantId, BigDecimal price, String imageUrl) {
        this.menuName = menuName;
        this.restaurantId = restaurantId;
        this.price = price;
        this.imageUrl = imageUrl;
    }

}
