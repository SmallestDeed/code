package com.sandu.api.base.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseProductStyle implements Serializable {
    private Long id;

    private Integer pid;

    private String name;

    private String code;

    private String longCode;

    private Integer value;

    private Integer isLeaf;

    private Integer level;

    private String sysCode;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String att1;

    private String att2;

    private Integer numa1;

    private Integer numa2;

    private String remark;
}