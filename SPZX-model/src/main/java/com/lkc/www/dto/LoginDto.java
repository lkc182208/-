package com.lkc.www.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginDto {
    private String userName;
    private String password;
    private String captcha;//验证码
    private String codeKey;//redis中验证码的key
}
