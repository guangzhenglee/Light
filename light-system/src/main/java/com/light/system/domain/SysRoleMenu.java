package com.light.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色和菜单关联 sys_role_menu
 * author:ligz
 */
@Data
@ApiModel(description="角色和菜单关联关系")
public class SysRoleMenu implements Serializable {
    @ApiModelProperty(value="角色ID", name="roleId", example = "1")
    private Long roleId;

    @ApiModelProperty(value="菜单ID", name="menuId", example = "1")
    private Long menuId;
}
