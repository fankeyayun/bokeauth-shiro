package com.xh.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: SysLogPageReqVO
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/9/23 16:17
 * @UpdateUser: as
 * @UpdateDate: 2019/9/23 16:17
 * @Version: 0.0.1
 */
@Data
public class SysLogPageReqVO {
    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;
}
