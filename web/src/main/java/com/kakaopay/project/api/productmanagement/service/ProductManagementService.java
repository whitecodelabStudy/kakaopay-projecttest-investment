package com.kakaopay.project.api.productmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.productmanagement.dto.ProductDto;
import com.kakaopay.project.api.productmanagement.dto.ProductSearchDto;
import com.kakaopay.project.api.productmanagement.mapper.ProductManagementMapper;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.common.exception.ApiException;

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
    try {
      int result = productManagementMapper.updateProduct(ProductDto);
      if (result == 0) {
        throw new ApiException(ApiCode.PRODUCT_MODIFIED_FAILED, "Product modified failed!!");
      }
    } catch (Exception e) {
      throw new ApiException(ApiCode.PRODUCT_MODIFIED_FAILED, "Product modified failed!!");
    }
  }

  @Transactional
  public void deleteProduct(long productId) {
    try {
      int result = productManagementMapper.deleteProduct(productId);
      if (result == 0) {
        throw new ApiException(ApiCode.PRODUCT_DELETE_FAILED, "Product delete failed!!");
      }
    } catch (Exception e) {
      throw new ApiException(ApiCode.PRODUCT_DELETE_FAILED, "Product delete failed!!");
    }
  }

  @Transactional
  public void addProduct(ProductDto ProductDto) {
    try {
      int result = productManagementMapper.insertProduct(ProductDto);
      if (result == 0) {
        throw new ApiException(ApiCode.PRODUCT_ADD_FAILED, "Product modified failed!!");
      }
    } catch (Exception e) {
      throw new ApiException(ApiCode.PRODUCT_ADD_FAILED, "Product add failed!!");
    }

  }

  @Transactional
  public int selectProductCount() {
    return productManagementMapper.selectProductCount();
  }

}
