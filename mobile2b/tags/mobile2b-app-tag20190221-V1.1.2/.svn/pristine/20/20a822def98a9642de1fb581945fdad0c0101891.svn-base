package com.nork.mobile.controller;

import com.google.gson.Gson;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.constant.AutoRenderTaskConstant;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.*;
import com.nork.design.service.DesignPlanAutoRenderService;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.FullHouseDesignPlanService;
import com.nork.design.service.impl.DesignPlanAutoRenderServiceImpl;
import com.nork.home.model.SpaceCommonConstant;
import com.nork.mobile.model.ProductGroupReplaceTaskDetail;
import com.nork.mobile.model.ProductReplaceTaskDetail;
import com.nork.mobile.model.input.PlanNameUpdate;
import com.nork.mobile.model.search.MobileRenderingModel;
import com.nork.mobile.model.search.MobileRenderingSearch;
import com.nork.mobile.service.MobileDesignPlanProductService;
import com.nork.mobile.service.MobileDesignPlanService;
import com.nork.mobile.service.MobilePayRenderService;
import com.nork.mobile.service.MobileSearchDesignPlanProductService;
import com.nork.product.dao.CompanyFranchiserGroupMapper;
import com.nork.product.model.CompanyFranchiserGroup;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.model.*;
import com.nork.system.service.*;
import com.sandu.common.LoginContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.nork.system.controller.PlatformController.BASE_PLATFORM_INFO;
import static com.nork.system.controller.PlatformController.MOBILE2B_PLATFORM_CODE;

@Controller
@RequestMapping("/{style}/mobile/design/designPlan")
public class MobileDesignPlanController {
    private static Logger logger = Logger.getLogger(MobileDesignPlanController.class);

    private static final Gson GSON = new Gson();

    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private ResRenderPicService resRenderPicService;
    @Autowired
    private ResRenderPicMapper resRenderPicMapper;
    @Autowired
    private ResRenderVideoService resRenderVideoService;
   /* @Autowired
    private DesignPlanAutoRenderServiceImpl designPlanAutoRenderServiceImpl;*/
   @Autowired
   private DesignPlanAutoRenderService designPlanAutoRenderService;
    @Autowired
    private MobilePayRenderService mobilePayRenderService;
    @Autowired
    private MobileSearchDesignPlanProductService mobileSearchDesignPlanProductService;
    @Autowired
    private MobileDesignPlanService mobileDesignPlanService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CompanyFranchiserGroupMapper companyFranchiserGroupMapper;
    @Autowired
    private MobileDesignPlanProductService mobileDesignPlanProductService;
    @Autowired
    private FullHouseDesignPlanService fullHouseDesignPlanService;
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;

