package com.light.system.domain;

import com.light.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型表 sys_dict_type
 * author:ligz
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description="字典类型",parent= BaseEntity.class)
public class SysDictType extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id", name = "dictId", example = "1")
    private Long dictId;

    @ApiModelProperty(value = "字典名称", name = "dictName", example = "用户性别")
    private String dictName;

    @ApiModelProperty(value = "字典类型的标识", name = "dictType", example = "sys_user_sex")
    private String dictType;

    @ApiModelProperty(value = "状态", name = "status", example = "0", allowableValues = "0,1",reference = "0正常，1停用")
    private String status;
}
