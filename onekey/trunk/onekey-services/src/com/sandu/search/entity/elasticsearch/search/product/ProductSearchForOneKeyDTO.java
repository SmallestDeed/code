package com.sandu.search.entity.elasticsearch.search.product;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 一键装修搜索产品接口参数
 * @author huangsongbo
 *
 */
public class ProductSearchForOneKeyDTO implements Serializable{

	private static final long serialVersionUID = -6708429836817094050L;
	
	/**
	 * 产品大类value
	 */
	private String productTypeValue;
	
	/**
	 * 产品小类value
	 */
	private Integer productSmallTypeValue;
	
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
	 * 产品长度范围start
	 */
	private Integer productLengthStartInteger;
	
	/**
	 * 产品长度范围end
	 */
	private Integer productLengthEndInteger;
	
	/**
	 * 尺寸代码
	 */
	private String measureCode;
	
	/**
	 * 款式id
	 */
	private Integer styleId;
	
	/**
	 * 白膜id(定制产品对应的白膜id)
	 */
	private String bmIds;
	
	/**
	 * 墙体类型
	 */
	private String wallType;
	
	/**
	 * 布局标识
	 */
	private String productSmallpoxIdentify;
	
	/**
	 * 布局标识list
	 */
	private List<String> identifyList;
	
	/**
	 * 产品过滤属性
	 */
	/*private List<ProductPropsSimple> productFilterPropList;*/
	private Map<String, String> productFilterAttributeMap;
	
	/**
	 * 是否是背景墙
	 */
	private boolean isBeijing;
	
	/**
	 * 系列id
	 */
	private Integer seriesId;
	
	/**
	 * 产品小类list(排除)
	 */
	private List<Integer> excludeSmallTypeValueList;
	
	/**
	 * 小类list
	 */
	private List<Integer> smallTypeValueListForShowAll;
	
	/**
	 * 产品状态list
	 */
	private List<Integer> putawayStateList;
	
	private Integer isDeleted;
	
	// 排序条件 ->start
	
	/**
	 * 排序条件:为该小类value的产品排前面
	 */
	private Integer orderSmallTypeValue;
	
	/**
	 * 排序条件:为该styleId的产品排前面
	 */
	private Integer orderStyleId;
	
	/**
	 * 排序条件:等于该brandId的产品排前面
	 */
	private Integer orderBrandId;
	
	/**
	 * 排序条件:等于该productModelNumber的产品排前面
	 */
	private String orderProductModelNumber;
	
	/**
	 * 排序条件:等于该布局标识的产品排前面
	 */
	private String orderProductSmallpoxIdentify;
	
	/**
	 * 排序条件:产品长度最接近orderAbsProuductLength的产品排前面
	 */
	private Integer orderAbsProuductLength;
	
	// 排序条件 ->end
	
	private Integer start;
	
	private Integer limit;

	/**
	 * 产品长度下限(搜索条件)
	 */
	private Integer productHeightStart;
	
	/**
	 * 产品长度上限(搜索条件)
	 */
	private Integer productHeightEnd;
	
	public Integer getProductHeightStart() {
		return productHeightStart;
	}

	public void setProductHeightStart(Integer productHeightStart) {
		this.productHeightStart = productHeightStart;
	}

	public Integer getProductHeightEnd() {
		return productHeightEnd;
	}

	public void setProductHeightEnd(Integer productHeightEnd) {
		this.productHeightEnd = productHeightEnd;
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

	public String getProductTypeValue() {
		return productTypeValue;
	}

	public void setProductTypeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
	}

	public Integer getProductSmallTypeValue() {
		return productSmallTypeValue;
	}

	public void setProductSmallTypeValue(Integer productSmallTypeValue) {
		this.productSmallTypeValue = productSmallTypeValue;
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

	public Integer getProductLengthStartInteger() {
		return productLengthStartInteger;
	}

	public void setProductLengthStartInteger(Integer productLengthStartInteger) {
		this.productLengthStartInteger = productLengthStartInteger;
	}

	public Integer getProductLengthEndInteger() {
		return productLengthEndInteger;
	}

	public void setProductLengthEndInteger(Integer productLengthEndInteger) {
		this.productLengthEndInteger = productLengthEndInteger;
	}

	public String getMeasureCode() {
		return measureCode;
	}

	public void setMeasureCode(String measureCode) {
		this.measureCode = measureCode;
	}

	public Integer getStyleId() {
		return styleId;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}

	public String getBmIds() {
		return bmIds;
	}

	public void setBmIds(String bmIds) {
		this.bmIds = bmIds;
	}

	public String getWallType() {
		return wallType;
	}

	public void setWallType(String wallType) {
		this.wallType = wallType;
	}

	public String getProductSmallpoxIdentify() {
		return productSmallpoxIdentify;
	}

	public void setProductSmallpoxIdentify(String productSmallpoxIdentify) {
		this.productSmallpoxIdentify = productSmallpoxIdentify;
	}

	public List<String> getIdentifyList() {
		return identifyList;
	}

	public void setIdentifyList(List<String> identifyList) {
		this.identifyList = identifyList;
	}

	public Map<String, String> getProductFilterAttributeMap() {
		return productFilterAttributeMap;
	}

	public void setProductFilterAttributeMap(Map<String, String> productFilterAttributeMap) {
		this.productFilterAttributeMap = productFilterAttributeMap;
	}

	public boolean isBeijing() {
		return isBeijing;
	}

	public void setBeijing(boolean isBeijing) {
		this.isBeijing = isBeijing;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public List<Integer> getExcludeSmallTypeValueList() {
		return excludeSmallTypeValueList;
	}

	public void setExcludeSmallTypeValueList(List<Integer> excludeSmallTypeValueList) {
		this.excludeSmallTypeValueList = excludeSmallTypeValueList;
	}

	public List<Integer> getSmallTypeValueListForShowAll() {
		return smallTypeValueListForShowAll;
	}

	public void setSmallTypeValueListForShowAll(List<Integer> smallTypeValueListForShowAll) {
		this.smallTypeValueListForShowAll = smallTypeValueListForShowAll;
	}

	public Integer getOrderSmallTypeValue() {
		return orderSmallTypeValue;
	}

	public void setOrderSmallTypeValue(Integer orderSmallTypeValue) {
		this.orderSmallTypeValue = orderSmallTypeValue;
	}

	public Integer getOrderStyleId() {
		return orderStyleId;
	}

	public void setOrderStyleId(Integer orderStyleId) {
		this.orderStyleId = orderStyleId;
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

	public String getOrderProductSmallpoxIdentify() {
		return orderProductSmallpoxIdentify;
	}

	public void setOrderProductSmallpoxIdentify(String orderProductSmallpoxIdentify) {
		this.orderProductSmallpoxIdentify = orderProductSmallpoxIdentify;
	}

	public List<Integer> getPutawayStateList() {
		return putawayStateList;
	}

	public void setPutawayStateList(List<Integer> putawayStateList) {
		this.putawayStateList = putawayStateList;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getOrderAbsProuductLength() {
		return orderAbsProuductLength;
	}

	public void setOrderAbsProuductLength(Integer orderAbsProuductLength) {
		this.orderAbsProuductLength = orderAbsProuductLength;
	}
	
}