package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.UserDAO;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> getAllUser() {
        return userDAO.getAllUser();
    }

    @Override
    public User getUserById(Integer userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public User getUserByOthers(String value) {
        return userDAO.getUserByOthers(value);
    }

    @Override
    public void deleteUserById(Integer userId) {
        userDAO.deleteUserById(userId);
    }

    @Override
    public void registerUser(User user) {
        userDAO.registerUser(user);
    }

    @Override
    public void updateUserInfo(User user) {
        userDAO.updateUserInfo(user);
    }

    @Override
    public void updateUserPassword(User user) {
        userDAO.updateUserPassword(user);
    }

    public Map<String, String> validate(String value, String type) {
        Map<String, String> result = new HashMap<>();
        String msg = "";
        String status = "false";
        String pat;
        Pattern pattern;
        Matcher matcher;
        User user = userDAO.getUserByOthers(value);

        switch (type) {
            case "username":
                if (value.equals("")) {
                    msg = "请输入用户名";
                    break;
                } else if (value.length() < 6 || value.length() > 20) {
                    msg = "用户名长度在6-20个字符之间";
                    break;
                } else {
                    pat = "^[a-zA-Z][a-zA-Z0-9]+$";
                    pattern = Pattern.compile(pat);
                    matcher = pattern.matcher(value);

                    if (!matcher.matches()) {
                        msg = "用户名仅由字母和数字组成，且仅以字母开头";
                        break;
                    } else if (user == null) {
                        msg = value + "已被注册";
                        break;
                    }
                }
                msg = "";
                status = "true";
                break;
            case "telephone":
                pat = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
                pattern = Pattern.compile(pat);
                matcher = pattern.matcher(value);
                if (value.length() != 0 && value.length() != 11) {
                    msg = "手机号码位数错误";
                    break;
                } else if (value.length() != 0 && !matcher.matches()) {
                    msg = "手机号格式错误";
                    break;
                } else if (value.length() != 0 && user != null) {
                    msg = value + "已被注册";
                    break;
                }
                msg = "";
                status = "true";
                break;
            case "email":
                pat = "[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}";
                pattern = Pattern.compile(pat);
                matcher = pattern.matcher(value);
                if (value.length() == 0) {
                    msg = "请输入邮箱地址";
                    break;
                } else {
                    if (!matcher.matches()) {
                        msg = "邮箱地址格式错误";
                        break;
                    } else if (user != null) {
                        msg = value + "已被注册";
                    }
                }
                msg = "";
                status = "true";
                break;
        }

        result.put("msg",msg);
        result.put("status",status);
        return result;
    }
}
