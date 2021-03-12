package com.kakaopay.project.api.investment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.investment.dto.investor.AddInvestorDto;
import com.kakaopay.project.api.investment.dto.investor.UpdateInvestorDto;
import com.kakaopay.project.api.investment.service.InvestorService;
import com.kakaopay.project.common.apiformat.ApiResponseJson;

@RestController
@RequestMapping("/api/investor")
public class InvestorController {

  private final transient InvestorService investorService;

  @Autowired
  public InvestorController(InvestorService investorService) {
    this.investorService = investorService;
  }

  @PostMapping
  public ResponseEntity<ApiResponseJson> addProduct(@RequestBody AddInvestorDto addInvestorDto,
      @RequestHeader(value = "X-USER-ID") long investorNo) {
    return ResponseEntity.ok(new ApiResponseJson.Builder(investorService.addInvestor(addInvestorDto)).build());
  }

  @PutMapping
  public ResponseEntity<ApiResponseJson> addProduct(@RequestBody UpdateInvestorDto updateInvestorDto) {
    return ResponseEntity.ok(new ApiResponseJson.Builder(investorService.modifyInvestor(updateInvestorDto)).build());
  }

}
