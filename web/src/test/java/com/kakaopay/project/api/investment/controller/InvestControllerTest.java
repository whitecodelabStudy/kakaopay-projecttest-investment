package com.kakaopay.project.api.investment.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.project.api.BaseControllerTest;
import com.kakaopay.project.api.investment.dto.InvestProductDto;
import com.kakaopay.project.api.investment.mapper.InvestMapper;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
class InvestControllerTest extends BaseControllerTest {

  private final InvestMapper investMapper;

  @Autowired
  public InvestControllerTest(InvestMapper investMapper) {
    this.investMapper = investMapper;
  }

  @Test
  void investProduct() {

  }

  @Test
  void deleteProductInvest() {
    long investId = 9090;
    investMapper.deleteProductInvest(investId);
  }
}