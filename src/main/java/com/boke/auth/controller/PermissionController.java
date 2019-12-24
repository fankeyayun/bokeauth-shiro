package com.boke.auth.controller;

import com.boke.auth.aop.annotation.LogAnnotation;
import com.boke.auth.entity.SysPermission;
import com.boke.auth.utils.DataResult;
import com.boke.auth.vo.req.PermissionAddReqVO;
import com.boke.auth.vo.req.PermissionPageReqVO;
import com.boke.auth.vo.req.PermissionUpdateReqVO;
import com.boke.auth.vo.resp.PageVO;
import com.boke.auth.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 权限控制层
 * @ClassName: PermissionController
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/20 14:05
 * @UpdateUser: as
 * @UpdateDate: 2019/10/20 14:05
 * @Version: 0.0.1
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-菜单权限管理")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @PostMapping("/permission")
    @ApiOperation(value = "新增菜单权限接口")
    @LogAnnotation(title = "菜单权限管理",action = "新增菜单权限")
    @RequiresPermissions("sys:permission:add")
    public DataResult<SysPermission> addPermission(@RequestBody @Valid PermissionAddReqVO vo){
        DataResult<SysPermission> result=DataResult.success();
        result.setData(permissionService.addPermission(vo));
        return result;
    }
    @DeleteMapping("/{id}/permission")
    @ApiOperation(value = "删除菜单权限接口")
    @LogAnnotation(title = "菜单权限管理",action = "删除菜单权限")
    @RequiresPermissions("sys:permission:deleted")
    public DataResult deleted(@PathVariable("id") String id){
        DataResult result=DataResult.success();
        permissionService.deleted(id);
        return result;
    }
    @PutMapping("/permission")
    @ApiOperation(value = "更新菜单权限接口")
    @LogAnnotation(title = "菜单权限管理",action = "更新菜单权限")
    @RequiresPermissions("sys:permission:update")
    public DataResult updatePermission(@RequestBody @Valid PermissionUpdateReqVO vo){
        DataResult result=DataResult.success();
        permissionService.updatePermission(vo);
        return result;
    }
    @GetMapping("/{id}/permission")
    @ApiOperation(value = "查询菜单权限接口")
    @LogAnnotation(title = "菜单权限管理",action = "查询菜单权限")
    @RequiresPermissions("sys:permission:detail")
    public DataResult<SysPermission> detailInfo(@PathVariable("id") String id){
        DataResult<SysPermission> result=DataResult.success();
        result.setData(permissionService.detailInfo(id));
        return result;
    }

    @PostMapping("/permissions")
    @ApiOperation(value = "分页查询菜单权限接口")
    @LogAnnotation(title = "菜单权限管理",action = "分页查询菜单权限")
    @RequiresPermissions("sys:permission:list")
    public DataResult<PageVO<SysPermission>> pageInfo(@RequestBody PermissionPageReqVO vo){
        DataResult<PageVO<SysPermission>> result=DataResult.success();
        result.setData(permissionService.pageInfo(vo));
        return result;

    }
}
