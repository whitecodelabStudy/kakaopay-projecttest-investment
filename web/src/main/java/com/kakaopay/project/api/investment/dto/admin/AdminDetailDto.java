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
  private String adminId;
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
