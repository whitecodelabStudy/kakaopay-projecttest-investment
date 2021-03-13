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
    // INVESTOR_NOT_FOUND
    INVESTOR_NOT_FOUND("ERR-0002"),
    // BAD_CREDENTIALS
    BAD_CREDENTIALS("ERR-0003");

  private String errorCode;

  ApiCode(final String errorCode) {
    this.errorCode = errorCode;
  }

  public String getCode() {
    return errorCode;
  }

  @Override
  public String toString() {
    return "[" + errorCode + "]";
  }

}