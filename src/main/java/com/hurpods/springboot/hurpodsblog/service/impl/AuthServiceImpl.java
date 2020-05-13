package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.security.JwtUser;
import com.hurpods.springboot.hurpodsblog.service.AuthService;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import com.hurpods.springboot.hurpodsblog.utils.JwtTokenUtil;
import com.hurpods.springboot.hurpodsblog.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsService jwtUserDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    MyUtil myUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public User register(String username,String password, HttpServletRequest request) {
        Timestamp nowTime = new Timestamp(new Date().getTime());

        User user=new User();
        user.setUserName(username);
        if (userService.selectByOthers(username) != null) {
            return null;
        }
        user.setUserPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setLastLoginIp(myUtil.getIpAddress(request));
        user.setLastLoginTime(nowTime);
        user.setRegisterTime(nowTime);
        user.setUserNickName("用户" + myUtil.getRandomString(8));

        userService.insert(user);

        return user;
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        return jwtTokenUtil.generateToken(userDetails);
    }
}
