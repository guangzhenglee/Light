package com.light.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 树结构实体类
 * author:ligz
 */
@Data
public class Ztree implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 父节点ID
     */
    private Long pId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点标题
     */
    private String title;

    /**
     * 节点可选
     */
    private boolean checked = false;

    /**
     * 节点可展开
     */
    private boolean open = false;

    /**
     * 是否能勾选
     */
    private boolean nocheck = false;
}
