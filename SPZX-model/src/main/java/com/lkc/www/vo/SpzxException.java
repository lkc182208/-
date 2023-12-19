package com.lkc.www.vo;

import lombok.Data;

@Data
public class SpzxException extends RuntimeException{
    private Integer code;
    private String message;

    private ResultCodeEnum resultCodeEnum;

    public SpzxException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        this.resultCodeEnum = resultCodeEnum;
    }
    public SpzxException(Integer code,String message){
        this.code = code;
        this.message = message;
    }

}
