package com.xh.lesson.service;

import com.xh.lesson.entity.SysPermission;
import com.xh.lesson.vo.req.PermissionAddReqVO;
import com.xh.lesson.vo.req.PermissionPageReqVO;
import com.xh.lesson.vo.req.PermissionUpdateReqVO;
import com.xh.lesson.vo.resp.PageVO;
import com.xh.lesson.vo.resp.PermissionRespNode;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: PermissionService
 * TODO:类文件简单描述
 * @Author: 小霍
 * @CreateDate: 2019/9/19 11:39
 * @UpdateUser: 小霍
 * @UpdateDate: 2019/9/19 11:39
 * @Version: 0.0.1
 */
public interface PermissionService {
    /**
     * 先查出用户拥有的角色
     * 再去差用户拥有的权限
     * @Author:      小霍
     * @CreateDate:  2019/9/20 11:42
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 11:42
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<com.xh.lesson.entity.SysPermission>
     * @throws
     */
    List<SysPermission> getPermission(String userId);
    /**
     * 新增菜单权限
     * @Author:      小霍
     * @CreateDate:  2019/9/20 12:24
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 12:24
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.entity.SysPermission
     * @throws
     */
    SysPermission addPermission(PermissionAddReqVO vo);
    /**
     * 查询菜单权限详情
     * @Author:      小霍
     * @CreateDate:  2019/9/20 14:05
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 14:05
     * @Version:     0.0.1
     * @param permissionId
     * @return       com.xh.lesson.entity.SysPermission
     * @throws
     */
    SysPermission detailInfo(String permissionId);
    /**
     * 更新菜单权限
     * @Author:      小霍
     * @CreateDate:  2019/9/20 14:04
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 14:04
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    void updatePermission(PermissionUpdateReqVO vo);
    /**
     * 删除菜单权限
     * 先判断是否 有角色关联
     * @Author:      小霍
     * @CreateDate:  2019/9/20 14:04
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 14:04
     * @Version:     0.0.1
     * @param permissionId
     * @return       void
     * @throws
     */
    void deleted(String permissionId);
    /**
     * 分页获取所有菜单权限
     * @Author:      小霍
     * @CreateDate:  2019/9/20 14:03
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 14:03
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.vo.resp.PageVO<com.xh.lesson.entity.SysPermission>
     * @throws
     */
    PageVO<SysPermission> pageInfo(PermissionPageReqVO vo);
    /**
     * 获取所有菜单权限
     * @Author:      小霍
     * @CreateDate:  2019/9/20 14:03
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 14:03
     * @Version:     0.0.1
     * @param
     * @return       java.util.List<com.xh.lesson.entity.SysPermission>
     * @throws
     */
    List<SysPermission> selectAll();
    /**
     * 获取权限标识
     * @Author:      小霍
     * @CreateDate:  2019/9/20 23:13
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 23:13
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<java.lang.String>
     * @throws
     */
    Set<String> getPermissionsByUserId(String userId);
    /**
     * 以树型的形式把用户拥有的菜单权限返回给客户端
     * @Author:      小霍
     * @CreateDate:  2019/9/23 10:53
     * @UpdateUser:
     * @UpdateDate:  2019/9/23 10:53
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<com.xh.lesson.vo.resp.PermissionRespNode>
     * @throws
     */
    List<PermissionRespNode> permissionTreeList(String userId);

}
