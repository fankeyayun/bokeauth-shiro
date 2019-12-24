package com.boke.auth.service;

import com.boke.auth.vo.req.UserRoleOperationReqVO;

import java.util.List;

/**
 * @ClassName: UserRoleService
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/19 11:39
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 11:39
 * @Version: 0.0.1
 */
public interface UserRoleService {
    /**
     * 删除用户和角色关联数据
     * @Author:      as
     * @CreateDate:  2019/10/20 10:42
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 10:42
     * @Version:     0.0.1
     * @param roleId
     * @return       int
     * @throws
     */
    int removeByRoleId(String roleId);
    /**
     * 查询用户所拥有的角色id集合
     * @Author:      as
     * @CreateDate:  2019/10/20 14:43
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 14:43
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<java.lang.String>
     * @throws
     */
    List<String> getRoleIdsByUserId(String userId);

    /**
     * 用户新增角色
     * 主要逻辑
     * 先删除原先用户和角色的关联
     * 再添加新的用户和角色关联
     * @Author:      as
     * @CreateDate:  2019/10/20 15:44
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 15:44
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    void addUserRoleInfo(UserRoleOperationReqVO vo);
    /**
     * 删除用户所有角色
     * @Author:      as
     * @CreateDate:  2019/10/20 16:02
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 16:02
     * @Version:     0.0.1
     * @param userId
     * @return       int
     * @throws
     */
    int removeByUserId(String userId);

}
