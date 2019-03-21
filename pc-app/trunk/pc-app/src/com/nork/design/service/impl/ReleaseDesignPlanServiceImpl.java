package com.nork.design.service.impl;

import com.nork.common.async.SyncRecommendedPlanTask;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.Utils;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.dao.ReleaseDesignPlanMapper;
import com.nork.design.model.*;
import com.nork.design.service.*;
import com.nork.home.model.SpaceCommon;
import com.nork.home.model.SpaceCommonStatus;
import com.nork.home.service.SpaceCommonService;
import com.nork.product.model.BaseCompany;
import com.nork.product.model.constant.BaseCompanyBusinessTypeConstant;
import com.nork.product.service.BaseCompanyService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysUser;
import com.nork.system.model.search.ResRenderPicSearch;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.SysUserService;
import com.nork.threadpool.ThreadPool;
import com.nork.threadpool.ThreadPoolManager;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/6/7
 * @since : sandu_yun_1.0
 */
@Service("releaseDesignPlanService")
public class ReleaseDesignPlanServiceImpl implements ReleaseDesignPlanService {
    private static Logger logger = LoggerFactory.getLogger(ReleaseDesignPlanServiceImpl.class);

    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;

    @Autowired
    private ReleaseDesignPlanMapper releaseDesignPlanMapper;

    @Autowired
    SpaceCommonService spaceCommonService;

    @Autowired
    DesignTempletService designTempletService;

    @Autowired
    ResRenderPicService resRenderPicService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ThreadPoolManager threadPoolManager;

    @Autowired
    private SyncRecommendedPlanService syncRecommendedPlanService;
    
