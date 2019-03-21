package com.nork.render.service.impl;

import com.google.gson.Gson;
import com.nork.common.constant.SysDictionaryConstant;
import com.nork.common.exception.BizException;
import com.nork.common.exception.GeneratePanoramaException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.SendEmail;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.WebSocketUtils;
import com.nork.design.common.HttpResult;
import com.nork.design.common.HttpService;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.service.DesignPlanAutoRenderService;
import com.nork.pay.common.IdGenerator;
import com.nork.pay.metadata.PayState;
import com.nork.pay.metadata.ProductType;
import com.nork.pay.model.PayOrder;
import com.nork.pay.service.PayOrderService;
import com.nork.payAccount.model.PayAccount;
import com.nork.payAccount.service.PayAccountService;
import com.nork.platform.model.BasePlatform;
import com.nork.platform.service.BasePlatformService;
import com.nork.render.model.RenderTypeCode;
import com.nork.render.model.input.RenderPayInput;
import com.nork.render.model.vo.RenderPayVo;
import com.nork.render.service.HighDefinitionRenderService;
import com.nork.render.service.IAutoRenderService;
import com.nork.system.model.BaseMessage;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.BaseMessageService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;
import com.nork.system.websocket.obj.MessageResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @ClassName RenderOrderServiceImpl
 * @Description 付费渲染订单 ServiceImpl
 * @Author chenm
 * @Date 2019/1/15 17:35
 * @Version 1.0
 **/
@Service("highDefinitionRenderService")
@Transactional(rollbackFor = Exception.class)
public class HighDefinitionRenderServiceImpl implements HighDefinitionRenderService {

    private Logger logger = LoggerFactory.getLogger(HighDefinitionRenderServiceImpl.class);
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private PayAccountService payAccountService;
    @Autowired
    private WebAutoRenderTaskProcessor webAutoRenderTaskProcessor;
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private BaseMessageService baseMessageService;
    @Autowired
    private DesignPlanAutoRenderService designPlanAutoRenderService;

    @Override
    public RenderPayVo getHDRenderPayInfo(RenderPayInput payInput, LoginUser loginUser) throws BizException {

        RenderPayVo renderPayVo = null;
        RenderPayInput.PayMethodEnum payMethodEnum = payInput.getMethodEnum();
        try {
            //得到需支付费用
            renderPayVo = this.getRenderPayPriceInfo(payInput,loginUser);
        } catch (BizException e) {
            logger.error(e.getMessage());
            throw new BizException(e.getMessage());
        }
        //查询用户余额
        BasePlatform platform = basePlatformService.getByCode(payInput.getPlatformCode());
        if(platform == null){
            throw  new BizException("平台信息不正确!");
        }
        PayAccount userAccount = payAccountService.getUserPayAccountByUserId(loginUser.getId().longValue(),platform.getPlatformBussinessType());
        if(userAccount != null){
            String balanceStr = null;
            Double balance = new BigDecimal(userAccount.getBalanceAmount()).divide(new BigDecimal(10),1,RoundingMode.DOWN).doubleValue();
            //如果是整数，则不显示小数点
            if(Math.round(balance) == balance){
                balanceStr = String.valueOf((long)balance.doubleValue());
            }else{
                balanceStr = String.valueOf(balance.doubleValue());
            }
            renderPayVo.setBalanceAmount(balanceStr);
        }
        if(payMethodEnum == RenderPayInput.PayMethodEnum.DUBI){
            //使用余额支付直接返回价格信息，先不生成订单
            return renderPayVo;
        }
        payInput.setPlatformId(platform.getId());
        //生成订单
        String orderNo = this.getOrderNoByRenderOrder(payInput,renderPayVo,loginUser);
        //得到支付二维码信息
        renderPayVo = this.doRenderPay(payInput,orderNo,loginUser,renderPayVo);

        return renderPayVo;

    }

