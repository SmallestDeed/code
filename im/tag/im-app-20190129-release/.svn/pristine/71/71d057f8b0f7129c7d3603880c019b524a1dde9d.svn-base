package com.sandu.im.service.handlermsg.impl;

import com.sandu.im.common.bo.HistoryMessageBo;
import com.sandu.im.service.handlermsg.HistoryMessageHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("mobile")
public class Mobile2BHandlerMsg implements HistoryMessageHandler {

    public static final String DESIGN_PLAN_PROMPT_MSG = "您收到一个方案,请在pc端查看";

    public static final String BASE_HOUSE_PROMPT_MSG = "您收到一个户型,请在pc端查看";

    @Override
    public void handlerSingleSpaceDesingPlanFromUser(List<HistoryMessageBo> resultList) {
        buildSend2bUserMsg(resultList);
    }

    private List<HistoryMessageBo> filterDifferentMsgByType(List<HistoryMessageBo> resultList, Integer type, Integer direction) {

        List<HistoryMessageBo> reviceMsgs = resultList
                                                    .stream()
                                                    .filter(item -> { return Objects.equals(direction, item.getDirection()); })
                                                    .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(reviceMsgs)) {
            return reviceMsgs
                            .stream()
                            .filter(single -> Objects.equals(single.getMsgType(), type))
                            .collect(Collectors.toList());
        }
        return reviceMsgs;
    }

    @Override
    public void handlerFullHouseDesingPlanFromUser(List<HistoryMessageBo> resultList) {
        buildSend2bUserMsg(resultList);
    }

    private void buildSend2bUserMsg(List<HistoryMessageBo> resultList) {
        List<HistoryMessageBo> lists = filterDifferentMsgByType(resultList, 3, 1);
        if (!CollectionUtils.isEmpty(lists)) {
            lists
                    .stream()
                    .forEach(it -> {
                        it.setMsgBody("你发送了一个方案消息,请在pc点查看");
                    });
        }
    }


    @Override
    public void handlerBaseHouseDesingPlanFromUser(List<HistoryMessageBo> resultList) {

    }

    @Override
    public void handlerSingleSpaceDesingPlanToUser(List<HistoryMessageBo> resultList) {
        handlerMobile2BDesignPlanAndHouseMsg(resultList, 2, DESIGN_PLAN_PROMPT_MSG);
    }

    @Override
    public void handlerFullHouseDesingPlanToUser(List<HistoryMessageBo> resultList) {
        //过滤是否有发送单空间的消息
        handlerMobile2BDesignPlanAndHouseMsg(resultList, 3, DESIGN_PLAN_PROMPT_MSG);
    }

    @Override
    public void handlerBaseHouseDesingPlanToUser(List<HistoryMessageBo> resultList) {
        handlerMobile2BDesignPlanAndHouseMsg(resultList, 4, BASE_HOUSE_PROMPT_MSG);
    }

    private void handlerMobile2BDesignPlanAndHouseMsg(List<HistoryMessageBo> resultList, Integer msgType, String msgBody) {
        //过滤是否有发送单空间的消息
        List<HistoryMessageBo> msgTypes = this.filterDifferentMsgByType(resultList, msgType, 2);

        if (!CollectionUtils.isEmpty(msgTypes)) {
            msgTypes
                    .stream()
                    .forEach(it -> {
                        it.setMsgBody(msgBody);
                    });
//            Map<String, String> msgTypesMap = msgTypes.stream().collect(Collectors.toMap(HistoryMessageBo::getId, HistoryMessageBo::getMsgBody));
//            msgTypes.stream().forEach(msg ->{
//                if (StringUtils.isNotEmpty(msgTypesMap.get(msg.getId()))){
//                    msg.setMsgBody(msgBody);
//                }
//            });
        }
    }
}
