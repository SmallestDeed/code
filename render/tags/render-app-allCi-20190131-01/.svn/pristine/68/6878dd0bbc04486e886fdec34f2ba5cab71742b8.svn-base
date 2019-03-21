package com.nork.render.controller;

import com.google.gson.Gson;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.exception.BizException;
import com.nork.common.jwt.Jwt;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.service.DesignPlanAutoRenderService;
import com.nork.render.model.input.RenderPayInput;
import com.nork.render.model.vo.RenderPayVo;
import com.nork.render.service.HighDefinitionRenderService;
import com.sandu.common.LoginContext;
import com.sandu.pay.order.metadata.PayState;
import com.sandu.platform.BasePlatform;
import com.sandu.system.service.BasePlatformService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName ProfessionalRenderController
 * @Description 专业渲染
 * @Author chenm
 * @Date 2019/1/15 17:07
 * @Version 1.0
 **/
@RequestMapping("/{style}/render/profession")
@Controller
public class ProfessionalRenderController {
    Logger logger = LoggerFactory.getLogger(ProfessionalRenderController.class);
    @Autowired
    private HighDefinitionRenderService highDefinitionRenderService;
    @Autowired
    private DesignPlanAutoRenderService designPlanAutoRenderService;

    /**
     * 获取高清渲染任务支付信息
     * @author: chenm
     * @date: 2019/1/15 16:38
     * @param payInput
     * @return: java.lang.Object
     */
    @RequestMapping("/getRenderCost")
    @ResponseBody
    public Object getRenderPayInfo(@ModelAttribute RenderPayInput payInput,HttpServletRequest request){
        String platformCode = request.getHeader("Platform-Code");
        String authorization = request.getHeader("Authorization");
        if(Utils.isBlank(platformCode)){
            return new ResponseEnvelope<>(false,"缺失平台信息!");
        }
        if(Utils.isBlank(authorization)){
            return new ResponseEnvelope<>(false,"缺失用户信息!");
        }
        payInput.setPlatformCode(platformCode);
        payInput.setAuthorization(authorization);
        if(payInput.getDesignPlanId() == null ||payInput.getRenderingType() == null ||payInput.getPayMethodNum() == null ){
            return new ResponseEnvelope<>(false,"缺失参数!");
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null){
            return new ResponseEnvelope<>(false,"缺失用户信息");
        }

        //判断支付方式
        RenderPayInput.PayMethodEnum payMethodEnum = null;
        if(payInput.getPayMethodNum() == null || payInput.getPayMethodNum().equals(RenderPayInput.PayMethodEnum.DUBI.getPayMethodNum())){
            //默认使用度币支付
            payMethodEnum = RenderPayInput.PayMethodEnum.DUBI;
            payInput.setPayMethodNum(payMethodEnum.getPayMethodNum());
        }else if(payInput.getPayMethodNum().equals(RenderPayInput.PayMethodEnum.ALI.getPayMethodNum())){
            payMethodEnum = RenderPayInput.PayMethodEnum.ALI;
        }else if(payInput.getPayMethodNum().equals(RenderPayInput.PayMethodEnum.WX.getPayMethodNum())){
            payMethodEnum = RenderPayInput.PayMethodEnum.WX;
        }else{
            return new ResponseEnvelope<>(false,"支付类型无效!");
        }
        payInput.setMethodEnum(payMethodEnum);
        RenderPayVo renderPayVo = null;
        try {
            renderPayVo = highDefinitionRenderService.getHDRenderPayInfo(payInput,loginUser);
        } catch (BizException e) {
            logger.error("getRenderCost 出现异常:{}",e);
            return new ResponseEnvelope<>(false,e.getMessage());
        }

        return new ResponseEnvelope<>(true,renderPayVo);
    }

