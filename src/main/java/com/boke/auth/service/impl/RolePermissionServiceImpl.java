package com.boke.auth.service.impl;

import com.boke.auth.entity.SysRolePermission;
import com.boke.auth.exception.BusinessException;
import com.boke.auth.exception.code.BaseResponseCode;
import com.boke.auth.mapper.SysRolePermissionMapper;
import com.boke.auth.service.RolePermissionService;
import com.boke.auth.vo.req.RolePermissionOperationReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: RolePermissionServiceImpl
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/19 11:41
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 11:41
 * @Version: 0.0.1
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    /**
     * 删除角色和权限关联数据
     * @Author:      as
     * @CreateDate:  2019/10/20 10:41
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 10:41
     * @Version:     0.0.1
     * @param roleId
     * @return       int
     * @throws
     */
    @Override
    public int removeByRoleId(String roleId) {
        return sysRolePermissionMapper.removeByRoleId(roleId);
    }
    /**
     * 根据角色id集合获取所有的菜单权限id
     * @Author:      as
     * @CreateDate:  2019/10/20 15:57
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 15:57
     * @Version:     0.0.1
     * @param roleIds
     * @return       java.util.List<java.lang.String>
     * @throws
     */
    @Override
    public List<String> getPermissionIdsByRoles(List<String> roleIds) {

        return sysRolePermissionMapper.getPermissionIdsByRoles(roleIds);
    }
    /**
     * 新增角色的权限
     * 主要逻辑：
     * 先删除角色和菜单权限关联
     * 再新增角色和菜单权限关联
     * @Author:      as
     * @CreateDate:  2019/10/20 15:56
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 15:56
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    @Override
    public void addRolePermission(RolePermissionOperationReqVO vo) {

        Date createTime=new Date();
        List<SysRolePermission> list=new ArrayList<>();
        for (String permissionId:vo.getPermissionIds()){
            SysRolePermission sysRolePermission=new SysRolePermission();
            sysRolePermission.setId(UUID.randomUUID().toString());
            sysRolePermission.setCreateTime(createTime);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermission.setRoleId(vo.getRoleId());
            list.add(sysRolePermission);
        }
        sysRolePermissionMapper.removeByRoleId(vo.getRoleId());
        int count=sysRolePermissionMapper.batchRolePermission(list);
        if (count==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
    /**
     * 根据菜单权限id删除跟角色关联
     * @Author:      as
     * @CreateDate:  2019/10/22 16:43
     * @UpdateUser:
     * @UpdateDate:  2019/10/22 16:43
     * @Version:     0.0.1
     * @param permissionId
     * @return       int
     * @throws
     */
    @Override
    public int removeByPermissionId(String permissionId) {

        return sysRolePermissionMapper.removeByPermissionId(permissionId);
    }
    /**
     * 根据菜单权限id 判读是否有角色跟它关联
     * @Author:      as
     * @CreateDate:  2019/10/25 13:39
     * @UpdateUser:
     * @UpdateDate:  2019/10/25 13:39
     * @Version:     0.0.1
     * @param permissionId
     * @return       java.util.List<java.lang.String>
     * @throws
     */
    @Override
    public List<String> getRoleIds(String permissionId) {

        return sysRolePermissionMapper.getRoleIds(permissionId);
    }
}
