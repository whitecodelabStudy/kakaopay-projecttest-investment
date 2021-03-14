package com.kakaopay.project.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberDto {

  /**
   * memberId
   */
  private Long memberId;
  /**
   * name
   */
  private String name;
  /**
   * password
   */
  private String password;

}
