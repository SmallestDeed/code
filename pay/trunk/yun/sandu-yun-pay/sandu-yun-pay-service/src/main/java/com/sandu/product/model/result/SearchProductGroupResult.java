package com.sandu.product.model.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/28.
 * 搜索产品组接口响应结果实体
 */
public class SearchProductGroupResult implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 产品组ID **/
    private Integer groupId;
    /** 产品组CODE **/
    private String groupCode;
    /** 产品组名称 **/
    private String groupName;
    /** 产品组封面图片地址 **/
    private String picPath;
    /** 产品组位置关系 **/
    private String groupConfig;
    /** 产品组详情（产品列表） **/
    private List<SearchProductGroupDetail> groupProductDetails;
    
    /** 产品组详情（产品列表） **/
    private List<SearchProductGroupDetail> structureProductList;
    
    /** 组合中主产品的ID，方便U3D处理逻辑 **/
    private Integer productId;
    /** 收藏状态 **/
    private Integer collectState;
    /** 组合产品价格 **/
    private Double groupPrice;
    /** 品牌名 **/
    private String brandName;
    private Integer brandId;
    
    public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	private Integer isFavorite;//是否被收藏.0:未收藏,1收藏
    
	public Integer getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Integer isFavorite) {
		this.isFavorite = isFavorite;
	}

	/*组合名,和参数name一样,方便前端*/
	private String productName;
	/*价格*/
	private Double salePrice;
	/*结构id*/
	private Integer productStructureId;
	/*结构name*/
	private String structureName;
	/*结构code*/
	private String structureCode;
	/*结构Config*/
	private String structureConfig;
	
	/**
	 * 组合风格
	 */
	private String styleName;
	/**
	 * 描述
	 */
	private String remark;
	
	/**产品风格*/
	private String productStyleIdInfo;
	
	/**产品风格(list)*/
	private List<String> productStyleInfoList;
	
	private String productStyle;
	
	/** 产品型号 **/
	private String productType;
	
	//配置文件路径
	private String filePath;
	
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductStyleIdInfo() {
		return productStyleIdInfo;
	}

	public void setProductStyleIdInfo(String productStyleIdInfo) {
		this.productStyleIdInfo = productStyleIdInfo;
	}

	public List<String> getProductStyleInfoList() {
		return productStyleInfoList;
	}

	public void setProductStyleInfoList(List<String> productStyleInfoList) {
		this.productStyleInfoList = productStyleInfoList;
	}

	public String getProductStyle() {
		return productStyle;
	}

	public void setProductStyle(String productStyle) {
		this.productStyle = productStyle;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public String getStructureCode() {
		return structureCode;
	}

	public void setStructureCode(String structureCode) {
		this.structureCode = structureCode;
	}

	public String getStructureConfig() {
		return structureConfig;
	}

	public void setStructureConfig(String structureConfig) {
		this.structureConfig = structureConfig;
	}

	public List<SearchProductGroupDetail> getStructureProductList() {
		return structureProductList;
	}

	public void setStructureProductList(
			List<SearchProductGroupDetail> structureProductList) {
		this.structureProductList = structureProductList;
	}

	public Integer getProductStructureId() {
		return productStructureId;
	}

	public void setProductStructureId(Integer productStructureId) {
		this.productStructureId = productStructureId;
	}
	
	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Double getGroupPrice() {
		return groupPrice;
	}

	public void setGroupPrice(Double groupPrice) {
		this.groupPrice = groupPrice;
	}

	public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getGroupConfig() {
        return groupConfig;
    }

    public void setGroupConfig(String groupConfig) {
        this.groupConfig = groupConfig;
    }

    public List<SearchProductGroupDetail> getGroupProductDetails() {
        return groupProductDetails;
    }

    public void setGroupProductDetails(List<SearchProductGroupDetail> groupProductDetails) {
        this.groupProductDetails = groupProductDetails;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCollectState() {
        return collectState;
    }

    public void setCollectState(Integer collectState) {
        this.collectState = collectState;
    }
}
