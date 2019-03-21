package com.sandu.service.act.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.act.model.WxActBargainAward;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * award
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:25
 */
@Repository
public interface WxActBargainAwardMapper {
	
    void insertWxActBargainAward(WxActBargainAward award);

    int updateWxActBargainAwardById(WxActBargainAward award);

    int deleteWxActBargainAwardById(@Param("awardId") String awardId);

    WxActBargainAward selectWxActBargainAwardById(@Param("awardId") String awardId);

}
