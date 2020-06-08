package com.hurpods.springboot.hurpodsblog.controller;

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

import javax.servlet.http.HttpServletRequest;
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public Result getAllUser(Integer pageNum) {
        PageHelper.startPage(pageNum,12);
        List<User> userList = userService.getAllUser();

        Integer size=userService.getNumber();

        Map<String,Object>result=new HashMap<>();
        result.put("pageSize",size);
        result.put("userList",userList);

        return userList.size() != 0 ?
                ResultFactory.buildSuccessResult(result) :
                ResultFactory.buildCustomFailureResult(ResultCode.RESULT_DATA_NONE, "无数据");
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_EDITOR','ROLE_JUDGEMENT','ROLE_MANAGER')")
    public Result getUserDetail(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            user.setUserPassword(null);
            return ResultFactory.buildSuccessResult(user);
        } else {
            return ResultFactory.buildFailureResult(ResultCode.USER_NOT_EXIST);
        }
    }

    @PutMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public Result updateUser(@PathVariable Integer userId,@RequestBody UpdateRequest user){
        return userService.updateUser(user,userId);
    }

    @GetMapping("/city")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_EDITOR','ROLE_JUDGEMENT','ROLE_MANAGER')")
    public Result getAllCity() {
        return ResultFactory.buildSuccessResult(cityService.getAllCity());
    }

    @PostMapping("/updateInfo/{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_EDITOR','ROLE_JUDGEMENT','ROLE_MANAGER')")
    public Result updateUser(@RequestBody UpdateRequest updateRequest, @PathVariable String username) {
        return userService.updateUserInfo(updateRequest, username);
    }

    @PostMapping("/updatePassword/{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_EDITOR','ROLE_JUDGEMENT','ROLE_MANAGER')")
    public Result updatePassword(@RequestBody UpdateRequest updateRequest, @PathVariable String username) {
        return userService.updateUserPassword(updateRequest, username);
    }

    @PostMapping("/deleteUser/{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_JUDGEMENT','ROLE_MANAGER')")
    public Result deleteUser(@RequestBody UpdateRequest updateRequest, @PathVariable String username) {
        return userService.deleteUserByUsername(updateRequest.getOldPassword(), username);
    }
}
