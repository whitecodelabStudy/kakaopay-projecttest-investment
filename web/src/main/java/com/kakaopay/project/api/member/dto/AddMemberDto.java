package com.kakaopay.project.api.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AddMemberDto {

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
  /**
   * memberType
   */
  private String memberType;

}
