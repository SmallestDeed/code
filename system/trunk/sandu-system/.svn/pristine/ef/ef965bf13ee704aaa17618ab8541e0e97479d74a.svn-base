package com.sandu.service.act.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.act.input.WxActBargainRegistrationQuery;
import com.sandu.api.act.model.WxActBargainRegistration;
import com.sandu.api.act.output.WxActBargainRegCountResultVO;
import com.sandu.api.act.output.WxActBargainRegistrationAnalyseResultVO;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * registration
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:31
 */
@Repository
public interface WxActBargainRegistrationMapper {
	
    int insertWxActBargainRegistration(WxActBargainRegistration wxActBargainRegistration);

    int updateWxActBargainRegistrationById(WxActBargainRegistration wxActBargainRegistration);

    int deleteWxActBargainRegistrationById(@Param("regId") String regId);

    WxActBargainRegistration selectWxActBargainRegistrationById(@Param("regId") String regId);

	WxActBargainRegistration selectWxActBargainRegistrationByActIdAndOpenId(@Param("actId")String actId, @Param("openId")String openId);

	void updateToReduceRegProductRemainPriceById(@Param("regId")String regId, @Param("cutPrice")Double cutPrice);

	int updateRegCompleteStatusToFinish(@Param("regId") String regId);

	int updateRegAwardStatusToWait(@Param("regId") String regId);


	List<WxActBargainRegistrationAnalyseResultVO> selectWxActBargainRegAnalyseResult(
			WxActBargainRegistrationQuery query);
	
	/**
	 * 参与人数
	 * @param regId
	 * @return
	 */
	List<WxActBargainRegCountResultVO> selectRegCount(@Param("actId") String actId,@Param("beginTime")Date beginTime,@Param("endTime")Date endTime);
	
	/**
	 * 砍价成功人数
	 * @param regId
	 * @return
	 */
	List<WxActBargainRegCountResultVO> selectRegSuccessCount(@Param("actId") String actId,@Param("beginTime")Date beginTime,@Param("endTime")Date endTime);
	
	/**
	 * 所有参与砍价的人数(包括自己,好友,装进我家)
	 * @param regId
	 * @return
	 */
	List<WxActBargainRegCountResultVO> selectCutCount(@Param("actId") String actId,@Param("beginTime")Date beginTime,@Param("endTime")Date endTime);
	


    List<WxActBargainRegistration> getBargainRegistrationsByActIds(@Param("ids") List<String> ids);

    
	List<WxActBargainRegistration> selectWxActBargainRegistrationByIdList(@Param("regIdList") List<String> regIdList);

}
