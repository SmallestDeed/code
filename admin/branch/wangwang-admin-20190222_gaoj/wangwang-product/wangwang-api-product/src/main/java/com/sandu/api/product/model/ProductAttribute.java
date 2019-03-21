package com.sandu.api.product.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品属性表
 *
 * @author Sandu
 * @date 2017/12/19
 */
@Data
public class ProductAttribute  implements Serializable {
    /** */
    private Long id;

    /** 产品小分类（产品属性父级）*/
    private String attributeTypeValue;

    /** 属性值*/
    private Integer attributeId;

    /** 属性key*/
    private String attributeKey;

    /** 属性名称*/
    private String attributeName;

    /** 属性值value*/
    private Integer attributeValueId;

    /** 属性值key*/
    private String attributeValueKey;

    /** 属性值名称*/
    private String attributeValueName;

    /** 属性值图片*/
    private String attributeValuePicIds;

    /** 产品ID*/
    private Integer productId;

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

    /** */
    private String productCode;

    /** 字符备用2*/
    private String att2;


    /** 整数备用1*/
    private Integer numa1;

    /** 整数备用2*/
    private Integer numa2;

    /** 备注*/
    private String remark;
}
