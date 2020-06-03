package com.hurpods.springboot.hurpodsblog.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hurpods.springboot.hurpodsblog.dto.LoginRequest;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.security.constans.SecurityConstants;
import com.hurpods.springboot.hurpodsblog.security.entity.JwtUser;
import com.hurpods.springboot.hurpodsblog.security.utils.JwtTokenUtil;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

//如果用户名和密码正确，那么过滤器将创建一个JWT Token 并在HTTP Response 的header中返回它，格式：token: "Bearer +具体token值"
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            rememberMe.set(loginRequest.getRememberMe());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(objectMapper.writeValueAsString(ResultFactory.buildFailureResult("用户名或密码错误")));
            printWriter.flush();
            printWriter.close();
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        PrintWriter printWriter = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        //如果该账号没有被封禁则返回token及账号信息
        if (jwtUser.getEnabled()) {
            List<String> authorities = jwtUser.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            String token = JwtTokenUtil.createToken(jwtUser.getUsername(), authorities, rememberMe.get());
            rememberMe.remove();
            //消除密码
            jwtUser.setPassword("");
            response.setHeader(SecurityConstants.TOKEN_HEADER, token);
            response.setHeader("Content-Type", "application/json;charset=utf8");
            //写入用户基本信息
            printWriter.write(mapper.writeValueAsString(ResultFactory.buildSuccessResult(jwtUser)));
        } else {
            printWriter.write(mapper.writeValueAsString(ResultFactory.buildFailureResult("该账号已经被封停，禁止使用")));
        }

        printWriter.flush();
        printWriter.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(mapper.writeValueAsString(ResultFactory.buildFailureResult("账号或密码错误")));
    }
}
