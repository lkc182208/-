package com.lkc.www.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.lkc.www.dto.LoginDto;
import com.lkc.www.entity.SysUser;
import com.lkc.www.mapper.SysUserDao;
import com.lkc.www.service.SysUserService;
import com.lkc.www.util.JwtUtil;
import com.lkc.www.vo.LoginVo;
import com.lkc.www.vo.ResultCodeEnum;
import com.lkc.www.vo.SpzxException;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.lkc.www.util.RedisConstants.LOGIN_USER_KEY;
import static com.lkc.www.util.RedisConstants.LOGIN_USER_TTL;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    SysUserDao sysUserDao;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
        //1.校验参数
        if(loginDto == null){
            //用户信息为空
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }
        //2.获取数据
        String username = loginDto.getUserName();
        String inputPassword = loginDto.getPassword();

        //3.数据库中查询
        SysUser user = sysUserDao.getUser(username);
        if(user == null){
            throw new SpzxException(ResultCodeEnum.LOGIN_ERROR);
        }
        //4.验证密码是否正确
        String md5DBPassword = user.getPassword();
        String md5NewPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        //用户输入的密码加密后和数据库中的密码比较
        if(!md5DBPassword.equals(md5NewPassword)){
            //密码错误
            throw new SpzxException(ResultCodeEnum.LOGIN_ERROR);
        }
        //保证幂等性，用户点击了多次登录，只插入一条token到redis中
        int usernameLength = username.length();
        Set<String> keys = stringRedisTemplate.keys(LOGIN_USER_KEY+"*");
        for (String key : keys) {
            boolean flag = key.substring(key.length() - usernameLength).contains(username);
            if(flag){
                return null;
            }
        }
        //5.生成令牌
        user.setPassword(null);
        user.setPhone(null);
        String token = JwtUtil.getToken(user);
        //6.保存信息到redis中
        //将令牌存到redis中,后20位作为key，过期时间设置30分钟
        int tokenLength = token.length() - 20;
        stringRedisTemplate.opsForValue().set(LOGIN_USER_KEY+token.substring(tokenLength)+username,token,LOGIN_USER_TTL,TimeUnit.MINUTES);
        //返回值
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }
}
