package com.kakaopay.project.api.member.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.kakaopay.project.api.base.controller.BaseControllerTest;
import com.kakaopay.project.api.member.dto.UpdateMemberDto;
import com.kakaopay.project.api.util.TestUtil;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.web.WebApplication;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
class MemberControllerTest extends BaseControllerTest {

  @Test
  @Order(1)
  @Transactional
  void getMember() throws Exception {
    LinkedHashMap<String, Object> hashMap = getMember(200L, "!@1q2w3e4r", "ADMIN");
    assertEquals(Long.valueOf(hashMap.get("memberId").toString()), 200L);
    assertEquals(hashMap.get("memberType").toString(), "ADMIN");
  }

  public LinkedHashMap<String, Object> getMember(Long memberId, String password, String memberType) throws Exception {

    generateToken(memberId, password, memberType);

    MvcResult mvcResult = getMockMvc()
        .perform(MockMvcRequestBuilders.get("/api/member").accept(MediaType.APPLICATION_JSON).headers(getHeaders())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertEquals(apiResponseJson.getResultCode(), ApiCode.SUCCESS.getCode());
    return (LinkedHashMap) apiResponseJson.getResponse().get(0);
  }

  @Test
  @Transactional
  @Order(3)
  void modifyMember() throws Exception {
    // 등록 및 토큰 발급
    signup();
    // 수정.
    UpdateMemberDto updateMemberDto = new UpdateMemberDto();
    updateMemberDto.setMemberId(1000L);
    updateMemberDto.setPassword("1q2w3e4r!@");
    updateMemberDto.setName("TEST_ADMIN");

    MvcResult mvcResult = getMockMvc()
        .perform(MockMvcRequestBuilders.put("/api/member").accept(MediaType.APPLICATION_JSON).headers(getHeaders())
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(updateMemberDto)))
        .andDo(MockMvcResultHandlers.print()).andReturn();
    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertEquals(apiResponseJson.getResultCode(), ApiCode.SUCCESS.getCode());

    LinkedHashMap<String, Object> hashMap = getMember(1000L, "1q2w3e4r!@", "INVESTOR");
    assertEquals(hashMap.get("name").toString(), "TEST_ADMIN");
    assertEquals(hashMap.get("memberType").toString(), "INVESTOR");
  }

  @Test
  @Transactional
  @Order(2)
  void signup() throws Exception {
    // 1000 회원 등록
    addMember(1000, "1q2w3e4r", "tiger", "INVESTOR");
    // 토큰 발급
    generateToken(1000, "1q2w3e4r", "INVESTOR");
    // 등록된 회원 조회.
    LinkedHashMap<String, Object> hashMap = getMember(1000L, "1q2w3e4r", "INVESTOR");
    assertEquals(Long.valueOf(hashMap.get("memberId").toString()), 1000L);
    assertEquals(hashMap.get("memberType").toString(), "INVESTOR");
  }

}