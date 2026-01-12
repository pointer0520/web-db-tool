package com.dashu.dbtool.controller;

import com.dashu.dbtool.common.response.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/init")
    public CommonResult<String> testInit() {
        return CommonResult.success("成功");
    }
}
