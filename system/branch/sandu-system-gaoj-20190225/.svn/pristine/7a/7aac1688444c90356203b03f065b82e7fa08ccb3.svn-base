package com.sandu.api.act.service;

import com.sandu.api.act.input.WxActBargainAwardMsgQuery;
import com.sandu.api.act.input.WxActBargainAwardAdd;
import com.sandu.api.act.model.WxActBargainAwardMsg;
import com.sandu.api.act.output.WxActBargainAwardMsgVO;
import com.sandu.api.act.output.WxActBargainAwardMsgWebVO;
import org.springframework.stereotype.Component;

import java.util.List;
import com.sandu.api.user.model.SysUser;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * awardMsg
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:33
 */
@Component
public interface WxActBargainAwardMsgService {

    /**
     * 插入
     *
     * @param wxActBargainAwardMsg
     * @return
     */
    void createWxActBargainAwardMsg(WxActBargainAwardMsg wxActBargainAwardMsg);

    /**
     * 更新
     *
     * @param wxActBargainAwardMsg
     * @return
     */
    int modifyWxActBargainAwardMsg(WxActBargainAwardMsg wxActBargainAwardMsg);

    /**
     * 删除
     *
     * @param awardMsgId
     * @return
     */
    int removeWxActBargainAwardMsg(String  awardMsgId,String modifier);

    /**
     * 通过ID获取详情
     *
     * @param awardMsgId
     * @return
     */
    WxActBargainAwardMsg getWxActBargainAwardMsg(String awardMsgId);

    /**
    * 查询领取消息列表
    * @author : WangHaiLin
    * @date : 2018/11/22 10:07
    * @param query 查询入参
    * @return java.util.List<com.sandu.api.act.output.WxActBargainAwardMsgVO>
    *
    */
    List<WxActBargainAwardMsgVO> getListByActId(WxActBargainAwardMsgQuery query);

    /**
     * 查询领取消息数量
     * @author : WangHaiLin
     * @date : 2018/11/22 10:07
     * @param query 查询入参
     * @return int 领取消息数量
     */
    int getCountByActId(WxActBargainAwardMsgQuery query);

    /**
    * 随机获取领取消息实体
    * @author : WangHaiLin
    * @date  2018/11/22 14:08
    * @param actId  活动Id
    * @return java.util.List<com.sandu.api.act.output.WxActBargainAwardMsgWebVO>
    *
    */
    List<WxActBargainAwardMsgWebVO> getMsgRandomList(String actId);



}
