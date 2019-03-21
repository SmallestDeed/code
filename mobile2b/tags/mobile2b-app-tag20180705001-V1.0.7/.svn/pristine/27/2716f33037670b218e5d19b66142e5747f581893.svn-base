package com.nork.mobile.controller;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.dao.DesignTempletMapper;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.DesignTemplet;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.impl.DesignPlanAutoRenderServiceImpl;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.mobile.model.search.MobileRenderRecord;
import com.nork.mobile.service.MobilePayRenderService;
import com.nork.mobile.service.MobileResRenderPicService;
import com.nork.pay.metadata.ProductType;
import com.nork.pay.model.PayOrder;
import com.nork.pay.service.PayOrderService;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysUser;
import com.nork.system.model.search.ResRenderPicSearch;
import com.nork.system.service.SysUserService;
import com.sandu.common.LoginContext;

/**
 * 移动端获取渲染图的controller

 *
 * @author yangzhun

 */
@Controller
@RequestMapping("/{style}/mobile/renderPic")
public class MobileRenderPicController {


    private static Logger logger = Logger
            .getLogger(MobileRenderPicController.class);


    @Autowired
    private MobileResRenderPicService mobileResRenderPicService;
    @Autowired
    private SpaceCommonService spaceCommonService;
    @Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedService;

    @Autowired
    private DesignPlanAutoRenderServiceImpl designPlanAutoRenderServiceImpl;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MobilePayRenderService mobilePayRenderService;
    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;
    @Autowired
    private DesignTempletMapper designTempletMapper;

    @Autowired
    private PayOrderService payOrderService;


    /**
     * 通过sourcePlanId和templateId获取720全景图的sys_code的接口
     *
     * @param resRenderPic
     * @return
     * @throws UnknownHostException
     */
    @SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/getResRenderPicWeb")
    @ResponseBody
    public Object getResRenderPic(@RequestBody ResRenderPic resRenderPic, HttpServletRequest request) throws UnknownHostException {
        ResponseEnvelope message = new ResponseEnvelope();
        Integer planRecommendedId = resRenderPic.getPlanRecommendedId();
        Integer designPlanSceneId = resRenderPic.getDesignPlanSceneId();
        Integer operationSource = resRenderPic.getOperationSource();//操作来源，0，我的设计，1,为推荐方案
        Integer templateId = resRenderPic.getTemplateId();
        Integer renderingType = resRenderPic.getRenderingType();
        String renderTaskType = resRenderPic.getRenderTaskType();
        Double totalFee = resRenderPic.getTotalFee();
        String payType = resRenderPic.getPayType();
        String productType = resRenderPic.getProductType();

        Integer productId = 0;
        String productDesc = "移动端装进我家";
        String tradeType = "";
        Integer spaceCommonId = null;
        Integer userId = resRenderPic.getUserId();
        SysUser sysUser = sysUserService.get(userId);
        LoginUser loginUser = sysUser.toLoginUser();
        //判断用户余额是不是充足
        if (sysUser.getBalanceAmount().intValue() < totalFee.intValue()) {
            return new ResponseEnvelope<>(false, "您的余额不足，请到PC端充值！");
        }
        PayOrder payOrder = mobilePayRenderService.getOrder(totalFee.intValue(), payType, productId,
                productType, productDesc, tradeType, userId, -1);
        if (payOrder == null) {
            message.setSuccess(false);
            message.setMessage("生成订单失败");
            return message;
        }
        AutoRenderTask autoRenderTask = new AutoRenderTask();
        String templateCode = null;
        String designCode = null;
        String designName = null;
        InetAddress addr = InetAddress.getLocalHost();
        String hostIp = addr.getHostAddress().toString();
        if (payOrder != null) {
            //生成替换渲染任务start
            if (operationSource.intValue() == 0) {
                DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneService.get(designPlanSceneId);
                spaceCommonId = designPlanRenderScene.getSpaceCommonId();
                designCode = designPlanRenderScene.getPlanCode();
                designName = designPlanRenderScene.getPlanName();
                autoRenderTask.setPlanId(designPlanSceneId);
            } else if (operationSource.intValue() == 1) {
                DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(planRecommendedId);
                spaceCommonId = designPlanRecommended.getSpaceCommonId();
                designCode = designPlanRecommended.getPlanCode();
                designName = designPlanRecommended.getPlanName();
                autoRenderTask.setPlanId(planRecommendedId);
            }
            if (templateId.intValue() > 0) {
                DesignTemplet designTemplet = designTempletMapper.selectByPrimaryKey(templateId);
                templateCode = designTemplet.getDesignCode();
            }
            //在我的账户，消费记录中的消费任务增加 “设计方案”名称
            payOrder.setProductName(payOrder.getProductName() + " | " + designName);
            payOrderService.update(payOrder);

            SpaceCommon spaceCommon = spaceCommonService.get(spaceCommonId);
            String spaceCode = spaceCommon.getSpaceCode();
            logger.error("operationSource==" + operationSource);
            autoRenderTask.setOperationSource(operationSource);
            autoRenderTask.setTemplateId(templateId);
            autoRenderTask.setSpaceCode(spaceCode);
            autoRenderTask.setCreator(loginUser.getName());
            autoRenderTask.setModifier(loginUser.getName());
            autoRenderTask.setGmtCreate(new Date());
            autoRenderTask.setGmtModified(new Date());
            autoRenderTask.setTaskType(0);
            autoRenderTask.setOperationUserId(loginUser.getId());
            autoRenderTask.setOperationUserName(loginUser.getName());
            autoRenderTask.setOrderNumber(payOrder.getOrderNo());
            autoRenderTask.setDesignCode(designCode);
            autoRenderTask.setDesignName(designName);
            autoRenderTask.setTemplateCode(templateCode);
            autoRenderTask.setTaskSource(1);
            autoRenderTask.setHostIp(hostIp);
            if (renderTaskType.equals(ProductType.COMMON_RENDER)) {// 普通照片级
                autoRenderTask.setRenderTypesStr(ProductType.PHOTO);
                autoRenderTask.setRenderPic(0);
            } else if (renderTaskType.equals(ProductType.PANORAMA_RENDER)) {// 720全景
                autoRenderTask.setRenderTypesStr(ProductType.ROAM720);
                autoRenderTask.setRender720(0);
            } else if (renderTaskType.equals(ProductType.ROAM_VIDEO_RENDER)) {// 漫游视频
                autoRenderTask.setRenderTypesStr(ProductType.VIDEO);
                autoRenderTask.setRenderVideo(0);
            } else if (renderTaskType.equals(ProductType.ROAM_PANORAMA_RENDER)) {// 720多点
                autoRenderTask.setRenderTypesStr(ProductType.ROAMN720);
                autoRenderTask.setRenderN720(0);
            }
        }
        Boolean flag = false;
        Integer taskId = designPlanAutoRenderServiceImpl.add(autoRenderTask);
        autoRenderTask.setId(taskId);
        Integer result = designPlanAutoRenderServiceImpl.addRedisLists(autoRenderTask);
        payOrder.setTaskId(taskId);
        payOrderService.update(payOrder);
        logger.error("getResRenderPic=>create task ====>" + taskId + "payOrder=" + payOrder.getOrderNo());
        if (result > 0) {
            flag = true;
            return new ResponseEnvelope<>(flag, "创建渲染任务成功！");
        }
        flag = false;
        return new ResponseEnvelope<>(flag, "创建渲染任务失败！");
//		Map<String, String> map = mobileResRenderPicService.getResRenderPic(planRecommendedId, templateId,
//				renderingType);

    }


