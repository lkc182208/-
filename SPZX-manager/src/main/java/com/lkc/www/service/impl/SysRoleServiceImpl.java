package com.lkc.www.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lkc.www.dto.SysRoleDto;
import com.lkc.www.entity.SysRole;
import com.lkc.www.mapper.SysRoleDao;
import com.lkc.www.service.SysRoleService;
import com.lkc.www.vo.ResultCodeEnum;
import com.lkc.www.vo.SpzxException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    SysRoleDao sysRoleDao;

    @Override
    public PageInfo<SysRole> findRoleByPage(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto) {
        //设置分页参数
        PageHelper.startPage(pageNum,pageSize);
        //根据条件查询所有数据
        List<SysRole> sysRoleList = sysRoleDao.findRoleByPage(sysRoleDto);

        return new PageInfo<SysRole>(sysRoleList);
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        //检验参数
        if (BeanUtil.isEmpty(sysRole)) {
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }
        //接口层添加
        sysRoleDao.saveSysRole(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        //检验参数
        if(BeanUtil.isEmpty(sysRole)){
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }
        sysRoleDao.updateSysRole(sysRole);
    }

    @Override
    public void deleteById(Long roleId) {
        //检验参数
        if(null == roleId){
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }
        sysRoleDao.deleteByIdAfter(roleId);
    }
}
