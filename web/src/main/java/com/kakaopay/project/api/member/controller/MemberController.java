package com.kakaopay.project.api.member.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.member.dto.AddMemberDto;
import com.kakaopay.project.api.member.dto.UpdateMemberDto;
import com.kakaopay.project.api.member.service.MemberService;
import com.kakaopay.project.common.apiformat.ApiResponseJson;

@RestController
@RequestMapping("/api/member")
public class MemberController {

  private final MemberService memberService;

  @Autowired
  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping
  @ApiOperation(value = "회원 조회", notes = "회원 조회 API.")
  public ResponseEntity<ApiResponseJson> getMember(@RequestHeader("X-USER-ID") long memberId) {
    return ResponseEntity.ok(new ApiResponseJson.Builder(memberService.getMember(memberId)).build());
  }

  @PutMapping
  @ApiOperation(value = "회원 수정", notes = "회원 수정 API.")
  public ResponseEntity<ApiResponseJson> modifyMember(@RequestBody UpdateMemberDto updateMemberDto) {
    memberService.modifyMember(updateMemberDto);
    return ResponseEntity.ok(new ApiResponseJson());
  }

  @PostMapping("/signup")
  @ApiOperation(value = "회원 추가", notes = "회원 추가 API.")
  public ResponseEntity<ApiResponseJson> signup(@RequestBody AddMemberDto addMemberDto) {
    memberService.addMember(addMemberDto);
    return ResponseEntity.ok(new ApiResponseJson());
  }

}
