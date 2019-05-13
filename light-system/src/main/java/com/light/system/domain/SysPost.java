package com.light.system.domain;

import com.light.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位表 sys_post
 * author:ligz
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="岗位信息",parent=BaseEntity.class)
public class SysPost extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="岗位序号", name="postId", example = "1")
    private Long postId;

    @ApiModelProperty(value="岗位编码", name="postCode", example = "hr")
    private String postCode;

    @ApiModelProperty(value="岗位名称", name="postName", example = "人事")
    private String postName;

    @ApiModelProperty(value="岗位排序", name="postSort", example = "1")
    private String postSort;

    @ApiModelProperty(value="状态", name="status", example = "0", allowableValues = "0,1", reference = "0正常，1停用")
    private String status;

    @ApiModelProperty(value="用户是否存在此岗位标识", name="flag")
    private boolean flag = false;
}
