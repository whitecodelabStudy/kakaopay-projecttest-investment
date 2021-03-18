package com.kakaopay.project.common.exception;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.kakaopay.project.common.code.ApiCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponseJson> handlerCustomException(ApiException ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(ex.getHttpStatus())
        .body(new ApiResponseJson.Builder(ex.getMessage(), ex.getApiCode()).build());
  }

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<ApiResponseJson> handlerSqlException(SQLException sqlException) {
    log.error(sqlException.getMessage(), sqlException);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ApiResponseJson.Builder(sqlException.getMessage(), ApiCode.SQL_ERROR).build());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiResponseJson> handlerAccessDeniedException(AccessDeniedException accessDeniedException) {
    log.error(accessDeniedException.getMessage(), accessDeniedException);
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(new ApiResponseJson.Builder(accessDeniedException.getMessage(), ApiCode.ACCESS_DENIED_ERROR).build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseJson> handlerException(Exception exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ApiResponseJson.Builder(exception.getMessage(), ApiCode.UNKNOWN_ERROR).build());
  }

}
