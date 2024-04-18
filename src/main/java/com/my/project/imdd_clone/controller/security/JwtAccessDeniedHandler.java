package com.my.project.imdd_clone.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.project.imdd_clone.controller.handler.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response
        .getWriter()
        .write(
            objectMapper.writeValueAsString(new ErrorMessage(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage(), null)));
  }
}
