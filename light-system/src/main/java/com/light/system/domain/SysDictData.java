package com.light.system.domain;

import com.light.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据字典 sys_dict_data
 * author:ligz
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="数据字典",parent=BaseEntity.class)
public class SysDictData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典主键", name = "dictCode", example = "1")
    private Long dictCode;

    @ApiModelProperty(value = "字典排序", name = "dictSort", example = "1")
    private Long dictSort;

    @ApiModelProperty(value = "字典标签", name = "dictLabel", example = "男")
    private String dictLabel;

    @ApiModelProperty(value = "字典键值", name = "dictValue", example = "0")
    private String dictValue;

    @ApiModelProperty(value = "字典类型,对应的是SysDictType表", name = "dictType", example = "sys_user_sex")
    private String dictType;

    @ApiModelProperty(value = "css样式", name = "cssClass")
    private String cssClass;

    @ApiModelProperty(value = "表格样式", name = "listClass")
    private String listClass;

    @ApiModelProperty(value = "是否默认", name = "isDefault", example = "N", allowableValues = "Y,N", reference = "Y是,N否")
    private String isDefault;

    @ApiModelProperty(value = "状态", name = "status", example = "0", allowableValues = "0, 1", reference = "0正常,1停用")
    private String status;


}
