package com.xh.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: RolePageReqVO
 * TODO:类文件简单描述
 * @Author: 小霍
 * @CreateDate: 2019/9/20 0:34
 * @UpdateUser: 小霍
 * @UpdateDate: 2019/9/20 0:34
 * @Version: 0.0.1
 */
@Data
public class RolePageReqVO {
    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;
}
