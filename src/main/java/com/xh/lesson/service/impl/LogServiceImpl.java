package com.xh.lesson.service.impl;

import com.github.pagehelper.PageHelper;
import com.xh.lesson.entity.SysLog;
import com.xh.lesson.mapper.SysLogMapper;
import com.xh.lesson.service.LogService;
import com.xh.lesson.utils.PageUtils;
import com.xh.lesson.vo.req.SysLogPageReqVO;
import com.xh.lesson.vo.resp.PageVO;
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
