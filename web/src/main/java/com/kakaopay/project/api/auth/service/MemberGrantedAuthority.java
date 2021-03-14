package com.kakaopay.project.api.auth.service;

import org.springframework.security.core.GrantedAuthority;

public class MemberGrantedAuthority implements GrantedAuthority {

  private String memberType;

  public MemberGrantedAuthority(String memberType) {
    this.memberType = memberType;
  }

  public void setMemberType(String memberType) {
    this.memberType = memberType;
  }

  @Override
  public String getAuthority() {
    return this.memberType;
  }

}
