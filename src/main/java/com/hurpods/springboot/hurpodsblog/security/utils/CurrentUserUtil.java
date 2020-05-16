package com.hurpods.springboot.hurpodsblog.security.utils;

import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserUtil {
    @Autowired
    UserService userService;

    public User getCurrentUser() {
        return userService.getUserByUsername(getCurrentUserName());
    }

    private static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
