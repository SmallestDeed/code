package com.sandu.api.designplan.model;

import com.sandu.api.base.common.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @version V1.0
 * @Title: ResRenderPic.java
 * @Package com.sandu.system.model
 * @Description:渲染图片资源库-渲染图片资源库
 * @createAuthor pandajun
 * @CreateDate 2017-03-22 14:39:08
 */
@Data
public class ResRenderPic extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**
	 * 推荐方案id
	 */
	private Integer recommendedPlanId;
	
	/**
	 * 设计方案id，对应的是design_plan_render_scene表id
	 */
	private Integer planId;
	
	
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 设计方案
     **/
    private Integer businessId;
    /**
     * 图片编码
     **/
    private String picCode;
    /**
     * 图片名称
     **/
    private String picName;
    /**
     * 图片文件名称
     **/
    private String picFileName;
    /**
     * 图片类型
     **/
    private String picType;
    /**
     * 图片大小
     **/
    private Integer picSize;
    /**
     * 图片长
     **/
    private Integer picWeight;
    /**
     * 图片高
     **/
    private Integer picHigh;
    /**
     * 图片后缀
     **/
    private String picSuffix;
    /**
     * 图片级别
     **/
    private Integer picLevel;
    /**
     * 图片格式
     **/
    private String picFormat;
    /**
     * 图片路径
     **/
    private String picPath;
    /**
     * 图片描述
     **/
    private String picDesc;
    /**
     * 图片排序
     **/
    private Integer picOrdering;
    /**
     * key标识
     **/
    private String fileKey;
    /**
     * key标识（多个）
     **/
    private String fileKeys;
    /**
     * 图片对应的缩略图id信息
     **/
    private String smallPicInfo;
    /**
     * 渲染图视角
     **/
    private Integer viewPoint;
    /**
     * 渲染图场景
     **/
    private Integer scene;
    /**
     * 渲染类型
     **/
    private Integer renderingType;
    /**
     * 图片文件排序序号
     **/
    private Integer sequence;
    /**
     * 全景图地址
     **/
    private String panoPath;
    /**
     * 渲染任务创建时间
     **/
    private Date taskCreateTime;
    /**
     * 原图id
     **/
    private Integer pid;
    /**
     * 系统编码
     **/
    private String sysCode;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;
    /**
     * 字符备用1
     **/
    private String att1;
    /**
     * 字符备用2
     **/
    private String att2;
    /**
     * 整数备用1
     **/
    private Integer numa1;
    /* 户型ID*/
    private Integer houseId;

	/**
     * 整数备用2
     **/
    private Integer numa2;
    /**
     * 备注
     **/
    private String remark;
    private String OriginalPicPath;
    /**
     * 原图ID。缩略图时使用
     **/
    private Integer baseRenderId;

    /**
     * 渲染任务截图
     */
    private Integer sysTaskPicId;
    /**
     * 720全景图清晰度等级
     **/
    private Integer panoLevel;
    /**
     * 漫游图片关系
     **/
    private String roam;
    /**
     * 设计方案副本id
     */
    private Integer designSceneId;

    private Integer sourcePlanId;

    private Integer templateId;

    List<String> fileKeyList = new ArrayList<String>();

    private Integer renderTaskType;

	private Integer userId;

    /**
     * 订单产品类型(具体可以见ProductType.java)
     */
    private String productType;

    /**
     * 订单总金额，单位为分
     */
    private Double totalFee;

    private List<Integer> noIdList;

    /**
     * 支付类型
     */
    private String payType;

    private Integer platformId;
    
