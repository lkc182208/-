package com.lkc.www.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lkc.www.dto.LoginDto;
import com.lkc.www.dto.SysUserDto;
import com.lkc.www.entity.SysUser;
import com.lkc.www.mapper.SysUserDao;
import com.lkc.www.service.SysUserService;
import com.lkc.www.util.AuthContextUtil;
import com.lkc.www.util.JwtUtil;
import com.lkc.www.vo.LoginVo;
import com.lkc.www.vo.ResultCodeEnum;
import com.lkc.www.vo.SpzxException;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.lkc.www.util.RedisConstants.*;

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
        //2.获取验证码
        String captcha = loginDto.getCaptcha();
        String inputCodeKey = loginDto.getCodeKey();
        //3.检验验证码
        String code = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + inputCodeKey);
        if(StrUtil.isBlank(captcha) || !captcha.equalsIgnoreCase(code)){
            //3.1不一致，则抛出异常
            throw new SpzxException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //3.2一致，判断是否已过期
        Long expire = stringRedisTemplate.opsForValue().getOperations().getExpire(LOGIN_CODE_KEY + inputCodeKey);
        if(expire < 0) {
            //说明已过期或没有此key
            throw new SpzxException(ResultCodeEnum.VALIDATECODE_TTL);
        }
        //验证码通过，判断验证码
        stringRedisTemplate.delete(LOGIN_CODE_KEY+inputCodeKey);
        //4.获取数据
        String username = loginDto.getUserName();
        String inputPassword = loginDto.getPassword();

        //5.数据库中查询
        SysUser user = sysUserDao.getUser(username);
        if(user == null){
            throw new SpzxException(ResultCodeEnum.LOGIN_ERROR);
        }
        //6.验证密码是否正确
        String md5DBPassword = user.getPassword();
        String md5NewPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        //用户输入的密码加密后和数据库中的密码比较
        if(!md5DBPassword.equals(md5NewPassword)){
            //密码错误
            throw new SpzxException(ResultCodeEnum.LOGIN_ERROR);
        }
        /*//7.保证幂等性，用户点击了多次登录，只插入一条token到redis中
        int usernameLength = username.length();
        Set<String> keys = stringRedisTemplate.keys(LOGIN_USER_KEY+"*");
        for (String key : keys) {
            boolean flag = key.substring(key.length() - usernameLength).contains(username);
            if(flag){
                return null;
            }
        }*/
        //8.生成令牌
        user.setPassword(null);
        user.setPhone(null);
        String token = JwtUtil.getToken(user);
        //9.保存信息到redis中
        //将令牌存到redis中,后20位作为key，过期时间设置30分钟
        int tokenLength = token.length() - 26;
        stringRedisTemplate.opsForValue().set(LOGIN_USER_KEY+token.substring(tokenLength),token,LOGIN_USER_TTL,TimeUnit.MINUTES);
        //10.返回值
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        //1.检验token是否有效
        if(StrUtil.isBlank(token)){
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }
        SysUser sysUser = AuthContextUtil.get();

        if(BeanUtil.isEmpty(sysUser)){
            throw new SpzxException(ResultCodeEnum.LOGIN_AUTH);
        }
        //5.将用户信息返回
        return sysUser;
    }

    @Override
    public void logout(String token) {
        //删除用户信息
        int tokenLength = token.length() - 26;

        Boolean delete = stringRedisTemplate.delete(LOGIN_USER_KEY + token.substring(tokenLength));

        if(Boolean.FALSE.equals(delete)){
            throw new SpzxException(ResultCodeEnum.LOGIN_TTL);
        }
    }

    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum,pageSize);
        //查询数据库
        List<SysUser> list =  sysUserDao.findByPage(sysUserDto);
        return new PageInfo(list);
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        if(BeanUtil.isEmpty(sysUser)){
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }
        //判断用户名不能重复
        String username = sysUser.getUsername();
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysUser::getUsername,username);
        SysUser user = sysUserDao.selectOne(lqw);
        if(user != null){
            //用户名已经存在
            throw new SpzxException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //密码加密
        String md5Password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(md5Password);

        sysUserDao.saveSysUser(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        if(BeanUtil.isEmpty(sysUser)){
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }
        //判断用户名
        String username = sysUser.getUsername();
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysUser::getUsername,username);
        Long l = sysUserDao.selectCount(lqw);
        if(l > 1){
            //用户名已经存在
            throw new SpzxException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        if(!StrUtil.isBlank(sysUser.getPassword())){
            //密码加密
            String md5Password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
            sysUser.setPassword(md5Password);
        }
        //更新时间
        sysUser.setUpdateTime(new Date(System.currentTimeMillis()));
        sysUserDao.updateSysUser(sysUser);
    }

    @Override
    public void deleteSysUser(Long userId) {
        SysUser sysUser = sysUserDao.selectById(userId);
        if(BeanUtil.isEmpty(sysUser)){
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }
        sysUserDao.deleteSysUser(userId);
    }
}
