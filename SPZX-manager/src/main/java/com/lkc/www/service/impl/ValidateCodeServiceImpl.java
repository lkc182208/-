package com.lkc.www.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.lkc.www.service.ValidateCodeService;
import com.lkc.www.vo.ValidateCodeVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.lkc.www.util.RedisConstants.LOGIN_CODE_KEY;
import static com.lkc.www.util.RedisConstants.LOGIN_CODE_TTL;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
        //生成图片验证码
        //hutool
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 2);
        String code = circleCaptcha.getCode();//验证码
        String imageBase64 = circleCaptcha.getImageBase64();//图片验证码，base64编码

        //把验证码存储到redis中，设置redis的key，uuid，redis的value：验证码值，过期时间
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+key, code,LOGIN_CODE_TTL, TimeUnit.MINUTES);

        //返回ValidateCodeVO对
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64,"+imageBase64);
        return validateCodeVo;
    }
}
