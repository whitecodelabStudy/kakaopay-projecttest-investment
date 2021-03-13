package com.kakaopay.project.api.investment.dto.investor;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddInvestorDto {

  /**
   * investorId
   */
  private String investorId;
  /**
   * name
   */
  private String name;
  /**
   * password
   */
  private String password;

}
