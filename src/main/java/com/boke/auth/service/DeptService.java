package com.boke.auth.service;

import com.boke.auth.entity.SysDept;
import com.boke.auth.entity.SysUser;
import com.boke.auth.vo.req.DeptAddReqVO;
import com.boke.auth.vo.req.DeptPageReqVO;
import com.boke.auth.vo.req.DeptUpdateReqVO;
import com.boke.auth.vo.req.UserPageUserByDeptReqVO;
import com.boke.auth.vo.resp.DeptRespNodeVO;
import com.boke.auth.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: DeptService
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/19 11:38
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 11:38
 * @Version: 0.0.1
 */
public interface DeptService {
    /**
     * 新增组织
     * 主要逻辑：
     * relationCode=父级relationCode+自己的deptCode
     * @Author:      as
     * @CreateDate:  2019/10/19 14:01
     * @UpdateUser:
     * @UpdateDate:  2019/10/19 14:01
     * @Version:     0.0.1
     * @param vo
     * @return       com.boke.auth.entity.SysDept
     * @throws
     */
    SysDept addDept(DeptAddReqVO vo);
    /**
     * 更新 组织
     * 主要逻辑：
     * 更新修改信息；如果父级发生了变化，要统一维护relation_code(包括自己和它所有的子集的子集)
     * @Author:      as
     * @CreateDate:  2019/10/19 15:05
     * @UpdateUser:
     * @UpdateDate:  2019/10/19 15:05
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    void updateDept(DeptUpdateReqVO vo);
    /**
     * 查询组织详情
     * @Author:      as
     * @CreateDate:  2019/10/19 15:14
     * @UpdateUser:
     * @UpdateDate:  2019/10/19 15:14
     * @Version:     0.0.1
     * @param id
     * @return       com.boke.auth.entity.SysDept
     * @throws
     */
    SysDept detailInfo(String id);
    /**
     * 删除操作
     * 这里是逻辑删除 (要和产品确定好删除是否要加条件)
     * @Author:      as
     * @CreateDate:  2019/10/19 15:24
     * @UpdateUser:
     * @UpdateDate:  2019/10/19 15:24
     * @Version:     0.0.1
     * @param id
     * @return       void
     * @throws
     */
    void deleted(String id);
    /**
     * 分页获取组织列表接口
     * @Author:      as
     * @CreateDate:  2019/10/19 16:10
     * @UpdateUser:
     * @UpdateDate:  2019/10/19 16:10
     * @Version:     0.0.1
     * @param vo
     * @return       com.boke.auth.vo.resp.PageVO<com.boke.auth.entity.SysDept>
     * @throws
     */
    PageVO<SysDept> pageInfo(DeptPageReqVO vo);

    /**
     * 递归获取树形 关系的组织信息
     * @Author:      as
     * @CreateDate:  2019/10/19 22:17
     * @UpdateUser:
     * @UpdateDate:  2019/10/19 22:17
     * @Version:     0.0.1
     * @param
     * @return       java.util.List<com.boke.auth.vo.resp.DeptRespNodeVO>
     * @throws
     */
    List<DeptRespNodeVO> deptTreeList();
    /**
     * 分页获取组织下所有用户
     * @Author:      as
     * @CreateDate:  2019/10/20 17:21
     * @UpdateUser:
     * @UpdateDate:  2019/10/20 17:21
     * @Version:     0.0.1
     * @param vo
     * @return       com.boke.auth.vo.resp.PageVO<com.boke.auth.entity.SysUser>
     * @throws
     */
    PageVO<SysUser> pageDeptUserInfo(UserPageUserByDeptReqVO vo);
}