    /**
     * 从推荐页面 获取720图片
     *
     * @param resRenderPicSearch
     * @return
     */
    @RequestMapping(value = "/getPanoPicture")
    @ResponseBody
    public Object getPanoPicture(@RequestBody ResRenderPicSearch resRenderPicSearch) {
        // 获取推荐方案id
        Integer id = resRenderPicSearch.getPlanRecommendedId();


        if (id == null) {
            return new ResponseEnvelope<ResRenderPic>(false, "请求参数为空");
        }
        resRenderPicSearch.setPlanRecommendedId(id);
        String panoPath = mobileResRenderPicService.getRecommendedPicturePath(resRenderPicSearch);
        return new ResponseEnvelope<ResRenderPic>(panoPath);
    }


    /**
     * 从我的设计里获取一个设计方案的照片集
     */
    @RequestMapping(value = "/getPictureList")
    @ResponseBody
    public Object getPictureList(@RequestBody DesignPlan designPlan) {

        String remark = designPlan.getRemark();


        if (designPlan.getId() == null) {
            return new ResponseEnvelope<ResRenderPic>(false, "请求参数为空");
        }
        return mobileResRenderPicService.getPictureList(designPlan.getId(), remark);
    }


    // 移动端获取二维码
    @SuppressWarnings("unused")
    @RequestMapping("/getQRCodePicUrl")
    @ResponseBody
    public Object getQRCodePicUrl(@RequestBody MobileRenderRecord model, HttpServletRequest request) {
        Integer picId = model.getPicId();
        String renderingType = model.getRenderingType();
        String msgId = model.getMsgId();
        String remark = model.getRemark();

        // 获取登录用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        /* 参数验证 */
        if (picId == null || picId < 1) {
            return new ResponseEnvelope<>(false, "参数picId不能为空并且不能小于1", msgId);
        }
        if (loginUser == null) {
            return new ResponseEnvelope<>(false, "登录超时，请重新登录", msgId);
        }
		/* 参数验证->end */


        return mobileResRenderPicService.getQRCodePicUrl(picId, msgId, loginUser, remark);
    }



}