    public RenderPayVo doRenderPay(RenderPayInput payInput,String orderNo, LoginUser loginUser, RenderPayVo renderPayVo) throws BizException {

        //获取配置信息
        //TODO:地址应该自己拼起来......
        String renderServerUrl = Utils.getPropertyName("app", "app.render.server.url", "https://render.ci.sanduspace.com/");
        String notifyUrl = Utils.getPropertyName("app", "render.highDefinition.pay.notifyUrl", null);
        String payUrl = Utils.getPropertyName("app", "pay.gateway.url", null);
        String wxpayIp = Utils.getPropertyName("app", "wxpay.ip", "127.0.0.1");
        if(StringUtils.isBlank(notifyUrl) || StringUtils.isBlank(payUrl) || StringUtils.isBlank(wxpayIp)){
            logger.error("doRenderPay 获取支付地址相关信息错误");
            throw new BizException("获取支付信息失败");
        }
        //初始化请求参数
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("intenalTradeNo",orderNo);
        requestParam.put("tradeDesc", "高清渲染");
        requestParam.put("totalFee", renderPayVo.getRenderCost());
        requestParam.put("payMethod",payInput.getMethodEnum().getPayMethodValue());
        requestParam.put("ip", wxpayIp);
        requestParam.put("notifyUrl", notifyUrl);
        requestParam.put("operator", loginUser.getId().toString());
        requestParam.put("source", "2");//??

        String platformCode = null != payInput.getPlatformCode() ? payInput.getPlatformCode() : "pc2b";
        List<BasicHeader> basicHeaders = Arrays.asList(new BasicHeader("Platform-Code", platformCode), new BasicHeader("Authorization", payInput.getAuthorization()));
        //rest 调用支付服务
        HttpResult httpResult = null;
        HttpService httpService = new HttpService();
        try {

            logger.error("支付参数requestParam=" + new Gson().toJson(requestParam));
            httpResult = httpService.doPost(payUrl, requestParam, basicHeaders);
        } catch (Exception e) {
            logger.error("获取支付二维码失败",e);
            throw new BizException("");
        }
        if (Objects.equals(httpResult.getCode(), 200)) {
            String body = httpResult.getBody();
            JSONObject jsonObject = JSONObject.fromObject(body);
            String qrCodeUrl = (String)jsonObject.get("qrCodeUrl");
            /*String payTradeNo = (String)jsonObject.get("payTradeNo");*/
            renderPayVo.setQrCodeUrl(qrCodeUrl);
            return renderPayVo;
        }else{
            throw new BizException("出现异常,获取支付二维码失败!");

            /*return renderPayVo;*/
        }
    }

    @Override
    public RenderPayVo getRenderPayPriceInfo(RenderPayInput paySearch, LoginUser loginUser) throws BizException {
        if(paySearch.getDesignPlanId() == null || paySearch.getRenderingType() == null){
            return null;
        }
        RenderPayVo renderPayVo = new RenderPayVo();

        //查询渲染价格
        Map<String,Object> renderPriceMap = null;
        try {
            renderPriceMap = this.getRenderPrice(paySearch.getRenderingType());
        } catch (BizException e) {
            logger.error("查询专业渲染价格时出现异常:",e.getMessage());
            throw new BizException(e.getMessage());
        }
        if(renderPriceMap == null){
            String errorMsg = (String) renderPriceMap.get("errorMsg");
            if(Utils.isBlank(errorMsg)){
                errorMsg = "未获取到对应价格信息";
            }
            logger.error("{},请求参数paySearch：{}",errorMsg,paySearch.toString());
            throw new BizException(errorMsg);
        }
        //渲染价格
        String totalFeeStr = (String)renderPriceMap.get("totalFee");
        if(Utils.isBlank(totalFeeStr)){
            logger.error("未获取到对应价格信息,请求参数paySearch：{}",paySearch.toString());
            throw new BizException("未获取到对应价格信息!");
        }
        Long cost = Long.valueOf(totalFeeStr);
        renderPayVo.setRenderCost(cost);
        //转换显示价格
        if(paySearch.getMethodEnum() == RenderPayInput.PayMethodEnum.DUBI){
            String fee = null;
            double totalFee = new BigDecimal(Double.valueOf(totalFeeStr.trim())).divide(new BigDecimal(10d),1, RoundingMode.UP).doubleValue();
            //如果是整数，则不显示小数点
            if(Math.round(totalFee) - totalFee == 0){
                fee = String.valueOf((long)totalFee);
            }else{
                fee = String.valueOf(totalFee);
            }
            renderPayVo.setTotalFee(fee + "");
            renderPayVo.setPriceUnit("个");
        }else{
            double totalFee = new BigDecimal(Double.valueOf(totalFeeStr.trim())).divide(new BigDecimal(100d),2, RoundingMode.UP).doubleValue();
            renderPayVo.setTotalFee(totalFee + "");
            renderPayVo.setPriceUnit("元");
        }
        renderPayVo.setPayMethodNum(paySearch.getPayMethodNum());

        return renderPayVo;
    }

