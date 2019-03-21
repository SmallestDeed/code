package com.sandu.service.springFestivalActivity.impl;

import com.sandu.api.springFestivalActivity.model.WxUserTask;
import com.sandu.api.springFestivalActivity.service.WxUserTaskService;
import com.sandu.service.springFestivalActivity.dao.WxUserTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wxUserTaskService")
public class WxUserTaskServiceImpl implements WxUserTaskService {
    @Autowired
    private WxUserTaskMapper wxUserTaskMapper;

    @Override
    public List<WxUserTask> selectSelective(WxUserTask wxUserTask) {
        return wxUserTaskMapper.selectSelective(wxUserTask);
    }
}
