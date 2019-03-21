package com.sandu.panorama.model;

import com.sandu.common.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/***
 * 720打组制作主表
 */
@Data
public class DesignPlanStoreRelease  extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 453428507971936000L;
	
	/** 唯一标识 **/
    private String uuid;
    /** 用户ID **/
    private Integer userId;
    /** 分享标题**/
    private String shareTitle;
    /** 分享类型（全户型分享、随意组合分享） **/
    private Integer shareType;
    /** 浏览量 **/
    private Integer pv;
    /** 联盟门店主表ID **/
    private Integer unionGroupId;
    /** 优惠活动ID **/
    private Integer unionSpecialOfferId;
    /** 联系人ID **/
    private Integer unionContactId;
    /** 户型ID **/
    private Integer houseId;
    /** 户型缩略图ID **/
    private Integer housePicId;

    /**
     * 分享类型
     */
    public class ShareType{
        /** 全户型分享 **/
        public static final int FULL_HOUSE = 1;
        /** 随意组合分享 **/
        public static final int CASUAL = 2;
    }

    @Override
    public String toString(){

        return "DesignPlanStoreRelease{" +
                "id : " + this.getId() +
                ",uuid : " + uuid +
                ",userId : " + userId +
                ",shareTitle : " + shareTitle +
                ",shareType : " + shareType +
                ",pv : " + pv +
                ",unionGroupId : " + unionGroupId +
                ",unionSpecialOfferId : " + unionSpecialOfferId +
                ",unionContactId : " + unionContactId +
                ",houseId : " + houseId +
                ",housePicId : " + housePicId +
                "}";
    }

}
