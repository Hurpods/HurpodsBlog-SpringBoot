package com.hurpods.springboot.hurpodsblog.security.entity;

import com.hurpods.springboot.hurpodsblog.pojo.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Data
public class JwtUser implements UserDetails {
    private Integer id;
    private String username;
    private String userAvatar;
    private String password;
    private Boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() {
    }


    public JwtUser(User user) {
        id = user.getUserId();
        username = user.getUserName();
        password = user.getUserPassword();
        userAvatar = user.getUserAvatar();
        enabled = user.getEnabled() == null ? true : user.getEnabled();
        authorities = user.getSecurityRoles();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