    /**
     * 微信/支付宝 支付专业渲染费用回调
     * @author: chenm
     * @date: 2019/1/23 21:25
     * @param request
     * @param response
     * @return: void
     */
    @RequestMapping("/payHDRenderCallBack")
    public void payHDRenderCallBack(HttpServletRequest request , HttpServletResponse response){

        logger.error("开始调用支付回调接口");

        //接收回调信息
        Map<String,String> paramMap = new HashMap<>();
        Map<String,String[]> requestParamMap = new HashMap<>();
        requestParamMap = request.getParameterMap();
        if(requestParamMap == null || requestParamMap.size() < 1){
            try {
                response.getWriter().print("FAIL");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        for (String key : requestParamMap.keySet()){
            String[] values = requestParamMap.get(key);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < values.length; i++){
                stringBuffer.append(i == values.length -1 ? values[i] : values[i] + ",");
            }
            paramMap.put(key,stringBuffer.toString());
        }
        String resultCode = paramMap.get("resultCode");
        String resultMsg = paramMap.get("resultMsg");
        String intenalTradeNo = paramMap.get("intenalTradeNo");
        String payTradeNo = paramMap.get("payTradeNo");
        String platformCode = paramMap.get("platformCode");

        logger.error("开始调用支付回调接口参数="+new Gson().toJson(paramMap));

        try {
            //处理
            if(Objects.equals("SUCCESS",resultCode)){
                //更改订单状态
                highDefinitionRenderService.updatePayOrderState(intenalTradeNo, PayState.SUCCESS,payTradeNo,resultMsg);
                //生成高清渲染订单
                //创建渲染任务并回填pay_order
                highDefinitionRenderService.createHDRenderTaskWithOrder(intenalTradeNo,platformCode);
                //发送websocket 消息给前端
                highDefinitionRenderService.sendMessageForCreateHDRenderTask(intenalTradeNo);
                response.getWriter().print("SUCCESS");
            }else{
                //更改订单状态
                highDefinitionRenderService.updatePayOrderState(intenalTradeNo, PayState.PAY_ERROR,payTradeNo,resultMsg);
                response.getWriter().print("FAIL");
            }
        } catch (Exception e) {
            logger.error("支付回调接口执行异常",e);
        }
    }

    /**
     * 度币支付 专业渲染花费
     * @author: chenm
     * @date: 2019/1/23 21:21
     * @param request
     * @param payInput
     * @return: java.lang.Object
     */
    @RequestMapping("/saveHDRenderOrder")
    @ResponseBody
    public Object saveHighDefinitionRenderOrder(HttpServletRequest request,@ModelAttribute RenderPayInput payInput) {
        String platformCode = request.getHeader("Platform-Code");
        String authorization = request.getHeader("Authorization");
        if (Utils.isBlank(platformCode)) {
            return new ResponseEnvelope<>(false,"缺失平台信息!");
        }
        payInput.setPlatformCode(platformCode);
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null || loginUser.getId() < 0){
            return new ResponseEnvelope<>(false,"请重新登录!");
        }
        payInput.setPayMethodNum(0);
        if(payInput == null || payInput.getDesignPlanId() == null || payInput.getRenderingType() == null ){
            return new ResponseEnvelope<>(false,"缺少参数!");
        }
        RenderPayInput.PayMethodEnum payMethodEnum = null;
        if(payInput.getPayMethodNum() == null || payInput.getPayMethodNum().equals(RenderPayInput.PayMethodEnum.DUBI.getPayMethodNum())){
            //默认使用度币支付
            payMethodEnum = RenderPayInput.PayMethodEnum.DUBI;
            payInput.setMethodEnum(RenderPayInput.PayMethodEnum.DUBI);
            payInput.setPayMethodNum(payMethodEnum.getPayMethodNum());
        }else{
            return new ResponseEnvelope<>(false,"支付类型无效!");
        }
        boolean ret;
        try {
            ret = highDefinitionRenderService.saveRenderOrderWithDuBi(payInput, loginUser);
        } catch (BizException e) {
            logger.error("saveHDRenderOrder出现异常:", e);
            return new ResponseEnvelope<>(false, e.getMessage());
        } catch (Exception e1) {
            logger.error("saveHDRenderOrder出现异常:", e1);
            return new ResponseEnvelope<>(false, "出现异常,支付失败！");
        }
        if (ret) {
            return new ResponseEnvelope<>(true, "支付成功！");
        } else {
            return new ResponseEnvelope<>(false, "支付失败！");
        }

    }

