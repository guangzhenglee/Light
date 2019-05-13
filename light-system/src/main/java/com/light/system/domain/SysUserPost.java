package com.light.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户和岗位关联 sys_user_post
 * author:ligz
 */
@Data
@ApiModel(description="用户和岗位关联关系")
public class SysUserPost implements Serializable {
    @ApiModelProperty(value="用户ID",name="userId",example="1")
    private Long userId;

    @ApiModelProperty(value="岗位ID",name="postId",example="1")
    private Long postId;
}
