package com.sandu.service.springFestivalActivity.impl;

import com.sandu.api.springFestivalActivity.model.WxSpringActivity;
import com.sandu.api.springFestivalActivity.output.DateVo;
import com.sandu.api.springFestivalActivity.service.WxSpringActivityService;
import com.sandu.service.springFestivalActivity.dao.WxSpringActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wxSpringActivityService")
public class WxSpringActivityServiceImpl implements WxSpringActivityService {
    @Autowired
    private WxSpringActivityMapper wxSpringActivityMapper;

    @Override
    public WxSpringActivity selectByPrimaryKey(Long id) {
        return wxSpringActivityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WxSpringActivity record) {
        return wxSpringActivityMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public DateVo getSignInDate(long activityId) {
        return wxSpringActivityMapper.getSignInDate(activityId);
    }

    @Override
    public int decrRedPacketRemainNum(WxSpringActivity wxSpringActivity) {
        return wxSpringActivityMapper.decrRedPacketRemainNum(wxSpringActivity);
    }
}