    @Override
    public String getOrderNoByRenderOrder(RenderPayInput payInput , RenderPayVo renderPayVo, LoginUser loginUser) throws BizException {
        
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderDate(new Date());
        payOrder.setPayState(PayState.NOTPAY);
        //转为分
        payOrder.setTotalFee(renderPayVo.getRenderCost().intValue());
        payOrder.setPlanId(payInput.getDesignPlanId());
        if(Objects.equals(RenderTypeCode.COMMON_PICTURE_LEVEL,payInput.getRenderingType())){
            //照片级渲染
            payOrder.setProductName(ProductType.COMMON_RENDER_NAME);
            payOrder.setProductType(ProductType.COMMON_RENDER);
        }else if(Objects.equals(RenderTypeCode.COMMON_720_LEVEL,payInput.getRenderingType())){
            //720单点渲染
            payOrder.setProductName(ProductType.PANORAMA_RENDER_NAME);
            payOrder.setProductType(ProductType.PANORAMA_RENDER);
        }else if(Objects.equals(RenderTypeCode.ROAM_720_LEVEL,payInput.getRenderingType())){
            //720多点渲染
            payOrder.setProductName(ProductType.ROAM_PANORAMA_RENDER_NAME);
            payOrder.setProductType(ProductType.ROAM_PANORAMA_RENDER);
        }else if(Objects.equals(RenderTypeCode.HD_VIDEO,payInput.getRenderingType())) {
            //视频渲染
            payOrder.setProductName(ProductType.ROAM_VIDEO_RENDER_NAME);
            payOrder.setProductType(ProductType.ROAM_VIDEO_RENDER);
        }
        payOrder.setProductDesc("高清渲染");
        payOrder.setPayType(payInput.getMethodEnum().getPayType());
        payOrder.setTradeType(payInput.getMethodEnum().getPayType());
        payOrder.setBizType("Buy");
        payOrder.setOrderNo(IdGenerator.generateNo());
        payOrder.setCreator(loginUser.getLoginName());
        payOrder.setGmtCreate(new Date());
        payOrder.setModifier(loginUser.getLoginName());
        payOrder.setGmtModified(new Date());
        payOrder.setUserId(loginUser.getId());
        payOrder.setRenderTaskType(RenderTypeCode.RENDER_TASK_TYPE_HD_AUTO);
        payOrder.setRenderParamsStr(payInput.getRenderParamsStr());
        payOrder.setPlatformId(payInput.getPlatformId());
        payOrder.setProductId(payInput.getDesignPlanId());
        int id = payOrderService.add(payOrder);
        if(id < 0){
            throw new BizException("创建支付凭证失败！");
        }
        return payOrder.getOrderNo();
    }

    /**
     * 从数据字典中查询对应渲染类型专业渲染的价格
     * @author: chenm
     * @date: 2019/1/15 19:20
     * @param renderingType
     * @return: java.lang.Integer
     */
    private Map<String,Object> getRenderPrice(Integer renderingType) throws BizException {
        if(renderingType == null){
            return null;
        }
        //查询数据字典的value
        String sysValueKey = "";
        if(RenderTypeCode.COMMON_PICTURE_LEVEL == renderingType){
            sysValueKey = SysDictionaryConstant.PROFESSIONAL_RENDER_PRICE_PHOTO;
        }else if(RenderTypeCode.COMMON_720_LEVEL == renderingType){
            sysValueKey = SysDictionaryConstant.PROFESSIONAL_RENDER_PRICE_COMMON_720;
        }else if(RenderTypeCode.ROAM_720_LEVEL == renderingType){
            sysValueKey = SysDictionaryConstant.PROFESSIONAL_RENDER_PRICE_ROAM_720;
        }else if(RenderTypeCode.COMMON_VIDEO == renderingType){
            sysValueKey = SysDictionaryConstant.PROFESSIONAL_RENDER_PRICE_VIDEO;
        }else{
            logger.error("查询专业渲染时出现未知的渲染类型renderingType:{}",renderingType);
            throw new BizException("渲染类型不正确！");
        }
        SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValueKeyInCache(SysDictionaryConstant.SYS_DICTIONARY_PROFESSIONAL_RENDER_PRICE
                ,sysValueKey);
        if(dictionary == null || dictionary.getId() == null){
            logger.error("没有查到此类型的数据字典,type:{},valuekey:{}"
                    ,SysDictionaryConstant.SYS_DICTIONARY_PROFESSIONAL_RENDER_PRICE,sysValueKey);
            throw new BizException("未找到该渲染类型数据！");
        }
        Map<String,Object> renderPriceMap = new HashMap<String,Object>(2);
        //渲染价格，单位为分
        renderPriceMap.put("totalFee",dictionary.getAtt1());
       /* renderPriceMap.put("priceUnit","");*/

        return renderPriceMap;
    }

