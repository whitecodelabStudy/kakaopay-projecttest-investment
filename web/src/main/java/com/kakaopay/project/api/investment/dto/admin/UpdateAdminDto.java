package com.kakaopay.project.api.investment.dto.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateAdminDto {

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

}
