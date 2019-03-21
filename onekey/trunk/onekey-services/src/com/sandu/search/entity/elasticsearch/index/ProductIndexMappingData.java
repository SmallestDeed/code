package com.sandu.search.entity.elasticsearch.index;

import com.sandu.search.entity.elasticsearch.po.metadate.ProductPlatformRelPo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 分类产品索引对象
 *
 * @date 20171212
 * @auth pengxuangang
 */
public class ProductIndexMappingData implements Serializable {

    private static final long serialVersionUID = 1947663178318930925L;

    /******************************** 产品基本属性字段 **********************************/
    //产品ID
    private Integer id;
    //产品类型ID
    private Integer productCategoryId;
    //产品编码
    private String productCode;
    //产品名称
    private String productName;
    //产品品牌ID
    private Integer productBrandId;
    //产品品牌名
    private String productBrandName;
    //产品风格ID
    private List<Integer> productStyleList;
    //产品规格
    private String productSpecification;
    //产品颜色
    private Integer productColorId;
    //产品长度
    private Integer productLength;
    //产品宽度
    private Integer productWidth;
    //产品高度
    private Integer productHeight;
    //销售价格
    private Double productSalePrice;
    //销售价格值
    private Integer productSalePriceValue;
    //产品图片ID
    private Integer productPicId;
    //产品图片路径
    private String productPicPath;
    //产品图片列表
    private List<Integer> productPicList;
    //产品图片路径列表
    private List<String> productPicPathList;
    //产品模型ID
    private Integer productModelId;
    //产品描述
    private String productDesc;
    //系统编码
    private String productSystemCode;
    //创建时间
    private Date productCreateDate;
    //修改时间
    private Date productModifyDate;
    //上架时间
    private Date productPutawayDate;
    //产品材质ID列表
    private List<String> productMaterialList;
    //产品大类
    private Integer productTypeValue;
    //产品小类
    private Integer productTypeSmallValue;
    //发布状态(0:未上架,1:已上架,2:测试中,3:已发布,4:已下架)
    private Integer productPutawayState;
    //产品设计者ID
    private Integer productDesignerId;
    //产品系列ID
    private Integer productSeriesId;
    //产品型号
    private String productModelNumber;
    //产品尺寸
    private String productMeasurementCode;
    //产品天花布局标识
    private List<String> productCeilingLayoutIdenList;
    //产品地面布局标识
    private String productGroundLayoutIden;
    //产品全铺长度(白膜)
    private Integer productFullPaveLength;
    //产品是否保密(0不保密，1保密',default:0)
    private Integer secrecyFlag;
    //天花随机拼花标识(0:非随机拼花, 1:随机拼花)
    private Integer ceilingRandomPatchFlowerFlag;
    //数据是否删除(0:未删除, 1:已删除)
    private Integer dataIsDelete;
    // 产品款式id
    private Integer styleModelId;
    // 硬装产品新增存的白模id
    private String bmIds;
    // 墙体分类
    /*private String wallType;*/
    // 多材质信息
    private String splitTexturesInfo;

    /******************************** 产品分类字段 **********************************/
    //一级分类ID
    private Integer firstLevelCategoryId;
    //一级分类名
    private String firstLevelCategoryName;
    //二级分类ID
    private Integer secondLevelCategoryId;
    //二级分类名
    private String secondLevelCategoryName;
    //三级分类ID
    private Integer thirdLevelCategoryId;
    //三级分类名
    private String thirdLevelCategoryName;
    //四级分类ID
    private Integer fourthLevelCategoryId;
    //四级分类名
    private String fourthLevelCategoryName;
    //五级分类ID
    private Integer fifthLevelCategoryId;
    //五级分类名
    private String fifthLevelCategoryName;
    //每级对象
    private List<String> allLevelCategoryName;
    //产品分类编码
    private String productCategoryCode;
    //产品分类长编码列表
    private List<String> productCategoryLongCodeList;
    //分类父节点
    private Integer parentCategoryId;
    //分类名称
    private String categoryName;
    //分类级别
    private Integer categoryLevel;
    //分类排序
    private Integer categoryOrder;
    //分类系统编码
    private String categorySystemCode;
    //产品所有分类ID
    private List<Integer> allProductCategoryId;
    //产品所有分类名
    private List<String> allProductCategoryName;

    /******************************** 产品材质字段 **********************************/
    private List<String> productMaterialNameList;


