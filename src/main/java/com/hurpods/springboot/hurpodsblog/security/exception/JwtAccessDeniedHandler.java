package com.hurpods.springboot.hurpodsblog.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//403
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        PrintWriter printWriter = httpServletResponse.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        printWriter.write(mapper.writeValueAsString(ResultFactory.buildCustomFailureResult(ResultCode.PERMISSION_NO_ACCESS,"authFail")));
        printWriter.flush();
        printWriter.close();
    }
}
