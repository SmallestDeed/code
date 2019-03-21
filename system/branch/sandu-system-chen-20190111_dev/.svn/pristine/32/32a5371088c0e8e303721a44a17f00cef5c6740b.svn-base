package com.sandu.api.act.input;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * bargain
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-20 14:24
 */
@Data
public class WxActBargainAdd implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;

    @NotNull(message = "活动名称不能为空")
    @ApiModelProperty(value = "砍价活动名称")
    private String actName;
        
            
    @ApiModelProperty(value = "活动描述")
    private String actDetail;
        
            
    @ApiModelProperty(value = "活动规则")
    private String actRule;

    @ApiModelProperty(value = "转发图片")
    private  String shareImg;
        
    @NotNull(message = "活动开始时间不能为空")
    @ApiModelProperty(value = "活动开始时间")
    private Date begainTime;

    @NotNull(message = "活动结束时间不能为空")
    @ApiModelProperty(value = " 活动结束时间")
    private Date endTime;

    @NotNull(message = "产品名称不能为空")
    @ApiModelProperty(value = "产品名称")
    private String productName;

    @NotNull(message = "产品原价不能不空")
    @ApiModelProperty(value = "原价")
    private Double productOriginalPrice;

    @NotNull(message = "产品优惠价不能为空")
    @ApiModelProperty(value = "优惠价")
    private Double productDiscountPrice;

    @NotNull(message = "产品底价不能为空")
    @ApiModelProperty(value = "底价")
    private Double productMinPrice;
        
            
    @ApiModelProperty(value = "产品图片")
    private String productImg;

    @NotNull(message = "产品数量不能为空")
    @ApiModelProperty(value = "产品数量")
    private Integer productCount;
        

    @ApiModelProperty(value = "产品剩余数量")
    private Integer productRemainCount;
        
            
    @ApiModelProperty(value = "只记录虚拟扣除数量,不做逻辑处理(定时任务定时扣除)")
    private Integer productVitualCount;

    //@NotNull(message = "参与人数不能为空")
    @ApiModelProperty(value = "参与人数")
    private Integer registrationCount;
        
            
    @ApiModelProperty(value = "系统每小时扣减数量:(定时任务定时扣除,可当作参与人数与减少库存数)")
    private Integer sysReduceNum;

    @NotNull(message = "自己砍价最低金额不能为空")
    @ApiModelProperty(value = "自己砍价最低金额")
    private Double myCutPriceMin;

    @NotNull(message = "自己砍价最高金额不能为空")
    @ApiModelProperty(value = "自己砍价最高金额")
    private Double myCutPriceMax;

    @NotNull(message = "好友砍价最低金额不能为空")
    @ApiModelProperty(value = "好友砍价最低金额")
    private Double cutMethodPriceMin;

    @NotNull(message = "好友砍价最高金额不能为空")
    @ApiModelProperty(value = "好友砍价最高金额")
    private Double cutMethodPriceMax;
        
            
    @ApiModelProperty(value = "最少邀请好友数")
    private Integer cutMethodInviteNumMin;
        
            
    @ApiModelProperty(value = "最多邀请好友数")
    private Integer cutMethodInviteNumMax;
        
            
    @ApiModelProperty(value = "是否有效0:无效,1:有效")
    private Integer isEnable;
        
    @NotNull(message = "微信appid不能为空")
    @ApiModelProperty(value = "微信appid")
    private String appId;
        
            
    @ApiModelProperty(value = "小程序名称")
    private String appName;
        
            
    @ApiModelProperty(value = "小程序所属企业id")
    private Integer companyId;
        
            
    @ApiModelProperty(value = "小程序所属企业名称")
    private String companyName;
        
            
    @ApiModelProperty(value = "创建人")
    private String creator;
        
            
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
        
            
    @ApiModelProperty(value = "修改人")
    private String modifier;
        
            
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
        
            
    @ApiModelProperty(value = "是否删除：0未删除、1已删除")
    private Integer isDeleted;

}
