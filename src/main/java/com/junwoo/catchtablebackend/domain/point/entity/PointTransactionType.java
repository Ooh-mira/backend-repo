package com.junwoo.catchtablebackend.domain.point.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 19.
 */
@Getter
@RequiredArgsConstructor
public enum PointTransactionType {
    EARN("적립"),
    USE("사용"),
    CANCEL("취소환원"),
    EXPIRE("만료");

    private final String description;
}
