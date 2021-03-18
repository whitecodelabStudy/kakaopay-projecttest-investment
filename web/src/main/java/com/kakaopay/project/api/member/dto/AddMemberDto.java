package com.kakaopay.project.api.member.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AddMemberDto {

  /**
   * memberId
   */
  @NonNull
  private Long memberId;
  /**
   * name
   */
  @NonNull
  private String name;
  /**
   * password
   */
  @NonNull
  private String password;
  /**
   * memberType
   */
  @NonNull
  private String memberType;

}
