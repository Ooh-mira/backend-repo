package com.junwoo.catchtablebackend.domain.payment.entity;

import com.junwoo.catchtablebackend.common.entity.UpdatableBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 16.
 */
@Getter
@Builder
@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentEntity extends UpdatableBaseEntity {

    @Column(nullable = false)
    private Long reservationId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal pointAmount; // 포인트 사용 금액

    @Column(precision = 10, scale = 2)
    private BigDecimal actualAmount; // 실제 결제 금액 (totalAmount - pointAmount)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    @Column(length = 100)
    private String pgProvider; // PG사 (KAKAO, TOSS 등)

    @Column(length = 100)
    private String pgTransactionId; // PG사 거래 고유번호

    @Column(length = 100)
    private String approvalNumber; // 승인번호

    private LocalDateTime paidAt;

    private LocalDateTime cancelledAt;

    @Column(length = 500)
    private String cancelReason; // 취소 사유

    @Column(length = 1000)
    private String failureReason; // 실패 사유
}
