package com.sandu.product.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sandu.product.model.dto.SplitTextureDTO;
import com.sandu.product.model.po.BaseProductPO;
import com.sandu.product.model.result.CategoryProductResult;

/**   
 * @Title: BaseProduct.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品库
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:37
 * @version V1.0   
 */
public class BaseProduct  extends BaseProductPO implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer productId;
    private Integer hideId;

	/**   饰品名称  **/
	private String decorationName;
	
	private String picPath;
	
	private String filePath;
	
	private String maxModelPath;

	private String rootType;
	
	//0、实体墙  1、背景墙 2、门框
	private Integer bgWall;
	//模型path
	private String productModelPath;
	/** 产品属性 **/
	private Map<String,String> propertyMap;

	/**产品价格单位名称*/
	private String salePriceValueName;

	//色系排序
	private Integer colorsOrdering;
	/**携带得白模产品集合*/
	private Map<String,CategoryProductResult> basicModelMap;

	/**产品风格(list)*/
	private List<String> productStyleInfoList;
	
 
	private List<String> list;
	
	private List<Integer> resIdList;
	
	private Integer dicSmallTypeValue;

	/*  是否显示U3D模型 0:不显示,1:显示  */
	private Integer showU3dModel;

	/**
	 * 过滤属性信息(用于组合/单品匹配过滤属性)
	 */
	private List<ProductPropsSimple> productFilterPropList;

	/**
	 * 是否显示无品牌开关(0:默认显示无品牌,1:不显示无品牌)
	 * add by huangsongbo 2017.11.4
	 */
	private Integer statusShowWu;

	/*品牌行业list*/
	private List<BrandIndustryModel> brandIndustryModelList;

	public List<BrandIndustryModel> getBrandIndustryModelList() {
		return brandIndustryModelList;
	}

	public void setBrandIndustryModelList(List<BrandIndustryModel> brandIndustryModelList) {
		this.brandIndustryModelList = brandIndustryModelList;
	}

	private Boolean isShrink;
	public Boolean getIsShrink() {
		return isShrink;
	}
	public void setIsShrink(Boolean isShrink) {
		this.isShrink = isShrink;
	}
	
	public Integer getStatusShowWu() {
		return statusShowWu;
	}

	public void setStatusShowWu(Integer statusShowWu) {
		this.statusShowWu = statusShowWu;
	}

	public List<ProductPropsSimple> getProductFilterPropList() {
		return productFilterPropList;
	}

	public void setProductFilterPropList(List<ProductPropsSimple> productFilterPropList) {
		this.productFilterPropList = productFilterPropList;
	}

	public Integer getShowU3dModel() {
		return showU3dModel;
	}

	public void setShowU3dModel(Integer showU3dModel) {
		this.showU3dModel = showU3dModel;
	}

	public Integer getDicSmallTypeValue() {
		return dicSmallTypeValue;
	}

	public void setDicSmallTypeValue(Integer dicSmallTypeValue) {
		this.dicSmallTypeValue = dicSmallTypeValue;
	}

	public List<Integer> getResIdList() {
		return resIdList;
	}

	public void setResIdList(List<Integer> resIdList) {
		this.resIdList = resIdList;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	/**属性信息(eg:BedWidth,1,99,180;size,0,99,2)*/
	private String propInfo;
	
	/*设计方案产品序号*/
	private Integer productIndex;
	/*设计方案产品id*/
	private Integer designPlanProductId;
	/*数据字典的att1*/
	private String att1;
	
	public String getAtt1() {
		return att1;
	}

	public void setAtt1(String att1) {
		this.att1 = att1;
	}

	public Integer getProductIndex() {
		return productIndex;
	}

	public void setProductIndex(Integer productIndex) {
		this.productIndex = productIndex;
	}

	public Integer getDesignPlanProductId() {
		return designPlanProductId;
	}

	public void setDesignPlanProductId(Integer designPlanProductId) {
		this.designPlanProductId = designPlanProductId;
	}

	public String getPropInfo() {
		return propInfo;
	}

	public void setPropInfo(String propInfo) {
		this.propInfo = propInfo;
	}

	public List<String> getProductStyleInfoList() {
		return productStyleInfoList;
	}

	public void setProductStyleInfoList(List<String> productStyleInfoList) {
		this.productStyleInfoList = productStyleInfoList;
	}

	public Map<String, CategoryProductResult> getBasicModelMap() {
		return basicModelMap;
	}

	public void setBasicModelMap(Map<String, CategoryProductResult> basicModelMap) {
		this.basicModelMap = basicModelMap;
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

	public void setSalePriceValueName(String salePriceValueName) {
		this.salePriceValueName = salePriceValueName;
	}
 
	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public String getProductModelPath() {
		return productModelPath;
	}

	public void setProductModelPath(String productModelPath) {
		this.productModelPath = productModelPath;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	private String[] smallPicPath;

    /**白模名称**/
	private String bmName;

	private String materialFilePath;
	//材质图片s
	private String[] materialPicPaths;
	//材质球配置文件地址
	private String materialConfigPath;

	private String[] material3dPicPaths;
	/**  产品大类型  **/
	private String productTypeCode;
	//序列号配置品牌ids
	private List<String> configBrandIdList;
	//序列号配置大类values
	private List<String> configTypeValueList;
	//序列号配置小类values
	private List<String> configSmallTypeIdList;
	//序列号配置产品ids
	private List<String> configProductIdList;
	//设备终端号(查询产品时过滤产品的参数)
	private String terminalImei;
	/** 模型长度 **/
	private int modelLength;
	/** 模型宽度 **/
	private int modelWidth;
	/** 模型高度 **/
	private int modelHeight;
	/** 模型最小高度 **/
	private int modelMinHeight;
	/*材质属性*/
	private Integer textureAttrValue; 
	/*铺设方式*/
	private String laymodes;

	private List<SplitTextureDTO> splitTexturesChoose;
	private List<SplitTextureDTO> splitTexturesInfoList;
	/*查询条件,产品idList*/
	private List<Integer> productIdList;

	//状态集合
	private List<Integer> putawayStateList;

	//是否是背景墙
	private boolean isBeijing;
	//是否是需拉伸产品
	private boolean isStretch;
	//伸缩产品长高过滤产品用
	private Integer startLength;
	private Integer endLength;
	private String stretchHeight;
	private List<String> attributeConditionList;
	private Integer attributeConditionSize;

	private String  deviceId = null;
	private String  msgId = null;
	private String  ids = null;
	private Integer start = 0;
	private Integer limit = 20;
	private String  order = null;
	private String  orderNum = null;
	private String  orders = null;
	/**级别限制的资源数量*/
	private int levelLimitCount=0;
	
	public List<SplitTextureDTO> getSplitTexturesInfoList() {
		return splitTexturesInfoList;
	}

	public void setSplitTexturesInfoList(List<SplitTextureDTO> splitTexturesInfoList) {
		this.splitTexturesInfoList = splitTexturesInfoList;
	}

	public List<String> getAttributeConditionList() {
		return attributeConditionList;
	}

	public void setAttributeConditionList(List<String> attributeConditionList) {
		this.attributeConditionList = attributeConditionList;
	}

	public Integer getAttributeConditionSize() {
		return attributeConditionSize;
	}

	public void setAttributeConditionSize(Integer attributeConditionSize) {
		this.attributeConditionSize = attributeConditionSize;
	}

	public Integer getStartLength() {
		return startLength;
	}

	public void setStartLength(Integer startLength) {
		this.startLength = startLength;
	}

	public Integer getEndLength() {
		return endLength;
	}

	public void setEndLength(Integer endLength) {
		this.endLength = endLength;
	}

	public String getStretchHeight() {
		return stretchHeight;
	}

	public void setStretchHeight(String stretchHeight) {
		this.stretchHeight = stretchHeight;
	}

	public boolean isBeijing() {
		return isBeijing;
	}


	public void setBeijing(boolean beijing) {
		isBeijing = beijing;
	}

	public boolean isStretch() {
		return isStretch;
	}

	public void setStretch(boolean stretch) {
		isStretch = stretch;
	}

	public List<Integer> getPutawayStateList() {
		return putawayStateList;
	}

	public void setPutawayStateList(List<Integer> putawayStateList) {
		this.putawayStateList = putawayStateList;
	}


	public List<Integer> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<Integer> productIdList) {
		this.productIdList = productIdList;
	}

	public List<SplitTextureDTO> getSplitTexturesChoose() {
		return splitTexturesChoose;
	}

	public void setSplitTexturesChoose(List<SplitTextureDTO> splitTexturesChoose) {
		this.splitTexturesChoose = splitTexturesChoose;
	}

	public String getMaxModelPath() {
		return maxModelPath;
	}

	public void setMaxModelPath(String maxModelPath) {
		this.maxModelPath = maxModelPath;
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

	public String[] getMaterial3dPicPaths() {
		return material3dPicPaths;
	}

	public void setMaterial3dPicPaths(String[] material3dPicPaths) {
		this.material3dPicPaths = material3dPicPaths;
	}
	
	public String getMaterialFilePath() {
		return materialFilePath;
	}

	public void setMaterialFilePath(String materialFilePath) {
		this.materialFilePath = materialFilePath;
	}
	
	public String[] getSmallPicPath() {
		return smallPicPath;
	}

	public void setSmallPicPath(String[] smallPicPath) {
		this.smallPicPath = smallPicPath;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public Integer getBgWall() {
		return bgWall;
	}

	public void setBgWall(Integer bgWall) {
		this.bgWall = bgWall==null?0:bgWall;
	}

	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	public Integer getHideId() {
		return hideId;
	}

	public void setHideId(Integer hideId) {
		this.hideId = hideId;
	}
	public String getTerminalImei() {
		return terminalImei;
	}

	public void setTerminalImei(String terminalImei) {
		this.terminalImei = terminalImei;
	}

	public List<String> getConfigBrandIdList() {
		return configBrandIdList;
	}

	public void setConfigBrandIdList(List<String> configBrandIdList) {
		this.configBrandIdList = configBrandIdList;
	}

	public List<String> getConfigTypeValueList() {
		return configTypeValueList;
	}

	public void setConfigTypeValueList(List<String> configTypeValueList) {
		this.configTypeValueList = configTypeValueList;
	}

	public List<String> getConfigSmallTypeIdList() {
		return configSmallTypeIdList;
	}

	public void setConfigSmallTypeIdList(List<String> configSmallTypeIdList) {
		this.configSmallTypeIdList = configSmallTypeIdList;
	}

	public List<String> getConfigProductIdList() {
		return configProductIdList;
	}

	public void setConfigProductIdList(List<String> configProductIdList) {
		this.configProductIdList = configProductIdList;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	//同类型产品(标识同一类)
	private Integer parentProductId;

	public String getBmName() {
		return bmName;
	}

	public void setBmName(String bmName) {
		this.bmName = bmName;
	}

	/**  产品类型code（小类）  **/
	private String productSmallTypeCode;
	/**  产品类型name（小类）  **/
	private String productSmallTypeName;

	/**  U3D模型路径  **/
	private String u3dModelPath;

	private String iosU3dModelPath;
	/** android使用u3d模型 **/
	private String androidU3dModelPath;
	/** macBook使用u3d模型 **/
	private String macBookU3dModelPath;
	/** windows使用u3d模型 **/
	private String windowsU3dModelPath;
	/** ipad使用u3d模型 **/
	private String ipadU3dModelPath;

	
	/*可能是任何模型的路径  通过传入的mediaType决定  用于 getDataAndModel 方法*/
	private String mediaTypeModelPath;
	
	private String mediaType;

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaTypeModelPath() {
		return mediaTypeModelPath;
	}

	public void setMediaTypeModelPath(String mediaTypeModelPath) {
		this.mediaTypeModelPath = mediaTypeModelPath;
	}

	public String getIosU3dModelPath() {
		return iosU3dModelPath;
	}

	public void setIosU3dModelPath(String iosU3dModelPath) {
		this.iosU3dModelPath = iosU3dModelPath;
	}

	public String getAndroidU3dModelPath() {
		return androidU3dModelPath;
	}

	public void setAndroidU3dModelPath(String androidU3dModelPath) {
		this.androidU3dModelPath = androidU3dModelPath;
	}

	public String getMacBookU3dModelPath() {
		return macBookU3dModelPath;
	}

	public void setMacBookU3dModelPath(String macBookU3dModelPath) {
		this.macBookU3dModelPath = macBookU3dModelPath;
	}

	public String getWindowsU3dModelPath() {
		return windowsU3dModelPath;
	}

	public void setWindowsU3dModelPath(String windowsU3dModelPath) {
		this.windowsU3dModelPath = windowsU3dModelPath;
	}


	public String getIpadU3dModelPath() {
		return ipadU3dModelPath;
	}

	public void setIpadU3dModelPath(String ipadU3dModelPath) {
		this.ipadU3dModelPath = ipadU3dModelPath;
	}
	private Integer userId;
	
    private String brandName;
    
    private String brandReferred;
	public String getBrandReferred() {
		return brandReferred;
	}

	public void setBrandReferred(String brandReferred) {
		this.brandReferred = brandReferred;
	}
    
    private String houseType;
    
    private String productType;
    
    private String productStyle;
    
    private String productColor;
    
    private String productPicPath;
    
    private String productSmallType;
    
    List smallPiclist = new ArrayList(); 
    
    //图片列表缩略图集合
    List thumbnailList = new ArrayList(); 

    public List getThumbnailList() {
		return thumbnailList;
	}

	public void setThumbnailList(List thumbnailList) {
		this.thumbnailList = thumbnailList;
	}

	List colorlist = new ArrayList(); 
    
    List materiallist = new ArrayList();
    
    /*List attributelist = new ArrayList();*/
    
    Map<String,List<ProductAttribute>> map = new HashMap<String,List<ProductAttribute>>();
    
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public int getLevelLimitCount() {
		return levelLimitCount;
	}

	public void setLevelLimitCount(int levelLimitCount) {
		this.levelLimitCount = levelLimitCount;
	}

	public Map<String, List<ProductAttribute>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<ProductAttribute>> map) {
		this.map = map;
	}

	/**public List getAttributelist() {
		return attributelist;
	}

	public void setAttributelist(List attributelist) {
		this.attributelist = attributelist;
	}*/

	private Integer collectState;//收藏状态
    
    private String putawayStateName;//上架狀態

	/**
	 * 商品类型唯一标识
	 */
	private String productTypeKey;
	/**
	 * 商品颜色RGB值
	 */
	private String productColorKey;
	/** 品牌（多品牌逗号分隔） **/
	private List<String> brandIds;
	
	private Date tomorrow;

	/*判断是否是可拆分材质产品:0:普通产品;1:可拆分材质产品(橱柜)*/
	private Integer isSplit=new Integer(0);
	/**其他小类显示全部(不经过授权码过滤)*/
	private List<Integer> smallTypeValueListForShowAll;

	public List<Integer> getSmallTypeValueListForShowAll() {
		return smallTypeValueListForShowAll;
	}

	public void setSmallTypeValueListForShowAll(List<Integer> smallTypeValueListForShowAll) {
		this.smallTypeValueListForShowAll = smallTypeValueListForShowAll;
	}

	public Integer getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(Integer isSplit) {
		this.isSplit = isSplit;
	}

	public Date getTomorrow() {
		return tomorrow;
	}

	public void setTomorrow(Date tomorrow) {
		this.tomorrow = tomorrow;
	}

	public String getDecorationName() {
		return decorationName;
	}

	public void setDecorationName(String decorationName) {
		this.decorationName = decorationName;
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

	public String getU3dModelPath() {
		return u3dModelPath;
	}
	public void setU3dModelPath(String u3dModelPath) {
		this.u3dModelPath = u3dModelPath;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<String> getBrandIds() {
		return brandIds;
	}
	public void setBrandIds(List<String> brandIds) {
		this.brandIds = brandIds;
	}

	@Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BaseProduct other = (BaseProduct) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getProductCode() == null ? other.getProductCode() == null : this.getProductCode().equals(other.getProductCode()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getBrandId() == null ? other.getBrandId() == null : this.getBrandId().equals(other.getBrandId()))
            && (this.getProStyleId() == null ? other.getProStyleId() == null : this.getProStyleId().equals(other.getProStyleId()))
            && (this.getProductSpec() == null ? other.getProductSpec() == null : this.getProductSpec().equals(other.getProductSpec()))
            && (this.getColorId() == null ? other.getColorId() == null : this.getColorId().equals(other.getColorId()))
            && (this.getProductLength() == null ? other.getProductLength() == null : this.getProductLength().equals(other.getProductLength()))
            && (this.getProductWidth() == null ? other.getProductWidth() == null : this.getProductWidth().equals(other.getProductWidth()))
            && (this.getProductHeight() == null ? other.getProductHeight() == null : this.getProductHeight().equals(other.getProductHeight()))
            && (this.getSalePrice() == null ? other.getSalePrice() == null : this.getSalePrice().equals(other.getSalePrice()))
            && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
            && (this.getModelId() == null ? other.getModelId() == null : this.getModelId().equals(other.getModelId()))
            && (this.getProductDesc() == null ? other.getProductDesc() == null : this.getProductDesc().equals(other.getProductDesc()))
            && (this.getPicIds() == null ? other.getPicIds() == null : this.getPicIds().equals(other.getPicIds()))
            && (this.getMaterialPicIds() == null ? other.getMaterialPicIds() == null : this.getMaterialPicIds().equals(other.getMaterialPicIds()))
            && (this.getHouseTypeValues() == null ? other.getHouseTypeValues() == null : this.getHouseTypeValues().equals(other.getHouseTypeValues()))
            && (this.getProductSmallTypeValue() == null ? other.getProductSmallTypeValue() == null : this.getProductSmallTypeValue().equals(other.getProductSmallTypeValue()))
            && (this.getProductSmallTypeMark() == null ? other.getProductSmallTypeMark() == null : this.getProductSmallTypeMark().equals(other.getProductSmallTypeMark()))
            && (this.getProductTypeMark() == null ? other.getProductTypeMark() == null : this.getProductTypeMark().equals(other.getProductTypeMark()))
            && (this.getU3dModelId() == null ? other.getU3dModelId() == null : this.getU3dModelId().equals(other.getU3dModelId()))
            && (this.getMergeProductIds() == null ? other.getMergeProductIds() == null : this.getMergeProductIds().equals(other.getMergeProductIds()))
            && (this.getPutawayModified() == null ? other.getPutawayModified() == null : this.getPutawayModified().equals(other.getPutawayModified()))
            && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
            && (this.getProductTypeValue() == null ? other.getProductTypeValue() == null : this.getProductTypeValue().equals(other.getProductTypeValue()))
            && (this.getPutawayState() == null ? other.getPutawayState() == null : this.getPutawayState().equals(other.getPutawayState()))
            && (this.getNumAtt3() == null ? other.getNumAtt3() == null : this.getNumAtt3().equals(other.getNumAtt3()))
            && (this.getNumAtt4() == null ? other.getNumAtt4() == null : this.getNumAtt4().equals(other.getNumAtt4()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
			&& (this.getU3dModelPath() == null ? other.getU3dModelPath() == null : this.getU3dModelPath().equals(other.getU3dModelPath()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getProductCode() == null) ? 0 : getProductCode().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getBrandId() == null) ? 0 : getBrandId().hashCode());
        result = prime * result + ((getProStyleId() == null) ? 0 : getProStyleId().hashCode());
        result = prime * result + ((getProductSpec() == null) ? 0 : getProductSpec().hashCode());
        result = prime * result + ((getColorId() == null) ? 0 : getColorId().hashCode());
        result = prime * result + ((getProductLength() == null) ? 0 : getProductLength().hashCode());
        result = prime * result + ((getProductWidth() == null) ? 0 : getProductWidth().hashCode());
        result = prime * result + ((getProductHeight() == null) ? 0 : getProductHeight().hashCode());
        result = prime * result + ((getSalePrice() == null) ? 0 : getSalePrice().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getModelId() == null) ? 0 : getModelId().hashCode());
        result = prime * result + ((getProductDesc() == null) ? 0 : getProductDesc().hashCode());
        result = prime * result + ((getPicIds() == null) ? 0 : getPicIds().hashCode());
        result = prime * result + ((getMaterialPicIds() == null) ? 0 : getMaterialPicIds().hashCode());
        result = prime * result + ((getHouseTypeValues() == null) ? 0 : getHouseTypeValues().hashCode());
        result = prime * result + ((getProductSmallTypeValue() == null) ? 0 : getProductSmallTypeValue().hashCode());
        result = prime * result + ((getProductSmallTypeMark() == null) ? 0 : getProductSmallTypeMark().hashCode());
        result = prime * result + ((getProductTypeMark() == null) ? 0 : getProductTypeMark().hashCode());
        result = prime * result + ((getU3dModelId() == null) ? 0 : getU3dModelId().hashCode());
        result = prime * result + ((getMergeProductIds() == null) ? 0 : getMergeProductIds().hashCode());
        result = prime * result + ((getPutawayModified() == null) ? 0 : getPutawayModified().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getProductTypeValue() == null) ? 0 : getProductTypeValue().hashCode());
        result = prime * result + ((getPutawayState() == null) ? 0 : getPutawayState().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
		result = prime * result + ((getU3dModelPath() == null) ? 0 : getU3dModelPath().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public BaseProduct copy(){
       BaseProduct obj =  new BaseProduct();
       obj.setSysCode(this.getSysCode());
       obj.setCreator(this.getCreator());
       obj.setGmtCreate(this.getGmtCreate());
       obj.setModifier(this.getModifier());
       obj.setGmtModified(this.getGmtModified());
       obj.setIsDeleted(this.getIsDeleted());
       obj.setProductCode(this.getProductCode());
       obj.setProductName(this.getProductName());
       obj.setBrandId(this.getBrandId());
       obj.setProStyleId(this.getProStyleId());
       obj.setProductSpec(this.getProductSpec());
       obj.setColorId(this.getColorId());
       obj.setProductLength(this.getProductLength());
       obj.setProductWidth(this.getProductWidth());
       obj.setProductHeight(this.getProductHeight());
       obj.setSalePrice(this.getSalePrice());
       obj.setPicId(this.getPicId());
       obj.setModelId(this.getModelId());
       obj.setProductDesc(this.getProductDesc());
       obj.setPicIds(this.getPicIds());
       obj.setMaterialPicIds(this.getMaterialPicIds());
       obj.setHouseTypeValues(this.getHouseTypeValues());
       obj.setProductTypeValue(this.getProductTypeValue());
       obj.setU3dModelId(this.getU3dModelId());
       obj.setMergeProductIds(this.getMergeProductIds());
       obj.setPutawayModified(this.getPutawayModified());
       obj.setDateAtt2(this.getDateAtt2());
       obj.setProductSmallTypeValue(this.getProductSmallTypeValue());
       obj.setProductSmallTypeMark(this.getProductSmallTypeMark());
       obj.setProductTypeMark(this.getProductTypeMark());
       obj.setPutawayState(this.getPutawayState());
       obj.setNumAtt3(this.getNumAtt3());
       obj.setNumAtt4(this.getNumAtt4());
       obj.setRemark(this.getRemark());
	   obj.setU3dModelPath(this.getU3dModelPath());
       return obj;
    }
    
     /**获取对象的map**/
    /*public Map toMap(){
       Map map =  new HashMap();
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("productCode",this.productCode);
       map.put("productName",this.productName);
       map.put("brandId",this.brandId);
       map.put("proStyleId",this.proStyleId);
       map.put("productSpec",this.productSpec);
       map.put("colorId",this.colorId);
       map.put("productLength",this.productLength);
       map.put("productWidth",this.productWidth);
       map.put("productHeight",this.productHeight);
       map.put("salePrice",this.salePrice);
       map.put("picId",this.picId);
       map.put("modelId",this.modelId);
       map.put("productDesc",this.productDesc);
       map.put("picIds",this.picIds);
       map.put("materialPicIds",this.materialPicIds);
       map.put("houseTypeValues",this.houseTypeValues);
       map.put("productTypeValue",this.productTypeValue);
       map.put("u3dModelId",this.u3dModelId);
       map.put("mergeProductIds",this.mergeProductIds);
       map.put("putawayModified",this.putawayModified);
       map.put("dateAtt2",this.dateAtt2);
       map.put("productSmallTypeValue",this.productSmallTypeValue);
       map.put("productSmallTypeMark",this.productSmallTypeMark);
       map.put("productTypeMark",this.productTypeMark);
       map.put("putawayState",this.putawayState);
       map.put("numAtt3",this.numAtt3);
       map.put("numAtt4",this.numAtt4);
       map.put("remark",this.remark);
	   map.put("u3dModelPath",this.u3dModelPath);

       return map;
    }*/

	public String getPutawayStateName() {
		return putawayStateName;
	}

	public void setPutawayStateName(String putawayStateName) {
		this.putawayStateName = putawayStateName;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}
    
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	private String materialId;
    
	
	//合并数量
	private Integer mergeCount;
	
	public Integer getMergeCount() {
		return mergeCount;
	}

	public void setMergeCount(Integer mergeCount) {
		this.mergeCount = mergeCount;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductStyle() {
		return productStyle;
	}

	public void setProductStyle(String productStyle) {
		this.productStyle = productStyle;
	}

	public String getProductColor() {
		return productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public String getProductPicPath() {
		return productPicPath;
	}

	public void setProductPicPath(String productPicPath) {
		this.productPicPath = productPicPath;
	}

	public List getColorlist() {
		return colorlist;
	}

	public void setColorlist(List colorlist) {
		this.colorlist = colorlist;
	}

	public List getSmallPiclist() {
		return smallPiclist;
	}

	public void setSmallPiclist(List smallPiclist) {
		this.smallPiclist = smallPiclist;
	}

	public List getMateriallist() {
		return materiallist;
	}

	public void setMateriallist(List materiallist) {
		this.materiallist = materiallist;
	}

	public String getProductTypeKey() {
		return productTypeKey;
	}

	public void setProductTypeKey(String productTypeKey) {
		this.productTypeKey = productTypeKey;
	}
	
	public String getProductColorKey() {
		return productColorKey;
	}

	public void setProductColorKey(String productColorKey) {
		this.productColorKey = productColorKey;
	}

	public String getProductSmallType() {
		return productSmallType;
	}

	public void setProductSmallType(String productSmallType) {
		this.productSmallType = productSmallType;
	}
	
	//产品推荐状态
	private Integer recommendState;
	
	public Integer getRecommendState() {
		return recommendState;
	}

	public void setRecommendState(Integer recommendState) {
		this.recommendState = recommendState;
	}
	
	/** 产品挂节点路径 **/
	private String posIndexPath;
	/**  产品序号  **/
	private String productSequence;
	/**方案Id**/
	private int planId;
	/**产品类型 （软装4，硬装1，软装白模3，硬装白模2）**/
	private String isProductType ;
	/**编辑页面编辑code权限 （0，1）**/
	private Integer editCodeState;
	/**编辑页面编辑codeList**/
	List<String> editCodeList = new ArrayList<String>();
	/** 规则json **/
	private Map<String,String> rulesMap;
	
	
	public Map<String, String> getRulesMap() {
		return rulesMap;
	}

	public void setRulesMap(Map<String, String> rulesMap) {
		this.rulesMap = rulesMap;
	}

	public List<String> getEditCodeList() {
		return editCodeList;
	}

	public void setEditCodeList(List<String> editCodeList) {
		this.editCodeList = editCodeList;
	}

	public Integer getEditCodeState() {
		return editCodeState;
	}

	public void setEditCodeState(Integer editCodeState) {
		this.editCodeState = editCodeState;
	}

	public String getIsProductType() {
		return isProductType;
	}

	public void setIsProductType(String isProductType) {
		this.isProductType = isProductType;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getPosIndexPath() {
		return posIndexPath;
	}

	public void setPosIndexPath(String posIndexPath) {
		this.posIndexPath = posIndexPath;
	}

	public String getProductSequence() {
		return productSequence;
	}

	public void setProductSequence(String productSequence) {
		this.productSequence = productSequence;
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
	
	//产品大分类名称
	private String productTypeName;

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	
	//组合产品ID
	private Integer groupId;
	//贴图选中状态1，为选中
	private Integer selectedState;
	//是否是内部用户
	private String isInternalUser;
	
	private String bigTypeValueKey;
	
	private String smallTypeValueKey;
	
	private Integer groupCount;
	
	private String smallTypeAtt1;
	
	private String bigTypeName;
	
	private String smallTypeName;
	
	public String getBigTypeName() {
		return bigTypeName;
	}

	public void setBigTypeName(String bigTypeName) {
		this.bigTypeName = bigTypeName;
	}

	public String getSmallTypeName() {
		return smallTypeName;
	}

	public void setSmallTypeName(String smallTypeName) {
		this.smallTypeName = smallTypeName;
	}

	public String getSmallTypeAtt1() {
		return smallTypeAtt1;
	}

	public void setSmallTypeAtt1(String smallTypeAtt1) {
		this.smallTypeAtt1 = smallTypeAtt1;
	}

	public String getBigTypeValueKey() {
		return bigTypeValueKey;
	}

	public void setBigTypeValueKey(String bigTypeValueKey) {
		this.bigTypeValueKey = bigTypeValueKey;
	}

	public String getSmallTypeValueKey() {
		return smallTypeValueKey;
	}

	public void setSmallTypeValueKey(String smallTypeValueKey) {
		this.smallTypeValueKey = smallTypeValueKey;
	}

	public Integer getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}

	public String getIsInternalUser() {
		return isInternalUser;
	}

	public void setIsInternalUser(String isInternalUser) {
		this.isInternalUser = isInternalUser;
	}

	public Integer getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(Integer selectedState) {
		this.selectedState = selectedState;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}
