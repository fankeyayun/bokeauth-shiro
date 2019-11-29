package com.xh.lesson.service;

import com.xh.lesson.vo.req.RolePermissionOperationReqVO;

import java.util.List;

/**
 * @ClassName: RolePermissionService
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/19 11:39
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 11:39
 * @Version: 0.0.1
 */
public interface RolePermissionService {
    /**
     * 批量删除角色和权限关联数据
     * @Author:      as
     * @CreateDate:  2019/10/20 10:41
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 10:41
     * @Version:     0.0.1
     * @param roleId
     * @return       int
     * @throws
     */
    int removeByRoleId(String roleId);
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
    List<String> getPermissionIdsByRoles(List<String> roleIds);
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
    void addRolePermission(RolePermissionOperationReqVO vo);
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
    int removeByPermissionId(String permissionId);
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
    List<String> getRoleIds(String permissionId);
}
