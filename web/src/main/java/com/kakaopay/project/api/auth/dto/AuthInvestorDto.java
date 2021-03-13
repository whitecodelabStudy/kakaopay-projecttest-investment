package com.kakaopay.project.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthInvestorDto {

  /**
   * 투자자 아이디
   */
  private String investId;
  /**
   * 투자자 seq
   */
  private Long investNo;
  /**
   * 투자자 이름
   */
  private String name;
  /**
   * 투자자 패스워드
   */
  private String password;


}
