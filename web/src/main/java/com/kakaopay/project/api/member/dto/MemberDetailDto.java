package com.kakaopay.project.api.member.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MemberDetailDto {

  /**
   * memberId 회원아이디
   */
  private Long memberId;
  /**
   * name 이름
   */
  private String name;
  /**
   * password 비밀번호
   */
  private String password;
  /**
   * memberType 회원유형(ADMIN/INVESTOR)
   */
  private String memberType;
  /**
   * modifiedTime
   */
  private String modifiedTime;
  /**
   * createdTime
   */
  private String createdTime;

}
