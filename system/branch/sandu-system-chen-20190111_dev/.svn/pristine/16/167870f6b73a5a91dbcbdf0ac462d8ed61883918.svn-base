package com.sandu.api.act3.output;

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
public class LuckyWheelLotteryVO implements Serializable {

	/**
	 * 是否中奖
	 */
    private boolean hasPrize;
    
	private String prizeId;
	
	/**
	 * 奖品名称(包括谢谢参与)
	 */
	private String prizeName;
    
    /**
     * 中奖id
     */
	private String awardId;
	
    /**
     * 是否已绑定手机号
     */
    private boolean hasBindMobile;
    /**
     * 是否需要地址
     */
    private boolean needAddress;
    
    
}
