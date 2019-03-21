package com.sandu.api.act.service;

import com.sandu.api.act.input.WxActBargainAwardAdd;
import com.sandu.api.act.model.WxActBargainAward;
import com.sandu.api.user.model.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * award
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:25
 */
@Component
public interface WxActBargainAwardService {

    /**
     * 插入
     *
     * @param wxactbargainaward
     * @return
     */
    void createWxActBargainAward(WxActBargainAward wxActBargainAward);

    /**
     * 更新
     *
     * @param wxactbargainaward
     * @return
     */
    int modifyWxActBargainAward(WxActBargainAward wxActBargainAward);

    /**
     * 删除
     *
     * @param wxactbargainawardIds
     * @return
     */
    int removeWxActBargainAward(String awardId);

    /**
     * 通过ID获取详情
     *
     * @param wxactbargainawardId
     * @return
     */
    WxActBargainAward getWxActBargainAward(String awardId);

    /**
     *
     * 领奖
     */
	void addAwardRecord(WxActBargainAwardAdd wxActBargainAwardAdd,SysUser user);

}
