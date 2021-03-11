package com.kakaopay.project.api.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.product.dto.ProductDto;
import com.kakaopay.project.api.product.service.ProductService;
import com.kakaopay.project.common.apiformat.ApiResponseJson;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  private final transient ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public @ResponseBody ApiResponseJson getProducts() {
    return new ApiResponseJson.Builder(productService.getProductList()).build();
  }

  @GetMapping("/{productId}")
  public @ResponseBody ApiResponseJson getProduct(@PathVariable long productId) {
    return new ApiResponseJson.Builder(productService.getProductById(productId)).build();
  }

  @PostMapping
  public @ResponseBody ApiResponseJson addProduct(@RequestBody ProductDto ProductDto) {
    productService.addProduct(ProductDto);
    return new ApiResponseJson();
  }

  @PutMapping
  public @ResponseBody ApiResponseJson updateProduct(@RequestBody ProductDto ProductDto) {
    productService.updateProduct(ProductDto);
    return new ApiResponseJson();
  }

  @DeleteMapping("/{productId}")
  public @ResponseBody ApiResponseJson deleteProduct(@RequestBody long productId) {
    productService.deleteProduct(productId);
    return new ApiResponseJson();
  }

}
