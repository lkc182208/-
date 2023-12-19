package com.lkc.www.controller;

import com.lkc.www.dto.LoginDto;
import com.lkc.www.service.SysUserService;
import com.lkc.www.vo.LoginVo;
import com.lkc.www.vo.Result;
import com.lkc.www.vo.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/admin/system/index")
public class SysUserController {
    @Resource
    SysUserService sysUserService;

    //用户登录
    @Operation(summary = "登录的方法")
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }
}
