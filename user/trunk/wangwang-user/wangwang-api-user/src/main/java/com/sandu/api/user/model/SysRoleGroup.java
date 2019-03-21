package com.sandu.api.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRoleGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 角色组编码
     **/
    private String code;
    /**
     * 角色组名称
     **/
    private String name;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;
    /**
     * 备注
     **/
    private String remark;
}
