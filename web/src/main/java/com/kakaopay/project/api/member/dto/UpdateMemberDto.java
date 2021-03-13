package com.kakaopay.project.api.member.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateMemberDto {

  /**
   * memberId
   */
  private String memberId;
  /**
   * name
   */
  private String name;
  /**
   * password
   */
  private String password;

}
