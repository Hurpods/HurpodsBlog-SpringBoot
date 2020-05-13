package com.hurpods.springboot.hurpodsblog.security;

import com.hurpods.springboot.hurpodsblog.dao.RoleMapper;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("JwtUserDetailsService:" + userName);
        User user=userService.selectByOthers(userName);
        System.out.println("1");
        if(user==null){
            System.out.println("2");
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
        }else{
            return new JwtUser(user,user.getRoles());
        }
    }
}