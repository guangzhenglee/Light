package com.light.system.domain;

import com.light.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统访问记录表 sys_login_log
 * author:ligz
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="系统访问记录",parent=BaseEntity.class)
public class SysLoginLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="主键", name="infoId", example="1")
    private Long infoId;

    @ApiModelProperty(value="用户账号", name="loginName", example="admin")
    private String loginName;

    @ApiModelProperty(value="登录状态", name="status", example="0", allowableValues = "0,1", reference = "0成功，1失败")
    private String status;

    @ApiModelProperty(value="登录ip", name="ipAddr", example="127.0.0.1")
    private String ipAddr;

    @ApiModelProperty(value="登陆地点", name="loginLocation", example="内网IP")
    private String loginLocation;

    @ApiModelProperty(value="浏览器", name="browser", example="Chrome")
    private String browser;

    @ApiModelProperty(value="操作系统", name="os", example="Windows 10")
    private String os;

    @ApiModelProperty(value="登录消息", name="msg", example="登录成功")
    private String msg;

    @ApiModelProperty(value="访问时间", name="loginTime", example="2018-12-15 18:03:58", dataType="java.util.Date")
    private Date loginTime;

}
