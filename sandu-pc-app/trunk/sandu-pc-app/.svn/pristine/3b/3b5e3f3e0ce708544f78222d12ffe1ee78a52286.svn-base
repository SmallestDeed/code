package com.sandu.cityunion.model;

import com.sandu.common.model.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UnionSpecialOffer extends BaseModel implements Serializable {

    /**  用户ID  **/
    private Integer userId;
    /** 活动开始时间 **/
    private Date effectiveBegin;
    /** 活动结束时间 **/
    private Date effectiveEnd;
    /**  优惠活动名称  **/
    private String specialOfferName;
    /**  优惠活动内容  **/
    private String specialOfferContent;
    /**  是否显示(0否/1是)  **/
    private Integer isDisplayed;
    /**  图片id  **/
    private Integer picId;

    @Override
    public String toString() {
        return "UnionSpecialOffer{" +
                "userId=" + userId +
                ", effectiveBegin=" + effectiveBegin +
                ", effectiveEnd=" + effectiveEnd +
                ", specialOfferName='" + specialOfferName + '\'' +
                ", specialOfferContent='" + specialOfferContent + '\'' +
                ", isDisplayed=" + isDisplayed +
                ", picId=" + picId +
                '}';
    }
}
