package com.junwoo.catchtablebackend.domain.point.entity;

import com.junwoo.catchtablebackend.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
@Table(name = "points", indexes = {
        @Index(name = "idx_user_id", columnList = "userId", unique = true)
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balance; // 현재 포인트 잔액

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalEarned; // 총 적립 포인트

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalUsed; // 총 사용 포인트

    private LocalDateTime lastUsedAt; // 마지막 사용일시

}
