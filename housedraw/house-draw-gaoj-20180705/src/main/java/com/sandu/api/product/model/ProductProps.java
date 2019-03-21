package com.sandu.api.product.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 产品属性表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-04-25 14:21:51.144
 */

@Data
public class ProductProps implements Serializable {
    
    /**
     * product_props.id
     */
    private Long id;

    /**
     * 属性CODE<p>
     * product_props.code
     */
    private String code;

    /**
     * 属性长CODE<p>
     * product_props.long_code
     */
    private String longCode;

    /**
     * 属性名称<p>
     * product_props.name
     */
    private String name;

    /**
     * 属性值<p>
     * product_props.prop_value
     */
    private String propValue;

    /**
     * 图片<p>
     * product_props.pic_path
     */
    private Integer picPath;

    /**
     * 父级ID<p>
     * product_props.pid
     */
    private Integer pid;

    /**
     * 类型<p>
     * product_props.type
     */
    private Integer type;

    /**
     * 等级<p>
     * product_props.level
     */
    private Integer level;

    /**
     * 是否子级<p>
     * product_props.is_leaf
     */
    private Integer isLeaf;

    /**
     * 排序<p>
     * product_props.ordering
     */
    private Integer ordering;

    /**
     * 系统编码<p>
     * product_props.sys_code
     */
    private String sysCode;

    /**
     * 创建者<p>
     * product_props.creator
     */
    private String creator;

    /**
     * product_props.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * product_props.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * product_props.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * product_props.is_deleted
     */
    private Integer isDeleted;

    /**
     * 字符备用1<p>
     * product_props.att1
     */
    private String att1;

    /**
     * 字符备用2<p>
     * product_props.att2
     */
    private String att2;

    /**
     * 整数备用1<p>
     * product_props.numa1
     */
    private Integer numa1;

    /**
     * 整数备用2<p>
     * product_props.numa2
     */
    private Integer numa2;

    /**
     * 备注<p>
     * product_props.remark
     */
    private String remark;

    /**
     * 用途：（排序、过滤）<p>
     * product_props.filter_order
     */
    private String filterOrder;

    /**
     * 属性序号<p>
     * product_props.sequence_number
     */
    private Integer sequenceNumber;

    /**
     * 属性长序号<p>
     * product_props.long_numbers
     */
    private String longNumbers;

    private static final long serialVersionUID = 1L;
}