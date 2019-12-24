package com.boke.auth.service.impl;

import com.boke.auth.constants.Constant;
import com.boke.auth.entity.SysRole;
import com.boke.auth.exception.BusinessException;
import com.boke.auth.exception.code.BaseResponseCode;
import com.boke.auth.mapper.SysRoleMapper;
import com.boke.auth.mapper.SysUserRoleMapper;
import com.boke.auth.service.RedisService;
import com.boke.auth.service.RolePermissionService;
import com.boke.auth.service.RoleService;
import com.boke.auth.service.UserRoleService;
import com.boke.auth.utils.PageUtils;
import com.boke.auth.utils.TokenSettings;
import com.boke.auth.vo.req.RoleAddReqVO;
import com.boke.auth.vo.req.RolePageReqVO;
import com.boke.auth.vo.req.RolePermissionOperationReqVO;
import com.boke.auth.vo.req.RoleUpdateReqVO;
import com.boke.auth.vo.resp.PageVO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RoleServiceImpl
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/19 11:40
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 11:40
 * @Version: 0.0.1
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TokenSettings tokenSettings;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    /**
     * 新增角色
     * @Author:      as
     * @CreateDate:  2019/10/20 0:28
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 0:28
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.entity.SysRole
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole addRole(RoleAddReqVO vo) {

        SysRole sysRole=new SysRole();
        BeanUtils.copyProperties(vo,sysRole);
        sysRole.setId(UUID.randomUUID().toString());
        sysRole.setCreateTime(new Date());
        int count= sysRoleMapper.insertSelective(sysRole);
        if(count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        if(null!=vo.getPermissions()&&!vo.getPermissions().isEmpty()){
            RolePermissionOperationReqVO reqVO=new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
        }

        return sysRole;
    }
    /**
     * 更新角色信息
     * @Author:      as
     * @CreateDate:  2019/10/20 0:37
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 0:37
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(RoleUpdateReqVO vo, String accessToken) {
        SysRole sysRole=sysRoleMapper.selectByPrimaryKey(vo.getId());
        if (null==sysRole){
            log.error("传入 的 id:{}不合法",vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        SysRole update=new SysRole();
        BeanUtils.copyProperties(vo,update);
//        BeanUtils.copyProperties(vo,sysRole);
        update.setUpdateTime(new Date());
        int count=sysRoleMapper.updateByPrimaryKeySelective(update);
        if(count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        if(null!=vo.getPermissions()&&!vo.getPermissions().isEmpty()){
            rolePermissionService.removeByRoleId(sysRole.getId());
            RolePermissionOperationReqVO reqVO=new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);

            List<String> userIds=sysUserRoleMapper.getInfoByUserIdByRoleId(vo.getId());
            /**
             * 修改了角色关联的菜单权限后  要主动去刷新token
             * 因为用户所拥有的菜单权限是通过角色去关联的
             * 所以要把跟这个角色关联的用户 都要重新刷新token
             */
            if(!userIds.isEmpty()){
                for (String userId:userIds){
                    redisService.set(Constant.JWT_REFRESH_KEY +userId,userId,tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
                }
            }

        }

    }
    /**
     * 获取角色详情
     * @Author:      as
     * @CreateDate:  2019/10/20 0:37
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 0:37
     * @Version:     0.0.1
     * @param id
     * @return       com.xh.lesson.entity.SysRole
     * @throws
     */
    @Override
    public SysRole detailInfo(String id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }
    /**
     * 删除角色
     * 主要逻辑：
     * 删除 用户和角色关联数据
     * 删除 角色和权限关联数据
     * @Author:      as
     * @CreateDate:  2019/10/20 10:34
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 10:34
     * @Version:     0.0.1
     * @param id
     * @return       void
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletedRole(String id) {
        SysRole sysRole=new SysRole();
        sysRole.setId(id);
        sysRole.setUpdateTime(new Date());
        sysRole.setDeleted(0);
        int count=sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        List<String> userIds=sysUserRoleMapper.getInfoByUserIdByRoleId(id);
        rolePermissionService.removeByRoleId(id);
        userRoleService.removeByRoleId(id);
        /**
         * 刪除角色后  要主动去刷新跟該角色有关联用户的token
         * 因为用户所拥有的菜单权限是通过角色去关联的
         * 所以要把跟这个角色关联的用户 都要重新刷新token
         */
        if(!userIds.isEmpty()){
            for (String userId:userIds){
                redisService.set(Constant.JWT_REFRESH_KEY +userId,userId,tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
            }
        }
    }
    /**
     * 分页获取角色信息列表
     * @Author:      as
     * @CreateDate:  2019/10/20 0:40
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 0:40
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.vo.resp.PageVO<com.xh.lesson.entity.SysRole>
     * @throws
     */
    @Override
    public PageVO<SysRole> pageInfo(RolePageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysRole> sysRoles =sysRoleMapper.selectAll();
        return PageUtils.getPageVO(sysRoles);
    }
    /**
     * 获取用户的所有角色
     * @Author:      as
     * @CreateDate:  2019/10/20 16:38
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 16:38
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<com.xh.lesson.entity.SysRole>
     * @throws
     */
    @Override
    public List<SysRole> getRoleInfoByUserId(String userId) {

        List<String> roleIds=userRoleService.getRoleIdsByUserId(userId);
        if (roleIds.isEmpty()){
            return null;
        }
        return sysRoleMapper.getRoleInfoByIds(roleIds);
    }
    /**
     * 获取所有角色名称
     * @Author:      as
     * @CreateDate:  2019/10/20 23:13
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 23:13
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<java.lang.String>
     * @throws
     */
    @Override
    public List<String> getRoleNames(String userId) {

        List<SysRole> sysRoles=getRoleInfoByUserId(userId);
        if (null==sysRoles||sysRoles.isEmpty()){
            return null;
        }
        List<String> list=new ArrayList<>();
        for (SysRole sysRole:sysRoles){
            list.add(sysRole.getName());
        }
        return list;
    }
}
