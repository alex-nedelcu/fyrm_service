package com.fyrm.fyrm_service.infrastructure.spring.security.jwt;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointJwt implements AuthenticationEntryPoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationEntryPointJwt.class);

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException exception
  ) throws IOException {
    LOGGER.error("Unauthorized error: {}", exception.getMessage());

    response.setContentType(APPLICATION_JSON_VALUE);
    response.setStatus(SC_UNAUTHORIZED);

    Map<String, Object> body = getUnauthorizedExceptionBody(request, exception);
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), body);
  }

  private Map<String, Object> getUnauthorizedExceptionBody(HttpServletRequest request, AuthenticationException exception) {
    Map<String, Object> body = new HashMap<>();

    body.put("status", SC_UNAUTHORIZED);
    body.put("error", "Unauthorized");
    body.put("message", exception.getMessage());
    body.put("path", request.getServletPath());

    return body;
  }
}
