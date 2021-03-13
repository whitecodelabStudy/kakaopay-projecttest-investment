package com.kakaopay.project.api.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.kakaopay.project.api.member.dto.AddMemberDto;
import com.kakaopay.project.api.member.dto.MemberDetailDto;
import com.kakaopay.project.api.member.dto.UpdateMemberDto;

@Mapper
public interface MemberMapper {

  int insertMember(AddMemberDto addMemberDto);

  int updateMember(UpdateMemberDto updateMemberDto);

  MemberDetailDto selectMemberById(long memberId);

}
