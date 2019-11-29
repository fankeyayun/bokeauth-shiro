package com.xh.lesson.config;

import com.xh.lesson.shiro.CustomAccessControlFilter;
import com.xh.lesson.shiro.CustomHashedCredentialsMatcher;
import com.xh.lesson.shiro.CustomRealm;
import com.xh.lesson.shiro.RedisCacheManager;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限配置
 * @ClassName: ShiroConfig
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/9/7 13:31
 * @UpdateUser: as
 * @UpdateDate: 2019/9/7 13:31
 * @Version: 0.0.1
 */
@Configuration
public class ShiroConfig {
    /**
     * 自定义密码 校验
     * @Author:      as
     * @CreateDate:  2019/9/19 10:46
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 10:46
     * @Version:     0.0.1
     * @param
     * @return       com.xh.lesson.shiro.CustomHashedCredentialsMatcher
     * @throws
     */
    @Bean
    public CustomHashedCredentialsMatcher customHashedCredentialsMatcher(){
        return new CustomHashedCredentialsMatcher();
    }
    /**
     * 自定义域
     * @Author:      as
     * @CreateDate:  2019/9/19 10:46
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 10:46
     * @Version:     0.0.1
     * @param
     * @return       com.xh.lesson.shiro.CustomRealm
     * @throws
     */
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm=new CustomRealm();
        customRealm.setCredentialsMatcher(customHashedCredentialsMatcher());
        customRealm.setCacheManager(redisCacheManager());
        return customRealm;
    }
    /**
     * 缓存管理器
     * @Author:      as
     * @CreateDate:  2019/9/19 10:40
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 10:40
     * @Version:     0.0.1
     * @param
     * @return       com.xh.lesson.shiro.RedisCacheManager
     * @throws
     */
    @Bean
    public RedisCacheManager redisCacheManager(){
        return new RedisCacheManager();
    }

    /**
     * 安全管理
     * @Author:      as
     * @CreateDate:  2019/9/19 10:47
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 10:47
     * @Version:     0.0.1
     * @param
     * @return       org.apache.shiro.mgt.SecurityManager
     * @throws
     */
    @Bean
    public SecurityManager securityManager(){

        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        // 禁用Session作为存储策略的实现。
        DefaultSubjectDAO defaultSubjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluatord = (DefaultSessionStorageEvaluator) defaultSubjectDAO
                .getSessionStorageEvaluator();
        defaultSessionStorageEvaluatord.setSessionStorageEnabled(false);
        return securityManager;
    }

    /**
     * shiro过滤器，配置拦截哪些请求
     * @Author:      as
     * @CreateDate:  2019/9/19 10:47
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 10:47
     * @Version:     0.0.1
     * @param securityManager
     * @return       org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @throws
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器限制并发人数,参考博客：
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //用来校验token
        filtersMap.put("token", new CustomAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/sys/user/login", "anon");
        filterChainDefinitionMap.put("/sys/user/token", "anon");
        //放开swagger-ui地址
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/captcha.jpg", "anon");
        filterChainDefinitionMap.put("/","anon");
        filterChainDefinitionMap.put("/csrf","anon");
        filterChainDefinitionMap.put("/**","token,authc");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/sys/user/unLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     * @Author:      as
     * @CreateDate:  2019/9/19 10:50
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 10:50
     * @Version:     0.0.1
     * @param securityManager
     * @return       org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @throws
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


}
