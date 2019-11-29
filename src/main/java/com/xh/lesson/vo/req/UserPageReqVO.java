package com.xh.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: UserPageReqVO
 * TODO:类文件简单描述
 * @Author: sa
 * @CreateDate: 2019/10/20 16:45
 * @UpdateUser: as
 * @UpdateDate: 2019/10/20 16:45
 * @Version: 0.0.1
 */
@Data
public class UserPageReqVO {
    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;
}
