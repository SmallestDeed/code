package com.sandu.user.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:19 2018/5/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.base.service.BaseAreaService;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.common.util.collections.Lists;
import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.model.ResRenderPic;
import com.sandu.designplan.service.DesignPlanRecommendedProductService;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.designplan.service.DesignPlanRenderSceneService;
import com.sandu.designplan.vo.MydecorationPlanVo;
import com.sandu.fullhouse.service.FullHouseDesignPlanService;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.service.HouseSpaceNewService;
import com.sandu.node.constant.NodeInfoConstant;
import com.sandu.supplydemand.model.DemandInfoRel;
import com.sandu.supplydemand.model.SupplyDemandPic;
import com.sandu.supplydemand.service.SupplyDemandService;
import com.sandu.system.model.ResHousePic;
import com.sandu.system.service.NodeInfoBizService;
import com.sandu.system.service.ResHousePicService;
import com.sandu.system.service.ResRenderPicService;
import com.sandu.user.dao.UserReviewsMapper;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserReviews;
import com.sandu.user.model.input.UserDemandReviewsAdd;
import com.sandu.user.model.input.UserReviewsAdd;
import com.sandu.user.model.view.UserReviewsVo;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserFinanceService;
import com.sandu.user.service.UserReviewsService;
import com.sandu.user.websocket.SocketIOUtil;
import com.sandu.web.task.model.PushMessageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author weisheng
 * @Title: 用户评论
 * @Package
 * @Description:
 * @date 2018/5/4 0004PM 3:19
 */
@Service("userReviewsService")
@Slf4j
public class UserReviewsServiceImpl implements UserReviewsService {

    @Autowired
    private UserReviewsMapper userReviewsMapper;

    @Autowired
    private DesignPlanRecommendedProductService designPlanRecommendedProductService;

    @Autowired
    private SupplyDemandService supplyDemandService;

    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;

    @Autowired
    private FullHouseDesignPlanService fullHouseDesignPlanService;

    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ResHousePicService resHousePicService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private HouseSpaceNewService houseSpaceNewService;

    @Autowired
    private NodeInfoBizService nodeInfoBizService;
    @Autowired
    private ResRenderPicService resRenderPicService;

    @Override
    public int getAllUserReviewsCount(Integer supplyDemandId, Integer userId, Integer isRead) {
        return userReviewsMapper.selectAllUserReviewsCount(supplyDemandId,userId,isRead);
    }

