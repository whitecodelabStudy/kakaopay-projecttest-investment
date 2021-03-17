package com.kakaopay.project.api.investment.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.project.api.BaseControllerTest;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
class InvestProductControllerTest extends BaseControllerTest {

  @Test
  public void getInvestProducts() {
    // do nothing
  }

  @Test
  public void getInvestProduct() {
    // do nothing
  }
}