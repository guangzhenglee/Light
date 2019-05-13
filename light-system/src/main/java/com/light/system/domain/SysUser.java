package com.light.system.domain;

import com.light.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

/**
 * 用户 sys_user
 * author:ligz
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="用户信息",parent=BaseEntity.class)
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="用户序号",name="userId",example="1")
    private Long userId;

    @ApiModelProperty(value="部门ID",name="deptId",example="1")
    private Long deptId;

    @ApiModelProperty(value="部门父ID",name="parentId",example="1")
    private Long parentId;

    @ApiModelProperty(value="角色ID",name="roleId",example="1")
    private Long roleId;

    @ApiModelProperty(value="登录名称",name="loginName",example="admin")
    private String loginName;

    @ApiModelProperty(value="用户名称",name="userName",example="系统管理员")
    private String userName;

    @ApiModelProperty(value="用户邮箱",name="email",example="ligz@qq.com")
    private String email;

    @ApiModelProperty(value="手机号码",name="phonenumber",example="18700001111")
    private String phonenumber;

    @ApiModelProperty(value="用户性别",name="sex",example="0",allowableValues = "0,1,2",reference="0=男,1=女,2=未知")
    private String sex;

    @ApiModelProperty(value="用户头像",name="avatar")
    private String avatar;

    @ApiModelProperty(value="密码",name="password",example="123456")
    private String password;

    @ApiModelProperty(value="盐加密",name="salt",example="111111")
    private String salt;

    @ApiModelProperty(value="帐号状态",name="status",example="0",allowableValues = "0,1",reference="0=正常,1=停用")
    private String status;

    @ApiModelProperty(value="删除标志",name="delFlag",example="0=正常,2=删除")
    private String delFlag;

    @ApiModelProperty(value="最后登陆IP",name="loginIp",example="127.0.0.1")
    private String loginIp;

    @ApiModelProperty(value="最后登陆时间",name="loginDate",example="2018-12-15 18:03:58",dataType="java.util.Date")
    private Date loginDate;

    @ApiModelProperty(value = "部门信息",hidden = true)
    private SysDept dept;

    @ApiModelProperty(value = "角色组",hidden = true)
    private List<SysRole> roles;

    @ApiModelProperty(value = "角色组",hidden = true)
    private Long[] roleIds;

    @ApiModelProperty(value = "岗位组",hidden = true)
    private Long[] postIds;

    @ApiIgnore
    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    @ApiIgnore
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public SysDept getDept() {
        return dept == null?new SysDept() : dept;
    }


}
