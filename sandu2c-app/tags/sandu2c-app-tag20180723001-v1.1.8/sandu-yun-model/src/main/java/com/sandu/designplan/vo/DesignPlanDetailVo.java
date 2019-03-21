package com.sandu.designplan.vo;

import com.sandu.designplan.model.RenderPicInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设计方案详情VO
 */
public class DesignPlanDetailVo {


    //方案编码
    private String planCode;
    //方案名称
    private String planName;
    //设计者id
    private Integer userId;
    //方案来源类型
    private Integer designSourceType;
    //来源id
    private Integer designId;
    //设计风格
    private Integer designStyleId;
    //方案图片id
    private Integer picId;
    //空间id
    private Integer spaceCommonId;
    //方案描述
    private String planDesc;
    //创建者
    private String creator;
    //创建时间
    private Date gmtCreate;
    //备注
    private String remark;
    //分享数量
    private Integer shareTotal;
    //设计方案封面
    private Integer coverPicId;
    //方案推荐风格ID
    private Integer designRecommendedStyleId;
    //方案推荐风格名称
    private String designRecommendedStyleName;
    
    public String getDesignRecommendedStyleName() {
		return designRecommendedStyleName;
	}

	public void setDesignRecommendedStyleName(String designRecommendedStyleName) {
		this.designRecommendedStyleName = designRecommendedStyleName;
	}

	//空间码
    private String spaceCode;
    //空间名
    private String spaceName;
    //空间面积
    private String spaceAreas;
    //是否收藏(0:未收藏，1:已收藏)
    private int isFavorite;
    //渲染图片List
    private List<RenderPicInfo> renderPicList;
    //渲染图片MAp
    private Map<Integer, List<String>> renderMap;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDesignSourceType() {
        return designSourceType;
    }

    public void setDesignSourceType(Integer designSourceType) {
        this.designSourceType = designSourceType;
    }

    public Integer getDesignId() {
        return designId;
    }

    public void setDesignId(Integer designId) {
        this.designId = designId;
    }

    public Integer getDesignStyleId() {
        return designStyleId;
    }

    public void setDesignStyleId(Integer designStyleId) {
        this.designStyleId = designStyleId;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getSpaceCommonId() {
        return spaceCommonId;
    }

    public void setSpaceCommonId(Integer spaceCommonId) {
        this.spaceCommonId = spaceCommonId;
    }

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getShareTotal() {
        return shareTotal;
    }

    public void setShareTotal(Integer shareTotal) {
        this.shareTotal = shareTotal;
    }

    public Integer getCoverPicId() {
        return coverPicId;
    }

    public void setCoverPicId(Integer coverPicId) {
        this.coverPicId = coverPicId;
    }

    public Integer getDesignRecommendedStyleId() {
        return designRecommendedStyleId;
    }

    public void setDesignRecommendedStyleId(Integer designRecommendedStyleId) {
        this.designRecommendedStyleId = designRecommendedStyleId;
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

    public String getSpaceAreas() {
        return spaceAreas;
    }

    public void setSpaceAreas(String spaceAreas) {
        this.spaceAreas = spaceAreas;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public List<RenderPicInfo> getRenderPicList() {
        return renderPicList;
    }

    public void setRenderPicList(List<RenderPicInfo> renderPicList) {
        this.renderPicList = renderPicList;
    }

    public Map<Integer, List<String>> getRenderMap() {
        return renderMap;
    }

    public void setRenderMap(Map<Integer, List<String>> renderMap) {
        this.renderMap = renderMap;
    }

    @Override
    public String toString() {
        return "DesignPlanDetailVo{" +
                "planCode='" + planCode + '\'' +
                ", planName='" + planName + '\'' +
                ", userId=" + userId +
                ", designSourceType=" + designSourceType +
                ", designId=" + designId +
                ", designStyleId=" + designStyleId +
                ", picId=" + picId +
                ", spaceCommonId=" + spaceCommonId +
                ", planDesc='" + planDesc + '\'' +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", remark='" + remark + '\'' +
                ", shareTotal=" + shareTotal +
                ", coverPicId=" + coverPicId +
                ", designRecommendedStyleId=" + designRecommendedStyleId +
                ", spaceCode='" + spaceCode + '\'' +
                ", spaceName='" + spaceName + '\'' +
                ", spaceAreas='" + spaceAreas + '\'' +
                ", isFavorite=" + isFavorite +
                ", renderPicList=" + renderPicList +
                ", renderMap=" + renderMap +
                '}';
    }
}
