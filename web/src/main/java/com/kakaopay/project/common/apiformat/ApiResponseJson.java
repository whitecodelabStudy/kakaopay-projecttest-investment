package com.kakaopay.project.common.apiformat;

import java.util.Collections;
import java.util.List;

import com.kakaopay.project.common.code.ApiCode;

public class ApiResponseJson {

  private String resultCode = ApiCode.SUCCESS.getCode();
  private List<Object> response;

  public ApiResponseJson() {
    // do nothing
  }

  /**
   * ApiResponseJson.
   *
   * @param builder
   */
  public ApiResponseJson(final Builder builder) {
    this.response = builder.builderResponse;
  }

  /**
   * setResultCode
   * 
   * @return
   */
  public String getResultCode() {
    return resultCode;
  }

  /**
   * setResultCode
   * 
   * @param resultCode
   */
  public void setResultCode(final String resultCode) {
    this.resultCode = resultCode;
  }

  /**
   * getResponse
   * 
   * @return
   */
  public List<Object> getResponse() {
    return response;
  }

  /**
   * setResponse
   *
   * @param response
   */
  public void setResponse(final List<Object> response) {
    this.response = response;
  }

  /**
   * Builder
   */
  public static class Builder {
    private transient List<Object> builderResponse;

    /**
     * Builder
     *
     * @param response
     */
    public Builder(final Object response) {
      if (response instanceof List) {
        this.builderResponse = (List<Object>) response;
      } else {
        this.builderResponse = Collections.singletonList(response);
      }
    }

    /**
     * setResponse
     *
     * @param response
     * @return Builder
     */
    public Builder setResponse(final Object response) {
      if (response instanceof List) {
        this.builderResponse = (List<Object>) response;
      } else {
        this.builderResponse = Collections.singletonList(response);
      }
      return this;
    }

    /**
     * build
     *
     * @return ApiResponseJson
     */
    public ApiResponseJson build() {
      return new ApiResponseJson(this);
    }
  }

}
