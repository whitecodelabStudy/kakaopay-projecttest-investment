package com.kakaopay.project.api.productmanagement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.kakaopay.project.api.productmanagement.dto.AddProductDto;
import com.kakaopay.project.api.productmanagement.dto.ProductDto;
import com.kakaopay.project.api.productmanagement.dto.ProductSearchDto;

@Mapper
@Component
public interface ProductManagementMapper {

  List<ProductSearchDto> selectMyProducts(long memberId);

  ProductSearchDto selectProductById(long productId);

  List<ProductSearchDto> selectInvestProducts();

  int deleteProduct(long productId);

  int updateProduct(ProductDto productDto);

  int insertProduct(AddProductDto addProductDto);

  int selectProductCount();

}
