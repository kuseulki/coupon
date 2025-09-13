package com.a.couponcore.repository.mysql;

import static com.a.couponcore.model.QCouponIssue.couponIssue;

import com.a.couponcore.model.CouponIssue;
import com.a.couponcore.model.QCoupon;
import com.a.couponcore.model.QCouponIssue;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CouponIssueRepository {

    private final JPQLQueryFactory queryFactory;

    public CouponIssue findFirstCouponIssue(long couponId, long userId) {
        return queryFactory.selectFrom(couponIssue)
                .where(couponIssue.couponId.eq(couponId))
                .where(couponIssue.userId.eq(userId))
                .fetchFirst();
    }

}
