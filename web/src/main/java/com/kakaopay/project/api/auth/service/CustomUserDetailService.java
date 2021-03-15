package com.kakaopay.project.api.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kakaopay.project.api.member.dto.MemberDetailDto;
import com.kakaopay.project.api.member.mapper.MemberMapper;

@Service
public class CustomUserDetailService implements UserDetailsService {

  private final MemberMapper memberMapper;

  @Autowired
  public CustomUserDetailService(MemberMapper memberMapper) {
    this.memberMapper = memberMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String memberId) {
    MemberDetailDto memberDetailDto = memberMapper.selectMemberById(Long.valueOf(memberId));
    if (memberDetailDto != null && memberId.equals(String.valueOf(memberDetailDto.getMemberId()))) {
      return User.withUsername(String.valueOf(memberDetailDto.getMemberId())).password(memberDetailDto.getPassword())
          .authorities(memberDetailDto.getMemberType()).build();
    } else {
      throw new UsernameNotFoundException("Member not found!!");
    }
  }

}
