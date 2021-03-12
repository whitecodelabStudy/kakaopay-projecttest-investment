package com.kakaopay.project.api.investment.dto.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateAdminDto {

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

}
