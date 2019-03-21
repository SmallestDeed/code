package com.sandu.pay.controller;

import com.google.gson.Gson;
import com.sandu.common.constant.PayConstant;
import com.sandu.common.constant.PlatformConstants;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.payOrder.ExpenseRecordObject;
import com.sandu.common.objectconvert.payOrder.RechargeIntegralObject;
import com.sandu.common.objectconvert.payOrder.RenderTypeObject;
import com.sandu.common.pay.IdGenerator;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BasePlatformService;
import com.sandu.pay.mgrRecharge.model.MgrRechargeLog;
import com.sandu.pay.order.PayModelVo;
import com.sandu.pay.order.metadata.*;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.PayOrderModel;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.order.model.search.PayOrderSearch;
import com.sandu.pay.order.service.MgrRechargeLogService;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay.order.vo.*;
import com.sandu.product.model.MobileProductReplace;
import com.sandu.render.model.RenderTypeCode;
import com.sandu.render.model.vo.RenderTypeVO;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.SysDictionaryConstant;
import com.sandu.system.model.task.SysTask;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.model.CompanyFranchiserGroup;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.CompanyFranchiserGroupService;
import com.sandu.user.service.SysUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @version V1.0
 * @Title: PayOrderController.java
 * @Package com.sandu.pay.controller
 * @Description:支付-支出凭证Controller
 * @createAuthor pandajun
 * @CreateDate 2016-09-19 16:17:40
 */
@Controller
@RequestMapping("/v1/web/pay/payOrder")
public class PayOrderController {

    //Json转换类
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[支付服务]:";
    private static Logger logger = LogManager.getLogger(PayOrderController.class);

    /**
     * 支付开关
     **/
    private static final Boolean PAYABLE = true;

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MgrRechargeLogService mgrRechargeLogService;
    /**
     * 未支付订单最大允许数
     */
    private static final Integer PAYINGORDERMAX = 10;
    @Resource
    private BasePlatformService basePlatformService;
    @Resource
    private CompanyFranchiserGroupService companyFranchiserGroupService;
    @Resource
    private PayModelGroupRefService payModelGroupRefService;

    /**
     * 获取充值度币数据
     *
     * @return
     * @date 20171017
     * @author xiaoxc
     */
    @ResponseBody
    @RequestMapping(value = "/getRechargeIntegral")
    public Object getRechargeIntegral() {

        //获取数据字典中充值度币的所有数据
        List<SysDictionary> sysDictionaryList = sysDictionaryService.findAllByType(SysDictionaryConstant.PAY_INTEGRAL_RECHARGE);
        logger.info(CLASS_LOG_PREFIX + "获取充值度币配置数据->获取数据字典中充值度币的所有数据完成sysDictionaryList:{}", gson.toJson(sysDictionaryList));

        //初始化返回数据
        List<RechargeIntegralVo> rechargeIntegralVoList = new ArrayList<>(null == sysDictionaryList ? 0 : sysDictionaryList.size());

        for (SysDictionary dictionary : sysDictionaryList) {
            //装入返回集
            rechargeIntegralVoList.add(RechargeIntegralObject.parseToRechargeIntegralVoBySysDictionary(dictionary));
        }

        logger.info(CLASS_LOG_PREFIX + "获取充值度币配置数据完成->List<RechargeIntegralVo>", gson.toJson(rechargeIntegralVoList));

        return new ResponseEnvelope(Boolean.TRUE, "", rechargeIntegralVoList);
    }

