package com.light.system.domain;

import com.light.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门表 sys_dept
 * author:ligz
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "部门表", parent = BaseEntity.class)
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门Id", name = "deptId", example = "100")
    private Long deptId;

    @ApiModelProperty(value = "父部门Id", name = "parentId", example = "0")
    private Long parentId;

    @ApiModelProperty(value = "父节点列表", name = "ancestors", example = "0,100")
    private String ancestors;

    @ApiModelProperty(value = "部门名称", name = "deptName", example = "城市智能")
    private String deptName;

    @ApiModelProperty(value = "显示顺序", name = "orderNum", example = "1")
    private String orderNum;

    @ApiModelProperty(value = "部门负责人", name = "leader", example = "小马")
    private String leader;

    @ApiModelProperty(value = "部门电话", name = "phone", example = "18700001111")
    private String phone;

    @ApiModelProperty(value = "部门邮箱", name = "email", example = "ligz_7@163.com")
    private String email;

    @ApiModelProperty(value = "状态标志", name = "status", example = "0", allowableValues = "0,1", reference = "0正常，1停用")
    private String status;

    @ApiModelProperty(value = "删除标志", name = "delFlag", example = "0", allowableValues = "0,2", reference = "0存在，2删除")
    private String delFlag;

    @ApiModelProperty(value = "父部门名称", name = "parentName", example = "航天所")
    private String parentName;
}
