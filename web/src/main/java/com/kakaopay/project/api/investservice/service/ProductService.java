package com.kakaopay.project.api.investservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.investservice.dto.ProductDto;
import com.kakaopay.project.api.investservice.dto.ProductSearchDto;
import com.kakaopay.project.api.investservice.mapper.ProductMapper;

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

  @Transactional
  public void deleteProduct(long id) {
    productMapper.deleteProduct(id);
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
