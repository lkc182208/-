package com.lkc.www.service;

import com.lkc.www.dto.LoginDto;
import com.lkc.www.vo.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);
}
