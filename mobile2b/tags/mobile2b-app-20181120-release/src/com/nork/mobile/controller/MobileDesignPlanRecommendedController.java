package com.nork.mobile.controller;

import com.google.gson.Gson;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.constant.AutoRenderTaskConstant;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.*;
import com.nork.home.model.SpaceCommonConstant;
import com.nork.mobile.model.search.DesignPlanRecommendedModel;
import com.nork.mobile.service.MobileDesignPlanRecommendedService;
import com.nork.system.model.BasePlatform;
import com.nork.system.model.ResRenderPic;
import com.nork.system.service.ResRenderPicService;
import com.sandu.common.LoginContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.nork.system.controller.PlatformController.BASE_PLATFORM_INFO;
import static com.nork.system.controller.PlatformController.MOBILE2B_PLATFORM_CODE;

/**
 * 移动端推荐设计方案的controller
 *
 * @author yangz
 */
@Controller
@RequestMapping("/{style}/mobile/designPlanRecommended")
public class MobileDesignPlanRecommendedController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MobileDesignPlanRecommendedController.class);
    private static final Gson GSON = new Gson();

    @Autowired
    private MobileDesignPlanRecommendedService mobileDesignPlanRecommendedService;
    @Autowired
    private ResRenderPicService resRenderPicService;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    public final static String SYSTEM_FORMAT = Utils.getValue("app.system.format", "linux").trim();

    /**
     * 将推荐方案列表的类型返回给前端
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/returnRecommendedType")
    @ResponseBody
    public ResponseEnvelope returnRecommendedType() {
        /*[{
            "name": "样板方案",
    		"value": "public"
    	}, {
    		"name": "一键方案",
    		"value": "decorate"
    	}]*/
        RecommendedType templetPlan = new RecommendedType();
        templetPlan.setPlanTypeName("样板方案");
        templetPlan.setPlanTypeCode("public");
        RecommendedType onekey = new RecommendedType();
        onekey.setPlanTypeName("一键方案");
        onekey.setPlanTypeCode("decorate");

        List<RecommendedType> recommendedTypeList = new ArrayList<RecommendedType>();
        recommendedTypeList.add(onekey);
        recommendedTypeList.add(templetPlan);


        return new ResponseEnvelope(recommendedTypeList.size(), recommendedTypeList);
    }

    private class RecommendedType {
        //方案类型名称
        private String planTypeName;
        //方案类型value
        private String planTypeCode;

        public String getPlanTypeName() {
            return planTypeName;
        }

        public void setPlanTypeName(String planTypeName) {
            this.planTypeName = planTypeName;
        }

        public String getPlanTypeCode() {
            return planTypeCode;
        }

        public void setPlanTypeCode(String planTypeCode) {
            this.planTypeCode = planTypeCode;
        }

    }

    /**
     * 方案推荐 列表 数据
     *
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unused")
    @RequestMapping("/planRecommendedList")
    @ResponseBody
    public Object planRecommendedList(@RequestBody DesignPlanRecommendedModel designPlanRecommendedModel,
                                      HttpServletRequest request, HttpServletResponse response) {
        String msgId = designPlanRecommendedModel.getMsgId();
        boolean valid = this.validPlanReleaseParam(msgId);
        if (!valid) {
            return new ResponseEnvelope<>(false, "参数不完整", msgId);
        }

        // 获取登录用户的信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser) {
            return new ResponseEnvelope(false, "登录信息已失效，请重新登录", msgId);
        }

        //添加平台过滤，平台id
        Integer platformId = null;
        String basePlatformInfo = JedisUtils.getMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE);
        logger.error("推荐方案列表 ====> basePlatform="+basePlatformInfo);
        if (StringUtils.isNotEmpty(basePlatformInfo)) {
            BasePlatform basePlatform = GSON.fromJson(basePlatformInfo, BasePlatform.class);
            platformId = basePlatform.getId();
        }

        PlanRecommendedListModel model = new PlanRecommendedListModel();

        BeanUtils.copyProperties(designPlanRecommendedModel,model);

        model.setLoginUser(loginUser);
        logger.error("推荐方案列表 ====> platformId="+platformId);
        model.setPlatformId(platformId);


        return mobileDesignPlanRecommendedService.getPlanRecommendedList(model);
    }

    /**
     * 参数完整性判断
     *
     * @param args
     * @return
     */
    private boolean validPlanReleaseParam(String... args) {
        boolean result = true;
        for (String arg : args) {
            if (StringUtils.isEmpty(arg)) {
                result = false;
                return result;
            }
        }
        return result;
    }

    /**
     * 从推荐页面里获取一个推荐方案的缩略图集
     */
    @RequestMapping(value = "/getRecommendedPictureList")
    @ResponseBody
    public Object getRecommendedPictureList(@RequestBody DesignPlanRecommendedModel model) {
        String remark = model.getRemark();
        Integer planRecommendedId = model.getPlanRecommendedId();
        if (planRecommendedId == null) {
            return new ResponseEnvelope<ResRenderPic>(false, "请求参数为空");
        }
        ResRenderPic res = new ResRenderPic();
        List<String> fileKeys = new ArrayList<>();

        res.setPlanRecommendedId(planRecommendedId);
        res.setIsDeleted(0);
        res.setOrder(" gmt_create desc");

        if (remark.equals("photo")) {
            res.setRenderingType(1);
            fileKeys.add("design.designPlanRecommended.render.small.pic");
            res.setFileKeyList(fileKeys);
        } else if (remark.equals("720")) {
            res.setRenderingType(4);
            fileKeys.add("design.designPlanRecommended.render.small.pic");
            res.setFileKeyList(fileKeys);
        } else if (remark.equals("roam")) {
            res.setRenderingType(8);
            fileKeys.add("design.designPlanRecommended.render.small.pic");
            res.setFileKeyList(fileKeys);
        } else if (remark.equals("video")) {
            res.setRenderingType(6);
            fileKeys.add("design.designPlanRecommended.render.video.cover");
            fileKeys.add("design.designPlanRecommended.render.pic");
            res.setFileKeyList(fileKeys);
        }

        List<ResRenderPic> picList = resRenderPicService.getList(res);

        ResponseEnvelope<ResRenderPic> envelope = new ResponseEnvelope<>();
        envelope.setDatalist(picList);
        envelope.setTotalCount(picList.size());
        return envelope;
    }

    /**
     * 获取方案或子方案是否适配样板房
     *
     * @param designPlanRecommendedModel
     * @return
     */
    @RequestMapping("/getMatchPlan")
    @ResponseBody
    public ResponseEnvelope getMatchPlan(@RequestBody DesignPlanRecommendedModel designPlanRecommendedModel) {
        if (designPlanRecommendedModel == null) {
            return new ResponseEnvelope(false, "参数为空");
        }
        Integer planRecommendedId = designPlanRecommendedModel.getPlanRecommendedId();
        if (planRecommendedId == null) {
            return new ResponseEnvelope(false, "推荐方案id为空");
        }
        Integer templateId = designPlanRecommendedModel.getTemplateId();
        if (templateId == null) {
            return new ResponseEnvelope(false, "样板房id为空");
        }

        DesignPlanRecommendedResult designPlanRecommended = mobileDesignPlanRecommendedService.getMatchPlan(designPlanRecommendedModel);
        if (designPlanRecommended == null) {
            return new ResponseEnvelope(false, "该方案不适合");
        }

        return new ResponseEnvelope(true, designPlanRecommended);
    }

}
