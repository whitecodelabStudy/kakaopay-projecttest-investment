package com.kakaopay.project.api.investment.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.kakaopay.project.api.base.controller.BaseControllerTest;
import com.kakaopay.project.api.investment.dto.InvestProductDto;
import com.kakaopay.project.api.util.TestUtil;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
public class InvestControllerTest extends BaseControllerTest {

  /**
   * investProduct 정상 투자 성공
   *
   * @throws Exception
   */
  @Test
  public void investProduct() throws Exception {
    InvestProductDto investProductDto = new InvestProductDto(1L, 5_555L);
    investProductDto.setMemberId(20_191_218L);
    MvcResult mvcResult = getMockMvc()
        .perform(MockMvcRequestBuilders.post("/api/invest").accept(MediaType.APPLICATION_JSON).headers(getHeaders())
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(investProductDto)))
        .andDo(MockMvcResultHandlers.print()).andReturn();

    assertThat(TestUtil.getApiResponseJson(mvcResult).getResultCode()).isEqualTo(ApiCode.SUCCESS.getCode());
  }

  /**
   * investProductIsSoldOut 투자 상품 sold out
   *
   * @throws Exception
   */
  @Test
  public void investProductIsSoldOut() throws Exception {
    InvestProductDto investProductDto = new InvestProductDto(1L, 1_111_111_111_111L);
    investProductDto.setMemberId(20_191_218L);
    MvcResult mvcResult = getMockMvc()
        .perform(MockMvcRequestBuilders.post("/api/invest").accept(MediaType.APPLICATION_JSON).headers(getHeaders())
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(investProductDto)))
        .andDo(MockMvcResultHandlers.print()).andReturn();

    assertThat(TestUtil.getApiResponseJson(mvcResult).getResultCode()).isEqualTo(ApiCode.SUCCESS.getCode());
  }

  /**
   * investProductIsFinished 투사 상품 기한 끝남.
   *
   * @throws Exception
   */
  @Test
  public void investProductIsFinished() throws Exception {
    InvestProductDto investProductDto = new InvestProductDto(2L, 1L);
    investProductDto.setMemberId(20_191_218L);
    MvcResult mvcResult = getMockMvc()
        .perform(MockMvcRequestBuilders.post("/api/invest").accept(MediaType.APPLICATION_JSON).headers(getHeaders())
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(investProductDto)))
        .andDo(MockMvcResultHandlers.print()).andReturn();

    assertThat(TestUtil.getApiResponseJson(mvcResult).getResultCode()).isEqualTo(ApiCode.SUCCESS.getCode());
  }

}