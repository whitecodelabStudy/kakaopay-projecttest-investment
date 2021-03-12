package com.kakaopay.project.api.investservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.investservice.dto.InvestProductDto;
import com.kakaopay.project.api.investservice.service.InvestService;
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
  public @ResponseBody ApiResponseJson addProduct(@RequestBody InvestProductDto investProductDto,
      @RequestHeader(value = "X-USER-ID") long investorId) {
    investProductDto.setInvestorId(investorId);
    return new ApiResponseJson.Builder(investService.insertProductInvest(investProductDto)).build();
  }

}