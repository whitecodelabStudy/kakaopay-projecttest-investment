package com.kakaopay.project.api.member.controller;

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
  public ResponseEntity<ApiResponseJson> getMember(@RequestHeader(value = "X-USER-ID") long memberId) {
    return ResponseEntity.ok(new ApiResponseJson.Builder(memberService.getMember(memberId)).build());
  }

  @PutMapping
  public ResponseEntity<ApiResponseJson> modifyMember(@RequestBody UpdateMemberDto updateMemberDto) {
    memberService.modifyMember(updateMemberDto);
    return ResponseEntity.ok(new ApiResponseJson());
  }

  @PostMapping("/signup")
  public ResponseEntity<ApiResponseJson> signup(@RequestBody AddMemberDto addMemberDto) {
    memberService.addMember(addMemberDto);
    return ResponseEntity.ok(new ApiResponseJson());
  }

}
