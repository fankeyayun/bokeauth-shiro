package com.boke.auth.service.impl;

import com.boke.auth.entity.SysLog;
import com.boke.auth.mapper.SysLogMapper;
import com.boke.auth.utils.PageUtils;
import com.boke.auth.vo.req.SysLogPageReqVO;
import com.boke.auth.vo.resp.PageVO;
import com.github.pagehelper.PageHelper;
import com.boke.auth.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: LogServiceImpl
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/23 16:18
 * @UpdateUser: as
 * @UpdateDate: 2019/10/23 16:18
 * @Version: 0.0.1
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    /**
     * 分页获取系统操作日志
     * @Author:      as
     * @CreateDate:  2019/10/23 16:21
     * @UpdateUser:
     * @UpdateDate:  2019/10/23 16:21
     * @Version:     0.0.1
     * @param vo
     * @return       com.xh.lesson.vo.resp.PageVO<com.xh.lesson.entity.SysLog>
     * @throws
     */
    @Override
    public PageVO<SysLog> pageInfo(SysLogPageReqVO vo) {

        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysLog> sysLogs = sysLogMapper.selectAll();
        return PageUtils.getPageVO(sysLogs);
    }
}
