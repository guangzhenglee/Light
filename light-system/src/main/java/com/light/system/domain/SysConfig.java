package com.light.system.domain;

import com.light.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 参数配置表 sys_config
 * author:ligz
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description="参数配置",parent= BaseEntity.class)
public class SysConfig extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数主键", name = "configId", example = "1")
    private Long configId;

    @ApiModelProperty(value = "配置名称", name = "configName", example = "用户管理-账号初始密码")
    private String configName;

    @ApiModelProperty(value = "配置键名", name = "configKey", example = "sys.user.initPassword")
    private String configKey;

    @ApiModelProperty(value = "配置键值", name = "configValue", example = "123456")
    private String configValue;

    @ApiModelProperty(value = "是否内置", name = "configType", example = "Y", allowableValues = "Y,N", reference = "Y是，N否")
    private String configType;
}
