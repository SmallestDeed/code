package com.nork.mobile.model.search;

import com.nork.common.model.Mapper;
import com.nork.system.model.bo.SysDictionaryBo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 1:57 2018/5/23 0023
 * @Modified By:
 */
public class MobileDesignPlanProduct extends Mapper implements Serializable{
    //推荐方案或效果图方案id
    private Integer planId;
    //平台id
    private Integer platformId;
    //产品可见范围对应的大小类
    private List<SysDictionaryBo> productTypeValueList;
    //经销商经销品牌集合
    private List<Integer> brandIdList;
    //公司id
    private Integer companyId;

    public List<Integer> getBrandIdList() {
        return brandIdList;
    }

    public void setBrandIdList(List<Integer> brandIdList) {
        this.brandIdList = brandIdList;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<SysDictionaryBo> getProductTypeValueList() {
        return productTypeValueList;
    }

    public void setProductTypeValueList(List<SysDictionaryBo> productTypeValueList) {
        this.productTypeValueList = productTypeValueList;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }
}
