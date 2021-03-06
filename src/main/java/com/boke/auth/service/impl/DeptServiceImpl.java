package com.boke.auth.service.impl;

import com.boke.auth.constants.Constant;
import com.boke.auth.entity.SysDept;
import com.boke.auth.entity.SysUser;
import com.boke.auth.exception.BusinessException;
import com.boke.auth.exception.code.BaseResponseCode;
import com.boke.auth.mapper.SysDeptMapper;
import com.boke.auth.service.DeptService;
import com.boke.auth.service.RedisService;
import com.boke.auth.utils.CodeUtil;
import com.boke.auth.utils.PageUtils;
import com.boke.auth.vo.req.DeptAddReqVO;
import com.boke.auth.vo.req.DeptPageReqVO;
import com.boke.auth.vo.req.DeptUpdateReqVO;
import com.boke.auth.vo.req.UserPageUserByDeptReqVO;
import com.boke.auth.vo.resp.DeptRespNodeVO;
import com.boke.auth.vo.resp.PageVO;
import com.github.pagehelper.PageHelper;
import com.boke.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: DeptServiceImpl
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/19 13:38
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 13:38
 * @Version: 0.0.1
 */
@Service
@Slf4j
public class DeptServiceImpl implements DeptService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private UserService userService;
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
    @Override
    public SysDept addDept(DeptAddReqVO vo) {
        String relationCode;
        long result=redisService.incrby(Constant.DEPT_CODE_KEY,1);
        String deptCode= CodeUtil.deptCode(String.valueOf(result),6,"0");
        SysDept parent=sysDeptMapper.selectByPrimaryKey(vo.getPid());
        if(vo.getPid().equals("0")){
            relationCode=deptCode;
        }else if(null==parent) {
            log.error("传入的 pid:{}不合法",vo.getPid());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }else {
            relationCode=parent.getRelationCode()+deptCode;
        }
        SysDept sysDept=new SysDept();
        BeanUtils.copyProperties(vo,sysDept);
        sysDept.setCreateTime(new Date());
        sysDept.setId(UUID.randomUUID().toString());
        sysDept.setDeptNo(deptCode);
        sysDept.setRelationCode(relationCode);
        int count=sysDeptMapper.insertSelective(sysDept);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        return sysDept;
    }
    /**
     * 更新 组织
     * 主要逻辑：
     * 更新修改信息；如果父级发生了变化，要统一维护relation_code(包括自己和它所有的子集的子集)
     * 这个适合多个根目录
     * 主要特殊 子集目录升级为跟目录
     * 根目录降级为子集目录
     * @Author:      as
     * @CreateDate:  2019/10/19 15:05
     * @UpdateUser:
     * @UpdateDate:  2019/10/19 15:05
     * @Version:     0.0.1
     * @param vo
     * @return       void
     * @throws
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(DeptUpdateReqVO vo) {

        SysDept sysDept=sysDeptMapper.selectByPrimaryKey(vo.getId());
        if(null==sysDept){
            log.error("传入 的 id:{}不合法",vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        SysDept update=new SysDept();
        BeanUtils.copyProperties(vo,update);
        update.setUpdateTime(new Date());
        int count=sysDeptMapper.updateByPrimaryKeySelective(update);
        if(count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }

        /**
         * 说明层级发生了变化
         */
        if(!StringUtils.isEmpty(vo.getPid())&&!vo.getPid().equals(sysDept.getPid())){
            SysDept parent=sysDeptMapper.selectByPrimaryKey(vo.getPid());
            if(!vo.getPid().equals("0")&&null==parent){
                log.error("传入 的 pid:{}不合法",vo.getId());
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            SysDept oldParent=sysDeptMapper.selectByPrimaryKey(sysDept.getPid());
            String oldRelationCode;
            String newRelationCode;
            /**
             * 根目录降到其他目录
             */
            if (sysDept.getPid().equals("0")){
                oldRelationCode=sysDept.getDeptNo();
                newRelationCode=parent.getRelationCode()+sysDept.getDeptNo();
            }else if(vo.getPid().equals("0")){//其他目录升级到跟目录
                oldRelationCode=sysDept.getRelationCode();
                newRelationCode=sysDept.getDeptNo();
            }else {
                oldRelationCode=oldParent.getRelationCode();
                newRelationCode=parent.getRelationCode();
            }
            sysDeptMapper.updateRelationCode(oldRelationCode,newRelationCode,sysDept.getRelationCode());
        }
    }
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
    @Override
    public SysDept detailInfo(String id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }
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
    @Override
    public void deleted(String id) {
        SysDept sysDept=new SysDept();
        sysDept.setId(id);
        sysDept.setDeleted(0);
        sysDept.setUpdateTime(new Date());
        int count=sysDeptMapper.updateByPrimaryKeySelective(sysDept);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
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
    @Override
    public PageVO<SysDept> pageInfo(DeptPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysDept> sysDepts = sysDeptMapper.selectAll();
        return PageUtils.getPageVO(sysDepts);
    }
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
    @Override
    public List<DeptRespNodeVO> deptTreeList() {
        List<SysDept>list=sysDeptMapper.selectAll();
        return getTree(list);
    }

    private List<DeptRespNodeVO> getTree(List<SysDept> all){
        List<DeptRespNodeVO> list=new ArrayList<>();
        for(SysDept sysDept:all){
            if(sysDept.getPid().equals("0")){
                DeptRespNodeVO deptTree=new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept,deptTree);
                deptTree.setChildren(getChild(sysDept.getId(),all));
                list.add(deptTree);
            }
        }
        return list;
    }
    private List<DeptRespNodeVO>getChild(String id, List<SysDept> all){
        List<DeptRespNodeVO> list=new ArrayList<>();
        for(SysDept sysDept:all){
            if(sysDept.getPid().equals(id)){
                DeptRespNodeVO deptTree=new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept,deptTree);
                deptTree.setChildren(getChild(sysDept.getId(),all));
                list.add(deptTree);
            }
        }
        return list;
    }
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
    @Override
    public PageVO<SysUser> pageDeptUserInfo(UserPageUserByDeptReqVO vo) {

        SysDept sysDept=sysDeptMapper.selectByPrimaryKey(vo.getDeptId());
        if (null==sysDept){
            log.error("传入 的 id:{}不合法",vo.getDeptId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        List<String> strings = sysDeptMapper.selectChildIds(sysDept.getRelationCode());

        return userService.selectUserInfoByDeptIds(vo.getPageNum(), vo.getPageSize(),strings);
    }

}
