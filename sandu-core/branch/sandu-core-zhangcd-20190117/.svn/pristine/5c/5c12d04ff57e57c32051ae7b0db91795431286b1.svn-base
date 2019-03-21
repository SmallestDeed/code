package com.sandu.service.springFestivalActivity.impl;

import com.sandu.api.base.common.exception.BizException;
import com.sandu.api.springFestivalActivity.model.WxRedPacketSummary;
import com.sandu.api.springFestivalActivity.service.WxRedPacketSummaryService;
import com.sandu.service.springFestivalActivity.dao.WxRedPacketSummaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("wxRedPacketSummaryService")
public class WxRedPacketSummaryServiceImpl implements WxRedPacketSummaryService {

    @Autowired
    private WxRedPacketSummaryMapper wxRedPacketSummaryMapper;

    @Override
    public WxRedPacketSummary getBySignInDate(Long activityId, Date signInDate) {
        if (null == activityId || 0 >= activityId) {
            throw new BizException("获取签到红包记录，activityId为空");
        }
        if (null == signInDate) {
            throw new BizException("获取签到红包记录，date为空");
        }

        return wxRedPacketSummaryMapper.getBySignInDate(activityId, signInDate);
    }

    @Override
    public int insert(WxRedPacketSummary wxRedPacketSummary) {
        return wxRedPacketSummaryMapper.insertSelective(wxRedPacketSummary);
    }

    @Override
    public int update(WxRedPacketSummary wxRedPacketSummary) {
        return wxRedPacketSummaryMapper.updateByPrimaryKeySelective(wxRedPacketSummary);
    }
}
