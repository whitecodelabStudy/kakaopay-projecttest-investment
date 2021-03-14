package com.kakaopay.project.common.exception;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kakaopay.project.common.apiformat.ApiResponseJson;

@RestControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponseJson> handleCustomException(ApiException ex) {
    return ResponseEntity.status(ex.getHttpStatus())
        .body(new ApiResponseJson.Builder(Collections.emptyList(), ex.getApiCode()).build());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiResponseJson> handleAccessDeniedException() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(new ApiResponseJson.Builder(Collections.emptyList()).build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseJson> handleException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ApiResponseJson.Builder(Collections.emptyList()).build());
  }

}
