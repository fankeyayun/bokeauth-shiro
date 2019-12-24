package com.boke.auth;

import com.boke.auth.mapper.SysUserRoleMapper;
import com.boke.auth.service.RedisService;
import com.boke.auth.service.RoleService;
import com.boke.auth.utils.JwtTokenUtil;
import com.boke.auth.service.PermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyFrameApplicationTests {

    @Autowired
    private RedisService redisService;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Test
    public void contextLoads() {
        System.out.println(JwtTokenUtil.generateToken("d","v",null,84858,null));
    }

}
