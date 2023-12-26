package com.lkc.www.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lkc.www.dto.SysRoleDto;
import com.lkc.www.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author kaicun.liu
 * @since 2023-12-22 10:53:31
 */
public interface SysRoleDao extends BaseMapper<SysRole> {

    List<SysRole> findRoleByPage(@Param("sysrole") SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(@Param("sysrole") SysRole sysRole);

    void deleteByIdAfter(@Param("id") Long roleId);
}