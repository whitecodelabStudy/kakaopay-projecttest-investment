package com.kakaopay.project.api.productmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.productmanagement.dto.ProductDto;
import com.kakaopay.project.api.productmanagement.dto.ProductSearchDto;
import com.kakaopay.project.api.productmanagement.mapper.ProductManagementMapper;

@Service
public class ProductManagementService {

  private final ProductManagementMapper productManagementMapper;

  @Autowired
  public ProductManagementService(ProductManagementMapper productManagementMapper) {
    this.productManagementMapper = productManagementMapper;
  }

  @Transactional
  public List<ProductSearchDto> getMyAddProducts(long memberId) {
    return productManagementMapper.selectMyProducts(memberId);
  }

  @Transactional
  public ProductSearchDto getProductById(long id) {
    return productManagementMapper.selectProductById(id);
  }

  @Transactional
  public List<ProductSearchDto> getInvestProducts() {
    return productManagementMapper.selectInvestProducts();
  }

  @Transactional
  public void updateProduct(ProductDto ProductDto) {
    productManagementMapper.updateProduct(ProductDto);
  }

  @Transactional
  public int deleteProduct(long productId) {
    return productManagementMapper.deleteProduct(productId);
  }

  @Transactional
  public void addProduct(ProductDto ProductDto) {
    productManagementMapper.insertProduct(ProductDto);
  }

  @Transactional
  public int selectProductCount() {
    return productManagementMapper.selectProductCount();
  }

}
