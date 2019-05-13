package com.light.system.domain;

import com.light.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表 sys_role
 * author:ligz
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="角色信息",parent=BaseEntity.class)
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="角色ID", name="roleId", example = "1")
    private Long roleId;

    @ApiModelProperty(value="角色名", name="roleName", example = "管理员")
    private String roleName;

    @ApiModelProperty(value="角色权限", name="roleKey", example = "admin")
    private String roleKey;

    @ApiModelProperty(value="角色排序", name="roleSort", example = "1")
    private String roleSort;

    @ApiModelProperty(value="数据范围", name="dataScope", example = "1", allowableValues = "1,2", reference = "1所有的数据权限，2自定义的数据权限")
    private String dataScope;

    @ApiModelProperty(value="角色状态", name="status", example = "1", allowableValues = "0,1", reference = "0正常,1停用")
    private String status;

    @ApiModelProperty(value="删除标志", name="delFlag", example = "0", allowableValues = "0,2", reference = "0正常,2删除")
    private String delFlag;

    @ApiModelProperty(value="用户是否存在此角色标识", name="flag")
    private boolean flag = false;

    @ApiModelProperty(value="菜单组", name="menuIds", hidden = true)
    private Long[] menuIds;

    @ApiModelProperty(value="部门组", name="deptIds", hidden = true)
    private Long[] deptIds;
}
