package com.xh.lesson.controller;

import com.xh.lesson.aop.annotation.LogAnnotation;
import com.xh.lesson.constants.Constant;
import com.xh.lesson.entity.SysUser;
import com.xh.lesson.exception.code.BaseResponseCode;
import com.xh.lesson.service.UserService;
import com.xh.lesson.utils.DataResult;
import com.xh.lesson.utils.JwtTokenUtil;
import com.xh.lesson.utils.TokenSettings;
import com.xh.lesson.vo.req.*;
import com.xh.lesson.vo.resp.LoginRespVO;
import com.xh.lesson.vo.resp.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.crypto.Data;

/**
 * @ClassName: UserController
 * TODO:登录控制器
 * @Author: as
 * @CreateDate: 2019/9/3 18:16
 * @UpdateUser: as
 * @UpdateDate: 2019/9/3 18:16
 * @Version: 0.0.1
 */
@RestController
@Api(tags = "组织模块-用户管理")
@RequestMapping("/sys")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/user/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult<LoginRespVO> login(@RequestBody LoginReqVO vo){
        DataResult<LoginRespVO> result=DataResult.success();
        result.setData(userService.login(vo));
        return result;
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册接口")
    public DataResult<String> register(@RequestBody @Valid RegisterReqVO vo){
        DataResult<String> result=DataResult.success();
        result.setData(userService.register(vo));
        return result;
    }
    @GetMapping("/user/token")
    @ApiOperation(value = "用户刷新token接口")
    @LogAnnotation(title = "用户管理",action = "用户刷新token")
    public DataResult<String> refreshToken(HttpServletRequest request){
        String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        DataResult<String> result=DataResult.success();
        result.setData(userService.refreshToken(refreshToken,accessToken));
        return result;
    }

    @GetMapping("/user/unLogin")
    @ApiOperation(value = "引导客户端去登录")
    public DataResult unLogin(){
        DataResult result= DataResult.getResult(BaseResponseCode.TOKEN_ERROR);
        return result;
    }

    @PutMapping("/user")
    @ApiOperation(value = "更新用户信息接口")
    @LogAnnotation(title = "用户管理",action = "更新用户信息")
    @RequiresPermissions("sys:user:update")
    public DataResult updateUserInfo(@RequestBody @Valid UserUpdateReqVO vo,HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        userService.updateUserInfo(vo,operationId,request.getHeader(Constant.ACCESS_TOKEN));
        return DataResult.success();
    }
    @GetMapping("/{id}/user")
    @ApiOperation(value = "查询用户详情接口")
    @LogAnnotation(title = "用户管理",action = "查询用户详情")
    @RequiresPermissions("sys:user:detail")
    public DataResult<SysUser> detailInfo(@PathVariable("id") String id){
        DataResult<SysUser> result=DataResult.success();
        result.setData(userService.detailInfo(id));
        return result;
    }
    @PostMapping("/users")
    @ApiOperation(value = "分页获取用户列表接口")
    @RequiresPermissions("sys:user:list")
    @LogAnnotation(title = "用户管理",action = "分页获取用户列表")
    public DataResult<PageVO<SysUser>> pageInfo(@RequestBody UserPageReqVO vo){
        DataResult<PageVO<SysUser>> result=DataResult.success();
        result.setData(userService.pageInfo(vo));
        return result;
    }
    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    @RequiresPermissions("sys:user:add")
    @LogAnnotation(title = "用户管理",action = "新增用户")
    public DataResult addUser(@RequestBody @Valid UserAddReqVO vo){
        userService.addUser(vo);
        return DataResult.success();
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "退出接口")
    @LogAnnotation(title = "用户管理",action = "退出")
    public DataResult logout(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
        userService.logout(accessToken,refreshToken);
        return DataResult.success();
    }
}
