package com.nork.product.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.model.Mapper;

/**   
 * @Title: BaseBrand.java 
 * @Package com.nork.product.model
 * @Description:产品-品牌表
 * @createAuthor pandajun 
 * @CreateDate 2015-06-16 10:03:47
 * @version V1.0   
 */
public class BaseBrand  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
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
	/**  品牌编码  **/
	private String brandCode;
	/**  品牌名称  **/
	private String brandName;
	private String brandReferred;
	
	private List<Integer> brandIds = new ArrayList<Integer>();
	
	/**
	 * 是否显示无品牌开关(0:默认显示无品牌,1:不显示无品牌)
	 * add by huangsongbo 2017.11.4
	 */
	private Integer statusShowWu;
	
	public Integer getStatusShowWu() {
		return statusShowWu;
	}

	public void setStatusShowWu(Integer statusShowWu) {
		this.statusShowWu = statusShowWu;
	}

	public List<Integer> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Integer> brandIds) {
		this.brandIds = brandIds;
	}

	public String getBrandReferred() {
		return brandReferred;
	}

	public void setBrandReferred(String brandReferred) {
		this.brandReferred = brandReferred;
	}

	/**  企业ID  **/
	private Integer companyId;
	private Integer brandStyleId;
	private String brandStyleName;
	/**  品牌logo  **/
	private String brandLogo;
	
	private String brandLogoPath;
	
	public String getBrandLogoPath() {
		return brandLogoPath;
	}

	public void setBrandLogoPath(String brandLogoPath) {
		this.brandLogoPath = brandLogoPath;
	}

	/**  品牌介绍  **/
	private String brandDesc;
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/
	private String att2;
	/**  字符备用3  **/
	private String att3;
	/**  字符备用4  **/
	private String att4;
	/**  字符备用5  **/
	private String att5;
	/**  字符备用6  **/
	private String att6;
	/**  时间备用1  **/
	private Date dateAtt1;
	/**  时间备用2  **/
	private Date dateAtt2;
	/**  整数备用1  **/
	private Integer numAtt1;
	/**  整数备用2  **/
	private Integer numAtt2;
	/**  数字备用1  **/
	private Double numAtt3;
	/**  数字备用2  **/
	private Double numAtt4;
	/**  备注  **/
	private String remark;
	
	//公司名称
	private String companyName;

	
	
	/**方案推荐专用  Begin >>*/
	
	/*design_plan_brand 表中的关联brand_id ,-1为所有品牌*/
	private Integer brandAssociatedId;
	/* 品牌id*/
	private Integer brandId;
	/*design_plan_brand 表主键Id*/
	private Integer designPlanBrandId;
	
	/**方案推荐专用  End  <<*/
 

	 

	public  String getSysCode() {
		return sysCode;
	}
	public Integer getBrandAssociatedId() {
		return brandAssociatedId;
	}

	public void setBrandAssociatedId(Integer brandAssociatedId) {
		this.brandAssociatedId = brandAssociatedId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getDesignPlanBrandId() {
		return designPlanBrandId;
	}

	public void setDesignPlanBrandId(Integer designPlanBrandId) {
		this.designPlanBrandId = designPlanBrandId;
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
	public  String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode){
		this.brandCode = brandCode;
	}
	public  String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName){
		this.brandName = brandName;
	}
	public  Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId){
		this.companyId = companyId;
	}
	
	public Integer getBrandStyleId() {
		return brandStyleId;
	}

	public void setBrandStyleId(Integer brandStyleId) {
		this.brandStyleId = brandStyleId;
	}
	
	public String getBrandStyleName() {
		return brandStyleName;
	}

	public void setBrandStyleName(String brandStyleName) {
		this.brandStyleName = brandStyleName;
	}

	public  String getBrandLogo() {
		return brandLogo;
	}
	public void setBrandLogo(String brandLogo){
		this.brandLogo = brandLogo;
	}
	public  String getBrandDesc() {
		return brandDesc;
	}
	public void setBrandDesc(String brandDesc){
		this.brandDesc = brandDesc;
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
	public  String getAtt3() {
		return att3;
	}
	public void setAtt3(String att3){
		this.att3 = att3;
	}
	public  String getAtt4() {
		return att4;
	}
	public void setAtt4(String att4){
		this.att4 = att4;
	}
	public  String getAtt5() {
		return att5;
	}
	public void setAtt5(String att5){
		this.att5 = att5;
	}
	public  String getAtt6() {
		return att6;
	}
	public void setAtt6(String att6){
		this.att6 = att6;
	}
	public  Date getDateAtt1() {
		return dateAtt1;
	}
	public void setDateAtt1(Date dateAtt1){
		this.dateAtt1 = dateAtt1;
	}
	public  Date getDateAtt2() {
		return dateAtt2;
	}
	public void setDateAtt2(Date dateAtt2){
		this.dateAtt2 = dateAtt2;
	}
	public  Integer getNumAtt1() {
		return numAtt1;
	}
	public void setNumAtt1(Integer numAtt1){
		this.numAtt1 = numAtt1;
	}
	public  Integer getNumAtt2() {
		return numAtt2;
	}
	public void setNumAtt2(Integer numAtt2){
		this.numAtt2 = numAtt2;
	}
	public  Double getNumAtt3() {
		return numAtt3;
	}
	public void setNumAtt3(Double numAtt3){
		this.numAtt3 = numAtt3;
	}
	public  Double getNumAtt4() {
		return numAtt4;
	}
	public void setNumAtt4(Double numAtt4){
		this.numAtt4 = numAtt4;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}


    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
        BaseBrand other = (BaseBrand) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getBrandCode() == null ? other.getBrandCode() == null : this.getBrandCode().equals(other.getBrandCode()))
            && (this.getBrandName() == null ? other.getBrandName() == null : this.getBrandName().equals(other.getBrandName()))
            && (this.getCompanyId() == null ? other.getCompanyId() == null : this.getCompanyId().equals(other.getCompanyId()))
            && (this.getBrandLogo() == null ? other.getBrandLogo() == null : this.getBrandLogo().equals(other.getBrandLogo()))
            && (this.getBrandDesc() == null ? other.getBrandDesc() == null : this.getBrandDesc().equals(other.getBrandDesc()))
            && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getAtt3() == null ? other.getAtt3() == null : this.getAtt3().equals(other.getAtt3()))
            && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
            && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
            && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
            && (this.getDateAtt1() == null ? other.getDateAtt1() == null : this.getDateAtt1().equals(other.getDateAtt1()))
            && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
            && (this.getNumAtt1() == null ? other.getNumAtt1() == null : this.getNumAtt1().equals(other.getNumAtt1()))
            && (this.getNumAtt2() == null ? other.getNumAtt2() == null : this.getNumAtt2().equals(other.getNumAtt2()))
            && (this.getNumAtt3() == null ? other.getNumAtt3() == null : this.getNumAtt3().equals(other.getNumAtt3()))
            && (this.getNumAtt4() == null ? other.getNumAtt4() == null : this.getNumAtt4().equals(other.getNumAtt4()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getBrandCode() == null) ? 0 : getBrandCode().hashCode());
        result = prime * result + ((getBrandName() == null) ? 0 : getBrandName().hashCode());
        result = prime * result + ((getCompanyId() == null) ? 0 : getCompanyId().hashCode());
        result = prime * result + ((getBrandLogo() == null) ? 0 : getBrandLogo().hashCode());
        result = prime * result + ((getBrandDesc() == null) ? 0 : getBrandDesc().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getAtt3() == null) ? 0 : getAtt3().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getDateAtt1() == null) ? 0 : getDateAtt1().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getNumAtt1() == null) ? 0 : getNumAtt1().hashCode());
        result = prime * result + ((getNumAtt2() == null) ? 0 : getNumAtt2().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public BaseBrand copy(){
       BaseBrand obj =  new BaseBrand();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setBrandCode(this.brandCode);
       obj.setBrandName(this.brandName);
       obj.setCompanyId(this.companyId);
       obj.setBrandLogo(this.brandLogo);
       obj.setBrandDesc(this.brandDesc);
       obj.setAtt1(this.att1);
       obj.setAtt2(this.att2);
       obj.setAtt3(this.att3);
       obj.setAtt4(this.att4);
       obj.setAtt5(this.att5);
       obj.setAtt6(this.att6);
       obj.setDateAtt1(this.dateAtt1);
       obj.setDateAtt2(this.dateAtt2);
       obj.setNumAtt1(this.numAtt1);
       obj.setNumAtt2(this.numAtt2);
       obj.setNumAtt3(this.numAtt3);
       obj.setNumAtt4(this.numAtt4);
       obj.setRemark(this.remark);

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("brandCode",this.brandCode);
       map.put("brandName",this.brandName);
       map.put("companyId",this.companyId);
       map.put("brandLogo",this.brandLogo);
       map.put("brandDesc",this.brandDesc);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("att3",this.att3);
       map.put("att4",this.att4);
       map.put("att5",this.att5);
       map.put("att6",this.att6);
       map.put("dateAtt1",this.dateAtt1);
       map.put("dateAtt2",this.dateAtt2);
       map.put("numAtt1",this.numAtt1);
       map.put("numAtt2",this.numAtt2);
       map.put("numAtt3",this.numAtt3);
       map.put("numAtt4",this.numAtt4);
       map.put("remark",this.remark);

       return map;
    }
}
