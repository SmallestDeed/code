package com.nork.product.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: GroupProductDetails.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品组合关联表
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:37:16
 * @version V1.0   
 */
public class GroupProductDetails  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  关联主表的 组合id  **/
	private Integer groupId;
	/**  关联产品的 id  **/
	private Integer productId;
	/**  是否为主要产品（1是，0不是）  **/
	private Integer isMain;
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/
	private String att2;
	/**  整数备用1  **/
	private Integer numa1;
	/**  整数备用2  **/
	private Integer numa2;
	/**  备注  **/
	private String remark;
	/**     **/
	private String groupName;
	/**    **/
	private String groupCode;
	/**    **/
	private String brandName;
	/**    **/
	private Integer groupPicId;
	/*查询条件->组合状态*/
	private List<Integer> statusList;
	
	/**贴图产品绑定的模型id**/
	private Integer chartletProductModelId;
	/**
	 * 相机位置
	 */
	private String cameraView;
	/**
	 * 产品远景位置
	 */
	private String cameraLook;
	
	/**
	 * 挂节点
	 */
	private String posIndexPath;
	/**
	 * 产品编码
	 */
	private String productCode;
	//产品序号
	private Integer productIndex;
 
	private String  modelPath;
	private Integer minHeight;

	//组合图片地址
	private String groupPicPath;
	//组合图片地址
	private Integer collectState;


	public String getGroupPicPath() {
		return groupPicPath;
	}

	public void setGroupPicPath(String groupPicPath) {
		this.groupPicPath = groupPicPath;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}

	public Integer getProductIndex() {
		return productIndex;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public Integer getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}

 
	public void setProductIndex(Integer productIndex) {
		this.productIndex = productIndex;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPosIndexPath() {
		return posIndexPath;
	}

	public void setPosIndexPath(String posIndexPath) {
		this.posIndexPath = posIndexPath;
	}

	Map<String,String>attributeMap= new HashMap<String,String>(); 
	
	public String getCameraView() {
		return cameraView;
	}

	public void setCameraView(String cameraView) {
		this.cameraView = cameraView;
	}

	public String getCameraLook() {
		return cameraLook;
	}

	public void setCameraLook(String cameraLook) {
		this.cameraLook = cameraLook;
	}

	public Integer getChartletProductModelId() {
		return chartletProductModelId;
	}

	public void setChartletProductModelId(Integer chartletProductModelId) {
		this.chartletProductModelId = chartletProductModelId;
	}
	
	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public Map<String, String> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, String> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public Integer getGroupPicId() {
		return groupPicId;
	}

	public void setGroupPicId(Integer groupPicId) {
		this.groupPicId = groupPicId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public  Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId){
		this.groupId = groupId;
	}
	public  Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId){
		this.productId = productId;
	}
	public  Integer getIsMain() {
		return isMain;
	}
	public void setIsMain(Integer isMain){
		this.isMain = isMain;
	}
	public  String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode){
		this.sysCode = sysCode;
	}
	public  String getCreator() {
		return creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	public  Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	public  String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	public  Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}
	public  Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	public  String getAtt1() {
		return att1;
	}
	public void setAtt1(String att1){
		this.att1 = att1;
	}
	public  String getAtt2() {
		return att2;
	}
	public void setAtt2(String att2){
		this.att2 = att2;
	}
	public  Integer getNuma1() {
		return numa1;
	}
	public void setNuma1(Integer numa1){
		this.numa1 = numa1;
	}
	public  Integer getNuma2() {
		return numa2;
	}
	public void setNuma2(Integer numa2){
		this.numa2 = numa2;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
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
        GroupProductDetails other = (GroupProductDetails) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getIsMain() == null ? other.getIsMain() == null : this.getIsMain().equals(other.getIsMain()))
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
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getIsMain() == null) ? 0 : getIsMain().hashCode());
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
;
        return result;
    }
    
    /**获取对象的copy**/
    public GroupProductDetails copy(){
       GroupProductDetails obj =  new GroupProductDetails();
       obj.setGroupId(this.groupId);
       obj.setProductId(this.productId);
       obj.setIsMain(this.isMain);
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
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("groupId",this.groupId);
       map.put("productId",this.productId);
       map.put("isMain",this.isMain);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("numa1",this.numa1);
       map.put("numa2",this.numa2);
       map.put("remark",this.remark);

       return map;
    }
    
}
