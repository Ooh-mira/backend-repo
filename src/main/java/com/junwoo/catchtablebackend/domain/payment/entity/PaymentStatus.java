package com.junwoo.catchtablebackend.domain.payment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 16.
 */
@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    PENDING("결제 대기"),
    APPROVED("결제 승인"),
    FAILED("결제 실패"),
    CANCELLED("결제 취소"),
    PARTIAL_CANCELLED("부분 취소");

    private final String description;
}
