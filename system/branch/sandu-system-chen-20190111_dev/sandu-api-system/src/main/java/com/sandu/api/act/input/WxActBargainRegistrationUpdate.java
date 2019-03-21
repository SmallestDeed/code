package com.sandu.api.act.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * registration
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-20 14:31
 */
@Data
public class WxActBargainRegistrationUpdate implements Serializable {

    
        
    @ApiModelProperty(value = "")
        
    private String id;
        
        
        
    @ApiModelProperty(value = "活动id")
        
        
    private String actId;
        
        
    @ApiModelProperty(value = "活动参与人open_id")
        
        
    private String openId;
        
        
    @ApiModelProperty(value = "活动参与人昵称")
        
        
    private String nickname;
        
        
    @ApiModelProperty(value = "活动参与人头像")
        
        
    private String headPic;
        
        
    @ApiModelProperty(value = "产品名称")
        
        
    private String productName;
        
        
    @ApiModelProperty(value = "产品价格")
        
        
    private String productPrice;
        
        
    @ApiModelProperty(value = "砍后价格")
        
        
    private String productRemainPrice;
        
        
    @ApiModelProperty(value = "好友砍价最低金额")
        
        
    private String cutMethodPriceMin;
        
        
    @ApiModelProperty(value = "好友砍价最高金额")
        
        
    private String cutMethodPriceMax;
        
        
    @ApiModelProperty(value = "最少邀请好友数")
        
        
    private Integer cutMethodInviteNum;
        
        
    @ApiModelProperty(value = "邀请状态:0:未邀请,10:已邀请")
        
        
    private Integer inviteStatus;
        
        
    @ApiModelProperty(value = "装修状态:0:未装修,10:已装修")
        
        
    private Integer decorateStatus;
        
        
    @ApiModelProperty(value = "领奖状态0:未领奖,10:待领奖,20:已领奖")
        
        
    private Integer awardsStatus;
        
        
    @ApiModelProperty(value = "异常状态0:正常,10:无库存;")
        
        
    private Integer exceptionStatus;
        
        
    @ApiModelProperty(value = "完成状态0:未完成,10:已完成;")
        
        
    private Integer completeStatus;
        
        
    @ApiModelProperty(value = "发货状态0:未发货,10:已发货;")
        
        
    private Integer shipmentStatus;
        
        
    @ApiModelProperty(value = "好友砍价金额")
        
        
    private Integer inviteCutPriceSum;
        
        
    @ApiModelProperty(value = "砍价人数")
        
        
    private Integer inviteCutRecordCount;
        
        
    @ApiModelProperty(value = "发货单号")
        
        
    private String shipmentNo;
        
        
    @ApiModelProperty(value = "微信appid")
        
        
    private String appId;
        
        
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
