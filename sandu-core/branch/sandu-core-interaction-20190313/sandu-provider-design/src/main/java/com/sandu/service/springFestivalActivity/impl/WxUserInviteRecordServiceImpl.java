package com.sandu.service.springFestivalActivity.impl;

import com.sandu.api.springFestivalActivity.model.WxUserInviteRecord;
import com.sandu.api.springFestivalActivity.service.WxUserInviteRecordService;
import com.sandu.service.springFestivalActivity.dao.WxUserInviteRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wxUserInviteRecordService")
public class WxUserInviteRecordServiceImpl implements WxUserInviteRecordService {
    @Autowired
    private WxUserInviteRecordMapper wxUserInviteRecordMapper;

    @Override
    public int insert(WxUserInviteRecord record) {
        return wxUserInviteRecordMapper.insert(record);
    }

    @Override
    public List<WxUserInviteRecord> selectSelective(WxUserInviteRecord record, Integer start, Integer limit) {
        return wxUserInviteRecordMapper.selectSelective(record, start, limit);
    }

    @Override
    public Integer selectSelectiveCount(WxUserInviteRecord record) {
        return wxUserInviteRecordMapper.selectSelectiveCount(record);
    }
}