    /******************************** 产品风格质字段 **********************************/
    //风格名
    private List<String> styleNameList;

    /******************************** 产品组合字段 **********************************/

    //产品组合ID列表
    private List<Integer> productGroupIdList;
    //产品组合名字列表
    private List<Integer> productGroupNameList;
    //产品组合Code列表
    private List<Integer> productGroupCodeList;

    /******************************** 产品公司字段 **********************************/
    //公司ID
    private Integer companyId;
    //公司编码
    private String companyCode;
    //公司行业
    private Integer companyIndustry;

    /******************************** 产品全平台过滤字段 **********************************/
    //平台编码列表
    private List<String> platformCodeList;
    //2B-移动端
    private ProductPlatformRelPo platformProductToBMobile;
    //2B-PC
    private ProductPlatformRelPo platformProductToBPc;
    //品牌2C-网站
    private ProductPlatformRelPo platformProductToCSite;
    //2C-移动端
    private ProductPlatformRelPo platformProductToCMobile;
    //三度-后台管理
    private ProductPlatformRelPo platformProductSanduManager;
    //户型绘制工具
    private ProductPlatformRelPo platformProductHouseDraw;
    //商家管理后台
    private ProductPlatformRelPo platformProductMerchantsManager;
    //测试
    private ProductPlatformRelPo platformProductTest;
    //U3D转换插件
    private ProductPlatformRelPo platformProductU3dPlugin;
    //小程序
    private ProductPlatformRelPo platformProductMiniProgram;

    /******************************** 产品属性字段 **********************************/
    //产品过滤属性类型集合
    private Map<String, String> productFilterAttributeMap;
    //产品排序属性类型集合
    private Map<String, String> productOrderAttributeMap;

