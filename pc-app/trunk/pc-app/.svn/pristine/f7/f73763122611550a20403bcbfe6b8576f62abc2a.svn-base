package com.nork.pay.controller;

import com.google.gson.Gson;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.SendEmail;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.WebSocketUtils;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.pay.common.HttpResult;
import com.nork.pay.model.CompanyDesignPlanIncome;
import com.nork.pay.model.CompanyDesignPlanIncomeAggregated;
import com.nork.pay.service.CompanyDesignPlanIncomeService;
import com.nork.pay.service.HttpService;
import com.nork.pay.common.TradeStatusConstant;
import com.nork.pay.model.UserDesignPlanPurchaseRecord;
import com.nork.pay.service.UserDesignPlanPurchaseRecordService;
import com.nork.product.model.BaseCompany;
import com.nork.product.service.BaseCompanyService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * 推荐方案支付
 * @Author chenqiang
 * @Description 支付Controller
 * @Date 2018/8/29 0029 10:31
 * @Modified By
 */
@RequestMapping("/{style}/web/plan/pay")
@Controller
public class PayPlanController {
    private static Logger logger = LoggerFactory.getLogger(PayPlanController.class);
    private final static String PAY_METHOD_WX = "wxScanCodePay";
    private final static String PAY_METHOD_ZF = "aliScanCodePay";

    @Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;

    @Autowired
    private UserDesignPlanPurchaseRecordService userDesignPlanPurchaseRecordService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CompanyDesignPlanIncomeService companyDesignPlanIncomeService;

