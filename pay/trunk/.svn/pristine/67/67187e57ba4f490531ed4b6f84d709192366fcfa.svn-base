package com.sandu.pay.controller;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.constant.PlatformConstants;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BasePlatformService;
import com.sandu.pay.common.exception.BizException;
import com.sandu.pay.order.PayModelVo;
import com.sandu.pay.order.metadata.PayModelConstantType;
import com.sandu.pay.order.model.PayModelConfig;
import com.sandu.pay.order.model.PayModelGroupRef;
import com.sandu.pay.order.service.PayModelConfigService;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay.order.vo.PayModelConfigVo;
import com.sandu.user.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/v1/web/pay")
public class PayPackageController2C {

    private Logger logger = LoggerFactory.getLogger(PayPackageController2C.class);

    @Autowired
    private BasePlatformService basePlatformService;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private PayModelGroupRefService payModelGroupRefService;

    @Autowired
    private PayModelConfigService payModelConfigService;

    @ApiOperation(value = "获取包年包月套餐信息", response = ResponseEnvelope.class)
    @RequestMapping(value = "/getPackages", method = RequestMethod.GET)
    public Object getCTerminalPackage(HttpServletRequest request) {
        logger.info("获取C端包年包月套餐begin");
        String platformCode = (String) request.getAttribute("platform-code");

        //校验是否为C端平台
        ResponseEnvelope responseEnvelope = this.checkPlatformIs2C(platformCode);
        if (!responseEnvelope.isSuccess()) {
            logger.error("平台编码不正确");
            return responseEnvelope;
        }
        try {
            List<PayModelConfig> list = payOrderService.getPayModelConfig2C();

            if (list != null && list.size() > 0) {
                list.forEach(l -> {
                    l.setPackagePrice(l.getPackagePrice() / 100);
                });
            }

            responseEnvelope.setObj(list);
            responseEnvelope.setFlag(Boolean.TRUE);
            responseEnvelope.setMessage("请求成功");
            return responseEnvelope;
        } catch (Exception e) {
            logger.error("系统错误", e);
            new ResponseEnvelope<>(true, false);
            return new ResponseEnvelope(false, null, "系统错误");
        }
    }

