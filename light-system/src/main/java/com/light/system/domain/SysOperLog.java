package com.light.system.domain;

import com.light.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 操作日志记录 sys_oper_log
 * author:ligz
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="操作日志",parent= BaseEntity.class)
public class SysOperLog extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="操作序号", name="operId", example="1")
    private Long operId;

    @ApiModelProperty(value="操作模块", name="title", example="字典类型")
    private String title;

    @ApiModelProperty(value="业务类型", name="businessType", example="0", allowableValues = "range[0,9]", reference="0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Integer businessType;

    @ApiModelProperty(value="请求方法", name="method", example="com.light.web.controller.monitor.SysUserOnlineController.forceLogout()")
    private String method;

    @ApiModelProperty(value="操作类别", name="operatorType", example="0", allowableValues = "0,1,2",reference = "0其他,1后台,2移动端")
    private Integer operatorType;

    @ApiModelProperty(value="操作人", name="operName", example="admin")
    private String operName;

    @ApiModelProperty(value="操作人的部门", name="deptName", example="研发部门")
    private String deptName;

    @ApiModelProperty(value="请求url", name="operUrl", example="/monitor/online/forceLogout")
    private String operUrl;

    @ApiModelProperty(value="操作的ip", name="operIp", example="127.0.0.1")
    private String operIp;

    @ApiModelProperty(value="操作地址", name="operLocation", example="内网IP")
    private String operLocation;

    @ApiModelProperty(value="请求参数", name="operParam")
    private String operParam;

    @ApiModelProperty(value="操作状态", name="status", example="0", allowableValues = "0,1", reference = "0正常,1异常")
    private Integer status;

    @ApiModelProperty(value="错误的消息", name="errorMsg")
    private String errorMsg;

    @ApiModelProperty(value="操作时间", name="operTime",example="2019-05-20 00:00:00",dataType="java.util.Date")
    private Date operTime;


}
