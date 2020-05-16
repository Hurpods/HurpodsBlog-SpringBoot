package com.hurpods.springboot.hurpodsblog.security.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//403
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e = new AccessDeniedException("Sorry you don not enough permissions to access it!");
        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    }
}
