package com.xh.lesson;

import com.xh.lesson.mapper.SysUserRoleMapper;
import com.xh.lesson.service.PermissionService;
import com.xh.lesson.service.RedisService;
import com.xh.lesson.service.RoleService;
import com.xh.lesson.utils.JwtTokenUtil;
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
