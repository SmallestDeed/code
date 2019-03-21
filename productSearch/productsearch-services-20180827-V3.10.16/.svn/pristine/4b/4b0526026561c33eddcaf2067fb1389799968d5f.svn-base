package com.nork.design.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.productprops.model.ProductPropsSimple;

/**
 * 按类型组装产品列表
 * 类型:单品/结构/组合
 *
 * @author huangsongbo
 *
 */
public class ProductListByTypeInfo implements Serializable{

	private static final long serialVersionUID = 2610844695301282526L;

	/**
	 * 单品信息
	 */
	private List<PlanProductInfo> productList;
	
	/**
	 * 结构信息
	 */
	private PlanStructureInfo structureInfo;
	
	/**
	 * 组合信息
	 */
	
	private PlanGroupInfo groupInfo;
	/**
	 * 产品归类
	 * 组装成Map<大类valukey, 产品list>的格式
	 */
	private Map<String, List<PlanProductInfo>> productListMap;
	
	/**
	 * 一件装修新建的设计方案id
	 */
	private Integer planId;
	
	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Map<String, List<PlanProductInfo>> getProductListMap() {
		return productListMap;
	}

	public void setProductListMap(Map<String, List<PlanProductInfo>> productListMap) {
		this.productListMap = productListMap;
	}

	public List<PlanProductInfo> getProductList() {
		return productList;
	}

	public void setProductList(List<PlanProductInfo> productList) {
		this.productList = productList;
	}

	public PlanStructureInfo getStructureInfo() {
		return structureInfo;
	}

	public void setStructureInfo(PlanStructureInfo structureInfo) {
		this.structureInfo = structureInfo;
	}

