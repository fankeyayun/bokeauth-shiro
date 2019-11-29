package com.xh.lesson.shiro;

import com.xh.lesson.constants.Constant;
import com.xh.lesson.exception.BusinessException;
import com.xh.lesson.exception.code.BaseResponseCode;
import com.xh.lesson.service.RedisService;
import com.xh.lesson.utils.JwtTokenUtil;
import com.xh.lesson.utils.TokenSettings;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: CustomHashedCredentialsMatcher
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/7 13:42
 * @UpdateUser: as
 * @UpdateDate: 2019/10/7 13:42
 * @Version: 0.0.1
 */
public class CustomHashedCredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private RedisService redisService;
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CustomPasswordToken customPasswordToken= (CustomPasswordToken) token;
        String accessToken = (String) customPasswordToken.getPrincipal();
        String userId=JwtTokenUtil.getUserId(accessToken);
        /**
         * 用户主动退出
         */
        if(redisService.hasKey(Constant.JWT_REFRESH_TOKEN_BLACKLIST+accessToken)){
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        /**
         * 存在这个key 说明接口已经刷新过还没有来得及更换新的 token
         */
        if(redisService.hasKey(Constant.JWT_REFRESH_STATUS+accessToken)){
            return true;
        }
        /**
         * token 已经过期 或者主动去刷新
         */
        if(JwtTokenUtil.isTokenExpired(accessToken)){
            throw new BusinessException(BaseResponseCode.TOKEN_PAST_DUE);
        }
        /**
         * 判断这个登录用户是否要主动去刷新
         *
         */
        if(redisService.hasKey(Constant.JWT_REFRESH_KEY+userId)){
            /**
             * 是否存在刷新的标识
             */
            if(!redisService.hasKey(Constant.JWT_REFRESH_IDENTIFICATION+accessToken)){
                throw new BusinessException(BaseResponseCode.TOKEN_PAST_DUE);
            }
        }
        return true;
    }
}
