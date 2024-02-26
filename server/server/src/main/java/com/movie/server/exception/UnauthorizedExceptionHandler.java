package com.movie.server.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;

public class UnauthorizedExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        HashMap<String, String> bodyResponse = new HashMap<>();

        bodyResponse.put("httpStatus", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        bodyResponse.put("message", "Not authorized. Must sent a valid token.");

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyResponseFormatted = objectMapper.writeValueAsString(bodyResponse);

        var out = response.getWriter();
        response.setContentType("application/json");
        out.print(bodyResponseFormatted);
        out.flush();
    }
}
