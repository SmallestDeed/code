package com.sandu.web.notify.wx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import com.sandu.api.applyHouse.model.BaseHouseApply;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;
import com.sandu.web.async.AsyncCallTemplateMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "templateMsg", description = "发送微信模板消息")
@RestController
@RequestMapping("/v1/notify/wx")
public class TemplateMsgController {

    private Logger logger = LoggerFactory.getLogger(TemplateMsgController.class);

    @Autowired
    private TemplateMsgService templateMsgService;

    @Resource
    private SysUserService userService;

    @Autowired
    private AsyncCallTemplateMsgService asyncCallTemplateMsgService;

    @Autowired
    private BaseAreaService baseAreaService;

    @ApiOperation(value = "收集小程序用户的formId", response = ResponseEnvelope.class)
    @PostMapping("/collectMiniUserFormId")
    public ResponseEnvelope collectMiniUserFormId(@RequestBody List<String> formIdList) {

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            if (formIdList == null || formIdList.size() == 0) {
                return new ResponseEnvelope<>(false, "formId列表为空");
            }
            SysUser user = userService.get(loginUser.getId());
            templateMsgService.collectMiniUserFormId(user.getOpenId(), formIdList);
            return new ResponseEnvelope<>(true, "保存成功!");
        } catch (Exception ex) {
            return new ResponseEnvelope<>(true, "保存失败!");
        }


    }

    @ApiOperation(value = "保存用户渲染时的formId", response = ResponseEnvelope.class)
    @PostMapping("/saveUserRenderFormId")
    public ResponseEnvelope saveUserRenderFormId(Long designPlanId, String formId, String forwardPage, Integer renderType, Integer renderLevel) {
        logger.info("保存用户渲染时的formId:designPlanId," + designPlanId + "formId:" + formId + ",forwardPage:" + forwardPage + ",renderType:" + renderType + ",renderLevel:" + renderLevel);
        //验证输入参数
        if (designPlanId == null) {
            return new ResponseEnvelope<>(false, "方案id不能为空!");
        }
        if (renderType == null) {
            return new ResponseEnvelope<>(false, "渲染类型不能为空!");
        } else if (renderType != 0 && renderType != 1) {
            return new ResponseEnvelope<>(false, "渲染类型不正确(0:装进我家,1:替换渲染)!");
        }
        if (renderLevel == null) {
            return new ResponseEnvelope<>(false, "渲染级别不能为空!");
        } else if (renderLevel != 1 && renderLevel != 4 && renderLevel != 6 && renderLevel != 8) {
            return new ResponseEnvelope<>(false, "渲染级别不正确(1:普通照片级 4:720单点 6.视频 8:720多点)!");
        }


        //保存formId到redis
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            boolean flag = templateMsgService.saveUserRenderFormId(loginUser.getId().longValue(), designPlanId, formId, forwardPage, renderType, renderLevel);
            if (flag) {
                return new ResponseEnvelope<>(true, "保存成功!");
            }
            return new ResponseEnvelope<>(true, "保存失败!");
        } catch (Exception ex) {
            return new ResponseEnvelope<>(true, "保存失败!");
        }


    }


    @ApiOperation(value = "发送渲染模板消息", response = ResponseEnvelope.class)
    @PostMapping("/sendRenderTemplateMsg")
    public ResponseEnvelope sendRenderTemplateMsg(Long userId, Long designPlanId, String designPlanName, String renderResult, Integer renderType, Integer renderLevel) {

        try {
            logger.info("发送渲染模板消息:userId," + userId + "designPlanId:" + designPlanId + ",designPlanName:" + designPlanName + ",renderResult:" + renderResult + ",renderType:" + renderType + ",renderLevel:" + renderLevel);
            //验证输入参数
            if (userId == null) {
                return new ResponseEnvelope<>(false, "用户id不能为空!");
            }
            if (designPlanId == null) {
                return new ResponseEnvelope<>(false, "方案id不能为空!");
            }
            if (renderType == null) {
                return new ResponseEnvelope<>(false, "渲染类型不能为空!");
            } else if (renderType != 0 && renderType != 1) {
                return new ResponseEnvelope<>(false, "渲染类型不正确(0:装进我家,1:替换渲染)!");
            }
            if (renderLevel == null) {
                return new ResponseEnvelope<>(false, "渲染级别不能为空!");
            } else if (renderLevel != 1 && renderLevel != 4 && renderLevel != 6 && renderLevel != 8) {
                return new ResponseEnvelope<>(false, "渲染级别不正确(1:普通照片级 4:720单点 6.视频 8:720多点)!");
            }
            //发送模板消息
            templateMsgService.sendRenderTemplateMsg(userId, designPlanId, designPlanName, renderResult, renderType, renderLevel);
            return new ResponseEnvelope<>(true, "发送成功!");
        } catch (Exception ex) {
            logger.error("发送渲染消息异常:", ex);
            return new ResponseEnvelope<>(false, "发送失败!");
        }
    }

    @GetMapping(value = "/sendHandlerApplyHouseMsg")
    public Object sendHandlerApplyHouseMsg(Integer applyHouseId) {

        if (Objects.isNull(applyHouseId)) {
            return new ResponseEnvelope<>(false, "参数不能为空");
        }

        try {
            sendTemplateMsg(templateMsgService.sendHandlerApplyHouseMsg(applyHouseId));
            return new ResponseEnvelope<>(true, "发送成功!");
        } catch (Exception e) {
            logger.error("发送申请户型消息异常:", e);
            return new ResponseEnvelope<>(false, "发送失败");
        }
    }

    private void sendTemplateMsg(Map<String, Object> objMap) {
        String page = "";//跳转的页面
        BaseHouseApply houseApply = (BaseHouseApply) objMap.get("house");
        if (Objects.equals(1, houseApply.getStatus())) {
            //上传完成
            page = "livingHouse";//跳转的页面
            //查询小区id
            Integer livingId = baseAreaService.getLivingId(houseApply.getAreaCode(), houseApply.getLivingInfo());
            logger.info("上传的完成的小区id =>{}"+livingId);
            if (Objects.nonNull(livingId)) {
                asyncCallTemplateMsgService.sendTemplateMsg((SysUser) objMap.get("sysUser"), BaseCompanyMiniProgramTemplateMsg.TEMPLATE_TYPE_APPLY_BASE_HOUSE_ADVICE, (Map) objMap.get("msgData"), page, livingId,houseApply.getLivingInfo());
            }
        }else{
            //跳转到户型搜索页面
            page = "searchHouse";
            asyncCallTemplateMsgService.sendTemplateMsg((SysUser) objMap.get("sysUser"), BaseCompanyMiniProgramTemplateMsg.TEMPLATE_TYPE_APPLY_BASE_HOUSE_ADVICE, (Map) objMap.get("msgData"), page, "","");
        }


    }

}
