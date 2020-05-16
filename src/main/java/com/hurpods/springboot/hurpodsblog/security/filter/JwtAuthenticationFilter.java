package com.hurpods.springboot.hurpodsblog.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hurpods.springboot.hurpodsblog.dto.LoginRequest;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.security.constans.SecurityConstants;
import com.hurpods.springboot.hurpodsblog.security.entity.JwtUser;
import com.hurpods.springboot.hurpodsblog.security.utils.JwtTokenUtil;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import com.hurpods.springboot.hurpodsblog.service.impl.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
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
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        List<String> authorities = jwtUser.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String token = JwtTokenUtil.createToken(jwtUser.getUsername(), authorities, rememberMe.get());
        rememberMe.remove();
        response.setHeader(SecurityConstants.TOKEN_HEADER, token);

        ObjectMapper mapper = new ObjectMapper();
        response.setHeader("Content-Type", "application/json;charset=utf8");
        jwtUser.setPassword("");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(mapper.writeValueAsString(ResultFactory.buildSuccessResult(jwtUser)));
        printWriter.flush();
        printWriter.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
    }
}
