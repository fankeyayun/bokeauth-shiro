package com.boke.auth.shiro;

import com.boke.auth.service.RedisService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: RedisCacheManager
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/6 17:53
 * @UpdateUser: as
 * @UpdateDate: 2019/10/6 17:53
 * @Version: 0.0.1
 */
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisService redisService;


    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new RedisCache(name, redisService);
    }

}