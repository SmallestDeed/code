package com.sandu.api.act.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * inviteRecord
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:36
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class WxActBargainInviteRecordVO implements Serializable {

	
	
    public WxActBargainInviteRecordVO(String nickname, String headPic, Double cutPrice,String cutTime) {
		super();
		this.nickname = nickname;
		this.headPic = headPic;
		this.cutPrice = cutPrice;
		this.cutTime = cutTime;
	}


	@ApiModelProperty(value = "砍价人昵称")
    private String nickname;
        
        
    @ApiModelProperty(value = "活动参与人头像")
    private String headPic;
        
        
    @ApiModelProperty(value = "砍掉价格")
    private Double cutPrice;
    
    @ApiModelProperty(value = "砍价时间")
    private String cutTime;
        
}
