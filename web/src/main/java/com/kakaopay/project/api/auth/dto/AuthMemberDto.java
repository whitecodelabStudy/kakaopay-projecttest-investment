package com.kakaopay.project.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthMemberDto {

  /**
   * 회원아이디
   */
  private long memberId;
  /**
   * 비밀번호
   */
  private String password;
  /**
   * 권한
   */
  private String authority;

}
