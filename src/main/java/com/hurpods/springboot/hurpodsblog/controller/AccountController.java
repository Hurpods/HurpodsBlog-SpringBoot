package com.hurpods.springboot.hurpodsblog.controller;

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

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    UserService userService;
    @Autowired
    CityService cityService;

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
