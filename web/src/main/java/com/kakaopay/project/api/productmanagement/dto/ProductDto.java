package com.kakaopay.project.api.productmanagement.dto;

import com.kakaopay.project.common.enumtype.ProductType;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ProductDto {

  /**
   * 상품 id
   */
  @Setter
  private Long productId;

  /**
   * 투자명
   */
  @NonNull
  private final String title;

  /**
   * 상품 유형
   */
  @NonNull
  private final ProductType productType;

  /**
   * 총 투자 모집 금액
   */
  @NonNull
  private final Long totalInvestingAmount;

  /**
   * 투자시작일시
   */
  @NonNull
  private final String startedAt;

  /**
   * 투자종료일시
   */
  @NonNull
  private final String finishedAt;

  /**
   * 생성시간
   */
  @Setter
  private String createdTime;

  /**
   * 수정시간
   */
  @Setter
  private String modifiedTime;

  /**
   * 상품 관리자 식별자
   */
  @NonNull
  private final Long memberId;

}
