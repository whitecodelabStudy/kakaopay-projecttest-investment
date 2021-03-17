package com.kakaopay.project.api.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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
  public void getMember() {
    Long memberId = 20_191_218L;
    // 회원 찾기 성공일때.
    MemberDetailDto memberDetailDto = memberService.getMember(memberId);
    assertEquals(memberDetailDto.getMemberId(), memberId);

    // 오류 테스트 (조회된 회원이 없을때)
    assertThrows(ApiException.class, () -> memberService.getMember(123_123));
  }

  @Test
  @Transactional
  public void addMember() {
    AddMemberDto addMemberDto = new AddMemberDto();
    addMemberDto.setMemberId(779_797_979L);
    addMemberDto.setName("name TETET");
    addMemberDto.setMemberType("INVESTOR");
    addMemberDto.setPassword("779797979l");
    // 처음 성공
    memberService.addMember(addMemberDto);

    assertEquals(memberService.getMember(addMemberDto.getMemberId()).getMemberId(), addMemberDto.getMemberId());
  }

  @Test
  @Transactional
  @Rollback(false)
  public void modifyMember() {

    List<UpdateMemberDto> list = Arrays.asList(new UpdateMemberDto(20_171_036L, "상품관리자", "1q2w3e4r"),
        new UpdateMemberDto(20_191_218L, "승후", "1q2w3e4r"), new UpdateMemberDto(10_111_218L, "sangsub.lee", "1q2w3e4r"),
        new UpdateMemberDto(76_664L, "mirae", "1q2w3e4r"), new UpdateMemberDto(97_553L, "호비", "1q2w3e4r"),
        new UpdateMemberDto(3_325_812L, "베니", "1q2w3e4r"));

    for (UpdateMemberDto updateMemberDto : list) {
      // 회원 수정
      memberService.modifyMember(updateMemberDto);
      // 회원 조회 뒤 비교
      MemberDetailDto memberDetailDto = memberService.getMember(updateMemberDto.getMemberId());
      assertEquals(memberDetailDto.getMemberId(), updateMemberDto.getMemberId());
      assertEquals(memberDetailDto.getName(), updateMemberDto.getName());
    }
  }

}
