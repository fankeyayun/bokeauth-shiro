package com.boke.auth.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: DeptRespNodeVO
 * TODO:类文件简单描述
 * @Author: as
 * @CreateDate: 2019/10/19 22:01
 * @UpdateUser: as
 * @UpdateDate: 2019/10/19 22:01
 * @Version: 0.0.1
 */
@Data
public class DeptRespNodeVO {
    @ApiModelProperty(value = "组织id")
    private String id;

    @ApiModelProperty(value = "组织编码")
    private String deptNo;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "组织父级id")
    private String pid;

    @ApiModelProperty(value = "组织状态")
    private Integer status;

    @ApiModelProperty(value = "组织关系id")
    private String relationCode;

    @ApiModelProperty(value = "子集")
    private List<?> children;
}
