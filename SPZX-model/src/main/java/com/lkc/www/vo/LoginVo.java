package com.lkc.www.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class LoginVo implements Serializable {
    private String token;
    private String refresh_token; //该字段不会存储对应的值
}
