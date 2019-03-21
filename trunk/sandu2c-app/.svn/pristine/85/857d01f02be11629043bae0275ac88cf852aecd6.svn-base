package com.sandu.web.supplydemand;


import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.exception.BizException;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.BadWordUtil;
import com.sandu.common.util.StringUtils;
import com.sandu.supplydemand.convert.BaseSupplyDemandConvert;
import com.sandu.supplydemand.input.DemandAdd;
import com.sandu.supplydemand.model.BaseSupplyDemand;
import com.sandu.supplydemand.output.BaseSupplyDemandVo;
import com.sandu.supplydemand.output.DemandOut;
import com.sandu.supplydemand.output.SupplyDemandDetailVo;
import com.sandu.supplydemand.service.SupplyDemandService;
import com.sandu.user.model.UserVo;
import com.sandu.user.service.SysUserService;
import com.sandu.validate.AnnotationValidator;
import com.sandu.validate.vo.ValidateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author weisheng
 * @Title:
 * @Package
 * @Description:
 * @date 2018/4/27 0027PM 5:37
 */
@Slf4j
@RestController
@RequestMapping("/v1/supplydeman/demand")
public class SXWDemandController {
    private final static String CLASS_LOG_PREFIX = "[随选网需求基础服务]";

    private static Gson gson = new Gson();
    @Autowired
    private SupplyDemandService supplyDemandService;


    @Autowired
    private SysUserService sysUserService;


    /**
     * @Title: 发布供求信息
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */
    @RequestMapping(value = "/publishdemandinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope publishSupplyDemandInfo(@RequestBody DemandAdd demandAdd, HttpServletRequest request){

        /*检查用户是否登录*/
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn(CLASS_LOG_PREFIX+"用户登录失败:"+gson.toJson(loginUser));
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info(CLASS_LOG_PREFIX+"用户登录校验通过:"+gson.toJson(loginUser));



        //校验传入信息
        ValidateResult result = AnnotationValidator.validate(demandAdd);
        if (!result.isValid()) {
            log.warn(CLASS_LOG_PREFIX+"参数校验未通过:"+gson.toJson(result));
            return new ResponseEnvelope(false, result.getFieldName() + "------------" + result.getMessage());
        }
        log.info(CLASS_LOG_PREFIX+"传参信息校验通过:"+gson.toJson(demandAdd));

        //如果传入方案ID,方案类型也必传.
        if(null != demandAdd.getPlanId() && demandAdd.getPlanId()>0){
            if(demandAdd.getPlanType() ==null || demandAdd.getPlanType() ==0 ){
                log.warn(CLASS_LOG_PREFIX+"方案ID和方案类型必须同时传入"+gson.toJson(demandAdd));
                return new ResponseEnvelope(false,"缺失参数");
            }
        }

        //校验信息标题是否含有敏感词汇
        if(StringUtils.isNotBlank(demandAdd.getTitle())){
            boolean containtBadWord = BadWordUtil.isContaintBadWord(demandAdd.getTitle(), 2);
            if(containtBadWord){
                return new ResponseEnvelope(false,"信息标题含有敏感词汇");
            }
        }

        //校验信息详情是否含有敏感词汇
        if(StringUtils.isNotBlank(demandAdd.getDescription())){
            boolean containtBadWord = BadWordUtil.isContaintBadWord(demandAdd.getDescription(), 2);
            if(containtBadWord){
                demandAdd.setDescription(BadWordUtil.replaceBadWord(demandAdd.getDescription(),2,"*"));
            }
        }


        //查询用户信息
        UserVo userVo;
        try {
            userVo = sysUserService.getUserInfo(loginUser.getId());
            if(null == userVo){
                log.warn(CLASS_LOG_PREFIX+"查询用户信息失败"+loginUser.getId());
                return new ResponseEnvelope(false,"查询用户信息失败");
            }
        }catch (Exception e ){
            log.error(CLASS_LOG_PREFIX+"查询用户信息数据异常"+e);
            return new ResponseEnvelope(false,"查询用户信息数据异常");
        }



        //保存发布信息到数据库
        DemandOut demandOut;
        try {
            demandOut = supplyDemandService.publishSupplyDemandInfo(demandAdd,userVo);
            if(null == demandOut){
                log.warn(CLASS_LOG_PREFIX+"需求信息发布失败"+gson.toJson(demandAdd));
                return new ResponseEnvelope(false,"需求信息发布失败");
            }
        }catch (Exception e){
            log.error(CLASS_LOG_PREFIX+"需求信息发布数据异常"+e);
            return new ResponseEnvelope(false,"需求信息发布数据异常");
        }


        return new ResponseEnvelope(true, "需求信息发布成功", demandOut);


    }




    /**
     * @Title: 获取供求信息详情
     * @Package
     * @Description:
     * @author weisheng
     * @date 2018/4/27 0027PM 6:02
     */

    @RequestMapping(value = "/getsupplydemanddetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getSupplyDemandInfo(@RequestParam(required = false) Integer supplyDemandId, HttpServletRequest request) {
         /*检查用户是否登录*/
      LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn(CLASS_LOG_PREFIX+"用户登录失败:"+gson.toJson(loginUser));
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info(CLASS_LOG_PREFIX+"用户登录校验通过:"+gson.toJson(loginUser));
/*    LoginUser loginUser = new LoginUser();
     loginUser.setName("--");
     loginUser.setId(24832);

        supplyDemandId = 283;*/
        //校验参数
        if(null ==supplyDemandId || 0 ==supplyDemandId){
            log.warn(CLASS_LOG_PREFIX+"缺失参数supplyDemandId");
            return new ResponseEnvelope(false,"缺失参数supplyDemandId");
        }
        //查询信息详情
        SupplyDemandDetailVo supplyDemandDetailVo;
        try {
            supplyDemandDetailVo = supplyDemandService.getSupplyDemandDetailVo(supplyDemandId,loginUser.getId());
            if (supplyDemandDetailVo == null) {
                log.warn(CLASS_LOG_PREFIX+"未获取到该供求信息:" + supplyDemandId);
                return new ResponseEnvelope(false, "未获取到该供求信息");
            }
        }catch (Exception e){
            log.error(CLASS_LOG_PREFIX+"获取该供求信息数据异常:", e);
            return new ResponseEnvelope(false, "获取该供求信息数据异常");
        }
        return new ResponseEnvelope(true, "", supplyDemandDetailVo);
    }




}
