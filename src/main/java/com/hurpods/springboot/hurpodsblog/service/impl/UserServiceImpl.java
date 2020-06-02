package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.UserDAO;
import com.hurpods.springboot.hurpodsblog.dao.UserRoleRefDAO;
import com.hurpods.springboot.hurpodsblog.dto.RegisterRequest;
import com.hurpods.springboot.hurpodsblog.dto.UpdateRequest;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.pojo.UserRoleRef;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import com.hurpods.springboot.hurpodsblog.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.hurpods.springboot.hurpodsblog.utils.MyUtil.regexMatch;

@Service
public class UserServiceImpl implements UserService {
    private static final String BASE_URL = "http://localhost:8090";
    private static final String DEFAULT_AVATAR = "/file/img/avatar/0.png";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
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
    public Result deleteUserByUsername(String password, String username) {
        User user = userDAO.getUserByUsername(username);
        boolean matches = bCryptPasswordEncoder.matches(password, user.getUserPassword());
        if (matches) {
            int num = userDAO.deleteUserByUsername(username);
            return num == 1 ? ResultFactory.buildSuccessResult("账号成功删除，即将返回首页") : ResultFactory.buildFailureResult("发生错误，请检查输入后重试");
        } else {
            return ResultFactory.buildCustomFailureResult(ResultCode.PARAM_IS_INVALID, "密码错误，请重新输入");
        }
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
            user.setUserPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
            user.setUserNickName("用户" + MyUtil.getRandomString(12));
            user.setUserEmail(registerRequest.getEmail());
            user.setUserAvatar(BASE_URL + DEFAULT_AVATAR);
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

            return userDAO.getUserByUsername(registerRequest.getUsername());
        } else {
            return null;
        }
    }

    @Override
    public Result updateUser(UpdateRequest updateRequest, Integer userId) {
        User user=userDAO.getUserById(userId);
        user.setUserNickName(updateRequest.getNickName());
        user.setUserName(updateRequest.getUserName());
        user.setUserLocate(updateRequest.getLocate());
        user.setUserEmail(updateRequest.getEmail());
        user.setUserTel(updateRequest.getTelephone());



        return null;
    }

    @Override
    public Result updateUserInfo(UpdateRequest updateRequest, String username) {
        User user = userDAO.getUserByUsername(username);

        user.setUserNickName(updateRequest.getNickName().equals("") ? user.getUserNickName() : updateRequest.getNickName());
        user.setUserTel(updateRequest.getTelephone().equals("") ? user.getUserTel() : updateRequest.getTelephone());
        user.setUserLocate(updateRequest.getLocate() == null ? user.getUserLocate() : updateRequest.getLocate());

        if (!updateRequest.getEmail().equals("")) {
            Result result = validateEmail(updateRequest.getEmail());
            if (result.getCode() == 1) {
                user.setUserEmail(updateRequest.getEmail());
            } else {
                return result;
            }
        }

        int num = userDAO.updateUserInfo(user);

        return num == 1 ? ResultFactory.buildSuccessResult(user) : ResultFactory.buildFailureResult("发生错误，请检查输入后重试");
    }

    @Override
    public Result updateUserPassword(UpdateRequest updateRequest, String username) {
        User user = userDAO.getUserByUsername(username);
        //加密后的密码匹配不能用.equals(),需要用Spring Security提供的matches对比
        boolean matches = bCryptPasswordEncoder.matches(updateRequest.getOldPassword(), user.getUserPassword());
        if (matches) {
            if (updateRequest.getNewPassword().equals(updateRequest.getConfirmPassword())) {
                user.setUserPassword(bCryptPasswordEncoder.encode(updateRequest.getNewPassword()));
                int num = userDAO.updateUserPassword(user);
                return num == 1 ? ResultFactory.buildSuccessResult("修改密码成功，请重新登录") : ResultFactory.buildFailureResult("发生错误，请检查输入后重试");
            } else {
                return ResultFactory.buildCustomFailureResult(ResultCode.PARAM_IS_INVALID, "两次输入的密码不一致");
            }
        } else {
            return ResultFactory.buildCustomFailureResult(ResultCode.PARAM_IS_INVALID, "原始密码错误！");
        }
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
            } else if (getUserByUsername(username) != null) {
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
                } else if (getUserByOthers(telephone) != null) {
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
            } else if (getUserByOthers(email) != null) {
                return ResultFactory.buildFailureResult(email + "已被注册");
            } else {
                return ResultFactory.buildSuccessResult(email);
            }
        }
    }

    private String getSalt(Object object) throws Exception {
        Class cl = Class.forName(object.getClass().getName());
        Method getUsername = cl.getMethod("getUsername");
        String username = (String) getUsername.invoke(object);

        return Base64.getEncoder().encodeToString(username.getBytes());
    }
}
