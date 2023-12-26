package com.lkc.www.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lkc.www.dto.SysUserDto;
import com.lkc.www.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author kaicun.liu
 * @since 2023-12-18 18:19:47
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    SysUser getUser(@Param("username") String username);

    List<SysUser> findByPage(@Param("sysUser") SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);
}