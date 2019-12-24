package com.boke.auth.controller;

import com.boke.auth.aop.annotation.LogAnnotation;
import com.boke.auth.service.UserRoleService;
import com.boke.auth.utils.DataResult;
import com.boke.auth.vo.req.UserRoleOperationReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName: UserRolerController
 * TODO:用户角色控制层
 * @Author: as
 * @CreateDate: 2019/10/19 11:38
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 11:38
 * @Version: 0.0.1
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织管理-用户和角色关联接口")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
    @PostMapping("/user/role")
    @ApiOperation(value = "修改或者新增用户角色接口")
    @LogAnnotation(title = "用户和角色关联接口",action = "修改或者新增用户角色")
    public DataResult operationUserRole(@RequestBody @Valid UserRoleOperationReqVO vo){
        userRoleService.addUserRoleInfo(vo);
        return DataResult.success();
    }
}
