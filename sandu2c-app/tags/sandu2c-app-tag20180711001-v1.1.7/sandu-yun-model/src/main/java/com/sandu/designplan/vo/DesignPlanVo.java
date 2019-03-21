package com.sandu.designplan.vo;

import com.sandu.common.model.PageModel;

/**
 * 设计方案VO
 */
public class DesignPlanVo {

    private Integer id;
    //设计方案ID
    private Integer designPlanId;
    //方案编码
    private String designPlanCode;
    //方案名称
    private String designPlanName;
    //设计风格ID
    private Integer designPlanStyleId;
    //方案图片ID
    private Integer designPlanPicId;
    //方案图片路径
    private String designPlanPicPath;
    //方案描述
    private String designPlanDesc;
    //方案面积
    private String designPlanAreas;
    //方案空间类型
    private Integer designPlanSpaceType;
    //空间功能ID
    private Integer spaceFunctionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static int getDesignplanPagePagesize() {
        return PageModel.DEFAULT_PAGE_PAGESIZE;
    }

    public static int getDesignplanPageCurrentpage() {
        return PageModel.DEFAULT_PAGE_CURRENTPAGE;
    }

    public Integer getDesignPlanId() {
        return designPlanId;
    }

    public void setDesignPlanId(Integer designPlanId) {
        this.designPlanId = designPlanId;
    }

    public String getDesignPlanCode() {
        return designPlanCode;
    }

    public void setDesignPlanCode(String designPlanCode) {
        this.designPlanCode = designPlanCode;
    }

    public String getDesignPlanName() {
        return designPlanName;
    }

    public void setDesignPlanName(String designPlanName) {
        this.designPlanName = designPlanName;
    }

    public Integer getDesignPlanStyleId() {
        return designPlanStyleId;
    }

    public void setDesignPlanStyleId(Integer designPlanStyleId) {
        this.designPlanStyleId = designPlanStyleId;
    }

    public Integer getDesignPlanPicId() {
        return designPlanPicId;
    }

    public void setDesignPlanPicId(Integer designPlanPicId) {
        this.designPlanPicId = designPlanPicId;
    }

    public String getDesignPlanDesc() {
        return designPlanDesc;
    }

    public void setDesignPlanDesc(String designPlanDesc) {
        this.designPlanDesc = designPlanDesc;
    }

    public String getDesignPlanAreas() {
        return designPlanAreas;
    }

    public void setDesignPlanAreas(String designPlanAreas) {
        this.designPlanAreas = designPlanAreas;
    }

    public Integer getDesignPlanSpaceType() {
        return designPlanSpaceType;
    }

    public void setDesignPlanSpaceType(Integer designPlanSpaceType) {
        this.designPlanSpaceType = designPlanSpaceType;
    }

    public String getDesignPlanPicPath() {
        return designPlanPicPath;
    }

    public void setDesignPlanPicPath(String designPlanPicPath) {
        this.designPlanPicPath = designPlanPicPath;
    }

    public Integer getSpaceFunctionId() {
        return spaceFunctionId;
    }

    public void setSpaceFunctionId(Integer spaceFunctionId) {
        this.spaceFunctionId = spaceFunctionId;
    }

    @Override
    public String toString() {
        return "DesignPlanVo{" +
                "designPlanId=" + designPlanId +
                ", designPlanCode='" + designPlanCode + '\'' +
                ", designPlanName='" + designPlanName + '\'' +
                ", designPlanStyleId=" + designPlanStyleId +
                ", designPlanPicId=" + designPlanPicId +
                ", designPlanPicPath='" + designPlanPicPath + '\'' +
                ", designPlanDesc='" + designPlanDesc + '\'' +
                ", designPlanAreas='" + designPlanAreas + '\'' +
                ", designPlanSpaceType=" + designPlanSpaceType +
                ", spaceFunctionId=" + spaceFunctionId +
                '}';
    }
}
