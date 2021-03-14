package com.kakaopay.project.common.exception;


import org.springframework.http.HttpStatus;

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
  private final String message;
  private HttpStatus httpStatus = HttpStatus.OK;

  public ApiException(final ApiCode apiCode, final String message, final Throwable cause) {
    super(message, cause);
    this.apiCode = apiCode;
    this.message = message;
  }

  public ApiException(final ApiCode apiCode, final String message) {
    super(message);
    this.apiCode = apiCode;
    this.message = message;
  }

  public ApiCode getApiCode() {
    return apiCode;
  }

  public ApiException setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
    return this;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}
