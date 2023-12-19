package com.lkc.www.config;

import com.lkc.www.vo.Result;
import com.lkc.www.vo.SpzxException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();

        return Result.build(null,201,"出现了异常");
    }

    @ExceptionHandler(SpzxException.class)
    public Result error(SpzxException e){
        e.printStackTrace();

        return Result.build(null,e.getResultCodeEnum());
    }
}
