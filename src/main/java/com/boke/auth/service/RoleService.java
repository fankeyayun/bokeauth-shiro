package com.boke.auth.service;

import com.boke.auth.entity.SysRole;
import com.boke.auth.vo.req.RoleAddReqVO;
import com.boke.auth.vo.req.RolePageReqVO;
import com.boke.auth.vo.req.RoleUpdateReqVO;
import com.boke.auth.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: RoleService
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/9/19 11:38
 * @UpdateUser: as
 * @UpdateDate: 2019/9/19 11:38
 * @Version: 0.0.1
 */
public interface RoleService {
    /**
     * 新增角色
     * @Author:      as
     * @CreateDate:  2019/9/20 0:28
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 0:28
     * @Version:     0.0.1
     * @param vo
     * @return       com.boke.auth.entity.SysRole
     * @throws
     */
    SysRole addRole(RoleAddReqVO vo);
    /**
     * 更新角色信息
     * @Author:      as
     * @CreateDate:  2019/9/20 0:37
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 0:37
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    void updateRole(RoleUpdateReqVO vo,String accessToken);
    /**
     * 获取角色详情
     * @Author:      as
     * @CreateDate:  2019/9/20 0:37
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 0:37
     * @Version:     0.0.1
     * @param id
     * @return       com.boke.auth.entity.SysRole
     * @throws
     */
    SysRole detailInfo(String id);
    /**
     * 删除角色
     * 主要逻辑：
     * 删除 用户和角色关联数据
     * 删除 角色和权限关联数据
     * @Author:      as
     * @CreateDate:  2019/9/20 10:34
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 10:34
     * @Version:     0.0.1
     * @param id
     * @return       void
     * @throws
     */
    void deletedRole(String id);
    /**
     * 分页获取角色信息列表
     * @Author:      as
     * @CreateDate:  2019/9/20 0:40
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 0:40
     * @Version:     0.0.1
     * @param vo
     * @return       com.boke.auth.vo.resp.PageVO<com.boke.auth.entity.SysRole>
     * @throws
     */
    PageVO<SysRole> pageInfo(RolePageReqVO vo);
    /**
     * 获取用户的所有角色
     * @Author:      as
     * @CreateDate:  2019/9/20 16:38
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 16:38
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<com.boke.auth.entity.SysRole>
     * @throws
     */
    List<SysRole> getRoleInfoByUserId(String userId);
    /**
     * 获取所有角色名称
     * @Author:      as
     * @CreateDate:  2019/9/20 23:13
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 23:13
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<java.lang.String>
     * @throws
     */
    List<String> getRoleNames(String userId);
}