    /**
     * 渲染机获取高清渲染任务
     * @author: chenm
     * @date: 2019/1/19 16:32
     * @param response
     * @param request
     * @param renderMachineIp
     * @param renderLevel
     * @param renderProgramVersion
     * @return: java.lang.Object
     */
    @RequestMapping("/getHDRenderTaskByAuto")
    @ResponseBody
    public Object getHDRenderTaskByAuto(HttpServletResponse response,HttpServletRequest request,String renderMachineIp,Integer renderLevel
            ,String renderProgramVersion) {

        AutoRenderTask renderTask = null;

        //先查找置顶任务
        do {
            renderTask = designPlanAutoRenderService.getHDRenderTaskByAuto(SystemCommonConstant.REDIS_HD_RENDER_TASK_STICK,renderMachineIp,renderLevel,renderProgramVersion);
        }while (JedisUtils.getSizeOfList(SystemCommonConstant.REDIS_HD_RENDER_TASK_STICK) > 0 && renderTask == null);
        //没有置顶任务再查找高清渲染任务
        if(renderTask == null){
            do {
                renderTask = designPlanAutoRenderService.getHDRenderTaskByAuto(SystemCommonConstant.REDIS_HD_RENDER_TASK_LIST,renderMachineIp,renderLevel,renderProgramVersion);
            }while (JedisUtils.getSizeOfList(SystemCommonConstant.REDIS_HD_RENDER_TASK_LIST) > 0 && renderTask == null);
        }


        return new ResponseEnvelope<>(true,renderTask);
    }

    /**
     * 删除用户的高清渲染任务
     * @author: chenm
     * @date: 2019/1/19 17:06
     * @param response
     * @param request
     * @param taskId 任务Id
     * @return: java.util.Objects
     */
    @RequestMapping("/deleteHDRenderTask")
    @ResponseBody
    public Object deleteHDRenderTask(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "taskId") Integer taskId, @RequestParam(value = "msgId")String msgId ){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null || loginUser.getId() <= 0){
            return new ResponseEnvelope<>(false,"请重新登录!",msgId);
        }
        if(taskId == null || taskId < 0){
            return new ResponseEnvelope<>(false,"参数无效!",msgId);
        }
        AutoRenderTask renderTask = designPlanAutoRenderService.getRenderTaskById(taskId);
        if(renderTask == null){
            return new ResponseEnvelope<>(false,"该任务不存在!",msgId);
        }
        if(Objects.equals(renderTask.getOperationUserId(),loginUser.getId())){
            return new ResponseEnvelope<>(false,"您无权限删除此任务!",msgId);
        }

        //删除任务及关联状态记录
        boolean ret = designPlanAutoRenderService.deleteTaskAndStateByTaskId(taskId,loginUser);

        if (ret) {
            return new ResponseEnvelope<>(true,"删除成功!",msgId);
        }else{
            return new ResponseEnvelope<>(false,"删除失败!",msgId);
        }

    }

    /**
     * 渲染失败调的接口.....
     * @author: chenm
     * @date:
     * @param planId
     * @param templateId
     * @param businessId
     * @param failType
     * @param failReason
     * @param taskId
     * @param request
     * @param response
     * @param msgId
     * @return: com.nork.common.model.ResponseEnvelope<com.nork.design.model.AutoRenderTaskState>
     */
    /*@RequestMapping("/updateHDRenderTaskState")
    @ResponseBody
    public ResponseEnvelope<AutoRenderTaskState> updateHDRenderTaskState(Integer planId, Integer templateId, Integer businessId , Integer failType, String failReason, Integer taskId, HttpServletRequest request, HttpServletResponse response, String msgId) {
        String token = request.getHeader("Authorization");
        Map<String, Object> map = Jwt.validToken(token);
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            logger.error("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
        if (taskId == null || taskId.intValue() < 1) {
            return new ResponseEnvelope<>(false, "任务ID为空！");
        }
        AutoRenderTaskState taskState = new AutoRenderTaskState();
        taskState.setPlanId(planId);
        taskState.setTemplateId(templateId);
        taskState.setFailReason(failReason);
        taskState.setFailType(failType);
        taskState.setBusinessId(businessId);
        taskState.setTaskId(taskId);


    }*/

}
