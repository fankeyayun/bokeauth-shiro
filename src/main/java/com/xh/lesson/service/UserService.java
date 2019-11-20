package com.xh.lesson.service;

import com.xh.lesson.entity.SysUser;
import com.xh.lesson.vo.req.*;
import com.xh.lesson.vo.resp.LoginRespVO;
import com.xh.lesson.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: UserService
 * TODO:类文件简单描述
 * @Author: 小霍
 * @CreateDate: 2019/9/7 22:55
 * @UpdateUser: 小霍
 * @UpdateDate: 2019/9/7 22:55
 * @Version: 0.0.1
 */
public interface UserService {
    /**
     * 用户注册
     * @Author:      小霍
     * @CreateDate:  2019/9/7 23:02
     * @UpdateUser:
     * @UpdateDate:  2019/9/7 23:02
     * @Version:     0.0.1
     * @param vo
     * @return       java.lang.String
     * @throws
     */
    String register(RegisterReqVO vo);
    /**
     * 用户登录接口
     * @Author:      小霍
     * @CreateDate:  2019/9/8 10:05
     * @UpdateUser:
     * @UpdateDate:  2019/9/8 10:05
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.vo.resp.LoginRespVO
     * @throws
     */
    LoginRespVO login(LoginReqVO vo);

    /**
     * 刷新token
     * @Author:      小霍
     * @CreateDate:  2019/9/8 10:15
     * @UpdateUser:
     * @UpdateDate:  2019/9/8 10:15
     * @Version:     0.0.1
     * @param refreshToken
     * @return       java.lang.String
     * @throws
     */
    String refreshToken(String refreshToken,String accessToken);
    /**
     * 更新用户
     * @Author:      小霍
     * @CreateDate:  2019/9/20 16:57
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 16:57
     * @Version:     0.0.1
     * @param vo
     * @param operationId
     * @return       void
     * @throws
     */
    void updateUserInfo(UserUpdateReqVO vo,String operationId,String accessToken);
    /**
     * 删除用户
     * @Author:      小霍
     * @CreateDate:  2019/9/20 16:57
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 16:57
     * @Version:     0.0.1
     * @param userId
     * @param operationId
     * @return       void
     * @throws
     */
    void deleted(String userId,String operationId);
    /**
     * 分页查询用户信息
     * @Author:      小霍
     * @CreateDate:  2019/9/20 16:56
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 16:56
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.vo.resp.PageVO<com.xh.lesson.entity.SysUser>
     * @throws
     */
    PageVO<SysUser> pageInfo(UserPageReqVO vo);
    /**
     * 查询用户详情
     * @Author:      小霍
     * @CreateDate:  2019/9/20 16:56
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 16:56
     * @Version:     0.0.1
     * @param userId
     * @return       com.xh.lesson.entity.SysUser
     * @throws
     */
    SysUser detailInfo(String userId);
    /**
     * 分页查询组织下的用户
     * @Author:      小霍
     * @CreateDate:  2019/9/20 17:22
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 17:22
     * @Version:     0.0.1
     * @param pageNum
     * @param pageSize
     * @param deptIds
     * @return       com.xh.lesson.vo.resp.PageVO<com.xh.lesson.entity.SysUser>
     * @throws
     */
    PageVO<SysUser> selectUserInfoByDeptIds(int pageNum, int pageSize,List<String> deptIds);
    /**
     * 新增用户
     * @Author:      小霍
     * @CreateDate:  2019/9/22 16:51
     * @UpdateUser:
     * @UpdateDate:  2019/9/22 16:51
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    void addUser(UserAddReqVO vo);
    /**
     * 退出登录
     * @Author:      小霍
     * @CreateDate:  2019/9/22 19:58
     * @UpdateUser:
     * @UpdateDate:  2019/9/22 19:58
     * @Version:     0.0.1
     * @param accessToken
     * @param refreshToken
     * @return       void
     * @throws
     */
    void logout(String accessToken,String refreshToken);
}
