package com.kakaopay.project.api.investment.controller;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.kakaopay.project.api.BaseControllerTest;
import com.kakaopay.project.api.investment.dto.InvestProductDto;
import com.kakaopay.project.api.util.TestUtil;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
class InvestControllerTest extends BaseControllerTest {

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
  void investProduct() throws Exception {
    // 정상투자
    InvestProductDto investProductDto = new InvestProductDto(6l, 5555l);
    investProductDto.setMemberId(20191218l);
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.get("/api/invest").accept(MediaType.APPLICATION_JSON).headers(headers)
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(investProductDto)))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertThat(apiResponseJson.getResultCode()).isEqualTo(ApiCode.SUCCESS.getCode());

    // sold out
    investProductDto = new InvestProductDto(104l, 1111111111111l);
    investProductDto.setMemberId(20191218l);
    mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.get("/api/invest").accept(MediaType.APPLICATION_JSON).headers(headers)
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(investProductDto)))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertThat(apiResponseJson.getResultCode()).isEqualTo(ApiCode.SUCCESS.getCode());

    // 투자 기간 끝남.
    investProductDto = new InvestProductDto(117l, 1l);
    investProductDto.setMemberId(20191218l);
    mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.get("/api/invest").accept(MediaType.APPLICATION_JSON).headers(headers)
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(investProductDto)))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertThat(apiResponseJson.getResultCode()).isEqualTo(ApiCode.SUCCESS.getCode());
  }

}