    @Override
    public boolean saveRenderOrderWithDuBi(RenderPayInput payInput, LoginUser loginUser) throws BizException {
        RenderPayVo renderPayVo = new RenderPayVo();

        //查询费用
        Map<String,Object> renderPriceMap = null;
        try {
            renderPriceMap = this.getRenderPrice(payInput.getRenderingType());
        } catch (BizException e) {
            logger.error("查询高清渲染费用时出现异常:",e);
            throw new BizException("出现异常,支付失败!");
        }
       /* if(renderPriceMap == null){
            String errorMsg = (String) renderPriceMap.get("errorMsg");
            if(Utils.isBlank(errorMsg)){
                errorMsg = "未获取到对应价格信息";
            }
            logger.error("{},请求参数paySearch：{}",errorMsg,payInput.toString());
            throw new BizException(errorMsg);
        }*/
        //度币与分的比例是: 1:10
        renderPayVo.setRenderCost(Long.parseLong((String)renderPriceMap.get("totalFee")));

        Long totalFee = renderPayVo.getRenderCost() * 10;
        renderPayVo.setTotalFee(totalFee + "");

        //查询用户账号信息
        BasePlatform platform = basePlatformService.getByCode(payInput.getPlatformCode());
        if(platform == null){

        }
        PayAccount userAccount = payAccountService.getUserPayAccountByUserId(loginUser.getId().longValue(),platform.getPlatformBussinessType());
        if(userAccount == null){
            logger.error("支付专业渲染订单时未找到该用户账号信息:");
            throw new BizException("账户异常，扣费失败!");
        }
        //单位为分
        Double cost = Double.parseDouble(renderPayVo.getRenderCost().toString());
        //校验用户余额是否足够
        if(userAccount.getBalanceAmount() < renderPayVo.getRenderCost()){
            throw new BizException("余额不足,扣费失败!");
        }

        //扣费
        try {

            payAccountService.updateBalanceAmount(cost,loginUser.getId(),platform.getPlatformBussinessType());
        } catch (Exception e) {
            logger.error("支付专业渲染订单时出现异常:",e);
            throw new BizException("扣费失败!");
        }
        //创建支出凭证 pay_order
        String orderNo = this.getOrderNoByRenderOrder(payInput,renderPayVo,loginUser);
        //创建渲染任务并回填pay_order
        //创建高清渲染任务
        AutoRenderTask renderTask = null;
        try {
            renderTask = webAutoRenderTaskProcessor.createHDRenderTask(payInput,orderNo,loginUser);
        } catch (BizException e) {
            logger.error("创建高清渲染任务时出现异常:",e);
            throw new BizException("出现异常！");
        } catch (Exception e){
            e.printStackTrace();
            logger.error("",e);
            throw new BizException("出现异常！");
        }
        //回填 pay_order
        if(renderTask != null){
            PayOrder payOrder = payOrderService.findOneByOrderNo(orderNo);
            payOrder.setTaskId(renderTask.getTaskId());
            payOrder.setPayState(PayState.SUCCESS);
            payOrderService.update(payOrder);
        }

        return true;
    }

    @Override
    public int updatePayOrderState(String orderNo, String payState, String payTradeNo, String info) {
        PayOrder payOrder = payOrderService.get(orderNo);
        if(payOrder == null){

        }
        payOrder.setPayState(payState);
        payOrder.setPayTradeNo(payTradeNo);
        payOrder.setRemark(payOrder.getRemark() == null ? info : payOrder.getRemark() + info);
        payOrder.setGmtModified(new Date());
        int ret = payOrderService.update(payOrder);
        return ret;
    }

