package com.boke.auth.controller;

import com.boke.auth.aop.annotation.LogAnnotation;
import com.boke.auth.entity.SysRole;
import com.boke.auth.service.RoleService;
import com.boke.auth.utils.DataResult;
import com.boke.auth.vo.req.RoleAddReqVO;
import com.boke.auth.vo.req.RolePageReqVO;
import com.boke.auth.vo.req.RoleUpdateReqVO;
import com.boke.auth.vo.resp.PageVO;
import com.boke.auth.constants.Constant;
import com.boke.auth.vo.req.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @ClassName: RoleController
 * TODO:角色控制层
 * @Author: as
 * @CreateDate: 2019/9/19 11:37
 * @UpdateUser: as
 * @UpdateDate: 2019/9/19 11:37
 * @Version: 0.0.1
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-角色管理")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping("/role")
    @ApiOperation(value = "新增角色接口")
    @LogAnnotation(title = "角色管理",action = "新增角色")
    @RequiresPermissions("sys:role:add")
    public DataResult<SysRole> addRole(@RequestBody @Valid RoleAddReqVO vo){
        DataResult<SysRole> result=DataResult.success();
        result.setData(roleService.addRole(vo));
        return result;
    }
    @DeleteMapping("/{id}/role")
    @ApiOperation(value = "删除角色接口")
    @LogAnnotation(title = "角色管理",action = "删除角色")
    @RequiresPermissions("sys:role:deleted")
    public DataResult deleted(@PathVariable("id") String id){
        roleService.deletedRole(id);
        return DataResult.success();
    }
    @PutMapping("/role")
    @ApiOperation(value = "更新角色信息接口")
    @LogAnnotation(title = "角色管理",action = "更新角色信息")
    @RequiresPermissions("sys:role:update")
    public DataResult updateDept(@RequestBody @Valid RoleUpdateReqVO vo, HttpServletRequest request){
        roleService.updateRole(vo,request.getHeader(Constant.ACCESS_TOKEN));
        return DataResult.success();
    }
    @GetMapping("/{id}/role")
    @ApiOperation(value = "查询角色详情接口")
    @LogAnnotation(title = "角色管理",action = "查询角色详情")
    @RequiresPermissions("sys:role:detail")
    public DataResult<SysRole> detailInfo(@PathVariable("id") String id){
        DataResult<SysRole> result=DataResult.success();
        result.setData(roleService.detailInfo(id));
        return result;
    }
    @PostMapping("/roles")
    @ApiOperation(value = "分页获取角色信息接口")
    @LogAnnotation(title = "角色管理",action = "分页获取角色信息")
    @RequiresPermissions("sys:role:list")
    public DataResult<PageVO<SysRole>> pageInfo(@RequestBody RolePageReqVO vo){
        DataResult<PageVO<SysRole>> result=DataResult.success();
        result.setData(roleService.pageInfo(vo));
        return result;
    }

}
