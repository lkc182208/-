package com.lkc.www.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.lkc.www.entity.SysUser;
import com.lkc.www.util.AuthContextUtil;
import com.lkc.www.util.JwtUtil;
import com.lkc.www.vo.Result;
import com.lkc.www.vo.ResultCodeEnum;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import static com.lkc.www.util.RedisConstants.LOGIN_USER_KEY;
import static com.lkc.www.util.RedisConstants.LOGIN_USER_TTL;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求头中的token数据
        String token = request.getHeader("token");
        //2.判断token是否存在
        if(StrUtil.isBlank(token)){
            //token为空
            responseNoLoginInfo(response);
            return false;
        }
        //3.根据token去redis中查询
        int tokenLength = token.length() - 26;
        String realToken = stringRedisTemplate.opsForValue().get(LOGIN_USER_KEY + token.substring(tokenLength));
        //4.判断token是否有效
        Claims claimsBody = JwtUtil.getClaimsBody(realToken);
        int expiredTTL = JwtUtil.verifyToken(claimsBody);
        if(expiredTTL>0){
            //已过期
            responseNoLoginInfo(response);
            return false;
        }
        //5.解析token获取用户信息
        SysUser sysUser = new SysUser();
        BeanUtil.fillBeanWithMap(claimsBody,sysUser,true);
        //6.将用户信息存储到ThreadLocal中
        AuthContextUtil.set(sysUser);
        //7.刷新token的过期时间
        stringRedisTemplate.expire(LOGIN_USER_KEY + token.substring(tokenLength),LOGIN_USER_TTL, TimeUnit.MINUTES);
        //8.放行
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //删除ThreadLocal中的值
        AuthContextUtil.delete();
    }
    private void responseNoLoginInfo(HttpServletResponse response){
        Result result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);

        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");

        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }
}
