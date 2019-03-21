package com.sandu.api.company.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 */
@Data
public class CompanyCategoryRel implements Serializable{
    /** */
    private Long id;

    /** 企业id*/
    private Integer companyId;

    /** 分类id*/
    private Integer categoryId;

    /** 是否显示(1是0否)*/
    private Integer displayStatus;

    /** 系统编码*/
    private String sysCode;

    /** 创建者*/
    private String creator;

    /** 创建时间*/
    private Date gmtCreate;

    /** 修改人*/
    private String modifier;

    /** 修改时间*/
    private Date gmtModified;

    /** 是否删除*/
    private Integer isDeleted;

    /** 字符备用1*/
    private String att1;

    /** 字符备用2*/
    private String att2;

    /** 整数备用1*/
    private Integer numa1;

    /** 整数备用2*/
    private Integer numa2;

    /** 备注*/
    private String remark;
}
