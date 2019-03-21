package com.sandu.service.act.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.act.model.WxActBargainDecorateRecord;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>

import com.sandu.api.act.model.WxActBargainDecorateRecord;duspace.cn>
 * @datetime 2018-Nov-20 14:34
 */
@Repository
public interface WxActBargainDecorateRecordMapper {
	
    void insertWxActBargainDecorateRecord(WxActBargainDecorateRecord decorateRecord);

    int updateWxActBargainDecorateRecordById(WxActBargainDecorateRecord decorateRecord);

    int deleteWxActBargainDecorateRecordById(@Param("decorateRecordId") String decorateRecordId);

    WxActBargainDecorateRecord selectWxActBargainDecorateRecordById(@Param("decorateRecordId") String decorateRecordId);

	WxActBargainDecorateRecord selectWxActBargainDecorateRecordByRegId(@Param("regId") String regId);

}
