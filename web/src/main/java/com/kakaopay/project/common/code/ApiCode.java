package com.kakaopay.project.common.code;

/**
 * ApiCode.java
 *
 * @version : 1.0
 * @author : lovelySub
 * @Comment : Log 코드 enum 클래스
 */
public enum ApiCode {
    // 공통 응답 코드 - 성공
    SUCCESS("SUC-0000"),
    // 알수없는 오류
    UNKNOWN_ERROR("ERR-9999"),
    // SOLD_OUT
    SOLD_OUT("ERR-0001"),
    // MEMBER_NOT_FOUND
    MEMBER_NOT_FOUND("ERR-0002"),
    // BAD_CREDENTIALS
    BAD_CREDENTIALS("ERR-0003"),
    // DUPLICATE_INVEST
    DUPLICATE_INVEST("ERR-0004"),
    // MEMBER_SIGN_UP_FAIL
    MEMBER_SIGN_UP_FAILED("ERR-0005"),
    // MEMBER_MODIFIED_FAILED
    MEMBER_MODIFIED_FAILED("ERR-0006"),
    // MEMBER_MODIFIED_FAILED
    PRODUCT_MODIFIED_FAILED("ERR-0007"),
    // MEMBER_MODIFIED_FAILED
    PRODUCT_DELETE_FAILED("ERR-0008"),
    // MEMBER_MODIFIED_FAILED
    PRODUCT_ADD_FAILED("ERR-0009");

  private String errorCode;
  private String message;

  ApiCode(final String errorCode) {
    this.errorCode = errorCode;
    this.message = "";
  }

  ApiCode(final String errorCode, final String message) {
    this.errorCode = errorCode;
    this.message = message;
  }

  public String getCode() {
    return errorCode;
  }

  @Override
  public String toString() {
    return "[" + errorCode + "]";
  }

}