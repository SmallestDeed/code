package com.nork.product.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nork.common.model.Mapper;
import com.nork.product.model.vo.ProductCeilingVO;

public class CategoryProductResult extends Mapper implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	/**
	 * 用于缩放功能，记录被点击的产品的长度，然后返回到列表 searchProduct 
	 */
	private String searchLength;
	
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 商品code
	 */
	private String productCode;
	
	/**
	 * 商品名称
	 */
	private String productName;
	
	/**
	 * 产品风格名称
	 */
	private String proStyleId;
	/**
	 * 产品风格名称
	 */
	private String proStyle;
	
	/**
	 * 产品色调
	 */
	private String colorId;
	/**
	 * 产品色调名称
	 */
	private String color;
	
	/**
	 * 产品规格
	 */
	private String productSpec;
	
	/**
	 * 销售价格
	 */
	private String salePrice;
	
	/**
	 * 销售价格单位
	 */
	private String salePriceValueName;

	/**
	 * 品牌名称
	 */
	private String brandName;

	/**
	 * 产品图片路径
	 */
	private String picPath;
	

	/**
	 * 分类ID
	 */
	private String categoryId;

	/**
	 * 分类名称
	 */
	private String categoryName;
	
	/*大分类*/
	private Integer productTypeValue;
	
	private String productTypeName;

	private String productTypeCode;
	/*小分类*/
	private Integer  productSmallTypeValue;
	
	private String productSmallTypeName;

	private String productSmallTypeCode;
	
	private String productLength;
	
	private String productWidth;

	private String productHeight;

    private Integer collectState;
    //产品使用量
    private Integer productCount;
    //排序字段
    private Integer orderType;
    //当前模型产品ID
    private Integer modelProductId;
    //产品最小高度（天花用到）
  	private String minHeight;
	/** 产品u3d模型文件 **/
    private String productModelPath;
    private String u3dModelPath;

	/** 软装硬装分类 **/
	private String rootType;
	//0、实体墙  1、背景墙 2、门框
	private Integer bgWall;

	/** 空间ID **/
	private Integer spaceCommonId;
	
	/** 材质ID **/
	private String materialPicId;
	
	/** 材质路径 **/
	private String materialPicPath;

	/** parentId **/
	private Integer parentId;

	/** 材质路径s **/
	private String[] materialPicPaths;

	/** 材质配置文件路径 **/
	private String materialConfigPath;

	/** 同类型产品ID **/
	private Integer parentProductId;
	
	/** 规则json **/
	private Map<String,String> rulesMap;
	
	/*品牌id*/
	private Integer brandId;
	/** 模型长度 **/
	private int modelLength;
	/** 模型宽度 **/
	private int modelWidth;
	/** 模型高度 **/
	private int modelHeight;
	/** 模型最小高度 **/
	private int modelMinHeight;
	/** 产品属性 **/
	private Map<String,String> propertyMap;
	/*材质属性*/
	private Integer textureAttrValue; 
	/*铺设方式*/
	private String laymodes;
	/**材质宽*/
	private String textureWidth;
	/**材质高*/
	private String textureHeight;
	
	List<Map<String,String>>attributeList=new ArrayList<Map<String,String>>();
