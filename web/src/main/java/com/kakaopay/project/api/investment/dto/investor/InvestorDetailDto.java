package com.kakaopay.project.api.investment.dto.investor;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class InvestorDetailDto {

  /**
   * investorNo
   */
  private Long investorNo;
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
  /**
   * modifiedTime
   */
  private String modifiedTime;
  /**
   * createdTime
   */
  private String createdTime;

}
