package com.lkc.www.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValidateCodeVo implements Serializable {

    //验证码的key
    private String codeKey;

    //图片验证码对应的字符串数据
    private String codeValue;
}