//    /**
//     * 单品替换集合
//     * add by xiaozunp
//     */
//    private List<ProductReplaceTaskDetail> productReplaceList;
//    /**
//     * 产品移除集合
//     * add by xiaozunp
//     */
//    private List<ProductReplaceTaskDetail> productDeleteList;
//    /**
//     * 组合产品替换集合
//     * add by xiaozunp
//     */
//    private List<ProductGroupReplaceTaskDetail> productGroupReplaceList;
//    /**
//     * 材质替换集合
//     * add by xiaozunp
//     */
//    private List<ProductReplaceTaskDetail> textureReplaceList;
    

    /**
     * 渲染时的实际方案名称，后面设计方案发生修改，名称不在同步
     */
    private String designPlanName;
    /**
     * 设计方案对应的房屋空间类型
     */
    private String spaceType;
    /**
     * 面积范围
     */
    private String area;
    /**
     * 创建者的userid
     */

    private Integer planRecommendedId;

    private Integer operationSource;

    private Integer designPlanSceneId;

    private long createUserId;

    private String cameraLocation;

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
        ResRenderPic other = (ResRenderPic) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getBusinessId() == null ? other.getBusinessId() == null : this.getBusinessId().equals(other.getBusinessId()))
                && (this.getPicCode() == null ? other.getPicCode() == null : this.getPicCode().equals(other.getPicCode()))
                && (this.getPicName() == null ? other.getPicName() == null : this.getPicName().equals(other.getPicName()))
                && (this.getPicFileName() == null ? other.getPicFileName() == null : this.getPicFileName().equals(other.getPicFileName()))
                && (this.getPicType() == null ? other.getPicType() == null : this.getPicType().equals(other.getPicType()))
                && (this.getPicSize() == null ? other.getPicSize() == null : this.getPicSize().equals(other.getPicSize()))
                && (this.getPicWeight() == null ? other.getPicWeight() == null : this.getPicWeight().equals(other.getPicWeight()))
                && (this.getPicHigh() == null ? other.getPicHigh() == null : this.getPicHigh().equals(other.getPicHigh()))
                && (this.getPicSuffix() == null ? other.getPicSuffix() == null : this.getPicSuffix().equals(other.getPicSuffix()))
                && (this.getPicLevel() == null ? other.getPicLevel() == null : this.getPicLevel().equals(other.getPicLevel()))
                && (this.getPicFormat() == null ? other.getPicFormat() == null : this.getPicFormat().equals(other.getPicFormat()))
                && (this.getPicPath() == null ? other.getPicPath() == null : this.getPicPath().equals(other.getPicPath()))
                && (this.getPicDesc() == null ? other.getPicDesc() == null : this.getPicDesc().equals(other.getPicDesc()))
                && (this.getPicOrdering() == null ? other.getPicOrdering() == null : this.getPicOrdering().equals(other.getPicOrdering()))
                && (this.getFileKey() == null ? other.getFileKey() == null : this.getFileKey().equals(other.getFileKey()))
                && (this.getFileKeys() == null ? other.getFileKeys() == null : this.getFileKeys().equals(other.getFileKeys()))
                && (this.getSmallPicInfo() == null ? other.getSmallPicInfo() == null : this.getSmallPicInfo().equals(other.getSmallPicInfo()))
                && (this.getViewPoint() == null ? other.getViewPoint() == null : this.getViewPoint().equals(other.getViewPoint()))
                && (this.getScene() == null ? other.getScene() == null : this.getScene().equals(other.getScene()))
                && (this.getRenderingType() == null ? other.getRenderingType() == null : this.getRenderingType().equals(other.getRenderingType()))
                && (this.getSequence() == null ? other.getSequence() == null : this.getSequence().equals(other.getSequence()))
                && (this.getPanoPath() == null ? other.getPanoPath() == null : this.getPanoPath().equals(other.getPanoPath()))
                && (this.getTaskCreateTime() == null ? other.getTaskCreateTime() == null : this.getTaskCreateTime().equals(other.getTaskCreateTime()))
                && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
                && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
                && (this.getNuma1() == null ? other.getNuma1() == null : this.getNuma1().equals(other.getNuma1()))
                && (this.getNuma2() == null ? other.getNuma2() == null : this.getNuma2().equals(other.getNuma2()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBusinessId() == null) ? 0 : getBusinessId().hashCode());
        result = prime * result + ((getPicCode() == null) ? 0 : getPicCode().hashCode());
        result = prime * result + ((getPicName() == null) ? 0 : getPicName().hashCode());
        result = prime * result + ((getPicFileName() == null) ? 0 : getPicFileName().hashCode());
        result = prime * result + ((getPicType() == null) ? 0 : getPicType().hashCode());
        result = prime * result + ((getPicSize() == null) ? 0 : getPicSize().hashCode());
        result = prime * result + ((getPicWeight() == null) ? 0 : getPicWeight().hashCode());
        result = prime * result + ((getPicHigh() == null) ? 0 : getPicHigh().hashCode());
        result = prime * result + ((getPicSuffix() == null) ? 0 : getPicSuffix().hashCode());
        result = prime * result + ((getPicLevel() == null) ? 0 : getPicLevel().hashCode());
        result = prime * result + ((getPicFormat() == null) ? 0 : getPicFormat().hashCode());
        result = prime * result + ((getPicPath() == null) ? 0 : getPicPath().hashCode());
        result = prime * result + ((getPicDesc() == null) ? 0 : getPicDesc().hashCode());
        result = prime * result + ((getPicOrdering() == null) ? 0 : getPicOrdering().hashCode());
        result = prime * result + ((getFileKey() == null) ? 0 : getFileKey().hashCode());
        result = prime * result + ((getFileKeys() == null) ? 0 : getFileKeys().hashCode());
        result = prime * result + ((getSmallPicInfo() == null) ? 0 : getSmallPicInfo().hashCode());
        result = prime * result + ((getViewPoint() == null) ? 0 : getViewPoint().hashCode());
        result = prime * result + ((getScene() == null) ? 0 : getScene().hashCode());
        result = prime * result + ((getRenderingType() == null) ? 0 : getRenderingType().hashCode());
        result = prime * result + ((getSequence() == null) ? 0 : getSequence().hashCode());
        result = prime * result + ((getPanoPath() == null) ? 0 : getPanoPath().hashCode());
        result = prime * result + ((getTaskCreateTime() == null) ? 0 : getTaskCreateTime().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getNuma1() == null) ? 0 : getNuma1().hashCode());
        result = prime * result + ((getNuma2() == null) ? 0 : getNuma2().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * 获取对象的copy
     **/
    public ResRenderPic copy() {
        ResRenderPic obj = new ResRenderPic();
        obj.setBusinessId(this.businessId);
        obj.setPicCode(this.picCode);
        obj.setPicName(this.picName);
        obj.setPicFileName(this.picFileName);
        obj.setPicType(this.picType);
        obj.setPicSize(this.picSize);
        obj.setPicWeight(this.picWeight);
        obj.setPicHigh(this.picHigh);
        obj.setPicSuffix(this.picSuffix);
        obj.setPicLevel(this.picLevel);
        obj.setPicFormat(this.picFormat);
        obj.setPicPath(this.picPath);
        obj.setPicDesc(this.picDesc);
        obj.setPicOrdering(this.picOrdering);
        obj.setFileKey(this.fileKey);
        obj.setFileKeys(this.fileKeys);
        obj.setSmallPicInfo(this.smallPicInfo);
        obj.setViewPoint(this.viewPoint);
        obj.setScene(this.scene);
        obj.setRenderingType(this.renderingType);
        obj.setSequence(this.sequence);
        obj.setPanoPath(this.panoPath);
        obj.setTaskCreateTime(this.taskCreateTime);
        obj.setPid(this.pid);
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setAtt1(this.att1);
        obj.setAtt2(this.att2);
        obj.setNuma1(this.numa1);
        obj.setNuma2(this.numa2);
        obj.setRemark(this.remark);

        return obj;
    }

    /**
     * 获取对象的map
     **/
    public Map toMap() {
        Map map = new HashMap();
        map.put("businessId", this.businessId);
        map.put("picCode", this.picCode);
        map.put("picName", this.picName);
        map.put("picFileName", this.picFileName);
        map.put("picType", this.picType);
        map.put("picSize", this.picSize);
        map.put("picWeight", this.picWeight);
        map.put("picHigh", this.picHigh);
        map.put("picSuffix", this.picSuffix);
        map.put("picLevel", this.picLevel);
        map.put("picFormat", this.picFormat);
        map.put("picPath", this.picPath);
        map.put("picDesc", this.picDesc);
        map.put("picOrdering", this.picOrdering);
        map.put("fileKey", this.fileKey);
        map.put("fileKeys", this.fileKeys);
        map.put("smallPicInfo", this.smallPicInfo);
        map.put("viewPoint", this.viewPoint);
        map.put("scene", this.scene);
        map.put("renderingType", this.renderingType);
        map.put("sequence", this.sequence);
        map.put("panoPath", this.panoPath);
        map.put("taskCreateTime", this.taskCreateTime);
        map.put("pid", this.pid);
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("att1", this.att1);
        map.put("att2", this.att2);
        map.put("numa1", this.numa1);
        map.put("numa2", this.numa2);
        map.put("remark", this.remark);

        return map;
    }


    @Override
    public String toString() {
        return "ResRenderPic{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", picCode='" + picCode + '\'' +
                ", picName='" + picName + '\'' +
                ", picFileName='" + picFileName + '\'' +
                ", picType='" + picType + '\'' +
                ", picSize=" + picSize +
                ", picWeight=" + picWeight +
                ", picHigh=" + picHigh +
                ", picSuffix='" + picSuffix + '\'' +
                ", picLevel=" + picLevel +
                ", picFormat='" + picFormat + '\'' +
                ", picPath='" + picPath + '\'' +
                ", picDesc='" + picDesc + '\'' +
                ", picOrdering=" + picOrdering +
                ", fileKey='" + fileKey + '\'' +
                ", fileKeys='" + fileKeys + '\'' +
                ", smallPicInfo='" + smallPicInfo + '\'' +
                ", viewPoint=" + viewPoint +
                ", scene=" + scene +
                ", renderingType=" + renderingType +
                ", sequence=" + sequence +
                ", panoPath='" + panoPath + '\'' +
                ", taskCreateTime=" + taskCreateTime +
                ", pid=" + pid +
                ", sysCode='" + sysCode + '\'' +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                ", att1='" + att1 + '\'' +
                ", att2='" + att2 + '\'' +
                ", numa1=" + numa1 +
                ", numa2=" + numa2 +
                ", remark='" + remark + '\'' +
                ", OriginalPicPath='" + OriginalPicPath + '\'' +
                ", baseRenderId=" + baseRenderId +
                ", sysTaskPicId=" + sysTaskPicId +
                ", panoLevel=" + panoLevel +
                ", roam='" + roam + '\'' +
                ", designSceneId=" + designSceneId +
                ", sourcePlanId=" + sourcePlanId +
                ", templateId=" + templateId +
                ", fileKeyList=" + fileKeyList +
                ", designPlanName='" + designPlanName + '\'' +
                ", spaceType='" + spaceType + '\'' +
                ", area='" + area + '\'' +
                ", planRecommendedId=" + planRecommendedId +
                ", createUserId=" + createUserId +
                '}';
    }
}
