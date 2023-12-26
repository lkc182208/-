package com.lkc.www.controller;

import com.github.pagehelper.PageInfo;
import com.lkc.www.dto.SysUserDto;
import com.lkc.www.entity.SysUser;
import com.lkc.www.service.SysUserService;
import com.lkc.www.vo.Result;
import com.lkc.www.vo.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/sysUser")
public class UserManagerController {

    @Resource
    SysUserService sysUserService;

    /**
     * 用户管理的分页查询
     * @param pageNum 当前页
     * @param pageSize 一页显示记录数
     * @param sysUserDto 查询条件
     * @return
     */
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable("pageNum") Integer pageNum,
                             @PathVariable("pageSize") Integer pageSize,
                             SysUserDto sysUserDto){
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(pageNum,pageSize,sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 用户管理新增用户
     * @param sysUser
     * @return
     */
    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    /**
     * 用户管理更新用户
     * @param sysUser
     * @return
     */
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @DeleteMapping("/deleteSysUser/{userId}")
    public Result deleteSysUser(@PathVariable("userId") Long userId){
        sysUserService.deleteSysUser(userId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
