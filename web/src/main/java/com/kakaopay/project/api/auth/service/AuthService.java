package com.kakaopay.project.api.auth.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;

@Service
public class AuthService {

  private String secretKey = "ThisisHyoJunSecretKeyWelcomeMyFirstJwt";

  private static final Log LOG = LogFactory.getLog(AuthService.class);

  public String makeJwt(String id, String password, String email) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    Date expireTime = new Date();
    expireTime.setTime(expireTime.getTime() + 1000 * 60 * 1);
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    Map<String, Object> headerMap = new HashMap<>();

    headerMap.put("typ", "JWT");
    headerMap.put("alg", "HS256");

    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("email", email);

    JwtBuilder builder = Jwts.builder().setHeader(headerMap).setClaims(map).setExpiration(expireTime)
        .signWith(signatureAlgorithm, signingKey);

    return builder.compact();
  }

  public boolean checkJwt(String token) {
    try {
      // 정상수행 된다면 해당 토큰은 정상토큰
      Claims claims =
          Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(token).getBody();
      LOG.info("### id :" + claims.get("id"));
      LOG.info("### Email :" + claims.get("email"));
      LOG.info("### expireTime :" + claims.getExpiration());
      return true;
    } catch (ExpiredJwtException exception) {
      LOG.info("### Token expired");
      return false;
    } catch (JwtException exception) {
      LOG.info("### Token check ERROR!!");
      return false;
    }
  }

}
