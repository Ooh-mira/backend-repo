package com.junwoo.catchtablebackend.domain.inventory.entity;

import com.junwoo.catchtablebackend.common.entity.UpdatableBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 19.
 */
@Getter
@Builder
@Entity
@Table(name = "restaurant_time_slots", indexes = {
        @Index(name = "idx_restaurant_id", columnList = "restaurantId")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RestaurantTimeSlotEntity extends UpdatableBaseEntity {

    @Column(nullable = false)
    private Long restaurantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private DayOfWeek dayOfWeek; // 요일

    @Column(nullable = false)
    private LocalTime startTime; // 시작 시간

    @Column(nullable = false)
    private LocalTime endTime; // 종료 시간

    @Column(nullable = false)
    private Integer slotDuration; // 타임슬롯 단위 (분)

    @Column(nullable = false)
    private Integer maxTableCount; // 시간대별 최대 테이블 수

    @Column(nullable = false)
    private Boolean isActive; // 활성화 여부

    /**
     * 시간대 활성화
     */
    public void activate() {
        this.isActive = true;
    }

    /**
     * 시간대 비활성화
     */
    public void deactivate() {
        this.isActive = false;
    }
}
