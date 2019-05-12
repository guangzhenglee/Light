package com.light.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.light.common.constant.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体类的基类
 * author:ligz
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="创建者",name="createBy",example="ligz")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="创建时间",name="createTime",example="2019-05-20 13:14:00",dataType="java.util.Date")
    private Date createTime;

    @ApiModelProperty(value="更新者",name="updateBy",example="ligz")
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="更新时间",name="updateTime",example="2019-05-20 13:14:00",dataType="java.util.Date")
    private Date updateTime;

    @ApiModelProperty(value="备注",name="remark")
    private String remark;

    @ApiModelProperty(value = "请求参数", name = "params")
    private transient Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>(Constants.INITIAL_CAPACITY);
        }
        return params;
    }
}
