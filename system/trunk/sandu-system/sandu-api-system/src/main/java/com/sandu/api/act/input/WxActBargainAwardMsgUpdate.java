package com.sandu.api.act.input;

import com.sandu.api.act.model.WxActBargainAwardMsg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * awardMsg
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-20 14:33
 */
@Data
public class WxActBargainAwardMsgUpdate implements Serializable {

    
        
    @ApiModelProperty(value = "消息Id",required = true)
    @NotNull(message = "消息Id不能为空")
    private String id;

        
    @ApiModelProperty(value = "活动id")
    private String actId;
        
        
    @ApiModelProperty(value = "报名id")
    private String registrationId;
        
        
    @ApiModelProperty(value = "兑奖人open_id")
    private String openId;
        
        
    @ApiModelProperty(value = "消息内容")
    private String message;
        
        
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


    public WxActBargainAwardMsg dataTransfer(WxActBargainAwardMsgUpdate update){
        WxActBargainAwardMsg msg = new WxActBargainAwardMsg();
        msg.setId(update.getId());
        msg.setActId(update.getActId());
        msg.setRegistrationId(update.getRegistrationId());
        msg.setOpenId(update.getOpenId());
        msg.setMessage(update.getMessage());
        msg.setAppId(update.getAppId());
        msg.setCreator(update.getCreator());
        msg.setGmtCreate(update.getGmtCreate());
        msg.setModifier(update.getModifier());
        msg.setGmtModified(update.getGmtModified());
        msg.setIsDeleted(update.getIsDeleted());
        return msg;
    }
        
    
}
