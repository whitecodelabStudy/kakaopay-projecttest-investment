package com.kakaopay.project.api.investment.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class InvestProductDto {

  /**
   * 상품 id
   */
  @NonNull
  private Long productId;
  /**
   * 투자자 식별자
   */
  @Setter
  private Long memberId;
  /**
   * 투자금액
   */
  @NonNull
  private Long investedAmount;

}
