package com.sandu.service.springFestivalActivity.impl;

import com.sandu.api.springFestivalActivity.model.WxFilmTicket;
import com.sandu.api.springFestivalActivity.service.WxFilmTicketService;
import com.sandu.service.springFestivalActivity.dao.WxFilmTicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wxFilmTicketService")
public class WxFilmTicketServiceImpl implements WxFilmTicketService {
    @Autowired
    private WxFilmTicketMapper wxFilmTicketMapper;

    @Override
    public WxFilmTicket getEmptyTicket(Long activityId) {
        return wxFilmTicketMapper.getEmptyTicket(activityId);
    }

    @Override
    public int insertSelective(WxFilmTicket record) {
        return wxFilmTicketMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(WxFilmTicket record) {
        return wxFilmTicketMapper.updateByPrimaryKeySelective(record);
    }
}
