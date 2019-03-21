package com.sandu.service.springFestivalActivity.impl;

import com.sandu.api.springFestivalActivity.model.WxSigninSummary;
import com.sandu.api.springFestivalActivity.output.LotteryWheelVo;
import com.sandu.api.springFestivalActivity.service.WxSigninSummaryService;
import com.sandu.api.springFestivalActivity.service.WxUserTaskService;
import com.sandu.api.user.model.SysUser;
import com.sandu.service.springFestivalActivity.dao.WxSigninSummaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("wxSigninSummaryService")
public class WxSigninSummaryServiceImpl implements WxSigninSummaryService {
    @Autowired
    private WxSigninSummaryMapper wxSigninSummaryMapper;
    @Autowired
    private WxUserTaskService wxUserTaskService;

    @Override
    public WxSigninSummary getBySelective(WxSigninSummary wxSigninSummary) {
        return wxSigninSummaryMapper.getBySelective(wxSigninSummary);
    }

    @Override
    public int insert(WxSigninSummary wxSigninSummary) {
        return wxSigninSummaryMapper.insert(wxSigninSummary);
    }

    @Override
    public int update(WxSigninSummary wxSigninSummary) {
        return wxSigninSummaryMapper.updateByPrimaryKeySelective(wxSigninSummary);
    }

    @Override
    public int decrOnceLotteryCount(Long activityId, Long userId) {
        return wxSigninSummaryMapper.decrOnceLotteryCount(activityId, userId);
    }


    @Override
    public LotteryWheelVo getLotteryWheelNeedData(Long activityId, SysUser sysUser) {
        LotteryWheelVo lotteryWheelVo = wxSigninSummaryMapper.getLotteryWheelNeedData(activityId, sysUser.getId());
        if (lotteryWheelVo == null) {
            lotteryWheelVo = new LotteryWheelVo();
        }
        if (null == lotteryWheelVo.getSignInCount()) {
            lotteryWheelVo.setSignInCount(0);
        }
        if (null == lotteryWheelVo.getLotteryRemainCount()) {
            lotteryWheelVo.setLotteryRemainCount(0);
        }
        Integer lotteryRate = wxUserTaskService.getRateByUserId(activityId, sysUser.getId());
        if (null == lotteryRate) {
            lotteryRate = 5;
        }
        lotteryWheelVo.setLotteryRate(lotteryRate);
        log.info("获取转盘所需数据返回值lotteryWheelVo={}", lotteryWheelVo);
        return lotteryWheelVo;
    }
}
