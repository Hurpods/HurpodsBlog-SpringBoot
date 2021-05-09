package com.hurpods.springboot.hurpodsblog.controller;


import cn.hutool.json.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hurpods.springboot.hurpodsblog.dto.UpdateRequest;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.CityService;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    UserService userService;
    @Autowired
    CityService cityService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result getAllUser(Integer pageNum) {
        List<User> userList;
        if (pageNum != null) {
            PageHelper.startPage(pageNum, 12);
        }
        userList = userService.getAllUser();

        Integer size = userService.getNumber();

        Map<String, Object> result = new HashMap<>();
        result.put("pageSize", size);
        result.put("userList", userList);

        return userList.size() != 0 ?
                ResultFactory.buildSuccessResult(result) :
                ResultFactory.buildCustomFailureResult(ResultCode.RESULT_DATA_NONE, "无数据");
    }

    @GetMapping("/user/{param}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_EDITOR','ROLE_JUDGEMENT')")
    public Result getUserDetail(@PathVariable String param) {
        User user;
        try {
            Integer userId = Integer.parseInt(param);
            user = userService.getUserById(userId);
        } catch (Exception e) {
            user = userService.getUserByUsername(param);
        }
        if (user != null) {
            user.setUserPassword(null);
            return ResultFactory.buildSuccessResult(user);
        } else {
            return ResultFactory.buildFailureResult(ResultCode.USER_NOT_EXIST);
        }
    }

    @GetMapping("/user/search/{keywords}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result fuzzySearch(@PathVariable String keywords) {
        List<User> userList = userService.fuzzySearch(keywords);
        return userList.size() != 0 ?
                ResultFactory.buildSuccessResult(userList) :
                ResultFactory.buildFailureResult(ResultCode.RESULT_DATA_NONE);
    }

    @PutMapping("/user/ban")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_JUDGEMENT')")
    public Result banUser(@RequestBody String ids) {
        JSONObject json = new JSONObject(ids);
        List<Integer> idList = (List<Integer>) json.get("ids");
        Integer res = userService.banUser(idList);
        return res > 0 ? ResultFactory.buildSuccessResult(res) : ResultFactory.buildFailureResult("发生未知错误");
    }

    @PutMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result updateUser(@PathVariable Integer userId, @RequestBody UpdateRequest user) {
        return userService.updateUser(user, userId);
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result deleteUser(@PathVariable Integer userId) {
        return userService.deleteUserById(userId);
    }

    @GetMapping("/specialUser")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result getSpecial(Integer pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<User> specialList = userService.getSpecial();
        Integer size = specialList.size();
        Map<String, Object> map = new HashMap<>();
        map.put("specialList", specialList);
        map.put("specialSize", size);
        return specialList.size() != 0 ?
                ResultFactory.buildSuccessResult(map) :
                ResultFactory.buildCustomFailureResult(ResultCode.RESULT_DATA_NONE, "不存在特殊用户");
    }

    @GetMapping("/city")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_EDITOR','ROLE_JUDGEMENT')")
    public Result getAllCity() {
        return ResultFactory.buildSuccessResult(cityService.getAllCity());
    }

    @PostMapping("/user/update/{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_EDITOR','ROLE_JUDGEMENT')")
    public Result updateUser(@RequestBody UpdateRequest updateRequest, @PathVariable String username) {
        return userService.updateUserInfo(updateRequest, username);
    }

    @PostMapping("/user/updatePassword/{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_EDITOR','ROLE_JUDGEMENT')")
    public Result updatePassword(@RequestBody UpdateRequest updateRequest, @PathVariable String username) {
        return userService.updateUserPassword(updateRequest, username);
    }

    @PostMapping("/user/delete/{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_JUDGEMENT')")
    public Result deleteUser(@RequestBody UpdateRequest updateRequest, @PathVariable String username) {
        return userService.deleteUserByUsername(updateRequest.getOldPassword(), username);
    }
}
