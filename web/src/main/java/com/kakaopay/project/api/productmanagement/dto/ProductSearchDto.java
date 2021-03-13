package com.kakaopay.project.api.productmanagement.dto;

import com.kakaopay.project.common.enumtype.InvestmentRecruitmentStatus;
import com.kakaopay.project.common.enumtype.ProductType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ProductSearchDto {

  /**
   * 상품 id
   */
  @Setter
  private Long productId;

  /**
   * 투자명
   */
  @NonNull
  private String title;

  /**
   * 상품 유형
   */
  @NonNull
  private ProductType productType;

  /**
   * 총 투자 모집 금액
   */
  @NonNull
  private Long totalInvestingAmount;

  /**
   * 투자시작일시
   */
  @NonNull
  private String startedAt;

  /**
   * 투자종료일시
   */
  @NonNull
  private String finishedAt;

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
  private Long memberId;

  /**
   * 투자자 수
   */
  @Setter
  private Integer investorCount;

  /**
   * 현재모집금액
   */
  @Setter
  private Long nowInvestingAmount;

  /**
   * 투자 모집 상태
   */
  @Setter
  private InvestmentRecruitmentStatus investmentRecruitmentStatus;

}
