package com.kakaopay.project.api.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.member.dto.AddMemberDto;
import com.kakaopay.project.api.member.dto.MemberDetailDto;
import com.kakaopay.project.api.member.dto.UpdateMemberDto;
import com.kakaopay.project.api.member.mapper.MemberMapper;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.common.exception.ApiException;

@Service
public class MemberService {
  private final MemberMapper memberMapper;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
    this.memberMapper = memberMapper;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * getMember
   *
   * @param memberId memberId
   * @return MemberDetailDto
   */
  @Transactional
  public MemberDetailDto getMember(long memberId) {
    MemberDetailDto memberDetailDto = memberMapper.selectMemberById(memberId);
    if (memberDetailDto == null) {
      throw new ApiException(ApiCode.MEMBER_NOT_FOUND, "Member not found!!").setHttpStatus(HttpStatus.NO_CONTENT);
    } else {
      return memberDetailDto;
    }
  }

  /**
   * 회원 등록
   * 
   * @param addMemberDto addMemberDto
   */
  @Transactional
  public void addMember(AddMemberDto addMemberDto) {
    String encodePassword = passwordEncoder.encode(addMemberDto.getPassword());
    addMemberDto.setPassword(encodePassword);
    if (memberMapper.insertMember(addMemberDto) == 0) {
      throw new ApiException(ApiCode.MEMBER_SIGN_UP_FAILED, "Member signup failed!!");
    }
  }

  /**
   * 회원 수정
   *
   * @param updateMemberDto updateMemberDto
   */
  @Transactional
  public void modifyMember(UpdateMemberDto updateMemberDto) {
    String encodePassword = passwordEncoder.encode(updateMemberDto.getPassword());
    updateMemberDto.setPassword(encodePassword);
    if (memberMapper.updateMember(updateMemberDto) == 0) {
      throw new ApiException(ApiCode.MEMBER_MODIFIED_FAILED, "Member modification failed!!");
    }
  }

}
