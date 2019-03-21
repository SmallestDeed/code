package com.sandu.service.springFestivalActivity.impl;

import com.sandu.api.springFestivalActivity.model.WxUserCard;
import com.sandu.api.springFestivalActivity.service.WxUserCardService;
import com.sandu.service.springFestivalActivity.dao.WxUserCardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wxUserCardService")
public class WxUserCardServiceImpl implements WxUserCardService {
    @Autowired
    private WxUserCardMapper wxUserCardMapper;

    @Override
    public List<WxUserCard> selectSelective(WxUserCard record, Integer start, Integer limit) {
        return wxUserCardMapper.selectSelective(record, start, limit);
    }

    @Override
    public Integer insertSelective(WxUserCard record) {
        return wxUserCardMapper.insertSelective(record);
    }
}
