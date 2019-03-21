package com.sandu.im.service.handlermsg.impl;

import com.sandu.im.common.bo.HistoryMessageBo;

import com.sandu.im.model.DesignPlanRecommendedResult;
import com.sandu.im.model.ResRenderPic;
import com.sandu.im.service.DesignPlanService;
import com.sandu.im.service.handlermsg.HistoryMessageHandler;
import com.sandu.im.util.ListUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("sxw")
public class SxwHandlerMsg extends BaseHandlerMsg implements HistoryMessageHandler {

    @Autowired
    private DesignPlanService designPlanService;

    /**
     * 处理当前登录平台的发送者消息
     * @param resultList
     */
    @Override
    public void handlerSingleSpaceDesingPlanFromUser(List<HistoryMessageBo> resultList) {
        super.handlerSxwSendORPcUnionReceiveSinglerDesignPlanMsg(resultList,HistoryMessageBo.DIRECTION_SEND);
    }

    @Override
    public void handlerFullHouseDesingPlanFromUser(List<HistoryMessageBo> resultList) {
        super.handlerSxwSendORPcUnionReceiveFullHouseMsg(resultList,HistoryMessageBo.DIRECTION_SEND);
    }

    @Override
    public void handlerBaseHouseDesingPlanFromUser(List<HistoryMessageBo> resultList) {
        super.handlerSxwSendORPcUnionReceiveBaseHouseMsg(resultList,HistoryMessageBo.DIRECTION_SEND);
    }

    @Override
    public void handlerSingleSpaceDesingPlanToUser(List<HistoryMessageBo> resultList) {
        super.handlerSingleDesingPlanToUser(resultList, HistoryMessageBo.DIRECTION_RECEIVE);
//        List<HistoryMessageBo> receiveMsgs = resultList.stream().filter(item -> Objects.equals(item.getDirection(), 2)).collect(Collectors.toList());
//        if (ListUtil.isNotEmpty(receiveMsgs)){
//            //提取单空间方案信息
//            receiveMsgs.stream().forEach(single ->{
//                ResRenderPic resRenderPic = designPlanService.selectResRenderCoverPicByDesignSceneId(Integer.parseInt(single.getMsgBody()));
//                DesignPlanRecommendedResult designPlanRecommendedResult = designPlanService.selectDesignPlanRenderSceneInfo(Integer.parseInt(single.getMsgBody()));
//                //DesignPlanRecommendedResult designPlanRecommendedResult = new DesignPlanRecommendedResult();
//                designPlanRecommendedResult.setBusinessId(Integer.parseInt(single.getMsgBody()));
//                designPlanRecommendedResult.setCoverPath(resRenderPic.getPicPath());
//                single.setObj(designPlanRecommendedResult);
//            });
//        }
    }

    @Override
    public void handlerFullHouseDesingPlanToUser(List<HistoryMessageBo> resultList) {
        super.handlerFullHousePlanToUser(resultList,HistoryMessageBo.DIRECTION_RECEIVE);
//        List<HistoryMessageBo> receiveMsgs = resultList.stream().filter(item -> Objects.equals(item.getDirection(), 3)).collect(Collectors.toList());
//        if (ListUtil.isNotEmpty(receiveMsgs)){
//            //提取全屋方案信息
//            receiveMsgs.stream().forEach(fullHouse ->{
//                DesignPlanRecommendedResult designPlanRecommendedResult = designPlanService.getFullHouseInfo(Integer.parseInt(fullHouse.getMsgBody()));
//                fullHouse.setObj(designPlanRecommendedResult);
//            });
//        }
    }

    @Override
    public void handlerBaseHouseDesingPlanToUser(List<HistoryMessageBo> resultList) {
        //TODO 没有收到户型的逻辑
    }
}
