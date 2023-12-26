package com.lkc.www.controller;

import com.lkc.www.dto.LoginDto;
import com.lkc.www.entity.SysUser;
import com.lkc.www.service.SysUserService;
import com.lkc.www.service.ValidateCodeService;
import com.lkc.www.vo.LoginVo;
import com.lkc.www.vo.Result;
import com.lkc.www.vo.ResultCodeEnum;
import com.lkc.www.vo.ValidateCodeVo;
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
    @Resource
    ValidateCodeService validateCodeService;

    //用户登录
    @Operation(summary = "登录的方法")
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    //生成验证码图片
    @GetMapping("/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }
    //获取用户信息
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader(name = "token") String token){
        //1.请求头获取token

        //2.根据token查询redis获取信息
        SysUser sysUser = sysUserService.getUserInfo(token);
        //3.返回用户信息
        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
    }
    //退出登录
    @GetMapping("/logout")
    public Result logout(@RequestHeader(name = "token") String token){
        sysUserService.logout(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