	public PlanGroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(PlanGroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	public static class PlanProductInfo implements Cloneable, Serializable{

		private static final long serialVersionUID = 7168106563769294849L;

		/**
		 * 产品id
		 */
		private Integer productId;
		
		/**
		 * 产品大类valuekey
		 */
		private String bigTypeValuekey;
		
		/**
		 * 产品小类valuekey
		 */
		private String smallTypeValuekey;

		/**
		 * posName;
		 */
		private String posName;
		
		/**
		 * init产品(初始产品)大类valuekey
		 */
		private String bigTypeValuekeyInit;
		
		/**
		 * init产品(初始产品)小类valuekey
		 */
		private String smallTypeValuekeyInit;
		
		/**
		 * 产品编码
		 */
		private String productCode;
		
		/**
		 * 产品长度
		 */
		private String productLength;
		
		/**
		 * 产品宽度
		 */
		private String productWidth;
		
		/**
		 * 产品高度
		 */
		private String productHeight;
		
		/**
		 * 背景墙全铺长度
		 */
		private String fullPaveLength;
		
		/**
		 * 产品长度匹配条件(start)
		 */
		private Integer productLengthStart;
		
		/**
		 * 产品长度匹配条件(end)
		 */
		private Integer productLengthEnd;
		
		/**
		 * 该产品是否被匹配过
		 */
		private boolean isMatched = false;
		
		/**
		 * 墙体类型
		 */
		private String wallOrientation;
		
		/**
		 * 天花尺寸代码
		 */
		private String measureCode;
		
		/**
		 * 区域标识
		 */
		private String regionMark;
		
		/**
		 * 区域标识匹配前缀
		 * 区域标识 like "#{regionMarkLikeStart}%"
		 */
		private String regionMarkLikeStart;
		
		/**
		 * 区域标识匹配前缀过滤(not like)
		 * 区域标识 not like "#{regionMarkLikeStart}%"
		 */
		private String regionMarkNotLikeStart;
		
		/**
		 * 墙体类型
		 */
		private String wallType;
		
		/**
		 * 是否是组合中的主产品
		 */
		private Integer isMainProduct;
		
		/**
		 * 对应样板房产品表/推荐方案产品表中的plan_group_id信息(组合id+唯一标识信息)
		 */
		private String planGroupId;

		/**
		 * 结构中心点信息
		 */
		private String center;
		
		/**
		 * 组合类型
		 */
		private Integer groupType;

		/**
		 * 产品对应的样板房产品表id
		 */
		private Integer planProductId;
		
		/**
		 * isHide(样板房产品表信息)
		 */
		private Integer isHide;
		
		/**
		 * 初始化白膜id
		 */
		private Integer initProductId;
		
		/**
		 * 组合/结构id
		 */
		private Integer groupOrStructureId;
		
		/**
		 * 绑定点信息(样板房产品表信息)
		 */
		private String bindParentProductId;
		
		/**
		 * 多材质选择信息
		 */
		private String splitTexturesChooseInfo;
		
		/**
		 * 是否是标准结构(推荐方案产品表信息)
		 */
		private Integer isStandard;
		
		/**
		 * 款式(推荐方案产品表信息)
		 */
		private Integer styleId;
		
		/**
		 * productIndex(推荐方案产品表信息)
		 */
		private Integer productIndex;
		
		/**
		 * isMainStructureProduct(推荐方案产品表信息)
		 */
		private Integer isMainStructureProduct;
		
		/**
		 * isGroupReplaceWay(推荐方案产品表信息)
		 */
		private Integer isGroupReplaceWay;
		
		/**
		 * 搜素id,定制产品对应的白膜id(bm_ids)
		 */
		private String bmIds;
		
		/**
		 * 背景墙匹配优先级(分数越高优先级越高)
		 */
		private Integer wallTypeScore;
		
		/**
		 * 匹配信息
		 */
		private String matchInfo;
		
		/**
		 * 组合中产品的唯一标识
		 */
		private String groupProductUniqueId;
		
		/**
		 * 多小类搜索条件
		 */
		private List<Integer> smallTypeValueList;
		
		/**
		 * 结构单品属性(用于匹配结构中的白膜)
		 */
		private String structureProductProp;
		
		/**
		 * 初始化白膜code
		 */
		private String initProductCode;
		
		/**
		 * 小类valuekey排序(靠前)
		 */
		private String orderSmallTypeValueKey;
		
		/**
		 * 小类value排序(靠前)
		 */
		private Integer orderSmallTypeValue;

		/**
		 * 款式排序
		 */
		private Integer orderStyleId;
		
		/**
		 * 布局标识搜索条件
		 */
		private String productSmallpoxIdentify;
		
		/**
		 * 结构布局标识
		 */
		private String structureProductSmallpoxIdentify;
		
		/**
		 * 布局标识搜索条件List
		 * add by huangsongbo 2017.11.23
		 */
		private List<String> productSmallpoxIdentifyList;
		
		/**
		 * 结构布局标识List
		 * add by huangsongbo 2017.11.23
		 */
		private List<String> structureProductSmallpoxIdentifyList;
		
		/**
		 * 过滤属性信息(用于组合/单品匹配过滤属性)
		 */
		private List<ProductPropsSimple> productFilterPropList;
		
		private boolean isBeijing;
		
		/**
		 * 多小类搜索条件(valuekeyList)
		 */
		private List<String> smallTypeValueKeyList;
		
		/**
		 * 品牌排序,本身品牌>无品牌>其他品牌
		 */
		private Integer orderBrandId;
		
		/**
		 * 型号排序,本身型号>其他型号
		 */
		private String orderProductModelNumber;
		
		/**
		 * 产品品牌id
		 */
		private Integer brandId;
		
		/**
		 * 产品型号
		 */
		private String productModelNumber;
		
		/**
		 * 系列标识
		 */
		private String seriesSign;
		
		/**
		 * 产品系列id
		 */
		private Integer seriesId;
		
		/**
		 * 产品搜索条件:小类list(排除)
		 */
		private List<Integer> excludeSmallTypeValueList;
		
		/**
		 * 布局标识排序
		 */
		private String orderProductSmallpoxIdentify;
		
		public List<Integer> getExcludeSmallTypeValueList() {
			return excludeSmallTypeValueList;
		}

		public void setExcludeSmallTypeValueList(List<Integer> excludeSmallTypeValueList) {
			this.excludeSmallTypeValueList = excludeSmallTypeValueList;
		}
		
		public String getOrderProductSmallpoxIdentify() {
			return orderProductSmallpoxIdentify;
		}

		public void setOrderProductSmallpoxIdentify(String orderProductSmallpoxIdentify) {
			this.orderProductSmallpoxIdentify = orderProductSmallpoxIdentify;
		}

		public List<String> getProductSmallpoxIdentifyList() {
			return productSmallpoxIdentifyList;
		}

		public void setProductSmallpoxIdentifyList(List<String> productSmallpoxIdentifyList) {
			this.productSmallpoxIdentifyList = productSmallpoxIdentifyList;
		}

		public List<String> getStructureProductSmallpoxIdentifyList() {
			return structureProductSmallpoxIdentifyList;
		}

		public void setStructureProductSmallpoxIdentifyList(List<String> structureProductSmallpoxIdentifyList) {
			this.structureProductSmallpoxIdentifyList = structureProductSmallpoxIdentifyList;
		}

		public Integer getSeriesId() {
			return seriesId;
		}

		public void setSeriesId(Integer seriesId) {
			this.seriesId = seriesId;
		}

		public String getSeriesSign() {
			return seriesSign;
		}

		public void setSeriesSign(String seriesSign) {
			this.seriesSign = seriesSign;
		}

		public Integer getBrandId() {
			return brandId;
		}

		public void setBrandId(Integer brandId) {
			this.brandId = brandId;
		}

		public String getProductModelNumber() {
			return productModelNumber;
		}

		public void setProductModelNumber(String productModelNumber) {
			this.productModelNumber = productModelNumber;
		}

		public Integer getOrderBrandId() {
			return orderBrandId;
		}

		public void setOrderBrandId(Integer orderBrandId) {
			this.orderBrandId = orderBrandId;
		}

		public String getOrderProductModelNumber() {
			return orderProductModelNumber;
		}

		public void setOrderProductModelNumber(String orderProductModelNumber) {
			this.orderProductModelNumber = orderProductModelNumber;
		}

		public List<String> getSmallTypeValueKeyList() {
			return smallTypeValueKeyList;
		}

		public void setSmallTypeValueKeyList(List<String> smallTypeValueKeyList) {
			this.smallTypeValueKeyList = smallTypeValueKeyList;
		}

		public boolean isBeijing() {
			return isBeijing;
		}

		public void setBeijing(boolean isBeijing) {
			this.isBeijing = isBeijing;
		}

		public List<ProductPropsSimple> getProductFilterPropList() {
			return productFilterPropList;
		}

		public void setProductFilterPropList(List<ProductPropsSimple> productFilterPropList) {
			this.productFilterPropList = productFilterPropList;
		}

		public String getStructureProductSmallpoxIdentify() {
			return structureProductSmallpoxIdentify;
		}

		public void setStructureProductSmallpoxIdentify(String structureProductSmallpoxIdentify) {
			this.structureProductSmallpoxIdentify = structureProductSmallpoxIdentify;
		}

		public String getProductSmallpoxIdentify() {
			return productSmallpoxIdentify;
		}

		public void setProductSmallpoxIdentify(String productSmallpoxIdentify) {
			this.productSmallpoxIdentify = productSmallpoxIdentify;
		}

		public Integer getOrderStyleId() {
			return orderStyleId;
		}

		public void setOrderStyleId(Integer orderStyleId) {
			this.orderStyleId = orderStyleId;
		}

		public String getOrderSmallTypeValueKey() {
			return orderSmallTypeValueKey;
		}

		public void setOrderSmallTypeValueKey(String orderSmallTypeValueKey) {
			this.orderSmallTypeValueKey = orderSmallTypeValueKey;
		}

		public Integer getOrderSmallTypeValue() {
			return orderSmallTypeValue;
		}

		public void setOrderSmallTypeValue(Integer orderSmallTypeValue) {
			this.orderSmallTypeValue = orderSmallTypeValue;
		}

		public String getInitProductCode() {
			return initProductCode;
		}

		public void setInitProductCode(String initProductCode) {
			this.initProductCode = initProductCode;
		}

		public String getStructureProductProp() {
			return structureProductProp;
		}

		public void setStructureProductProp(String structureProductProp) {
			this.structureProductProp = structureProductProp;
		}
		
		public List<Integer> getSmallTypeValueList() {
			return smallTypeValueList;
		}

		public void setSmallTypeValueList(List<Integer> smallTypeValueList) {
			this.smallTypeValueList = smallTypeValueList;
		}

		public String getGroupProductUniqueId() {
			return groupProductUniqueId;
		}

		public void setGroupProductUniqueId(String groupProductUniqueId) {
			this.groupProductUniqueId = groupProductUniqueId;
		}

		public String getMatchInfo() {
			return matchInfo;
		}

		public void setMatchInfo(String matchInfo) {
			this.matchInfo = matchInfo;
		}

		public Integer getWallTypeScore() {
			return wallTypeScore;
		}

		public void setWallTypeScore(Integer wallTypeScore) {
			this.wallTypeScore = wallTypeScore;
		}

		public String getBmIds() {
			return bmIds;
		}

		public void setBmIds(String bmIds) {
			this.bmIds = bmIds;
		}

		public Integer getIsMainStructureProduct() {
			return isMainStructureProduct;
		}

		public void setIsMainStructureProduct(Integer isMainStructureProduct) {
			this.isMainStructureProduct = isMainStructureProduct;
		}

		public Integer getIsGroupReplaceWay() {
			return isGroupReplaceWay;
		}

		public void setIsGroupReplaceWay(Integer isGroupReplaceWay) {
			this.isGroupReplaceWay = isGroupReplaceWay;
		}

		public Integer getProductIndex() {
			return productIndex;
		}

		public void setProductIndex(Integer productIndex) {
			this.productIndex = productIndex;
		}

		public Integer getStyleId() {
			return styleId;
		}

		public void setStyleId(Integer styleId) {
			this.styleId = styleId;
		}

		public Integer getIsStandard() {
			return isStandard;
		}

		public void setIsStandard(Integer isStandard) {
			this.isStandard = isStandard;
		}

		public String getSplitTexturesChooseInfo() {
			return splitTexturesChooseInfo;
		}

		public void setSplitTexturesChooseInfo(String splitTexturesChooseInfo) {
			this.splitTexturesChooseInfo = splitTexturesChooseInfo;
		}

		public String getBindParentProductId() {
			return bindParentProductId;
		}

		public void setBindParentProductId(String bindParentProductId) {
			this.bindParentProductId = bindParentProductId;
		}

		public Integer getGroupOrStructureId() {
			return groupOrStructureId;
		}

		public void setGroupOrStructureId(Integer groupOrStructureId) {
			this.groupOrStructureId = groupOrStructureId;
		}

		public Integer getInitProductId() {
			return initProductId;
		}

		public void setInitProductId(Integer initProductId) {
			this.initProductId = initProductId;
		}

		public Integer getIsHide() {
			return isHide;
		}

		public void setIsHide(Integer isHide) {
			this.isHide = isHide;
		}

		public Integer getPlanProductId() {
			return planProductId;
		}

		public void setPlanProductId(Integer planProductId) {
			this.planProductId = planProductId;
		}

		public Integer getGroupType() {
			return groupType;
		}

		public void setGroupType(Integer groupType) {
			this.groupType = groupType;
		}

		public String getCenter() {
			return center;
		}

		public void setCenter(String center) {
			this.center = center;
		}

		public String getPlanGroupId() {
			return planGroupId;
		}

		public void setPlanGroupId(String planGroupId) {
			this.planGroupId = planGroupId;
		}

		public Integer getIsMainProduct() {
			return isMainProduct;
		}

		public void setIsMainProduct(Integer isMainProduct) {
			this.isMainProduct = isMainProduct;
		}

		public String getWallType() {
			return wallType;
		}

		public void setWallType(String wallType) {
			this.wallType = wallType;
		}

		public String getRegionMarkNotLikeStart() {
			return regionMarkNotLikeStart;
		}

		public void setRegionMarkNotLikeStart(String regionMarkNotLikeStart) {
			this.regionMarkNotLikeStart = regionMarkNotLikeStart;
		}

		public String getRegionMarkLikeStart() {
			return regionMarkLikeStart;
		}

		public void setRegionMarkLikeStart(String regionMarkLikeStart) {
			this.regionMarkLikeStart = regionMarkLikeStart;
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

		public String getWallOrientation() {
			return wallOrientation;
		}

		public void setWallOrientation(String wallOrientation) {
			this.wallOrientation = wallOrientation;
		}

		public boolean isMatched() {
			return isMatched;
		}

		public void setMatched(boolean isMatched) {
			this.isMatched = isMatched;
		}

		public Integer getProductLengthStart() {
			return productLengthStart;
		}

		public void setProductLengthStart(Integer productLengthStart) {
			this.productLengthStart = productLengthStart;
		}

		public Integer getProductLengthEnd() {
			return productLengthEnd;
		}

		public void setProductLengthEnd(Integer productLengthEnd) {
			this.productLengthEnd = productLengthEnd;
		}

		public String getFullPaveLength() {
			return fullPaveLength;
		}

		public void setFullPaveLength(String fullPaveLength) {
			this.fullPaveLength = fullPaveLength;
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

		public String getProductCode() {
			return productCode;
		}

		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}

		public String getBigTypeValuekeyInit() {
			return bigTypeValuekeyInit;
		}

		public void setBigTypeValuekeyInit(String bigTypeValuekeyInit) {
			this.bigTypeValuekeyInit = bigTypeValuekeyInit;
		}

		public String getSmallTypeValuekeyInit() {
			return smallTypeValuekeyInit;
		}

		public void setSmallTypeValuekeyInit(String smallTypeValuekeyInit) {
			this.smallTypeValuekeyInit = smallTypeValuekeyInit;
		}

		public String getPosName() {
			return posName;
		}

		public void setPosName(String posName) {
			this.posName = posName;
		}

		public Integer getProductId() {
			return productId;
		}

		public void setProductId(Integer productId) {
			this.productId = productId;
		}

		public String getBigTypeValuekey() {
			return bigTypeValuekey;
		}

		public void setBigTypeValuekey(String bigTypeValuekey) {
			this.bigTypeValuekey = bigTypeValuekey;
		}

		public String getSmallTypeValuekey() {
			return smallTypeValuekey;
		}

		public void setSmallTypeValuekey(String smallTypeValuekey) {
			this.smallTypeValuekey = smallTypeValuekey;
		}
		
		public PlanProductInfo clone() {
			try {
				return (PlanProductInfo) super.clone();
			} catch (CloneNotSupportedException e) {
				return null;
			}
		}

	}
	
	public class PlanStructureInfo implements Serializable{
		
		private static final long serialVersionUID = -6655584381529104965L;

		/**
		 * Map<plan_group_id, PlanProductInfo> plan_group_id对主产品信息的map
		 */
		Map<String, PlanProductInfo> mainProudctMap = new HashMap<String, PlanProductInfo>();
		
		/**
		 * plan_group_id对结构白膜List信息的map
		 */
		Map<String, List<PlanProductInfo>> structureProudctListMap = new HashMap<String, List<PlanProductInfo>>();

		public Map<String, PlanProductInfo> getMainProudctMap() {
			return mainProudctMap;
		}

		public void setMainProudctMap(Map<String, PlanProductInfo> mainProudctMap) {
			this.mainProudctMap = mainProudctMap;
		}

		public Map<String, List<PlanProductInfo>> getStructureProudctListMap() {
			return structureProudctListMap;
		}

		public void setStructureProudctListMap(Map<String, List<PlanProductInfo>> structureProudctListMap) {
			this.structureProudctListMap = structureProudctListMap;
		}
		
	}
	
	public class PlanGroupInfo implements Serializable{
		
		private static final long serialVersionUID = -854030339720301228L;

		/**
		 * Map<plan_group_id, PlanProductInfo> plan_group_id对主产品信息的map
		 */
		Map<String, PlanProductInfo> mainProudctMap = new HashMap<String, PlanProductInfo>();
		
		/**
		 * plan_group_id对结构白膜List信息的map
		 */
		Map<String, List<PlanProductInfo>> groupProudctListMap = new HashMap<String, List<PlanProductInfo>>();

		public Map<String, PlanProductInfo> getMainProudctMap() {
			return mainProudctMap;
		}

		public void setMainProudctMap(Map<String, PlanProductInfo> mainProudctMap) {
			this.mainProudctMap = mainProudctMap;
		}

		public Map<String, List<PlanProductInfo>> getGroupProudctListMap() {
			return groupProudctListMap;
		}

		public void setGroupProudctListMap(Map<String, List<PlanProductInfo>> groupProudctListMap) {
			this.groupProudctListMap = groupProudctListMap;
		}
		
	}
	
}
