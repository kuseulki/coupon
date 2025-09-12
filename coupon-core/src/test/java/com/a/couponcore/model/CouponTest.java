package com.a.couponcore.model;

import static org.junit.jupiter.api.Assertions.*;

import com.a.couponcore.exception.CouponIssueException;
import com.a.couponcore.exception.ErrorCode;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    @DisplayName("발급 수량이 남아있다면 true 반환")
    void 수량검증_o () {

        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .build();

        // when
        boolean result = coupon.availableIssueQuantity();

        // then
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("발급 수량이 없다면 false 반환")
    void 발급수량1_x () {

        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(100)
                .build();

        // when
        boolean result = coupon.availableIssueQuantity();

        // then
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("최대 발급 수량이 설정되지 않았다면 true 반환")
    void 발급수량2_x () {

        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(null)
                .issuedQuantity(100)
                .build();

        // when
        boolean result = coupon.availableIssueQuantity();

        // then
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("발급 기한이 시작되지 않았다면 false 반환")
    void 발급기한1_x () {

        // given
        Coupon coupon = Coupon.builder()
                .dateIssuedStart(LocalDateTime.now().plusDays(1))
                .dateIssuedEnd(LocalDateTime.now().plusDays(2))
                .build();

        // when
        boolean result = coupon.availableIssueDate();

        // then
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("발급 기한에 해당다면 true 반환")
    void 발급기한2_o () {

        // given
        Coupon coupon = Coupon.builder()
                .dateIssuedStart(LocalDateTime.now().minusDays(1))
                .dateIssuedEnd(LocalDateTime.now().plusDays(2))
                .build();

        // when
        boolean result = coupon.availableIssueDate();

        // then
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("발급 기한이 종료되면 false 반환")
    void 발급기한3_x () {

        // given
        Coupon coupon = Coupon.builder()
                .dateIssuedStart(LocalDateTime.now().minusDays(2))
                .dateIssuedEnd(LocalDateTime.now().minusDays(1))
                .build();

        // when
        boolean result = coupon.availableIssueDate();

        // then
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("발급수량_o, 발급기간_o, 발급 성공")
    void issue_1 () {

        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .dateIssuedStart(LocalDateTime.now().minusDays(1))
                .dateIssuedEnd(LocalDateTime.now().plusDays(2))
                .build();

        // when
        coupon.issue();

        // then
        Assertions.assertEquals(coupon.getIssuedQuantity(), 100);
    }

    @Test
    @DisplayName("발급수량_x, 발급기간_o, 예외 반환")
    void issue_2 () {

        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(100)
                .dateIssuedStart(LocalDateTime.now().minusDays(1))
                .dateIssuedEnd(LocalDateTime.now().plusDays(2))
                .build();

        // when & then
        CouponIssueException exception = assertThrows(CouponIssueException.class, coupon::issue);
        Assertions.assertEquals(exception.getErrorCode(), ErrorCode.INVALID_COUPON_ISSUE_QUANTITY);
    }

    @Test
    @DisplayName("발급수량_o, 발급기간_x, 예외 반환")
    void issue_3 () {

        // given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .dateIssuedStart(LocalDateTime.now().plusDays(1))
                .dateIssuedEnd(LocalDateTime.now().plusDays(2))
                .build();

        // when & then
        CouponIssueException exception = assertThrows(CouponIssueException.class, coupon::issue);
        Assertions.assertEquals(exception.getErrorCode(), ErrorCode.INVALID_COUPON_ISSUE_DATE);
    }

}