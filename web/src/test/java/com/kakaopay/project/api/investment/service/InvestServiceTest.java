package com.kakaopay.project.api.investment.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.base.controller.BaseControllerTest;
import com.kakaopay.project.api.investment.dto.InvestProductDto;
import com.kakaopay.project.api.investment.dto.InvestStatusDto;
import com.kakaopay.project.api.investment.dto.MyInvestProductDto;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class InvestServiceTest extends BaseControllerTest {

  private final InvestService investService;

  @Autowired
  public InvestServiceTest(InvestService investService) {
    super();
    this.investService = investService;
  }

  @Test
  @Transactional
  public void insertProductInvest() {
    // 정상투자
    InvestProductDto investProductDto = new InvestProductDto(3L, 5_555L);
    investProductDto.setMemberId(20_191_218L);
    InvestStatusDto investStatusDto = investService.insertProductInvest(investProductDto);
    assertThat(investStatusDto.getInvestStatus()).isEqualTo("SUCCESS");

    // sold out
    investProductDto = new InvestProductDto(2L, 1_111_111_111_111L);
    investProductDto.setMemberId(20_191_218L);
    investStatusDto = investService.insertProductInvest(investProductDto);
    assertThat("FAIL").isEqualTo(investStatusDto.getInvestStatus());
    assertThat("SOLD_OUT").isEqualTo(investStatusDto.getFailReason());

    // 투자 기간 끝남.
    investProductDto = new InvestProductDto(1L, 1L);
    investProductDto.setMemberId(20_191_218L);
    investStatusDto = investService.insertProductInvest(investProductDto);
    assertThat("FAIL").isEqualTo(investStatusDto.getInvestStatus());
    assertThat("FINISHED").isEqualTo(investStatusDto.getFailReason());
  }

  @Test
  public void getMyInvestProducts() {
    List<MyInvestProductDto> myInvestProductList = investService.getMyInvestProducts(19_840_130);
    for (MyInvestProductDto myInvestProduct : myInvestProductList) {
      assertThat(myInvestProduct.getProductId()).isEqualTo(1);
    }
  }

  @Test
  public void deleteProductInvest() {
    // 투자 취소. 모집이 끝나지 않은 상태에만 가능.
    // 성공
    int result = investService.deleteProductInvest(6);
    assertThat(result).isEqualTo(1);
    // 실패
    investService.deleteProductInvest(19);
    assertThat(result).isEqualTo(0);
    // 실패 투자한 목록이 없음.
    investService.deleteProductInvest(6);
    assertThat(result).isEqualTo(0);
  }


}