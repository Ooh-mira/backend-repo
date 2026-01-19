package com.junwoo.catchtablebackend.domain.inventory.entity;

import com.junwoo.catchtablebackend.common.entity.UpdatableBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 19.
 */
@Getter
@Builder
@Entity
@Table(name = "reservation_inventories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_restaurant_date_time",
                        columnNames = {"restaurantId", "reservationDate", "reservationTime"}
                )
        },
        indexes = {
                @Index(name = "idx_restaurant_date", columnList = "restaurantId, reservationDate")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationInventoryEntity extends UpdatableBaseEntity {

    @Column(nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private LocalDate reservationDate; // 예약 날짜

    @Column(nullable = false)
    private LocalTime reservationTime; // 예약 시간

    @Column(nullable = false)
    private Integer totalTableCount; // 총 테이블 수

    @Column(nullable = false)
    private Integer availableTableCount; // 예약 가능한 테이블 수

    @Column(nullable = false)
    private Integer reservedTableCount; // 예약된 테이블 수

    @Column(nullable = false)
    private Boolean isAvailable; // 예약 가능 여부

    /**
     * 예약 차감
     */
    public void reserve() {
        if (availableTableCount <= 0) {
            throw new IllegalStateException("예약 가능한 테이블이 없습니다.");
        }
        this.availableTableCount--;
        this.reservedTableCount++;

        if (this.availableTableCount == 0) {
            this.isAvailable = false;
        }
    }

    /**
     * 예약 취소 (재고 복원)
     */
    public void cancelReservation() {
        this.availableTableCount++;
        this.reservedTableCount--;
        this.isAvailable = true;
    }

    /**
     * 재고 마감
     */
    public void closeInventory() {
        this.isAvailable = false;
    }

    /**
     * 재고 오픈
     */
    public void openInventory() {
        if (this.availableTableCount > 0) {
            this.isAvailable = true;
        }
    }
}
