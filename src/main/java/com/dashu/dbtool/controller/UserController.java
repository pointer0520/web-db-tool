package com.dashu.dbtool.controller;

import com.dashu.dbtool.common.response.CommonResult;
import com.dashu.dbtool.model.dto.request.UserRegisterReq;
import com.dashu.dbtool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("login")
    public CommonResult<Void> login() {
        return null;
    }

    @PostMapping("register")
    public CommonResult<Void> register(@Validated @RequestBody UserRegisterReq req) {
        userService.register(req);
        return CommonResult.success();
    }
}
