package com.sandu.service.activity.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.activity.input.WxSpringActivityAdd;
import com.sandu.api.activity.input.WxSpringActivityQuery;
import com.sandu.api.activity.model.WxSpringActivity;
import com.sandu.api.activity.model.WxSpringActivityBO;
import com.sandu.api.activity.service.WxSpringActivityService;
import com.sandu.api.user.model.User;
import com.sandu.service.activity.dao.WxSpringActivityMapper;
import com.sandu.service.user.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Yuxc
 * @Description:
 * @Date: 2019/1/18
 */

@Slf4j
@Service("wxSpringActivityService")
public class WxSpringActivityServiceImpl implements WxSpringActivityService {

    @Autowired
    private WxSpringActivityMapper wxSpringActivityMapper;

    @Autowired
    private UserDao userDao;

    @Value("${spring.activity.path}")
    private String activityPath;

    @Override
    public Integer addSpringActivity(WxSpringActivityAdd add) {

        WxSpringActivity wxSpringActivity = new WxSpringActivity();
        BeanUtils.copyProperties(add, wxSpringActivity);

        wxSpringActivity.setFilmUseNum(0);
        wxSpringActivity.setFilmRemainNum(wxSpringActivity.getFilmToalNum());
        wxSpringActivity.setRedPacketRemainNum(wxSpringActivity.getRedPacketNum());
        User creator = userDao.selectById(Long.valueOf(add.getCreator()));
        wxSpringActivity.setCreator(creator.getUserName());

        return wxSpringActivityMapper.insertSelective(wxSpringActivity);
    }

    @Override
    public List<WxSpringActivityBO> wheelList() {
        return wxSpringActivityMapper.queryWheelList();
    }

    @Override
    public WxSpringActivityBO getWheelInfo(String id) {
        return wxSpringActivityMapper.getWheelInfo(id);
    }

    @Override
    public PageInfo<WxSpringActivityBO> queryAllActivity(WxSpringActivityQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<WxSpringActivityBO> lists = wxSpringActivityMapper.queryAllActivity();
        lists.forEach(result ->
                result.setActivityPath(activityPath + result.getId()+"&wheelId="+result.getWxActLuckyWheelId())
        );
        return new PageInfo<>(lists);
    }

    @Override
    public List<WxSpringActivityBO> queryExport(Integer type,String activityId) {
        List<WxSpringActivityBO> results;
        switch (type) {
            case 1:
                results = wxSpringActivityMapper.queryExportCheck(activityId);
                break;
            case 2:
                results = wxSpringActivityMapper.queryExportDate(activityId);
                break;
            case 3:
                results = wxSpringActivityMapper.queryExportWheel(activityId);
                break;
            case 4:
                results = wxSpringActivityMapper.queryExportPicture(activityId);
                break;
            case 5:
                results = wxSpringActivityMapper.queryExportTask(activityId);
                break;
            default:
                results = null;
                break;
        }
        return results;
    }
}
