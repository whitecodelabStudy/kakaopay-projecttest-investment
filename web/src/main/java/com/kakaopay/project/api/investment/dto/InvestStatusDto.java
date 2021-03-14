package com.kakaopay.project.api.investment.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvestStatusDto {

  /**
   * 투자 상태
   */
  @NonNull
  private final String investStatus;
  /**
   * 투자 실패 이유
   */
  private final String failReason;

}
