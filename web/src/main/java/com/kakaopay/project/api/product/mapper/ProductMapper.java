package com.kakaopay.project.api.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kakaopay.project.api.product.dto.ProductDto;

@Mapper
public interface ProductMapper {

  List<ProductDto> selectProductList();

  ProductDto selectProductById(long productId);

  int deleteProduct(long productId);

  int updateProduct(ProductDto ProductDto);

  int insertProduct(ProductDto ProductDto);

}
