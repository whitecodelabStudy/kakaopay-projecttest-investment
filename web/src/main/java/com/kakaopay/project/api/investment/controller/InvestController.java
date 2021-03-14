package com.kakaopay.project.api.investment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.investment.dto.InvestProductDto;
import com.kakaopay.project.api.investment.service.InvestService;
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
  public ResponseEntity<ApiResponseJson> investProduct(@RequestBody InvestProductDto investProductDto,
      @RequestHeader(value = "X-USER-ID") long memberId) {
    investProductDto.setMemberId(memberId);
    return ResponseEntity.ok(new ApiResponseJson.Builder(investService.insertProductInvest(investProductDto)).build());
  }

  @DeleteMapping("{investId}")
  public ResponseEntity<ApiResponseJson> deleteProductInvest(@PathVariable Long investId) {
    return ResponseEntity.ok(new ApiResponseJson.Builder(investService.deleteProductInvest(investId)).build());
  }


}
