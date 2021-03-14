package com.kakaopay.project.common.apiformat;

import java.util.Collections;
import java.util.List;

import com.kakaopay.project.common.code.ApiCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseJson {

  private String resultCode;
  private List<Object> response;

  public ApiResponseJson() {
    // do nothing
  }

  /**
   * ApiResponseJson.
   *
   * @param builder response json builder
   */
  public ApiResponseJson(final Builder builder) {
    this.resultCode = builder.builderResultCode;
    this.response = builder.builderResponse;
  }

  /**
   * Builder
   */
  public static class Builder {
    private List<Object> builderResponse;
    private String builderResultCode;

    /**
     * Builder
     *
     * @param response response builder
     */
    public Builder(final Object response) {
      if (response instanceof List) {
        this.builderResponse = (List<Object>) response;
      } else {
        this.builderResponse = Collections.singletonList(response);
      }
      this.builderResultCode = ApiCode.SUCCESS.getCode();
    }

    public Builder(final Object response, final ApiCode apiCode) {
      if (response instanceof List) {
        this.builderResponse = (List<Object>) response;
      } else {
        this.builderResponse = Collections.singletonList(response);
      }
      this.builderResultCode = apiCode.getCode();
    }


    /**
     * setResponse
     *
     * @param response response
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

    public Builder setApiCode(final ApiCode resultCode) {
      this.builderResultCode = resultCode.getCode();
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
