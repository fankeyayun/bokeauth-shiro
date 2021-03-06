package com.boke.auth.controller;

import com.boke.auth.aop.annotation.LogAnnotation;
import com.boke.auth.entity.SysDept;
import com.boke.auth.entity.SysUser;
import com.boke.auth.service.DeptService;
import com.boke.auth.utils.DataResult;
import com.boke.auth.vo.req.DeptAddReqVO;
import com.boke.auth.vo.req.DeptPageReqVO;
import com.boke.auth.vo.req.DeptUpdateReqVO;
import com.boke.auth.vo.req.UserPageUserByDeptReqVO;
import com.boke.auth.vo.resp.DeptRespNodeVO;
import com.boke.auth.vo.resp.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 机构控制层
 * @ClassName: DeptController
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/19 11:37
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 11:37
 * @Version: 0.0.1
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-机构管理")
public class DeptController {
    @Autowired
    private DeptService deptService;
    @PostMapping("/dept")
    @ApiOperation(value = "新增组织接口")
    @LogAnnotation(title = "机构管理",action = "新增组织")
    @RequiresPermissions("sys:dept:add")
    public DataResult<SysDept> addDept(@RequestBody @Valid DeptAddReqVO vo){
        DataResult<SysDept> result=DataResult.success();
        result.setData(deptService.addDept(vo));
        return result;
    }
    @DeleteMapping("/{id}/dept")
    @ApiOperation(value = "删除组织接口")
    @LogAnnotation(title = "机构管理",action = "删除组织")
    @RequiresPermissions("sys:dept:deleted")
    public DataResult deleted(@PathVariable("id") String id){
        deptService.deleted(id);
        return DataResult.success();
    }
    @PutMapping("/dept")
    @ApiOperation(value = "更新组织信息接口")
    @LogAnnotation(title = "机构管理",action = "更新组织信息")
    @RequiresPermissions("sys:dept:update")
    public DataResult updateDept(@RequestBody @Valid DeptUpdateReqVO vo){
        deptService.updateDept(vo);
        return DataResult.success();
    }
    @GetMapping("/{id}/dept")
    @ApiOperation(value = "查询组织详情接口")
    @LogAnnotation(title = "机构管理",action = "查询组织详情")
    @RequiresPermissions("sys:dept:detail")
    public DataResult<SysDept> detailInfo(@PathVariable("id") String id){
        DataResult<SysDept> result=DataResult.success();
        result.setData(deptService.detailInfo(id));
        return result;
    }
    @PostMapping("/depts")
    @ApiOperation(value = "分页获取组织信息接口")
    @LogAnnotation(title = "机构管理",action = "分页获取组织信息")
    @RequiresPermissions("sys:dept:list")
    public DataResult<PageVO<SysDept>> pageInfo(@RequestBody DeptPageReqVO vo){
        DataResult<PageVO<SysDept>> result=DataResult.success();
        result.setData(deptService.pageInfo(vo));
        return result;
    }
    @GetMapping("/dept/tree")
    @ApiOperation(value = "树型组织列表接口")
    @LogAnnotation(title = "机构管理",action = "树型组织列表")
    public DataResult<List<DeptRespNodeVO>> getTree(){
        DataResult<List<DeptRespNodeVO>> result=DataResult.success();
        result.setData(deptService.deptTreeList());
        return result;
    }

    @PostMapping("/dept/users")
    @ApiOperation(value = "分页获取组织下所有用户接口")
    @LogAnnotation(title = "机构管理",action = "分页获取组织下所有用户")
    @RequiresPermissions("sys:dept:user:list")
    public DataResult<PageVO<SysUser>> pageDeptUserInfos(@RequestBody @Valid UserPageUserByDeptReqVO vo){
        DataResult<PageVO<SysUser>> result=DataResult.success();
        result.setData(deptService.pageDeptUserInfo(vo));
        return result;
    }
}
