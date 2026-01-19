package com.junwoo.catchtablebackend.domain.point.entity;

import com.junwoo.catchtablebackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 19.
 */
@Getter
@Builder
@Entity
@Table(name = "point_histories", indexes = {
        @Index(name = "idx_user_id", columnList = "userId"),
        @Index(name = "idx_payment_id", columnList = "paymentId")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointHistoryEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    private Long paymentId; // 결제 ID (결제 관련일 경우)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PointTransactionType type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balanceAfter; // 거래 후 잔액

    @Column(length = 200)
    private String description; // 적립/사용 설명

    private LocalDateTime expiresAt; // 포인트 만료일 (적립 시)

}
