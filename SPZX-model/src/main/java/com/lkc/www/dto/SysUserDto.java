package com.lkc.www.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserDto {

    private String username;

    private Date beginTime;

    private Date endTime;
}
