package com.kakaopay.project.api.member.controller;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.BaseControllerTest;
import com.kakaopay.project.api.util.TestUtil;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
class MemberControllerTest extends BaseControllerTest {

  public MemberControllerTest(MockMvc mockMvc) {
    super(mockMvc);
  }

  @BeforeEach
  public void setup() throws Exception {
    if (headers != null) {
      return;
    } else {
      // access token 발급.
      makeHeader();
    }
  }


  @Test
  void getMember() throws Exception {
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.get("/api/member").accept(MediaType.APPLICATION_JSON).headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    Assert.assertEquals(apiResponseJson.getResultCode(), ApiCode.SUCCESS);
    Assert.assertEquals(apiResponseJson.getResponse().size(), 1);
  }

  @Test
  @Transactional
  void modifyMember() throws Exception {
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.put("/api/member").accept(MediaType.APPLICATION_JSON).headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    Assert.assertEquals(apiResponseJson.getResultCode(), ApiCode.SUCCESS);
  }

  @Test
  @Transactional
  void signup() throws Exception {
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/api/member/signup").accept(MediaType.APPLICATION_JSON).headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    Assert.assertEquals(apiResponseJson.getResultCode(), ApiCode.SUCCESS);
  }

}