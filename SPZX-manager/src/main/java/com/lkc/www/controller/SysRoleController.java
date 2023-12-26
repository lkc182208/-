package com.lkc.www.controller;

import com.github.pagehelper.PageInfo;
import com.lkc.www.dto.SysRoleDto;
import com.lkc.www.entity.SysRole;
import com.lkc.www.service.SysRoleService;
import com.lkc.www.vo.Result;
import com.lkc.www.vo.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {
    @Resource
    SysRoleService sysRoleService;

    /**
     * pageNum 当前页
     * pageSize 一页显示的数据量
     * sysRoleDto 前端传过来的条件查询数据
     * @return
     */
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findRoleByPage(@PathVariable(value = "pageNum") Integer pageNum,
                                                    @PathVariable(value = "pageSize") Integer pageSize,
                                                    @RequestBody SysRoleDto sysRoleDto){
           PageInfo<SysRole> pageInfo = sysRoleService.findRoleByPage(pageNum,pageSize,sysRoleDto);
           return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
    /**
     * 添加角色
     * @param sysRole
     * @return
     */
    @PostMapping("/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    /**
     * 更新角色
     * @param sysRole
     * @return
     */
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable("roleId") Long roleId){
        sysRoleService.deleteById(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
