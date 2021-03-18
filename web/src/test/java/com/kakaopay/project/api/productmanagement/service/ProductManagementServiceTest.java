package com.kakaopay.project.api.productmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.productmanagement.dto.AddProductDto;
import com.kakaopay.project.api.productmanagement.dto.ProductDto;
import com.kakaopay.project.api.productmanagement.dto.ProductSearchDto;
import com.kakaopay.project.common.enumtype.ProductType;
import com.kakaopay.project.web.WebApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ProductManagementServiceTest {

  private final ProductManagementService productManagementService;

  private final long testMemberId = 20_191_218;

  private List<ProductSearchDto> productList;

  @Autowired
  public ProductManagementServiceTest(ProductManagementService productManagementService) {
    this.productManagementService = productManagementService;
  }

  /**
   * 테스트 데이터 상품 입력
   */
  @BeforeAll
  public void insertProductDtoTestData() {
    // List<AddProductDto> testDataList = Arrays.asList(
    // new AddProductDto("TEST_1남양주 부동산", ProductType.REAL_ESTATE, (long) 10_000_000, "2021-04-01", "2021-04-02",
    // testMemberId),
    // new AddProductDto("TEST_개인신용 포트폴리오", ProductType.CREDIT, (long) 999_999, "2020-03-01", "2020-12-31",
    // testMemberId),
    // new AddProductDto("TEST_판교 빌딩", ProductType.REAL_ESTATE, (long) 777_777, "2021-04-01", "2021-05-31",
    // testMemberId),
    // new AddProductDto("TEST_개인신용 주식", ProductType.CREDIT, (long) 808_080, "2021-01-01", "2021-07-31", testMemberId),
    // new AddProductDto("TEST_강남 부동산", ProductType.REAL_ESTATE, (long) 222_222_222, "2021-03-01", "2021-12-31",
    // testMemberId));
    // for (AddProductDto addProductDto : testDataList) {
    // productManagementService.addProduct(addProductDto);
    // }
    // int productCount = productManagementService.selectProductCount();
    // assertThat(productCount).isGreaterThan(5);
    //
    // productList = productManagementService.getMyAddProducts(testMemberId);
  }

  @Test
  public void getMyAddProducts() {
    List<ProductSearchDto> productList = productManagementService.getMyAddProducts(testMemberId);
    for (ProductSearchDto productSearchDto : productList) {
      // 나의 상품이므로 멤버아이디로 비교.
      assertThat(productSearchDto.getMemberId()).isEqualTo(testMemberId);
    }
  }

  @Test
  public void getProductById() {
    for (ProductSearchDto productSearchDto : productList) {
      ProductSearchDto searchDto = productManagementService.getProductById(productSearchDto.getProductId());
      assertThat(productSearchDto.getProductId()).isEqualTo(searchDto.getProductId());
      assertThat(productSearchDto.getTitle()).isEqualTo(searchDto.getTitle());
    }
  }

  @Test
  public void getInvestProducts() {
    List<ProductSearchDto> productSearchDtoList = productManagementService.getInvestProducts();
    for (ProductSearchDto productSearchDto : productSearchDtoList) {
      LocalDateTime productStartDate = LocalDateTime.parse(productSearchDto.getStartedAt().toString(),
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      LocalDateTime productFinishedDate = LocalDateTime.parse(productSearchDto.getFinishedAt().toString(),
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      LocalDateTime nowDate = LocalDateTime.now();
      // 시간 비교.
      assertThat(nowDate).isAfter(productStartDate);
      assertThat(nowDate).isBefore(productFinishedDate);
    }
  }

  @Test
  public void updateProduct() {
    ProductSearchDto testProductSearchDto = null;
    for (ProductSearchDto productSearchDto : productList) {
      LocalDateTime productStartDate = LocalDateTime.parse(productSearchDto.getStartedAt().toString(),
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      LocalDateTime nowDate = LocalDateTime.now();
      if (productStartDate.isAfter(nowDate)) {
        testProductSearchDto = productSearchDto;
        break;
      }
    }
    if (testProductSearchDto != null) {
      ProductDto testProductDto =
          new ProductDto(testProductSearchDto.getTitle() + "TEST", testProductSearchDto.getProductType(),
              testProductSearchDto.getTotalInvestingAmount() + 20_000, testProductSearchDto.getStartedAt(),
              testProductSearchDto.getFinishedAt(), testProductSearchDto.getMemberId());
      testProductDto.setProductId(testProductSearchDto.getProductId());

      productManagementService.updateProduct(testProductDto);

      ProductSearchDto tempProductSearchDto = productManagementService.getProductById(testProductDto.getProductId());
      assertThat(tempProductSearchDto.getTitle()).isEqualTo(testProductDto.getTitle());
    }

  }

  @Test
  public void addProduct() {
    // int productCount = productManagementService.selectProductCount();
    // AddProductDto testProductDto = new AddProductDto("TEST_111111", ProductType.REAL_ESTATE, (long) 9_000_000,
    // "2021-03-01", "2021-12-31", testMemberId);
    // productManagementService.addProduct(testProductDto);
    // assertThat(productManagementService.selectProductCount()).isGreaterThan(productCount);
  }

}