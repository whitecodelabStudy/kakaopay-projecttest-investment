package com.kakaopay.project.api.util;

import java.io.IOException;

import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.kakaopay.project.common.apiformat.ApiResponseJson;

public class TestUtil {

  public static ApiResponseJson getApiResponseJson(MvcResult mvcResult) throws IOException {
    return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
  }

}
