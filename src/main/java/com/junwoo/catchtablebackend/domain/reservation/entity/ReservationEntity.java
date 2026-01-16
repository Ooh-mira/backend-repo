package com.junwoo.catchtablebackend.domain.reservation.entity;

import com.junwoo.catchtablebackend.common.entity.UpdatableBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 16.
 */
@Getter
@Builder
@Entity
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity extends UpdatableBaseEntity {

    private Long userId;

    private Long restaurantId;

    private LocalDate reservationDate;

    private LocalDateTime reservationTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
