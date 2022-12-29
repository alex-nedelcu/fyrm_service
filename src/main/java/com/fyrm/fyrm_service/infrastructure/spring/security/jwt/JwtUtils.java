package com.fyrm.fyrm_service.infrastructure.spring.security.jwt;

import com.fyrm.fyrm_service.infrastructure.spring.security.service.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${fyrm.service.jwt-secret}")
  private String jwtSecret;

  @Value("${fyrm.service.jwt-expiration-ms}")
  private long jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject((principal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(Date.from(ZonedDateTime.now().toInstant().plusMillis(jwtExpirationMs)))
        .signWith(toKey(jwtSecret))
        .compact();
  }

  public String getUsernameFromJwtToken(String jwtToken) {
    return Jwts.parserBuilder()
        .setSigningKey(toKey(jwtSecret))
        .build()
        .parseClaimsJwt(jwtToken)
        .getBody()
        .getSubject();
  }

  public boolean validateJwtToken(String jwtToken) {
    try {
      Jwts.parserBuilder().setSigningKey(toKey(jwtSecret)).build().parseClaimsJws(jwtToken);
      return true;
    } catch (MalformedJwtException error) {
      LOGGER.error("Invalid JWT token: {}", error.getMessage());
    } catch (ExpiredJwtException error) {
      LOGGER.error("JWT token is expired: {}", error.getMessage());
    } catch (UnsupportedJwtException error) {
      LOGGER.error("JWT token is unsupported: {}", error.getMessage());
    } catch (IllegalArgumentException error) {
      LOGGER.error("JWT claims string is empty: {}", error.getMessage());
    }

    return false;
  }

  private Key toKey(String secret) {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }
}
