package com.boke.auth.controller;

import com.boke.auth.aop.annotation.LogAnnotation;
import com.boke.auth.entity.SysLog;
import com.boke.auth.utils.DataResult;
import com.boke.auth.vo.req.SysLogPageReqVO;
import com.boke.auth.vo.resp.PageVO;
import com.boke.auth.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;

/**
 * @ClassName: SysLogController
 * @Author: as
 * @CreateDate: 2019/10/23 16:15
 * @UpdateUser: as
 * @UpdateDate: 2019/10/23 16:15
 * @Version: 0.0.1
 */
@RequestMapping("/sys")
@Api(tags = "系统模块-系统操作日志管理")
@RestController
public class SysLogController {

    @Autowired
    private LogService logService;
    @PostMapping("/logs")
    @ApiOperation(value = "分页查询系统操作日志接口")
    @LogAnnotation(title = "系统操作日志管理",action = "分页查询系统操作日志")
    @RequiresPermissions("sys:log:list")
    public DataResult<PageVO<SysLog>> pageInfo(@RequestBody SysLogPageReqVO vo){
        PageVO<SysLog> sysLogPageVO = logService.pageInfo(vo);
        DataResult<PageVO<SysLog>> result=DataResult.success();
        result.setData(sysLogPageVO);
        return result;
    }
}
