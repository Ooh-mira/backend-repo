package com.junwoo.catchtablebackend.domain.payment.entity;

import com.junwoo.catchtablebackend.common.entity.UpdatableBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity extends UpdatableBaseEntity {

    private BigDecimal totalAmount;

    private PaymentMethod method;

    private PaymentStatus status;

    private LocalDateTime paidAt;
}
