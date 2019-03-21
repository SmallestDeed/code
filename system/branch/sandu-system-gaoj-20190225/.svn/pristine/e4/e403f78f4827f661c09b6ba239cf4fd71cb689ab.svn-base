package com.sandu.service.act.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.act.model.WxActBargain;

import java.util.List;

import java.util.Date;

@Repository
public interface WxActBargainMapper {
	
    int insertWxActBargain(WxActBargain WxActBargain);

    int updateWxActBargainById(WxActBargain WxActBargain);

    int deleteWxActBargainById(@Param("actId") String actId);

    WxActBargain selectWxActBargainById(@Param("actId") String actId);
    
    WxActBargain selectWxActBargainByIdAndAppId(@Param("actId") String actId,@Param("appId") String appId);

	int updateToReduceProductRemainCount(@Param("actId") String actId);

	void updateToIncreaseRegistrationCount(@Param("actId") String actId);

    List<WxActBargain> selectByAppids(@Param("appids") List<String> appids,@Param("page") Integer page, @Param("limit")Integer limit);
    /**
     * 查询所有有效砍价活动
     * @author : WangHaiLin
     * @param currentTime 当前时间
     * @date : 2018/11/22 19:27
     * @return java.util.List<com.sandu.api.act.model.WxActBargainAward>
     *
     */
    List<WxActBargain> selectAllEffectiveBargainAward(@Param("currentTime") Date currentTime);

	List<WxActBargain> selectWillExpireList();
    /**
     * 定时任务，扣库存
     */
    void updateVitualCount();


    int countByAppids(@Param("appids")List<String> appids);

    WxActBargain selectByActName(String actName);
}
