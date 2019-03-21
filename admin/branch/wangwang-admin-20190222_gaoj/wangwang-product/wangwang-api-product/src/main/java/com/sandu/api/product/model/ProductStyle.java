package com.sandu.api.product.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 款式
 * @author Sandu
 */
@Data
public class ProductStyle implements Serializable{
    /** */
    private Long id;

    /** 父类id*/
    private Integer parentId;

    /** 款式名称*/
    private String styleName;

    /** 款式编码*/
    private String styleCode;

    /** 款式长编码*/
    private String longCode;

    /** 是否子节点*/
    private Integer isLeaf;

    /** 排序*/
    private Integer ordering;

    /** 等级*/
    private Integer level;

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