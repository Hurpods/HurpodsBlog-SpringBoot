package com.hurpods.springboot.hurpodsblog.security.filter;

import com.hurpods.springboot.hurpodsblog.security.constans.SecurityConstants;
import com.hurpods.springboot.hurpodsblog.security.service.UserDetailsServiceImpl;
import com.hurpods.springboot.hurpodsblog.security.utils.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//过滤器处理所有HTTP请求，并检查是否存在带有正确令牌的Authorization标头。例如，如果令牌未过期或签名密钥正确。
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
        } else {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }

    /**
     * 获取用户认证信息 Authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {
        String token = authorization.replace(SecurityConstants.TOKEN_PREFIX, "");
        try {
            String username = JwtTokenUtil.getUsernameByToken(token);
            if (!StringUtils.isEmpty(username)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                return userDetails.isEnabled() ? usernamePasswordAuthenticationToken : null;
            }
        } catch (Exception e) {
            System.out.println("Request to parse JWT with invalid signature . Detail : " + e.getMessage());
        }
        return null;
    }
}
