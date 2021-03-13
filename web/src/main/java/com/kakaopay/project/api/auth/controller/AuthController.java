package com.kakaopay.project.api.auth.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.auth.dto.AuthDto;
import com.kakaopay.project.api.auth.service.AuthService;
import com.kakaopay.project.common.apiformat.ApiResponseJson;
import com.wepicksoft.webads.common.apiformat.ApiRequestJson;
import com.wepicksoft.webads.core.util.StringUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  // RESPONSE KEY
  private static final String RESPONSE_MAP_KEY_DATA = "data";
  private static final String RESPONSE_MAP_KEY_ID = "id";
  private static final String RESPONSE_MAP_KEY_PASSWORD = "password";
  private static final String RESPONSE_MAP_KEY_EMAIL = "email";

  @Autowired
  private AuthService authService;

  /**
   * authenticate
   *
   * @param authDto
   * @return ResponseEntity
   */
  @PostMapping("/authenticate")
  public ResponseEntity<ApiResponseJson> authenticate(@RequestBody AuthDto authDto) {
    return Collections.singletonList(Collections.singletonMap(RESPONSE_MAP_KEY_DATA,
        authService.makeJwt(apiRequestJson.getRequest().get(RESPONSE_MAP_KEY_ID).toString(),
            apiRequestJson.getRequest().get(RESPONSE_MAP_KEY_PASSWORD).toString(),
            apiRequestJson.getRequest().get(RESPONSE_MAP_KEY_EMAIL).toString())));
  }

  @PostMapping("/checkToken")
  public ResponseEntity<ApiResponseJson> authToken(@RequestBody ApiRequestJson apiRequestJson) throws Exception {
    String token = apiRequestJson.getAuth().getToken();
    if (StringUtil.isEmpty(token)) {
      return false;
    } else {
      return authService.checkJwt(token);
    }
  }

}
