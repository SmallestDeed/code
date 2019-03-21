package com.sandu.api.basesupplydemand.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SupplyDemandCategory implements Serializable {
    private Integer id;

    private Integer pid;

    private String name;

    private Integer isLeaf;

    private Integer level;

    private Integer ordering;

    private Integer picId;

    private String sysCode;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String remark;

    private Integer treeType;

}