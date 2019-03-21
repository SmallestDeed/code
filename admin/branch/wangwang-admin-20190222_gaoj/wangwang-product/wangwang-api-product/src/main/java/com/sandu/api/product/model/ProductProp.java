package com.sandu.api.product.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品-属性表
 *
 * @author Sandu
 * @date 2017/12/18
 */
@Data
public class ProductProp  implements Serializable {
    public static final Integer PROP_LEVEL_ZERO = 0;
    public static final Integer PROP_LEVEL_FIRST = 1;
    public static final Integer PROP_LEVEL_SECOND = 2;
    public static final Integer PROP_LEVEL_THIRD = 3;
    public static final Integer PROP_LEVEL_FOURTH = 4;
    public static final Integer PROP_LEVEL_FIFTH = 5;
    public static final Integer PROP_LEVEL_SIX = 6;
    /** */
    private Long id;

    /** 属性CODE*/
    private String code;

    /** 属性长CODE*/
    private String longCode;

    /** 属性名称*/
    private String name;

    /** 属性值*/
    private String propValue;

    /** 图片*/
    private Integer picPath;

    /** 父级ID*/
    private Integer pid;

    /** 类型*/
    private Integer type;

    /** 等级*/
    private Integer level;

    /** 是否子级*/
    private Integer isLeaf;

    /** 排序*/
    private Integer ordering;

    /** 系统编码*/
    private String sysCode;

    /** 创建者*/
    private String creator;

    /** */
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

    /** 用途：（排序、过滤）*/
    private String filterOrder;

    /** 属性序号*/
    private Integer sequenceNumber;

    /** 属性长序号*/
    private String longNumbers;

    /** 属性是否显示*/
    private Integer standardFlag;
}
