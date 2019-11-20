package com.xh.lesson.service;

import com.xh.lesson.entity.SysDept;
import com.xh.lesson.entity.SysUser;
import com.xh.lesson.vo.req.DeptAddReqVO;
import com.xh.lesson.vo.req.DeptPageReqVO;
import com.xh.lesson.vo.req.DeptUpdateReqVO;
import com.xh.lesson.vo.req.UserPageUserByDeptReqVO;
import com.xh.lesson.vo.resp.DeptRespNodeVO;
import com.xh.lesson.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: DeptService
 * TODO:类文件简单描述
 * @Author: 小霍
 * @CreateDate: 2019/9/19 11:38
 * @UpdateUser: 小霍
 * @UpdateDate: 2019/9/19 11:38
 * @Version: 0.0.1
 */
public interface DeptService {
    /**
     * 新增组织
     * 主要逻辑：
     * relationCode=父级relationCode+自己的deptCode
     * @Author:      小霍
     * @CreateDate:  2019/9/19 14:01
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 14:01
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.entity.SysDept
     * @throws
     */
    SysDept addDept(DeptAddReqVO vo);
    /**
     * 更新 组织
     * 主要逻辑：
     * 更新修改信息；如果父级发生了变化，要统一维护relation_code(包括自己和它所有的子集的子集)
     * @Author:      小霍
     * @CreateDate:  2019/9/19 15:05
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 15:05
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    void updateDept(DeptUpdateReqVO vo);
    /**
     * 查询组织详情
     * @Author:      小霍
     * @CreateDate:  2019/9/19 15:14
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 15:14
     * @Version:     0.0.1
     * @param id
     * @return       com.xh.lesson.entity.SysDept
     * @throws
     */
    SysDept detailInfo(String id);
    /**
     * 删除操作
     * 这里是逻辑删除 (要和产品确定好删除是否要加条件)
     * @Author:      小霍
     * @CreateDate:  2019/9/19 15:24
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 15:24
     * @Version:     0.0.1
     * @param id
     * @return       void
     * @throws
     */
    void deleted(String id);
    /**
     * 分页获取组织列表接口
     * @Author:      小霍
     * @CreateDate:  2019/9/19 16:10
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 16:10
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.vo.resp.PageVO<com.xh.lesson.entity.SysDept>
     * @throws
     */
    PageVO<SysDept> pageInfo(DeptPageReqVO vo);

    /**
     * 递归获取树形 关系的组织信息
     * @Author:      小霍
     * @CreateDate:  2019/9/19 22:17
     * @UpdateUser:
     * @UpdateDate:  2019/9/19 22:17
     * @Version:     0.0.1
     * @param
     * @return       java.util.List<com.xh.lesson.vo.resp.DeptRespNodeVO>
     * @throws
     */
    List<DeptRespNodeVO> deptTreeList();
    /**
     * 分页获取组织下所有用户
     * @Author:      小霍
     * @CreateDate:  2019/9/20 17:21
     * @UpdateUser:
     * @UpdateDate:  2019/9/20 17:21
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.vo.resp.PageVO<com.xh.lesson.entity.SysUser>
     * @throws
     */
    PageVO<SysUser> pageDeptUserInfo(UserPageUserByDeptReqVO vo);
}
