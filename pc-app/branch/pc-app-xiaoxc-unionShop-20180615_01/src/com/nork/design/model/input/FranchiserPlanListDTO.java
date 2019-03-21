package com.nork.design.model.input;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.nork.design.model.constant.PlanSourceEnum;

/**
 * function:WebDesignPlanRecommendedControllerV2.franchiserPlanList参数
 * 
 * @author huangsongbo
 * @date 2018.3.28
 *
 */
public class FranchiserPlanListDTO implements Serializable{

	private static final long serialVersionUID = 7025229736728938758L;

	@NotNull(message = "参数msgId不能为空")
	private String msgId;
	
	/**
	 * 数据来源
	 * 依据enum:PlanSourceEnum
	 */
	@NotNull
	private String planSource;

	/**
	 * 查询条件:公司id
	 */
	private Integer companyId;
	
	/**
	 * 方案名称
	 */
	private String planName;
	
	/**
	 * 空间类型
	 */
	private Integer houseType;
	
	/**
	 * 设计风格id
	 */
	private Integer designRecommendedStyleId;
	
	/**
	 * 方案类型
	 * ONEKEY : 智能方案
	 * OPEN : 公开方案
	 * ...
	 */
	private String shelfStatus;
	
	/**
	 * "" : 全部
	 * 2b : 渠道管理
	 * 2c : 线上管理
	 * 2b,2c : 渠道+线上管理
	 * default : 未分配
	 */
	private String platformType;
	
	/**
	 * 查询条件:公司idList
	 */
	private List<Integer> baseCompanyIdList;
	
	/**
	 * 查询条件shelfStatus like
	 */
	private String shelfStatusLike;
	
	/**
	 * 查询条件shelfStatus not like
	 */
	private String shelfStatusNotLike;
	
	/**
	 * platformType = 2b platformTypeButton = true:意为查看2b平台的推荐方案
	 * platformType = 2b platformTypeButton = false:意为查看非2b平台的推荐方案
	 */
	private boolean platformTypeButton;
	
	/**
	 * 查看平台类型
	 */
	private List<String> platformTypeList;
	
	/**
	 * 平台id
	 */
	private Integer platformId;
	
	/**
	 * 平台code
	 */
	private String platformCode;
	
	private Integer start;
	
	private Integer limit;

	/**
	 * 是否发布,对应constant:RecommendedDecorateState
	 */
	private Integer isRelease;
	
	/**
	 * 1:普通方案
	 * 2:智能方案
	 */
	private Integer recommendedType;
	
	/**
	 * 数据排序字段
	 */
	private String order;
	
	/**
	 * 数据排序顺序
	 */
	private String orderNum;
	
	public Integer getRecommendedType() {
		return recommendedType;
	}

	public void setRecommendedType(Integer recommendedType) {
		this.recommendedType = recommendedType;
	}

	public Integer getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
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

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public List<String> getPlatformTypeList() {
		return platformTypeList;
	}

	public void setPlatformTypeList(List<String> platformTypeList) {
		this.platformTypeList = platformTypeList;
	}

	public boolean getPlatformTypeButton() {
		return platformTypeButton;
	}

	public void setPlatformTypeButton(boolean platformTypeButton) {
		this.platformTypeButton = platformTypeButton;
	}

	public String getShelfStatusLike() {
		return shelfStatusLike;
	}

	public void setShelfStatusLike(String shelfStatusLike) {
		this.shelfStatusLike = shelfStatusLike;
	}

	public String getShelfStatusNotLike() {
		return shelfStatusNotLike;
	}

	public void setShelfStatusNotLike(String shelfStatusNotLike) {
		this.shelfStatusNotLike = shelfStatusNotLike;
	}

	public List<Integer> getBaseCompanyIdList() {
		return baseCompanyIdList;
	}

	public void setBaseCompanyIdList(List<Integer> baseCompanyIdList) {
		this.baseCompanyIdList = baseCompanyIdList;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getHouseType() {
		return houseType;
	}

	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}

	public Integer getDesignRecommendedStyleId() {
		return designRecommendedStyleId;
	}

	public void setDesignRecommendedStyleId(Integer designRecommendedStyleId) {
		this.designRecommendedStyleId = designRecommendedStyleId;
	}

	public String getShelfStatus() {
		return shelfStatus;
	}

	public void setShelfStatus(String shelfStatus) {
		this.shelfStatus = shelfStatus;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getPlanSource() {
		return planSource;
	}

	public void setPlanSource(String planSource) {
		this.planSource = planSource;
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

}