    @Override
    public boolean createHDRenderTaskWithOrder(String orderNo,String platformCode) {
        //处理参数
        PayOrder payOrder = payOrderService.get(orderNo);
        RenderPayInput payInput = new RenderPayInput();
        payInput.setDesignPlanId(payOrder.getPlanId());
        String renderTypeStr = payOrder.getProductType();
        if(Objects.equals(ProductType.COMMON_RENDER , payOrder.getProductType())){
            payInput.setRenderingType(RenderTypeCode.COMMON_PICTURE_LEVEL);
        }else  if(Objects.equals(ProductType.PANORAMA_RENDER,payOrder.getProductType())){
            payInput.setRenderingType(RenderTypeCode.COMMON_720_LEVEL);
        }else  if(Objects.equals(ProductType.ROAM_PANORAMA_RENDER ,payOrder.getProductType())){
            payInput.setRenderingType(RenderTypeCode.ROAM_720_LEVEL);
        }else  if(Objects.equals(ProductType.ROAM_VIDEO_RENDER, payOrder.getProductType())){
            payInput.setRenderingType(RenderTypeCode.HD_VIDEO);
        }
        payInput.setPlatformCode(platformCode);
        Integer platformId = payOrder.getPlatformId();
        if(platformId != null){
            BasePlatform platform = basePlatformService.selectPlatformById(platformId);
            if(platform != null && platform.getId()!=null){
                payInput.setPlatformId(platformId);
                payInput.setPlatformCode(platform.getPlatformCode());
            }
        }
        payInput.setRenderParamsStr(payOrder.getRenderParamsStr());
        SysUser sysUser = sysUserService.get(payOrder.getUserId());
        LoginUser loginUser = new LoginUser();
        loginUser.setId(payOrder.getUserId());
        loginUser.setLoginName(sysUser.getNickName());

        //创建高清渲染任务
        AutoRenderTask renderTask = null;
        try {
            renderTask = webAutoRenderTaskProcessor.createHDRenderTask(payInput,orderNo,loginUser);
        } catch (BizException e) {
            e.printStackTrace();
            logger.error("",e);
            return false;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("",e);
            return false;
        }
        //回填 pay_order
        if(renderTask != null){
            payOrder.setTaskId(renderTask.getTaskId());
            payOrderService.update(payOrder);
        }
        return true;
    }


    @Override
    public void sendMessageForCreateHDRenderTask(String intenalTradeNo) throws BizException {
        PayOrder payOrder = payOrderService.get(intenalTradeNo);
        if(payOrder == null){
            throw new BizException("找不到该订单信息");
        }
        AutoRenderTask renderTask = designPlanAutoRenderService.getRenderTaskById(payOrder.getTaskId());

        SysUser sysUser = sysUserService.get(payOrder.getUserId());
        //1.增加消息记录  BaseMessage  BaseMessageRecieve
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setUserId(payOrder.getUserId());
        baseMessage.setBusinessTypeId(5);//消息类型??不知道干吗用的
        baseMessage.setBusinessObjId(payOrder.getProductId());
        baseMessage.setBusinessObjType("design_plan");
        baseMessage.setMessageType(0);
        baseMessage.setCreator(sysUser.getNickName());
        baseMessage.setGmtCreate(new Date());
        baseMessage.setModifier(sysUser.getNickName());
        baseMessage.setGmtModified(new Date());
        baseMessage.setIsDeleted(0);
        int id = baseMessageService.add(baseMessage);

        /* baseMessage.set*/
        //2.TODO:发送websocket 消息
        //发送消息、通知支付成功
        try {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setSuccess(true);
            messageResponse.setMessage("专业渲染订单"+intenalTradeNo+"支付成功");
            Map<String,String> map = new HashMap<String,String>();
            map.put("designPlanId", payOrder.getProductId() + "");
            messageResponse.setObj(map);
            messageResponse.setType(4);
            JSONObject jsonObject = JSONObject.fromObject(messageResponse);
            String message=jsonObject.toString();

            logger.error("通知消息 = message websocket = " + message);
            WebSocketUtils.sendMessage("app.webSocket.message", payOrder.getUserId() + "", message);
        }catch(Exception e){
            logger.error("message websocket链接异常"+e);
            // 发送邮件
            StringBuffer stringBuffer = new StringBuffer("Dear All ,<br>");
            stringBuffer.append("websocket服务器可能已中断,"+ WebSocketUtils.webSocket.getString("app.webSocket.message"));
            String subject = "【websocket链接异常】";
            SendEmail.sendEmailForRenderWarning(subject, stringBuffer.toString());
        }

    }
}
