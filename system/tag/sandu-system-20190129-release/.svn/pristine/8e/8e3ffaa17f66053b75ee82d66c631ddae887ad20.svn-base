package com.sandu.api.act.service;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sandu.api.act.input.WxActBargainRegistrationQuery;
import com.sandu.api.act.model.WxActBargainRegistration;
import com.sandu.api.act.output.RegistrationStatusVO;
import com.sandu.api.act.output.WxActBargainRegCutResultVO;
import com.sandu.api.act.output.WxActBargainRegDashBoardResultVO;
import com.sandu.api.act.output.WxActBargainRegShipmentInfoVO;
import com.sandu.api.act.output.WxActBargainRegistrationAnalyseResultVO;
import com.sandu.api.user.model.SysUser;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * registration
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:31
 */
public interface WxActBargainRegistrationService {

    /**
     * 插入
     *
     * @param registration
     * @return
     */
    void createWxActBargainRegistration(WxActBargainRegistration wxActBargainRegistration);

    /**
     * 更新
     *
     * @param registration
     * @return
     */
    int modifyWxActBargainRegistration(WxActBargainRegistration wxActBargainRegistration);

    /**
     * 删除
     *
     * @param registrationIds
     * @return
     */
    int removeWxActBargainRegistration(String regId);

    /**
     * 通过ID获取详情
     *
     * @param registrationId
     * @return
     */
     WxActBargainRegistration getWxActBargainRegistration(String regId);

     /**
      * 获取任务状态
      * @param actId
      * @param openId
      * @return
      */
     RegistrationStatusVO getWxActBargainRegistrationStatus(String actId, SysUser user);

     /**
      * 自己砍价
      * @param actId
      * @param user
      * @return
      */
     WxActBargainRegCutResultVO cutPriceByMyself(String actId, SysUser user);

     /**
      * 砍价(装进我家)
      * @param actId
      * @param user
      * @return
      */
     WxActBargainRegCutResultVO cutPriceByDecorate(String actId, SysUser user,Long houseId,String houseName);

	 /**
	  * 好友砍价状态
	  * @param actId
	  * @param openId
	  * @param registrationId
	  * @return
	  */
	 String getWxActBargainInviteRecordCutStatus(String actId, String openId, String registrationId);

	 /**
	  * 邀请好友砍价接口
	  * @param registrationId
	  * @param user
	  * @return
	  */
	 WxActBargainRegCutResultVO cutPriceByInvite(String actId,String registrationId, SysUser user);

	 /**
	  * 未领奖状态更新成待领奖
	  * @param regId
	  * @return
	  */
	int refreshRegAwardStatusToWait(String regId);


	/**
	 * 报名任务分析列表
	 * @param query
	 * @return
	 */
	PageInfo<WxActBargainRegistrationAnalyseResultVO> getWxActBargainRegAnalyseResultList(
			WxActBargainRegistrationQuery query);

	/**
	 * 编辑运单号
	 * @param regId
	 * @param user
	 * @return
	 */
	int modifyShipmentNo(String regId, String carrier,String shipmentNo,SysUser user);

	/**
	 * 图形统计数据列表
	 * @param regId
	 * @return
	 */
	WxActBargainRegDashBoardResultVO getWxActBargainRegDashBoardResultList(String actId,Date beginTime,Date endTime);
     

    List<WxActBargainRegistration> getBargainRegistrationsByActIds(List<String> ids);
    
    List<WxActBargainRegistration> getBargainRegistrationsByActId(String id);

    
    WxActBargainRegShipmentInfoVO getShipmentInfo(String regId);

    /**
     * 获取任务列表
     * @param regIdList
     * @return
     */
	List<WxActBargainRegistration> getWxActBargainRegList(List<String> regIdList);
}