    /**
     * 我的设计列表
     *
     * @param model
     * @return
     */
    @SuppressWarnings("rawtypes")
    /*@RequestMapping(value = "/myDesignPlanMobile")*/
    @ResponseBody
    public ResponseEnvelope getThumbList(
            @RequestBody MobileRenderingModel model) {
        ResponseEnvelope envelope = new ResponseEnvelope();

        String msgId = model.getMsgId();
        Integer start = model.getStart();//第几条数据
        Integer limit = model.getLimit();//每页显示多少条，最大不能超过20页
        Integer userId = model.getUserId();
        String name = model.getName();//查询名称
        Integer spaceFunctionId = model.getSpaceFunctionId();
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        if (userId < 0) {
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }

        SysUser sysUser = sysUserService.get(userId);
        //主账号ID
        Integer franchiserId = null;
        //1为经销商 子账号 ,0为其他账号
        Integer type = 0;
        //经销商账号类型
        Integer franchiserAccountType = null;
        Integer userType = sysUser.getUserType();
        Integer franchiseGroupId = sysUser.getFranchiserGroupId();
        logger.error("我的设计列表  userId= " + userId + "       franchiseGroupId=" + franchiseGroupId);
        if (userType.intValue() == 3 && franchiseGroupId.intValue() > 0) {
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupMapper.selectByPrimaryKey(franchiseGroupId);
            franchiserId = companyFranchiserGroup.getFranchiserId();
            franchiserAccountType = sysUser.getFranchiserAccountType();
            if (userType.intValue() == 3 && franchiserAccountType.intValue() == 2) {
                type = 1;
            }
        }
        //添加平台过滤，能看到B端所有消息
        String platformBussinessType = null;
        String basePlatformInfo = JedisUtils.getMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE);
        if (StringUtils.isNotEmpty(basePlatformInfo)) {
            BasePlatform basePlatform = GSON.fromJson(basePlatformInfo, BasePlatform.class);
            platformBussinessType = basePlatform.getPlatformBussinessType();
        }
        DesignPlanRenderScene designPlanRenderScene = new DesignPlanRenderScene();
        designPlanRenderScene.setPlatformBussinessType(platformBussinessType);
        designPlanRenderScene.setUserId(userId);
        designPlanRenderScene.setPlanName(name);
        designPlanRenderScene.setStart(start);
        designPlanRenderScene.setLimit(limit);
        designPlanRenderScene.setSpaceFunctionId(spaceFunctionId);
        designPlanRenderScene.setFranchiserId(franchiserId);
        envelope = designPlanAutoRenderService.getrenderPicByPageV2(designPlanRenderScene, type);
        envelope.setMsgId(msgId);
        envelope.setMessage("success");
        return envelope;

    }

    /**
     * 获取720图片  ，，没有按钮的
     *
     * @param designPlan
     * @return
     */
    @RequestMapping(value = "/getPanoPicture")
    @ResponseBody
    public Object getPanoPicture(@RequestBody MobileRenderingModel designPlan, HttpServletRequest request) {
        if (designPlan.getThumbId() == null) {
            return new ResponseEnvelope<ResRenderPic>(false, "请求参数为空");
        }

        ResRenderPic res = resRenderPicService.get(designPlan.getThumbId());
        //获取用户信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        //获取二维码路径   没有按钮的
        String panoPath = this.getQRpicPath(res, loginUser);
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("url", panoPath);
        returnMap.put("contentUrl", res.getId().toString());
//		return new ResponseEnvelope<ResRenderPic>(panoPath);
        return new ResponseEnvelope<>(returnMap);
    }

    /**
     * 获取空间类型
     *
     * @return
     */
    @RequestMapping(value = "/getSpace")
    @ResponseBody
    public Object getSpace() {
        List<SysDictionary> list = sysDictionaryService.findAllByType("houseType");
        return new ResponseEnvelope<SysDictionary>(list.size(), list);
    }


    /**
     * 获取该方案的产品清单
     *
     * @param model
     * @return
     */
    @RequestMapping("/getDesignPlanProductList")
    @ResponseBody
    public Object getDesignPlanProductList(
            @RequestBody MobileRenderingModel model, HttpServletRequest request) {

        Integer userId = SystemCommonUtil.getUserIdByToken(request);
        if (userId == null || userId.intValue() <= 0) {
            return new ResponseEnvelope(false, "获取不到用户信息");
        }
        model.setUserId(userId);
//        return mobileSearchDesignPlanProductService.getDesignPlanProductList(model);
        List<ProductsCostType> list = mobileDesignPlanProductService.getDesignPlanProductList(model);
        if (list == null) {
            return new ResponseEnvelope(false, "产品清单显示失败");
        }
        return new ResponseEnvelope(list.size(), list);
    }

    /**
     * 生成二维码图的url
     *
     * @param resPic
     * @param loginUser
     * @return
     */
    public String getQRpicPath(ResRenderPic resPic, LoginUser loginUser) {
        int renderingType = resPic.getRenderingType().intValue();
        String picPath = this.getQRCodeInfo(resPic, loginUser);
        if (resPic.getRenderingType() != null) {
            if (RenderTypeCode.COMMON_720_LEVEL == renderingType || RenderTypeCode.HD_720_LEVEL == renderingType) {// 普通单图720
                picPath = Utils.getPropertyName("app", "app.server.url", "")
                        + Utils.getPropertyName("app", "app.server.siteName", "") + picPath;
            } else if (RenderTypeCode.ROAM_720_LEVEL == renderingType) {// 720漫游
                picPath = Utils.getPropertyName("app", "app.server.url", "")
                        + Utils.getPropertyName("app", "app.server.siteName", "") + picPath;
            } else if (RenderTypeCode.COMMON_VIDEO == renderingType) {//普通视频
                picPath = Utils.getPropertyName("app", "app.resources.url", "") + picPath;
            } else if (RenderTypeCode.HD_VIDEO == renderingType) {//高清视频
                picPath = Utils.getPropertyName("app", "app.resources.url", "") + picPath;
            } else {
                picPath = Utils.getValue("app.resources.url", "") + picPath;
            }
        } else {
            /*取普通渲染图路径*/
            picPath = Utils.getValue("app.resources.url", "") + picPath;
        }
        return picPath;
    }

    /**
     * 二维码的信息
     *
     * @param resPic
     * @param loginUser
     * @return
     */
    public String getQRCodeInfo(ResRenderPic resPic, LoginUser loginUser) {

        String picPath = "";
        String code = "";
        if (resPic.getRenderingType() != null) {
            if (RenderTypeCode.COMMON_720_LEVEL == resPic.getRenderingType() || RenderTypeCode.HD_720_LEVEL == resPic.getRenderingType()) {// 普通单图720
                ResRenderPic singleCode = resRenderPicMapper.get720SingleCode(resPic);//得到720单点分享需要的code
                if (singleCode != null && StringUtils.isNotEmpty(singleCode.getSysCode())) {
                    code = singleCode.getSysCode();
                } else {
                    logger.error("ResRenderPic is null ,res_render_pic_id=" + resPic.getId());
                    return null;
                }
                picPath = "pages/vr720/vr720MobileSingle.htm?code=" + code;
            } else if (RenderTypeCode.ROAM_720_LEVEL == resPic.getRenderingType()) {// 720漫游
                // 获取720漫游组信息，传过来的pid问截图ID。渲染图列表接口返回了缩略图列表和对应的截图ID
                logger.error("id = " + resPic.getId());
                DesignRenderRoam romanCode = resRenderPicMapper.get720RomanCode(resPic);
                logger.error("romanCode  == " + romanCode.getUuid());
                if (romanCode != null && StringUtils.isNotEmpty(romanCode.getUuid())) {
                    code = romanCode.getUuid();
                    picPath = "pages/vr720/vr720RoamOfMobile.htm?code=" + code;
                } else {
                    logger.error("DesignRenderRoam is null ,res_render_pic_id=" + resPic.getId());
                    return null;
                }
            } else if (RenderTypeCode.COMMON_VIDEO == resPic.getRenderingType() || RenderTypeCode.HD_VIDEO == resPic.getRenderingType()) {//普通视频
                ResRenderVideo resRenderVideo = new ResRenderVideo();
                resRenderVideo.setIsDeleted(0);
                resRenderVideo.setSysTaskPicId(resPic.getId());
                List<ResRenderVideo> videoList = resRenderVideoService.getList(resRenderVideo);
                if (videoList == null || videoList.size() <= 0) {
                    return null;
                } else if (videoList != null && videoList.size() == 1) {
                    picPath = videoList.get(0).getVideoPath();
                } else {
                    return null;
                }
            } else {
                picPath = resPic.getPicPath();
            }
        } else {
            /*取普通渲染图路径*/
            picPath = resPic.getPicPath();
        }
        return picPath;
    }

    /**
     * 产品替换
     *
     * @param taskId
     * @param msgId
     * @return
     */
    @RequestMapping("/productReplaceList")
    @ResponseBody
    public ResponseEnvelope<ProductReplaceTaskDetail> productReplaceList(Integer taskId, String msgId) {
        List<ProductReplaceTaskDetail> replaceProductList = mobilePayRenderService.selectListByTaskId(taskId, msgId);
        ResponseEnvelope<ProductReplaceTaskDetail> result = new ResponseEnvelope<ProductReplaceTaskDetail>(replaceProductList, msgId);
        return result;
    }

    /**
     * 产品移除
     *
     * @param taskId
     * @param msgId
     * @return
     */
    @RequestMapping("/productDelList")
    @ResponseBody
    public ResponseEnvelope<ProductReplaceTaskDetail> productDelList(Integer taskId, String msgId) {
        List<ProductReplaceTaskDetail> replaceProductList = mobilePayRenderService.selectDelListByTaskId(taskId, msgId);
        ResponseEnvelope<ProductReplaceTaskDetail> result = new ResponseEnvelope<ProductReplaceTaskDetail>(replaceProductList, msgId);
        return result;
    }

    /**
     * 组合替换
     *
     * @param taskId
     * @param msgId
     * @return
     */
    @RequestMapping("/groupProductReplaceList")
    @ResponseBody
    public ResponseEnvelope<ProductGroupReplaceTaskDetail> groupProductReplaceList(Integer taskId, String msgId) {
        List<ProductGroupReplaceTaskDetail> groupReplaceList = mobilePayRenderService.selectGroupReplaceListByTaskId(taskId, msgId);
        ResponseEnvelope<ProductGroupReplaceTaskDetail> result = new ResponseEnvelope<ProductGroupReplaceTaskDetail>(groupReplaceList, msgId);
        return result;
    }

    /**
     * 材质替换
     *
     * @param taskId
     * @param msgId
     * @return
     */
    @RequestMapping("/textureReplaceList")
    @ResponseBody
    public ResponseEnvelope<ProductReplaceTaskDetail> textureReplaceList(Integer taskId, String msgId) {
        List<ProductReplaceTaskDetail> selectTextureReplaceList = mobilePayRenderService.selectTextureReplaceListByTaskId(taskId, msgId);
        ResponseEnvelope<ProductReplaceTaskDetail> result = new ResponseEnvelope<ProductReplaceTaskDetail>(selectTextureReplaceList, msgId);
        return result;
    }

    /**
     * 逻辑删除我的设计、我的任务、我的消息
     *
     * @param model
     * @return
     */
    @RequestMapping("/deleteMyDesignPlanAndTask")
    @ResponseBody
    public Object deleteMyDesignPlanAndTask(@RequestBody MobileRenderingModel model) {
        Integer planId = model.getPlanId();
        if (planId == null || planId==0) {
            return new ResponseEnvelope(false,"方案id有误");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false,"请登录");
        }

        Integer userId = loginUser.getId();

        if (AutoRenderTaskConstant.PLAN_FULL_HOUSE_TYPE.equals(model.getPlanHouseType())){
            return fullHouseDesignPlanService.deleteFullHousePlanAndTask(planId,userId);
        } else if(model.getPlanHouseType().equals(0)){
            return designPlanAutoRenderService.deleteTaskAndTaskStateByTaskId(planId,loginUser);
        } else {
            return mobileDesignPlanService.deleteMyDesignPlanAndTask(planId);
        }
    }


    /**
     * "我的设计" 用户渲染任务和效果图方案展示列表
     * @author: chenm
     * @date: 2019/1/22 20:40
     * @param search
     * @return: com.nork.common.model.ResponseEnvelope
     */
    @RequestMapping(value = "/myDesignPlanMobile")
    @ResponseBody
    public ResponseEnvelope getUserRenderPlan(
            @RequestBody MobileRenderingSearch search,HttpServletRequest request) {
        ResponseEnvelope envelope = new ResponseEnvelope();
        System.out.println(search.toString());
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if(loginUser == null || loginUser.getId() == null){
            return new ResponseEnvelope(false,"用户信息无效，请重新登录！");
        }
        String platformCode = request.getHeader("Platform-Code");
        if(Utils.isBlank(platformCode)){
            return new ResponseEnvelope(false,"缺少平台标识！");
        }
        //主账号ID
        Integer franchiserId = null;
        //1为经销商 子账号 ,0为其他账号
        Integer type = 0;
        //经销商账号类型
        Integer franchiserAccountType = null;
        SysUser sysUser = sysUserService.get(loginUser.getId());
        Integer userType = sysUser.getUserType();
        Integer franchiseGroupId = sysUser.getFranchiserGroupId();
        if (userType.intValue() == 3 && franchiseGroupId.intValue() > 0) {
            //如果用户账号是经销商子账号，那么也可以看到主账号的方案信息
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupMapper.selectByPrimaryKey(franchiseGroupId);
            if(companyFranchiserGroup != null){
                franchiserId = companyFranchiserGroup.getFranchiserId();
                franchiserAccountType = sysUser.getFranchiserAccountType();
                if (userType.intValue() == 3 && franchiserAccountType.intValue() == 2) {
                    type = 1;
                }
            }
        }
        //添加平台过滤，能看到B端所有消息
        String platformBussinessType = null;
        BasePlatform basePlatform = basePlatformService.getByPlatformCode(platformCode);
        platformBussinessType = basePlatform.getPlatformBussinessType();
        List<Integer> platformIdList = basePlatformService.getIdsbyBussinessType(platformBussinessType);
        search.setPlatformIdList(platformIdList);
        List<Integer> userIdList = new ArrayList<>();
        userIdList.add(loginUser.getId());
        if(franchiserId != null && franchiserId > 0 && !franchiserId.equals(loginUser.getId())){
            userIdList.add(franchiserId);
        }
        search.setUserIdList(userIdList);
        search.setUserId(loginUser.getId());
        int count = designPlanAutoRenderService.selectUserRenderCount(search);
        List<ThumbData> thumbDataList = new ArrayList<>();
        if(count > 0){
            thumbDataList = designPlanAutoRenderService.getUserRenderPlanAndTaskList(search);
        }

        return new ResponseEnvelope<ThumbData>(count,thumbDataList);

    }

    @RequestMapping(value = "/updatePlanName")
    @ResponseBody
    public ResponseEnvelope updatePlanName(@RequestBody PlanNameUpdate nameUpdate) {
        ResponseEnvelope envelope = new ResponseEnvelope();

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if (loginUser == null || loginUser.getId() == null) {
            return new ResponseEnvelope(false, "用户信息无效，请重新登录！");
        }
        if(Utils.isBlank(nameUpdate.getPlanName())){
            return new ResponseEnvelope(false, "名称不允许为空！");
        }
        if(nameUpdate.getPlanId() != null && nameUpdate.getPlanId() > 0){
            DesignPlanRenderScene planRenderScene = designPlanRenderSceneService.get(nameUpdate.getPlanId());
            if(planRenderScene == null || planRenderScene.getId() == null){
                return new ResponseEnvelope(false, "没有该方案！");
            }
            DesignPlanRenderScene planUpdate = new DesignPlanRenderScene();
            planUpdate.setId(nameUpdate.getPlanId());
            planUpdate.setPlanName(nameUpdate.getPlanName());
            planUpdate.setGmtModified(new Date());
            planUpdate.setModifier(loginUser.getLoginName());
            designPlanRenderSceneService.update(planUpdate);
        }else if(nameUpdate.getTaskId() != null && nameUpdate.getTaskId() > 0){
            AutoRenderTask renderTask = new AutoRenderTask();
            renderTask.setId(nameUpdate.getTaskId());
            renderTask.setDesignName(nameUpdate.getPlanName());
            designPlanAutoRenderService.updateAutoRenderTask(renderTask);
            //.....
        }else{
            return new ResponseEnvelope(false, "缺少参数！");
        }
        return new ResponseEnvelope(true, "修改成功！");
    }

}
