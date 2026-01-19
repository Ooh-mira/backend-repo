package com.junwoo.catchtablebackend.domain.report.entity;

import com.junwoo.catchtablebackend.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 19.
 */
@Getter
@Builder
@Entity
@Table(name = "payment_reports", indexes = {
        @Index(name = "idx_report_date", columnList = "reportDate"),
        @Index(name = "idx_restaurant_id_date", columnList = "restaurantId, reportDate")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReportEntity extends BaseEntity {

    @Column(nullable = false)
    private LocalDate reportDate; // 보고서 일자

    private Long restaurantId; // 식당 ID (null이면 전체)

    @Column(nullable = false)
    private Integer totalTransactions; // 총 거래 건수

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount; // 총 결제 금액

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPointAmount; // 총 포인트 사용 금액

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalActualAmount; // 총 실제 결제 금액

    @Column(nullable = false)
    private Integer approvedCount; // 승인 건수

    @Column(nullable = false)
    private Integer cancelledCount; // 취소 건수

    @Column(nullable = false)
    private Integer failedCount; // 실패 건수

    @Column(columnDefinition = "TEXT")
    private String additionalInfo; // 추가 정보 (JSON)
}
