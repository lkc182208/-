package com.lkc.www.service;

import com.github.pagehelper.PageInfo;
import com.lkc.www.dto.LoginDto;
import com.lkc.www.dto.SysUserDto;
import com.lkc.www.entity.SysUser;
import com.lkc.www.vo.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteSysUser(Long userId);

}