    /**
     * 方案购买是否校验接口
     * @author chenqiang
     * @param designPlanId      方案id
     * @param msgId
     * @param request
     * @param response
     * @return
     * @date 2018/9/10 0010 13:49
     */
    @RequestMapping("/planRecommendedLPriceCheck")
    @ResponseBody
    public Object planRecommendedLPriceCheck(String designPlanId,String msgId,HttpServletRequest request,HttpServletResponse response){

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if(loginUser == null || loginUser.getId() <= 0 )
            return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"请重新登录！",msgId);
        if(org.apache.commons.lang3.StringUtils.isBlank(designPlanId))
            return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"方案参数不能为空！",msgId);

        DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(Integer.parseInt(designPlanId));
        if(null == designPlanRecommended)
            return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"方案不存在！",msgId);

        try {
            Integer.parseInt(designPlanId);
        }catch (Exception e){
            return new ResponseEnvelope<DesignPlanRecommendedResult>(false,"方案参数有误！",msgId);
        }

        Integer count = userDesignPlanPurchaseRecordService.getPlanZFCheck(loginUser.getId(),Integer.parseInt(designPlanId));

        if(null != count && count > 0)
            return new ResponseEnvelope<DesignPlanRecommendedResult>(true,"方案已购买！",msgId,true);
        else
            return new ResponseEnvelope<DesignPlanRecommendedResult>(true,"方案未购买！",msgId,false);

    }


    @RequestMapping("/callback")
    public void payCallbackFun(HttpServletRequest request) {

        logger.error("支付完成，接口回调。");
        logger.error("request param:{}", new Gson().toJson(request.getParameterMap()));

        Integer recordStatus;
        String payTradeNo = "";
        String intenalTradeNo = "";
        try {

            if ("SUCCESS".equals(request.getParameter("resultCode"))) {
                recordStatus = TradeStatusConstant.PAY_ALREADY_PAID;
            } else {
                recordStatus = TradeStatusConstant.PAY_FAILURE;
            }

            payTradeNo = request.getParameter("payTradeNo");
            intenalTradeNo = request.getParameter("intenalTradeNo");
            logger.error("交易系统单号="+payTradeNo);
            logger.error("应用系统单号="+intenalTradeNo);
            logger.error("支付完成状态="+recordStatus);

            //更新订单状态
            userDesignPlanPurchaseRecordService.updateObjByTradeNo(intenalTradeNo,recordStatus);

            //查询是否更新、并返回对象
            UserDesignPlanPurchaseRecord userDesignPlanPurchaseRecord = userDesignPlanPurchaseRecordService.getUserDesignPlanPurchaseRecordByNO(intenalTradeNo);
            if(null != userDesignPlanPurchaseRecord){
                //新增/更新公司收益
                this.addCompanyDesignPlanIncome(userDesignPlanPurchaseRecord);

                ResponseEnvelope responseEnvelope = new ResponseEnvelope(true,payTradeNo,"方案购买成功");

                //发送消息、通知支付成功
                try {
                    logger.error("通知消息=message websocket");
                    WebSocketUtils.sendMessage("app.webSocket.payOrder", userDesignPlanPurchaseRecord.getUserId()+"", new Gson().toJson(responseEnvelope));
                }catch(Exception e){
                    logger.error("message websocket链接异常"+e);
                    // 发送邮件
                    StringBuffer stringBuffer = new StringBuffer("Dear All ,<br>");
                    stringBuffer.append("websocket服务器可能已中断,"+ WebSocketUtils.webSocket.getString("app.webSocket.message"));
                    String subject = "【websocket链接异常】";
                    SendEmail.sendEmailForRenderWarning(subject, stringBuffer.toString());
                }

            }else{
                logger.error("获取用户购买方案交易信息失败payTradeNo="+payTradeNo+"|||intenalTradeNo="+intenalTradeNo);
            }

        } catch (Exception e) {
            logger.error("exception:{}", e);
            e.printStackTrace();
        }
    }


    public void addCompanyDesignPlanIncome(UserDesignPlanPurchaseRecord userDesignPlanPurchaseRecord) {
        logger.error("支付回调开始插入公司方案统计表 useType=>{}"+userDesignPlanPurchaseRecord.getUseType());

        //获取方案详情
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(userDesignPlanPurchaseRecord.getDesignPlanId().intValue());
        Integer companyId = designPlanRecommended.getCompanyId();
        if ("share".equals(designPlanRecommended.getPlanSource())){
            Integer SourceId =  designPlanRecommendedServiceV2.getDesignPlanTargetId(designPlanRecommended.getId());
            DesignPlanRecommended targetDesignPlanRecommended = designPlanRecommendedServiceV2.get(SourceId);
            companyId = targetDesignPlanRecommended.getCompanyId();
        }
        //获取公司方案提现率
        BaseCompany baseCompany = baseCompanyService.get(companyId);
        //获取用户信息
        SysUser sysUser = sysUserService.get(userDesignPlanPurchaseRecord.getUserId().intValue());

        CompanyDesignPlanIncome c = new CompanyDesignPlanIncome();
        Date now = new Date();
        c.setPlanCode(designPlanRecommended.getPlanCode());
        if(StringUtils.isNotBlank(designPlanRecommended.getPlanPrice())){
            c.setPlanPrice(new Double(designPlanRecommended.getPlanPrice()));
            c.setWithdrawAmount(c.getPlanPrice() * baseCompany.getWithdrawRate());
            c.setWithdrawAmount(new BigDecimal(c.getWithdrawAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        c.setWithdrawStatus(0);
        c.setCompanyId(new Long(baseCompany.getId()));
        c.setUseTime(now);
        c.setBuyerId(userDesignPlanPurchaseRecord.getUserId());
        c.setIsDeleted(0);
        c.setCreator(sysUser.getUserName());
        c.setGmtCreate(now);
        c.setGmtModified(now);
        c.setUseType(userDesignPlanPurchaseRecord.getUseType());
        c.setModifier(sysUser.getUserName());

        companyDesignPlanIncomeService.insert(c);
        logger.error("insert into CompanyDesignPlanIncome parameter =>{}"+new Gson().toJson(c));

        //更新公司统计收益表
        CompanyDesignPlanIncomeAggregated companyAggregated = companyDesignPlanIncomeService.getCompanyAggregatedByCompanyId(baseCompany.getId());
        logger.error("update CompanyDesignPlanIncomeAggregated =>{}"+(companyAggregated == null?0:1));
        if (companyAggregated == null) {
            //该公司首次收益
            this.addCompanyDesignPlanIncomeAggregated(c);
        } else {
            //更新公司收益
            this.updateCompanyDesignPlanIncomeAggregated(companyAggregated,c);
        }

    }

    private void addCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncome c) {
        CompanyDesignPlanIncomeAggregated cdpia = new CompanyDesignPlanIncomeAggregated();
        cdpia.setCompanyId(c.getCompanyId());
        cdpia.setIsDeleted(0);
        cdpia.setTodayIncome(c.getPlanPrice());
        cdpia.setTotalIncome(c.getPlanPrice());
        cdpia.setWaitWithdrawAmount(c.getWithdrawAmount());
        cdpia.setLastUpdatedDate(new Date());
        companyDesignPlanIncomeService.addCompanyDesignPlanIncomeAggregated(cdpia);
    }

    private void updateCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated companyAggregated,CompanyDesignPlanIncome companyDesignPlanIncome) {
        logger.error("更新之前信息companyAggregated" + (new Gson().toJson(companyAggregated)));
        //判断企业当天是否有收益
        boolean vaildCurrentDay = this.vaildCurrentDay(companyAggregated.getLastUpdatedDate());
        logger.error("判断今天是否收益 =>{}"+vaildCurrentDay);
        if (!vaildCurrentDay) {
            companyAggregated.setTodayIncome(companyDesignPlanIncome.getPlanPrice());
        }else{
            companyAggregated.setTodayIncome(companyAggregated.getTodayIncome()+ companyDesignPlanIncome.getPlanPrice());
        }
        companyAggregated.setWaitWithdrawAmount(companyAggregated.getWaitWithdrawAmount()+companyDesignPlanIncome.getWithdrawAmount());
        companyAggregated.setTotalIncome(companyAggregated.getTotalIncome()+companyDesignPlanIncome.getPlanPrice());
        companyAggregated.setLastUpdatedDate(new Date());
        logger.error("更新对象信息companyAggregated" + (new Gson().toJson(companyAggregated)));
        companyDesignPlanIncomeService.updateCompanyDesignPlanIncomeAggregated(companyAggregated);
    }

    /**
     * 检验是否是当天时间
     *
     * @param lastUpdatedDate
     * @return
     */
    private boolean vaildCurrentDay(Date lastUpdatedDate) {
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        LocalDateTime vaildTime = LocalDateTime.ofInstant(lastUpdatedDate.toInstant(), ZoneId.systemDefault());

        return vaildTime.isAfter(startTime) && vaildTime.isBefore(endTime);
    }

    /**
     * 支付接口
     * @author chenqiang
     * @param designPlanId  方案id
     * @param tradeType     支付类型：0：支付宝、1：微信
     * @param useType       使用类型：0.装进我家,1.产品替换,2.方案改造,3.硬装替换，4.全屋替换
     * @param request
     * @param response
     * @return
     * @date 2018/8/29 0029 10:47
     */
    @RequestMapping("/confirm")
    @ResponseBody
    public Object initServicePayOrder(@PathVariable String style,String designPlanId, String tradeType,String useType,String msgId,
                                      HttpServletRequest request, HttpServletResponse response) {

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);

        //参数校验
        if(StringUtils.isBlank(msgId))
            return new ResponseEnvelope<>(false, "msgId不能为空", msgId);
        if(StringUtils.isBlank(designPlanId))
            return new ResponseEnvelope<>(false, "方案id不能为空", msgId);
        if(StringUtils.isBlank(tradeType))
            return new ResponseEnvelope<>(false, "支付类型不能为空", msgId);
        if(StringUtils.isBlank(useType))
            return new ResponseEnvelope<>(false, "使用类型不能为空", msgId);
        if(null == loginUser)
            return new ResponseEnvelope<>(false, "请重新登录", msgId);
        try {
            Integer.parseInt(useType);
            Integer.parseInt(designPlanId);
            int type = Integer.parseInt(tradeType);
            if(0 == type || 1==type){
            }else{
                return new ResponseEnvelope<>(false, "支付类型传参有误", msgId);
            }
        }catch (Exception e){
            return new ResponseEnvelope<>(false, "参数传递有误", msgId);
        }
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(Integer.parseInt(designPlanId));
        if(null == designPlanRecommended || !(1 == designPlanRecommended.getChargeType()) || StringUtils.isBlank(designPlanRecommended.getPlanPrice()))
            return new ResponseEnvelope<>(false, "推荐方案参数有误", msgId);


        //获取配置信息
        String notifyUrl = Utils.getPropertyName("app", "service.pay.notifyUrl", null);
        String payUrl = Utils.getPropertyName("app", "service.pay.url", null);
        String wxpayIp = Utils.getPropertyName("app", "wxpay.ip", null);
        if(StringUtils.isBlank(notifyUrl) || StringUtils.isBlank(payUrl) || StringUtils.isBlank(wxpayIp))
            return new ResponseEnvelope<>(false, "方案支付配置信息有误", msgId);
        notifyUrl = notifyUrl.replace("style",style);

        //调用支付生成支付二维码
        HttpResult httpResult;
        UserDesignPlanPurchaseRecord userDesignPlanPurchaseRecord = null;
        try {
            //新增用户购买方案信息
            userDesignPlanPurchaseRecord = userDesignPlanPurchaseRecordService.insertSelective(designPlanId, tradeType, notifyUrl.trim(), payUrl.trim(), wxpayIp.trim(), designPlanRecommended,useType,loginUser);
            if(null == userDesignPlanPurchaseRecord)
                return new ResponseEnvelope<>(false, "新增用户购买方案信息失败", msgId);

            //生成支付订单,获取支付二维码
            httpResult = initPayPlan(request, userDesignPlanPurchaseRecord, loginUser);

        } catch (Exception e) {
            return new ResponseEnvelope<>(false, e.getMessage());
        }


        logger.info("获取支付二维码,httpResult:{}", httpResult);
        if (Objects.equals(httpResult.getCode(), 200)) {

            //生成二维码
            /*PayResultModel payResultModel = new Gson().fromJson(httpResult.getBody(),PayResultModel.class);
            String codeUrl = QrCodeUtil.generateQrCode(payResultModel.getQrCodeUrl(),payResultModel.getPayTradeNo());*/
            return new ResponseEnvelope<>(true, (Object) httpResult.getBody(),"获取成功",msgId);
        }

        return new ResponseEnvelope<>(false, "系统异常，获取支付信息失败",msgId);
    }

    private HttpResult initPayPlan(HttpServletRequest request, UserDesignPlanPurchaseRecord record, LoginUser loginUser) {
        try {

            //初始化支付参数
            Map<String, String> requestParam = new HashMap<>();
            requestParam.put("intenalTradeNo", record.getTradeNo());
            requestParam.put("tradeDesc", "推荐方案用户购买");
            logger.error("支付金额:{}", record.getTradeAmount());
            requestParam.put("totalFee", new BigDecimal(record.getTradeAmount()).multiply(new BigDecimal(100)).longValue() + "");
            requestParam.put("payMethod", 0 == record.getTradeType() ? PAY_METHOD_ZF : PAY_METHOD_WX);
            requestParam.put("ip", record.getWxPayIp());
            requestParam.put("notifyUrl", record.getNotifyUrl());
            requestParam.put("operator", loginUser.getId().toString());
            requestParam.put("source", "3");
            logger.error("支付参数:{}", requestParam+",支付路径="+record.getPayUrl()+",回调函数="+record.getNotifyUrl());
            String platformCode = null != request.getHeader("Platform-Code") ? request.getHeader("Platform-Code") : "pc2b";
            List<BasicHeader> basicHeaders = Arrays.asList(new BasicHeader("Platform-Code", platformCode), new BasicHeader("Authorization", request.getHeader("Authorization")));

            //rest 调用支付服务
            HttpResult httpResult = HttpService.doPost(record.getPayUrl(), requestParam, basicHeaders);
            logger.error("rest_pay_result:{}", new Gson().toJson(httpResult));

            //返回
            return httpResult;
        } catch (Exception e) {
            logger.error("exception:{}", e);
            e.printStackTrace();
        }
        return new HttpResult();
    }

}
