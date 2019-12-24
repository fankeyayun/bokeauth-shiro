package com.boke.auth.shiro;

import com.boke.auth.service.RedisService;
import com.boke.auth.service.RoleService;
import com.boke.auth.utils.JwtTokenUtil;
import com.boke.auth.constants.Constant;
import com.boke.auth.service.PermissionService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collection;

/**
 * @ClassName: CustomRealm
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/6 18:02
 * @UpdateUser: as
 * @UpdateDate: 2019/10/6 18:02
 * @Version: 0.0.1
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private RedisService redisService;
    @Autowired
    @Lazy
    private PermissionService permissionService;
    @Autowired
    @Lazy
    private RoleService roleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomPasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        String accessToken= (String) SecurityUtils.getSubject().getPrincipal();
        String userId= JwtTokenUtil.getUserId(accessToken);
        log.info("userId={}",userId);
        /**
         * 说明是旧的token所以要读取DB拿最新的角色/权限
         */
        if(redisService.hasKey(Constant.JWT_REFRESH_KEY+userId)){
            authorizationInfo.addRoles(roleService.getRoleNames(userId));
            authorizationInfo.setStringPermissions(permissionService.getPermissionsByUserId(userId));
        }else {
            Claims claimsFromToken = JwtTokenUtil.getClaimsFromToken(accessToken);
            authorizationInfo.addRoles((Collection<String>) claimsFromToken.get(Constant.JWT_ROLES_KEY));
            authorizationInfo.addStringPermissions((Collection<String>) claimsFromToken.get(Constant.JWT_PERMISSIONS_KEY));
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomPasswordToken token= (CustomPasswordToken) authenticationToken;
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(token.getPrincipal(),token.getPrincipal(),getName());
        return simpleAuthenticationInfo;
    }
}