    @Override
    public ResponseEnvelope<DesignPlanRecommendedResult> releaseDesignPlan(ReleaseDesignPlanModel designPlanModel, LoginUser loginUser, String msgId) {

        //推荐方案类型
        List<Integer> recommendedTypeArray = designPlanModel.getRecommendedTypeArray();
        DesignPlanRenderScene designPlanRenderScene = designPlanModel.getDesignPlanRenderScene();

        //TODO Check this logic , Because the check interface is called by planFormalIsReleaseCheck method, this is duplicated call.
//        DesignPlanRecommendedCheck check = validateCommonInfo(designPlanRenderScene, recommendedTypeArray);
//        if (!check.isState()) {
//            return new ResponseEnvelope<DesignPlanRecommendedResult>(false, check.getErrMsg(), msgId);
//        }
        //获得用户关联公司Id
        if(loginUser.getBusinessAdministrationId() == null) {
            SysUser sysUser = sysUserService.get(loginUser.getId());
            if(sysUser != null && sysUser.getBusinessAdministrationId() != null) {
                loginUser.setBusinessAdministrationId(Long.valueOf(sysUser.getBusinessAdministrationId() + ""));
            }
        }
        if(loginUser.getBusinessAdministrationId() != null) {
            BaseCompany company = baseCompanyService.get(loginUser.getBusinessAdministrationId().intValue());
            if(company != null && company.getBusinessType() != null && company.getBusinessType() == BaseCompanyBusinessTypeConstant.BUSINESS_TYPE_FRANCHISER) {
                loginUser.setBusinessAdministrationId(Long.parseLong(company.getPid() + ""));
            }
        }


        //校验方案是否提交测试过
        DesignPlanRecommended designPlanRecommendedSearch = new DesignPlanRecommended();
        designPlanRecommendedSearch.setPlanId(designPlanRenderScene.getId());
        designPlanRecommendedSearch.setRecommendedTypes(recommendedTypeArray);
        designPlanRecommendedSearch.setIsDeleted(0);
        //增加公司Id过滤效果图方案关联的推荐方案 begin
        if(loginUser.getBusinessAdministrationId() != null) {
            designPlanRecommendedSearch.setSearch_CompanyId(loginUser.getBusinessAdministrationId().intValue());
        }
        //增加公司Id过滤效果图方案关联的推荐方案 end
        DesignPlanRecommendedCheck check = validatePlanIsReleased(designPlanRecommendedSearch);
        if (!check.isState()) {
             String message = check.getErrMsg();
             return new ResponseEnvelope<DesignPlanRecommendedResult>(false,message);
        }
        List<String> brandIdList = null;
        if(StringUtils.isNotEmpty(designPlanModel.getBrandIds())) {
            String[] arr = designPlanModel.getBrandIds().split(",");
            brandIdList = Arrays.asList(arr);
        }

		Map<String, ResponseEnvelope<DesignPlanRecommendedResult>> resultMap = new HashMap<String, ResponseEnvelope<DesignPlanRecommendedResult>>();
        for (Integer recommendType : recommendedTypeArray) {
            if (DesignPlanConstants.RECOMMENDED_TYPE_SHARE == recommendType) {
            	ResponseEnvelope<DesignPlanRecommendedResult> result = designPlanRecommendedServiceV2.shareRecommend(designPlanModel,designPlanRenderScene,brandIdList,msgId,loginUser);

            	if(result.isSuccess()){
                    Integer recommended = (Integer)result.getObj();
                    if(null != recommended && null!= designPlanRenderScene.getId())
                        //把推荐方案id回填到装修报价表
                        designPlanRecommendedServiceV2.updatePlanDecoratePrice(recommended,designPlanRenderScene.getId());
                }

            	resultMap.put("普通方案", result);
            }
            if (DesignPlanConstants.RECOMMENDED_TYPE_DECORATE == recommendType) {

                Integer groupId = null;
                ResponseEnvelope<DesignPlanRecommendedResult> result = designPlanRecommendedServiceV2.decorateTestRecommend(designPlanModel,designPlanRenderScene,brandIdList,msgId,loginUser);
                if(result.isSuccess()){
                    groupId = (Integer)result.getObj();

                    if(null != groupId && null!= designPlanRenderScene.getId())
                        //把推荐方案id回填到装修报价表
                        designPlanRecommendedServiceV2.updatePlanDecoratePrice(groupId,designPlanRenderScene.getId());

                    //判断该方案是否为组合方案
                    if(null != designPlanRenderScene.getGroupPrimaryId() && designPlanRenderScene.getGroupPrimaryId() > 0){
                        //加入id集合，维护组合之间的关系
                        List<Integer> idList = new ArrayList<>();
                        idList.add(groupId);

                        //查询效果图组合列表
                        List<DesignPlanRenderScene> renderSceneList = designPlanRenderSceneService.getGroupList(designPlanRenderScene.getGroupPrimaryId(),0);

                        //copy子方案
                        for (DesignPlanRenderScene renderScene : renderSceneList) {
                            //判断是否与主方案相等，相等copy下一个
                            if(designPlanRenderScene.getId().equals(renderScene.getId()))
                                continue;

                            ResponseEnvelope<DesignPlanRecommendedResult> resultSon = designPlanRecommendedServiceV2.decorateTestRecommend(designPlanModel,renderScene,brandIdList,msgId,loginUser);

                            if(resultSon.isSuccess()) {

                                Integer sonId = (Integer) resultSon.getObj();
                                idList.add(sonId);
                                if (null != sonId && null != renderScene.getId())
                                    //把推荐方案id回填到装修报价表
                                    designPlanRecommendedServiceV2.updatePlanDecoratePrice(sonId, renderScene.getId());

                            }else {
                                logger.error("copy子方案失败renderScene=" + renderScene.getId() + ",resultSon=" + resultSon.toString());
                            }

                        }

                        //维护主方案与子方案之间的关系
                        int i = designPlanRecommendedServiceV2.updateGroupDesign(groupId,idList);
                        if (i > 0) {
                            //同步到ES搜索服务 add by xiaoxc_20180823
                            String planIds = Utils.getStringFromIntegerList(idList);
                            if (!StringUtils.isEmpty(planIds)) {
                                ThreadPool threadPool = threadPoolManager.getThreadPool();
                                SyncRecommendedPlanTask syncPlanTask = new SyncRecommendedPlanTask(planIds, DesignPlanConstants.UPDATE_ACTION, syncRecommendedPlanService);
                                threadPool.submit(syncPlanTask);
                            }
                        }

                    }
                }

                resultMap.put("智能方案", result);
            }
        }
        ResponseEnvelope<DesignPlanRecommendedResult> responseEnvelope = this.dealWithResultList(resultMap);
		responseEnvelope.setMsgId(msgId);
		return responseEnvelope;
    }

    /**
	 * 处理测试发布返回结果(拼接返回信息)
	 * 
	 * @author huangsongbo
	 * @param resultList
	 * @return
	 */
	private ResponseEnvelope<DesignPlanRecommendedResult> dealWithResultList(Map<String, ResponseEnvelope<DesignPlanRecommendedResult>> resultList) {
		boolean success = true;
		if(resultList == null) {
			return new ResponseEnvelope<>(false, "系统异常", null);
		}
		StringBuffer msg = new StringBuffer("");
		for(Entry<String, ResponseEnvelope<DesignPlanRecommendedResult>> entry : resultList.entrySet()) {
			ResponseEnvelope<DesignPlanRecommendedResult> responseEnvelope = entry.getValue();
			if(!responseEnvelope.isSuccess()) {
				success = responseEnvelope.isSuccess();
			}
			String message = responseEnvelope.getMessage();
			if(StringUtils.isNotEmpty(message) && message.endsWith(";")) {
				message = message.substring(0, message.length() - 1);
			}
			msg.append(entry.getKey() + ":" + message);
		}
		return new ResponseEnvelope<>(success, msg.toString(), null);
	}
    
