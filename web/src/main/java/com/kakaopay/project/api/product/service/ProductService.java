package com.kakaopay.project.api.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.product.mapper.ProductMapper;
import com.kakaopay.project.api.product.model.ProductModel;

@Service
@Transactional
public class ProductService {

  private final transient ProductMapper productMapper;

  @Autowired
  public ProductService(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  public List<ProductModel> getProductList() {
    return productMapper.selectProductList();
  }

  public ProductModel getProductById(long id) {
    return productMapper.selectProductById(id);
  }

  public void updateProduct(ProductModel productModel) {
    productMapper.updateProduct(productModel);
  }

  public void deleteProduct(long id) {
    productMapper.deleteProduct(id);
  }

  public void addProduct(ProductModel productModel) {
    productMapper.insertProduct(productModel);
  }

}
