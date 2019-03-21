package com.sandu.product.model;

import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/12/23.
 */
@Data
public class ProductCostDetail extends Mapper implements Serializable {

    /**
     * 产品ID
     **/
    private int productId;
    private Integer userId;
    private Integer houseId;
    private Integer productCount;
    private String costTypeCode;
    private Integer secrecy;

    private Integer companyId;
	/**
     * 产品名称
     **/
    private String productName;
    /**
     * 产品图片路径
     **/
    private String productPicPath;
    
/*    产品挂节点*/
    private String posIndexPath;
    
    public String getPosIndexPath() {
		return posIndexPath;
	}

	public void setPosIndexPath(String posIndexPath) {
		this.posIndexPath = posIndexPath;
	}

	/**
     * 产品原图路径
     **/
    private String productOriginalPicPath;
    /**
     * 品牌名称
     **/
    private String brandName;
    /**
     * 产品单价
     **/
    private BigDecimal unitPrice;
    /**
     * 产品数量
     **/
    private Integer count;
    /**
     * 总价
     **/
    private BigDecimal totalPrice;
    /**
     * 产品型号
     **/
    private String productModelNumber;
    /**
     * 产品规格
     **/
    private String productSpec;
    /**
     * 产品描述
     **/
    private String productDesc;
    /**
     * 产品价格单位
     **/
    private String productUnit;
    /*是否被收藏,0未被收藏，1被收藏*/
    private Integer isFavorite;

    private Integer designPlanId;

    private Integer houseType;
    private Integer spaceCommonId;
    private Integer isStandard;
    private Integer styleId;
    private String smallTypeValue;
    private String productTypeValue;
    private Integer planProductId;
    private String measureCode;
    private String regionMark;
    private Integer isMainProduct;
    private String categoryCode;

    private String smallCategoryCode;
    private String productCode;
    private String sourcePlanGroupId;
    private Integer sourceProductGroupId;
    private String sourceGroupProductUniqueId;
    private Integer sourceGroupProductId;
    private String sourceGroupProductCode;
    /**
     * 是否做了材质替换(0:否;1:是)
     * add by yangz
     * 2018年1月17日23:33:02
     */
	private Integer isReplaceTexture;
	
	//关联样板房id
	private Integer designTempletId;
	//产品分类id
	private Integer proCategoryId;
	//产品分类长编码
	private String proCategoryLongCode;
	private String productSmallTypeValue;//产品类别value···小类
	private String sourceSplitTexturesChooseInfo;//默认材质信息
	private Integer structureId;//结构id
	/** 用这个值来判断bgwall应该赋什么值 */
	private String valuekey;
	//产品分类小类valuekey值
	private String typeValueKey;

	private String typeValue;
	//品牌id
	private Integer brandId;
	//产品是否保密
	private Integer secrecyFlag;
	//产品平台关联表的产品分类字符串
	private String styleIdStr;

}