    @Override
    public DesignPlanRecommendedCheck validateCommonInfo(DesignPlanRenderScene designPlanRenderScene, List <Integer> recommendedTypeArray) {

          //提交测试时取消校验样板房，空间，产品状态
//        DesignTemplet designTemplet = designTempletService.get(designPlanRenderScene.getDesignTemplateId());
//        SpaceCommon spaceCommon = spaceCommonService.get(designTemplet.getSpaceCommonId());
//        check = validateDesignTemplateAndSpaceCommnInfo(designTemplet, spaceCommon, DesignPlanConstants.FUNCTION_TYPE_TEST_FRLEASE);
//        if (!check.isState()) {
//            return check;
//        }
//        check = validateDesignProductIsReleased(designPlanRenderScene.getId());
//        if (!check.isState()) {
//            return check;
//        }
        DesignPlanRecommendedCheck check = new DesignPlanRecommendedCheck();
        check = validateRenderRes(designPlanRenderScene);
        if (!check.isState()) {
            return check;
        }
        return check;
    }


    //check this plan already is released
    private DesignPlanRecommendedCheck validatePlanIsReleased(DesignPlanRecommended designPlanRecommendedSearch) {
        releaseDesignPlanMapper.markIsDeletedForCheckFailurePlan(designPlanRecommendedSearch.getPlanId());
        DesignPlanRecommendedCheck check = new DesignPlanRecommendedCheck();
        check.setState(true);
        //get all the plan that the is_release value is (1,2,3)
        List <DesignPlanRecommended> planList = releaseDesignPlanMapper.getIsReleasedPlan(designPlanRecommendedSearch);
        for (DesignPlanRecommended planRecommended : planList
                ) {
            for (Integer recommendType : designPlanRecommendedSearch.getRecommendedTypes()
                    ) {
                if (planRecommended.getRecommendedType().intValue() == recommendType.intValue()) {
                    check.setState(false);
                    check.setErrMsg("该类型推荐方案只允许发布一次！再次发布，请取消历史发布！");
                }

            }
        }
        //TODO Check this logic, whether only update this recommend type?
//        if(recommendedList.get(0).getIsRelease().intValue() == RecommendedDecorateState.FAILURE_CHECK_RELEASE){
//            DesignPlanRecommended failureCheck = new DesignPlanRecommended();//如果上条数据是审核失败的先删除 在进行发布
//            failureCheck.setId(recommendedList.get(0).getId());
//            failureCheck.setIsDeleted(1);
//            this.update(failureCheck);
//        }
        return check;
    }

    private DesignPlanRecommendedCheck validateDesignTemplateAndSpaceCommnInfo(DesignTemplet designTemplet, SpaceCommon spaceCommon, String functionType) {
        DesignPlanRecommendedCheck check = new DesignPlanRecommendedCheck();
        check.setState(true);
        StringBuilder errMsg = null;
        if(DesignPlanConstants.FUNCTION_TYPE_FRLEASE.equals(functionType) || DesignPlanConstants.FUNCTION_TYPE_TRANSFORM.equals(functionType)){
            //1.样板房  空间 是否为发布中，否则不让发布
            if(designTemplet == null || designTemplet.getPutawayState() != DesignTempletPutawayState.IS_RELEASE.intValue()){
                errMsg = new StringBuilder("方案样板房");
                check.setState(false);
            }
            if(spaceCommon == null || spaceCommon.getStatus().intValue() != SpaceCommonStatus.IS_RELEASE.intValue() ){
                if(errMsg != null) {
                    errMsg.append("与空间");
                }else {
                    errMsg = new StringBuilder("方案样板房空间");
                }

                check.setState(false);
            }
        }

        if (DesignPlanConstants.FUNCTION_TYPE_TEST_FRLEASE.equals(functionType)) {
            if (designTemplet == null
                    || designTemplet.getPutawayState() == DesignTempletPutawayState.NO_UP.intValue()
                    || designTemplet.getPutawayState() == DesignTempletPutawayState.IS_DOWN.intValue()) {
                errMsg = new StringBuilder("方案样板房");
                check.setState(false);
            }
            if (spaceCommon == null
                    || spaceCommon.getStatus().intValue() == SpaceCommonStatus.NO_UP.intValue()
                    || spaceCommon.getStatus().intValue() == SpaceCommonStatus.IS_DOWN.intValue()
                    || spaceCommon.getStatus().intValue() == SpaceCommonStatus.IS_DISABLE.intValue()) {
                if(errMsg != null) {
                    errMsg.append("与空间");
                }else {
                    errMsg = new StringBuilder("方案样板房空间");
                }
                check.setState(false);
            }
        }
        if (!check.isState()) {
            errMsg.append("未发布！");
            check.setErrMsg(errMsg.toString());
        }
        return check;

    }

