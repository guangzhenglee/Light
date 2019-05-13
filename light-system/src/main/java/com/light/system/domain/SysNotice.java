package com.light.system.domain;

import com.light.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 通知公告 sys_notice
 * author:ligz
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description="通知公告",parent=BaseEntity.class)
public class SysNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="通知主键ID", name="noticeId", example="1")
    private Long noticeId;

    @ApiModelProperty(value="通知标题", name="noticeTitle", example="系统维护提醒")
    private String noticeTitle;

    @ApiModelProperty(value="通知类型", name="noticeType", example="1", allowableValues = "1,2", reference = "1通知，2公告")
    private String noticeType;

    @ApiModelProperty(value="通知内容", name="noticeContent", example="服务于今日凌晨暂时关闭维护")
    private String noticeContent;

    @ApiModelProperty(value="公告状态", name="status", example="0", allowableValues = "0,1", reference = "0正常,1关闭")
    private String status;
}