    @Override
    public List<UserReviewsVo> getAllUserReviews(Integer supplyDemandId, Integer userId, int start, int limit, String order, Integer isRead) {
        List<UserReviewsVo> userReviewsVos = userReviewsMapper.selectAllUserReviews(supplyDemandId, userId, start, limit, order,isRead);

        //'方案类型,1:单空间推荐方案.2:全屋单推荐方案,3:单空间我的设计方案,4:全屋我的设计方案',
        if (null != userReviewsVos && userReviewsVos.size() > 0) {
            List<Integer> houseIds = new ArrayList<>();
            List<Integer> signleSpaceRecommendedPlanIds = new ArrayList<>();
            List<Integer> fullHousePlanIds = new ArrayList<>();
            List<Integer> signleSpaceScenePlanIds = new ArrayList<>();
            for (UserReviewsVo userReviewsVo : userReviewsVos) {
                if (null != userReviewsVo.getHouseId() && userReviewsVo.getHouseId() > 0) {
                    houseIds.add(userReviewsVo.getHouseId());
                }
                if (userReviewsVo.getPlanType() != null && userReviewsVo.getPlanType() > 0) {
                    switch (userReviewsVo.getPlanType()) {
                        case 1:
                            signleSpaceRecommendedPlanIds.add(userReviewsVo.getPlanId());
                            break;
                        case 2:
                        case 4:
                            fullHousePlanIds.add(userReviewsVo.getPlanId());
                            break;
                        case 3:
                            signleSpaceScenePlanIds.add(userReviewsVo.getPlanId());
                            break;
                        default:
                            log.error("错误的空间类型" + new Gson().toJson(userReviewsVo));
                            break;

                    }
                }

            }

            for (UserReviewsVo userReviewsVo : userReviewsVos) {
                //获取供求信息图片列表,取第一个为封面图,没有封面图就取方案封面图,否则取户型封面图
                String coverPicIds = userReviewsVo.getDemandCoverPicId();
                Integer planId = 0;
                Integer planType = 0;
                Integer houseId = 0;
                List<Integer> coverPicIdList = Utils.getIntegerListFromStringList(coverPicIds, ",");
                List<DemandInfoRel> demandInfoRels = supplyDemandService.selectDemandInfoRelBySupplyDemandId(userReviewsVo.getBusinessId());
                if (demandInfoRels != null && demandInfoRels.size() > 0) {
                    planId = demandInfoRels.get(0).getPlanId();
                    planType = demandInfoRels.get(0).getPlanType();
                    houseId = demandInfoRels.get(0).getHouseId();
                }
                if (coverPicIdList != null && coverPicIdList.size() > 0) {
                    List<SupplyDemandPic> supplyDemandPicList = supplyDemandService.selectSupplyDemandPic(coverPicIdList);
                    if (supplyDemandPicList != null && supplyDemandPicList.size() > 0) {
                        userReviewsVo.setCoverPicPath(supplyDemandPicList.get(0).getPicPath());
                    }
                } else if (planId != null && planId > 0 && planType > 0) {
                    //1:单空间推荐方案.2:全屋单推荐方案,3:单空间我的设计方案,4:全屋我的设计方案
                    switch (planType) {
                        case 1:
                            ResRenderPic singleSpaceCoverResRenderPic = resRenderPicService.getSingleSpaceCoverResRenderPic(planId);
                            if(null != singleSpaceCoverResRenderPic){
                                userReviewsVo.setCoverPicPath(singleSpaceCoverResRenderPic.getPicPath());
                            }
                            break;

                        case 2:
                        case 4:
                            ResRenderPic fullHouseCoverResRenderPic = resRenderPicService.getFullHouseCoverResRenderPic(planId);
                            if(null != fullHouseCoverResRenderPic){
                                userReviewsVo.setCoverPicPath(fullHouseCoverResRenderPic.getPicPath());
                            }
                            break;

                        case 3:
                            ResRenderPic myPlanCoverResRenderPic = resRenderPicService.getMyPlanCoverResRenderPic(planId);
                            if(null != myPlanCoverResRenderPic){
                                userReviewsVo.setCoverPicPath(myPlanCoverResRenderPic.getPicPath());
                            }
                            break;

                        default:
                            log.error("错误的方案类型:"+"planType:"+planType+"--------------"+"planId"+planId);
                            break;
                    }

                } else if ((planId == null || planType == 0) && houseId != null && houseId > 0) {
                    BaseHouse baseHouse = new BaseHouse();
                    if (null != houseId && houseId > 0) {
                        baseHouse = userFinanceService.queryUserUsedHouseDetailById(houseId);
                    }
                    if (null != baseHouse) {
                        // 取户型的缩略图和大图,优先户型绘制的图
                        if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
                            // 截图图片处理
                            ResHousePic resHousePic = resHousePicService.get(baseHouse.getSnapPicId());
                            if(null != resHousePic){
                                userReviewsVo.setCoverPicPath(resHousePic.getPicPath());
                            }

                        } else if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() > 0) {
                            ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
                            if(null != resHousePic){
                                userReviewsVo.setCoverPicPath(resHousePic.getPicPath());
                            }
                        }

                    }
                }
            }
            //处理单空间推荐方案详情
            if (signleSpaceRecommendedPlanIds != null && signleSpaceRecommendedPlanIds.size() > 0) {
                List<DesignPlanRecommendedResult> designPlanRecommendedResult = designPlanRecommendedProductService.getDesignPlanRecommendedInfoList(userId, signleSpaceRecommendedPlanIds);
                designPlanRecommendedResult = designPlanRecommendedService.setPlanResultInfo(userId, designPlanRecommendedResult);
                if (null != designPlanRecommendedResult && designPlanRecommendedResult.size() > 0) {
                    for (UserReviewsVo userReviewsVos1 : userReviewsVos) {
                        for (DesignPlanRecommendedResult designPlanRecommendedResult1 : designPlanRecommendedResult) {
                            if (null != userReviewsVos1.getPlanId() && designPlanRecommendedResult1.getPlanRecommendedId().intValue() == userReviewsVos1.getPlanId().intValue()) {
                                userReviewsVos1.setDesignPlanRecommendedResult(designPlanRecommendedResult1);
                            }
                        }

                    }
                }
            }

