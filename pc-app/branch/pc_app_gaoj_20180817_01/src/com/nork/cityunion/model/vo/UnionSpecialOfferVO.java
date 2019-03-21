package com.nork.cityunion.model.vo;

import java.io.Serializable;

/**
 * Created by kono on 2018/1/18 0018.
 */
public class UnionSpecialOfferVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //优惠活动Id
    private Integer specialOfferId;
    //优惠活动名称
    private String specialOfferName;
    //优惠活动内容
    private String specialOfferContent;

    public Integer getSpecialOfferId() {
        return specialOfferId;
    }

    public void setSpecialOfferId(Integer specialOfferId) {
        this.specialOfferId = specialOfferId;
    }

    public String getSpecialOfferName() {
        return specialOfferName;
    }

    public void setSpecialOfferName(String specialOfferName) {
        this.specialOfferName = specialOfferName;
    }

    public String getSpecialOfferContent() {
        return specialOfferContent;
    }

    public void setSpecialOfferContent(String specialOfferContent) {
        this.specialOfferContent = specialOfferContent;
    }
}
