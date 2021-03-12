package com.kakaopay.project.api.invest.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ProductInvestDto {

  /**
   * 상품 id
   */
  @NonNull
  private final Long productId;
  /**
   * 투자자아이디
   */
  @Setter
  private Long investorId;
  /**
   * 투자금액
   */
  @NonNull
  private final Long investedAmount;

}
