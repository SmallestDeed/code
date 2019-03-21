package com.nork.home.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: DesignRecommendation.java 
 * @Package com.nork.home.model
 * @Description:户型房型-效果图推荐
 * @createAuthor pandajun 
 * @CreateDate 2015-07-06 20:00:19
 * @version V1.0   
 */
public class DesignRecommendation  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    private Integer designId;
    
	public Integer getDesignId() {
		return designId;
	}

	public void setDesignId(Integer designId) {
		this.designId = designId;
	}
	/**  来源code  **/
	private String srcCode;
	/**  来源类型  **/
	private String srcType;
	/**  图片ID  **/
	private String picId;
	/**  首页是否显示  **/
	private Integer homeIsDisplayed;
	/**  设计风格  **/
	private Integer designStyleId;
	private Integer houseTypeId;
	/**  用户ID  **/
	private Integer userId;
	public Integer getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(Integer houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	/**  面积  **/
	private String spaceArea;
	/**  名称  **/
	private String designName;
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
	/**  描述  **/
	private String description;
	/**  字符备用2  **/
	private String att2;
	/**  整数备用1  **/
	private Integer numa1;
	/**  整数备用2  **/
	private Integer numa2;
	/**  备注  **/
	private String remark;
	private String renderPicIds;

	public String getRenderPicIds() {
		return renderPicIds;
	}

	public void setRenderPicIds(String renderPicIds) {
		this.renderPicIds = renderPicIds;
	}

	public  String getSrcCode() {
		return srcCode;
	}
	public void setSrcCode(String srcCode){
		this.srcCode = srcCode;
	}
	public  String getSrcType() {
		return srcType;
	}
	public void setSrcType(String srcType){
		this.srcType = srcType;
	}
	public  String getPicId() {
		return picId;
	}
	public void setPicId(String picId){
		this.picId = picId;
	}
	public  Integer getHomeIsDisplayed() {
		return homeIsDisplayed;
	}
	public void setHomeIsDisplayed(Integer homeIsDisplayed){
		this.homeIsDisplayed = homeIsDisplayed;
	}
	public  Integer getDesignStyleId() {
		return designStyleId;
	}
	public void setDesignStyleId(Integer designStyleId){
		this.designStyleId = designStyleId;
	}
	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getSpaceArea() {
		return spaceArea;
	}
	public void setSpaceArea(String spaceArea){
		this.spaceArea = spaceArea;
	}
	public  String getDesignName() {
		return designName;
	}
	public void setDesignName(String designName){
		this.designName = designName;
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
    public String getDescription() {
	return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
        DesignRecommendation other = (DesignRecommendation) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSrcCode() == null ? other.getSrcCode() == null : this.getSrcCode().equals(other.getSrcCode()))
            && (this.getSrcType() == null ? other.getSrcType() == null : this.getSrcType().equals(other.getSrcType()))
            && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
            && (this.getHomeIsDisplayed() == null ? other.getHomeIsDisplayed() == null : this.getHomeIsDisplayed().equals(other.getHomeIsDisplayed()))
            && (this.getDesignStyleId() == null ? other.getDesignStyleId() == null : this.getDesignStyleId().equals(other.getDesignStyleId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getSpaceArea() == null ? other.getSpaceArea() == null : this.getSpaceArea().equals(other.getSpaceArea()))
            && (this.getDesignName() == null ? other.getDesignName() == null : this.getDesignName().equals(other.getDesignName()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
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
        result = prime * result + ((getSrcCode() == null) ? 0 : getSrcCode().hashCode());
        result = prime * result + ((getSrcType() == null) ? 0 : getSrcType().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getHomeIsDisplayed() == null) ? 0 : getHomeIsDisplayed().hashCode());
        result = prime * result + ((getDesignStyleId() == null) ? 0 : getDesignStyleId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getSpaceArea() == null) ? 0 : getSpaceArea().hashCode());
        result = prime * result + ((getDesignName() == null) ? 0 : getDesignName().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getNuma1() == null) ? 0 : getNuma1().hashCode());
        result = prime * result + ((getNuma2() == null) ? 0 : getNuma2().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public DesignRecommendation copy(){
       DesignRecommendation obj =  new DesignRecommendation();
       obj.setSrcCode(this.srcCode);
       obj.setSrcType(this.srcType);
       obj.setPicId(this.picId);
       obj.setHomeIsDisplayed(this.homeIsDisplayed);
       obj.setDesignStyleId(this.designStyleId);
       obj.setUserId(this.userId);
       obj.setSpaceArea(this.spaceArea);
       obj.setDesignName(this.designName);
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setDescription(this.description);
       obj.setAtt2(this.att2);
       obj.setNuma1(this.numa1);
       obj.setNuma2(this.numa2);
       obj.setRemark(this.remark);

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("srcCode",this.srcCode);
       map.put("srcType",this.srcType);
       map.put("picId",this.picId);
       map.put("homeIsDisplayed",this.homeIsDisplayed);
       map.put("designStyleId",this.designStyleId);
       map.put("userId",this.userId);
       map.put("spaceArea",this.spaceArea);
       map.put("designName",this.designName);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("att1",this.description);
       map.put("att2",this.att2);
       map.put("numa1",this.numa1);
       map.put("numa2",this.numa2);
       map.put("remark",this.remark);

       return map;
    }
    
    private String spaceName;
    private String picPath;
    
	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
    
    
}