    private DesignPlanRecommendedCheck validateDesignProductIsReleased(Integer planId) {
        DesignPlanRecommendedCheck check = new DesignPlanRecommendedCheck();
        check.setState(true);
        StringBuilder errMsg = null;
        List <String> productCodeList = releaseDesignPlanMapper.getDesignPlanProductList(planId);
        if (productCodeList.size() > 0) {
            errMsg = new StringBuilder("如下产品 [");
            check.setState(false);
            for (String productCode : productCodeList
                    ) {
                errMsg.append(productCode);
                errMsg.append(",");
            }
        }
        if (!check.isState()) {
            errMsg.append("] 没有发布！");
            check.setErrMsg(errMsg.toString());
        }
        return check;
    }

    private DesignPlanRecommendedCheck validateRenderRes(DesignPlanRenderScene designPlanRenderScene) {
        DesignPlanRecommendedCheck check = new DesignPlanRecommendedCheck();
        check.setState(true);
        StringBuilder errMsg = new StringBuilder("此方案未进行:");
        boolean commonPictureLevel = false;  //照片级普通
        boolean hdPictureLevel = false;  //照片级高清
        boolean ultraHdPictureLevel = false;  //照片级超高清
        boolean common720level = false;  //720度普通
        boolean hd720level = false;  //720度高清
        boolean multipoint = false;  //多点
        boolean video = false; //视频

        List <String> fileKeyList = new ArrayList <String>();
        fileKeyList.add(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY.replace(".upload.path", ""));
        fileKeyList.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER.replace(".upload.path", ""));
        ResRenderPic resRenderPic = new ResRenderPic();
        resRenderPic.setFileKeyList(fileKeyList);
        resRenderPic.setIsDeleted(0);
        resRenderPic.setDesignSceneId(designPlanRenderScene.getId());
        List <ResRenderPic> resRenderPiList = resRenderPicService.getList(resRenderPic);
        for (ResRenderPic resRenderPic_ : resRenderPiList) {
            if (ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER.replace(".upload.path", "").equals(resRenderPic_.getFileKey())) {
                video = true;
            } else {
                if (RenderTypeCode.COMMON_PICTURE_LEVEL == resRenderPic_.getRenderingType()) {
                    commonPictureLevel = true;
                } else if (RenderTypeCode.HD_PICTURE_LEVEL == resRenderPic_.getRenderingType()) {
                    hdPictureLevel = true;
                } else if (RenderTypeCode.ULTRA_HD_PICTURE_LEVEL == resRenderPic_.getRenderingType()) {
                    ultraHdPictureLevel = true;
                } else if (RenderTypeCode.COMMON_720_LEVEL == resRenderPic_.getRenderingType()) {
                    common720level = true;
                } else if (RenderTypeCode.HD_720_LEVEL == resRenderPic_.getRenderingType()) {
                    hd720level = true;
                }
            }
            // 因为 多点渲染图 原图 没有和 效果图建立关系。这是紧急措施，修复好 效果图建立关系 后，删掉这些
            if (!multipoint && resRenderPic_.getSysTaskPicId() != null && resRenderPic_.getSysTaskPicId().intValue() > 0
                    && !ResProperties.DESIGNPLAN_RENDER_ROAM_PIC_FILEKEY.replace(".upload.path", "").equals(resRenderPic_.getFileKey())) {
                ResRenderPicSearch roam = new ResRenderPicSearch();
                roam.setIsDeleted(0);
                roam.setSysTaskPicId(resRenderPic_.getSysTaskPicId());
                roam.setFileKey(ResProperties.DESIGNPLAN_RENDER_ROAM_PIC_FILEKEY.replace(".upload.path", ""));
                int roamCount = resRenderPicService.getCount(roam);
                if (roamCount > 0) {
                    multipoint = true;
                }
            }
        }
        if (!commonPictureLevel && !hdPictureLevel) {
            check.setState(false);
            errMsg.append("照片级");
        }
        if (!ultraHdPictureLevel && !common720level && !hd720level) {
            if(check.isState()){
                errMsg.append("720全景");
            }else{
                errMsg.append(",720全景");
            }
            check.setState(false);

        }
        if (!multipoint) {
            if(check.isState()){
                errMsg.append("多点全景图");
            }else{
                errMsg.append(",多点全景图");
            }
            check.setState(false);
        }
        if (!video) {
            if(check.isState()){
                errMsg.append("漫游视频");
            }else{
                errMsg.append(",漫游视频");
            }
            check.setState(false);
        }
        errMsg.append("渲染!");
        if(!check.isState()) {
            check.setErrMsg(errMsg.toString());
        }
        return check;
    }


}
