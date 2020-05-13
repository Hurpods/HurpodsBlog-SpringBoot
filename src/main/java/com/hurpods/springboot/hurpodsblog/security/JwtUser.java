package com.hurpods.springboot.hurpodsblog.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hurpods.springboot.hurpodsblog.pojo.Role;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JwtUser implements UserDetails {
    private Integer userId;
    private String userName;
    private String userPassword;
    //权限集合
    private Collection<? extends GrantedAuthority> authorities;

    //拼接用户权限和密码，从数据库调入
    public JwtUser(User user, List<Role> authorities) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userPassword = user.getUserPassword();
        this.authorities = authorities;
    }

    //获取角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    /**
     * 账户是否过期
     *
     * @return boolean
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否未锁定
     *
     * @return boolean
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     *
     * @return boolean
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否激活
     *
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}