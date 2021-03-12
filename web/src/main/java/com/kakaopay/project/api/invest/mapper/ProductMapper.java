package com.kakaopay.project.api.invest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.kakaopay.project.api.invest.dto.ProductDto;
import com.kakaopay.project.api.invest.dto.ProductSearchDto;

@Mapper
@Component
public interface ProductMapper {

  List<ProductSearchDto> selectProductList();

  ProductSearchDto selectProductById(long productId);

  int deleteProduct(long productId);

  int updateProduct(ProductDto ProductDto);

  int insertProduct(ProductDto ProductDto);

  int selectProductCount();

}
