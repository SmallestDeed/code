package com.sandu.service.act.dao;

import com.sandu.api.act.input.WxActBargainAwardMsgQuery;
import com.sandu.api.act.output.WxActBargainAwardMsgVO;
import com.sandu.api.act.output.WxActBargainAwardMsgWebVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.act.model.WxActBargainAwardMsg;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * awardMsg
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:33
 */
@Repository
public interface WxActBargainAwardMsgMapper {
	
    void insertWxActBargainAwardMsg(WxActBargainAwardMsg awardMsg);

    int updateWxActBargainAwardMsgById(WxActBargainAwardMsg awardMsg);

    int deleteWxActBargainAwardMsgById(@Param("awardMsgId") String awardMsgId, @Param("modifier")String modifier);

    WxActBargainAwardMsg selectWxActBargainAwardMsgById(@Param("awardMsgId") String awardMsgId);

    List<WxActBargainAwardMsgVO> selectListByActId(WxActBargainAwardMsgQuery query);

    int selectCountByActId(WxActBargainAwardMsgQuery query);

    List<WxActBargainAwardMsgWebVO> selectMsgRandomList(@Param("actId") String actId);

}
