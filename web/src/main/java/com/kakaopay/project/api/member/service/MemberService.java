package com.kakaopay.project.api.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.member.dto.AddMemberDto;
import com.kakaopay.project.api.member.dto.MemberDetailDto;
import com.kakaopay.project.api.member.dto.UpdateMemberDto;
import com.kakaopay.project.api.member.mapper.MemberMapper;

@Service
public class MemberService {
  private final MemberMapper memberMapper;

  @Autowired
  public MemberService(MemberMapper memberMapper) {
    this.memberMapper = memberMapper;
  }

  @Transactional
  public MemberDetailDto getMember(long memberId) {
    return memberMapper.selectMemberById(memberId);
  }

  @Transactional
  public int addMember(AddMemberDto addMemberDto) {
    return memberMapper.insertMember(addMemberDto);
  }

  @Transactional
  public int modifyMember(UpdateMemberDto updateMemberDto) {
    return memberMapper.updateMember(updateMemberDto);
  }

}
