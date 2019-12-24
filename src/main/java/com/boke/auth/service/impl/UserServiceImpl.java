package com.boke.auth.service.impl;

import com.boke.auth.constants.Constant;
import com.boke.auth.entity.SysUser;
import com.boke.auth.exception.BusinessException;
import com.boke.auth.exception.code.BaseResponseCode;
import com.boke.auth.mapper.SysUserMapper;
import com.boke.auth.service.*;
import com.boke.auth.utils.JwtTokenUtil;
import com.boke.auth.utils.PageUtils;
import com.boke.auth.utils.PasswordUtils;
import com.boke.auth.vo.req.*;
import com.boke.auth.vo.resp.LoginRespVO;
import com.boke.auth.vo.resp.PageVO;
import com.github.pagehelper.PageHelper;
import com.boke.auth.service.*;
import com.boke.auth.vo.req.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UserServiceImpl
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/9/7 22:56
 * @UpdateUser: as
 * @UpdateDate: 2019/9/7 22:56
 * @Version: 0.0.1
 */
@Service
@Slf4j
@Lazy
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleService userRoleService;
    @Override
    public String register(RegisterReqVO vo) {
        SysUser sysUser=new SysUser();
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
        sysUser.setPassword(encode);
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setCreateTime(new Date());
        int i = sysUserMapper.insertSelective(sysUser);
        if (i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        return sysUser.getId();
    }

    @Override
    public LoginRespVO login(LoginReqVO vo) {
        SysUser sysUser=sysUserMapper.getUserInfoByName(vo.getUsername());
        if (null==sysUser){
            throw new BusinessException(BaseResponseCode.NOT_ACCOUNT);
        }
        if (sysUser.getStatus()==2){
            throw new BusinessException(BaseResponseCode.USER_LOCK);
        }
        if(!PasswordUtils.matches(sysUser.getSalt(),vo.getPassword(),sysUser.getPassword())){
            throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
        }
        LoginRespVO respVO=new LoginRespVO();
        BeanUtils.copyProperties(sysUser,respVO);
        Map<String,Object> claims=new HashMap<>();
        claims.put(Constant.JWT_PERMISSIONS_KEY,getPermissionsByUserId(sysUser.getId()));
        claims.put(Constant.JWT_ROLES_KEY,getRolesByUserId(sysUser.getId()));
        claims.put(Constant.JWT_USER_NAME,sysUser.getUsername());
        String access_token= JwtTokenUtil.getAccessToken(sysUser.getId(),claims);
        String refresh_token=JwtTokenUtil.getRefreshToken(sysUser.getId(),claims);
        respVO.setAccessToken(access_token);
        respVO.setRefreshToken(refresh_token);
        respVO.setList(permissionService.permissionTreeList(sysUser.getId()));
        return respVO;
    }
    /**
     * 获取用户的角色
     * 这里先用伪代码代替
     * 后面我们讲到权限管理系统后 再从 DB 读取
     * @Author:      as
     * @CreateDate:  2019/9/8 9:56
     * @UpdateUser:
     * @UpdateDate:  2019/9/8 9:56
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<com.boke.auth.entity.SysRole>
     * @throws
     */
    private List<String> getRolesByUserId(String userId){
       return  roleService.getRoleNames(userId);
    }
    /**
     * 获取用户的权限
     * 这里先用伪代码代替
     * 后面我们讲到权限管理系统后 再从 DB 读取
     * @Author:      as
     * @CreateDate:  2019/9/8 9:56
     * @UpdateUser:
     * @UpdateDate:  2019/9/8 9:56
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<com.boke.auth.entity.SysPermission>
     * @throws
     */
    private Set<String>getPermissionsByUserId(String userId){
        return  permissionService.getPermissionsByUserId(userId);
    }

    @Override
    public String refreshToken(String refreshToken,String accessToken) {
        /**
         * 用户主动退出或者refreshToken 已经过期
         */
        if(redisService.hasKey(Constant.JWT_ACCESS_TOKEN_BLACKLIST+refreshToken)||!JwtTokenUtil.validateToken(refreshToken)){
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        String userId=JwtTokenUtil.getUserId(refreshToken);
        log.info("userId={}",userId);
        SysUser sysUser=sysUserMapper.selectByPrimaryKey(userId);
        if (null==sysUser){
            throw new BusinessException(BaseResponseCode.TOKEN_PARSE_ERROR);
        }
        Map<String,Object> claims=null;
        /**
         * 用户主动去刷新
         * 更新角色/权限信息
         */
        if(redisService.hasKey(Constant.JWT_REFRESH_KEY)){
            claims=new HashMap<>();
            claims.put(Constant.JWT_ROLES_KEY,getRolesByUserId(userId));
            claims.put(Constant.JWT_PERMISSIONS_KEY,getPermissionsByUserId(userId));
        }
        String newAccessToken=JwtTokenUtil.refreshToken(refreshToken,claims);
        /**
         * 把刷新的状态存入redis 60S 防止一个功能一次性请求接口还没来得级使用新的token
         * 如果已经刷新过就不在改动
         */
        redisService.setifAbsen(Constant.JWT_REFRESH_STATUS+accessToken,userId,1,TimeUnit.MINUTES);

        /**
         * 如果是主动去刷新着 redis 标记新的access_token
         * 过期时间为 key=Constant.JWT_REFRESH_KEY+userId 的剩余过期时间
         */
        if(redisService.hasKey(Constant.JWT_REFRESH_KEY+userId)){
            redisService.set(Constant.JWT_REFRESH_IDENTIFICATION+newAccessToken,userId,redisService.getExpire(Constant.JWT_REFRESH_KEY+userId,TimeUnit.MILLISECONDS),TimeUnit.MILLISECONDS);
        }
        return newAccessToken;
    }
    /**
     * 更新用户
     * @Author:      as
     * @CreateDate:  2019/9/20 16:57
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 16:57
     * @Version:     0.0.1
     * @param vo
     * @param operationId
      * @param accessToken
     * @return       void
     * @throws
     */
    @Override
    public void updateUserInfo(UserUpdateReqVO vo, String operationId, String accessToken) {

        SysUser sysUser=sysUserMapper.selectByPrimaryKey(vo.getId());
        if (null==sysUser){
            log.error("传入 的 id:{}不合法",vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        SysUser update=new SysUser();
        BeanUtils.copyProperties(vo,update);
        update.setUpdateTime(new Date());
        update.setUpdateId(operationId);
       int count= sysUserMapper.updateByPrimaryKeySelective(update);
       if (count!=1){
           throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
       }
        if(null!=vo.getRoleIds()&&!vo.getRoleIds().isEmpty()){
            userRoleService.removeByUserId(vo.getId());
            UserRoleOperationReqVO reqVO=new UserRoleOperationReqVO();
            reqVO.setUserId(sysUser.getId());
            reqVO.setRoleIds(vo.getRoleIds());
            userRoleService.addUserRoleInfo(reqVO);
            /**
             * 修改了 菜单权限 主动去刷新token
             * 过期时间为token剩余的过期时间
             */

            redisService.set(Constant.JWT_REFRESH_KEY+sysUser.getId(),sysUser.getId(),JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);

        }
    }
    /**
     * 删除用户
     * @Author:      as
     * @CreateDate:  2019/9/20 16:57
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 16:57
     * @Version:     0.0.1
     * @param userId
     * @param operationId
     * @return       void
     * @throws
     */
    @Override
    public void deleted(String userId,String operationId) {

        SysUser sysUser=new SysUser();
        sysUser.setId(userId);
        sysUser.setUpdateId(operationId);
        sysUser.setUpdateTime(new Date());
        int count=sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if (count==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
    /**
     * 分页查询用户信息
     * @Author:      as
     * @CreateDate:  2019/9/20 16:56
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 16:56
     * @Version:     0.0.1
     * @param vo
     * @return       com.boke.auth.vo.resp.PageVO<com.boke.auth.entity.SysUser>
     * @throws
     */
    @Override
    public PageVO<SysUser> pageInfo(UserPageReqVO vo) {

        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysUser> sysUsers = sysUserMapper.selectAll();
        return PageUtils.getPageVO(sysUsers);
    }
    /**
     * 查询用户详情
     * @Author:      as
     * @CreateDate:  2019/9/20 16:56
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 16:56
     * @Version:     0.0.1
     * @param userId
     * @return       com.boke.auth.entity.SysUser
     * @throws
     */
    @Override
    public SysUser detailInfo(String userId) {

        return sysUserMapper.selectByPrimaryKey(userId);
    }
    /**
     * 分页查询组织下的用户
     * @Author:      as
     * @CreateDate:  2019/9/20 17:22
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 17:22
     * @Version:     0.0.1
     * @param pageNum
     * @param pageSize
     * @param deptIds
     * @return       com.boke.auth.vo.resp.PageVO<com.boke.auth.entity.SysUser>
     * @throws
     */
    @Override
    public PageVO<SysUser> selectUserInfoByDeptIds(int pageNum, int pageSize,List<String> deptIds) {

        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> list=sysUserMapper.selectUserInfoByDeptIds(deptIds);
        return PageUtils.getPageVO(list);
    }
    /**
     * 新增用户
     * @Author:      as
     * @CreateDate:  2019/9/22 16:51
     * @UpdateUser:
     * @UpdateDate:  2019/9/22 16:51
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    @Override
    public void addUser(UserAddReqVO vo) {
        SysUser sysUser=new SysUser();
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
        sysUser.setPassword(encode);
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setCreateTime(new Date());
        int i = sysUserMapper.insertSelective(sysUser);
        if (i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        if(null!=vo.getRoleIds()&&!vo.getRoleIds().isEmpty()){
            UserRoleOperationReqVO reqVO=new UserRoleOperationReqVO();
            reqVO.setUserId(sysUser.getId());
            reqVO.setRoleIds(vo.getRoleIds());
            userRoleService.addUserRoleInfo(reqVO);
        }
    }
    /**
     * 退出登录
     * @Author:      as
     * @CreateDate:  2019/9/22 19:58
     * @UpdateUser:
     * @UpdateDate:  2019/9/22 19:58
     * @Version:     0.0.1
     * @param accessToken
     * @param refreshToken
     * @return       void
     * @throws
     */
    @Override
    public void logout(String accessToken, String refreshToken) {
        if(StringUtils.isEmpty(accessToken)||StringUtils.isEmpty(refreshToken)){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.getPrincipals());
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        String userId=JwtTokenUtil.getUserId(accessToken);
        /**
         * 把token 加入黑名单 禁止再登录
         */
        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST+accessToken,userId,JwtTokenUtil.getRemainingTime(accessToken),TimeUnit.MILLISECONDS);
        /**
         * 把 refreshToken 加入黑名单 禁止再拿来刷新token
         */
        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST+refreshToken,userId,JwtTokenUtil.getRemainingTime(refreshToken),TimeUnit.MILLISECONDS);
    }
}
