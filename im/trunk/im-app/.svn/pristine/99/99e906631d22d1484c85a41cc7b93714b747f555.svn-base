package com.sandu.im.service.handlermsg.impl;

import com.sandu.im.common.bo.HistoryMessageBo;
import com.sandu.im.model.*;
import com.sandu.im.service.BaseHouseService;
import com.sandu.im.service.DesignPlanService;
import com.sandu.im.util.ListUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseHandlerMsg {

    @Autowired
    private DesignPlanService designPlanService;

    @Autowired
    BaseHouseService baseHouseService;

    public void handlerSxwSendORPcUnionReceiveSinglerDesignPlanMsg(List<HistoryMessageBo> resultList,Integer sendType){
        List<HistoryMessageBo> senderMsg = filterCurrentPlatformUserSendMsgs(resultList,sendType);
        if (Objects.nonNull(senderMsg) && !senderMsg.isEmpty()) {
            //提取方案id
            Set<Integer> singlePlanIds = senderMsg.stream().filter(type -> Objects.equals(2, type.getMsgType())).map(it -> Integer.parseInt(it.getMsgBody())).collect(Collectors.toSet());
            if (Objects.nonNull(singlePlanIds) && !singlePlanIds.isEmpty()) {
                //查询方案信息
                List<DesignPlanInfo> infos = designPlanService.selectSingleDesinPlanInfo(singlePlanIds);
                if (Objects.nonNull(infos) && !infos.isEmpty()) {
                    //处理渲染转态
                    handlerRenderState(infos);
                    //获取资源文件
                    getRenderResource(infos);
                    //获取用户的户型
                    getDesignPlanArea(infos);
                    //封装返回消息
                    buildReturnDesignPlanInfo(infos, resultList,sendType);
                }
            }
        }
    }

    /**
     * 处理随选网发送全屋方案消息或者同城联盟接收全屋方案消息
     * @param resultList
     * @param type
     */
    public void handlerSxwSendORPcUnionReceiveFullHouseMsg(List<HistoryMessageBo> resultList,Integer type) {
        List<HistoryMessageBo> sendMsgs = filterCurrentPlatformUserSendMsgs(resultList,type);
        if (ListUtil.isNotEmpty(sendMsgs)){
            //抽取全屋户型的消息
            List<HistoryMessageBo> fullHouses = sendMsgs.stream().filter(it -> Objects.equals(3, it.getMsgType())).collect(Collectors.toList());
            if (ListUtil.isNotEmpty(fullHouses)){
                Set<Integer> fullHouseIds = fullHouses.stream().map(house -> Integer.parseInt(house.getMsgBody())).collect(Collectors.toSet());
                List<DesignPlanRecommendedResult> recommendedResults =  designPlanService.selectselectFullHouseDesignPlanDetail(fullHouseIds);
                if (ListUtil.isNotEmpty(recommendedResults)){
                    Map<Integer, List<DesignPlanRecommendedResult>> fullHouseMap = recommendedResults.stream().collect(Collectors.groupingBy(DesignPlanRecommendedResult::getId));
                    resultList.stream().forEach(result ->{
                        if (Objects.equals(result.getMsgType(),3) && Objects.equals(type,result.getDirection())){
                            List<DesignPlanRecommendedResult> house = fullHouseMap.get(Integer.parseInt(result.getMsgBody()));
                            if (Objects.nonNull(house)){
                                result.setObj(house.get(0));
                            }
                        }
                    });
                }
            }
        }
    }


    /**
     * 处理随选网发送户型消息或者同城联盟接收户型消息
     * @param resultList
     * @param type
     */
    public void handlerSxwSendORPcUnionReceiveBaseHouseMsg(List<HistoryMessageBo> resultList,Integer type) {
        List<HistoryMessageBo> sendMsgs = filterCurrentPlatformUserSendMsgs(resultList,type);
        if (ListUtil.isNotEmpty(sendMsgs)) {
            List<HistoryMessageBo> baseHouses = sendMsgs.stream().filter(it -> Objects.equals(4, it.getMsgType())).collect(Collectors.toList());
            if (ListUtil.isNotEmpty(baseHouses)) {
                //提取户型信息
                Set<Integer> houseIds = baseHouses.stream().map(house -> Integer.parseInt(house.getMsgBody())).collect(Collectors.toSet());
                List<BaseHouse> baseHouseInfos = baseHouseService.queryBaseHouseInfo(houseIds);
                if (ListUtil.isNotEmpty(baseHouseInfos)) {
                    baseHouseInfos.stream().forEach(baseHouse -> {
                        //获取户型图片
                        getHousePic(baseHouse);
                        //获取户型所在小区名称
                        baseHouse.setHouseAddress(getLivingAddress(baseHouse.getAreaLongCode()));
                        //获取户型的定位空间类型
                        getSpaceCommonType(baseHouse);
                    });
                    //设置户型返回参数
                    buildReturnHouseInfo(resultList,baseHouseInfos);
                }
            }
        }
    }

    /**
     * 处理pc端发送单空间方案,随选网接口单空间方案消息
     * @param resultList
     * @param type
     */
    public void handlerSingleDesingPlanToUser(List<HistoryMessageBo> resultList,Integer type){
        List<HistoryMessageBo> receiveMsgs = filterCurrentPlatToUserSendMsgs(resultList,type);
        if (ListUtil.isNotEmpty(receiveMsgs)){
            //提取单空间方案信息
            List<HistoryMessageBo> singleDesign = receiveMsgs.stream().filter(msg -> Objects.equals(2, msg.getMsgType())).collect(Collectors.toList());
            if (ListUtil.isNotEmpty(singleDesign))
            singleDesign.stream().forEach(single ->{
                ResRenderPic resRenderPic = designPlanService.selectResRenderCoverPicByDesignSceneId(Integer.parseInt(single.getMsgBody()));
                DesignPlanRenderScene renderScene = designPlanService.selectDesignPlanRenderSceneInfo(Integer.parseInt(single.getMsgBody()));
                //DesignPlanRecommendedResult designPlanRecommendedResult = new DesignPlanRecommendedResult();
                renderScene.setCoverPath(resRenderPic.getPicPath());
                single.setObj(renderScene);
            });
        }
    }

    /**
     * 处理pc端发送全屋方案,随选网接口全屋方案消息
     * @param resultList
     * @param type
     */
    public void handlerFullHousePlanToUser(List<HistoryMessageBo> resultList,Integer type) {
        List<HistoryMessageBo> receiveMsgs = filterCurrentPlatToUserSendMsgs(resultList,type);
        if (ListUtil.isNotEmpty(receiveMsgs)){
            //提取全屋方案信息
            List<HistoryMessageBo> fullHouseList = receiveMsgs.stream().filter(msg -> {
                return Objects.equals(3, msg.getMsgType());
            }).collect(Collectors.toList());
            if (ListUtil.isNotEmpty(fullHouseList))
            fullHouseList.stream().forEach(fullHouse ->{
                //DesignPlanRecommendedResult designPlanRecommendedResult = designPlanService.getFullHouseInfo(Integer.parseInt(fullHouse.getMsgBody()));
                //获取全屋方案的主任务详情
                FullHouseMainTaskInfo fullHouseMainTaskInfo = designPlanService.getMianTaskInfo(Integer.parseInt(fullHouse.getMsgBody()));
                if (Objects.nonNull(fullHouseMainTaskInfo)){
                    //获取全屋方案的效果图
                    String coverPic = designPlanService.getFullHouseCoverPicture(Integer.parseInt(fullHouse.getMsgBody()));
                    fullHouseMainTaskInfo.setPicPath(coverPic);
                }
                fullHouse.setObj(fullHouseMainTaskInfo);
            });
        }
    }

    /**
     * 设置户型返回参数
     * @param resultList
     * @param baseHouseInfos
     */
    private void buildReturnHouseInfo(List<HistoryMessageBo> resultList, List<BaseHouse> baseHouseInfos) {
        Map<Integer, List<BaseHouse>> houseMap = baseHouseInfos.stream().collect(Collectors.groupingBy(BaseHouse::getId));
        resultList.stream().forEach(result ->{
            if (Objects.equals(result.getMsgType(),4)){ //双向发送户型时需要加发送方校验
                List<BaseHouse> h = houseMap.get(Integer.parseInt(result.getMsgBody()));
                if (Objects.nonNull(h)){
                    result.setObj(h.get(0));
                }
            }
        });
    }


    /**
     * 获取户型下的空间类型
     * @param baseHouse
     */
    private void getSpaceCommonType(BaseHouse baseHouse) {
        List<String> spaceTypeList = baseHouseService.getSpaceTypeListByHouseId(baseHouse.getId());
        if (ListUtil.isNotEmpty(spaceTypeList)) {
            Map<String, Integer> elementsCount = new HashMap<String, Integer>();
            for (String s : spaceTypeList) {
                Integer i = elementsCount.get(s);
                if (i == null) {
                    elementsCount.put(s, 1);
                } else {
                    elementsCount.put(s, i + 1);
                }
            }
            baseHouse.setHouseTypeStr(((elementsCount.containsKey("3") ? elementsCount.get("3") : 0) + "厅"
                    + (elementsCount.containsKey("4") ? elementsCount.get("4") : 0)) + "室"
                    + (elementsCount.containsKey("6") ? elementsCount.get("6") : 0) + "卫"
                    + (elementsCount.containsKey("5") ? elementsCount.get("5") : 0) + "厨"
                    + (elementsCount.containsKey("7") ? elementsCount.get("7") : 0) + "书"
                    + (elementsCount.containsKey("8") ? elementsCount.get("8") : 0) + "衣"
                    + (elementsCount.containsKey("9") ? elementsCount.get("9") : 0) + "其他");

        }
    }



    /**
     * 获取户型所在的小区信息
     * @param areaLongCode
     * @return
     */
    private String getLivingAddress(String areaLongCode) {
        String longCodeName = "";
        //查询小区的地址
        if (StringUtils.isNotBlank(areaLongCode)) {
            String str = areaLongCode.substring(1, areaLongCode.length() - 1);
            String[] split = str.split("\\.");
            List<String> list = new ArrayList<String>();
            for (String code : split) {
                if (!list.contains(code)) {
                    list.add(code);
                    String codeName = baseHouseService.getLivingName(code);
                    longCodeName += codeName;
                }
            }
        }
        return longCodeName;
    }

    /**
     * 获取户型图
     * @param baseHouse
     */
    private void getHousePic(BaseHouse baseHouse) {
        // 取户型的缩略图和大图,优先户型绘制的图
        if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
            // 截图图片处理
            ResHousePic resHousePic = baseHouseService.getHousePicPath(baseHouse.getSnapPicId());
            if (resHousePic != null) {
                baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
                baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
            }

        } else if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() > 0) {
            ResHousePic resHousePic = baseHouseService.getHousePicPath(baseHouse.getPicRes1Id());
            if (resHousePic != null) {
                baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getThumbnailPath() : "");
                baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getLargeThumbnailPath() : "");
            }

        } else {
            baseHouse.setThumbnailPath("/default/huxing_bg_pic_no.png");
            baseHouse.setLargeThumbnailPath("/default/huxing_bg_pic_no.png");
        }
    }


    private void buildReturnDesignPlanInfo(List<DesignPlanInfo> infos, List<HistoryMessageBo> resultList,Integer sendType) {
        Map<Integer, List<DesignPlanInfo>> maps = infos.stream().collect(Collectors.groupingBy(DesignPlanInfo::getBusinessId));
        resultList.stream().forEach(result -> {
            if (Objects.equals(2,result.getMsgType()) && Objects.equals(sendType,result.getDirection())){
                List<DesignPlanInfo> designPlanInfos = maps.get(Integer.parseInt(result.getMsgBody()));
                if (Objects.nonNull(designPlanInfos)) {
                    result.setObj(designPlanInfos.get(0));
                }
            }
        });
    }


    private void getDesignPlanArea(List<DesignPlanInfo> infos) {
        infos.stream().forEach(fo -> {
            if (Objects.nonNull(fo.getTemplateId())) {
                String area = designPlanService.selectDesianPlanArea(fo.getTemplateId());
                fo.setSpaceArea(area);
            }
        });
    }

    private void getRenderResource(List<DesignPlanInfo> infos) {
        //提取效果图方案id
        Set<Integer> businessIds = infos.stream().map(info -> info.getBusinessId()).collect(Collectors.toSet());
        //批量获取效果图图片
        List<ResRenderPic> renderPics =  designPlanService.selectResRenderCoverPics(businessIds);
        Map<Integer, List<ResRenderPic>> renderPicMap = renderPics.stream().collect(Collectors.groupingBy(ResRenderPic::getDesignSceneId));
        infos.stream().forEach(fo -> {
            ResRenderPic renderPic = renderPicMap.get(fo.getBusinessId()).get(0);
            //ResRenderPic renderPic = designPlanService.selectResRenderCoverPicByDesignSceneId(fo.getBusinessId());
            if (Objects.nonNull(renderPic)){
                fo.setResRenderPicId(renderPic.getId());
                fo.setPlanPicPath(renderPic.getPicPath());
            }
        });

    }


    private void handlerRenderState(List<DesignPlanInfo> infos) {
        infos.stream().forEach(info -> {
            //处理渲染状态
            info.setPlanRenderType(1);
            if ("1".equals(info.getRenderTypesStr())) {
                info.setIsSuccess(info.getRenderPic());
            } else if ("2".equals(info.getRenderTypesStr())) {
                info.setIsSuccess(info.getRender720());
            } else if ("4".equals(info.getRenderTypesStr())) {
                info.setIsSuccess(info.getRenderVideo());
            } else if ("3".equals(info.getRenderTypesStr())) {
                info.setIsSuccess(info.getRenderN720());
            }
        });
    }

    /**
     * 提取当前发送方的消息
     * @param resultList
     * @return
     */
    private List<HistoryMessageBo> filterCurrentPlatformUserSendMsgs(List<HistoryMessageBo> resultList,Integer sendType) {
        //提取发送的消息
        return resultList.stream().filter(msg -> Objects.equals(msg.getDirection(), sendType)).collect(Collectors.toList());
    }

    /**
     * 提取接受方的消息
     * @param resultList
     * @return
     */
    private List<HistoryMessageBo> filterCurrentPlatToUserSendMsgs(List<HistoryMessageBo> resultList,Integer type) {
        //提取发送的消息
        return resultList.stream().filter(msg -> Objects.equals(msg.getDirection(),type)).collect(Collectors.toList());
    }

}
