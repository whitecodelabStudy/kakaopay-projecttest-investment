package com.kakaopay.project.api.invest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.invest.dto.ProductInvestDto;
import com.kakaopay.project.api.invest.dto.ProductInvestStatusDto;
import com.kakaopay.project.api.invest.service.InvestService;
import com.kakaopay.project.common.apiformat.ApiResponseJson;

@RestController
@RequestMapping("/api/invest")
public class InvestController {

  private final transient InvestService investService;

  @Autowired
  public InvestController(InvestService investService) {
    this.investService = investService;
  }

  @PostMapping
  public @ResponseBody ApiResponseJson addProduct(@RequestBody ProductInvestDto productInvestDto,
      @RequestHeader(value = "X-USER-ID") long investorId) {
    productInvestDto.setInvestorId(investorId);
    ProductInvestStatusDto productInvestStatusDto = investService.insertProductInvest(productInvestDto);

    return new ApiResponseJson();
  }

}
