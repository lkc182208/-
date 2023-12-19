package com.lkc.www;

import com.lkc.www.entity.SysUser;
import com.lkc.www.mapper.SysUserDao;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
public class T {

    @Resource
    SysUserDao sysUserDao;

    @Resource
    StringRedisTemplate stringRedisTemplate;

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
        String InputPassword = "123456";

        String md5Password = DigestUtils.md5DigestAsHex(InputPassword.getBytes());

        System.out.println(md5Password);
    }
}
