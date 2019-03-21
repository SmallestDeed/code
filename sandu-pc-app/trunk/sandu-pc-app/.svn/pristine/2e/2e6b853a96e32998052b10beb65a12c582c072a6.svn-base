package com.sandu.product.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sandu.product.model.po.BaseBrandPO;

/**   
 * @Title: BaseBrand.java 
 * @Package com.nork.product.model
 * @Description:产品-品牌表
 * @createAuthor pandajun 
 * @CreateDate 2015-06-16 10:03:47
 * @version V1.0   
 */
public class BaseBrand extends BaseBrandPO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private List<Integer> brandIds = new ArrayList<Integer>();

	/**
	 * companyIndustry 公司所属行业
	 * companySmallType 公司细分行业
	 * authorizedBigType 授权码大分类
	 * authorizedSmallTypeIds 授权码小分类
	 * authorizedProductIds 授权码产品ids
	 * companyProductSmallValueKeys 公司细分行业产品小分类keys
	 * add by xiaoxc 20171118
	 */
	private Integer companyIndustry;
	private Integer companySmallType;
	private String authorizedBigType;
	private String authorizedSmallTypeIds;
	private String authorizedProductIds;
	private String companyProductSmallValueKeys;

	private String  deviceId = null;
	private String  msgId = null;
	private String  ids = null;
	private Integer start = 0;
	private Integer limit = 20;
	private String  order = null;
	private String  orderNum = null;
	private String  orders = null;
	/**级别限制的资源数量*/
	private int levelLimitCount=0;
	
	public String getCompanyProductSmallValueKeys() {
		return companyProductSmallValueKeys;
	}

	public void setCompanyProductSmallValueKeys(String companyProductSmallValueKeys) {
		this.companyProductSmallValueKeys = companyProductSmallValueKeys;
	}

	public String getAuthorizedBigType() {
		return authorizedBigType;
	}

	public void setAuthorizedBigType(String authorizedBigType) {
		this.authorizedBigType = authorizedBigType;
	}

	public String getAuthorizedSmallTypeIds() {
		return authorizedSmallTypeIds;
	}

	public void setAuthorizedSmallTypeIds(String authorizedSmallTypeIds) {
		this.authorizedSmallTypeIds = authorizedSmallTypeIds;
	}

	public String getAuthorizedProductIds() {
		return authorizedProductIds;
	}

	public void setAuthorizedProductIds(String authorizedProductIds) {
		this.authorizedProductIds = authorizedProductIds;
	}

	public Integer getCompanyIndustry() {
		return companyIndustry;
	}

	public void setCompanyIndustry(Integer companyIndustry) {
		this.companyIndustry = companyIndustry;
	}

	public Integer getCompanySmallType() {
		return companySmallType;
	}

	public void setCompanySmallType(Integer companySmallType) {
		this.companySmallType = companySmallType;
	}

	public List<Integer> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Integer> brandIds) {
		this.brandIds = brandIds;
	}

	private String brandStyleName;
	
	private String brandLogoPath;
	
	public String getBrandLogoPath() {
		return brandLogoPath;
	}

	public void setBrandLogoPath(String brandLogoPath) {
		this.brandLogoPath = brandLogoPath;
	}

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

	public String getBrandStyleName() {
		return brandStyleName;
	}

	public void setBrandStyleName(String brandStyleName) {
		this.brandStyleName = brandStyleName;
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
    /*public BaseBrand copy(){
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
    }*/
    
     /**获取对象的map**/
    /*public Map toMap(){
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
    }*/
}
