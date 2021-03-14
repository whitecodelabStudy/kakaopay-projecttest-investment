package com.kakaopay.project.api.member.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.member.dto.AddMemberDto;
import com.kakaopay.project.api.member.dto.MemberDetailDto;
import com.kakaopay.project.api.member.dto.UpdateMemberDto;
import com.kakaopay.project.common.exception.ApiException;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
class MemberServiceTest {

  private final MemberService memberService;

  @Autowired
  public MemberServiceTest(MemberService memberService) {
    this.memberService = memberService;
  }

  @Test
  void getMember() {
    Long memberId = 777l;
    // 회원 찾기 성공일때.
    MemberDetailDto memberDetailDto = memberService.getMember(777l);
    Assert.assertEquals(memberDetailDto.getMemberId(), memberId);

    // 오류 테스트 (조회된 회원이 없을때)
    Assert.assertThrows(ApiException.class, () -> memberService.getMember(777111111l));
  }

  @Test
  @Transactional
  void addMember() {
    AddMemberDto addMemberDto = new AddMemberDto();
    addMemberDto.setMemberId(779797979l);
    addMemberDto.setName("name TETET");
    addMemberDto.setMemberType("INVESTOR");
    addMemberDto.setPassword("779797979l");
    // 처음 성공
    memberService.addMember(addMemberDto);

    Assert.assertEquals(memberService.getMember(addMemberDto.getMemberId()).getMemberId(), addMemberDto.getMemberId());
  }

  @Test
  @Transactional
  void modifyMember() {
    AddMemberDto addMemberDto = new AddMemberDto();
    addMemberDto.setMemberId(779797979l);
    addMemberDto.setName("name TETET");
    addMemberDto.setMemberType("INVESTOR");
    addMemberDto.setPassword("779797979l");
    // 회원 생성
    memberService.addMember(addMemberDto);

    UpdateMemberDto updateMemberDto = new UpdateMemberDto();
    updateMemberDto.setMemberId(779797979l);
    updateMemberDto.setName("TXWX");
    updateMemberDto.setPassword("0p0p0p0p0");
    // 회원 수정
    memberService.modifyMember(updateMemberDto);
    // 회원 조회 뒤 비교
    MemberDetailDto memberDetailDto = memberService.getMember(addMemberDto.getMemberId());
    Assert.assertEquals(memberDetailDto.getMemberId(), updateMemberDto.getMemberId());
    Assert.assertEquals(memberDetailDto.getName(), updateMemberDto.getName());
    Assert.assertNotEquals(memberDetailDto.getName(), addMemberDto.getName());
  }

}
