package com.kakaopay.project.api.investment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.productmanagement.service.ProductManagementService;
import com.kakaopay.project.common.apiformat.ApiResponseJson;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/invest/product")
public class InvestProductController {

  private final ProductManagementService productManagementService;

  @Autowired
  public InvestProductController(ProductManagementService productManagementService) {
    this.productManagementService = productManagementService;
  }

  @GetMapping
  @ApiOperation(value = "현재 투자 가능 투자 상품 조회 API", notes = "현재 투자 가능 투자 상품 조회 API.")
  public ResponseEntity<ApiResponseJson> getInvestProducts() {
    return ResponseEntity.ok(new ApiResponseJson.Builder(productManagementService.getInvestProducts()).build());
  }

  @GetMapping("/{productId}")
  @ApiOperation(value = "투자 상품 조회 API", notes = "단일 상품 조회.")
  public ResponseEntity<ApiResponseJson> getInvestProduct(@PathVariable long productId) {
    return ResponseEntity.ok(new ApiResponseJson.Builder(productManagementService.getProductById(productId)).build());
  }

}
