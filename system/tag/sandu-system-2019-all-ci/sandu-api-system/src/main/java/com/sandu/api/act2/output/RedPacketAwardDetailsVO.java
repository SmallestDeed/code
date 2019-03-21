package com.sandu.api.act2.output;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * bargain
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:24
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@AllArgsConstructor
public class RedPacketAwardDetailsVO implements Serializable {

	
	/**
	 * 第几个红包
	 */
	private Integer redPacketSeq;
	/**
	 * 需要邀请人数
	 */
	private Integer requestInviteNum;
	/**
	 * 红包描述
	 */
	private String redPacketDesc;
	
	/**
	 * 是否已领奖
	 */
	private boolean hasAward;
	/**
	 * 是否可以领奖
	 */
	private boolean canAward;
    
    
}
