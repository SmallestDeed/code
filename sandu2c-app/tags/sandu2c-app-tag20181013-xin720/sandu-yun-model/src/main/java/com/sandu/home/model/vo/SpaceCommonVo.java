package com.sandu.home.model.vo;

import java.io.Serializable;

/**
 * 空间Vo
 */
public class SpaceCommonVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer spaceCommonId;// 空间id
	private String spaceCode;// 空间编码
	private String spaceName;// 空回名称
	private Integer spaceFunctionId;// 空间类型
	private String spaceAreas;// 空间面积
	private String spaceShape;// 空间形状
	private Integer spaceStatus;// 空间状态
	private Integer isStandardSpace;// 是否是标准空间
	private Integer totalUsageAmount;// 空间使用次数
	private String spaceviewPlanPathPic;// 空间图片
	private String spaceviewPlanSmallPathPic;// 空间缩略图

	public Integer getSpaceCommonId() {
		return spaceCommonId;
	}

	public void setSpaceCommonId(Integer spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}

	public String getSpaceCode() {
		return spaceCode;
	}

	public void setSpaceCode(String spaceCode) {
		this.spaceCode = spaceCode;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public Integer getSpaceFunctionId() {
		return spaceFunctionId;
	}

	public void setSpaceFunctionId(Integer spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}

	public String getSpaceAreas() {
		return spaceAreas;
	}

	public void setSpaceAreas(String spaceAreas) {
		this.spaceAreas = spaceAreas;
	}

	public String getSpaceShape() {
		return spaceShape;
	}

	public void setSpaceShape(String spaceShape) {
		this.spaceShape = spaceShape;
	}

	public Integer getSpaceStatus() {
		return spaceStatus;
	}

	public void setSpaceStatus(Integer spaceStatus) {
		this.spaceStatus = spaceStatus;
	}

	public Integer getIsStandardSpace() {
		return isStandardSpace;
	}

	public void setIsStandardSpace(Integer isStandardSpace) {
		this.isStandardSpace = isStandardSpace;
	}

	public Integer getTotalUsageAmount() {
		return totalUsageAmount;
	}

	public void setTotalUsageAmount(Integer totalUsageAmount) {
		this.totalUsageAmount = totalUsageAmount;
	}

	public String getSpaceviewPlanPathPic() {
		return spaceviewPlanPathPic;
	}

	public void setSpaceviewPlanPathPic(String spaceviewPlanPathPic) {
		this.spaceviewPlanPathPic = spaceviewPlanPathPic;
	}

	public String getSpaceviewPlanSmallPathPic() {
		return spaceviewPlanSmallPathPic;
	}

	public void setSpaceviewPlanSmallPathPic(String spaceviewPlanSmallPathPic) {
		this.spaceviewPlanSmallPathPic = spaceviewPlanSmallPathPic;
	}

	@Override
	public String toString() {
		return "SpaceCommonVo [spaceCommonId=" + spaceCommonId + ", spaceCode=" + spaceCode + ", spaceName=" + spaceName
				+ ", spaceFunctionId=" + spaceFunctionId + ", spaceAreas=" + spaceAreas + ", spaceShape=" + spaceShape
				+ ", spaceStatus=" + spaceStatus + ", isStandardSpace=" + isStandardSpace + ", totalUsageAmount="
				+ totalUsageAmount + ", spaceviewPlanPathPic=" + spaceviewPlanPathPic + ", spaceviewPlanSmallPathPic="
				+ spaceviewPlanSmallPathPic + "]";
	}

}
