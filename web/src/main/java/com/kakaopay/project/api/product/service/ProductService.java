package com.kakaopay.project.api.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.product.mapper.ProductMapper;
import com.kakaopay.project.api.product.dto.ProductDto;

@Service
@Transactional
public class ProductService {

  private final transient ProductMapper productMapper;

  @Autowired
  public ProductService(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  public List<ProductDto> getProductList() {
    return productMapper.selectProductList();
  }

  public ProductDto getProductById(long id) {
    return productMapper.selectProductById(id);
  }

  public void updateProduct(ProductDto ProductDto) {
    productMapper.updateProduct(ProductDto);
  }

  public void deleteProduct(long id) {
    productMapper.deleteProduct(id);
  }

  public void addProduct(ProductDto ProductDto) {
    productMapper.insertProduct(ProductDto);
  }

}