    @ApiOperation(value = "检验用户是否开通包年包月套餐", response = ResponseEnvelope.class)
    @RequestMapping(value = "/checkRenderGroopRef2C", method = RequestMethod.GET)
    public Object checkRenderGroopRef2C(HttpServletRequest request) {

        //获取平台编码
        String platformCode = (String) request.getAttribute("Platform-Code");
        //校验是否为C端平台
        ResponseEnvelope responseEnvelope = this.checkPlatformIs2C(platformCode);

        if (!responseEnvelope.isSuccess()) {
            return responseEnvelope;
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if (loginUser.getId() == null) {
            responseEnvelope.setSuccess(false);
            responseEnvelope.setMessage("用户信息为空");
            return responseEnvelope;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("state", PayModelConstantType.PACKAGE_YEAR_STATE);
        responseEnvelope.setStatus(Boolean.TRUE);
        responseEnvelope.setMessage("用户具备免费渲染的权限");
        responseEnvelope.setObj(map);
        responseEnvelope.setSuccess(Boolean.TRUE);
        responseEnvelope.setFlag(Boolean.TRUE);
        return responseEnvelope;
    }


    /*@ApiOperation(value = "检验用户是否开通包年包月套餐", response = ResponseEnvelope.class)
    @RequestMapping(value = "/checkRenderGroopRef2C", method = RequestMethod.GET)
    public Object checkRenderGroopRef2C(HttpServletRequest request) {

        //获取平台编码
        String platformCode = (String) request.getAttribute("Platform-Code");
        //校验是否为C端平台
        ResponseEnvelope responseEnvelope = this.checkPlatformIs2C(platformCode);

        if (!responseEnvelope.isSuccess()) {
            return responseEnvelope;
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if (loginUser.getId() == null) {
            responseEnvelope.setSuccess(false);
            responseEnvelope.setMessage("用户信息为空");
            return responseEnvelope;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        int leftTime = 0;
        //检验企业是否买断渲染 => start
        PayModelGroupRef p = payModelGroupRefService.checkCompanyBuyRender(new Long(loginUser.getId()));
        if (p != null) {
            //封装返回参数
            //计算套餐剩余天数
            leftTime = this.transformDay(p.getExpiryTime());
            this.buildReturnMap(map, p.getId(), p.getPayModelConfigId(), p.getExpiryTime(), leftTime);
            map.put("state", PayModelConstantType.PACKAGE_YEAR_STATE);
            responseEnvelope.setStatus(Boolean.TRUE);
            responseEnvelope.setMessage("用户所在企业买断渲染");
            responseEnvelope.setObj(map);
            responseEnvelope.setSuccess(Boolean.TRUE);
            responseEnvelope.setFlag(Boolean.TRUE);
            return responseEnvelope;
        }
        //检验企业是否买断渲染 => end

        //检验用户个人套餐 => start
        List<PayModelVo> payModelVoList = payModelGroupRefService
                .getPayModelVoRenderList2C(loginUser.getId().longValue());

        if (payModelVoList != null && payModelVoList.size() > 0) {
            if (payModelVoList.get(0).getExpiryTime() != null) {
                leftTime = this.transformDay(payModelVoList.get(0).getExpiryTime());
            }
            PayModelConfig payModelConfig = this.buildReturnMap(map, payModelVoList.get(0).getPayModelGroupRefId(),
                    payModelVoList.get(0).getPayModelConfigId(),
                    payModelVoList.get(0).getExpiryTime(), leftTime);

            if (payModelConfig.getTimeType().intValue() == 1) {
                map.put("state", PayModelConstantType.PACKAGE_YEAR_STATE);
            } else if (payModelConfig.getTimeType().intValue() == 0) {
                map.put("state", PayModelConstantType.PACKAGE_MONTH_STATE);
            }
            responseEnvelope.setStatus(Boolean.TRUE);
            responseEnvelope.setMessage("用户具备免费渲染的权限");
            responseEnvelope.setObj(map);
            responseEnvelope.setSuccess(Boolean.TRUE);
            responseEnvelope.setFlag(Boolean.TRUE);
            return responseEnvelope;
        } else {
            map.put("state", PayModelConstantType.NOT_PERMISSION_STATE);
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("用户不具备免费渲染的权限");
            responseEnvelope.setSuccess(Boolean.TRUE);
            responseEnvelope.setObj(map);
            responseEnvelope.setFlag(Boolean.FALSE);
            return responseEnvelope;
        }
    }*/

    private PayModelConfig buildReturnMap(Map<String, Object> map, Integer payModelGroupRefId, Integer payModelConfigId, Date expiryTime, int leftTime) {
        PayModelConfig payModelConfig = payModelConfigService.get(payModelConfigId);
        map.put("payModelGroupRefId", payModelGroupRefId);
        map.put("payModelConfigId", payModelConfig.getId());
        map.put("expiryTime", expiryTime);
        map.put("leftTime", leftTime);
        map.put("isShow", Boolean.FALSE);
        map.put("showMsg", "");
        return payModelConfig;
    }

    private int transformDay(Date expiryTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long time1 = cal.getTimeInMillis();
        cal.setTime(expiryTime);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }


    @RequestMapping("/addRenderOrder2C")
    public Object addRenderOrder2C(String productType, String companyDomainName,
                                   String planId, String houseId,
                                   HttpServletRequest request) {
        logger.info("渲染服务传参=>{}productType,companyDomainName,planId,houseId" + productType + "," + companyDomainName + "," + planId + "," + houseId);
        //由于渲染map是<String,String>,不得不这样接收参数
        PayModelConfigVo payModelConfigVo = this.buildPayModelConfigVo(productType, companyDomainName, planId, houseId);

        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
            //插入订单
            String orderNo = payOrderService.add2CPackagePayOrder(loginUser.getId(), payModelConfigVo, basePlatform);

            logger.info("生成订单成功 orderNo=>{}" + (StringUtils.isNotEmpty(orderNo) ? orderNo : ""));

            if (StringUtils.isNotEmpty(orderNo)) {
                ResponseEnvelope responseEnvelope = new ResponseEnvelope();
                responseEnvelope.setSuccess(true);
                responseEnvelope.setMessage("生成订单成功!");
                responseEnvelope.setObj(orderNo);
                return responseEnvelope;
            }
        } catch (BizException e) {
            return new ResponseEnvelope<>(false, e.getMsg());
        } catch (Exception e) {
            logger.error("系统错误!", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(false, "生成订单失败!");
    }


    /*@RequestMapping("/addRenderOrder2C")
    public Object addRenderOrder2CBak(String productType, String companyDomainName,
                                   String planId, String houseId,
                                   HttpServletRequest request) {
        logger.info("渲染服务传参=>{}productType,companyDomainName,planId,houseId" + productType + "," + companyDomainName + "," + planId + "," + houseId);
        //由于渲染map是<String,String>,不得不这样接收参数
        PayModelConfigVo payModelConfigVo = this.buildPayModelConfigVo(productType, companyDomainName, planId, houseId);

        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        try {
            //检验用户是否已经具备包年包月权限
            boolean flag = payModelGroupRefService.checkUserPackages(loginUser.getId());

            if (flag) {
                BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
                //插入订单
                String orderNo = payOrderService.add2CPackagePayOrder(loginUser.getId(), payModelConfigVo, basePlatform);

                logger.info("生成订单成功 orderNo=>{}" + (StringUtils.isNotEmpty(orderNo) ? orderNo : ""));

                if (StringUtils.isNotEmpty(orderNo)) {
                    ResponseEnvelope responseEnvelope = new ResponseEnvelope();
                    responseEnvelope.setSuccess(true);
                    responseEnvelope.setMessage("生成订单成功!");
                    responseEnvelope.setObj(orderNo);
                    return responseEnvelope;
                }
            }
        } catch (BizException e) {
            return new ResponseEnvelope<>(false, e.getMsg());
        } catch (Exception e) {
            logger.error("系统错误!", e);
            return new ResponseEnvelope<>(false, "系统错误");
        }
        return new ResponseEnvelope<>(false, "生成订单失败!");
    }*/


    private PayModelConfigVo buildPayModelConfigVo(String productType, String companyDomainName, String planId, String houseId) {
        logger.info("渲染服务传参=>{}productType,companyDomainName,planId,houseId" + productType, companyDomainName, planId, houseId);
        PayModelConfigVo payModelConfigVo = new PayModelConfigVo();
        payModelConfigVo.setProductType(productType);
        payModelConfigVo.setCompanyDomainName(companyDomainName);
        payModelConfigVo.setPlanId(Integer.valueOf(planId));
        payModelConfigVo.setHouseId(Integer.valueOf(houseId));
        return payModelConfigVo;
    }


    private ResponseEnvelope checkPlatformIs2C(String platformCode) {
        BasePlatform basePlatformByCode = basePlatformService.getBasePlatformByCode(platformCode);

        if (org.apache.commons.lang3.StringUtils.isEmpty(platformCode)) {
            return new ResponseEnvelope<>(false, "平台编码为空");
        }

        if (basePlatformByCode == null ||
                PlatformConstants.TB.equals(basePlatformByCode.getPlatformBussinessType())) {
            return new ResponseEnvelope<>(false, "平台编码不正确");
        }
        return new ResponseEnvelope(true, basePlatformByCode.getId());
    }
}
