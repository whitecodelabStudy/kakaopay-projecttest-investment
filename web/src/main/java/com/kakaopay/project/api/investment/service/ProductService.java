package com.kakaopay.project.api.investment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.investment.dto.ProductDto;
import com.kakaopay.project.api.investment.dto.ProductSearchDto;
import com.kakaopay.project.api.investment.mapper.ProductMapper;

@Service
public class ProductService {

  private final ProductMapper productMapper;

  @Autowired
  public ProductService(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  @Transactional
  public List<ProductSearchDto> getProductList() {
    return productMapper.selectProductList();
  }

  @Transactional
  public ProductSearchDto getProductById(long id) {
    return productMapper.selectProductById(id);
  }

  @Transactional
  public void updateProduct(ProductDto ProductDto) {
    productMapper.updateProduct(ProductDto);
  }

  /**
   * 투자상품 삭제
   * 투자시작일 이전 상품만 삭제 가능.
   *
   * @param productId 상품아이디.
   * @return 상품삭제 결과
   */
  @Transactional
  public int deleteProduct(long productId) {
    return productMapper.deleteProduct(productId);
  }

  @Transactional
  public void addProduct(ProductDto ProductDto) {
    productMapper.insertProduct(ProductDto);
  }

  @Transactional
  public int selectProductCount() {
    return productMapper.selectProductCount();
  }

}
