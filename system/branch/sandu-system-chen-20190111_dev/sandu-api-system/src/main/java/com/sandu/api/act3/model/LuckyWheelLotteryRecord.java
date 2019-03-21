package com.sandu.api.act3.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sandu.api.common.exception.SystemException;

import lombok.Data;

@Data
public class LuckyWheelLotteryRecord implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 未中奖
	 */
	public static final Integer AWARDS_STATUS_NONE_AWARD = 0;
	/**
	 * 未领奖
	 */
	public static final Integer AWARDS_STATUS_UNAWRD = 5;
	/**
	 * 待发放
	 */
	public static final Integer AWARDS_STATUS_WAIT_AWARD = 10;
	/**
	 * 已发放
	 */
	public static final Integer AWARDS_STATUS_AWARDED = 20;
	
	/**
	 * 未发货
	 */
	public static final Integer SHIPMENT_STATUS_UNSHIP = 0;
	
	/**
	 * 已发货
	 */
	public static final Integer SHIPMENT_STATUS_SHIP = 1;
	
	public static String getAwardsStatusText(Integer awardsStatusCode) {
		if (awardsStatusCode == null)
			throw new SystemException("参数不能为空");

		switch (awardsStatusCode) {
			case 0:
				return "未中奖";
			case 5:
				return "未领奖";
			case 10:
				return "待发放";
			case 20:
				return "已发放";
			default:
				throw new SystemException("非法参数");
		}

	}
	
    private String id;

    private String actId;

    private String regId;

    private String openId;

    private String nickname;

    private String headPic;

    private Date lotteryTime;

    private Integer lotterySeq;

    private String prizeId;

    private String prizeName;

    private Integer awardsStatus;

	private Integer shipmentStatus;

	private String carrier;

	private String shipmentNo;
    
    private Date receiveTime;

    private String receiver;

    private String mobile;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private String address;

    private String appId;

    private Long companyId;

    private Date gmtCreate;

    private Integer isDeleted;
   
    private List<Integer> awardsStatusList;

    //运营平台列表查询用参
    private Date lotteryTimeStart;
	private Date lotteryTimeEnd;
    
}