package com.lkc.www.service;

import com.github.pagehelper.PageInfo;
import com.lkc.www.dto.SysRoleDto;
import com.lkc.www.entity.SysRole;

public interface SysRoleService {
    PageInfo<SysRole> findRoleByPage(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

}
