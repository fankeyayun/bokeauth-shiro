package com.boke.auth.service.impl;

import com.boke.auth.entity.SysPermission;
import com.boke.auth.exception.BusinessException;
import com.boke.auth.exception.code.BaseResponseCode;
import com.boke.auth.mapper.SysPermissionMapper;
import com.boke.auth.service.RedisService;
import com.boke.auth.service.RolePermissionService;
import com.boke.auth.service.UserRoleService;
import com.boke.auth.utils.PageUtils;
import com.boke.auth.vo.req.PermissionAddReqVO;
import com.boke.auth.vo.req.PermissionPageReqVO;
import com.boke.auth.vo.req.PermissionUpdateReqVO;
import com.boke.auth.vo.resp.PageVO;
import com.boke.auth.vo.resp.PermissionRespNode;
import com.github.pagehelper.PageHelper;
import com.boke.auth.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ClassName: PermissionServiceImpl
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/19 11:40
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 11:40
 * @Version: 0.0.1
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    /**
     * 根据用户查询拥有的权限
     * 先查出用户拥有的角色
     * 再去差用户拥有的权限
     * 也可以多表关联查询
     * @Author:      as
     * @CreateDate:  2019/10/20 11:42
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 11:42
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<com.xh.lesson.entity.SysPermission>
     * @throws
     */
    @Override
    public List<SysPermission> getPermission(String userId) {
        List<String> roleIds = userRoleService.getRoleIdsByUserId(userId);
        if(roleIds.isEmpty()){
            return null;
        }
        List<String> permissionIds= rolePermissionService.getPermissionIdsByRoles(roleIds);
        if (permissionIds.isEmpty()){
            return null;
        }
        List<SysPermission> result=sysPermissionMapper.selectInfoByIds(permissionIds);
        return result;
    }
    /**
     * 新增菜单权限
     * @Author:      as
     * @CreateDate:  2019/10/20 12:24
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 12:24
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.entity.SysPermission
     * @throws
     */
    @Override
    public SysPermission addPermission(PermissionAddReqVO vo) {
        SysPermission sysPermission=new SysPermission();
        BeanUtils.copyProperties(vo,sysPermission);
        sysPermission.setId(UUID.randomUUID().toString());
        sysPermission.setCreateTime(new Date());
        int count=sysPermissionMapper.insertSelective(sysPermission);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        return sysPermission;
    }
    /**
     * 查询菜单权限详情
     * @Author:      as
     * @CreateDate:  2019/10/20 14:05
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 14:05
     * @Version:     0.0.1
     * @param permissionId
     * @return       com.xh.lesson.entity.SysPermission
     * @throws
     */
    @Override
    public SysPermission detailInfo(String permissionId) {

        return sysPermissionMapper.selectByPrimaryKey(permissionId);
    }
    /**
     * 更新菜单权限
     * @Author:      as
     * @CreateDate:  2019/10/20 14:04
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 14:04
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    @Override
    public void updatePermission(PermissionUpdateReqVO vo) {

        SysPermission sysPermission=sysPermissionMapper.selectByPrimaryKey(vo.getId());
        if (null==sysPermission){
            log.error("传入 的 id:{}不合法",vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        SysPermission update=new SysPermission();
        BeanUtils.copyProperties(vo,update);
        update.setUpdateTime(new Date());
//        BeanUtils.copyProperties(vo,sysPermission);
        int count=sysPermissionMapper.updateByPrimaryKeySelective(update);
        if(count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
    /**
     * 删除菜单权限
     * 先判断是否 有角色关联
     * @Author:      as
     * @CreateDate:  2019/10/20 14:04
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 14:04
     * @Version:     0.0.1
     * @param permissionId
     * @return       void
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleted(String permissionId) {
        List<String> list= rolePermissionService.getRoleIds(permissionId);
        if(!list.isEmpty()){
            throw new BusinessException(BaseResponseCode.ROLE_PERMISSION_RELATION);
        }
        SysPermission sysPermission=new SysPermission();
        sysPermission.setId(permissionId);
        sysPermission.setDeleted(0);
        sysPermission.setUpdateTime(new Date());
        int count=sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
        if(count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
    /**
     * 分页获取所有菜单权限
     * @Author:      as
     * @CreateDate:  2019/10/20 14:03
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 14:03
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.vo.resp.PageVO<com.xh.lesson.entity.SysPermission>
     * @throws
     */
    @Override
    public PageVO<SysPermission> pageInfo(PermissionPageReqVO vo) {

        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysPermission> list=selectAll();
        return PageUtils.getPageVO(list);
    }
    /**
     * 获取所有菜单权限
     * @Author:      as
     * @CreateDate:  2019/10/20 14:03
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 14:03
     * @Version:     0.0.1
     * @param
     * @return       java.util.List<com.xh.lesson.entity.SysPermission>
     * @throws
     */
    @Override
    public List<SysPermission> selectAll() {
        return sysPermissionMapper.selectAll();
    }
    /**
     * 获取权限标识
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
    public Set<String> getPermissionsByUserId(String userId) {

        List<SysPermission> list=getPermission(userId);
        Set<String> permissions=new HashSet<>();
        if (null==list||list.isEmpty()){
            return null;
        }
        for (SysPermission sysPermission:list){
            if(!StringUtils.isEmpty(sysPermission.getPerms())){
                permissions.add(sysPermission.getPerms());
            }

        }
        return permissions;
    }
    /**
     * 以树型的形式把用户拥有的菜单权限返回给客户端
     * @Author:      as
     * @CreateDate:  2019/10/23 10:53
     * @UpdateUser:
     * @UpdateDate:  2019/10/23 10:53
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<com.xh.lesson.vo.resp.PermissionRespNode>
     * @throws
     */
    @Override
    public List<PermissionRespNode> permissionTreeList(String userId) {

        List<SysPermission> list=getPermission(userId);
        return getTree(list);
    }

    private List<PermissionRespNode> getTree(List<SysPermission> all){
        List<PermissionRespNode> list=new ArrayList<>();
        for(SysPermission sysPermission:all){
            if(sysPermission.getPid().equals("0")){
                PermissionRespNode permissionRespNode=new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission,permissionRespNode);
                permissionRespNode.setChildren(getChild(sysPermission.getId(),all));
                list.add(permissionRespNode);
            }
        }
        return list;
    }
    private List<PermissionRespNode>getChild(String id,List<SysPermission> all){
        List<PermissionRespNode> list=new ArrayList<>();
        for(SysPermission sysPermission:all){
            if(sysPermission.getPid().equals(id)){
                PermissionRespNode permissionRespNode=new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission,permissionRespNode);
                permissionRespNode.setChildren(getChild(sysPermission.getId(),all));
                list.add(permissionRespNode);
            }
        }
        return list;
    }
}
