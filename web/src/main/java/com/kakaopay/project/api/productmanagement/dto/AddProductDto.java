package com.kakaopay.project.api.productmanagement.dto;

import com.kakaopay.project.common.enumtype.ProductType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductDto {

  /**
   * 투자명
   */
  private String title;

  /**
   * 상품 유형
   */
  private ProductType productType;

  /**
   * 총 투자 모집 금액
   */
  private Long totalInvestingAmount;

  /**
   * 투자시작일시
   */
  private String startedAt;

  /**
   * 투자종료일시
   */
  private String finishedAt;

  /**
   * 상품 관리자 식별자
   */
  private Long memberId;

}
