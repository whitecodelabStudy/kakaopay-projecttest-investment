package com.kakaopay.project.api.investment.dto.investor;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddInvestorDto {

  /**
   * investorId
   */
  private Long investorId;
  /**
   * name
   */
  private Long name;
  /**
   * password
   */
  private Long password;

}
