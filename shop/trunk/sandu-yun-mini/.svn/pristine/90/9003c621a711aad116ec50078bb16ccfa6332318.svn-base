package com.sandu.web.company.controller;

import com.google.gson.Gson;
import com.sandu.api.user.model.SmsVo;
import com.sandu.cache.service.RedisService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.company.model.ProprietorInfo;
import com.sandu.company.model.input.AppointmentAdd;
import com.sandu.company.service.ProprietorInfoService;
import com.sandu.sys.model.vo.SysUserVo;
import com.sandu.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * 店铺预约服务---前端---控制层
 * @author WangHaiLin
 * @date 2018/9/28  14:43
 */

@Api(description = "预约服务", tags = "proprietorInfo")
@RestController
@RequestMapping(value = "/v1/sandu/mini/proprietorInfo")
public class ProprietorInfoController {

    /**短信验证码**/
    private static final String SESSION_SMS_CODE = "SmsCode";

    private static final Logger logger = LoggerFactory.getLogger(ProprietorInfoController.class.getName());

    @Autowired
    private ProprietorInfoService proprietorInfoService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "新增预约服务", response = ResponseEnvelope.class)
    @PostMapping("/save")
    public ResponseEnvelope saveAppointment(@Valid @RequestBody AppointmentAdd appointment, BindingResult validResult) {
        try{
            // 校验参数
            if (validResult.hasErrors()) {
                logger.error("新增预约服务：入参 AppointmentAdd：{}",appointment);
                return new ResponseEnvelope(false,"参数异常",validResult);
            }
            //验证码校验
            if (null!=appointment.getCode()){
                ResponseEnvelope response = this.checkPhone(appointment.getUserPhone(), appointment.getCode());
                if (!response.isSuccess()){
                    return new ResponseEnvelope<>(false,response.getMessage());
                }
            }
            //参数转换
            ProprietorInfo services = this.getServices(appointment);
            //向数据库添加数据
            int insert = proprietorInfoService.insert(services);
            if (insert>0){
                return new ResponseEnvelope<>(true,"添加预约信息成功",insert);
            }
            return new ResponseEnvelope<>(false,"添加预约信息失败");
        }catch (Exception e){
            logger.error("新增预约服务 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }


    @ApiOperation(value = "获取当前用户信息", response = ResponseEnvelope.class)
    @GetMapping("/getUserInfo")
    public ResponseEnvelope getUserInfo(@RequestParam("id") Integer id) {
        try{
            SysUserVo user = sysUserService.getById(id.longValue());
            if (null!=user){
                return new ResponseEnvelope<>(true, user);
            }
            return new ResponseEnvelope<>(false, "获取用户失败!");

        }catch (Exception e){
            logger.error("预约服务 --获取当前用户信息--方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }



    /**
     * 数据转换
     * @param add 新增预约入参
     * @return ProprietorInfo
     */
    private ProprietorInfo getServices(AppointmentAdd add){
        ProprietorInfo proprietorInfo=new ProprietorInfo();
        proprietorInfo.setShopId(add.getShopId().longValue());
        proprietorInfo.setUserName(add.getUserName());
        proprietorInfo.setMobile(add.getUserPhone());
        proprietorInfo.setServicesType(add.getServiceType());
        proprietorInfo.setIsDeleted(0);
        proprietorInfo.setCreator(add.getUserName());
        proprietorInfo.setModifier(add.getUserName());
        proprietorInfo.setGmtCreated(new Date());
        proprietorInfo.setGmtModified(new Date());
        proprietorInfo.setBusinessType(0);
        proprietorInfo.setType(3);
        proprietorInfo.setSourceType(1);
        proprietorInfo.setDesignplanId(add.getDesignplanId());
        proprietorInfo.setAppointUserId(add.getAppointUserId());
        proprietorInfo.setAreaName(add.getAreaName());
        return proprietorInfo;
    }

    /**
     * 校验短信验证码
     * @param phone 电话号码
     * @param code 验证码
     * @return ResponseEnvelope 校验结果
     */
    private ResponseEnvelope checkPhone(String phone, String code) {
        Gson gson = new Gson();
        // TODO Auto-generated method stub
        logger.info("checkPhone():" + "phone:" + phone + "code:" + code);
        ResponseEnvelope resopnse = new ResponseEnvelope();
        //获取当前时间
        long currentTime = System.currentTimeMillis();
        long sendCodeTime = 0L;
        Integer verifyCount = 0;
        String yzm = "";
        //获取缓存中信息
        String map = redisService.getMap(SESSION_SMS_CODE, phone);
        if (StringUtils.isBlank(map)) {
            resopnse.setSuccess(false);
            resopnse.setMessage("请先获取验证码！");
            return resopnse;
        }
        SmsVo smsVo = gson.fromJson(map, SmsVo.class);
        sendCodeTime = smsVo.getSendTime();
        yzm = smsVo.getCode();
        verifyCount = smsVo.getVerifyCount();
        if (verifyCount <= 0) {//是否失效
            redisService.delMap(SESSION_SMS_CODE, phone);
            resopnse.setSuccess(false);
            resopnse.setMessage("验证码已失效，请重新获取！");
            return resopnse;
        }
        if (!code.equals(yzm)) {//匹配验证码
            smsVo.setVerifyCount(verifyCount);
            redisService.addMap(SESSION_SMS_CODE, phone, gson.toJson(smsVo));
            resopnse.setSuccess(false);
            resopnse.setMessage("验证码输入有误！");
            return resopnse;
        }
        if ((currentTime - sendCodeTime) > 60000 * 3) {//是否超时 默认3分钟
            resopnse.setSuccess(false);
            resopnse.setMessage("验证码已超时，请重新获取！");
            return resopnse;
        }
        resopnse.setSuccess(true);
        return resopnse;
    }
}
