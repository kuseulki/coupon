package com.a.couponapi.controller.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

// comment 가 null 인 경우 데이터 안 보냄
@JsonInclude(value = NON_NULL)
public record CouponIssueResponseDto(boolean isSuccess, String comment) {

}
