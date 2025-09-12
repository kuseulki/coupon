package com.a.couponcore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "coupons")
public class Coupon extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;               // 쿠폰명

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType couponType;      // 쿠폰타입

    private Integer totalQuantity;      // 쿠폰 발급 최대 수량

    @Column(nullable = false)
    private int issuedQuantity;         // 발급된 쿠폰 수량

    @Column(nullable = false)
    private int discountAmount;         // 할인 금액

    @Column(nullable = false)
    private int minAvailableAmount;     // 최소 사용 금액

    @Column(nullable = false)
    private LocalDateTime dateIssuedStart;  // 발급 시작 일시

    @Column(nullable = false)
    private LocalDateTime dateIssuedEnd;        // 발급 종료 일시
}
