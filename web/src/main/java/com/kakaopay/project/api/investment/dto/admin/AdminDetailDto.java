package com.kakaopay.project.api.investment.dto.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AdminDetailDto {

  /**
   * adminNo
   */
  private Long adminNo;
  /**
   * adminId
   */
  private Long adminId;
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
