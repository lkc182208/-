package com.lkc.www.mapper;

import com.lkc.www.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author ???
 * @since 2023-12-18 18:19:47
 */
public interface SysUserDao {

    SysUser getUser(@Param("username") String username);

}

