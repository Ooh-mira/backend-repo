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
public enum PaymentMethod {
    KAKAO_PAY("카카오페이", "KAKAO"),
    TOSS_PAY("토스페이먼츠", "TOSS"),
    CARD("신용/체크카드", "CARD"),
    POINT("포인트", "POINT"),
    MIXED("복합결제", "MIXED");

    private final String description;
    private final String pgProvider;
}