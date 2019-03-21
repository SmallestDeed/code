package com.sandu.api.product.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 产品属性
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-04-25 14:21:51.127
 */

@Data
public class ProductAttribute implements Serializable {
    
    /**
     * product_attribute.id
     */
    private Long id;

    /**
     * 产品小分类（产品属性父级）<p>
     * product_attribute.attribute_type_value
     */
    private String attributeTypeValue;

    /**
     * 属性值<p>
     * product_attribute.attribute_id
     */
    private Integer attributeId;

    /**
     * 属性key<p>
     * product_attribute.attribute_key
     */
    private String attributeKey;

    /**
     * 属性名称<p>
     * product_attribute.attribute_name
     */
    private String attributeName;

    /**
     * 属性值value<p>
     * product_attribute.attribute_value_id
     */
    private Integer attributeValueId;

    /**
     * 属性值key<p>
     * product_attribute.attribute_value_key
     */
    private String attributeValueKey;

    /**
     * 属性值名称<p>
     * product_attribute.attribute_value_name
     */
    private String attributeValueName;

    /**
     * 属性值图片<p>
     * product_attribute.attribute_value_pic_ids
     */
    private String attributeValuePicIds;

    /**
     * 产品ID<p>
     * product_attribute.product_id
     */
    private Integer productId;

    /**
     * 系统编码<p>
     * product_attribute.sys_code
     */
    private String sysCode;

    /**
     * 创建者<p>
     * product_attribute.creator
     */
    private String creator;

    /**
     * product_attribute.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * product_attribute.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * product_attribute.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * product_attribute.is_deleted
     */
    private Integer isDeleted;

    /**
     * product_attribute.product_code
     */
    private String productCode;

    /**
     * 字符备用2<p>
     * product_attribute.att2
     */
    private String att2;

    /**
     * 整数备用1<p>
     * product_attribute.numa1
     */
    private Integer numa1;

    /**
     * 整数备用2<p>
     * product_attribute.numa2
     */
    private Integer numa2;

    /**
     * 备注<p>
     * product_attribute.remark
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}