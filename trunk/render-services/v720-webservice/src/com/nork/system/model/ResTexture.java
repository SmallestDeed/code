package com.nork.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: ResTexture.java 
 * @Package com.nork.system.model
 * @Description:系统模块-材质库
 * @createAuthor pandajun 
 * @CreateDate 2016-06-30 14:10:42
 * @version V1.0   
 */
public class ResTexture  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    /*id集合*/
    List<String> resTextureIds= new ArrayList<String>();
    
    
    
    
 
	public List<String> getResTextureIds() {
		return resTextureIds;
	}

	public void setResTextureIds(List<String> resTextureIds) {
		this.resTextureIds = resTextureIds;
	}
	/**  文件手动命名  **/
	private String name;
	/**  文件名  **/
	private String fileName;
	/**  类型  **/
	private String type;
	/**  编码  **/
	private String fileCode;
	/**  文件大小  **/
	private Integer fileSize;
	/**  图片宽  **/
	private Integer fileWidth;
	/**  图片高  **/
	private Integer fileHeight;
	/**  图片格式  **/
	private String fileSuffix;
	/**  材质图片路径  **/
	private String filePath;
	/**  材质描述  **/
	private String fileDesc;
	/**  风格  **/
	private String style;
	/**  材质(材料)  **/
	private String texture;
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
	/*关联图片id*/
	private Integer picId;
	/*材质属性*/
	private Integer textureAttrValue; 
	/*铺设方式*/
	private String laymodes;
	/** 缩略图 **/
	private Integer thumbnailId;
	
	//法线贴图
	private Integer normalPicId;
	//法线参数
	private String normalParam;

	private String normalPath;
	
	//材质球文件Id
	private Integer textureBallFileId;
	//材质球文件名称
	private String textureBallFileName;
	//材质球文件路径
	private String materialPath;

	//品牌id
	private Integer brandId;
	//材质编号
	private String textureCode;

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getTextureCode() {
		return textureCode;
	}

	public void setTextureCode(String textureCode) {
		this.textureCode = textureCode;
	}

	public Integer getTextureBallFileId() {
		return textureBallFileId;
	}

	public void setTextureBallFileId(Integer textureBallFileId) {
		this.textureBallFileId = textureBallFileId;
	}

	public String getTextureBallFileName() {
		return textureBallFileName;
	}

	public void setTextureBallFileName(String textureBallFileName) {
		this.textureBallFileName = textureBallFileName;
	}

	public String getMaterialPath() {
		return materialPath;
	}

	public void setMaterialPath(String materialPath) {
		this.materialPath = materialPath;
	}
	
	public Integer getNormalPicId() {
		return normalPicId;
	}

	public void setNormalPicId(Integer normalPicId) {
		this.normalPicId = normalPicId;
	}

	public String getNormalParam() {
		return normalParam;
	}

	public void setNormalParam(String normalParam) {
		this.normalParam = normalParam;
	}

	public String getNormalPath() {
		return normalPath;
	}

	public void setNormalPath(String normalPath) {
		this.normalPath = normalPath;
	}

	public Integer getTextureAttrValue() {
		return textureAttrValue;
	}
 
 

	public void setTextureAttrValue(Integer textureAttrValue) {
		this.textureAttrValue = textureAttrValue;
	}

	public String getLaymodes() {
		return laymodes;
	}

	public void setLaymodes(String laymodes) {
		this.laymodes = laymodes;
	}

	public Integer getPicId() {
		return picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	public  String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public  String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	public  String getType() {
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public  String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode){
		this.fileCode = fileCode;
	}
	public  Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize){
		this.fileSize = fileSize;
	}
	public  Integer getFileWidth() {
		return fileWidth;
	}
	public void setFileWidth(Integer fileWidth){
		this.fileWidth = fileWidth;
	}
	public  Integer getFileHeight() {
		return fileHeight;
	}
	public void setFileHeight(Integer fileHeight){
		this.fileHeight = fileHeight;
	}
	public  String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix){
		this.fileSuffix = fileSuffix;
	}
	public  String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	public  String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc){
		this.fileDesc = fileDesc;
	}
	public  String getStyle() {
		return style;
	}
	public void setStyle(String style){
		this.style = style;
	}
	public  String getTexture() {
		return texture;
	}
	public void setTexture(String texture){
		this.texture = texture;
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

	public Integer getThumbnailId() {
		return thumbnailId;
	}

	public void setThumbnailId(Integer thumbnailId) {
		this.thumbnailId = thumbnailId;
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
        ResTexture other = (ResTexture) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getFileCode() == null ? other.getFileCode() == null : this.getFileCode().equals(other.getFileCode()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getFileWidth() == null ? other.getFileWidth() == null : this.getFileWidth().equals(other.getFileWidth()))
            && (this.getFileHeight() == null ? other.getFileHeight() == null : this.getFileHeight().equals(other.getFileHeight()))
            && (this.getFileSuffix() == null ? other.getFileSuffix() == null : this.getFileSuffix().equals(other.getFileSuffix()))
            && (this.getFilePath() == null ? other.getFilePath() == null : this.getFilePath().equals(other.getFilePath()))
            && (this.getFileDesc() == null ? other.getFileDesc() == null : this.getFileDesc().equals(other.getFileDesc()))
            && (this.getStyle() == null ? other.getStyle() == null : this.getStyle().equals(other.getStyle()))
            && (this.getTexture() == null ? other.getTexture() == null : this.getTexture().equals(other.getTexture()))
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
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getFileCode() == null) ? 0 : getFileCode().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getFileWidth() == null) ? 0 : getFileWidth().hashCode());
        result = prime * result + ((getFileHeight() == null) ? 0 : getFileHeight().hashCode());
        result = prime * result + ((getFileSuffix() == null) ? 0 : getFileSuffix().hashCode());
        result = prime * result + ((getFilePath() == null) ? 0 : getFilePath().hashCode());
        result = prime * result + ((getFileDesc() == null) ? 0 : getFileDesc().hashCode());
        result = prime * result + ((getStyle() == null) ? 0 : getStyle().hashCode());
        result = prime * result + ((getTexture() == null) ? 0 : getTexture().hashCode());
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
    public ResTexture copy(){
       ResTexture obj =  new ResTexture();
       obj.setName(this.name);
       obj.setFileName(this.fileName);
       obj.setType(this.type);
       obj.setFileCode(this.fileCode);
       obj.setFileSize(this.fileSize);
       obj.setFileWidth(this.fileWidth);
       obj.setFileHeight(this.fileHeight);
       obj.setFileSuffix(this.fileSuffix);
       obj.setFilePath(this.filePath);
       obj.setFileDesc(this.fileDesc);
       obj.setStyle(this.style);
       obj.setTexture(this.texture);
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
       map.put("name",this.name);
       map.put("fileName",this.fileName);
       map.put("type",this.type);
       map.put("fileCode",this.fileCode);
       map.put("fileSize",this.fileSize);
       map.put("fileWidth",this.fileWidth);
       map.put("fileHeight",this.fileHeight);
       map.put("fileSuffix",this.fileSuffix);
       map.put("filePath",this.filePath);
       map.put("fileDesc",this.fileDesc);
       map.put("style",this.style);
       map.put("texture",this.texture);
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
