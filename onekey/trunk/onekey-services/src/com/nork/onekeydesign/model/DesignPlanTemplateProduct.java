package com.nork.onekeydesign.model;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 模板方案产品表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-07-22 14:21:29.686
 */

public class DesignPlanTemplateProduct implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    /**
     * design_plan_template_product.id
     */
    private Long id;

    /**
     * 系统编码<p>
     * design_plan_template_product.sys_code
     */
    private String sysCode;

    /**
     * 模板方案id(design_plan_template)<p>
     * design_plan_template_product.plan_id
     */
    private Integer planId;

    /**
     * 产品id<p>
     * design_plan_template_product.product_id
     */
    private Integer productId;

    /**
     * design_plan_template_product.material_pic_id
     */
    private Integer materialPicId;

    /**
     * 相机配置<p>
     * design_plan_template_product.location_file_id
     */
    private Integer locationFileId;

    /**
     * 顺序<p>
     * design_plan_template_product.product_sequence
     */
    private String productSequence;

    /**
     * 创建者<p>
     * design_plan_template_product.creator
     */
    private String creator;

    /**
     * design_plan_template_product.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * design_plan_template_product.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * design_plan_template_product.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * design_plan_template_product.is_deleted
     */
    private Integer isDeleted;

    /**
     * design_plan_template_product.planProduct_id
     */
    private Integer planproductId;

    /**
     * design_plan_template_product.display_status
     */
    private Integer displayStatus;

    /**
     * 备注<p>
     * design_plan_template_product.remark
     */
    private String remark;

    /**
     * design_plan_template_product.is_hide
     */
    private Integer isHide;

    /**
     * design_plan_template_product.pos_index_path
     */
    private String posIndexPath;

    /**
     * design_plan_template_product.init_product_id
     */
    private Integer initProductId;

    /**
     * design_plan_template_product.is_dirty
     */
    private Integer isDirty;

    /**
     * 产品组合ID<p>
     * design_plan_template_product.product_group_id
     */
    private Integer productGroupId;

    /**
     * 是否是主产品<p>
     * design_plan_template_product.is_main_product
     */
    private Integer isMainProduct;

    /**
     * 挂节点名称<p>
     * design_plan_template_product.pos_name
     */
    private String posName;

    /**
     * 设计方案组合ID<p>
     * design_plan_template_product.plan_group_id
     */
    private String planGroupId;

    /**
     * 有模型的产品<p>
     * design_plan_template_product.model_product_id
     */
    private Integer modelProductId;

    /**
     * 绑定父产品Id<p>
     * design_plan_template_product.bind_parent_productId
     */
    private String bindParentProductid;

    /**
     * design_plan_template_product.split_textures_choose_info
     */
    private String splitTexturesChooseInfo;

    /**
     * 是组合还是结构,0:组合,1:结构<p>
     * design_plan_template_product.group_type
     */
    private Integer groupType;

    /**
     * 产品同小分类序号<p>
     * design_plan_template_product.same_product_type_index
     */
    private Integer sameProductTypeIndex;

    /**
     * 标准(1标准 0 非标准)<p>
     * design_plan_template_product.is_standard
     */
    private Integer isStandard;

    /**
     * 中心点<p>
     * design_plan_template_product.center
     */
    private String center;

    /**
     * 区域标识<p>
     * design_plan_template_product.region_mark
     */
    private String regionMark;

    /**
     * 款式id<p>
     * design_plan_template_product.style_id
     */
    private Integer styleId;

    /**
     * 尺寸代码<p>
     * design_plan_template_product.measure_code
     */
    private String measureCode;

    /**
     * 描述(区域、尺寸代码)<p>
     * design_plan_template_product.describe_info
     */
    private String describeInfo;

    /**
     * 序号<p>
     * design_plan_template_product.product_index
     */
    private Integer productIndex;

    /**
     * 是否为结构中的主产品标识（1是，0否）<p>
     * design_plan_template_product.is_main_structure_product
     */
    private Integer isMainStructureProduct;

    /**
     * 主产品单品是否可以作为组合方式替换（1是，0否）<p>
     * design_plan_template_product.is_group_replace_way
     */
    private Integer isGroupReplaceWay;

    /**
     * 墙体方位<p>
     * design_plan_template_product.wall_orientation
     */
    private String wallOrientation;

    /**
     * 墙体类型<p>
     * design_plan_template_product.wall_type
     */
    private String wallType;

    /**
     * 组合产品唯一标识<p>
     * design_plan_template_product.group_product_uniqueId
     */
    private String groupProductUniqueid;

    /**
     * 是否做了材质替换(0:否;>0:是taskId)<p>
     * design_plan_template_product.is_replace_texture
     */
    private Integer isReplaceTexture;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getMaterialPicId() {
		return materialPicId;
	}

	public void setMaterialPicId(Integer materialPicId) {
		this.materialPicId = materialPicId;
	}

	public Integer getLocationFileId() {
		return locationFileId;
	}

	public void setLocationFileId(Integer locationFileId) {
		this.locationFileId = locationFileId;
	}

	public String getProductSequence() {
		return productSequence;
	}

	public void setProductSequence(String productSequence) {
		this.productSequence = productSequence;
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

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getPlanproductId() {
		return planproductId;
	}

	public void setPlanproductId(Integer planproductId) {
		this.planproductId = planproductId;
	}

	public Integer getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(Integer displayStatus) {
		this.displayStatus = displayStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public String getPosIndexPath() {
		return posIndexPath;
	}

	public void setPosIndexPath(String posIndexPath) {
		this.posIndexPath = posIndexPath;
	}

	public Integer getInitProductId() {
		return initProductId;
	}

	public void setInitProductId(Integer initProductId) {
		this.initProductId = initProductId;
	}

	public Integer getIsDirty() {
		return isDirty;
	}

	public void setIsDirty(Integer isDirty) {
		this.isDirty = isDirty;
	}

	public Integer getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(Integer productGroupId) {
		this.productGroupId = productGroupId;
	}

	public Integer getIsMainProduct() {
		return isMainProduct;
	}

	public void setIsMainProduct(Integer isMainProduct) {
		this.isMainProduct = isMainProduct;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getPlanGroupId() {
		return planGroupId;
	}

	public void setPlanGroupId(String planGroupId) {
		this.planGroupId = planGroupId;
	}

	public Integer getModelProductId() {
		return modelProductId;
	}

	public void setModelProductId(Integer modelProductId) {
		this.modelProductId = modelProductId;
	}

	public String getBindParentProductid() {
		return bindParentProductid;
	}

	public void setBindParentProductid(String bindParentProductid) {
		this.bindParentProductid = bindParentProductid;
	}

	public String getSplitTexturesChooseInfo() {
		return splitTexturesChooseInfo;
	}

	public void setSplitTexturesChooseInfo(String splitTexturesChooseInfo) {
		this.splitTexturesChooseInfo = splitTexturesChooseInfo;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public Integer getSameProductTypeIndex() {
		return sameProductTypeIndex;
	}

	public void setSameProductTypeIndex(Integer sameProductTypeIndex) {
		this.sameProductTypeIndex = sameProductTypeIndex;
	}

	public Integer getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(Integer isStandard) {
		this.isStandard = isStandard;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getRegionMark() {
		return regionMark;
	}

	public void setRegionMark(String regionMark) {
		this.regionMark = regionMark;
	}

	public Integer getStyleId() {
		return styleId;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}

	public String getMeasureCode() {
		return measureCode;
	}

	public void setMeasureCode(String measureCode) {
		this.measureCode = measureCode;
	}

	public String getDescribeInfo() {
		return describeInfo;
	}

	public void setDescribeInfo(String describeInfo) {
		this.describeInfo = describeInfo;
	}

	public Integer getProductIndex() {
		return productIndex;
	}

	public void setProductIndex(Integer productIndex) {
		this.productIndex = productIndex;
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

	public String getWallOrientation() {
		return wallOrientation;
	}

	public void setWallOrientation(String wallOrientation) {
		this.wallOrientation = wallOrientation;
	}

	public String getWallType() {
		return wallType;
	}

	public void setWallType(String wallType) {
		this.wallType = wallType;
	}

	public String getGroupProductUniqueid() {
		return groupProductUniqueid;
	}

	public void setGroupProductUniqueid(String groupProductUniqueid) {
		this.groupProductUniqueid = groupProductUniqueid;
	}

	public Integer getIsReplaceTexture() {
		return isReplaceTexture;
	}

	public void setIsReplaceTexture(Integer isReplaceTexture) {
		this.isReplaceTexture = isReplaceTexture;
	}
    
}