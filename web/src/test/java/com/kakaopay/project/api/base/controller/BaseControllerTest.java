package com.kakaopay.project.api.base.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.project.api.auth.dto.AuthMemberDto;
import com.kakaopay.project.api.member.dto.AddMemberDto;
import com.kakaopay.project.api.productmanagement.dto.AddProductDto;
import com.kakaopay.project.api.util.TestUtil;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;
import com.kakaopay.project.common.enumtype.ProductType;
import com.kakaopay.project.web.WebApplication;

@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WebApplication.class)
public class BaseControllerTest {

  private static final String AUTH_HEADER = "Authorization";
  private static final String TOKEN_TYPE = "Bearer ";

  private HttpHeaders headers;

  private boolean isFirst = true;

  private long memberId = 200;
  private long memberFailId = 222;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  protected void beforeAll() throws Exception {
    if (isFirst) {
      // 테스트 데이터 생성
      // admin 데이터 생성
      addMember(100, "1q2w3e4r", "상품관리자", "ADMIN");
      addMember(200, "!@1q2w3e4r", "상품등록자", "ADMIN");
      // investor 데이터 생성
      addMember(300, "#1q2w3e4r", "섭이", "INVESTOR");
      addMember(400, "$1q2w3e4r", "베니", "INVESTOR");
      addMember(500, "%1q2w3e4r", "호비", "INVESTOR");
      addMember(600, "1q2w3e4r", "승후", "INVESTOR");
      // 상품등록자 로그인 (token 발급)
      generateToken(200, "!@1q2w3e4r", "ADMIN");
      // 상품 데이터 생성
      addProduct("남양주 부동산", ProductType.REAL_ESTATE, 10_000_000, "2021-04-01", "2021-04-02", 200);
      addProduct("개인신용 포트폴리오", ProductType.CREDIT, 999_999, "2020-03-01", "2020-12-31", 200);
      addProduct("판교 빌딩", ProductType.REAL_ESTATE, 777_777, "2021-04-01", "2021-05-31", 200);
      addProduct("개인신용 주식", ProductType.CREDIT, 808_080, "2021-01-01", "2021-07-31", 200);
      addProduct("강남 부동산", ProductType.REAL_ESTATE, 222_222_222, "2021-03-01", "2021-12-31", 200);
    }
    isFirst = false;
  }

  /**
   * 사용자 등록
   *
   * @param memberId
   * @param password
   * @param name
   * @param memberType
   * @throws Exception
   */
  @Transactional
  public void addMember(long memberId, String password, String name, String memberType) throws Exception {
    // 사용자 추가.
    AddMemberDto addMemberDto = new AddMemberDto();
    addMemberDto.setMemberId(memberId);
    addMemberDto.setPassword(password);
    addMemberDto.setName(name);
    addMemberDto.setMemberType(memberType);
    // 확인
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/api/member/signup").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper()
                .writeValueAsString(addMemberDto)))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertThat(apiResponseJson.getResultCode()).isEqualTo(ApiCode.SUCCESS.getCode());
  }

  /**
   * 투자상품 등록
   *
   * @param title
   * @param productType
   * @param totalInvestingAmount
   * @param startedAt
   * @param finishedAt
   * @param memberId
   * @throws Exception
   */
  @Transactional
  public void addProduct(String title, ProductType productType, long totalInvestingAmount, String startedAt,
      String finishedAt, long memberId) throws Exception {
    // 투자 상품 추가.
    AddProductDto addProductDto = new AddProductDto();
    addProductDto.setTitle(title);
    addProductDto.setProductType(productType);
    addProductDto.setTotalInvestingAmount(totalInvestingAmount);
    addProductDto.setStartedAt(startedAt);
    addProductDto.setFinishedAt(finishedAt);
    addProductDto.setMemberId(memberId);
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/api/product").accept(MediaType.APPLICATION_JSON).headers(getHeaders())
            .contentType(MediaType.APPLICATION_JSON)
            .content(new org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper()
                .writeValueAsString(addProductDto)))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertThat(apiResponseJson.getResultCode()).isEqualTo(ApiCode.SUCCESS.getCode());
  }

  /**
   * 토큰 발급 테스트 정상처리
   */
  protected void generateToken(long memberId, String password, String memberType) throws Exception {
    AuthMemberDto authMemberDto = new AuthMemberDto();
    authMemberDto.setMemberId(memberId);
    authMemberDto.setPassword(password);
    authMemberDto.setMemberType(memberType);
    makeHeader(authMemberDto);
  }

  protected void makeHeader(AuthMemberDto authMemberDto) throws Exception {
    // member id header 세팅.
    this.headers = setMemberIdHeader(String.valueOf(authMemberDto.getMemberId()));
    // token 발급.
    String accessToken = issueAccessToken(authMemberDto);
    // 발급받은 token 헤더에 세팅.
    this.headers.set(AUTH_HEADER, TOKEN_TYPE + accessToken);
  }

  protected String issueAccessToken(AuthMemberDto authMemberDto) throws Exception {
    if (this.headers == null) {
      this.headers = setMemberIdHeader(String.valueOf(authMemberDto.getMemberId()));
    }
    // token 발급
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/authenticate").accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(authMemberDto)).headers(this.headers)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    ApiResponseJson apiResponseJson = TestUtil.getApiResponseJson(mvcResult);
    assertThat(ApiCode.SUCCESS.getCode()).isEqualTo(apiResponseJson.getResultCode());
    return (String) ((LinkedHashMap) apiResponseJson.getResponse().get(0)).get("accessToken");
  }

  protected HttpHeaders setMemberIdHeader(String memberId) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("X-USER-ID", memberId);
    return headers;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public void setHeaders(HttpHeaders headers) {
    this.headers = headers;
  }

  public long getMemberId() {
    return memberId;
  }

  public void setMemberId(long memberId) {
    this.memberId = memberId;
  }

  public long getMemberFailId() {
    return memberFailId;
  }

  public void setMemberFailId(long memberFailId) {
    this.memberFailId = memberFailId;
  }

  public MockMvc getMockMvc() {
    return mockMvc;
  }

  public void setMockMvc(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

}
