package com.kakaopay.project.api.invest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.kakaopay.project.web.WebApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.project.api.enumtype.ProductType;
import com.kakaopay.project.api.invest.dto.ProductDto;
import com.kakaopay.project.api.invest.dto.ProductSearchDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class ProductServiceTest {

  @Autowired
  private ProductService productService;
  private int lastProductId = 0;

  /**
   * 테스트 데이터 상품 입력
   */
  @Before
  public void insertProductDtoTestData() {
    List<ProductDto> testDataList = Arrays.asList(
        new ProductDto("남양주 부동산", ProductType.REAL_ESTATE, (long) 10000000, "2021-03-01", "2021-12-31", (long) 1),
        new ProductDto("개인신용 포트폴리오", ProductType.CREDIT, (long) 999999, "2020-03-01", "2020-12-31", (long) 1),
        new ProductDto("판교 빌딩", ProductType.REAL_ESTATE, (long) 777777, "2021-04-01", "2021-05-31", (long) 1),
        new ProductDto("개인신용 주식", ProductType.CREDIT, (long) 808080, "2021-01-01", "2021-07-31", (long) 1),
        new ProductDto("강남 부동산", ProductType.REAL_ESTATE, (long) 222222222, "2021-03-01", "2021-12-31", (long) 1));
    for (ProductDto productDto : testDataList) {
      productService.addProduct(productDto);
    }
    int productCount = productService.selectProductCount();
    lastProductId = productCount;
    assertThat(productCount).isGreaterThan(5);
  }

  /**
   * 개별 상품 조회 여부 확인.
   */
  @Test
  public void testSelectProductById() {
    ProductSearchDto productSearchDto = productService.getProductById(lastProductId);
    assertThat(productSearchDto.getProductId()).isEqualTo(lastProductId);
    assertThat(productSearchDto.getTotalInvestingAmount()).isEqualTo(222222222);
  }

  /**
   * 상품 리스트 확인.
   */
  @Test
  public void testSelectProductList() {
    List<ProductSearchDto> productList = productService.getProductList();
    assertThat(productList.size()).isGreaterThan(5);
  }

}