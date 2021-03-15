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

  /**
   * 내가 등록한 상품 조회.
   *
   * @param memberId memberId
   * @return 투자상품리스트.
   */
  @Transactional
  public List<ProductSearchDto> getMyAddProducts(long memberId) {
    return productManagementMapper.selectMyProducts(memberId);
  }

  /**
   * 투자 상품 상세 조회
   * 
   * @param productId 투자상품아이디
   * @return 투자상품상세정보
   */
  @Transactional
  public ProductSearchDto getProductById(long productId) {
    return productManagementMapper.selectProductById(productId);
  }

  /**
   * 투자가능 상품 조회
   * 
   * @return 투자상품리스트
   */
  @Transactional
  public List<ProductSearchDto> getInvestProducts() {
    return productManagementMapper.selectInvestProducts();
  }

  /**
   * 투자 상품 수정
   *
   * @param productDto productDto
   */
  @Transactional
  public void updateProduct(ProductDto productDto) {
    try {
      int result = productManagementMapper.updateProduct(productDto);
      if (result == 0) {
        throw new ApiException(ApiCode.PRODUCT_MODIFIED_FAILED, "Product modified failed!!");
      }
    } catch (Exception e) {
      throw new ApiException(ApiCode.PRODUCT_MODIFIED_FAILED, "Product modified failed!!", e);
    }
  }

  /**
   * 투자 상품 삭제
   *
   * @param productId productId
   */
  @Transactional
  public void deleteProduct(long productId) {
    try {
      int result = productManagementMapper.deleteProduct(productId);
      if (result == 0) {
        throw new ApiException(ApiCode.PRODUCT_DELETE_FAILED, "Product delete failed!!");
      }
    } catch (Exception e) {
      throw new ApiException(ApiCode.PRODUCT_DELETE_FAILED, "Product delete failed!!", e);
    }
  }

  /**
   * 투자 상품 추가.
   *
   * @param productDto productDto
   */
  @Transactional
  public void addProduct(ProductDto productDto) {
    try {
      int result = productManagementMapper.insertProduct(productDto);
      if (result == 0) {
        throw new ApiException(ApiCode.PRODUCT_ADD_FAILED, "Product modified failed!!");
      }
    } catch (Exception e) {
      throw new ApiException(ApiCode.PRODUCT_ADD_FAILED, "Product add failed!!", e);
    }

  }

  /**
   * 투자 상품 갯수 조회
   *
   * @return count
   */
  @Transactional
  public int selectProductCount() {
    return productManagementMapper.selectProductCount();
  }

}
