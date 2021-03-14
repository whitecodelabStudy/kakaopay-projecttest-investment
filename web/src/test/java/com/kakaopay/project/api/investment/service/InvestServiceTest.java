package com.kakaopay.project.api.investment.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.project.api.BaseControllerTest;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
class InvestServiceTest extends BaseControllerTest {

  private final InvestService investService;

  @Autowired
  public InvestServiceTest(InvestService investService) {
    this.investService = investService;
  }

  @Test
  void insertProductInvest() {
  }

  @Test
  void deleteProductInvest() {
  }
}