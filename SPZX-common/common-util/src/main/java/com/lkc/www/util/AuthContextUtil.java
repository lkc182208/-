package com.lkc.www.util;

import com.lkc.www.entity.SysUser;

public class AuthContextUtil {

    private static final ThreadLocal<SysUser> USER_THREAD_LOCAL = new ThreadLocal<>();

    //添加数据
    public static void set(SysUser sysUser){
        USER_THREAD_LOCAL.set(sysUser);
    }
    //获取数据
    public static SysUser get(){
        return USER_THREAD_LOCAL.get();
    }
    //删除数据
    public static void delete(){
        USER_THREAD_LOCAL.remove();
    }
}
