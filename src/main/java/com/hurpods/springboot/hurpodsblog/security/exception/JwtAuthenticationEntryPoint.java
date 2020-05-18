package com.hurpods.springboot.hurpodsblog.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//401
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        PrintWriter printWriter=httpServletResponse.getWriter();
        ObjectMapper mapper=new ObjectMapper();
        printWriter.write(mapper.writeValueAsString(ResultFactory.buildFailureResult("登陆超时,请重新登陆")));
        printWriter.flush();
        printWriter.close();
    }
}
