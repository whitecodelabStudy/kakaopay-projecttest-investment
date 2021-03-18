package com.kakaopay.project.api.member.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
