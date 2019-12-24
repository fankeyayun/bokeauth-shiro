package com.boke.auth.service.impl;

import com.boke.auth.entity.SysUserRole;
import com.boke.auth.exception.BusinessException;
import com.boke.auth.exception.code.BaseResponseCode;
import com.boke.auth.mapper.SysUserRoleMapper;
import com.boke.auth.service.UserRoleService;
import com.boke.auth.vo.req.UserRoleOperationReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: UserRoleServiceImpl
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/19 11:42
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 11:42
 * @Version: 0.0.1
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
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
    @Override
    public int removeByRoleId(String roleId) {
        return sysUserRoleMapper.removeByRoleId(roleId);
    }
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
    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        return sysUserRoleMapper.getRoleIdsByUserId(userId);
    }


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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserRoleInfo(UserRoleOperationReqVO vo) {
        Date createTime=new Date();
        List<SysUserRole> list=new ArrayList<>();
        for (String roleId:vo.getRoleIds()){
            SysUserRole sysUserRole=new SysUserRole();
            sysUserRole.setId(UUID.randomUUID().toString());
            sysUserRole.setCreateTime(createTime);
            sysUserRole.setUserId(vo.getUserId());
            sysUserRole.setRoleId(roleId);
            list.add(sysUserRole);
        }
        sysUserRoleMapper.removeByUserId(vo.getUserId());
        int count=sysUserRoleMapper.batchUserRole(list);
        if (count==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
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
    @Override
    public int removeByUserId(String userId) {

        return sysUserRoleMapper.removeByUserId(userId);
    }
}
