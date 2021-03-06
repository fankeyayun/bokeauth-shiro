package com.boke.auth.service;

import com.boke.auth.entity.SysLog;
import com.boke.auth.vo.req.SysLogPageReqVO;
import com.boke.auth.vo.resp.PageVO;

/**
 * @ClassName: LogService
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/23 16:17
 * @UpdateUser: as
 * @UpdateDate: 2019/10/23 16:17
 * @Version: 0.0.1
 */
public interface LogService {
    /**
     * 分页获取系统操作日志
     * @Author:      as
     * @CreateDate:  2019/10/23 16:21
     * @UpdateUser:
     * @UpdateDate:  2019/10/23 16:21
     * @Version:     0.0.1
     * @param vo
     * @return       com.boke.auth.vo.resp.PageVO<com.boke.auth.entity.SysLog>
     * @throws
     */
    PageVO<SysLog> pageInfo(SysLogPageReqVO vo);
}
