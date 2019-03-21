package com.sandu.im.service.handlermsg;

import com.sandu.im.common.bo.HistoryMessageBo;

import java.util.List;

public interface HistoryMessageHandler {

    /**
     * 发送方 =>{}处理单空间方案消息
     */
     void handlerSingleSpaceDesingPlanFromUser(List<HistoryMessageBo> resultList);

    /**
     * 发送方 =>{}处理全屋方案消息
     */
    void handlerFullHouseDesingPlanFromUser(List<HistoryMessageBo> resultList);

    /**
     * 发送方 =>{}处理户型消息
     */
    void handlerBaseHouseDesingPlanFromUser(List<HistoryMessageBo> resultList);

    /**
     * 接收方 =>{}处理单空间方案消息
     */
    void handlerSingleSpaceDesingPlanToUser(List<HistoryMessageBo> resultList);

    /**
     * 接收方 =>{}处理全屋方案消息
     */
    void handlerFullHouseDesingPlanToUser(List<HistoryMessageBo> resultList);

    /**
     * 接收方 =>{}处理户型消息
     */
    void handlerBaseHouseDesingPlanToUser(List<HistoryMessageBo> resultList);
}
