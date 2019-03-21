package com.sandu.im.model;


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
public class ResRenderPic implements Serializable {
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
    
    
    public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

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

    /**
     * 支付类型
     */
    private String payType;

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

    private Integer planRecommendedId;

    private Integer operationSource;

    private Integer designPlanSceneId;

    private long createUserId;

}