            //处理全屋方案详情
            if (fullHousePlanIds != null && fullHousePlanIds.size() > 0) {
                List<DesignPlanRecommendedResult> designPlanRecommendedResult = fullHouseDesignPlanService.selectFullHouseDesignPlanDetailList(fullHousePlanIds, userId);
                if (null != designPlanRecommendedResult && designPlanRecommendedResult.size() > 0) {
                    for (UserReviewsVo userReviewsVos1 : userReviewsVos) {
                        for (DesignPlanRecommendedResult designPlanRecommendedResult1 : designPlanRecommendedResult) {
                            if (null != userReviewsVos1.getPlanId() && designPlanRecommendedResult1.getPlanRecommendedId().intValue() == userReviewsVos1.getPlanId().intValue()) {
                                userReviewsVos1.setDesignPlanRecommendedResult(designPlanRecommendedResult1);
                            }
                        }

                    }
                }
            }

            //处理单空间我的设计方案详情
            if (signleSpaceScenePlanIds != null && signleSpaceScenePlanIds.size() > 0) {
                List<MydecorationPlanVo> mydecorationPlanVoList = designPlanRenderSceneService.selectDesignPlanInfoList(signleSpaceScenePlanIds);
                List<Integer> sceneIds = new ArrayList<>(mydecorationPlanVoList.size());
                //处理方案任务来源(1：单空间方案，2：全屋方案无户型 3:全屋方案有户型)
                for (MydecorationPlanVo mydecorationPlanVo : mydecorationPlanVoList) {
                    if (null != mydecorationPlanVo.getPlanHouseType() && (mydecorationPlanVo.getPlanHouseType().intValue() == 2 || mydecorationPlanVo.getPlanHouseType().intValue() == 3)) {
                        //0自动渲染 1替换渲染
                        if (null != mydecorationPlanVo.getHouseId() && mydecorationPlanVo.getHouseId() > 0) {
                            mydecorationPlanVo.setPlanRenderType(3);
                        } else {
                            mydecorationPlanVo.setPlanRenderType(2);
                        }
                    } else if (null != mydecorationPlanVo.getPlanHouseType() && mydecorationPlanVo.getPlanHouseType().intValue() == 1) {

                        mydecorationPlanVo.setPlanRenderType(1);
                    }
                    if (mydecorationPlanVo.getBusinessId() != null && mydecorationPlanVo.getBusinessId() != 0) {
                        sceneIds.add(mydecorationPlanVo.getBusinessId());
                    }

                    //0,未渲染 1渲染中 2渲染成功 3渲染失败
                    if ("1".equals(mydecorationPlanVo.getRenderTypesStr())) {
                        mydecorationPlanVo.setIsSuccess(mydecorationPlanVo.getRenderPic());
                    } else if ("2".equals(mydecorationPlanVo.getRenderTypesStr())) {
                        mydecorationPlanVo.setIsSuccess(mydecorationPlanVo.getRender720());
                    } else if ("4".equals(mydecorationPlanVo.getRenderTypesStr())) {
                        mydecorationPlanVo.setIsSuccess(mydecorationPlanVo.getRenderVideo());
                    } else if ("3".equals(mydecorationPlanVo.getRenderTypesStr())) {
                        mydecorationPlanVo.setIsSuccess(mydecorationPlanVo.getRenderN720());
                    }
                }
                //处理方案的封面资源图
                List<ResRenderPic> resRenderPicList = new ArrayList<>(mydecorationPlanVoList.size());
                if (null != sceneIds && sceneIds.size() > 0) {
                    resRenderPicList = fullHouseDesignPlanService.getResRenderCoverPic(sceneIds);
                }
                if (resRenderPicList != null && resRenderPicList.size() > 0) {
                    for (MydecorationPlanVo mydecorationPlanVo : mydecorationPlanVoList) {
                        for (ResRenderPic resRenderPic : resRenderPicList) {
                            if (null != mydecorationPlanVo.getBusinessId() && mydecorationPlanVo.getBusinessId().intValue() == resRenderPic.getDesignSceneId().intValue()) {
                                mydecorationPlanVo.setPlanPicPath(resRenderPic.getPicPath());
                            }
                        }
                    }
                }

                if (mydecorationPlanVoList != null && mydecorationPlanVoList.size() > 0) {
                    for (UserReviewsVo userReviewsVos1 : userReviewsVos) {
                        for (MydecorationPlanVo mydecorationPlanVo : mydecorationPlanVoList) {
                            if (null != userReviewsVos1.getPlanId() && mydecorationPlanVo.getBusinessId().intValue() == mydecorationPlanVo.getBusinessId().intValue()) {
                                userReviewsVos1.setMydecorationPlanVo(mydecorationPlanVo);
                            }
                        }

                    }
                }

            }

