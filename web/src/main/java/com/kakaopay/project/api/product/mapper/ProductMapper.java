package com.kakaopay.project.api.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kakaopay.project.api.product.model.ProductModel;

@Mapper
public interface ProductMapper {

  List<ProductModel> selectProductList();

  ProductModel selectProductById(long productId);

  int  deleteProduct(long productId);

  int  updateProduct(ProductModel productModel);

  int  insertProduct(ProductModel productModel);

}
