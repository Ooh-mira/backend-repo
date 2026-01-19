package com.junwoo.catchtablebackend.domain.reservation.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 16.
 */
@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    PENDING("예약 대기"),
    CONFIRMED("예약 확정"),
    VISITED("방문 완료"),
    NO_SHOW("노쇼"),
    CANCELLED("예약 취소");

    private final String description;
}