            //获取评论信息图片列表,取第一个为封面图
            List<String> picIdsList = userReviewsVos.stream().map(userReviewsVo -> userReviewsVo.getPicIds()).collect(Collectors.toList());
            picIdsList.removeAll(Collections.singleton(null));
            if (picIdsList != null && picIdsList.size() > 0) {
                for (String picIds : picIdsList) {
                    List<Integer> coverPicIdList = Utils.getIntegerListFromStringList(picIds, ",");
                    if (coverPicIdList != null && coverPicIdList.size() > 0) {
                        List<String> picPathList = userReviewsMapper.selectReviewsPicPath(coverPicIdList);
                        if (null != picPathList && picPathList.size() > 0) {
                            for (UserReviewsVo userReviewsVos1 : userReviewsVos) {
                                if (StringUtils.isNotBlank(picIds) && picIds.equals(userReviewsVos1.getPicIds())) {
                                    userReviewsVos1.setPicPathList(picPathList);
                                }
                            }
                        }
                    }


                }
            }


            //查询户型信息
            List<BaseHouse> houselist;
            if (null != houseIds && houseIds.size() > 0) {
                houselist = userFinanceService.queryUserUsedHouseDetailById(houseIds);
            } else {
                houselist = null;
            }

            if (houselist != null) {
                for (BaseHouse baseHouse : houselist) {
                    // 取户型的缩略图和大图,优先户型绘制的图
                    if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
                        // 截图图片处理 v3.9.4(draw-v1.0.2)
                        ResHousePic resHousePic = resHousePicService.get(baseHouse.getSnapPicId());
                        baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
                        baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
                    } else if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() > 0) {
                        ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
                        baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getThumbnailPath() : "");
                        baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getLargeThumbnailPath() : "");
                    } else {
                        baseHouse.setThumbnailPath("/default/huxing_bg_pic_no.png");
                        baseHouse.setLargeThumbnailPath("/default/huxing_bg_pic_no.png");
                    }

                    String longCodeName = "";
                    //查询小区的地址
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(baseHouse.getAreaLongCode())) {
                        String longCode = baseHouse.getAreaLongCode();
                        String str = longCode.substring(1, longCode.length() - 1);
                        String[] split = str.split("\\.");
                        List<String> list = new ArrayList<String>();
                        for (String code : split) {
                            if (!list.contains(code)) {
                                list.add(code);
                                String codeName = baseAreaService.getCodeName(code);
                                longCodeName += codeName;
                            }
                        }
                    }
                    baseHouse.setHouseAddress(longCodeName);


                    // 取户型的空间定位类型
                    List<String> spaceTypeList = houseSpaceNewService.getSpaceTypeListByHouseId(baseHouse.getId());
                    if (Lists.isEmpty(spaceTypeList)) {
                        continue;
                    }
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

                for (UserReviewsVo userReviewsVos1 : userReviewsVos) {
                    for (BaseHouse baseHouse : houselist) {
                        if (userReviewsVos1.getHouseId() != null && userReviewsVos1.getHouseId().intValue() == baseHouse.getId().intValue()) {
                            userReviewsVos1.setBaseHouse(baseHouse);
                        }
                    }

                }


            }


        }
        if (supplyDemandId != null) {
            // 用户评论点赞数
            userReviewsVos = nodeInfoBizService.getNodeData(userReviewsVos, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_USER_REVIEW, userId);
            // 根据点赞数再次排序
            if ("nd.value".equals(order)) {
                userReviewsVos = userReviewsVos.stream()
                        .sorted((a1, a2) -> (a2.getLikeNum() == null ? 0 : a2.getLikeNum()) - (a1.getLikeNum() == null ? 0 : a1.getLikeNum()))
                        .collect(Collectors.toList());
            }
        }
        return userReviewsVos;
    }

    @Override
    public Integer addSupplyDemandUserReviews(UserReviewsAdd userReviewsAdd) {
        //封装用户评论信息
        UserReviews userReviews = new UserReviews();
        userReviews.setUserId(userReviewsAdd.getUserId());
        userReviews.setReviewsMsg(userReviewsAdd.getReviewsMsg());
        userReviews.setCreator(userReviewsAdd.getCreator());
        userReviews.setBusinessId(userReviewsAdd.getBusinessId());
        Date date = new Date();
        userReviews.setGmtCreate(date);
        userReviews.setIsDeleted(0);
        userReviewsMapper.insertSelective(userReviews);
        return userReviews.getId();
    }

    @Override
    @Transactional
    public int addUserReviews(UserDemandReviewsAdd userDemandReviewsAdd, LoginUser loginUser) {
        UserReviews userReviews = this.buildUserReviews(userDemandReviewsAdd, loginUser);

        userReviewsMapper.insertSelective(userReviews);
        if (userReviews.getId().intValue() > 0) {
            //保存评论成功,保存评论总数
            Integer nodeId = nodeInfoBizService.registerNodeInfo(userDemandReviewsAdd.getBusinessId(), NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_SUPPLY_DEMAND);
            nodeInfoBizService.updateNodeInfoDetailValue(loginUser.getId(),
                    nodeId, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_SUPPLY_DEMAND,
                    NodeInfoConstant.SYSTEM_DICTIONARY_DETAIL_TYPE_COMMENT, new Byte("1"));
        }


        //调用websocket发送消息
        SysUser sysUser = sysUserService.get(userDemandReviewsAdd.getSupplyDemandPublisherId());
        PushMessageInfo pushMessageInfo = new PushMessageInfo();
        pushMessageInfo.setTargetSessionId(sysUser.getUuid());
        pushMessageInfo.setMsgCode(SocketIOUtil.IM_PUSH_REVIEWS_MSG_CODE);
        pushMessageInfo.setMsgContent(new Gson().toJson(userReviews));
        SocketIOUtil.sendEventMessage(SocketIOUtil.IM_PUSH_MSG_EVENT, pushMessageInfo);

        return userReviews.getId().intValue();
    }

    @Override
    public int updateReviewsIsRead(Integer reviewsId) {
        return userReviewsMapper.updateReviewsIsRead(reviewsId);
    }

    private UserReviews buildUserReviews(UserDemandReviewsAdd userDemandReviewsAdd, LoginUser loginUser) {
        Date date = new Date();
        UserReviews userReviews = new UserReviews();
        userReviews.setBusinessId(userDemandReviewsAdd.getBusinessId());
        userReviews.setReviewsMsg(userDemandReviewsAdd.getReviewsMsg());
        userReviews.setCreator(loginUser.getName());
        userReviews.setUserId(loginUser.getId());
        userReviews.setSupplyDemandPublisherId(userDemandReviewsAdd.getSupplyDemandPublisherId());
        userReviews.setIsDeleted(0);
        userReviews.setGmtCreate(date);
        userReviews.setFid(0);
        userReviews.setPicIds(userDemandReviewsAdd.getCoverPicIds());
        if (null != userDemandReviewsAdd.getHouseId() && userDemandReviewsAdd.getHouseId() > 0) {
            userReviews.setHouseId(userDemandReviewsAdd.getHouseId());
        }

        if (null != userDemandReviewsAdd.getPlanId() && userDemandReviewsAdd.getPlanId() > 0) {
            userReviews.setPlanId(userDemandReviewsAdd.getPlanId());
            userReviews.setPlanType(userDemandReviewsAdd.getPlanType());
        }


        return userReviews;
    }
}
