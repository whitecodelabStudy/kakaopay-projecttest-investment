package com.kakaopay.project.api.util;

import java.io.IOException;
import java.util.List;

import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.kakaopay.project.common.apiformat.ApiResponseJson;

public final class TestUtil {

  private TestUtil() {
    // do nothing
  }

  public static ApiResponseJson getApiResponseJson(MvcResult mvcResult) throws IOException {
    return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
  }

  public static <T> T getResponseObject(MvcResult mvcResult, TypeReference<T> clazz) throws IOException {
    return getResponseObject(getApiResponseJson(mvcResult), clazz);
  }

  /**
   * getResponseObject
   * 
   * @param apiResponseJson
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T> T getResponseObject(ApiResponseJson apiResponseJson, TypeReference<T> clazz) {
    if (apiResponseJson == null) {
      return null;
    }
    if (clazz instanceof List) {
      return (T) apiResponseJson.getResponse();
    } else {
      return (T) apiResponseJson.getResponse().get(0);
    }
  }

}
