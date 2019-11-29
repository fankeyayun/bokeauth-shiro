package com.xh.lesson.utils;


import org.springframework.stereotype.Component;

/**
 * @ClassName: StaticContextInitializer
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/26 10:07
 * @UpdateUser: as
 * @UpdateDate: 2019/10/26 10:07
 * @Version: 0.0.1
 */
@Component
public class StaticInitializerUtil {
   private TokenSettings tokenSettings;

    public StaticInitializerUtil(TokenSettings tokenSettings) {
        JwtTokenUtil.setTokenSettings(tokenSettings);
    }
}