//	/** 床垫白模 **/
//	private CategoryProductResult basicMattress;
//	/** 床上用品白模 **/
//	private CategoryProductResult basicBedding;

	/** 携带得白模产品集合 **/
	private Map<String,CategoryProductResult> basicModelMap;
	
	private Integer productOrdering;
	
	/** 是否是定制 **/
	private Integer isCustomized;
	
	/**  创建时间  **/
	private Date gmtCreate;
	//是否是主产品 1 是 0 否
	private Integer isMainProduct;
	/*判断是否是可拆分材质产品:0:普通产品;1:可拆分材质产品(橱柜)*/
	private Integer isSplit=new Integer(0);
	/*可拆分材质信息*/
	private List<SplitTextureDTO> splitTexturesChoose;
	//色系排序
  	private Integer colorsOrdering;
	/**
	 * 产品色系
	 */
	private String colorsLongCode;
	/**产品多材质信息*/
	private String splitTexturesInfoStr;
	/**单材质产品的材质图片路径*/
	private String resTexturePath;
	/**用来判断软硬装*/
	private String smallTypeAtt1;
	/**样板房产品ID*/
	private String templateProductId;
	/**产品全铺长度*/
	private Integer fullPaveLength;
	/**产品全铺长度*/
	private Integer targetBmProductId;

	/**
	 * 产品标准
	 * 款式ID
	 * 区域款式
	 * 尺寸代码
	 * 天花布局标识
	 */
	private Integer isStandard;
	private Integer styleId;
	private String regionMark;
	private String measureCode;
    private Integer productSmallpoxIdentify;

    //系列ID
    private Integer seriesId = new Integer(0);
    //方案产品posName
    private String posName;

	/**产品型号*/
	private String productModelNumber;

	/**
	 * add by xiaoxc_20180314
	 * 产品天花结果集对象
	 */
	private ProductCeilingVO productCeilingVO;

	/**
	 * add by huangsongbo 2018.6.15
	 * 拼花产品的拼花可选材质ids
	 */
	private String parquetTextureIds;
	
	public String getParquetTextureIds() {
		return parquetTextureIds;
	}

	public void setParquetTextureIds(String parquetTextureIds) {
		this.parquetTextureIds = parquetTextureIds;
	}

	public ProductCeilingVO getProductCeilingVO() {
		return productCeilingVO;
	}

	public void setProductCeilingVO(ProductCeilingVO productCeilingVO) {
		this.productCeilingVO = productCeilingVO;
	}

	public String getProductModelNumber() {
		return productModelNumber;
	}

	public void setProductModelNumber(String productModelNumber) {
		this.productModelNumber = productModelNumber;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public String getSearchLength() {
		return searchLength;
	}

	public void setSearchLength(String searchLength) {
		this.searchLength = searchLength;
	}

	public Integer getProductSmallpoxIdentify() {
		return productSmallpoxIdentify;
	}

	public void setProductSmallpoxIdentify(Integer productSmallpoxIdentify) {
		this.productSmallpoxIdentify = productSmallpoxIdentify;
	}

	public Integer getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(Integer isStandard) {
		this.isStandard = isStandard;
	}

	public Integer getStyleId() {
		return styleId;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}

	public String getRegionMark() {
		return regionMark;
	}

	public void setRegionMark(String regionMark) {
		this.regionMark = regionMark;
	}

	public String getMeasureCode() {
		return measureCode;
	}

	public void setMeasureCode(String measureCode) {
		this.measureCode = measureCode;
	}

	public Integer getTargetBmProductId() {
		return targetBmProductId;
	}

	public void setTargetBmProductId(Integer targetBmProductId) {
		this.targetBmProductId = targetBmProductId;
	}

	public Integer getFullPaveLength() {
		return fullPaveLength;
	}

	public void setFullPaveLength(Integer fullPaveLength) {
		this.fullPaveLength = fullPaveLength;
	}

	public String getTemplateProductId() {
		return templateProductId;
	}

	public void setTemplateProductId(String templateProductId) {
		this.templateProductId = templateProductId;
	}

	public String getSmallTypeAtt1() {
		return smallTypeAtt1;
	}
	public void setSmallTypeAtt1(String smallTypeAtt1) {
		this.smallTypeAtt1 = smallTypeAtt1;
	}
	public String getSplitTexturesInfoStr() {
		return splitTexturesInfoStr;
	}
	public void setSplitTexturesInfoStr(String splitTexturesInfoStr) {
		this.splitTexturesInfoStr = splitTexturesInfoStr;
	}
	public String getResTexturePath() {
		return resTexturePath;
	}
	public void setResTexturePath(String resTexturePath) {
		this.resTexturePath = resTexturePath;
	}
	public String getMinHeight() {
  		return minHeight;
  	}
  	public void setMinHeight(String minHeight) {
  		this.minHeight = minHeight;
  	}
    
	public Integer getModelProductId() {
		return modelProductId;
	}

	public void setModelProductId(Integer modelProductId) {
		this.modelProductId = modelProductId;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getColorsLongCode() {
		return colorsLongCode;
	}
	
	public void setColorsLongCode(String colorsLongCode) {
		this.colorsLongCode = colorsLongCode;
	}
	
	public Integer getColorsOrdering() {
		return colorsOrdering;
	}
	
	public void setColorsOrdering(Integer colorsOrdering) {
		this.colorsOrdering = colorsOrdering;
	}
	
	public String getSalePriceValueName() {
		return salePriceValueName;
	}
	
	public Integer getIsSplit() {
		return isSplit;
	}

	public void setSalePriceValueName(String salePriceValueName) {
		this.salePriceValueName = salePriceValueName;
	}

	public void setIsSplit(Integer isSplit) {
		this.isSplit = isSplit;
	}

	public List<SplitTextureDTO> getSplitTexturesChoose() {
		return splitTexturesChoose;
	}

	public void setSplitTexturesChoose(List<SplitTextureDTO> splitTexturesChoose) {
		this.splitTexturesChoose = splitTexturesChoose;
	}

	public Integer getIsMainProduct() {
		return isMainProduct;
	}

	public void setIsMainProduct(Integer isMainProduct) {
		this.isMainProduct = isMainProduct;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Integer getIsCustomized() {
		return isCustomized;
	}

	public void setIsCustomized(Integer isCustomized) {
		this.isCustomized = isCustomized;
	}

	public Integer getProductOrdering() {
		return productOrdering;
	}

	public void setProductOrdering(Integer productOrdering) {
		this.productOrdering = productOrdering;
	}

	public List<Map<String, String>> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<Map<String, String>> attributeList) {
		this.attributeList = attributeList;
	}

	public String getTextureWidth() {
		return textureWidth;
	}

	public void setTextureWidth(String textureWidth) {
		this.textureWidth = textureWidth;
	}

	public String getTextureHeight() {
		return textureHeight;
	}

	public void setTextureHeight(String textureHeight) {
		this.textureHeight = textureHeight;
	}

	public Integer getTextureAttrValue() {
		return textureAttrValue;
	}

	public void setTextureAttrValue(Integer textureAttrValue) {
		this.textureAttrValue = textureAttrValue;
	}

	public String getLaymodes() {
		return laymodes;
	}

	public void setLaymodes(String laymodes) {
		this.laymodes = laymodes;
	}

	public Integer getBgWall() {
		return bgWall;
	}

	public void setBgWall(Integer bgWall) {
		this.bgWall = bgWall==null?0:bgWall;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getU3dModelPath() {
		return u3dModelPath;
	}

	public void setU3dModelPath(String u3dModelPath) {
		this.u3dModelPath = u3dModelPath;
	}

	public Map<String, String> getRulesMap() {
		return rulesMap;
	}

	public void setRulesMap(Map<String, String> rulesMap) {
		this.rulesMap = rulesMap;
	}
    
	public String getMaterialPicId() {
		return materialPicId;
	}

	public void setMaterialPicId(String materialPicId) {
		this.materialPicId = materialPicId;
	}

	public String getMaterialPicPath() {
		return materialPicPath;
	}

	public void setMaterialPicPath(String materialPicPath) {
		this.materialPicPath = materialPicPath;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductTypeValue() {
		return productTypeValue;
	}

	public void setProductTypeValue(Integer productTypeValue) {
		this.productTypeValue = productTypeValue;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public Integer getProductSmallTypeValue() {
		return productSmallTypeValue;
	}

	public void setProductSmallTypeValue(Integer productSmallTypeValue) {
		this.productSmallTypeValue = productSmallTypeValue;
	}

	public String getProductSmallTypeName() {
		return productSmallTypeName;
	}

	public void setProductSmallTypeName(String productSmallTypeName) {
		this.productSmallTypeName = productSmallTypeName;
	}

	public String getProductSmallTypeCode() {
		return productSmallTypeCode;
	}

	public void setProductSmallTypeCode(String productSmallTypeCode) {
		this.productSmallTypeCode = productSmallTypeCode;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProStyle() {
		return proStyle;
	}

	public void setProStyle(String proStyle) {
		this.proStyle = proStyle;
	}

	public String getProStyleId() {
		return proStyleId;
	}

	public void setProStyleId(String proStyleId) {
		this.proStyleId = proStyleId;
	}

	public String getColorId() {
		return colorId;
	}

	public void setColorId(String colorId) {
		this.colorId = colorId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getProductLength() {
		return productLength;
	}

	public void setProductLength(String productLength) {
		this.productLength = productLength;
	}

	public String getProductWidth() {
		return productWidth;
	}

	public void setProductWidth(String productWidth) {
		this.productWidth = productWidth;
	}

	public String getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(String productHeight) {
		this.productHeight = productHeight;
	}

    public Integer getCollectState() {
        return collectState;
    }

    public void setCollectState(Integer collectState) {
        this.collectState = collectState;
    }

	public String getProductModelPath() {
		return productModelPath;
	}

	public void setProductModelPath(String productModelPath) {
		this.productModelPath = productModelPath;
	}

	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	public Integer getSpaceCommonId() {
		return spaceCommonId;
	}

	public void setSpaceCommonId(Integer spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}

	public String[] getMaterialPicPaths() {
		return materialPicPaths;
	}

	public void setMaterialPicPaths(String[] materialPicPaths) {
		this.materialPicPaths = materialPicPaths;
	}

	public String getMaterialConfigPath() {
		return materialConfigPath;
	}

	public void setMaterialConfigPath(String materialConfigPath) {
		this.materialConfigPath = materialConfigPath;
	}

	public Integer getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(Integer parentProductId) {
		this.parentProductId = parentProductId;
	}

	public int getModelLength() {
		return modelLength;
	}

	public void setModelLength(int modelLength) {
		this.modelLength = modelLength;
	}

	public int getModelWidth() {
		return modelWidth;
	}

	public void setModelWidth(int modelWidth) {
		this.modelWidth = modelWidth;
	}

	public int getModelHeight() {
		return modelHeight;
	}

	public void setModelHeight(int modelHeight) {
		this.modelHeight = modelHeight;
	}

	public int getModelMinHeight() {
		return modelMinHeight;
	}

	public void setModelMinHeight(int modelMinHeight) {
		this.modelMinHeight = modelMinHeight;
	}

	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public Map<String, CategoryProductResult> getBasicModelMap() {
		return basicModelMap;
	}

	public void setBasicModelMap(Map<String, CategoryProductResult> basicModelMap) {
		this.basicModelMap = basicModelMap;
	}
}
