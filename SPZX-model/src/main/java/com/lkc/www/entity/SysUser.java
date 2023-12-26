package com.lkc.www.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表(SysUser)实体类
 *
 * @author 刘凯存
 * @since 2023-12-18 18:19:47
 */
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = -61496600327913568L;
/**
     * 会员id
     */
    private Long id;
/**
     * 用户名
     */
    private String username;
/**
     * 密码
     */
    private String password;
/**
     * 姓名
     */
    private String name;
/**
     * 手机
     */
    private String phone;
/**
     * 头像
     */
    private String avatar;
/**
     * 描述
     */
    private String description;
/**
     * 状态（1：正常 0：停用）
     */
    private Integer status;
/**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
/**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;
/**
     * 删除标记（0:未删除 1已删除）
     */
    private Integer isDeleted;
}

