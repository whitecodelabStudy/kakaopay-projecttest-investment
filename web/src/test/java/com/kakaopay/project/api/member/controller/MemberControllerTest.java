package com.kakaopay.project.api.member.controller;

import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.kakaopay.project.api.BaseControllerTest;
import com.kakaopay.project.api.member.dto.AddMemberDto;
import com.kakaopay.project.api.member.dto.UpdateMemberDto;
import com.kakaopay.project.api.util.TestUtil;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
class MemberControllerTest extends BaseControllerTest {

  private boolean isMakeHeader = true;

  @BeforeEach
  public void setup() throws Exception {
    if (headers != null && !isMakeHeader) {
      return;
    } else {
      // access token 발급.
      makeHeader();
    }
  }

  @Test
  void getMember() throws Exception {

    makeHeader();
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.get("/api/member").accept(MediaType.APPLICATION_JSON).headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    Assert.assertEquals(apiResponseJson.getResultCode(), ApiCode.SUCCESS.getCode());
    Assert.assertEquals(apiResponseJson.getResponse().size(), 1);
  }

  @Test
  @Transactional
  void modifyMember() throws Exception {
    UpdateMemberDto updateMemberDto = new UpdateMemberDto(Long.valueOf(20171036l), "테스트관리자", "1q2w3e4r");
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.put("/api/member").accept(MediaType.APPLICATION_JSON).headers(headers)
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(updateMemberDto)))
        .andDo(MockMvcResultHandlers.print()).andReturn();
    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    Assert.assertEquals(apiResponseJson.getResultCode(), ApiCode.SUCCESS.getCode());
  }

  @Test
  @Transactional
  void signup() throws Exception {
    isMakeHeader = false;
    // 사용자 추가.
    AddMemberDto addMemberDto = new AddMemberDto();
    addMemberDto.setMemberId(Long.valueOf(new Random().nextInt(99999)));
    addMemberDto.setPassword("123qwe");
    addMemberDto.setName("sssue");
    addMemberDto.setMemberType("ADMIN");
    // 확인
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/api/member/signup").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(addMemberDto)))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    Assert.assertEquals(apiResponseJson.getResultCode(), ApiCode.SUCCESS.getCode());
  }

}