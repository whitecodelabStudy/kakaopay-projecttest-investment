package com.kakaopay.project.api.investment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakaopay.project.api.investment.dto.MyInvestProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.BaseControllerTest;
import com.kakaopay.project.api.investment.dto.InvestProductDto;
import com.kakaopay.project.api.investment.dto.InvestStatusDto;
import com.kakaopay.project.web.WebApplication;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class InvestServiceTest extends BaseControllerTest {

  private final InvestService investService;

  @Autowired
  public InvestServiceTest(InvestService investService) {
    this.investService = investService;
  }

  @Test
  void insertProductInvest() {
    // 정상투자
    InvestProductDto investProductDto = new InvestProductDto(6l, 5555l);
    investProductDto.setMemberId(20191218l);
    InvestStatusDto investStatusDto = investService.insertProductInvest(investProductDto);
    assertThat(investStatusDto.getInvestStatus()).isEqualTo("SUCCESS");

    // sold out
    investProductDto = new InvestProductDto(104l, 1111111111111l);
    investProductDto.setMemberId(20191218l);
    investStatusDto = investService.insertProductInvest(investProductDto);
    assertThat("FAIL").isEqualTo(investStatusDto.getInvestStatus());
    assertThat("SOLD_OUT").isEqualTo(investStatusDto.getFailReason());

    // 투자 기간 끝남.
    investProductDto = new InvestProductDto(117l, 1l);
    investProductDto.setMemberId(20191218l);
    investStatusDto = investService.insertProductInvest(investProductDto);
    assertThat("FAIL").isEqualTo(investStatusDto.getInvestStatus());
    assertThat("FINISHED").isEqualTo(investStatusDto.getFailReason());
  }

  @Test
  void getMyInvestProducts() {
    List<MyInvestProductDto> myInvestProductList = investService.getMyInvestProducts(19840130);
    assertThat(myInvestProductList.size()).isEqualTo(2);
    for (MyInvestProductDto myInvestProduct : myInvestProductList) {
      assertThat(myInvestProduct.getProductId()).isEqualTo(1);
    }
  }

  // @Test
  void deleteProductInvest() {
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