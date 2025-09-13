package com.a.couponcore.service;

import static com.a.couponcore.exception.ErrorCode.COUPON_NOT_EXIST;
import static com.a.couponcore.exception.ErrorCode.DUPLICATED_COUPON_ISSUE;

import com.a.couponcore.exception.CouponIssueException;
import com.a.couponcore.model.Coupon;
import com.a.couponcore.model.CouponIssue;
import com.a.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.a.couponcore.repository.mysql.CouponIssueRepository;
import com.a.couponcore.repository.mysql.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponIssueService {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponIssueJpaRepository couponIssueJpaRepository;
    private final CouponIssueRepository couponIssueRepository;

    @Transactional
    public void issue(long couponId, long userId) {
        Coupon coupon = findCoupon(couponId);
        coupon.issue();
        saveCouponIssue(couponId, userId);
    }

    // 쿠폰 조회
    @Transactional(readOnly = true)
    public Coupon findCoupon(long couponId){
        return couponJpaRepository.findById(couponId).orElseThrow(() -> {
            throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }

    // 발행 쿠폰 저장
    @Transactional
    public CouponIssue saveCouponIssue(long couponId, long userId) {
        checkAlreadyIssuance(couponId, userId);
        CouponIssue issue = CouponIssue.builder()
                .couponId(couponId)
                .userId(userId)
                .build();
        return couponIssueJpaRepository.save(issue);
    }

    // 이미 발급된 쿠폰인지 확인
    private void checkAlreadyIssuance(long couponId, long userId) {
        CouponIssue issue = couponIssueRepository.findFirstCouponIssue(couponId, userId);
        if(issue != null) {
            throw new CouponIssueException(DUPLICATED_COUPON_ISSUE, "이미 발급된 쿠폰입니다. user_id: %s, coupon_id : %s".formatted(userId, couponId));
        }
    }
}

/**     1. 쿠폰이 실제로 존재하는 지 검증  -- 없으면 예외
 *      2. 쿠폰이 있으면 가져와서 issue() 처리
 *      3. saveCouponIssue() 하기 전 checkAlreadyIssuance()로 쿠폰이 이미 발급 된 적인 있는 지 확인.
 *          쿠폰 없으면 쿠폰 하나 발행하고 저장
 */
