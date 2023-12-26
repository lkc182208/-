package com.lkc.www.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 角色(SysRole)实体类
 *
 * @author kaicun.liu
 * @since 2023-12-22 10:53:32
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 147044657289333444L;
/**
     * 角色id
     */
    private Long id;
/**
     * 角色名称
     */
    private String roleName;
/**
     * 角色编码
     */
    private String roleCode;
/**
     * 描述
     */
    private String description;
/**
     * 创建时间
     */

    @JsonFormat
    private Date createTime;
/**
     * 更新时间
     */
    @JsonFormat
    private Date updateTime;
/**
     * 删除标记（0:不可用 1:可用）
     */
    private Integer isDeleted;


}

