package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.UserDAO;
import com.hurpods.springboot.hurpodsblog.dao.UserRoleRefDAO;
import com.hurpods.springboot.hurpodsblog.dto.LoginRequest;
import com.hurpods.springboot.hurpodsblog.dto.RegisterRequest;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.pojo.UserRoleRef;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import com.hurpods.springboot.hurpodsblog.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.hurpods.springboot.hurpodsblog.utils.MyUtil.regexMatch;

@Service
public class UserServiceImpl implements UserService {
    private static final String DEFAULT_AVATAR = "/file/img/avatar/0.png";

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserRoleRefDAO urrDAO;

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
    public User registerUser(RegisterRequest registerRequest, HttpServletRequest request) {
        if (!registerRequest.getPassword().equals(registerRequest.getRePassword())) {
            return null;
        }

        List<Result> resultList = new ArrayList<>();
        resultList.add(validateUsername(registerRequest.getUsername()));
        resultList.add(validateEmail(registerRequest.getEmail()));
        resultList.add(validateTelephone(registerRequest.getTelephone()));

        int flag = resultList.stream().mapToInt(Result::getCode).sum();

        if (flag == 3) {
            Timestamp nowTime = new Timestamp(new Date().getTime());

            User user = new User();

            user.setUserName(registerRequest.getUsername());

            String salt = Base64.getEncoder().encodeToString(registerRequest.getUsername().getBytes());
            user.setUserPassword(MyUtil.hashPass(registerRequest.getPassword(), salt));

            user.setUserNickName("用户" + MyUtil.getRandomString(12));
            user.setUserEmail(registerRequest.getEmail());
            user.setUserAvatar(DEFAULT_AVATAR);
            user.setLastLoginIp(MyUtil.getIpAddress(request));
            user.setRegisterTime(nowTime);
            user.setLastLoginTime(nowTime);
            user.setUserTel(registerRequest.getTelephone());
            user.setEnabled(true);

            userDAO.registerUser(user);

            UserRoleRef userRoleRef = new UserRoleRef();
            userRoleRef.setUserId(user.getUserId());
            userRoleRef.setRoleId(2);
            urrDAO.createUserRoleRef(userRoleRef);

            return user;
        } else {
            return null;
        }
    }

    @Override
    public Result loginUser(LoginRequest loginRequest, HttpServletRequest request) {
        User user = userDAO.getUserByUsername(loginRequest.getUsername());
        String salt = Base64.getEncoder().encodeToString(loginRequest.getUsername().getBytes());
        try {
            System.out.println( getSalt(loginRequest));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void updateUserInfo(User user) {
        userDAO.updateUserInfo(user);
    }

    @Override
    public void updateUserPassword(User user) {
        userDAO.updateUserPassword(user);
    }

    @Override
    public Result validateUsername(String username) {
        if (username.equals("")) {
            return ResultFactory.buildFailureResult("请输入用户名");
        } else if (username.length() < 6 || username.length() > 20) {
            return ResultFactory.buildFailureResult("用户名长度在6-20个字符之间");
        } else {
            String pat = "^[a-zA-Z][a-zA-Z0-9]+$";
            boolean flag = regexMatch(pat, username);
            if (!flag) {
                return ResultFactory.buildFailureResult("用户名仅由字母和数字组成，且仅以字母开头");
            } else if (userDAO.getUserByUsername(username) != null) {
                return ResultFactory.buildFailureResult(username + "已被注册");
            } else {
                return ResultFactory.buildSuccessResult(username);
            }
        }
    }

    @Override
    public Result validateTelephone(String telephone) {
        if (telephone.length() != 0) {
            if (telephone.length() != 11) {
                return ResultFactory.buildFailureResult("手机号码位数错误");
            } else {
                String pat = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
                boolean flag = regexMatch(pat, telephone);
                if (!flag) {
                    return ResultFactory.buildFailureResult("手机号码格式错误");
                } else if (userDAO.getUserByOthers(telephone) != null) {
                    return ResultFactory.buildFailureResult(telephone + "已被注册");
                } else {
                    return ResultFactory.buildSuccessResult(telephone);
                }
            }
        } else {
            return ResultFactory.buildSuccessResult(null);
        }
    }

    @Override
    public Result validateEmail(String email) {
        if (email.length() == 0) {
            return ResultFactory.buildFailureResult("请输入邮箱地址");
        } else {
            String pat = "[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}";
            boolean flag = regexMatch(pat, email);
            if (!flag) {
                return ResultFactory.buildFailureResult("邮箱地址格式错误");
            } else if (userDAO.getUserByOthers(email) != null) {
                return ResultFactory.buildFailureResult(email + "已被注册");
            } else {
                return ResultFactory.buildSuccessResult(email);
            }
        }
    }

    private String getSalt(Object object) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class cl = Class.forName(object.getClass().getName());
        System.out.println(object.getClass().getName());
        Object requestSalt = cl.newInstance();
        Method getUsername = cl.getMethod("getUsername");
        return (String) getUsername.invoke(requestSalt);
    }
}