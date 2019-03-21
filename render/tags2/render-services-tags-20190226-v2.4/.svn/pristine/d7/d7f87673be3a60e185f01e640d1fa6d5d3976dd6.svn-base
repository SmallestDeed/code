package com.nork.design.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * Created by Administrator on 2016/10/24.
 */
public class AutoCarryBaimoProducts implements Serializable {

    /** 产品小类CODE **/
    private String smallTypeCode;
    /** 和白模产品匹配的属性KEY.比如床架比较规格,则传入床架的规格属性的key **/
    private JSONArray contrastAttributeKey;
    /** 需要自动携带的白模产品集合 **/
    private JSONArray baimoProducts;

    public String getSmallTypeCode() {
        return smallTypeCode;
    }

    public void setSmallTypeCode(String smallTypeCode) {
        this.smallTypeCode = smallTypeCode;
    }

    public JSONArray getContrastAttributeKey() {
        return contrastAttributeKey;
    }

    public void setContrastAttributeKey(JSONArray contrastAttributeKey) {
        this.contrastAttributeKey = contrastAttributeKey;
    }

    public JSONArray getBaimoProducts() {
        return baimoProducts;
    }

    public void setBaimoProducts(JSONArray baimoProducts) {
        this.baimoProducts = baimoProducts;
    }
}
