package com.boke.auth.mapper;

import com.boke.auth.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    int removeByRoleId(String roleId);

    List<String> getRoleIdsByUserId(String userId);

    int batchUserRole(List<SysUserRole> list);

    int removeByUserId(String userId);

    List<String> getInfoByUserIdByRoleId( String roleId);
}