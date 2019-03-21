package com.sandu.cityunion.model;

import com.sandu.common.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 联盟门店明细表
 */
@Data
public class UnionGroupDetails extends BaseModel implements Serializable{

	private static final long serialVersionUID = -8848121243720863815L;
	
    /** 联盟门店主表ID **/
    private Integer groupId;
    /** 门店名称 **/
    private String name;
    /** 品牌 **/
    private Integer brandId;
    /** 联系人 **/
    private String contact;
    /** 联系电话 **/
    private String phone;
    /** 联系地址 **/
    private String address;
    /** 店铺id**/
    private Integer companyShopId;

    /** 用户ID **/
    private Integer userId;
    /** 企业**/
    private Integer companyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnionGroupDetails)) return false;
        if (!super.equals(o)) return false;

        UnionGroupDetails that = (UnionGroupDetails) o;

        if (getGroupId() != null ? !getGroupId().equals(that.getGroupId()) : that.getGroupId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getBrandId() != null ? !getBrandId().equals(that.getBrandId()) : that.getBrandId() != null) return false;
        if (getContact() != null ? !getContact().equals(that.getContact()) : that.getContact() != null) return false;
        if (getPhone() != null ? !getPhone().equals(that.getPhone()) : that.getPhone() != null) return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        if (getCompanyShopId() != null ? !getCompanyShopId().equals(that.getCompanyShopId()) : that.getCompanyShopId() != null)
            return false;
        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        return getCompanyId() != null ? getCompanyId().equals(that.getCompanyId()) : that.getCompanyId() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getGroupId() != null ? getGroupId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getBrandId() != null ? getBrandId().hashCode() : 0);
        result = 31 * result + (getContact() != null ? getContact().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getCompanyShopId() != null ? getCompanyShopId().hashCode() : 0);
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + (getCompanyId() != null ? getCompanyId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UnionGroupDetails{" +
                "groupId=" + groupId +
                ", name='" + name + '\'' +
                ", brandId=" + brandId +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", companyShopId=" + companyShopId +
                ", userId=" + userId +
                ", companyId=" + companyId +
                '}';
    }


}
