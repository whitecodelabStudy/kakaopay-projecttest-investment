package com.kakaopay.project.api.exception;


import com.kakaopay.project.common.code.ApiCode;

/**
 * ApiException.java
 *
 * @version : 1.0
 * @author : lovelySub
 * @date : 2021-03-11
 * @Comment : API 공통 Exception 클래스.
 */
public class ApiException extends RuntimeException {

  private static final long serialVersionUID = -2435810007394969909L;

  private final ApiCode apiCode;

  public ApiException() {
    super(ApiCode.UNKNOWN_ERROR.name());
    this.apiCode = ApiCode.UNKNOWN_ERROR;
  }

  public ApiException(final ApiCode apiCode) {
    super(apiCode.name());
    this.apiCode = apiCode;
  }

  public ApiException(final ApiCode apiCode, final String message, final Throwable cause) {
    super(message, cause);
    this.apiCode = apiCode;
  }

  public ApiException(final ApiCode apiCode, final String message) {
    super(message);
    this.apiCode = apiCode;
  }

  public ApiException(final ApiCode apiCode, final Throwable cause) {
    super(cause);
    this.apiCode = apiCode;
  }

  public ApiCode getApiCode() {
    return apiCode;
  }

}
