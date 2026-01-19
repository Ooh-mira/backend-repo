package com.junwoo.catchtablebackend.domain.policy.entity;

import com.junwoo.catchtablebackend.common.entity.UpdatableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 19.
 */
@Getter
@Builder
@Entity
@Table(name = "cancellation_policies", indexes = {
        @Index(name = "idx_restaurant_id", columnList = "restaurantId")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CancellationPolicyEntity extends UpdatableBaseEntity {

    @Column(nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private Integer hoursBeforeReservation; // 예약 N시간 전

    @Column(nullable = false)
    private Integer cancelFeePercentage; // 취소 수수료 비율 (%)

    @Column(precision = 10, scale = 2)
    private BigDecimal cancelFeeFixedAmount; // 고정 취소 수수료 금액

    @Column(length = 500)
    private String description; // 정책 설명

    @Column(nullable = false)
    private Boolean isActive; // 활성화 여부

    /**
     * 취소 수수료 계산
     */
    public BigDecimal calculateCancelFee(BigDecimal depositAmount) {
        if (cancelFeeFixedAmount != null) {
            return cancelFeeFixedAmount;
        }

        BigDecimal percentage = new BigDecimal(cancelFeePercentage).divide(new BigDecimal(100));
        return depositAmount.multiply(percentage);
    }
}