    /******************************** 产品使用次数字段 **********************************/
    //产品使用次数字段Map<userId, usageCount>
    private Map<Integer, Integer> productUsageCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Integer productCategoryId) {
		this.productCategoryId = productCategoryId;
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

	public Integer getProductBrandId() {
		return productBrandId;
	}

	public void setProductBrandId(Integer productBrandId) {
		this.productBrandId = productBrandId;
	}

	public String getProductBrandName() {
		return productBrandName;
	}

	public void setProductBrandName(String productBrandName) {
		this.productBrandName = productBrandName;
	}

	public List<Integer> getProductStyleList() {
		return productStyleList;
	}

	public void setProductStyleList(List<Integer> productStyleList) {
		this.productStyleList = productStyleList;
	}

	public String getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}

	public Integer getProductColorId() {
		return productColorId;
	}

	public void setProductColorId(Integer productColorId) {
		this.productColorId = productColorId;
	}

	public Integer getProductLength() {
		return productLength;
	}

	public void setProductLength(Integer productLength) {
		this.productLength = productLength;
	}

	public Integer getProductWidth() {
		return productWidth;
	}

	public void setProductWidth(Integer productWidth) {
		this.productWidth = productWidth;
	}

	public Integer getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(Integer productHeight) {
		this.productHeight = productHeight;
	}

	public Double getProductSalePrice() {
		return productSalePrice;
	}

	public void setProductSalePrice(Double productSalePrice) {
		this.productSalePrice = productSalePrice;
	}

	public Integer getProductSalePriceValue() {
		return productSalePriceValue;
	}

	public void setProductSalePriceValue(Integer productSalePriceValue) {
		this.productSalePriceValue = productSalePriceValue;
	}

	public Integer getProductPicId() {
		return productPicId;
	}

	public void setProductPicId(Integer productPicId) {
		this.productPicId = productPicId;
	}

	public String getProductPicPath() {
		return productPicPath;
	}

	public void setProductPicPath(String productPicPath) {
		this.productPicPath = productPicPath;
	}

	public List<Integer> getProductPicList() {
		return productPicList;
	}

	public void setProductPicList(List<Integer> productPicList) {
		this.productPicList = productPicList;
	}

	public List<String> getProductPicPathList() {
		return productPicPathList;
	}

	public void setProductPicPathList(List<String> productPicPathList) {
		this.productPicPathList = productPicPathList;
	}

	public Integer getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(Integer productModelId) {
		this.productModelId = productModelId;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductSystemCode() {
		return productSystemCode;
	}

	public void setProductSystemCode(String productSystemCode) {
		this.productSystemCode = productSystemCode;
	}

	public Date getProductCreateDate() {
		return productCreateDate;
	}

	public void setProductCreateDate(Date productCreateDate) {
		this.productCreateDate = productCreateDate;
	}

	public Date getProductModifyDate() {
		return productModifyDate;
	}

	public void setProductModifyDate(Date productModifyDate) {
		this.productModifyDate = productModifyDate;
	}

	public Date getProductPutawayDate() {
		return productPutawayDate;
	}

	public void setProductPutawayDate(Date productPutawayDate) {
		this.productPutawayDate = productPutawayDate;
	}

	public List<String> getProductMaterialList() {
		return productMaterialList;
	}

	public void setProductMaterialList(List<String> productMaterialList) {
		this.productMaterialList = productMaterialList;
	}

	public Integer getProductTypeValue() {
		return productTypeValue;
	}

	public void setProductTypeValue(Integer productTypeValue) {
		this.productTypeValue = productTypeValue;
	}

	public Integer getProductTypeSmallValue() {
		return productTypeSmallValue;
	}

	public void setProductTypeSmallValue(Integer productTypeSmallValue) {
		this.productTypeSmallValue = productTypeSmallValue;
	}

	public Integer getProductPutawayState() {
		return productPutawayState;
	}

	public void setProductPutawayState(Integer productPutawayState) {
		this.productPutawayState = productPutawayState;
	}

	public Integer getProductDesignerId() {
		return productDesignerId;
	}

	public void setProductDesignerId(Integer productDesignerId) {
		this.productDesignerId = productDesignerId;
	}

	public Integer getProductSeriesId() {
		return productSeriesId;
	}

	public void setProductSeriesId(Integer productSeriesId) {
		this.productSeriesId = productSeriesId;
	}

	public String getProductModelNumber() {
		return productModelNumber;
	}

	public void setProductModelNumber(String productModelNumber) {
		this.productModelNumber = productModelNumber;
	}

	public String getProductMeasurementCode() {
		return productMeasurementCode;
	}

	public void setProductMeasurementCode(String productMeasurementCode) {
		this.productMeasurementCode = productMeasurementCode;
	}

	public List<String> getProductCeilingLayoutIdenList() {
		return productCeilingLayoutIdenList;
	}

	public void setProductCeilingLayoutIdenList(List<String> productCeilingLayoutIdenList) {
		this.productCeilingLayoutIdenList = productCeilingLayoutIdenList;
	}

	public String getProductGroundLayoutIden() {
		return productGroundLayoutIden;
	}

	public void setProductGroundLayoutIden(String productGroundLayoutIden) {
		this.productGroundLayoutIden = productGroundLayoutIden;
	}

	public Integer getProductFullPaveLength() {
		return productFullPaveLength;
	}

	public void setProductFullPaveLength(Integer productFullPaveLength) {
		this.productFullPaveLength = productFullPaveLength;
	}

	public Integer getSecrecyFlag() {
		return secrecyFlag;
	}

	public void setSecrecyFlag(Integer secrecyFlag) {
		this.secrecyFlag = secrecyFlag;
	}

	public Integer getCeilingRandomPatchFlowerFlag() {
		return ceilingRandomPatchFlowerFlag;
	}

	public void setCeilingRandomPatchFlowerFlag(Integer ceilingRandomPatchFlowerFlag) {
		this.ceilingRandomPatchFlowerFlag = ceilingRandomPatchFlowerFlag;
	}

	public Integer getDataIsDelete() {
		return dataIsDelete;
	}

	public void setDataIsDelete(Integer dataIsDelete) {
		this.dataIsDelete = dataIsDelete;
	}

	public Integer getFirstLevelCategoryId() {
		return firstLevelCategoryId;
	}

	public void setFirstLevelCategoryId(Integer firstLevelCategoryId) {
		this.firstLevelCategoryId = firstLevelCategoryId;
	}

	public String getFirstLevelCategoryName() {
		return firstLevelCategoryName;
	}

	public void setFirstLevelCategoryName(String firstLevelCategoryName) {
		this.firstLevelCategoryName = firstLevelCategoryName;
	}

	public Integer getSecondLevelCategoryId() {
		return secondLevelCategoryId;
	}

	public void setSecondLevelCategoryId(Integer secondLevelCategoryId) {
		this.secondLevelCategoryId = secondLevelCategoryId;
	}

	public String getSecondLevelCategoryName() {
		return secondLevelCategoryName;
	}

	public void setSecondLevelCategoryName(String secondLevelCategoryName) {
		this.secondLevelCategoryName = secondLevelCategoryName;
	}

	public Integer getThirdLevelCategoryId() {
		return thirdLevelCategoryId;
	}

	public void setThirdLevelCategoryId(Integer thirdLevelCategoryId) {
		this.thirdLevelCategoryId = thirdLevelCategoryId;
	}

	public String getThirdLevelCategoryName() {
		return thirdLevelCategoryName;
	}

	public void setThirdLevelCategoryName(String thirdLevelCategoryName) {
		this.thirdLevelCategoryName = thirdLevelCategoryName;
	}

	public Integer getFourthLevelCategoryId() {
		return fourthLevelCategoryId;
	}

	public void setFourthLevelCategoryId(Integer fourthLevelCategoryId) {
		this.fourthLevelCategoryId = fourthLevelCategoryId;
	}

	public String getFourthLevelCategoryName() {
		return fourthLevelCategoryName;
	}

	public void setFourthLevelCategoryName(String fourthLevelCategoryName) {
		this.fourthLevelCategoryName = fourthLevelCategoryName;
	}

	public Integer getFifthLevelCategoryId() {
		return fifthLevelCategoryId;
	}

	public void setFifthLevelCategoryId(Integer fifthLevelCategoryId) {
		this.fifthLevelCategoryId = fifthLevelCategoryId;
	}

	public String getFifthLevelCategoryName() {
		return fifthLevelCategoryName;
	}

	public void setFifthLevelCategoryName(String fifthLevelCategoryName) {
		this.fifthLevelCategoryName = fifthLevelCategoryName;
	}

	public List<String> getAllLevelCategoryName() {
		return allLevelCategoryName;
	}

	public void setAllLevelCategoryName(List<String> allLevelCategoryName) {
		this.allLevelCategoryName = allLevelCategoryName;
	}

	public String getProductCategoryCode() {
		return productCategoryCode;
	}

	public void setProductCategoryCode(String productCategoryCode) {
		this.productCategoryCode = productCategoryCode;
	}

	public List<String> getProductCategoryLongCodeList() {
		return productCategoryLongCodeList;
	}

	public void setProductCategoryLongCodeList(List<String> productCategoryLongCodeList) {
		this.productCategoryLongCodeList = productCategoryLongCodeList;
	}

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public Integer getCategoryOrder() {
		return categoryOrder;
	}

	public void setCategoryOrder(Integer categoryOrder) {
		this.categoryOrder = categoryOrder;
	}

	public String getCategorySystemCode() {
		return categorySystemCode;
	}

	public void setCategorySystemCode(String categorySystemCode) {
		this.categorySystemCode = categorySystemCode;
	}

	public List<Integer> getAllProductCategoryId() {
		return allProductCategoryId;
	}

	public void setAllProductCategoryId(List<Integer> allProductCategoryId) {
		this.allProductCategoryId = allProductCategoryId;
	}

	public List<String> getAllProductCategoryName() {
		return allProductCategoryName;
	}

	public void setAllProductCategoryName(List<String> allProductCategoryName) {
		this.allProductCategoryName = allProductCategoryName;
	}

	public List<String> getProductMaterialNameList() {
		return productMaterialNameList;
	}

	public void setProductMaterialNameList(List<String> productMaterialNameList) {
		this.productMaterialNameList = productMaterialNameList;
	}

	public List<String> getStyleNameList() {
		return styleNameList;
	}

	public void setStyleNameList(List<String> styleNameList) {
		this.styleNameList = styleNameList;
	}

	public List<Integer> getProductGroupIdList() {
		return productGroupIdList;
	}

	public void setProductGroupIdList(List<Integer> productGroupIdList) {
		this.productGroupIdList = productGroupIdList;
	}

	public List<Integer> getProductGroupNameList() {
		return productGroupNameList;
	}

	public void setProductGroupNameList(List<Integer> productGroupNameList) {
		this.productGroupNameList = productGroupNameList;
	}

	public List<Integer> getProductGroupCodeList() {
		return productGroupCodeList;
	}

	public void setProductGroupCodeList(List<Integer> productGroupCodeList) {
		this.productGroupCodeList = productGroupCodeList;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Integer getCompanyIndustry() {
		return companyIndustry;
	}

	public void setCompanyIndustry(Integer companyIndustry) {
		this.companyIndustry = companyIndustry;
	}

	public List<String> getPlatformCodeList() {
		return platformCodeList;
	}

	public void setPlatformCodeList(List<String> platformCodeList) {
		this.platformCodeList = platformCodeList;
	}

	public ProductPlatformRelPo getPlatformProductToBMobile() {
		return platformProductToBMobile;
	}

	public void setPlatformProductToBMobile(ProductPlatformRelPo platformProductToBMobile) {
		this.platformProductToBMobile = platformProductToBMobile;
	}

	public ProductPlatformRelPo getPlatformProductToBPc() {
		return platformProductToBPc;
	}

	public void setPlatformProductToBPc(ProductPlatformRelPo platformProductToBPc) {
		this.platformProductToBPc = platformProductToBPc;
	}

	public ProductPlatformRelPo getPlatformProductToCSite() {
		return platformProductToCSite;
	}

	public void setPlatformProductToCSite(ProductPlatformRelPo platformProductToCSite) {
		this.platformProductToCSite = platformProductToCSite;
	}

	public ProductPlatformRelPo getPlatformProductToCMobile() {
		return platformProductToCMobile;
	}

	public void setPlatformProductToCMobile(ProductPlatformRelPo platformProductToCMobile) {
		this.platformProductToCMobile = platformProductToCMobile;
	}

	public ProductPlatformRelPo getPlatformProductSanduManager() {
		return platformProductSanduManager;
	}

	public void setPlatformProductSanduManager(ProductPlatformRelPo platformProductSanduManager) {
		this.platformProductSanduManager = platformProductSanduManager;
	}

	public ProductPlatformRelPo getPlatformProductHouseDraw() {
		return platformProductHouseDraw;
	}

	public void setPlatformProductHouseDraw(ProductPlatformRelPo platformProductHouseDraw) {
		this.platformProductHouseDraw = platformProductHouseDraw;
	}

	public ProductPlatformRelPo getPlatformProductMerchantsManager() {
		return platformProductMerchantsManager;
	}

	public void setPlatformProductMerchantsManager(ProductPlatformRelPo platformProductMerchantsManager) {
		this.platformProductMerchantsManager = platformProductMerchantsManager;
	}

	public ProductPlatformRelPo getPlatformProductTest() {
		return platformProductTest;
	}

	public void setPlatformProductTest(ProductPlatformRelPo platformProductTest) {
		this.platformProductTest = platformProductTest;
	}

	public ProductPlatformRelPo getPlatformProductU3dPlugin() {
		return platformProductU3dPlugin;
	}

	public void setPlatformProductU3dPlugin(ProductPlatformRelPo platformProductU3dPlugin) {
		this.platformProductU3dPlugin = platformProductU3dPlugin;
	}

	public ProductPlatformRelPo getPlatformProductMiniProgram() {
		return platformProductMiniProgram;
	}

	public void setPlatformProductMiniProgram(ProductPlatformRelPo platformProductMiniProgram) {
		this.platformProductMiniProgram = platformProductMiniProgram;
	}

	public Map<String, String> getProductFilterAttributeMap() {
		return productFilterAttributeMap;
	}

	public void setProductFilterAttributeMap(Map<String, String> productFilterAttributeMap) {
		this.productFilterAttributeMap = productFilterAttributeMap;
	}

	public Map<String, String> getProductOrderAttributeMap() {
		return productOrderAttributeMap;
	}

	public void setProductOrderAttributeMap(Map<String, String> productOrderAttributeMap) {
		this.productOrderAttributeMap = productOrderAttributeMap;
	}

	public Map<Integer, Integer> getProductUsageCount() {
		return productUsageCount;
	}

	public void setProductUsageCount(Map<Integer, Integer> productUsageCount) {
		this.productUsageCount = productUsageCount;
	}

	public Integer getStyleModelId() {
		return styleModelId;
	}

	public void setStyleModelId(Integer styleModelId) {
		this.styleModelId = styleModelId;
	}

	public String getBmIds() {
		return bmIds;
	}

	public void setBmIds(String bmIds) {
		this.bmIds = bmIds;
	}

	public String getSplitTexturesInfo() {
		return splitTexturesInfo;
	}

	public void setSplitTexturesInfo(String splitTexturesInfo) {
		this.splitTexturesInfo = splitTexturesInfo;
	}

}