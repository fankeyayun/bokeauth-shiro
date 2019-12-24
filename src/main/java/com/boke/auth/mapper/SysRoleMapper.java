package com.boke.auth.mapper;

import com.boke.auth.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectAll();

    List<SysRole> getRoleInfoByIds(List<String> ids);
}