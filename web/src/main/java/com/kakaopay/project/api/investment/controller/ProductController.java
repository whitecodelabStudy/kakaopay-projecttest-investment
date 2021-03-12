package com.kakaopay.project.api.investment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.investment.dto.ProductDto;
import com.kakaopay.project.api.investment.service.ProductService;
import com.kakaopay.project.common.apiformat.ApiResponseJson;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  @ApiOperation(value = "전체 투자 상품 조회 API", notes = "전체 투자 상품 조회 API. 상품모집기간 내의 상품만 응답.")
  public ResponseEntity<ApiResponseJson> getProducts() {
    return ResponseEntity.ok(new ApiResponseJson.Builder(productService.getProductList()).build());
  }

  @GetMapping("/{productId}")
  @ApiOperation(value = "투자 상품 조회 API", notes = "단일 상품 조회.")
  public ResponseEntity<ApiResponseJson> getProduct(@PathVariable long productId) {
    return ResponseEntity.ok(new ApiResponseJson.Builder(productService.getProductById(productId)).build());
  }

  @PostMapping
  @ApiOperation(value = "투자 상품 등록 API", notes = "투자 상품을 등록.")
  public ResponseEntity<ApiResponseJson> addProduct(@RequestBody ProductDto ProductDto) {
    productService.addProduct(ProductDto);
    return ResponseEntity.ok(new ApiResponseJson());
  }

  @PutMapping
  @ApiOperation(value = "투자 상품 수정 API", notes = "투자 상품을 수정. 투자 시작이전 상품만 수정 가능")
  public ResponseEntity<ApiResponseJson> updateProduct(@RequestBody ProductDto ProductDto) {
    productService.updateProduct(ProductDto);
    return ResponseEntity.ok(new ApiResponseJson());
  }

  @DeleteMapping("/{productId}")
  @ApiOperation(value = "투자 상품 삭제 API", notes = "투자 상품을 삭제. 투자 시작이전 상품만 삭제 가능")
  public ResponseEntity<ApiResponseJson> deleteProduct(@PathVariable Long productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.ok(new ApiResponseJson());
  }

}
