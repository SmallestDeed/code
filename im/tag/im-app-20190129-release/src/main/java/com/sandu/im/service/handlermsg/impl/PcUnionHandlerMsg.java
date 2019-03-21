package com.sandu.im.service.handlermsg.impl;

import com.sandu.im.common.bo.HistoryMessageBo;
import com.sandu.im.model.HistoryMessage;
import com.sandu.im.service.handlermsg.HistoryMessageHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pcUnion")
public class PcUnionHandlerMsg extends BaseHandlerMsg implements HistoryMessageHandler {

    @Override
    public void handlerSingleSpaceDesingPlanFromUser(List<HistoryMessageBo> resultList) {
        super.handlerSingleDesingPlanToUser(resultList, HistoryMessageBo.DIRECTION_SEND);
    }

    @Override
    public void handlerFullHouseDesingPlanFromUser(List<HistoryMessageBo> resultList) {
        super.handlerFullHousePlanToUser(resultList,HistoryMessageBo.DIRECTION_SEND);
    }

    /**
     * pc端没有发送户型的功能
     * @param resultList
     */
    @Override
    public void handlerBaseHouseDesingPlanFromUser(List<HistoryMessageBo> resultList) {
        //TODO 不支持户型发送
    }

    @Override
    public void handlerSingleSpaceDesingPlanToUser(List<HistoryMessageBo> resultList) {
        super.handlerSxwSendORPcUnionReceiveSinglerDesignPlanMsg(resultList,HistoryMessageBo.DIRECTION_RECEIVE);
    }

    @Override
    public void handlerFullHouseDesingPlanToUser(List<HistoryMessageBo> resultList) {
        super.handlerSxwSendORPcUnionReceiveFullHouseMsg(resultList,HistoryMessageBo.DIRECTION_RECEIVE);
    }

    @Override
    public void handlerBaseHouseDesingPlanToUser(List<HistoryMessageBo> resultList) {
        super.handlerSxwSendORPcUnionReceiveBaseHouseMsg(resultList,HistoryMessageBo.DIRECTION_RECEIVE);
    }
}
