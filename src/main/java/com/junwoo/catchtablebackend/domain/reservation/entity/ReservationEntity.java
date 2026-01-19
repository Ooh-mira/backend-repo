package com.junwoo.catchtablebackend.domain.reservation.entity;

import com.junwoo.catchtablebackend.common.entity.UpdatableBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 16.
 */
@Getter
@Builder
@Entity
@Table(name = "reservations", indexes = {
        @Index(name = "idx_user_id", columnList = "userId"),
        @Index(name = "idx_restaurant_id", columnList = "restaurantId"),
        @Index(name = "idx_reservation_date", columnList = "reservationDate")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationEntity extends UpdatableBaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private LocalDate reservationDate; // 예약 날짜

    @Column(nullable = false)
    private LocalTime reservationTime; // 예약 시간

    @Column(nullable = false)
    private Integer partySize; // 예약 인원

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status;

    @Column(length = 500)
    private String specialRequest; // 특별 요청사항

    @Column(length = 20)
    private String customerPhone; // 고객 연락처

    @Column(length = 50)
    private String customerName; // 예약자 이름

    private LocalDateTime visitedAt; // 방문 완료 시간

    private LocalDateTime cancelledAt; // 취소 시간

    @Column(length = 500)
    private String cancelReason; // 취소 사유

    /**
     * 예약 확정
     */
    public void confirm() {
        this.status = ReservationStatus.CONFIRMED;
    }

    /**
     * 방문 완료 처리
     */
    public void complete() {
        this.status = ReservationStatus.VISITED;
        this.visitedAt = LocalDateTime.now();
    }

    /**
     * 노쇼 처리
     */
    public void markAsNoShow() {
        this.status = ReservationStatus.NO_SHOW;
    }

    /**
     * 예약 취소
     */
    public void cancel(String reason) {
        this.status = ReservationStatus.CANCELLED;
        this.cancelReason = reason;
        this.cancelledAt = LocalDateTime.now();
    }

    /**
     * 취소 가능 여부 확인
     */
    public boolean isCancellable() {
        return this.status == ReservationStatus.PENDING
                || this.status == ReservationStatus.CONFIRMED;
    }
}
