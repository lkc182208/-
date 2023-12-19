package com.lkc.www.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginDto {
    private String userName;
    private String password;
}
