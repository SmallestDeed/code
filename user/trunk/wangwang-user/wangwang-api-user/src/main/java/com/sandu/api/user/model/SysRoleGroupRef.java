package com.sandu.api.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRoleGroupRef implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 角色id
     **/
    private Long roleId;
    /**
     * 角色组id
     **/
    private Long roleGroupId;
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
}
