package com.kakaopay.project.api.investment.dto.investor;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class InvestorDetailDto {

  /**
   * investorNd
   */
  private Long investorNd;
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
  /**
   * modifiedTime
   */
  private Long modifiedTime;
  /**
   * createdTime
   */
  private Long createdTime;

}