    /**
     * 扫码在线充值度币--运营网站
     */
    @RequestMapping(value = "/addscanrechargeIntegral")
    @ResponseBody
    public Object addscanrechargeIntegral(@ModelAttribute PayOrderModel payOrderModel, HttpServletRequest request) throws Exception {

        ResponseEnvelope responseEnvelope = new ResponseEnvelope();

        if (payOrderModel == null) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param is null ,please resubmit!");
            return responseEnvelope;
        }
        String payType = payOrderModel.getPayType();
        Integer productId = payOrderModel.getProductId();
        if (!PAYABLE) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("pay function has been closed!");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payType)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param payType is null ,please resubmit!!");
            return responseEnvelope;
        }

        SysDictionary sysDictionary = sysDictionaryService.get(productId);
        if (sysDictionary == null) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param productId error ,please resubmit!!");
            return responseEnvelope;
        }

        SysUser sysUser = null;
        Long userId = (Long) request.getAttribute("tokenUserId");
        if (null != userId) {
            sysUser = sysUserService.get(userId.intValue());
        } else {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param userId is null ,please resubmit!!");
            return responseEnvelope;
        }

        LoginUser loginUser = null;

        if (null != sysUser) {
            loginUser = new LoginUser(userId.intValue(), sysUser.getUserType(), sysUser.getUserName(), sysUser.getMobile());
        } else {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("The user information does not exist ,please resubmit!!");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        //获取充值二维码

        ResultMessage message = payOrderService.getRechargeQrCodePath(productId, 0, payType, loginUser, sysDictionary, platformCode);

        if (message != null) {
            if (message.isSuccess()) {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setObj(message.getObj());
                responseEnvelope.setMessage(message.getMessage());
            } else {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setMessage(message.getMessage());
            }
        }

        return responseEnvelope;
    }


    /**
     * 度币充值----2c移动端(APP支付)-----------------
     *
     * @param payOrderModel
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/rechargeIntegralByAppPay")
    @ResponseBody
    public Object rechargeIntegralByAppPay(@RequestBody PayOrderModel payOrderModel, HttpServletRequest request) throws Exception {

        ResponseEnvelope responseEnvelope = new ResponseEnvelope();

        if (payOrderModel == null) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param is null ,please resubmit!");
            return responseEnvelope;
        }
        String payType = payOrderModel.getPayType();
        Integer productId = payOrderModel.getProductId();
        if (!PAYABLE) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("pay function has been closed!");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payType)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param payType is null ,please resubmit!!");
            return responseEnvelope;
        }

        SysDictionary sysDictionary = sysDictionaryService.get(productId);
        if (sysDictionary == null) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param productId error ,please resubmit!!");
            return responseEnvelope;
        }

        SysUser sysUser = null;
        Long userId = (Long) request.getAttribute("tokenUserId");
        if (null != userId) {
            sysUser = sysUserService.get(userId.intValue());
        } else {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param userId is null ,please resubmit!!");
            return responseEnvelope;
        }

        LoginUser loginUser = null;

        if (null != sysUser) {
            loginUser = new LoginUser(userId.intValue(), sysUser.getUserType(), sysUser.getUserName(), sysUser.getMobile());
        } else {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("The user information does not exist ,please resubmit!!");
            return responseEnvelope;
        }

        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        ResultMessage message = payOrderService.rechargeIntegralByAppPay(productId, 0, payType, loginUser, sysDictionary, platformCode);
        if (message != null) {
            if (message.isSuccess()) {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setObj(message.getObj());
                responseEnvelope.setMessage(message.getMessage());
            } else {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setMessage(message.getMessage());
            }
        }

        return responseEnvelope;
    }

    /**
     * 扫码在线充值金额（运营网站）
     *
     * @param totalFee
     * @param adjustFee
     * @param payType
     * @param productId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addscanrecharge")
    @ResponseBody
    public Object addScanRecharge(
            @RequestParam(value = "totalFee", required = true, defaultValue = "0") double totalFee,
            @RequestParam(value = "adjustFee", required = false, defaultValue = "0") double adjustFee,
            @RequestParam(value = "payType", required = true) String payType,
            @RequestParam(value = "productId", required = true) Integer productId, HttpServletRequest request) throws Exception {

        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (!PAYABLE) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("pay function has been closed!");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payType)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param payType is null ,please resubmit!!");
            return responseEnvelope;
        }

        if (totalFee > -0.000001 && totalFee < +0.000001) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param totalFee is zero ,please resubmit!!");
            return responseEnvelope;
        }
        SysDictionary sysDictionary = sysDictionaryService.get(productId);
        if (sysDictionary == null) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param productId error ,please resubmit!!");
            return responseEnvelope;
        }
        if ((int) totalFee != sysDictionary.getValue().intValue()) {
            logger.info("充值金额：" + totalFee + ";实际充值金额：" + sysDictionary.getValue());
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("recharge amount is wrong,please resubmit!!");
            return responseEnvelope;
        }
        if ((int) adjustFee != Utils.getIntValue(sysDictionary.getAtt1().trim())) {
            logger.error("赠送金额：" + adjustFee + ";实际赠送金额：" + sysDictionary.getAtt1());
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("give away amount is wrong,please resubmit!!");
            return responseEnvelope;
        }

        SysUser sysUser = null;
        LoginUser loginUser = null;
        Long userId = (Long) request.getAttribute("tokenUserId");
        if (null != userId) {
            if (null != userId) {
                sysUser = sysUserService.get(userId.intValue());
            } else {
                responseEnvelope.setStatus(Boolean.FALSE);
                responseEnvelope.setMessage("Param userId is null ,please resubmit!!");
                return responseEnvelope;
            }

            if (null != sysUser) {
                loginUser = new LoginUser(userId.intValue(), sysUser.getUserType(), sysUser.getUserName(), sysUser.getMobile());
            } else {
                responseEnvelope.setStatus(Boolean.FALSE);
                responseEnvelope.setMessage("The user information does not exist ,please resubmit!!");
                return responseEnvelope;
            }
        } else {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param userId is null ,please resubmit!!");
            return responseEnvelope;
        }

        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        //获取充值二维码

        ResultMessage message = payOrderService.getRechargeQrCodePath((int) totalFee, (int) adjustFee, payType, loginUser, sysDictionary, platformCode);

        if (message != null) {
            if (message.isSuccess()) {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setObj(message.getObj());
                responseEnvelope.setMessage(message.getMessage());
            } else {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setMessage(message.getMessage());
            }
        }
        return responseEnvelope;
    }

    /**
     * 扫码支付购买产品
     */
    @RequestMapping(value = "/addscanpay")
    @ResponseBody
    public Object addScanPayOrder(@RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
                                  @RequestParam(value = "productType", required = true) String productType,
                                  @RequestParam(value = "productName", required = true) String productName,
                                  @RequestParam(value = "productDesc", required = true) String productDesc,
                                  @RequestParam(value = "payType", required = true) String payType, String msgId,
                                  @RequestParam(value = "totalFee", required = false, defaultValue = "0") Integer totalFee,
                                  HttpServletRequest request) throws Exception {

        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (Utils.isBlank(payType)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param payType is null ,please resubmit!");
            return responseEnvelope;
        }
        if (Utils.isBlank(productDesc)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param productDesc is null ,please resubmit!");
            return responseEnvelope;
        }
        if (Utils.isBlank(productName)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param productName is null ,please resubmit!");
            return responseEnvelope;
        }
        if (Utils.isBlank(productType)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param productType is null ,please resubmit!");
            return responseEnvelope;
        }
        if (!PAYABLE) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("pay function has been closed!");
            return responseEnvelope;
        }

        Long userId = (Long) request.getAttribute("tokenUserId");
        LoginUser loginUser = null;

        if (null != userId) {
            SysUser sysUser = null;

            if (null != userId) {
                sysUser = sysUserService.get(userId.intValue());
            } else {
                responseEnvelope.setStatus(Boolean.FALSE);
                responseEnvelope.setMessage("Param userId is null ,please resubmit!!");
                return responseEnvelope;
            }


            if (null != sysUser) {
                loginUser = new LoginUser(userId.intValue(), sysUser.getUserType(), sysUser.getUserName(), sysUser.getMobile());
            } else {
                responseEnvelope.setStatus(Boolean.FALSE);
                responseEnvelope.setMessage("The user information does not exist ,please resubmit!!");
                return responseEnvelope;
            }

        } else {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param userId is null ,please resubmit!!");
            return responseEnvelope;
        }

        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        //获取扫码支付信息(生成二维码信息)

        ResultMessage message = payOrderService.getPayScanOrderUrlInfo(totalFee, payType, productId, productType, productName, productDesc, loginUser, msgId, platformCode);

        if (message != null) {
            if (message.isSuccess()) {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setObj(message.getObj());
                responseEnvelope.setMessage(message.getMessage());
            } else {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setMessage(message.getMessage());
            }
        }

        return responseEnvelope;
    }


    /**
     * 度币支付购买产品
     */
    @RequestMapping(value = "/addpredepositpay")
    @ResponseBody
    public Object addPredepositPay(
            @RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
            @RequestParam(value = "productType", required = true) String productType,
            @RequestParam(value = "productName", required = true) String productName,
            @RequestParam(value = "productDesc", required = true) String productDesc,
            @RequestParam(value = "totalFee", required = false, defaultValue = "0") Integer totalFee,
            @RequestParam(value = "msgId", required = false) String msgId,
            HttpServletRequest request) throws Exception {

        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (Utils.isBlank(productDesc)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param productDesc is null ,please resubmit!");
            return responseEnvelope;
        }
        if (Utils.isBlank(productName)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param productName is null ,please resubmit!");
            return responseEnvelope;
        }
        if (Utils.isBlank(productType)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param productType is null ,please resubmit!");
            return responseEnvelope;
        }
        if (!PAYABLE) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("pay function has been closed!");
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        ResultMessage message = null;
        if (null != userId) {
            //余额支付
            message = payOrderService.getAddPredepositPay(userId.intValue(), totalFee, PayType.PREDEPOSIT, productId, productType, productName, productDesc, platformCode);
        } else {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMsgId(msgId);
            responseEnvelope.setMessage("Param userId is null ,please resubmit!!");
            return responseEnvelope;
        }
        if (message != null) {
            if (message.isSuccess()) {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setObj(message.getObj());
                responseEnvelope.setMessage(message.getMessage());
                responseEnvelope.setMsgId(msgId);
            } else {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setMessage(message.getMessage());
                responseEnvelope.setMsgId(msgId);
            }
        }
        return responseEnvelope;
    }

    /**
     * 运维网站生成订单
     *
     * @param userId
     * @param renderType
     * @param planId
     * @param houseId
     * @return
     */
    @RequestMapping(value = "/addIntegralPayRenderTask")
    @ResponseBody
    public Object addIntegralPayRenderTask(
            @RequestParam(value = "renderType", required = false) String renderType,
            @RequestParam(value = "planId", required = true) Integer planId,
            @RequestParam(value = "houseId", required = false) Integer houseId,
            @RequestParam(value = "userId", required = true) Integer userId,
            String companyDomainName,
            HttpServletRequest request) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        Integer temp = 0;
        logger.info("C端渲染类型为renderType =>{}" + renderType);
        if ("common_render".equals(renderType)) {
            temp = 1;
        } else if ("panorama_render".equals(renderType)) {
            temp = 4;
        } else if ("roam720".equals(renderType)) {
            temp = 8;
        } else if ("video".equals(renderType)) {
            temp = 6;
        } else if ("full_house_render_name".equals(renderType)) {
            temp = 10;
        } else {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("渲染类型不正确!");
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        SysUser sysUser = sysUserService.get(userId.intValue());
        if (null == sysUser) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(sysUser, basePlatform, companyDomainName);
        ResultMessage message = new ResultMessage();
        logger.info("运营网站c端开始渲染：companyDomainName：" + companyDomainName + "用户id：" + sysUser.getId());
        if (null == payModelVoList || payModelVoList.size() == 0) {
            // 扣度币渲染
            message = payOrderService.addIntegralPayRenderTask(userId.intValue(), temp, planId, houseId, platformCode);
        } else {
            // 企业已购买，可以免费渲染
            logger.info("运营网站c端开始渲染：companyDomainName：" + companyDomainName + "用户id：" + sysUser.getId() + "可以免费渲染");
            message = payOrderService.addCompanyPayRenderTask(sysUser, temp, planId, houseId, basePlatform);
        }
        if (message != null) {
            if (message.isSuccess()) {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setObj(message.getOrderNo());
                responseEnvelope.setMessage(message.getMessage());
            } else {
                responseEnvelope.setStatus(message.isSuccess());
                responseEnvelope.setMessage(message.getMessage());
            }
        }
        return responseEnvelope;
    }

    /**
     * 个人消费记录列表
     *
     * @param request
     * @param pageModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findExpenseRecordList")
    public Object findExpenseRecordList(@ModelAttribute PageModel pageModel, HttpServletRequest request,
                                        @RequestParam(value = "msgId", required = false) String msgId) {

        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        List<PayOrder> payOrderlist = new ArrayList<>();
        PayOrderSearch payOrderSearch = new PayOrderSearch();

        Long userId = (Long) request.getAttribute("tokenUserId");

        if (null != userId) {
            //只有支付成功的订单消费记录，排除序列号的订单，sql处理
            payOrderSearch.setUserId(userId.intValue());
        } else {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("Param userId is null ,please resubmit!!");
            return responseEnvelope;
        }
        SysUser sysUser = sysUserService.get(userId.intValue());
        if (sysUser == null) {
            responseEnvelope.setSuccess(false);
            responseEnvelope.setMessage("用户信息为空");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (StringUtils.isEmpty(platformCode)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("平台编码：platformCode{}为空");
            return responseEnvelope;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            return responseEnvelope;
        }
        payOrderSearch.setPlatformCode(platformCode);

        payOrderSearch.setPayState(PayState.SUCCESS);
        int total = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
        if (null == companyFranchiserGroup) {
            //total = payOrderService.getExpenseRecordCount(payOrderSearch);
            /**
             * edit by qiutq
             */
            total = payOrderService.getExpenseRecordCountV2(payOrderSearch);
            if (total > 0) {
                if (null != pageModel && 0 != pageModel.getPageSize()) {
                    payOrderSearch.setStart((pageModel.getCurPage() == 0 ? 0 : pageModel.getCurPage() - 1) * pageModel.getPageSize());
                    payOrderSearch.setLimit(pageModel.getPageSize());
                } else {
                    //加载默认分页参数
                    payOrderSearch.setLimit(PageModel.DEFAULT_PAGE_PAGESIZE);
                }
                //payOrderlist = payOrderService.getExpenseRecordList(payOrderSearch);
                /**
                 * edit by qiutq
                 */
                payOrderlist = payOrderService.getExpenseRecordListV2(payOrderSearch);
            }
        } else {
            payOrderSearch.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
            payOrderSearch.setFranchiserGroupId(sysUser.getFranchiserGroupId());
            total = payOrderService.getShareCountPc(payOrderSearch);
            if (total > 0) {
                if (null != pageModel && 0 != pageModel.getPageSize()) {
                    payOrderSearch.setStart((pageModel.getCurPage() == 0 ? 0 : pageModel.getCurPage() - 1) * pageModel.getPageSize());
                    payOrderSearch.setLimit(pageModel.getPageSize());
                } else {
                    //加载默认分页参数
                    payOrderSearch.setLimit(PageModel.DEFAULT_PAGE_PAGESIZE);
                }
                payOrderlist = payOrderService.getSharePaginatedListPc(payOrderSearch);
            }
        }

        List<ExpenseRecordVo> expenseRecordList = ExpenseRecordObject.parseToExpenseRecordVoByPayOrder(payOrderlist);
        responseEnvelope.setStatus(Boolean.TRUE);
        responseEnvelope.setObj(expenseRecordList);
        responseEnvelope.setTotalCount(total);
        responseEnvelope.setMsgId(msgId);

        return responseEnvelope;
    }


    /**
     * 生成渲染订单（移动端）
     *
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/replaceRecord")
    @ResponseBody
    public Object replaceRecord(@ModelAttribute MobileProductReplace model
            , HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            return responseEnvelope;
        }
        if (!basePlatform.getPlatformBussinessType().equals(PlatformConstants.TB)) {
            if (StringUtils.isBlank(model.getCompanyDomainName())) {
                responseEnvelope.setStatus(Boolean.FALSE);
                responseEnvelope.setMessage("参数companyDomainName为空");
                return responseEnvelope;
            }
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(user, basePlatform, model.getCompanyDomainName());
        if (null == payModelVoList || payModelVoList.size() == 0) {
            //判断用户是否具备共享度币的权限
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
            if (null == companyFranchiserGroup) {
                //个人消费
                return payOrderService.replaceRecord(model, platformCode);
            } else {
                // 度币共享
                ResultMessage resultMessage = payOrderService.addIntegralShareRenderOrder(basePlatform.getId(), userId.intValue(), model);
                responseEnvelope.setStatus(resultMessage.isSuccess());
                responseEnvelope.setMessage(resultMessage.getMessage());
                responseEnvelope.setObj(resultMessage.getObj());
                responseEnvelope.setSuccess(resultMessage.isSuccess());
                responseEnvelope.setFlag(resultMessage.isSuccess());
                return responseEnvelope;
            }
        } else {
            // 企业赠送
            PayModelConfigVo payModelConfigVo = new PayModelConfigVo();
            payModelConfigVo.setProductType(model.getProductType());
            ResultMessage resultMessage = payOrderService.addRenderOrderFree(basePlatform, user, payModelConfigVo);
            responseEnvelope.setStatus(resultMessage.isSuccess());
            responseEnvelope.setMessage(resultMessage.getMessage());
            responseEnvelope.setObj(resultMessage.getObj());
            responseEnvelope.setSuccess(resultMessage.isSuccess());
            responseEnvelope.setFlag(resultMessage.isSuccess());
            return responseEnvelope;
        }

    }

    /**
     * 根据订单号回写 任务id
     * task_id  任务id
     * order_no 订单号
     *
     * @author wutehua
     */
    @RequestMapping(value = "/updatePayoOrder")
    @ResponseBody
    public Object updatePayoOrder(@RequestParam(value = "orderNo") String orderNo,
                                  @RequestParam(value = "taskId") Integer taskId) {
        boolean fal = payOrderService.updatePayoOrder(orderNo, taskId);
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (fal) {
            responseEnvelope.setMessage("修改订单成功");
        } else {
            responseEnvelope.setMessage("修改订单失败");
        }
        return responseEnvelope;
    }

    /**
     * 通知退款或成功更新状态，非官方退款
     * task_id  任务id
     * order_no 订单号
     *
     * @author wutehua
     */
    @RequestMapping(value = "/notifyRefund")
    @ResponseBody
    public Object notifyRefund(@RequestParam(value = "orderNo") String orderNo,

                               @RequestParam(value = "userId") Integer userId, HttpServletRequest request) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        logger.info(CLASS_LOG_PREFIX + "notifyRefund:{}", "orderNo:" + orderNo);
        logger.info("调用退款接口start；用户id：userId:{}" + userId);
        ResultMessage message = payOrderService.notifyRefund(orderNo, userId);
        logger.info("调用退款接口end;" + message.getMessage());
        if (message != null) {
            if (message.isSuccess()) {
                responseEnvelope.setMessage(message.getMessage());
            } else {
                responseEnvelope.setMessage(message.getMessage());
            }
        }
        logger.info(CLASS_LOG_PREFIX + "notifyRefund finish");
        return responseEnvelope;
    }


    /**
     * 获取渲染类型数据字典
     *
     * @return
     * @date 20171023
     * @author xiaoxc
     */
    @ResponseBody
    @RequestMapping(value = "/getRenderType")
    public Object getRenderType() {

        //获取数据字典中渲染类型的所有数据
        List<SysDictionary> sysDictionaryList = sysDictionaryService.findAllByType(SysDictionaryConstant.PLATFROM_RENDER_TYPE);
        logger.info(CLASS_LOG_PREFIX + "获取渲染类型分配置数据->获取数据字典中渲染类型的所有数据完成sysDictionaryList:{}", gson.toJson(sysDictionaryList));

        //初始化返回数据
        List<RenderTypeVO> RenderTypeVoList = new ArrayList<>(null == sysDictionaryList ? 0 : sysDictionaryList.size());

        for (SysDictionary dictionary : sysDictionaryList) {

            if ("高清图片".equals(dictionary.getName())) {
                //提出图片渲染
                continue;
            }

            if ("full_house_render_name".equals(dictionary.getValuekey())) {
                continue;
            }

            //装入返回集
            RenderTypeVoList.add(RenderTypeObject.parseToRenderTypeVOBySysDictionary(dictionary));
        }

        logger.info(CLASS_LOG_PREFIX + "获取渲染类型分配置数据完成->List<RenderTypeVO>", gson.toJson(RenderTypeVoList));

        return new ResponseEnvelope(Boolean.TRUE, "", RenderTypeVoList);
    }

    /**
     * 移动端用户开通移动端登录（微信app支付-移动端）
     *
     * @param payMobileLoginVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMobileLoginAppPay")
    public Object addMobileLoginAppPay(HttpServletRequest request, @RequestBody PayMobileLoginVo payMobileLoginVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (null == payMobileLoginVo || null == payMobileLoginVo.getUserId() ||
                StringUtils.isEmpty(payMobileLoginVo.getPayType())) {
            logger.info("支付参数为空：payMobileLoginVo:{}" + ((null == payMobileLoginVo) ? "null" : payMobileLoginVo.toString()));
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付参数为空!");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        ResultMessage message = payOrderService.unifiedOrderForWxAppPay(payMobileLoginVo, platformCode);
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        return responseEnvelope;
    }

    /**
     * 移动端用户开通移动端登录或者延期登录时间（微信h5支付-移动端）
     *
     * @param payMobileLoginVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMobileLoginPay")
    public Object addMobileLoginPay(HttpServletRequest request, @RequestBody PayMobileLoginVo payMobileLoginVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (null == payMobileLoginVo || null == payMobileLoginVo.getUserId() ||
                StringUtils.isEmpty(payMobileLoginVo.getPayType())) {
            logger.info("支付参数为空：payMobileLoginVo:{}" + ((null == payMobileLoginVo) ? "null" : payMobileLoginVo.toString()));
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付参数为空!");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        ResultMessage message = payOrderService.getMobilePostponeWeChatPayUrl(payMobileLoginVo, platformCode);
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        return responseEnvelope;
    }

    /**
     * 移动端度币支付开通移动端登录或者延期登录时间（度币支付-移动端）
     *
     * @param payMobileLoginVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMobileLoginIntegralPay")
    public Object addMobileLoginIntegralPay(HttpServletRequest request, @RequestBody PayMobileLoginVo payMobileLoginVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (null == payMobileLoginVo || null == payMobileLoginVo.getUserId() ||
                StringUtils.isEmpty(payMobileLoginVo.getPayType())) {
            logger.info("支付参数为空：payMobileLoginVo:{}" + ((null == payMobileLoginVo) ? "null" : payMobileLoginVo.toString()));
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付参数为空!");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("暂未找到平台id对应的平台信息:basePlatform:{}" + (null == basePlatform ? null : basePlatform.toString()));
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            return responseEnvelope;
        }
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(payMobileLoginVo.getUserId());
        if (null == companyFranchiserGroup) {
            ResultMessage message = payOrderService.addMobileLoginIntegralPay(payMobileLoginVo, platformCode);
            responseEnvelope.setStatus(message.isSuccess());
            responseEnvelope.setMessage(message.getMessage());
            responseEnvelope.setObj(message.getObj());
            responseEnvelope.setSuccess(message.isSuccess());
            return responseEnvelope;
        } else {
            if (!PlatformConstants.TB.equals(basePlatform.getPlatformBussinessType())) {
                responseEnvelope.setStatus(Boolean.FALSE);
                return responseEnvelope;
            }
            payMobileLoginVo.setPayType(PayType.INTEGRAL_SHARE_PAY); // 支付方式
            ResultMessage resultMessage = payOrderService.addIntegralShareLoginPay(basePlatform.getId(), payMobileLoginVo);
            responseEnvelope.setStatus(resultMessage.isSuccess());
            responseEnvelope.setMessage(resultMessage.getMessage());
            responseEnvelope.setObj(resultMessage.getObj());
            responseEnvelope.setSuccess(resultMessage.isSuccess());
            responseEnvelope.setFlag(resultMessage.isSuccess());
            return responseEnvelope;
        }

    }


    /**
     * 移动端手机支付宝APP支付开通移动端登录（支付宝App支付-移动端）------------------------App支付宝支付
     *
     * @param payMobileLoginVo
     */
    @ResponseBody
    @RequestMapping(value = "/addMobileLoginAppAlipay")
    public Object addMobileLoginAppAlipay(HttpServletRequest request, @RequestBody PayMobileLoginVo payMobileLoginVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (null == payMobileLoginVo || null == payMobileLoginVo.getUserId() ||
                StringUtils.isEmpty(payMobileLoginVo.getPayType())) {
            logger.info("支付参数为空：payMobileLoginVo:{}" + ((null == payMobileLoginVo) ? "null" : payMobileLoginVo.toString()));
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付参数为空!");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        ResultMessage message = payOrderService.addPostponeAppAlipay(payMobileLoginVo, platformCode);
        logger.info("结果为：" + message.toString());
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        return responseEnvelope;
    }


    /**
     * 移动端手机支付宝网页支付开通移动端登录（支付宝手机网页支付-移动端）
     *
     * @param payMobileLoginVo
     */
    @ResponseBody
    @RequestMapping(value = "/addPostponeAlipay")
    public Object addPostponeAlipay(HttpServletRequest request, @RequestBody PayMobileLoginVo payMobileLoginVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (null == payMobileLoginVo || null == payMobileLoginVo.getUserId() ||
                StringUtils.isEmpty(payMobileLoginVo.getPayType())) {
            logger.info("支付参数为空：payMobileLoginVo:{}" + ((null == payMobileLoginVo) ? "null" : payMobileLoginVo.toString()));
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付参数为空!");
            return responseEnvelope;
        }

        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        ResultMessage message = payOrderService.addPostponeAlipay(payMobileLoginVo, platformCode);
        logger.info("结果为：" + message.toString());
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        return responseEnvelope;
    }


    //---------------------------PC专用-------------------------------

    /**
     * @param msgId
     * @param type
     * @return
     * @Description: 5.6版本获取支付通道信息（支付模式及对应价格）---pc端专用接口
     * @author
     */
    @RequestMapping(value = "/getRenderChannelInfoPc")
    @ResponseBody
    public Object getRenderChannelInfoPc(String msgId, Integer renderingType, String type) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        responseEnvelope.setMsgId(msgId);
        if (StringUtils.isEmpty(msgId)) {
            return new ResponseEnvelope<>(false, "缺少参数msgId", msgId);
        }
        if (renderingType == null) {
            return new ResponseEnvelope<>(false, "缺少参数renderingType", msgId);
        } else {

        }
        //查询渲染免费信息
        RenderCheckVo renderFreeTimeInfo = payOrderService.renderFreeTimeInfo();
        if (renderFreeTimeInfo == null) {
            logger.error("获取渲染价格接口getRenderChannelInfo：未查出免费时间信息");
        } else {
        }
        //根据渲染类型查询价格列表
        List<RenderPriceInfoVo> priceInfoList = payOrderService.findPriceInfo(renderingType);

        if (priceInfoList == null || priceInfoList.size() < 1) {
            responseEnvelope.setSuccess(false);
            responseEnvelope.setMessage("获取价格信息失败");
            responseEnvelope.setMsgId(msgId);
            responseEnvelope.setTotalCount(0);
            responseEnvelope.setDatalist(priceInfoList);
            logger.error("未从数据字典中查询到渲染价格信息");
            return responseEnvelope;
        } else {
            renderFreeTimeInfo.setPriceList(priceInfoList);
            responseEnvelope.setObj(renderFreeTimeInfo);
            responseEnvelope.setSuccess(true);
            responseEnvelope.setMsgId(msgId);
            responseEnvelope.setMessage("成功");
            responseEnvelope.setTotalCount(priceInfoList.size());
            return responseEnvelope;
        }
    }


    /**
     * 个人消费记录列表---pc专用
     *
     * @param payOrderSearch
     * @param msgId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findExpenseRecordListPc")
    public Object findExpenseRecordListPc(@ModelAttribute("payOrderSearch") PayOrderSearch payOrderSearch,
                                          @RequestParam(value = "msgId", required = false) String msgId,
                                          HttpServletRequest request) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        responseEnvelope.setMsgId(msgId);
        if (StringUtils.isEmpty(msgId)) {
            responseEnvelope.setSuccess(false);
            responseEnvelope.setMessage("参数msgId为空!");
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (StringUtils.isEmpty(platformCode)) {
            responseEnvelope.setSuccess(false);
            responseEnvelope.setMessage("平台编码：platformCode{}为空");
            return responseEnvelope;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setSuccess(false);
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser sysUser = sysUserService.get(userId.intValue());
        if (sysUser == null) {
            responseEnvelope.setSuccess(false);
            logger.info("用户信息为空");
            responseEnvelope.setMessage("用户信息为空");
            return responseEnvelope;
        }
        //只有支付成功的订单消费记录，排除序列号的订单，sql处理
        payOrderSearch.setUserId(userId.intValue());
        payOrderSearch.setPlatformCode(platformCode);
        payOrderSearch.setPayState(PayState.SUCCESS);
        List<PayOrder> list = new ArrayList<PayOrder>();
        int total = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
        if (null == companyFranchiserGroup) {
            try {
                total = payOrderService.getCount(payOrderSearch);
                if (total > 0) {
                    list = payOrderService.getPaginatedList(payOrderSearch);
                    for (PayOrder payOrder : list) {
                        payOrder.setTotalFee((payOrder.getTotalFee() + payOrder.getAdjustFee()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEnvelope<PayOrder>(false, "数据异常!", msgId);
            }
        } else {
            payOrderSearch.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
            payOrderSearch.setFranchiserGroupId(sysUser.getFranchiserGroupId());
            try {
                total = payOrderService.getShareCount(payOrderSearch);
                if (total > 0) {
                    list = payOrderService.getSharePaginatedList(payOrderSearch);
                    for (PayOrder payOrder : list) {
                        payOrder.setTotalFee((payOrder.getTotalFee() + payOrder.getAdjustFee()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEnvelope<PayOrder>(false, "数据异常!", msgId);
            }
        }
        responseEnvelope.setTotalCount(total);
        responseEnvelope.setDatalist(list);
        responseEnvelope.setMessage("查询成功");
        responseEnvelope.setMsgId(msgId);
        return responseEnvelope;
    }


    /**
     * 5.6版本：预付款支付购买产品（度币付款，包含生成渲染订单）
     *
     * @param productId
     * @param productType
     * @param productName
     * @param productDesc
     * @param totalFee
     * @param mode
     * @param type
     * @param isTurnOn
     * @param planId
     * @param viewPoint
     * @param scene
     * @param msgId
     * @param request
     * @param response
     * @param renderingType
     * @param level
     * @param priority
     * @param temporaryPic
     * @param orderNo
     * @param authorization
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addpredepositpayPc")
    @ResponseBody
    public Object addPredepositPayNewPc(
            @RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
            @RequestParam(value = "productType", required = true) String productType,
            @RequestParam(value = "productName", required = true) String productName,
            @RequestParam(value = "productDesc", required = true) String productDesc,
            @RequestParam(value = "totalFee", required = false, defaultValue = "0") Integer totalFee,
            Integer mode, String type, Integer isTurnOn, Integer planId, Integer viewPoint, Integer scene,
            String msgId, HttpServletRequest request, HttpServletResponse response, String renderingType,

            String level, Integer priority, String temporaryPic, String orderNo, String authorization) throws Exception {
        ResultMessage message = new ResultMessage();
        message.setMsgId(msgId);
        Long userId = (Long) request.getAttribute("tokenUserId");
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (StringUtils.isEmpty(platformCode)) {
            logger.info("平台编码：platformCode{}为空" + platformCode);
            message.setMessage("平台编码：platformCode{}为空");
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        ResultMessage resultMessage = payOrderService.addpredepositpayPc(productId, productType, productName, productDesc, totalFee, mode, type,
                isTurnOn, planId, viewPoint, scene, msgId, renderingType, basePlatform, level, priority,
                temporaryPic, orderNo, authorization, userId.intValue(), PAYINGORDERMAX);
        resultMessage.setMsgId(msgId);
        return resultMessage;
    }

    /**
     * pc端充值（微信和支付宝扫码支付）
     *
     * @param totalFee  总价钱
     * @param adjustFee 赠送金额
     * @param payType   支付类型
     * @param productId 产品类型
     * @param msgId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addscanrechargePc")
    @ResponseBody
    public Object addScanRechargePc(
            @RequestParam(value = "totalFee", required = true, defaultValue = "0") double totalFee,
            @RequestParam(value = "adjustFee", required = false, defaultValue = "0") double adjustFee,
            @RequestParam(value = "payType", required = true) String payType,
            @RequestParam(value = "productId", required = true) Integer productId, String msgId,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = new ResultMessage();
        message.setMsgId(msgId);
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (StringUtils.isEmpty(platformCode)) {
            logger.info("平台编码：platformCode{}为空" + platformCode);
            message.setMessage("平台编码：platformCode{}为空");
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        message = payOrderService.addscanrechargePc(totalFee, adjustFee, payType, productId, msgId, userId.intValue(), basePlatform);
        message.setMsgId(msgId);
        return message;
    }

    /**
     * 扫码支付购买产品
     */
    @RequestMapping(value = "/addscanpayPc")
    @ResponseBody
    public Object addScanPayOrderPc(@RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId,
                                    @RequestParam(value = "productType", required = true) String productType,
                                    @RequestParam(value = "productName", required = true) String productName,
                                    @RequestParam(value = "productDesc", required = true) String productDesc,
                                    @RequestParam(value = "payType", required = true) String payType,
            /*@RequestParam(value = "userId", required = false, defaultValue = "0") Integer userId,*/
                                    String msgId,
                                    @RequestParam(value = "totalFee", required = false, defaultValue = "0") Integer totalFee,
                                    HttpServletRequest request, HttpServletResponse response,
                                    // 渲染任务参数 ->start
                                    String level, Integer isTurnOn, Integer planId, Integer priority, Integer viewPoint,
                                    Integer scene, Integer renderingType, String temporaryPic, String orderNo, String type,
                                    Integer mode,// 数据字典value
                                    // 渲染任务参数 ->end
                                    // 登录验证参数 ->start
                                    String authorization
                                    // 登录验证参数 ->end
    ) throws Exception {
        ResultMessage message = new ResultMessage();
        message.setMsgId(msgId);
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (StringUtils.isEmpty(platformCode)) {
            logger.info("平台编码：platformCode{}为空" + platformCode);
            message.setMessage("平台编码：platformCode{}为空");
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        LoginUser loginUser = null;
        if (null != user) {
            loginUser = new LoginUser(userId.intValue(), user.getUserType(), user.getUserName(), user.getMobile());
        } else {
            message.setMessage("没有找到该用户信息");
            return message;
        }

        if (!StringUtils.equals("common_render", productType) && !StringUtils.equals("panorama_render", productType)
                && !StringUtils.equals("HD_render", productType) && !StringUtils.equals("UHD_render", productType)
                && !StringUtils.equals("video", productType) && !StringUtils.equals("roam720", productType)
                ) {

            //	LoginUser loginUser = getCurrentLoginUser(request);

            if (Utils.isBlank(payType)) {
                message.setMessage("参数payType不能为空");
                return message;
            }
            if (Utils.isBlank(productDesc)) {
                message.setMessage("参数productDesc不能为空");
                return message;
            }
            if (Utils.isBlank(productName)) {
                message.setMessage("参数productName不能为空");
                return message;
            }
            if (Utils.isBlank(productType)) {
                message.setMessage("参数productType不能为空");
                return message;
            }
            if (!PAYABLE) {
                message.setMessage("支付功能已经关闭.");
                message.setSuccess(false);
                return message;
            }
            //获取扫码支付信息(生成二维码信息)

            message = payOrderService.getPayScanOrderUrlInfo(totalFee, payType, productId, productType, productName, productDesc, loginUser, msgId, platformCode);
            message.setMsgId(msgId);

            return message;
        } else {
            message.setMsgId(msgId);

            // 验证登录参数 ->end

            if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
                    || Utils.isBlank(productType)) {
                message.setMessage("传参异常.");
                return message;
            }
            if (!PAYABLE) {
                message.setMessage("支付功能已经关闭.");
                message.setSuccess(false);
                return message;
            }

            // 验证(超过10个未支付的订单,则无法再次发起订单) ->start
            int payOrderNonPayment = payOrderService.getCountByUserIdAndStatus(userId.intValue(), PayState.PAYING);
            if (payOrderNonPayment > PAYINGORDERMAX) {
                message.setMessage("您的未支付订单已超过10个,请五分钟后再渲染");
                message.setSuccess(false);
                return message;
            }
            // 验证(超过10个未支付的订单,则无法再次发起订单) ->end

            // 高清渲染参数验证 ->start
            /*temporaryPic 为高清渲染临时图片*/
            if (planId == null) {
                message.setMessage("planId不能为空!");
                message.setSuccess(false);
                return message;
            }
            /*优先级，如果等null  那么优先级月底*/
            if (priority == null) {
                priority = 1;
            }
            /*视角*/
            if (viewPoint == null) {
                message.setMessage("参数viewPoint不能为空!");
                message.setSuccess(false);
                return message;
            }
            /*场景 白天黑夜 黄昏？*/
            if (scene == null) {
                message.setMessage("参数scene不能为空!");
                message.setSuccess(false);
                return message;
            }
            /*渲染类型   720    照片级？*/
            if (renderingType == null) {
                message.setMessage("参数renderingType不能为空");
                message.setSuccess(false);
                return message;
            }
            // mode
            if (mode == null) {
                message.setMessage("参数mode不能为空");
                message.setSuccess(false);
                return message;
            }
            // type
            if (StringUtils.isBlank(type)) {
                message.setMessage("参数type不能为空");
                message.setSuccess(false);
                return message;
            }
            // 高清渲染参数验证 ->end

            // 验证 ->end

            message = payOrderService.sendMessageAndCreateOrderNewPc(productId, productType, productName, productDesc,
                    payType, userId, msgId, message,
                    // 高清渲染参数 ->start

                    level, isTurnOn, planId, priority, viewPoint, scene, renderingType, temporaryPic, type, mode, loginUser, platformCode
                    // 高清渲染参数 ->end
            );

            return message;
        }
    }

    /**
     * 通过订单号获取订单信息
     *
     * @param orderNo 订单号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderInfo")
    public Object getOrderInfo(String orderNo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(orderNo)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("查询参数为空!");
            map.put("payState", null);
            responseEnvelope.setObj(map);
            return responseEnvelope;
        }
        PayOrder payOrder = payOrderService.getOrderByOrderNo(orderNo);
        if (null == payOrder) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("查询不到订单信息!");
            map.put("payState", null);
            responseEnvelope.setObj(map);
            return responseEnvelope;
        }
        if (payOrder.getPayState().equals(PayState.SUCCESS)) {
            responseEnvelope.setStatus(Boolean.TRUE);
            responseEnvelope.setMessage("该订单已完成支付!");
            map.put("payState", payOrder.getPayState());
            responseEnvelope.setObj(map);
            return responseEnvelope;
        } else if (payOrder.getPayState().equals(PayState.PAYING)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("该订单正在支付中!");
            map.put("payState", payOrder.getPayState());
            responseEnvelope.setObj(map);
            return responseEnvelope;
        } else if (payOrder.getPayState().equals(PayState.NOTPAY)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("该订单未支付!");
            map.put("payState", payOrder.getPayState());
            responseEnvelope.setObj(map);
            return responseEnvelope;
        }
        responseEnvelope.setStatus(Boolean.FALSE);
        responseEnvelope.setMessage("该订单未完成支付!");
        map.put("payState", null);
        responseEnvelope.setObj(map);
        return responseEnvelope;
    }

    /**
     * 购买户型
     *
     * @param expandType
     * @param request
     * @return
     */
    @RequestMapping(value = "/expandhouse")
    @ResponseBody
    public Object expandHouse(Integer expandType, HttpServletRequest request) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String companyDomainName = request.getHeader("Referer");// 获取域名
        logger.info("购买户型的域名是：" + companyDomainName);
        ResultMessage message = new ResultMessage();
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoHouseList(user, basePlatform.getId(), companyDomainName);
        if (null == payModelVoList || payModelVoList.size() == 0) {
            message = payOrderService.addExpandHouseOrder(user.getId(), expandType, platformCode);
        } else {
            message = payOrderService.addRenderOrderFree(basePlatform, user, expandType);
        }
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }

    /**
     * 购买渲染包年包月订单接口（pc端专用）
     *
     * @param request
     * @param payModelConfigVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRenderPayModelConfig")
    public Object addRenderPayModelConfig(HttpServletRequest request, @ModelAttribute PayModelConfigVo payModelConfigVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        responseEnvelope.setMsgId(null != payModelConfigVo ? payModelConfigVo.getMsgId() : null);
        if (null == payModelConfigVo
                || null == payModelConfigVo.getPayModelConfigId()
                || StringUtils.isEmpty(payModelConfigVo.getPayType())
                || StringUtils.isEmpty(payModelConfigVo.getTradeType())
                || StringUtils.isEmpty(payModelConfigVo.getMsgId())) {
            logger.info("支付参数为空：payModelConfigVo:{}" + ((null == payModelConfigVo) ? "null" : payModelConfigVo.toString()));
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("支付参数为空");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String companyDomainName = request.getHeader("Referer");// 获取域名
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(user, basePlatform, companyDomainName);
        if (payModelVoList != null && payModelVoList.size() > 0) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("企业已付费，过期后可购买");
            return responseEnvelope;
        }
        ResultMessage message = new ResultMessage();
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
        if (null == companyFranchiserGroup) {
            message = payOrderService.addRenderPayModelConfig(payModelConfigVo, userId.intValue(), basePlatform.getId());
        } else {
            message = payOrderService.addRenderPayModelConfigFranchiser(payModelConfigVo, userId.intValue(), basePlatform.getId());
        }
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }

    /**
     * 包年包月生成渲染订单接口（pc端专用）
     *
     * @param request
     * @param payModelConfigVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRenderPayOrder")
    public Object addRenderPayOrder(HttpServletRequest request, @ModelAttribute PayModelConfigVo payModelConfigVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        responseEnvelope.setMsgId(null != payModelConfigVo ? payModelConfigVo.getMsgId() : null);
        if (null == payModelConfigVo) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        ResultMessage message = new ResultMessage();
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
        if (null == companyFranchiserGroup) {
            message = payOrderService.addRenderPayOrder(basePlatform.getId(), userId.intValue(), payModelConfigVo);
        } else {
            message = payOrderService.addRenderPayOrderFranchiser(basePlatform.getId(), userId.intValue(), payModelConfigVo);
        }
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }


    /**
     * 包年包月生成渲染订单接口（pc端专用）
     *
     * @param request
     * @param payModelConfigVo
     * @return
     */
   /* @ResponseBody
    @RequestMapping(value = "/addRenderPayOrder")
    public Object addRenderPayOrderBak(HttpServletRequest request, @ModelAttribute PayModelConfigVo payModelConfigVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        responseEnvelope.setMsgId(null != payModelConfigVo ? payModelConfigVo.getMsgId() : null);
        if (null == payModelConfigVo) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        ResultMessage message = new ResultMessage();
        String companyDomainName = request.getHeader("Referer");// 获取域名
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(user, basePlatform, companyDomainName);
        if (null == payModelVoList || payModelVoList.size() == 0) {
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
            if (null == companyFranchiserGroup) {
                message = payOrderService.addRenderPayOrder(basePlatform.getId(), userId.intValue(), payModelConfigVo);
            } else {
                message = payOrderService.addRenderPayOrderFranchiser(basePlatform.getId(), userId.intValue(), payModelConfigVo);
            }
        } else {
            message = payOrderService.addRenderPayOrderFree(basePlatform, user, payModelConfigVo);
        }
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }
    */

    /**
     * 2c度币充值（微信h5支付，支付宝手机网页支付）
     *
     * @param request
     * @param payOrderModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rechargeIntegralByH5Pay")
    public Object rechargeIntegralByH5Pay(HttpServletRequest request, @RequestBody PayOrderModel payOrderModel) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (null == payOrderModel) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空：payOrderModel:{}");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType())) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付类型为空：payType:{}" + payOrderModel.getPayType());
            return responseEnvelope;
        }
        if (null == payOrderModel.getProductId()) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("产品id为空：productId:{}" + payOrderModel.getProductId());
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payOrderModel.getRedirectUrl())) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("跳转的url为空：redirectUrl:{}" + payOrderModel.getRedirectUrl());
            return responseEnvelope;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(payOrderModel.getRedirectUrl());
        sb.append(PayConstant.PARAM_NAME);
        payOrderModel.setRedirectUrl(sb.toString());
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }

        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        ResultMessage message = payOrderService.rechargeIntegralByH5Pay(userId.intValue(), payOrderModel, platformCode);
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }


    /**
     * 包年包月生成渲染订单接口（移动端专用）
     *
     * @param request
     * @param payModelConfigVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRenderOrder")
    public Object addRenderOrder(HttpServletRequest request, @ModelAttribute PayModelConfigVo payModelConfigVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (null == payModelConfigVo || StringUtils.isEmpty(platformCode)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空：payModelConfigVo:{}");
            return responseEnvelope;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        if (!basePlatform.getPlatformBussinessType().equals(PlatformConstants.TB)) {
            if (StringUtils.isBlank(payModelConfigVo.getCompanyDomainName())) {
                responseEnvelope.setStatus(Boolean.FALSE);
                responseEnvelope.setSuccess(Boolean.FALSE);
                responseEnvelope.setMessage("参数companyDomainName为空");
                return responseEnvelope;
            }
        }
        logger.info("运营网站和移动端专用包年包月渲染接口：用户id：{}" + userId);
        ResultMessage message = new ResultMessage();
        //List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(user, basePlatform, payModelConfigVo.getCompanyDomainName());
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
        if (null == companyFranchiserGroup) {
            message = payOrderService.addRenderOrder(basePlatform.getId(), userId.intValue(), payModelConfigVo);
        } else {
            message = payOrderService.addRenderOrderFranchiser(basePlatform.getId(), userId.intValue(), payModelConfigVo);
        }
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }


    /*@ResponseBody
    @RequestMapping(value = "/addRenderOrder")
    public Object addRenderOrderBak(HttpServletRequest request, @ModelAttribute PayModelConfigVo payModelConfigVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (null == payModelConfigVo || StringUtils.isEmpty(platformCode)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空：payModelConfigVo:{}");
            return responseEnvelope;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        if (!basePlatform.getPlatformBussinessType().equals(PlatformConstants.TB)) {
            if (StringUtils.isBlank(payModelConfigVo.getCompanyDomainName())) {
                responseEnvelope.setStatus(Boolean.FALSE);
                responseEnvelope.setSuccess(Boolean.FALSE);
                responseEnvelope.setMessage("参数companyDomainName为空");
                return responseEnvelope;
            }
        }
        logger.info("运营网站和移动端专用包年包月渲染接口：用户id：{}" + userId);
        ResultMessage message = new ResultMessage();
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(user, basePlatform, payModelConfigVo.getCompanyDomainName());
        if (null == payModelVoList || payModelVoList.size() == 0) {
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
            if (null == companyFranchiserGroup) {
                message = payOrderService.addRenderOrder(basePlatform.getId(), userId.intValue(), payModelConfigVo);
            } else {
                message = payOrderService.addRenderOrderFranchiser(basePlatform.getId(), userId.intValue(), payModelConfigVo);
            }
        } else {
            message = payOrderService.addRenderOrderFree(basePlatform, user, payModelConfigVo);
        }
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }*/

    /**
     * 2b购买包年包月接口（支付宝手机网页支付和微信h5支付）
     *
     * @param request
     * @param payModelConfigVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rechargePayModelByH5Pay")
    public Object addRenderPayModelConfigTob(HttpServletRequest request, @ModelAttribute PayModelConfigVo payModelConfigVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (null == payModelConfigVo || StringUtils.isEmpty(platformCode)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空：payModelConfigVo:{}");
            return responseEnvelope;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        StringBuilder sb = new StringBuilder();
        sb.append(payModelConfigVo.getRedirectUrl());
        sb.append(PayConstant.PARAM_NAME);
        payModelConfigVo.setRedirectUrl(sb.toString());
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            logger.info("当前用户信息为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String companyDomainName = request.getHeader("Referer");// 获取域名
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(user, basePlatform, companyDomainName);
        if (payModelVoList != null && payModelVoList.size() > 0) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("企业已付费，过期后可购买");
            return responseEnvelope;
        }
        ResultMessage message = payOrderService.rechargePayModelByH5Pay(payModelConfigVo, userId.intValue(), basePlatform.getId());
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }

    /**
     * 2b购买包年包月接口（支付宝和微信APP支付）
     *
     * @param request
     * @param payModelConfigVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rechargePayModelByAppPay")
    public Object rechargePayModelByAppPay(HttpServletRequest request, @ModelAttribute PayModelConfigVo payModelConfigVo) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (null == payModelConfigVo || StringUtils.isEmpty(platformCode)) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空：payModelConfigVo:{}");
            return responseEnvelope;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String companyDomainName = request.getHeader("Referer");// 获取域名
        List<PayModelVo> payModelVoList = payModelGroupRefService.getPayModelVoRenderList(user, basePlatform, companyDomainName);
        if (payModelVoList != null && payModelVoList.size() > 0) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setSuccess(Boolean.FALSE);
            responseEnvelope.setMessage("企业已付费，过期后可购买");
            return responseEnvelope;
        }
        ResultMessage message = payOrderService.rechargePayModelByAppPay(payModelConfigVo, userId.intValue(), basePlatform.getId());
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }

    /**
     * 获取度币信息列表（2b专用）
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRechargeIntegralList")
    public Object getRechargeIntegralList() {
        //获取数据字典中充值度币的所有数据
        List<SysDictionary> sysDictionaryList = sysDictionaryService.findAllByType(SysDictionaryConstant.PAY_RECHARGE_MONEY);
        //初始化返回数据
        List<Mobile2bRechargeIntegralVo> mobile2bRechargeIntegralVoList = new ArrayList<>(null == sysDictionaryList ? 0 : sysDictionaryList.size());
        for (SysDictionary dictionary : sysDictionaryList) {
            //装入返回集
            mobile2bRechargeIntegralVoList.add(RechargeIntegralObject.parseToMobile2bRechargeIntegralVo(dictionary));
        }
        return new ResponseEnvelope(Boolean.TRUE, "", mobile2bRechargeIntegralVoList);
    }

    /**
     * 2b度币充值（微信h5支付，支付宝手机网页支付）
     *
     * @param request
     * @param payOrderModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rechargeIntegralByH5Pay2b")
    public Object rechargeIntegralByH5Pay2b(HttpServletRequest request, @RequestBody PayOrderModel payOrderModel) {

        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (null == payOrderModel) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType())) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付类型为空：payType:{}" + payOrderModel.getPayType());
            return responseEnvelope;
        }
        if (null == payOrderModel.getProductId()) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("产品id为空");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payOrderModel.getRedirectUrl())) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("跳转的url为空：redirectUrl:{}" + payOrderModel.getRedirectUrl());
            return responseEnvelope;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(payOrderModel.getRedirectUrl());
        sb.append(PayConstant.PARAM_NAME);
        payOrderModel.setRedirectUrl(sb.toString());
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        ResultMessage message = payOrderService.rechargeIntegralByH5Pay2b(userId.intValue(), payOrderModel, platformCode);
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }

    /**
     * 2b度币充值（微信APP支付，支付宝APP支付）
     *
     * @param request
     * @param payOrderModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rechargeIntegralByAppPay2b")
    public Object rechargeIntegralByAppPay2b(HttpServletRequest request, @RequestBody PayOrderModel payOrderModel) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (null == payOrderModel) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType())) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付类型为空：payType:{}" + payOrderModel.getPayType());
            return responseEnvelope;
        }
        if (null == payOrderModel.getProductId()) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("产品id为空");
            return responseEnvelope;
        }
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser user = sysUserService.get(userId.intValue());
        if (null == user) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        ResultMessage message = payOrderService.rechargeIntegralByAppPay2b(userId.intValue(), payOrderModel, platformCode);
        responseEnvelope.setStatus(message.isSuccess());
        responseEnvelope.setMessage(message.getMessage());
        responseEnvelope.setObj(message.getObj());
        responseEnvelope.setSuccess(message.isSuccess());
        responseEnvelope.setFlag(message.isSuccess());
        return responseEnvelope;
    }
}
