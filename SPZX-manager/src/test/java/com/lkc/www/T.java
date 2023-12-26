package com.lkc.www;

import com.github.pagehelper.PageInfo;
import com.lkc.www.dto.SysRoleDto;
import com.lkc.www.entity.SysRole;
import com.lkc.www.entity.SysUser;
import com.lkc.www.mapper.SysRoleDao;
import com.lkc.www.mapper.SysUserDao;
import com.lkc.www.service.SysRoleService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest(classes = ManagerApplication.class)
public class T {

    @Resource
    SysUserDao sysUserDao;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    SysRoleService sysRoleService;

    @Resource
    SysRoleDao sysRoleDao;
    @Test
    void testtt() {
        List<SysRole> sysRoles = sysRoleDao.selectList(null);
        System.out.println(sysRoles);
    }

    @Test
    void name() {
        String username = "FcZgqRte8gk4hkum2qQiWXduwpj0Fnd4ST_90aXkWSLAcGQYOUxM5yIdSoNkiz0WkNWXkyE3TX7056xZE30AqA";
        
        //取字符串后30位
        int length = username.length()-30;

        String substring = username.substring(length);
        System.out.println(substring.length());
        System.out.println(substring);
    }

    @Test
    void name2() {
        Set<String> keys = stringRedisTemplate.keys("login:user*");
        System.out.println(keys);
    }

    @Test
    void name3() {
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(key);
    }
}
