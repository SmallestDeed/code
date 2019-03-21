package com.sandu.web.springFestivalActivity;

import com.sandu.api.act3.output.LuckyWheelLotteryVO;
import com.sandu.api.act3.service.LuckyWheelRegistrationService;
import com.sandu.api.base.common.LoginUser;
import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.common.exception.BizException;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.springFestivalActivity.output.LotteryWheelVo;
import com.sandu.api.springFestivalActivity.service.WxSigninSummaryService;
import com.sandu.api.springFestivalActivity.service.WxUserSigninService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: LotteryWheelController
 * @Auther: gaoj
 * @Date: 2019/1/23 19:39
 * @Description:
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/v1/core/lotterywheel")
public class LotteryWheelController {

    private static final String CLASS_LOG_PREFIX = "【春节活动转盘活动】";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private LuckyWheelRegistrationService luckyWheelRegistrationService;
    @Autowired
    private WxUserSigninService wxUserSigninService;
    @Autowired
    private WxSigninSummaryService wxSigninSummaryService;

    @RequestMapping("/lottery")
    public ResponseEnvelope lottery(@RequestParam("activityId") Long activityId, @RequestParam("actLuckWheelId") String actLuckWheelId) {
        if (StringUtils.isBlank(actLuckWheelId)) {
            return new ResponseEnvelope(false, "转盘id不能为空");
        }
        if (null == activityId || 0 >= activityId) {
            return new ResponseEnvelope(false, "活动id不能为空");
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        SysUser sysUser = sysUserService.get(loginUser.getId());
        if (null == sysUser) {
            return new ResponseEnvelope(false, "请登录");
        }

        LuckyWheelLotteryVO vo;
        try {
            vo = luckyWheelRegistrationService.lottery(actLuckWheelId, sysUser);
        } catch (SystemException ex) {
            log.warn(CLASS_LOG_PREFIX + "业务异常:" + ex.getMessage());
            return new ResponseEnvelope(false, ex.getMessage());
        } catch (Exception ex) {
            log.error(CLASS_LOG_PREFIX + "系统异常:", ex);
            return new ResponseEnvelope(false, "系统异常!");
        }

        //修改签到汇总表的抽奖次数，修改签到详情表的抽奖次数
        try {
            wxUserSigninService.updateLotteryCount(activityId, sysUser);
        } catch (BizException e) {
            log.warn(CLASS_LOG_PREFIX + "业务异常:" + e.getMessage());
            return new ResponseEnvelope(false, e.getMessage());
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "系统异常:", e);
            return new ResponseEnvelope(false, "系统异常!");
        }

        return new ResponseEnvelope(true, vo);
    }

    @RequestMapping("/getLotteryWheelNeedData")
    public ResponseEnvelope getLotteryWheelNeedData(@RequestParam("activityId") Long activityId, SysUser sysUser) {
        if (null == activityId || 0 >= activityId) {
            return new ResponseEnvelope(false, "活动参数不能为空");
        }

        LotteryWheelVo lotteryWheelNeedData;
        try {
            lotteryWheelNeedData = wxSigninSummaryService.getLotteryWheelNeedData(activityId, sysUser);
        } catch (Exception e) {
            log.info(CLASS_LOG_PREFIX + "获取转盘数据异常，exception={}", e);
            return new ResponseEnvelope(false, "系统异常!");
        }
        return new ResponseEnvelope(true, lotteryWheelNeedData);
    }
}
