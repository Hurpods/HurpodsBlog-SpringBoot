package com.hurpods.springboot.hurpodsblog.security.service;

import com.hurpods.springboot.hurpodsblog.security.entity.JwtUser;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new JwtUser(userService.getUserByUsername(s));
    }
}
