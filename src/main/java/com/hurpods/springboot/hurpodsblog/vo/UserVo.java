package com.hurpods.springboot.hurpodsblog.vo;

import com.hurpods.springboot.hurpodsblog.pojo.User;
import lombok.Data;

import java.io.Serializable;
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer userId;

    private String userName;

    private String userNickName;

    private String userAvatar;

    public UserVo(User user){
        this.userId=user.getUserId();
        this.userName=user.getUserName();
        this.userNickName=user.getUserNickName();
    }
}
