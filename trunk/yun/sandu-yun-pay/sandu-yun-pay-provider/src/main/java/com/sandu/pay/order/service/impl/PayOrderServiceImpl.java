package com.sandu.pay.order.service.impl;

import com.google.gson.Gson;
import com.sandu.common.constant.PlatformConstants;
import com.sandu.common.constant.SystemCommonConstant;
import com.sandu.common.constant.UserConstants;
import com.sandu.pay.common.exception.BizException;
import com.sandu.pay.common.exception.ExceptionCodes;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.pay.Config;
import com.sandu.common.pay.IdGenerator;
import com.sandu.common.pay.QrCodeUtil;
import com.sandu.common.util.*;
import com.sandu.config.ResourceConfig;
import com.sandu.order.MallBaseOrder;
import com.sandu.order.service.MallBaseOrderService;
import com.sandu.pay.alipay.ScanPayUtil;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BasePlatformService;
import com.sandu.pay.goods.model.Goods;
import com.sandu.pay.mgrRecharge.model.MgrRechargeLog;
import com.sandu.pay.order.dao.PayOrderMapper;
import com.sandu.pay.order.metadata.*;
import com.sandu.pay.order.model.*;
import com.sandu.pay.order.model.search.PayOrderSearch;
import com.sandu.pay.order.service.*;
import com.sandu.pay.order.vo.PayMobileLoginVo;
import com.sandu.pay.order.vo.PayModelConfigVo;
import com.sandu.pay.system.websocket.util.WebSocketUtils;
import com.sandu.pay.wexin.common.Signature;
import com.sandu.pay.wexin.common.Util;
import com.sandu.pay.wexin.metadata.WxTradeType;
import com.sandu.pay.wexin.protocol.ResultNotify;
import com.sandu.pay.wexin.protocol.UnifiedOrderReqData;
import com.sandu.pay.wexin.protocol.UnifiedOrderResData;
import com.sandu.pay.wexin.service.UnifiedOrderService;
import com.sandu.product.model.MobileProductReplace;
import com.sandu.product.model.ProductUtil;
import com.sandu.render.model.RenderTypeCode;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.SysDictionaryConstant;
import com.sandu.system.model.task.SysTask;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.system.service.SysTaskService;
import com.sandu.user.model.*;
import com.sandu.user.service.CompanyFranchiserGroupService;
import com.sandu.user.service.SysUserService;
import com.sandu.user.service.UserFinanceService;
import com.sandu.user.service.UserJurisdictionService;
import com.sandu.web.task.model.DesignPlan;
import com.sandu.web.task.model.SysTaskStatus;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/9/19.
 */
@Service("payOrderService")
public class PayOrderServiceImpl implements PayOrderService {

    //Json转换类
    private final static Gson gson = new Gson();
    private static Logger logger = LogManager.getLogger(PayOrderServiceImpl.class);
    private final static String CLASS_LOG_PREFIX = "[支付服务]:";

    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private MgrRechargeLogService mgrRechargeLogService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysTaskService sysTaskService;
    @Autowired
    private UserFinanceService userFinanceService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Resource
    private PayModelConfigService payModelConfigService;
    @Resource
    private PayModelGroupRefService payModelGroupRefService;
    @Resource
    private BasePlatformService basePlatformService;
    @Autowired
    private PayAccountService payAccountService;
    @Resource
    private CompanyFranchiserGroupService companyFranchiserGroupService;
    @Resource
    private UserJurisdictionService userJurisdictionService;

    @Autowired
    private MallBaseOrderService mallBaseOrderService;

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    public PayOrder get(Integer id){
        return payOrderMapper.get(id);
    }

    /**
     *　添加订单数据
     * @param payOrder
     * @return
     */
    @Override
    public Integer addPayOrder(PayOrder payOrder, LoginUser loginUser) {
        if( payOrder == null ){
            return 0;
        }
        // 1、通过商品类型判断从哪获取商品信息
//        Goods goods = null;
//        if( GoodsType.OPM_CHARGE.toString().equalsIgnoreCase(payOrder.getProductType()) ){   // 运营网站充值订单,价格信息从数据字典获取
//            goods = goodsService.getGoodsInfo("OPM_RECHARGE", payOrder.getProductId());// 获取商品信息（临时从数据字典获取，后期独立成goods表）
//            goods.setGoodsName(GoodsType.OPM_CHARGE.getName());
//            goods.setGoodsDesc(GoodsType.OPM_CHARGE.getName());
//        }

        // 2、保存订单数据
//        if( goods == null ){
//            return 0;
//        }
//        payOrder = getOrder(goods);
        sysSave(payOrder, loginUser);
        int i = payOrderMapper.insertSelective(payOrder);
//        payOrder.setGoods(goods);
        return payOrder.getId();
    }

    /**
     * 更新订单信息
     * @param payOrder 订单
     * @return
     */
    @Override
    @Transactional
    public Integer update(PayOrder payOrder){
        return payOrderMapper.updateByPrimaryKeySelective(payOrder);
    }

    /**
     * 通过订单NO更新订单状态
     * @param order 订单NO
     * @return
     */
    @Override
    @Transactional
    public Integer updatePayState(PayOrder order){
        return payOrderMapper.updatePayStateByOrderNo(order);
    }

    /**
     * 所有数据
     *
     * @param  payOrder
     * @return   List<PayOrder>
     */
    @Override
    public List<PayOrder> getList(PayOrder payOrder) {
        return payOrderMapper.selectList(payOrder);
    }

    /**
     * 通过订单编码查询订单信息
     * @param orderNo
     * @return
     */
    @Override
    public PayOrder getOrderByOrderNo(String orderNo) {
        return payOrderMapper.getOrderByOrderNo(orderNo);
    }

    /**
     * 组装订单信息
     * @param goods 商品信息
     * @return
     */
    public PayOrder getOrder(Goods goods){
        PayOrder payOrder = new PayOrder();
        payOrder.setProductId(goods.getGoodsId());
        payOrder.setOrderDate(new Date());
        payOrder.setPayState(PayState.NOTPAY);
        payOrder.setOrderNo(IdGenerator.generateNo());
        payOrder.setTotalFee(goods.getPrice().intValue());
        payOrder.setAdjustFee(goods.getAdjustFee().intValue());
        payOrder.setProductDesc(goods.getGoodsDesc());
        payOrder.setProductName(goods.getGoodsName());
        return payOrder;
    }

    /**
     *    获取数据数量
     *
     * @param  payOrderSearch
     * @return   int
     */
    @Override
    public int getCount(PayOrderSearch payOrderSearch){
        return  payOrderMapper.selectCount(payOrderSearch);
    }

    /**
     *    分页获取数据
     *
     * @return   List<PayOrder>
     */
    @Override
    public List<PayOrder> getPaginatedList(
            PayOrderSearch payOrderSearch) {
        return payOrderMapper.selectPaginatedList(payOrderSearch);
    }

    public PayOrder get(String orderNo){
        return payOrderMapper.getOrderByOrderNo(orderNo);
    }

    public PayOrder findOneByTaskId(Integer taskId) {
        PayOrderSearch payOrderSearch = new PayOrderSearch();
        payOrderSearch.setStart(0);
        payOrderSearch.setLimit(1);
        payOrderSearch.setTaskId(taskId);
        List<PayOrder> payOrderList = this.getPaginatedList(payOrderSearch);
        if(payOrderList != null && payOrderList.size() > 0){
            return payOrderList.get(0);
        }
        return null;
    }

    private PayOrder getOrder(int totalFee, String payType, String productName, String productDesc, String tradeType) {
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderDate(new Date());
        payOrder.setPayState(PayState.NOTPAY);
        payOrder.setOrderNo(IdGenerator.generateNo());
        payOrder.setTotalFee(totalFee);
        payOrder.setPayType(payType);
        payOrder.setProductDesc(productDesc);
        payOrder.setProductName(productName);
        payOrder.setTradeType(tradeType);
        return payOrder;
    }

    public PayOrder getOrder(int totalFee, String payType, int productId, String productType, String productName,
                             String productDesc, String tradeType) {
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderDate(new Date());
        payOrder.setPayState(PayState.NOTPAY);
        payOrder.setOrderNo(IdGenerator.generateNo());
        payOrder.setTotalFee(totalFee);
        payOrder.setPayType(payType);
        payOrder.setProductId(productId);
        payOrder.setProductType(productType);
        payOrder.setProductName(ProductUtil.getProductNameByProductType(productType));
        payOrder.setProductDesc(productDesc);
        payOrder.setTradeType(tradeType);
        return payOrder;

    }


    /**
     * 扫码在线充值二维码
     * @param productId
     * @param totalFee1
     * @param payType
     * @param loginUser
     * @param sysDictionary
     * @return
     */
    @Override

    public ResultMessage getRechargeQrCodePath(Integer productId, int totalFee1, String payType, LoginUser loginUser, SysDictionary sysDictionary, String platformCode){
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            logger.info("平台编码：platformCode{}为空" + platformCode);
            message.setMessage("平台编码：platformCode{}为空" + platformCode);
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        String tradeType = TradeType.SCANCODE;
        Integer userId = loginUser==null?0:loginUser.getId();
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            message.setMessage("用户信息为空.");
            return message;
        }
        if (sysDictionary == null || sysDictionary.getValue()==null) {
            message.setMessage("数据异常.");
            return message;
        }
        int originalPrice= Utils.getIntValue(sysDictionary.getAtt1());
        int discountPrice = sysDictionary.getValue();
        PayOrder payOrder = getOrder(originalPrice, payType, PayProductConstans.RECHARGE_PRODUCT_NAME, PayProductConstans.RECHARGE_PRODUCT_DESC, tradeType);
        ScanPayReqData reqData = new ScanPayReqData();
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        int balanceAmount = 0;
        if (null != companyFranchiserGroup) {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        try {
            payOrder.setProductType(PayProductConstans.PAY_PRODUCT_TYPE);
            sysSave(payOrder, loginUser);
            if (userId > 0) {
                payOrder.setUserId(userId);
            }
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            payOrder.setProductId(productId);
            payOrder.setBizType(BizType.RECHARGE);
            payOrder.setFinanceType(FinanceType.IN);

            payOrder.setCurrentIntegral((int)(balanceAmount+Double.parseDouble(sysDictionary.getAtt1())));
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                message.setMessage("保存在线充值单失败.");
                message.setSuccess(Boolean.FALSE);
                return message;
            }
            payOrder.setId(id);
            reqData.setOrderId(payOrder.getId());
            if (payType.equalsIgnoreCase(PayType.WXPAY)) {
                String flag = "SUCCESS";
                UnifiedOrderService orderService = new UnifiedOrderService();
                // 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
                String timeExpire = Utils.getTimeExpire(30);
                String notifyUrl = ResourceConfig.WX_PAY_NOTIFY_URL;
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(PayProductConstans.RECHARGE_PRODUCT_NAME, payOrder.getOrderNo(),
                        discountPrice, WxTradeType.NATIVE, timeExpire, notifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    String prepayId = unifiedOrderResData.getPrepay_id();
                    String wx_codeUrl = unifiedOrderResData.getCode_url();
                    String codeUrl = QrCodeUtil.generateQrCode(ResourceConfig.UPLOAD_ROOT,wx_codeUrl, payOrder.getOrderNo());
                    reqData.setCode_url(ResourceConfig.IMAGE_SERVER_URL+codeUrl);
                    reqData.setQrCodePath(codeUrl);
                    reqData.setOrderNo(payOrder.getOrderNo());
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setCodeUrl(codeUrl);
                    payOrder.setNotifyUrl(notifyUrl);
                    this.update(payOrder);
                    logger.info(CLASS_LOG_PREFIX+"成功生成在线充值单");
                    message.setMessage("成功生成在线充值单");
                    message.setObj(reqData);
                    message.setSuccess(Boolean.TRUE);

                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(2);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount((double) Utils.getIntValue(sysDictionary.getAtt1()));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                }
            }
            if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
                payOrder.setNotifyUrl(ResourceConfig.ALIPAY_NOTIFY_URL);
                message = ScanPayUtil.addScanpayOrder(payOrder, reqData, discountPrice, ResourceConfig.IMAGE_SERVER_URL, ResourceConfig.UPLOAD_ROOT);
                message.setObj(reqData);
                if (message.isSuccess()) {
                    message.setMessage("成功生成在线充值单");
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setCodeUrl(reqData.getQrCodePath());
                    this.update(payOrder);

                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(3);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount((double) Utils.getIntValue(sysDictionary.getAtt1()));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(Boolean.FALSE);
            message.setMessage("下单异常.");
        }

        return message;
    }



    /**
     * 运维网站度币预支付渲染任务
     * @param userId
     * @param renderType
     * @param planId
     * @param houseId
     * @return
     */
    @Override

    public ResultMessage addIntegralPayRenderTask(Integer userId, Integer renderType, Integer planId, Integer houseId, String platformCode){
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            message.setMessage("平台编码为空" + platformCode);
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            message.setMessage("平台信息为空");
            return message;
        }
        SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue(SysDictionaryConstant.PLATFROM_RENDER_TYPE,renderType);
        logger.info(CLASS_LOG_PREFIX + "度币支付渲染类型数据字典查询:" + gson.toJson(sysDictionary));
        String productName = "";
        String productType = "";
        double totalFee = 0;
        if (sysDictionary != null && sysDictionary.getId() != null) {
            productName = sysDictionary.getName();
            productType = sysDictionary.getValuekey();
            totalFee = Double.parseDouble(sysDictionary.getAtt1());
        }else{
            logger.info(CLASS_LOG_PREFIX + "找不到对应数据字典渲染类型！value = "+renderType+";type="+ SysDictionaryConstant.PLATFROM_RENDER_TYPE);
            message.setMessage(CLASS_LOG_PREFIX + "找不到对应数据字典渲染类型！value = "+renderType+";type="+ SysDictionaryConstant.PLATFROM_RENDER_TYPE);
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            return message;
        }
        SysUser user = sysUserService.get(userId);
        int balanceAmount = 0;
        if (null == user) {
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            message.setMessage("当前用户信息为空.");
            return message;
        }
        logger.info("渲染的用户id：userId:{}" + user.getId() + "，用户类型：userType:{}" + user.getUserType());
        if (UserConstants.USER_TYPE_TOURIST != user.getUserType().intValue()) {
            // 非游客的必要判断
            //检查用户户型是否可用(已购买的户型不算入户型数计算)
            logger.info(CLASS_LOG_PREFIX + "检查用户户型是否可用(已购买的户型不算入户型数计算):userId:{}, houseId:{}.", userId, houseId);
            boolean houseIsAbleUse = userFinanceService.checkUserHouseIsAbleUse(userId, houseId);
            logger.info(CLASS_LOG_PREFIX + "检查用户户型是否可用(已购买的户型不算入户型数计算)完成:houseIsAbleUse:{}", houseIsAbleUse);
            if (!houseIsAbleUse) {
                message.setStatus(MessageStateType.HOUSE_INSUFFICIENT_STATE);
                message.setMessage("账户户型不足，请购买户型.");
                return message;
            }
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
            if (null != companyFranchiserGroup) {
                balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
            } else {
                PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
                if (null == payAccount) {
                    logger.info("用户度币信息为空，生成订单失败" + "用户id：" + userId + "平台编码：" + basePlatform.getPlatformCode());
                    message.setMessage("用户度币信息为空，生成订单失败");
                    return message;
                }
                balanceAmount  = payAccount.getBalanceAmount().intValue(); // 账户余额
            }
            if (balanceAmount < totalFee) {
                message.setStatus(MessageStateType.PAY_INTEGRAL_INSUFFICIENT_STATE);
                message.setMessage("账户度币不足，请充值.");
                return message;
            }
        } else {
            totalFee = 0;
        }
        // 直接从账户余额中扣除
        logger.info("totalFee"+totalFee+";PayType.PREDEPOSIT ：" + PayType.PREDEPOSIT +"sysDictionaryID="+sysDictionary.getId()+"productType="+productType+"productName:"+productName);
        PayOrder payOrder = getOrder((int)totalFee, PayType.PREDEPOSIT, sysDictionary.getId(), productType, productName, PayProductConstans.PAY_RENDER_PRODUCT_DESC, TradeType.PREDEPOSIT);
        payOrder.setPlanId(planId);
        payOrder.setHouseId(houseId);
        payOrder.setProductName(productName); // 只可以使用此产品名称
        LoginUser loginUser = new LoginUser(userId,user.getUserType(),user.getUserName(),user.getMobile());
        try {
            // 插入支付订单
            sysSave(payOrder, loginUser);
            if (userId > 0) {
                payOrder.setUserId(userId);
            }
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);
            payOrder.setPayType(PayType.PREDEPOSIT);
            payOrder.setCurrentIntegral((int)(balanceAmount - totalFee));
            payOrder.setOrderDate(new Date());
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                message.setMessage("保存支付单失败.");
                return message;
            }
            if (UserConstants.USER_TYPE_TOURIST != user.getUserType().intValue()) {
                // 非游客
                // 更新账户度币和消费度币
                if(updateUserFinance(payOrder.getOrderNo())){
                    message.setMessage("支付成功.");
                }else{
                    PayOrder newPayOrder = new PayOrder();
                    newPayOrder.setPayState(PayState.PAY_ERROR);
                    newPayOrder.setOrderNo(payOrder.getOrderNo());
                    this.updatePayState(newPayOrder);
                    message.setStatus(MessageStateType.PAY_FAILED_STATE);
                    message.setMessage("支付失败.");
                    return message;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("运营网站渲染异常，内容为：",e);
            message.setSuccess(false);
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            message.setMessage("度币支付发生异常.");
        }
        message.setSuccess(true);
        message.setRenderingType(String.valueOf(renderType));
        message.setOrderNo(payOrder.getOrderNo());
        message.setMessage("度币支付成功");
        return message;
    }


    /**
     * 通知退款或成功更新状态，非官方退款
     * @param orderNo
     * @return
     */
    @Override
    @Transactional
    public synchronized ResultMessage notifyRefund(String orderNo, Integer userId){
        logger.info(CLASS_LOG_PREFIX + "验证通过,开始执行退款，进到service");
        ResultMessage message = new ResultMessage();
        if(userId==null) {
            logger.info(CLASS_LOG_PREFIX + "userId不能为空");
            message.setMessage("userId不能为空.");
            return message;
        }
        SysUser sysUser = sysUserService.get(userId);
        if(sysUser ==null) {
            logger.info(CLASS_LOG_PREFIX + "用户不存在");
            message.setMessage("用户不存在");
            return message;
        }

        PayOrder payOrder = payOrderMapper.getOrderByOrderNo(orderNo);
        if (payOrder == null) {
            logger.info(CLASS_LOG_PREFIX + "找不到该订单信息");
            message.setMessage("找不到该订单信息.");
            return message;
        }
        if (BizType.REFUND.equals(payOrder.getBizType())) {
            logger.info(CLASS_LOG_PREFIX + "已退过款.");
            message.setMessage("已退过款.");
            return message;
        }

        logger.info(CLASS_LOG_PREFIX + "验证通过,开始执行退款");
        //兼容旧数据
        int refundOrderCount = payOrderMapper.selectRefundOrderCountByTaskId(payOrder.getTaskId());
        if(refundOrderCount>0) {
            message.setMessage("已退过款old.");
            return message;
        }
        //新数据退款方式
        int i = payOrderMapper.updateForLockPayOrder(userId.toString(),orderNo);
        if(i<=0) {
            message.setMessage("已退过款new.");
            return message;
        }
        int balanceAmount = 0;
        Integer totalFee = payOrder.getTotalFee();
        String productName = "";
        BasePlatform basePlatform = basePlatformService.get(payOrder.getPlatformId());
        if (basePlatform.getPlatformCode().equals(PlatformConstants.PC_2B)) {
            productName =  this.payOrderMapper.getRefundProductNamePc(payOrder.getOrderNo());
        } else {
            productName =  this.payOrderMapper.getRefundProductName(payOrder.getOrderNo());
        }
        if (UserConstants.USER_TYPE_TOURIST != sysUser.getUserType().intValue()) {
            // 非游客
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
            if (null != companyFranchiserGroup) {
                balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
            } else {
                PayAccount payAccount = payAccountService.getPayAccountInfo(userId, payOrder.getPlatformId());
                if (null == payAccount) {
                    message.setMessage("用户度币信息为空，生成订单失败");
                    return message;
                }
                balanceAmount = payAccount.getBalanceAmount().intValue();
            }
        }
        PayOrder refundOrder = new PayOrder();
        refundOrder.setOrderDate(new Date());
        refundOrder.setPayState(PayState.SUCCESS);
        refundOrder.setOrderNo(IdGenerator.generateNo());
        refundOrder.setTotalFee(totalFee);
        refundOrder.setPayType("1");
        refundOrder.setProductId(payOrder.getProductId());
        refundOrder.setProductType("recharge");
        refundOrder.setProductName(productName);
        refundOrder.setProductDesc("渲染失败退款");
        refundOrder.setBizType(BizType.REFUND);
        refundOrder.setFinanceType(FinanceType.IN);
        refundOrder.setPayState(PayState.SUCCESS);
        refundOrder.setTradeType("refunds");
        refundOrder.setUserId(userId);
        refundOrder.setGmtCreate(new Date());
        refundOrder.setGmtModified(new Date());
        refundOrder.setCreator(sysUser.getMobile());
        refundOrder.setModifier(sysUser.getMobile());
        refundOrder.setIsDeleted(0);
        refundOrder.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        refundOrder.setCurrentIntegral(new Integer(balanceAmount + totalFee));
        refundOrder.setTaskId(payOrder.getTaskId());
        refundOrder.setPlanId(payOrder.getPlanId());
        refundOrder.setHouseId(payOrder.getHouseId());
        refundOrder.setPlatformId(payOrder.getPlatformId()); // 平台id
        logger.info("调用退款service中，退款的金额：totalFee:{}" + totalFee + "用户id：userId:{}" + userId
                + "退款的订单号：orderNo:{}" + orderNo + "，生成退款订单的订单号：" + refundOrder.getOrderNo());
        int id = payOrderMapper.insertSelective(refundOrder);
        logger.info(CLASS_LOG_PREFIX + "更新行数:{}"+id + "订单号为：orderNo:{}" + refundOrder.getOrderNo());
        if(id>0) {
            if (UserConstants.USER_TYPE_TOURIST != sysUser.getUserType().intValue()) {
                // 非游客
                if(!this.updateUserFinance(refundOrder.getOrderNo())){
                    message.setMessage("退款失败.");
                    return message;
                }
            }
        }else {
            message.setMessage("创建退款单失败.");
            return message;
        }

        message.setMessage("退款成功.");
        message.setSuccess(true);
        return message;
    }

    /**
     * 更新渲染订单状态
     * @param orderNo
     * @return
     */
    @Override
    @Transactional
    public ResultMessage updateRenderPayState(String orderNo, boolean falg){

        ResultMessage message = new ResultMessage();
        PayOrder payOrder = payOrderMapper.getOrderByOrderNo(orderNo);
        if (payOrder == null) {
            message.setMessage("找不到该订单信息.");
            return message;
        }
        if (falg) {
            PayOrder newPayOrder = new PayOrder();
            newPayOrder.setId(payOrder.getId());
            newPayOrder.setPayState(PayState.SUCCESS);
            int i = payOrderMapper.updateByPrimaryKeySelective(newPayOrder);
            if ( i > 0) {
                message.setMessage("渲染成功.");
                message.setSuccess(true);
                return message;
            } else {
                message.setMessage("更新订单状态失败.");
                return message;
            }
        } else {
            PayOrder newPayOrder = new PayOrder();
            newPayOrder.setId(payOrder.getId());
            newPayOrder.setBizType(BizType.REFUND);
            newPayOrder.setFinanceType(FinanceType.IN);
            newPayOrder.setPayState(PayState.SUCCESS);
            newPayOrder.setProductName(payOrder.getProductName()+",失败退款");
            int i = payOrderMapper.updateByPrimaryKeySelective(newPayOrder);
            if (i > 0) {
                //更新用户失败则更新订单退款状态
                if(!this.updateUserFinance(orderNo)){
                    newPayOrder = new PayOrder();
                    newPayOrder.setId(payOrder.getId());
                    newPayOrder.setPayState(PayState.REFUND_FAIL);
                    payOrderMapper.updateByPrimaryKeySelective(newPayOrder);
                    message.setMessage("退款失败.");
                }else{
                    //更新订单时间
                    newPayOrder = new PayOrder();
                    newPayOrder.setId(payOrder.getId());
                    newPayOrder.setCurrentIntegral(payOrder.getUserTotalIntegral()+payOrder.getTotalFee());
                    newPayOrder.setOrderDate(new Date());
                    payOrderMapper.updateByPrimaryKeySelective(newPayOrder);
                    message.setSuccess(true);
                    message.setMessage("退款成功.");
                }
            } else {
                message.setMessage("更新退款订单失败.");
            }
        }
        return message;
    }

    /**
     * 余额支付
     * @param userId
     * @param totalFee
     * @param payType
     * @param productId
     * @param productType
     * @param productName
     * @param productDesc
     * @return
     */
    @Override
    public ResultMessage getAddPredepositPay(Integer userId, Integer totalFee, String payType, Integer productId, String productType, String productName, String productDesc, String platformCode){

        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            logger.info("平台编码：platformCode{}为空" + platformCode);
            message.setMessage("平台编码：platformCode{}为空" + platformCode);
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }

        // 直接从账户余额中扣除
        PayOrder payOrder = getOrder(totalFee, payType, productId, productType, productName, productDesc, TradeType.PREDEPOSIT);
        SysUser user = sysUserService.get(userId);
        if (user != null) {
            int balanceAmount = 0;
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
            if (null != companyFranchiserGroup) {
                balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
            } else {
                PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
                if (null == payAccount) {
                    message.setMessage("用户度币信息为空，生成订单失败");
                    return message;
                }
                balanceAmount = payAccount.getBalanceAmount().intValue();
            }
            payOrder.setCurrentIntegral(balanceAmount);
            if (balanceAmount < (int)totalFee) {
                message.setMessage("当前用户的账户余额不足.");
                return message;
            } else {
                LoginUser loginUser = new LoginUser(userId,user.getUserType(),user.getUserName(),user.getMobile());
                try {
                    // 插入支付订单
                    sysSave(payOrder, loginUser);
                    if (userId > 0) {
                        payOrder.setUserId(userId);
                    }
                    payOrder.setPlatformId(basePlatform.getId()); // 平台id
                    payOrder.setBizType(BizType.BUY);
                    payOrder.setFinanceType(FinanceType.OUT);
                    payOrder.setPayState(PayState.SUCCESS);
                    int id = this.addPayOrder(payOrder,loginUser);
                    if (id == 0) {
                        message.setMessage("保存支付单失败.");
                        return message;
                    }
                    // 更新账户余额和消费金额
                    updateUserFinance(payOrder.getOrderNo());

                    message.setSuccess(true);
                    message.setMessage("支付成功.");

                } catch (Exception e) {
                    e.printStackTrace();
                    message.setSuccess(false);
                    message.setMessage("预付款支付购买产品发生异常.");
                }
            }

        } else {
            message.setMessage("当前用户信息为空.");
        }
        //TODO   余额支付临时处理
        message.setOrderNo(payOrder.getOrderNo());

        return message;
    }

    /**
     * 获取扫码支付信息（生成二维码）
     */
    @Override

    public ResultMessage getPayScanOrderUrlInfo(Integer totalFee, String payType, Integer productId, String productType, String productName, String productDesc, LoginUser loginUser, String msgId, String platformCode) {
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            logger.info("平台编码：platformCode{}为空" + platformCode);
            message.setMessage("平台编码：platformCode{}为空" + platformCode);
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        String tradeType = TradeType.SCANCODE;
        try {
            if (totalFee == 0) {
                totalFee = Config.getScan_total_fee();
            }
            PayOrder payOrder = getOrder(totalFee, payType, productId, productType, productName, productDesc, tradeType);
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            ScanPayReqData reqData = new ScanPayReqData();
            sysSave(payOrder, loginUser);
            if (loginUser  != null) {
                payOrder.setUserId(loginUser.getId());
            }
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                message.setMessage("保存支付单失败.");
                message.setSuccess(Boolean.FALSE);
                return message;
            }
            payOrder.setId(id);
            reqData.setOrderId(payOrder.getId());
            reqData.setOrderNo(payOrder.getOrderNo());
            if (payType.equalsIgnoreCase(PayType.WXPAY)) {
                String flag = "SUCCESS";
                UnifiedOrderService orderService = new UnifiedOrderService();
                // 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
                String timeExpire = Utils.getTimeExpire(30);
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(productName, payOrder.getOrderNo(),
                        payOrder.getTotalFee(), WxTradeType.NATIVE, timeExpire, ResourceConfig.WX_PAY_NOTIFY_URL);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    String prepayId = unifiedOrderResData.getPrepay_id();
                    String wx_codeUrl = unifiedOrderResData.getCode_url();
                    String codeUrl = QrCodeUtil.generateQrCode(ResourceConfig.UPLOAD_ROOT,wx_codeUrl, payOrder.getOrderNo());
                    reqData.setCode_url(wx_codeUrl);//直接返回访问路径，不在返回2维码
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setNotifyUrl(ResourceConfig.WX_PAY_NOTIFY_URL);
                    payOrder.setCodeUrl(codeUrl);
                    this.update(payOrder);
                    message.setMessage("成功生成扫描预付订单");
                    message.setObj(reqData);
                    message.setSuccess(true);
                }
            }else if(payType.equalsIgnoreCase(PayType.ALIPAY)){
                payOrder.setNotifyUrl(ResourceConfig.ALIPAY_NOTIFY_URL);
                message = ScanPayUtil.addScanpayOrder(payOrder,reqData,totalFee, ResourceConfig.IMAGE_SERVER_URL, ResourceConfig.UPLOAD_ROOT);
                message.setObj(reqData);
                if (message.isSuccess()) {
                    message.setMessage("成功生成扫描预付订单");
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setCodeUrl(reqData.getQrCodePath());
                    this.update(payOrder);
                }
            }else if (payType.equalsIgnoreCase(PayType.PREDEPOSIT)){//余额支付
                message.setSuccess(true);
                message.setMessage("余额下订单成功!");
                message.setObj(reqData);
                payOrder.setPayState(PayState.PAYING);
                payOrder.setCodeUrl("");
                this.update(payOrder);
            }else{
                message.setMessage("未知支付类型");
                logger.info(CLASS_LOG_PREFIX+"未知支付类型");
            }
            //TODO  临时处理余额支付
        } catch (Exception e) {
            logger.error(CLASS_LOG_PREFIX+e);
            message.setMessage("下单异常.");
        }
        return message;
    }

    /**
     * 微信支付回调更新信息
     * @param sbResult
     * @param success
     * @return
     */
    @Override
    public boolean wxPayCallbackUpdateInfo(String sbResult, boolean success){

        logger.info(CLASS_LOG_PREFIX + "微信回调结果:" + sbResult);
        logger.info("微信回调结果sbResult的长度：" + sbResult.length());
        if (sbResult.length() > 0) {
            // 测试回调接口是否会修改渲染任务状态和生成渲染配置文件所以注释,提交解开注释
            ResultNotify result = (ResultNotify) Util.getObjectFromXML(sbResult, ResultNotify.class);
            logger.info("微信回调结果result的内容：" + gson.toJson(result));
            if (result != null) {
                PayOrder order = new PayOrder();
                String orderNo = result.getOut_trade_no();
                String openId = result.getOpenid();
                String refId = result.getTransaction_id();
                order.setOrderNo(orderNo);
                order.setOpenId(openId);
                order.setRefId(refId);
                order.setPayState(PayState.PAY_ERROR);
                if (result.getResult_code().equalsIgnoreCase(PayState.SUCCESS)
                        && result.getReturn_code().equalsIgnoreCase(PayState.SUCCESS)) {
                    success = true;
                    order.setPayState(PayState.SUCCESS);
                }
                this.updatePayState(order);
                logger.info("微信回调结果order支付状态：" + order.getPayState() + "订单号：orderNo:{}" + orderNo);
                //FIXME:如果支付成功就回调修改充值状态
                MgrRechargeLog mgr = mgrRechargeLogService.getReChargeByOrderNo(orderNo);
                if (mgr != null) {
                    SysUser sysUser = sysUserService.get(mgr.getUserId());
//                    mgrRechargeLogService.saveLogAndUpdateUserBalance(mgr, sysUser);
                    mgrRechargeLogService.saveLogAndUpdateUserIntegral(mgr, sysUser);
                }

                //给客户端发送订单支付状态信息,更新任务状态，退款
                logger.info("给客户端发送订单支付状态信息,更新任务状态start==================");
                boolean flag = this.sendPayStateNew(orderNo);
                logger.info("给客户端发送订单支付状态信息,更新任务状态:flag:{}" + flag);
                if (flag) {
                    logger.info(CLASS_LOG_PREFIX + " 微信回调发送消息成功");
                    updateUserFinance(orderNo);
                }
            }
        }
        return success;
    }


    @Override
    public boolean wxMiniPayCallbackUpdateInfo(String sbResult, boolean success){
        logger.info("微信小程序回调结果result的内容：" + sbResult.length());
        if (sbResult.length() > 0) {
            ResultNotify result = (ResultNotify) Util.getObjectFromXML(sbResult, ResultNotify.class);
            logger.info("微信小程序回调结果result的内容：" + gson.toJson(result));
            if (result != null) {
                String orderNo = result.getOut_trade_no();
                PayOrder payOrder = getOrderByOrderNo(orderNo);
                if (result.getResult_code().equalsIgnoreCase(PayState.SUCCESS)
                        && result.getReturn_code().equalsIgnoreCase(PayState.SUCCESS)) {
                    success = true;
                    payOrder.setPayState(PayState.SUCCESS);
                    payOrder.setGmtModified(new Date());
                    logger.info("微信小程序回调updateOrderPayStatus====start,orderNo=" + orderNo+"payOrder"+payOrder.toString());
                    if(!PayProductConstans.RENDER_PRODUCT_TYPE_2C
                            .equals(payOrder.getProductType())){ //如果是包年包月不走这段逻辑
                        this.updateOrderPayStatus(orderNo+"",result.getResult_code(),result.getReturn_code());
                    }
                    logger.info("微信小程序回调updateOrderPayStatus====end,orderNo=" + orderNo);
                    //更新订单信息
                    this.update(payOrder);
                    //购买包年包月回调
                    if(PayProductConstans.RENDER_PRODUCT_TYPE_2C
                               .equals(payOrder.getProductType())){
                        //插入业务关联表
                        logger.info("包年包月回调添加个人套餐信息begin");
                        payModelGroupRefService.addPayModelGroupRef2C(payOrder);
                    }
                }
            }
        }
        return success;
    }

    /**
     * 2c度币充值小程序支付
     *
     * @param sysUser
     * @param basePlatform
     * @param payOrderModel
     * @return
     */
    @Override
    public ResultMessage rechargeIntegralByMiniPay2c(SysUser sysUser, BasePlatform basePlatform, PayOrderModel payOrderModel) {
        ResultMessage message = new ResultMessage();
        SysDictionary sysDictionary = sysDictionaryService.get(payOrderModel.getProductId());
        if (null == sysDictionary) {
            message.setMessage("数据字典中找不到度币信息");
            return message;
        }
        int originalPrice= Utils.getIntValue(sysDictionary.getAtt1());
        int discountPrice = sysDictionary.getValue();
        PayOrder payOrder = getOrder(originalPrice, payOrderModel.getPayType(), PayProductConstans.RECHARGE_PRODUCT_NAME, PayProductConstans.RECHARGE_PRODUCT_DESC, TradeType.MINIPAY);
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(sysUser.getId());
        int balanceAmount = 0;
        if (null == companyFranchiserGroup) {
            // 不具备经销商权限
            PayAccount payAccount = payAccountService.getPayAccountInfo(sysUser.getId(), basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
        } else {
            // 具备经销商权限
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        }
        try {
            payOrder.setPlatformId(basePlatform.getId());
            payOrder.setOpenId(payOrderModel.getOpenid());
            payOrder.setPayType(PayType.WXPAY);
            payOrder.setProductType(PayProductConstans.PAY_PRODUCT_TYPE);
            sysSave(payOrder, loginUser);
            payOrder.setUserId(sysUser.getId());
            payOrder.setProductId(payOrderModel.getProductId());
            payOrder.setBizType(BizType.RECHARGE);
            payOrder.setFinanceType(FinanceType.IN);
            payOrder.setCurrentIntegral((int)(balanceAmount+Double.parseDouble(sysDictionary.getAtt1())));
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                message.setMessage("保存在线充值单失败");
                return message;
            }
            String appid = "wx1cbabc1754956e83";
            String mchId = "1394367302";
            String key = "wxb3048NorKad782765143df7NorKcb2"; // 商户支付秘钥
            String flag = "SUCCESS";
            String timeExpire = Utils.getTimeExpire(30);
            String notifyUrl = ResourceConfig.WX_PAY_NOTIFY_URL; // 回调地址
            UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(appid, mchId, PayProductConstans.RECHARGE_PRODUCT_NAME, payOrder.getOrderNo(), discountPrice,
                    notifyUrl, WxTradeType.JSAPI, payOrderModel.getOpenid(), key, timeExpire);
            UnifiedOrderService orderService = new UnifiedOrderService();
            UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
            if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                    && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                Map<String, Object> packageParams = new HashMap<String, Object>();
                packageParams.put("appId", appid);
                packageParams.put("nonceStr", System.currentTimeMillis() + "");
                packageParams.put("package", "prepay_id="+ unifiedOrderResData.getPrepay_id());
                packageParams.put("signType","MD5");
                packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
                String packageSign = Signature.getSign(packageParams, key);
                packageParams.put("paySign", packageSign);
                message.setObj(packageParams);
                //添加充值记录
                this.addMgrRechargeLog(sysUser.getId(), 2, (double) Utils.getIntValue(sysDictionary.getAtt1()),loginUser,payOrder.getOrderNo(), basePlatform.getPlatformBussinessType());
                //更新订单
                payOrder.setPrepayId(unifiedOrderResData.getPrepay_id());
                payOrder.setPayState(PayState.PAYING);
                this.update(payOrder);
                message.setObj(packageParams);
                message.setMessage("下单成功");
                message.setSuccess(Boolean.TRUE);
            } else {
                message.setMessage("下单失败");
            }
        } catch (Exception e) {
            message.setMessage("下单异常");
            logger.error("下单异常信息：",e);
        }
        return message;
    }

    @Override
    public int deleteByOrderNo(String orderNo) {
        return this.payOrderMapper.deleteByOrderNo(orderNo);
    }

    /**
     * 添加一条充值记录
     *
     * @param userId  用户id
     * @param rechargeType 充值类型（微信：2，支付宝：3）
     * @param rechargeAmount 金额
     * @param loginUser
     * @param orderNo 订单号
     * @param platformBussinessType 平台类型
     */
    public void addMgrRechargeLog(Integer userId, Integer rechargeType, Double rechargeAmount, LoginUser loginUser,String orderNo, String platformBussinessType) {
        MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
        mgrRechargeLog.setUserId(userId);
        mgrRechargeLog.setRechargeType(rechargeType); // 充值类型
        mgrRechargeLog.setRechargeStatus(2); // 充值状态
        mgrRechargeLog.setRechargeAmount(rechargeAmount);
        mgrRechargeLog.setAdministratorId(userId);
        mgrRechargeLog.setOrderNo(orderNo);
        mgrRechargeLog.setPlatformBussinessType(platformBussinessType); // 平台类型
        mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
        mgrRechargeLogService.add(mgrRechargeLog);
    }

    private void updateOrderPayStatus(String orderNo,String resultCode,String returnCode) {
        orderNo = orderNo.substring(0, orderNo.length() - 4);
        MallBaseOrder baseOrder = mallBaseOrderService.getOrderByOrderNo(orderNo);
        mallBaseOrderService.updateBaseOrderByOrderIdAndCallBackStatus(baseOrder.getId(),resultCode,returnCode);
    }



    /**
     * 更新支付宝支付回调订单信息
     * @param order
     */
    @Override
    public boolean updateAliPayCallbackInfo(PayOrder order){
        logger.info(CLASS_LOG_PREFIX + "开始更新支付宝回调订单信息");
        this.updatePayState(order);
        //FIXME:如果支付成功就回调修改充值状态
        MgrRechargeLog mgr = mgrRechargeLogService.getReChargeByOrderNo(order.getOrderNo());
        if(mgr != null) {
            SysUser sysUser = sysUserService.get(mgr.getUserId());
//            mgrRechargeLogService.saveLogAndUpdateUserBalance(mgr, sysUser);
            mgrRechargeLogService.saveLogAndUpdateUserIntegral(mgr, sysUser);
        }

        // 给客户端发送支付状态消息
        logger.info("更新支付宝支付回调订单信息支付结果：" + order.getPayState());
        return updateUserFinance(order.getOrderNo());
    }

    /**
     * 更新度币
     * @param orderNo
     * @return
     */
    public boolean updateUserFinance(String orderNo) {
        //新版修改start
        ResultMessage message = payAccountService.updateAmountForOrder(orderNo);
        return message.isSuccess();
        //新版修改end
    }

    /**
     * 给客户端发送订单支付状态信息,更新任务状态，退款
     * @param orderNo
     * @return
     */
    @Override
    public boolean sendPayStateNew(String orderNo){
        boolean flag = true;
        logger.info(CLASS_LOG_PREFIX + "付款完成，给客户端发送支付结果... 订单No：" + orderNo);
        if(StringUtils.isBlank(orderNo) ){
            return false;
        }
        try {
            // 获取订单
            PayOrder payOrder = new PayOrder();
            payOrder.setOrderNo(orderNo);
            List<PayOrder> orderList = this.getList(payOrder);
            if( orderList != null && orderList.size() == 1 ){
                payOrder = orderList.get(0);
            }else{
                logger.error(CLASS_LOG_PREFIX + "没有找到订单No为 " +orderNo+ " 的订单信息！");
                return false;
            }

            // 获取产生订单的客户端
            logger.info(CLASS_LOG_PREFIX + "payOrder.getPayState:"+ payOrder.getPayState());
            if( payOrder.getPayState().equals(PayState.PAY_ERROR) || payOrder.getPayState().equals(PayState.SUCCESS)){
                //获取订单关联的任务
                SysTask sysTask = null;
                if(payOrder.getTaskId() != null && payOrder.getTaskId() > 0){
                    sysTask = sysTaskService.get(payOrder.getTaskId());
                }else{

                }
                // 组装发给客户端的信息
                ResponseEnvelope result = new ResponseEnvelope();
                SendStateResult sendStateResult = new SendStateResult();
                sendStateResult.setOrderNo(payOrder.getOrderNo());
                sendStateResult.setPayState(payOrder.getPayState());
                //如果该订单有关联任务，则从任务表中查询出渲染类型
                if(sysTask != null){
                    if(sysTask.getRenderType() != null ){
                        sendStateResult.setRenderingType(sysTask.getRenderType()+"");
                    }else{
                        logger.error(CLASS_LOG_PREFIX + "task id="+sysTask.getId()+"  param 'renderType' is null！");
                    }
                }else{
                    //处理？可能是序列号支付
                }

                if( payOrder.getPayState().equals(PayState.SUCCESS) ){
                    result.setStatus(true);
                }else if(payOrder.getPayState().equals(PayState.PAY_ERROR)){
                    result.setStatus(false);
                }else{

                }
                result.setObj(sendStateResult);
                JSONObject jsonObject = JSONObject.fromObject(result);

                // 发送消息
                logger.info(CLASS_LOG_PREFIX + "发送消息：" + jsonObject.toString());
                try {
                    WebSocketUtils.sendMessage(ResourceConfig.WEB_SOCKET_SERVER_URL, String.valueOf(payOrder.getId()), jsonObject.toString());
                    //消息发送成功，更新任务状态
                    if(sysTask != null){
                        //更新任务状态:由待渲染状态》渲染中
                        Boolean msgSendIsSuccess = Boolean.TRUE;//消息是否发送成功
                        this.updateNonPaymentTaskNew(sysTask,null,payOrder.getPayType(),payOrder.getPayState(),msgSendIsSuccess);
                    }else{
                        //处理？可能是序列号支付
                    }
                }catch(Exception e){
                    logger.error(CLASS_LOG_PREFIX + "payOrder websocket链接异常:"+e);
                    //消息发送失败，更新任务状态,退款
                    if(sysTask != null){
                        //更新任务状态:由待渲染状态》渲染中
                        Boolean msgSendIsSuccess = Boolean.FALSE;//消息是否发送成功
                        this.updateNonPaymentTaskNew(sysTask,null,payOrder.getPayType(),payOrder.getPayState(),msgSendIsSuccess);
                    }else{
                        //处理？可能是序列号支付
                    }
                    //websocket异常：发送预警邮件
//                    this.sendEmail();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return flag;
    }

    /**
     * 渲染失败退款到账户余额
     * @param sysTask
     * @param renderErroMsg
     */
    @Override
    public void renderRefund(SysTask sysTask, String renderErroMsg){
        if( sysTask != null ){
            // 找到渲染任务的订单
            List<PayOrder> payOrderList = new ArrayList<>();
            PayOrder order = null;
            // 修改为用payOrder的taskId字段关联
            if(sysTask != null && sysTask.getId() != null){
                order = this.findOneByTaskId(sysTask.getId());
                payOrderList.add(order);
            }
            PayOrder payOrder=null;
            if( payOrderList != null && payOrderList.size() > 0){
                payOrder = payOrderList.get(0);
                // 付款成功的才会退款
                if( PayState.SUCCESS.equals(payOrder.getPayState()) ) {
                    Double totalFee = new Double(payOrder.getTotalFee());// 渲染扣除金额（分）
                    logger.info(CLASS_LOG_PREFIX + "refund totalFee :" + totalFee);

                    // 更新账户余额
                    String platformBussinessType = null;
                    SysUser sysUser = sysUserService.get(payOrder.getUserId());
                    logger.info("获取用户的平台类型platformType =>{}"+sysUser.getPlatformType());
                    if(sysUser.getPlatformType().intValue() == 2){
                        platformBussinessType = "2b";
                    }else{
                        platformBussinessType = "2c";
                    }
                    PayAccount p = payAccountService.getInfoByUserIdAndPlatformBussinessType(sysUser.getId(),platformBussinessType);

                    PayAccount update = new PayAccount();
                    update.setId(p.getId());
                    update.setBalanceAmount(p.getBalanceAmount()+totalFee);
                    update.setConsumAmount(p.getConsumAmount()-totalFee);
                    update.setGmtModified(new Date());
                    payAccountService.update(update);

                    //更新订单状态
                    PayOrder payOrderNew = new PayOrder();
                    payOrderNew.setId(payOrder.getId());
                    payOrderNew.setPayState(PayState.REFUND_SUCCESS);
                    this.update(payOrderNew );
                }else{
                    logger.error(CLASS_LOG_PREFIX + "order has not been paid successfully... orderNo:"+payOrder.getOrderNo());
                }
                // 发送渲染失败告警邮件
                List<SysUser> warningUserList = sysUserService.getUserByRoleCode(SystemCommonConstant.RENDER_WARNING);
                if( warningUserList != null && warningUserList.size() > 0 ){
                    StringBuffer toEmailsStr = new StringBuffer();
                    int count = 0;
                    for( SysUser warningUser : warningUserList ){
                        if( StringUtils.isNotBlank(warningUser.getEmail()) ) {
                            if( (count+1) < warningUserList.size() ) {
                                toEmailsStr.append(warningUser.getEmail() + ",");
                            }else{
                                toEmailsStr.append(warningUser.getEmail());
                            }
                            count++;
                        }
                    }
                    if( toEmailsStr.length() > 0 ) {
                        String[] toEmails = toEmailsStr.toString().split(",");
                        StringBuffer stringBuffer = new StringBuffer("Dear All ,<br>");
                        stringBuffer.append("  有渲染任务异常结束。渲染任务编码：<br>");
                        stringBuffer.append("<font color=\"red\"><strong>"+sysTask.getBusinessCode()+"</strong></font><br>");

                        SysDictionary sysDictionary = null;
                        if (sysTask.getCreator() != null){
                            stringBuffer.append("  用户名:"+sysTask.getCreator()+"<br>");
                            List<SysUser> oneByLoginName = sysUserService.findOneByLoginName(sysTask.getCreator());
                            if (oneByLoginName != null && oneByLoginName.size() >0){
                                sysDictionary = sysDictionaryService.getSysDictionaryByValue(SysDictionaryConstant.SYS_USER_TYPE, oneByLoginName.get(0).getUserType());
                            }else{
                                logger.error(CLASS_LOG_PREFIX + "未查到该用户："+sysTask.getCreator());
                            }
                        }
                        if (sysDictionary != null){
                            stringBuffer.append("  用户类型："+sysDictionary.getName()+"<br>");
                        }
                        stringBuffer.append("异常信息："+renderErroMsg);
                        String subject = "【渲染异常告警】";
                        SendEmail.massSend(toEmails, subject, stringBuffer.toString());
                        logger.info(CLASS_LOG_PREFIX + "有渲染任务异常结束。渲染任务编码："+sysTask.getBusinessCode());
                    }
                }else{
                    logger.error(CLASS_LOG_PREFIX + "warningUserList is null");
                }
            }else{
                logger.error(CLASS_LOG_PREFIX + "not found payOrder... orderNo: null.");
            }
        }
    }

    public void sendEmail(){
        // 发送邮件
        List<SysUser> warningUserList = sysUserService.getUserByRoleCode(SystemCommonConstant.RENDER_WARNING);
        if( warningUserList != null && warningUserList.size() > 0 ){
            StringBuffer toEmailsStr = new StringBuffer();
            int count = 0;
            for( SysUser warningUser : warningUserList ){
                if( StringUtils.isNotBlank(warningUser.getEmail()) ) {
                    if( count < warningUserList.size() ) {
                        toEmailsStr.append(warningUser.getEmail() + ",");
                    }else{
                        toEmailsStr.append(warningUser.getEmail());
                    }
                    count++;
                }
            }
            if( toEmailsStr.length() > 0 ) {
                String[] toEmails = toEmailsStr.toString().split(",");
                StringBuffer stringBuffer = new StringBuffer("Dear All ,<br>");
                stringBuffer.append("websocket服务器可能已中断," + WebSocketUtils.webSocket.getString("app.webSocket.payOrder"));
                String subject = "【websocket链接异常】";
                SendEmail.massSend(toEmails, subject, stringBuffer.toString());
            }
        }else{
            logger.error(CLASS_LOG_PREFIX + "warningUserList is null");
        }
    }


    /**
     * 消费明细数量
     * @param payOrderSearch
     * @return
     */
    @Override
    public int getExpenseRecordCount(PayOrderSearch payOrderSearch){
        return payOrderMapper.selectExpenseRecordCount(payOrderSearch);
    }

    /**
     * 消费明细列表
     * @param payOrderSearch
     * @return
     */
    @Override
    public List<PayOrder> getExpenseRecordList(PayOrderSearch payOrderSearch){

        List<PayOrder> list = payOrderMapper.selectExpenseRecordList(payOrderSearch);
        for(PayOrder order : list){
            if(BizType.RECHARGE.equals(order.getProductType())){
                if(StringUtils.isNotEmpty(order.getPayType()) && PayType.WXPAY.equals(order.getPayType())){
                    order.setPlanName(com.sandu.pay.order.model.enums.PayType.WEIXIN.getName());
                }else if(StringUtils.isNotEmpty(order.getPayType()) && PayType.ALIPAY.equals(order.getPayType())){
                    order.setPlanName(com.sandu.pay.order.model.enums.PayType.ALIPAY.getName());
                }
            }
        }

        return list;
    }

    /**
     * 组装系统字段
     * @param order
     * @param loginUser
     */
    public void sysSave(PayOrder order, LoginUser loginUser){
        if (order != null) {
            if (order.getId() == null) {
                order.setUserId(loginUser.getId());
                order.setGmtCreate(new Date());
                order.setCreator(loginUser.getLoginName());
                order.setIsDeleted(0);
                if (order.getSysCode() == null || "".equals(order.getSysCode())) {
                    order.setSysCode(
                            Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }
            order.setGmtModified(new Date());
            order.setModifier(loginUser.getLoginName());
        }
    }

    @Override
    public int updateTaskId(PayOrder payOrder) {
        return payOrderMapper.updateTaskId(payOrder);

    }


    /**
     * 任务状态变更
     */
    @Override
    public String updateNonPaymentTaskNew(SysTask sysTask, LoginUser loginUser, String payType, String payState, Boolean msgSendIsSuccess) {
        //--------
        logger.info("任务状态变更2018-1-10:start");
        Integer planId = sysTask.getBusinessId();
        DesignPlan designPlan = sysTaskService.getDesignPlan(planId);
        if (designPlan == null) {
            logger.error("------设计方案未找到:id:" + planId);
            return "设计方案未找到";
        }

        SysTask task2 = new SysTask();
        task2.setId(sysTask.getId());
        String remark= "";
        if(msgSendIsSuccess == null){
            //余额支付
            if( PayState.SUCCESS.equals(payState)){//付款成功
                task2.setState(SysTaskStatus.RENDERING);
                if(PayType.ALIPAY.equals(payType)){
                    remark = "付款类型：支付宝,支付状态:支付成功";
                }else if(PayType.WXPAY.equals(payType)){
                    remark = "付款类型:微信,支付状态：支付成功";
                }else if(PayType.PREDEPOSIT.equals(payType)){
                    remark = "付款类型:余额,支付状态：支付成功";
                }else if(PayType.PAY_MODEL_CONFIG_PAY.equals(payType)){
                    remark = "付款类型:包年包月,支付状态：支付成功";
                }else if(PayType.PAY_MODEL_CONFIG_PAY_FRANCHISER.equals(payType)){
                    remark = "付款类型:经销商包年包月,支付状态：支付成功";
                }else if(PayType.PAY_MODEL_COMPANY_PAY.equals(payType)){
                    remark = "付款类型:品牌商家免费渲染,支付状态：支付成功";
                }else{
                    remark = "未知支付类型";
                    logger.error("付款类型:未知"+payType+"+,支付状态：支付成功");
                }
            }else{//付款失败
                if(PayType.ALIPAY.equals(payType)){
                    remark = "付款类型：支付宝,支付状态:支付失败";
                }else if(PayType.WXPAY.equals(payType)){
                    remark = "付款类型:微信,支付状态：支付失败";
                }else if(PayType.PREDEPOSIT.equals(payType)){
                    remark = "付款类型:余额,支付状态：支付失败";
                }else if(PayType.PAY_MODEL_CONFIG_PAY.equals(payType)){
                    remark = "付款类型:包年包月,支付状态：支付失败";
                }else if(PayType.PAY_MODEL_CONFIG_PAY_FRANCHISER.equals(payType)){
                    remark = "付款类型:经销商包年包月,支付状态：支付失败";
                }else if(PayType.PAY_MODEL_COMPANY_PAY.equals(payType)){
                    remark = "付款类型:品牌商家免费渲染,支付状态：支付失败";
                }else{
                    remark = "未知支付类型";
                    logger.error("付款类型:未知"+payType+"+,支付状态：支付失败");
                }
            }

        }else{
            if(msgSendIsSuccess){//扫码支付：消息发送成功
                if( PayState.SUCCESS.equals(payState)){//付款成功
                    task2.setState(SysTaskStatus.RENDERING);
                    if(PayType.ALIPAY.equals(payType)){
                        remark = "付款类型：支付宝,支付状态:支付成功,通知app端websocket支付消息：发送成功";
                    }else if(PayType.WXPAY.equals(payType)){
                        remark = "付款类型:微信,支付状态：支付成功,通知app端websocket支付消息：发送成功";
                    }else if(PayType.PREDEPOSIT.equals(payType)){
                        remark = "付款类型:余额,支付状态：支付成功,通知app端websocket支付消息：发送成功";
                    }else{
                        remark = "未知支付类型";
                        logger.error("付款类型:未知"+payType+"+,支付状态：支付成功,通知app端websocket支付消息：发送成功");
                    }
                    //记录消息发送成功时间
                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(currentTime);
                    task2.setAtt1(dateString);
                }else if(PayState.PAY_ERROR.equals(payState)){//付款失败
                    if(PayType.ALIPAY.equals(payType)){
                        remark = "付款类型：支付宝,支付状态:支付失败,通知app端websocket支付消息：发送成功";
                    }else if(PayType.WXPAY.equals(payType)){
                        remark = "付款类型:微信,支付状态：支付失败,通知app端websocket支付消息：发送成功";
                    }else if(PayType.PREDEPOSIT.equals(payType)){
                        remark = "付款类型:余额,支付状态：支付失败,通知app端websocket支付消息：发送成功";
                    }else{
                        remark = "未知支付类型";
                        logger.error("付款类型:未知"+payType+"+,支付状态：支付失败,通知app端websocket支付消息：发送成功");
                    }
                }else{

                }
            }else if(!msgSendIsSuccess){//扫码支付：消息发送失败
                task2.setState(SysTaskStatus.RENDER_FAILD);
                if( PayState.SUCCESS.equals(payState)){//付款成功
                    if(PayType.ALIPAY.equals(payType)){
                        remark = "付款类型：支付宝,支付状态:支付成功,通知app端websocket支付消息：发送失败，已退款到用户余额";
                    }else if(PayType.WXPAY.equals(payType)){
                        remark = "付款类型:微信,支付状态：支付成功,通知app端websocket支付消息：发送失败，已退款到用户余额";
                    }else if(PayType.PREDEPOSIT.equals(payType)){
                        remark = "付款类型:余额,支付状态：支付成功,通知app端websocket支付消息：发送失败，已退款到用户余额";
                    }else{
                        remark = "未知支付类型";
                        logger.error("付款类型:未知"+payType+"+,支付状态：支付成功,通知app端websocket支付消息：发送失败，已退款到用户余额");
                    }
                }else if(PayState.PAY_ERROR.equals(payState)){//付款失败
                    if(PayType.ALIPAY.equals(payType)){
                        remark = "付款类型：支付宝,支付状态:支付失败,通知app端websocket支付消息：发送失败";
                    }else if(PayType.WXPAY.equals(payType)){
                        remark = "付款类型:微信,支付状态：支付失败,通知app端websocket支付消息：发送失败";
                    }else if(PayType.PREDEPOSIT.equals(payType)){
                        remark = "付款类型:余额,支付状态：支付失败,通知app端websocket支付消息：发送失败";
                    }else{
                        remark = "未知支付类型";
                        logger.error("付款类型:未知"+payType+"+,支付状态：支付失败,通知app端websocket支付消息：发送失败");
                    }
                }else{

                }
                //付款成功但是消息发送失败的需要退款到用户余额
                this.renderRefund(sysTask, remark);
            }else{

            }
        }
        task2.setRemark(remark);
        task2.setGmtModified(new Date());
        sysTaskService.update(task2);
        logger.info("任务状态变更2018-1-10:end");
        return "success";
    }

    /**
     * 购买户型
     */
    @Override
    @Transactional

    public ResultMessage addExpandHouseOrder(Integer userId, Integer expandType, String platformCode) {
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            message.setMessage("平台编码：platformCode{}为空" + platformCode);
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            message.setMessage("平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            return message;
        }
        SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue(SysDictionaryConstant.EXPAND_HOUSE_TYPE, expandType);
        logger.info(CLASS_LOG_PREFIX + "度币支付购买户型类型数据字典查询:" + gson.toJson(sysDictionary));
        Double expandIntegral;
        Integer expandNumber;
        String productName = "";
        String productType = "";
        if (sysDictionary != null && sysDictionary.getId() != null) {
            productName = sysDictionary.getName();
            productType = sysDictionary.getValuekey();
            expandIntegral = Double.parseDouble(sysDictionary.getAtt1());
            expandNumber = Integer.parseInt(sysDictionary.getAtt2());
        } else {
            logger.info(CLASS_LOG_PREFIX + "找不到对应数据字典购买户型类型！value = " + expandType + ";type=" + SysDictionaryConstant.EXPAND_HOUSE_TYPE);
            message.setMessage(CLASS_LOG_PREFIX + "找不到对应数据字典购买户型类型！value = " + expandType + ";type=" + SysDictionaryConstant.EXPAND_HOUSE_TYPE);
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            return message;
        }
        // 户型的单价小于 0 和支付的度币小于0
        if (expandNumber <= 0 || expandIntegral <= 0) {
            message.setMessage(CLASS_LOG_PREFIX + "户型消耗度币值异常！value = " + expandType + ";type=" + SysDictionaryConstant.EXPAND_HOUSE_TYPE);
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            return message;
        }
        // (单个度币 * 10) *需要购买的户型数
        double totalFee = expandIntegral * 10;
        logger.info(productName + "需要支付的度币  ==> " + totalFee);
        // 直接从账户余额中扣除
        logger.info("totalFee" + totalFee + ";PayType.PREDEPOSIT ：" + PayType.PREDEPOSIT + "sysDictionaryID="
                + sysDictionary.getId() + "productType=" + productType + "productName:" + productName);
        PayOrder payOrder = getOrder((int)totalFee, PayType.PREDEPOSIT, sysDictionary.getId(),
                productType, productName, PayProductConstans.PAY_EXPEND_HOUSE_DESC, TradeType.PREDEPOSIT);

        payOrder.setProductName(productName);
        SysUser user = sysUserService.get(userId);
        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                logger.info("用户id：userId:{}" + userId + ",平台id：platformId:{}" + basePlatform.getId() + "的度币信息为空");
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        if (user != null) {
            if (balanceAmount < totalFee) {
                logger.info("账户度币不足，请充值. 用户剩余度币 ==> " + balanceAmount, "，需要支付的度币 ==> " + totalFee);
                message.setStatus(MessageStateType.PAY_INTEGRAL_INSUFFICIENT_STATE);
                message.setMessage("账户度币不足，请充值.");
                return message;
            } else {
                LoginUser loginUser = new LoginUser(userId, user.getUserType(), user.getUserName(), user.getMobile());
                try {
                    // 插入支付订单
                    sysSave(payOrder, loginUser);
                    if (userId > 0) {
                        payOrder.setUserId(userId);
                    }
                    payOrder.setPlatformId(basePlatform.getId()); // 平台id
                    payOrder.setBizType(BizType.BUY);
                    payOrder.setFinanceType(FinanceType.OUT);
                    payOrder.setPayState(PayState.SUCCESS);
                    payOrder.setPayType(PayType.PREDEPOSIT);

                    payOrder.setCurrentIntegral((int) (balanceAmount - totalFee));
                    payOrder.setOrderDate(new Date());
                    int id = this.addPayOrder(payOrder, loginUser);
                    if (id == 0) {
                        message.setStatus(MessageStateType.PAY_FAILED_STATE);
                        message.setMessage("保存支付单失败.");
                        return message;
                    }
                    // 更新账户度币和消费度币
                    if (updateUserFinance(payOrder.getOrderNo())) {
                        // 更新用户户型数
                        SysUser updateUser = new SysUser();
                        updateUser.setId(userId);
                        updateUser.setGmtModified(new Date());
                        updateUser.setModifier(userId + "");
                        Integer usableHouseNumber = user.getUsableHouseNumber();
                        logger.info("已有可用户型数 ==>" + usableHouseNumber);
                        usableHouseNumber = (usableHouseNumber != null) ? (usableHouseNumber + expandNumber) : expandNumber;
                        logger.info("购买后可用户型数 ==>" + usableHouseNumber);
                        updateUser.setUsableHouseNumber(usableHouseNumber);
                        int update = sysUserService.update(updateUser);
                        if (update <= 0) {
                            message.setSuccess(false);
                            message.setMessage("更新用户户型数失败.");
                            return message;
                        } else {
                            message.setSuccess(true);
                            message.setMessage("支付成功.");
                            return message;
                        }
                    } else {
                        PayOrder newPayOrder = new PayOrder();
                        newPayOrder.setPayState(PayState.PAY_ERROR);
                        newPayOrder.setOrderNo(payOrder.getOrderNo());
                        this.updatePayState(newPayOrder);
                        message.setSuccess(false);
                        message.setStatus(MessageStateType.PAY_FAILED_STATE);
                        message.setMessage("支付失败.");
                    }
                } catch (Exception e) {
                    logger.error("购买度币异常",e);
                    message.setSuccess(false);
                    message.setStatus(MessageStateType.PAY_OTHER_STATE);
                    message.setMessage("度币支付发生异常.");
                }
            }
        } else {
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            message.setMessage("当前用户信息为空.");
        }
        return message;
    }

    /**
     * 生成订单(移动端)
     *
     * @throws UnknownHostException
     */
    @SuppressWarnings({ "rawtypes" })
    @Override

    public Object replaceRecord(MobileProductReplace model, String platformCode) throws UnknownHostException {

        if(model == null) {
            return new ResponseEnvelope(false,"对象不能为空");
        }
        if (StringUtils.isEmpty(platformCode)) {
            return new ResponseEnvelope(false,"平台编码：platformCode{}为空" + platformCode);
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            return new ResponseEnvelope(false,"平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
        }
        ResponseEnvelope message = new ResponseEnvelope();
        model.setPayType(PayType.PREDEPOSIT); // 预存款支付
        Integer userId = model.getUserId();//用户id
        Double totalFee = model.getTotalFee();//订单总金额，单位为分
        String payType = model.getPayType();//支付类型
        String productType = model.getProductType();//订单产品类型(具体可以见ProductType.java)
        Integer productId = 0;
        String productDesc = "移动端替换产品";
        String tradeType = "";
        if(userId != null && totalFee != null && totalFee > 0.00 &&
                org.apache.commons.lang3.StringUtils.isNoneBlank(payType) && org.apache.commons.lang3.StringUtils.isNoneBlank(productType)) {
            SysUser sysUser = sysUserService.get(userId);
            logger.info("渲染的用户id：userId:{}" + sysUser.getId() + "，用户类型：userType:{}" + sysUser.getUserType());
            if (UserConstants.USER_TYPE_TOURIST == sysUser.getUserType().intValue()) {
                //游客
                totalFee = 0d; // 游客渲染总价格为0
                PayOrder payOrder = this.getOrder(totalFee.intValue(), payType, productId, productType, productDesc, tradeType,userId,-1, basePlatform.getId());
                if (payOrder == null) {
                    message.setSuccess(false);
                    message.setMessage("生成订单失败");
                    return message;
                }
                if(payOrder != null) {
                    message.setObj(payOrder.getOrderNo());
                }
            } else {
                //非游客
                int balanceAmount = 0;
                CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
                if (null != companyFranchiserGroup) {
                    balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
                } else {
                    PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
                    if (null == payAccount) {
                        message.setMessage("用户度币信息为空，生成订单失败");
                        return message;
                    }
                    balanceAmount = payAccount.getBalanceAmount().intValue();
                }
                if(balanceAmount < totalFee.intValue()) {
                    return new ResponseEnvelope(false,"您的余额不足，请到PC端充值！");
                }
                PayOrder payOrder = this.getOrder(totalFee.intValue(), payType, productId, productType, productDesc, tradeType,userId,-1, basePlatform.getId());
                if (payOrder == null) {
                    message.setSuccess(false);
                    message.setMessage("生成订单失败");
                    return message;
                }
                // 修改用户余额
                message = this.updateBalance(payOrder.getOrderNo());
                if(payOrder != null) {
                    message.setObj(payOrder.getOrderNo());
                }
            }
        }else {
            message.setSuccess(false);
            message.setMessage("参数不合法!");
        }
        message.setMessage("生成订单成功");
        message.setSuccess(true);
        return message;
    }


    /**
     * 修改用户余额的方法
     */
    @SuppressWarnings("rawtypes")

    private ResponseEnvelope updateBalance(String  orderNo) {
        ResponseEnvelope message = new ResponseEnvelope();
        ResultMessage resultMessage = payAccountService.updateAmountForOrder(orderNo);
        message.setSuccess(resultMessage.isSuccess());
        message.setMessage(resultMessage.getMessage());
        message.setSuccess(resultMessage.isSuccess());
        return message;
    }
    /**
     * 生成订单的方法
     *
     */
    public PayOrder getOrder(int totalFee, String payType, Integer productId, String productType, String productDesc,
                             String tradeType, Integer userId, Integer taskId, Integer platformId) {
        PayOrder payOrder = new PayOrder();
        payOrder.setPlatformId(platformId);
        payOrder.setOrderDate(new Date());
        payOrder.setPayState(PayState.NOTPAY);
        payOrder.setOrderNo(IdGenerator.generateNo());
        payOrder.setTotalFee(new Integer(totalFee));
        payOrder.setPayType(payType);
        payOrder.setProductId(productId);
        payOrder.setProductType(productType);
        payOrder.setProductName(ProductUtil.getProductNameByProductType(productType));
        payOrder.setProductDesc(productDesc);
        payOrder.setTradeType(tradeType);
        payOrder.setBizType(BizType.BUY);
        payOrder.setFinanceType(FinanceType.OUT);
        payOrder.setPayState(PayState.SUCCESS);

        String flag = Utils.getValue("auto.task.test.flag", "false");
        if ("true".equals(flag)) {
            // 渲染测试模拟用户
            if (userId == null || userId == 0) {
                userId = 36;
            }
        }
        // 插入支付订单
        if (userId > 0) {
            payOrder.setUserId(userId);
        }
        SysUser sysUser = sysUserService.get(userId);//根据用户id查询用户 的手机号和度币
        int balanceAmount = 0;
        //非游客的账户余额要到数据库查询，游客的账户余额为0
        if (UserConstants.USER_TYPE_TOURIST != sysUser.getUserType().intValue()) {
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
            if (null != companyFranchiserGroup) {
                balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
            } else {
                PayAccount payAccount = payAccountService.getPayAccountInfo(userId, platformId);
                balanceAmount = null == payAccount ? 0 : payAccount.getBalanceAmount().intValue();
            }
        }
        if (payOrder.getId() == null) {
            payOrder.setUserId(sysUser.getId());
            payOrder.setGmtCreate(new Date());
            payOrder.setCreator(sysUser.getUserName());
            payOrder.setIsDeleted(0);
            if (payOrder.getSysCode() == null || "".equals(payOrder.getSysCode())) {
                payOrder.setSysCode(
                        Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            }
        }

        payOrder.setGmtModified(new Date());
        payOrder.setModifier(sysUser.getUserName());


        payOrder.setCurrentIntegral(new Integer(balanceAmount - totalFee));
        // FIXME: 插入一条渲染失败款的记录到订单表pay_order，稍后修改到支付退款表pay_refund
        if ("refunds".equals(productType)) {
            payOrder.setProductType("recharge");
            payOrder.setProductName("渲染失败退款");
            payOrder.setProductDesc("渲染失败退款");
            payOrder.setBizType(BizType.REFUND);
            payOrder.setFinanceType(FinanceType.IN);
            payOrder.setPayState(PayState.SUCCESS);


            payOrder.setCurrentIntegral(new Integer(balanceAmount + totalFee));
        }
        payOrder.setTaskId(taskId);

        int id = payOrderMapper.insertSelective(payOrder);

        if (id == 0) {
            return null;
        }

        return payOrder;

    }

    @Override
    public boolean updatePayoOrder(String orderNo, Integer taskId) {
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderNo(orderNo);
        payOrder.setTaskId(taskId);
        int id = payOrderMapper.updateOrderByTaskId(payOrder);
        if (id == 0) {
            return false;
        }else {
            return true;
        }
    }

    /**
     * 度币充值----2c移动端(APP支付)-----------------
     *
     * @param productId
     * @param totalFee1
     * @param payType
     * @param loginUser
     * @param sysDictionary
     * @param platformCode
     * @return
     */
    @Override
    public ResultMessage rechargeIntegralByAppPay(Integer productId, int totalFee1, String payType, LoginUser loginUser, SysDictionary sysDictionary, String platformCode){
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            message.setMessage("平台编码：platformCode{}为空");
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        String tradeType = TradeType.SCANCODE;
        Integer userId = loginUser==null?0:loginUser.getId();
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            message.setMessage("用户信息为空.");
            return message;
        }
        if (sysDictionary == null || sysDictionary.getValue()==null) {
            message.setMessage("数据异常.");
            return message;
        }
        int originalPrice= Utils.getIntValue(sysDictionary.getAtt1());
        int discountPrice = sysDictionary.getValue();
        PayOrder payOrder = getOrder(originalPrice, payType, PayProductConstans.RECHARGE_PRODUCT_NAME, PayProductConstans.RECHARGE_PRODUCT_DESC, tradeType);
        ScanPayReqData reqData = new ScanPayReqData();
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        int balanceAmount = 0;
        if (null == companyFranchiserGroup) {
            // 不具备经销商权限
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        } else {
            // 具备经销商权限
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        }
        try {
            payOrder.setProductType(PayProductConstans.PAY_PRODUCT_TYPE);
            sysSave(payOrder, loginUser);
            if (userId > 0) {
                payOrder.setUserId(userId);
            }
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            payOrder.setProductId(productId);
            payOrder.setBizType(BizType.RECHARGE);
            payOrder.setFinanceType(FinanceType.IN);

            payOrder.setCurrentIntegral((int)(balanceAmount+Double.parseDouble(sysDictionary.getAtt1())));
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                message.setMessage("保存在线充值单失败.");
                message.setSuccess(Boolean.FALSE);
                return message;
            }
            payOrder.setId(id);
            reqData.setOrderId(payOrder.getId());
            if (payType.equalsIgnoreCase(PayType.WXPAY)) {
                String flag = "SUCCESS";
                UnifiedOrderService orderService = new UnifiedOrderService();
                // 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
                String timeExpire = Utils.getTimeExpire(30);

                String wxNotifyUrl = ResourceConfig.WX_PAY_NOTIFY_URL;
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(ResourceConfig.WXPAY_APPPAY_APPID,
                        ResourceConfig.WXPAY_APPPAY_MUCHID, ResourceConfig.WXPAY_APPPAY_KEY, PayProductConstans.RECHARGE_PRODUCT_NAME,
                        payOrder.getOrderNo(), discountPrice,
                        WxTradeType.APP, timeExpire, wxNotifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    Map<String, Object> packageParams = new HashMap<String, Object>();
                    packageParams.put("appid", ResourceConfig.WXPAY_APPPAY_APPID);
                    packageParams.put("partnerid", ResourceConfig.WXPAY_APPPAY_MUCHID);
                    packageParams.put("prepayid", unifiedOrderResData.getPrepay_id());
                    packageParams.put("package", "Sign=WXPay");
                    packageParams.put("noncestr", System.currentTimeMillis() + "");
                    packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
                    String packageSign = Signature.getSign(packageParams, ResourceConfig.WXPAY_APPPAY_KEY);
                    packageParams.put("sign", packageSign);
                    message.setSuccess(Boolean.TRUE);
                    message.setObj(packageParams);
                    message.setMessage("success");
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setNotifyUrl(wxNotifyUrl);
                    this.update(payOrder);
                    logger.info(CLASS_LOG_PREFIX+"2c移动端(APP支付)成功生成充值单");

                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(2);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount((double) Utils.getIntValue(sysDictionary.getAtt1()));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                }
            }
            if (payType.equalsIgnoreCase(PayType.ALIPAY)) {

                Map<String, Object> formMap = new HashMap<String, Object>();
                String alNotifyUrl = ResourceConfig.ALIPAY_NOTIFY_URL;
                String form = ScanPayUtil.addScanpayAppOrder(payOrder, alNotifyUrl);
                payOrder.setNotifyUrl(alNotifyUrl);
                if (StringUtils.isEmpty(form)) {
                    message.setSuccess(Boolean.FALSE);
                    message.setMessage("下单异常.");
                    return message;
                } else {
                    formMap.put("form", form);
                    message.setSuccess(Boolean.TRUE);
                    message.setObj(formMap);
                    message.setOrderNo(String.valueOf(payOrder.getId()));
                    message.setMessage("success");
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);

                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(3);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount((double) Utils.getIntValue(sysDictionary.getAtt1()));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(Boolean.FALSE);
            message.setMessage("下单异常.");
        }

        return message;
    }

    @Override
    public ResultMessage unifiedOrderForWxAppPay(PayMobileLoginVo payMobileLoginVo, String platformCode) {
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            message.setMessage("平台编码：platformCode{}为空" + platformCode);
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        String tradeType = TradeType.APP;
        Integer userId = payMobileLoginVo == null ? 0 : payMobileLoginVo.getUserId();
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            message.setMessage("用户信息为空");
            return message;
        }
        UserJurisdiction userJurisdiction = userJurisdictionService.getMobile2bUserJurisdiction(userId, basePlatform.getId());
        if (null != userJurisdiction) {
            message.setMessage("您已开通移动端，请到登录页面进行登录");
            return message;
        }
        if (!payMobileLoginVo.getPayType().equals(PayType.WXPAY)) {
            message.setMessage("支付方式异常");
            return message;
        }
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey(SysDictionaryConstant.PAY_MOBILE_LOGIN_AMOUNT_TYPE,
                SysDictionaryConstant.PAY_MOBILE_LOGIN_AMOUNT_KEY);
        if (null == sysDictionary || null == sysDictionary.getAtt1()) {
            message.setMessage("找不到数据字典中开通移动端所需要的金额");
            return message;
        }
        Integer productPrice = Double.valueOf(sysDictionary.getAtt1()).intValue();
        logger.info("开通移动端app支付的产品价钱（单位为：分）：" + productPrice);
        String productName = PayProductConstans.MOBILE_OPENED_AND_POSTPONE_PRODUCT_NAME.replaceAll("@price", String.valueOf(productPrice/100));
        PayOrder payOrder = getOrder(productPrice, payMobileLoginVo.getPayType(), productName,
                productName, tradeType);
        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        try {
            // 组装字段
            sysSave(payOrder, loginUser);
            payOrder.setPlatformId(basePlatform.getId());
            payOrder.setProductType(PayProductConstans.MOBILE_OPENED_AND_POSTPONE_TYPE);
            payOrder.setProductId(sysDictionary.getId());
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                message.setMessage("保存支付单失败.");
                message.setSuccess(Boolean.FALSE);
                return message;
            }
            payOrder.setId(id);
            // 微信app支付
            if (payMobileLoginVo.getPayType().equalsIgnoreCase(PayType.WXPAY)) {
                String flag = "SUCCESS";
                UnifiedOrderService orderService = new UnifiedOrderService();
                // 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
                String timeExpire = Utils.getTimeExpire(30);
                //移动端登录支付回调url
                String wxNotifyUrl = ResourceConfig.NOTIFY_URL_MOBILE_PAY;
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(ResourceConfig.WXPAY_APPPAY_APPID,
                        ResourceConfig.WXPAY_APPPAY_MUCHID, ResourceConfig.WXPAY_APPPAY_KEY, productName,
                        payOrder.getOrderNo(), productPrice,
                        WxTradeType.APP, timeExpire, wxNotifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    Map<String, Object> packageParams = new HashMap<String, Object>();
                    packageParams.put("appid", ResourceConfig.WXPAY_APPPAY_APPID);
                    packageParams.put("partnerid", ResourceConfig.WXPAY_APPPAY_MUCHID);
                    packageParams.put("prepayid", unifiedOrderResData.getPrepay_id());
                    packageParams.put("package", "Sign=WXPay");
                    packageParams.put("noncestr", System.currentTimeMillis() + "");
                    packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
                    String packageSign = Signature.getSign(packageParams, ResourceConfig.WXPAY_APPPAY_KEY);
                    packageParams.put("sign", packageSign);
                    message.setSuccess(Boolean.TRUE);
                    message.setObj(packageParams);
                    message.setMessage("success");
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setNotifyUrl(wxNotifyUrl);
                    this.update(payOrder);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(Boolean.FALSE);
            message.setMessage("下单异常.");
        }

        return message;
    }

    /**
     *
     * 移动端获取微信h5支付需要重定向的url
     *
     * @param payMobileLoginVo
     * @return
     */
    @Override

    public ResultMessage getMobilePostponeWeChatPayUrl(PayMobileLoginVo payMobileLoginVo, String platformCode) {
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            logger.info("平台编码：platformCode{}为空" + platformCode);
            message.setMessage("平台编码：platformCode{}为空" + platformCode);
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        String tradeType = TradeType.H5;
        Integer userId = payMobileLoginVo == null ? 0 : payMobileLoginVo.getUserId();
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            message.setMessage("用户信息为空");
            return message;
        }
        UserJurisdiction userJurisdiction = userJurisdictionService.getMobile2bUserJurisdiction(userId, basePlatform.getId());
        if (null != userJurisdiction) {
            message.setMessage("您已开通移动端，请到登录页面进行登录");
            return message;
        }
        if (!payMobileLoginVo.getPayType().equals(PayType.WXPAY)) {
            message.setMessage("支付方式异常");
            return message;
        }
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey(SysDictionaryConstant.PAY_MOBILE_LOGIN_AMOUNT_TYPE,
                SysDictionaryConstant.PAY_MOBILE_LOGIN_AMOUNT_KEY);
        if (null == sysDictionary || null == sysDictionary.getAtt1()) {
            message.setMessage("找不到数据字典中开通移动端所需要的金额");
            return message;
        }
        Integer productPrice = Double.valueOf(sysDictionary.getAtt1()).intValue();
        logger.info("开通移动端h5支付的产品价钱（单位为：分）：" + productPrice);
        String productName = PayProductConstans.MOBILE_OPENED_AND_POSTPONE_PRODUCT_NAME.replaceAll("@price", String.valueOf(productPrice/100));
        PayOrder payOrder = getOrder(productPrice, payMobileLoginVo.getPayType(), productName,
                productName, tradeType);
        Map<String, Object> urlMap = new HashMap<String, Object>();
        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        try {
            // 组装字段
            sysSave(payOrder, loginUser);
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            payOrder.setProductType(PayProductConstans.MOBILE_OPENED_AND_POSTPONE_TYPE);
            payOrder.setProductId(sysDictionary.getId());
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);

            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                message.setMessage("保存支付单失败.");
                message.setSuccess(Boolean.FALSE);
                return message;
            }
            payOrder.setId(id);
            // 微信h5支付
            if (payMobileLoginVo.getPayType().equalsIgnoreCase(PayType.WXPAY)) {
                String flag = "SUCCESS";
                UnifiedOrderService orderService = new UnifiedOrderService();
                // 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
                String timeExpire = Utils.getTimeExpire(30);
                //移动端登录支付回调url
                String wxNotifyUrl = ResourceConfig.NOTIFY_URL_MOBILE_PAY;
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(
                        productName, payOrder.getOrderNo(), productPrice,
                        WxTradeType.MWEB, timeExpire, wxNotifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    String redirect_url = ResourceConfig.WXPAY_REDIRECT_URL + payOrder.getOrderNo();
                    logger.info("h5 return url:" + unifiedOrderResData.getMweb_url() + ",微信同步的url是：" + redirect_url );
                    urlMap.put("mwebUrl", unifiedOrderResData.getMweb_url()+"&redirect_url=" + java.net.URLEncoder.encode(redirect_url,   "utf-8"));
                    urlMap.put("orderId", payOrder.getId());
                    message.setSuccess(Boolean.TRUE);
                    message.setObj(urlMap);
                    message.setMessage("success");
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(Boolean.FALSE);
            message.setMessage("下单异常.");
        }

        return message;
    }

    /**
     *
     * 移动端度币支付开通移动端登录
     *
     * @param payMobileLoginVo
     * @return
     */
    @Override

    public ResultMessage addMobileLoginIntegralPay(PayMobileLoginVo payMobileLoginVo, String platformCode) {
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            logger.info("pc平台编码：platformCode{}为空" + platformCode);
            message.setMessage("pc平台编码：platformCode{}为空" + platformCode);
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("pc平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            message.setMessage("pc平台信息：basePlatform{}为空");
            return message;
        }
        String tradeType = TradeType.PREDEPOSIT;
        Integer userId = payMobileLoginVo == null ? 0 : payMobileLoginVo.getUserId();
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            message.setMessage("用户信息为空");
            return message;
        }
        UserJurisdiction userJurisdiction = userJurisdictionService.getMobile2bUserJurisdiction(userId, basePlatform.getId());
        if (null != userJurisdiction) {
            message.setMessage("您已开通移动端，请到登录页面进行登录");
            return message;
        }
        if (!payMobileLoginVo.getPayType().equals(PayType.PREDEPOSIT)) {
            message.setMessage("支付方式异常");
            return message;
        }
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey(SysDictionaryConstant.PAY_MOBILE_LOGIN_INTEGRAL_TYPE,
                SysDictionaryConstant.PAY_MOBILE_LOGIN_INTEGRAL_KEY);
        if (null == sysDictionary || null == sysDictionary.getAtt1()) {
            message.setMessage("找不到数据字典中开通移动端所需要的度币");
            return message;
        }
        int totalFee = Double.valueOf(sysDictionary.getAtt1()).intValue();
        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        if (balanceAmount < totalFee) {
            message.setMessage("度币不足，请使用微信充值或支付宝充值");
            return message;
        }
        String productName = PayProductConstans.MOBILE_OPENED_AND_POSTPONE_INTEGRAL_NAME.replaceAll("@integral", String.valueOf(totalFee/10));
        PayOrder payOrder = getOrder(totalFee, payMobileLoginVo.getPayType(), productName,
                productName, tradeType);
        try {
            sysSave(payOrder, loginUser);
            if (userId > 0) {
                payOrder.setUserId(userId);
            }
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            payOrder.setProductType(PayProductConstans.MOBILE_OPENED_AND_POSTPONE_TYPE);
            payOrder.setProductId(sysDictionary.getId());
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);
            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                message.setMessage("保存支付单失败.");
                return message;
            }
            // 更新用户的移动端开通信息
            this.updateUserMobileInfo(payOrder.getOrderNo());
            // 更新账户余额和消费金额
            updateUserFinance(payOrder.getOrderNo());
            message.setSuccess(true);
            message.setMessage("支付成功.");
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMessage("预付款支付开通手机端登录发生异常.");
        }
        message.setOrderNo(payOrder.getOrderNo());
        return message;
    }



    /**
     *
     * 移动端支付宝App支付开通移动端登录0---------------------------------------------------APP支付--------------------------------------
     *
     * @param payMobileLoginVo
     * @return
     */
    @Override

    public ResultMessage addPostponeAppAlipay(PayMobileLoginVo payMobileLoginVo, String platformCode) {
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            logger.info("平台编码：platformCode{}为空" + platformCode);
            message.setMessage("平台编码：platformCode{}为空" + platformCode);
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        String tradeType = TradeType.APP;
        Integer userId = payMobileLoginVo == null ? 0 : payMobileLoginVo.getUserId();
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            message.setMessage("用户信息为空");
            return message;
        }
        UserJurisdiction userJurisdiction = userJurisdictionService.getMobile2bUserJurisdiction(userId, basePlatform.getId());
        if (null != userJurisdiction) {
            message.setMessage("您已开通移动端，请到登录页面进行登录");
            return message;
        }
        if (!payMobileLoginVo.getPayType().equals(PayType.ALIPAY)) {
            message.setMessage("支付方式异常");
            return message;
        }
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey(SysDictionaryConstant.PAY_MOBILE_LOGIN_AMOUNT_TYPE,
                SysDictionaryConstant.PAY_MOBILE_LOGIN_AMOUNT_KEY);
        if (null == sysDictionary || null == sysDictionary.getAtt1()) {
            message.setMessage("找不到数据字典中开通移动端所需要的金额");
            return message;
        }
        Integer productPrice = Double.valueOf(sysDictionary.getAtt1()).intValue();
        logger.info("开通移动端支付宝手机网页支付的产品价钱（单位为：分）：" + productPrice);
        String productName = PayProductConstans.MOBILE_OPENED_AND_POSTPONE_PRODUCT_NAME.replaceAll("@price", String.valueOf(productPrice/100));
        PayOrder payOrder = getOrder(productPrice, payMobileLoginVo.getPayType(), productName,
                productName, tradeType);
        Map<String, Object> formMap = new HashMap<String, Object>();
        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        try {
            // 组装字段
            sysSave(payOrder, loginUser);
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            payOrder.setProductType(PayProductConstans.MOBILE_OPENED_AND_POSTPONE_TYPE);
            payOrder.setProductId(sysDictionary.getId());
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);

            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                message.setMessage("保存支付单失败.");
                message.setSuccess(Boolean.FALSE);
                return message;
            }
            payOrder.setId(id);
            // 支付宝App支付
            if (payMobileLoginVo.getPayType().equalsIgnoreCase(PayType.ALIPAY)) {
                String notifyUrl =  ResourceConfig.ALIPAY_NOTIFY_URL_MOBILE_PAY;
                String form = ScanPayUtil.addScanpayAppOrder(payOrder, notifyUrl);
                payOrder.setNotifyUrl(notifyUrl);
                if (StringUtils.isEmpty(form)) {
                    message.setSuccess(Boolean.FALSE);
                    message.setMessage("下单异常.");
                    return message;
                } else {
                    formMap.put("form", form);
                    message.setSuccess(Boolean.TRUE);
                    message.setObj(formMap);
                    message.setOrderNo(String.valueOf(payOrder.getId()));
                    message.setMessage("success");
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(Boolean.FALSE);
            message.setMessage("下单异常.");
        }

        return message;

    }




    /**
     *
     * 移动端支付宝网页支付开通移动端登录
     *
     * @param payMobileLoginVo
     * @return
     */
    @Override

    public ResultMessage addPostponeAlipay(PayMobileLoginVo payMobileLoginVo, String platformCode) {
        ResultMessage message = new ResultMessage();
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
        String tradeType = TradeType.WAP;
        Integer userId = payMobileLoginVo == null ? 0 : payMobileLoginVo.getUserId();
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            message.setMessage("用户信息为空");
            return message;
        }
        UserJurisdiction userJurisdiction = userJurisdictionService.getMobile2bUserJurisdiction(userId, basePlatform.getId());
        if (null != userJurisdiction) {
            message.setMessage("您已开通移动端，请到登录页面进行登录");
            return message;
        }
        if (!payMobileLoginVo.getPayType().equals(PayType.ALIPAY)) {
            message.setMessage("支付方式异常");
            return message;
        }
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey(SysDictionaryConstant.PAY_MOBILE_LOGIN_AMOUNT_TYPE,
                SysDictionaryConstant.PAY_MOBILE_LOGIN_AMOUNT_KEY);
        if (null == sysDictionary || null == sysDictionary.getAtt1()) {
            message.setMessage("找不到数据字典中开通移动端所需要的金额");
            return message;
        }
        Integer productPrice = Double.valueOf(sysDictionary.getAtt1()).intValue();
        logger.info("开通移动端支付宝手机网页支付的产品价钱（单位为：分）：" + productPrice);
        String productName = PayProductConstans.MOBILE_OPENED_AND_POSTPONE_PRODUCT_NAME.replaceAll("@price", String.valueOf(productPrice/100));
        PayOrder payOrder = getOrder(productPrice, payMobileLoginVo.getPayType(), productName,
                productName, tradeType);
        Map<String, Object> formMap = new HashMap<String, Object>();
        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        try {
            // 组装字段
            sysSave(payOrder, loginUser);
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            payOrder.setProductType(PayProductConstans.MOBILE_OPENED_AND_POSTPONE_TYPE);
            payOrder.setProductId(sysDictionary.getId());
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);

            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                message.setMessage("保存支付单失败.");
                message.setSuccess(Boolean.FALSE);
                return message;
            }
            payOrder.setId(id);
            // 支付宝网页支付
            if (payMobileLoginVo.getPayType().equalsIgnoreCase(PayType.ALIPAY)) {
                // 设置异步通知地址
                String notifyUrl = ResourceConfig.ALIPAY_NOTIFY_URL_MOBILE_PAY;
                String returnUrl = ResourceConfig.ALIPAY_RETURN_URL + payOrder.getOrderNo();
                logger.info("开通移动端支付宝异步通知地址：notifyUrl：{}" + notifyUrl + ",同步地址：returnUrl：{}" + returnUrl);
                String form = ScanPayUtil.addScanpayOrder(payOrder, notifyUrl, returnUrl);
                payOrder.setNotifyUrl(notifyUrl);
                if (StringUtils.isEmpty(form)) {
                    message.setSuccess(Boolean.FALSE);
                    message.setMessage("下单异常.");
                    return message;
                } else {
                    formMap.put("form", form);
                    message.setSuccess(Boolean.TRUE);
                    message.setObj(formMap);
                    message.setOrderNo(String.valueOf(payOrder.getId()));
                    message.setMessage("success");
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(Boolean.FALSE);
            message.setMessage("下单异常.");
        }

        return message;

    }

    /**
     *
     * 移动端的开通移动端登录支付成功后更新用户信息
     *
     * @param orderNo 订单号
     */
    @Override
    public void updateUserMobileInfo(String orderNo) {
        PayOrder payOrder = payOrderMapper.getOrderByOrderNo(orderNo);
        if (null != payOrder &&  PayProductConstans.MOBILE_OPENED_AND_POSTPONE_TYPE.equals(payOrder.getProductType())) {
            userJurisdictionService.addMobileLogin(payOrder.getUserId(), payOrder.getPlatformId());
        }

    }

    /**
     * 5.6版本：判断渲染免费时间段和返回免费时间段
     * @author yanghz
     * @since 2017-05-26
     * @return
     */
    @Override
    public RenderCheckVo renderFreeTimeInfo(){
        RenderCheckVo rederCheck = new RenderCheckVo();
        SysDictionary renderFreeTime = sysDictionaryService.findOneByTypeAndValueKey("render", RenderTypeCode.RENDER_FREE_TIME_KEY);
        if (renderFreeTime == null){
            logger.error("渲染免费时间   数据字典valueKey="+ RenderTypeCode.RENDER_FREE_TIME_KEY+"找不到该记录");
        }else{
            if (renderFreeTime.getAtt1() == null || renderFreeTime.getAtt2() == null){
                logger.error("渲染免费时间   数据字典valueKey="+ RenderTypeCode.RENDER_FREE_TIME_KEY+"att1或att2字段值为空！");
            }else{
                String attr1=renderFreeTime.getAtt1();
                String attr2=renderFreeTime.getAtt2();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String timeStartStr ="";
                String timeEndStr ="";

                String dayStartStr = "";
                String dayEndStr = "";

                if(attr1 == null || attr1.length() < 1){

                }else{
                    String[] attr1Split = attr1.split(",");
                    timeStartStr = attr1Split[0];
                    dayEndStr = attr1Split[1];
                }
                if(attr2 == null || attr2.length() < 1){

                }else{
                    String[] attr2Split = attr2.split(",");
                    dayStartStr = attr2Split[0];
                    timeEndStr = attr2Split[1];
                }

                try {
                    Date dateNow = sdf.parse(sdf.format(new Date()));

                    Date dataStart = sdf.parse(timeStartStr);
                    Date dataEnd = sdf.parse(timeEndStr);

                    long startTimeLong = dataStart.getTime();
                    long endTimeLong = dataEnd.getTime();

                    long nowTimeLong = dateNow.getTime();

                    long dayStartLong = sdf.parse(dayStartStr).getTime();
                    long dayEndLong = sdf.parse(dayEndStr).getTime();
                    //判断当前时间是否在渲染免费时间段内
                    if ((startTimeLong < nowTimeLong && nowTimeLong < dayEndLong) || (dayStartLong < nowTimeLong && nowTimeLong < endTimeLong)){
                        rederCheck.setIsFree(RenderTypeCode.RENDER_FREE_TIME_TYPE_KEY);//免费
                        rederCheck.setFreeTimeStart(timeStartStr.substring(0,timeStartStr.lastIndexOf(":")));
                        rederCheck.setFreeTimeEnd(timeEndStr.substring(0,timeEndStr.lastIndexOf(":")));
                        rederCheck.setFreeTimeName("(免费时间段:晚"+rederCheck.getFreeTimeStart()+"-早"+rederCheck.getFreeTimeEnd()+")");
                        return rederCheck;
                    }else{
                        rederCheck.setIsFree(RenderTypeCode.RENDER_NOT_FREE_TIME_TYPE_KEY);//收费
                        rederCheck.setFreeTimeStart(timeStartStr.substring(0,timeStartStr.lastIndexOf(":")));
                        rederCheck.setFreeTimeEnd(timeEndStr.substring(0,timeEndStr.lastIndexOf(":")));
                        rederCheck.setFreeTimeName("(免费时间段:晚"+rederCheck.getFreeTimeStart()+"-早"+rederCheck.getFreeTimeEnd()+")");
                        return rederCheck;
                    }
                } catch (ParseException e) {
                    logger.error(e);
                }
            }
        }
        rederCheck.setIsFree(RenderTypeCode.RENDER_FREE_TIME_TYPE_KEY);//收费
        rederCheck.setFreeTimeStart("10:00");//默认值
        rederCheck.setFreeTimeEnd("08:00");//默认值
        rederCheck.setFreeTimeName("晚"+rederCheck.getFreeTimeStart()+"-早"+rederCheck.getFreeTimeEnd());
        return rederCheck;
    }
    /**
     * 更具渲染类型查询价格列表
     */
    @Override
    public List<RenderPriceInfoVo> findPriceInfo(Integer renderingType) {
        List<RenderPriceInfoVo> priceList = new ArrayList<>();
        String priceType = "";
        if(RenderTypeCode.COMMON_PICTURE_LEVEL == renderingType){
            priceType = "renderPrice_photo";
        }else if(RenderTypeCode.COMMON_720_LEVEL == renderingType) {
            priceType = "renderPrice_720";
        }else if( RenderTypeCode.COMMON_VIDEO == renderingType ){
            priceType = "renderPrice_video";
        }else if( RenderTypeCode.ROAM_720_LEVEL == renderingType ){
            priceType = "renderPrice_roam720";
        }else{
        }
        List<SysDictionary> renderPrice = sysDictionaryService.findAllByType(priceType);
        if(renderPrice == null){
            logger.error("查询渲染价格数据字典：type="+priceType+"未查询到信息！");
        }else{
            for(SysDictionary sysTemp : renderPrice){
                RenderPriceInfoVo priceInfoVo = new RenderPriceInfoVo();
                priceInfoVo.setType(priceType);
                if(sysTemp.getValue() == null){
                    logger.error("数据字典id="+sysTemp.getId()+"未设置value值");
                }else{
                    priceInfoVo.setMode(sysTemp.getValue()+"");
                }
                if(sysTemp.getAtt1() == null || "".equals(sysTemp.getAtt1()) ){
                    logger.error("数据字典id="+sysTemp.getId()+"未设置价格信息:att1字段");
                }else{
                    priceInfoVo.setPrice(sysTemp.getAtt1());
                }
                if(sysTemp.getName() == null || "".equals(sysTemp.getName())){
                    logger.error("数据字典id="+sysTemp.getId()+"未设置单位信息:name字段");
                }else{
                    priceInfoVo.setName(sysTemp.getName());
                }
                priceList.add(priceInfoVo);
            }
        }

        return priceList;
    }



    /**
     *
     * 移动端的开通支付回调(微信h5支付)
     *
     * @param sbResult
     * @param success
     * @return
     */
    @Override
    public boolean wxNotifyMobilePay(String sbResult, boolean success) {

        logger.info(CLASS_LOG_PREFIX + "微信回调结果:" + sbResult);
        if (sbResult.length() > 0) {
            // 测试回调接口是否会修改渲染任务状态和生成渲染配置文件所以注释,提交解开注释
            ResultNotify result = (ResultNotify) Util.getObjectFromXML(sbResult, ResultNotify.class);
            if (result != null) {
                PayOrder order = new PayOrder();
                String orderNo = result.getOut_trade_no();
                String openId = result.getOpenid();
                String refId = result.getTransaction_id();
                order.setOrderNo(orderNo);
                order.setOpenId(openId);
                order.setRefId(refId);
                order.setPayState(PayState.PAY_ERROR);
                if (result.getResult_code().equalsIgnoreCase(PayState.SUCCESS)
                        && result.getReturn_code().equalsIgnoreCase(PayState.SUCCESS)) {
                    success = true;
                    order.setPayState(PayState.SUCCESS);
                    this.updatePayState(order);
                    logger.info("更新订单状态结束");
                    // 更新用户的移动端开通信息
                    this.updateUserMobileInfo(orderNo);
                    logger.info(CLASS_LOG_PREFIX + " 微信回调发送消息成功");
                    updateUserFinance(orderNo);
                }
            }
        }
        return success;
    }

    /**
     *
     * 移动端登录支付成功的通知
     *
     * @param orderNo 订单号
     * @return
     */
    @Override
    public boolean wxNotifyMobilePaySend(String orderNo) {
        boolean flag = true;
        logger.info(CLASS_LOG_PREFIX + "付款完成，给客户端发送支付结果... 订单No：" + orderNo);
        if(StringUtils.isBlank(orderNo) ){
            return false;
        }
        try {
            // 获取订单
            PayOrder payOrder = new PayOrder();
            payOrder.setOrderNo(orderNo);
            List<PayOrder> orderList = this.getList(payOrder);
            if( orderList != null && orderList.size() == 1 ){
                payOrder = orderList.get(0);
            }else{
                logger.error(CLASS_LOG_PREFIX + "没有找到订单No为 " +orderNo+ " 的订单信息！");
                return false;
            }
            // 组装发给客户端的信息
            ResponseEnvelope result = new ResponseEnvelope();
            SendStateResult sendStateResult = new SendStateResult();
            sendStateResult.setOrderNo(payOrder.getOrderNo());
            sendStateResult.setPayState(payOrder.getPayState());
            if( payOrder.getPayState().equals(PayState.SUCCESS) ){
                result.setStatus(true);
            }else if(payOrder.getPayState().equals(PayState.PAY_ERROR)){
                result.setStatus(false);
            }
            result.setObj(sendStateResult);
            JSONObject jsonObject = JSONObject.fromObject(result);
            // 发送消息
            logger.info(CLASS_LOG_PREFIX + "发送消息：" + jsonObject.toString());
            try {
                logger.info("websocket地址：" + ResourceConfig.WEB_SOCKET_SERVER_URL);
                WebSocketUtils.sendMessage(ResourceConfig.WEB_SOCKET_SERVER_URL, String.valueOf(payOrder.getId()), jsonObject.toString());
            } catch (Exception e) {
                logger.error(CLASS_LOG_PREFIX + "payOrder websocket链接异常:"+e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return flag;
    }
    /**
     *
     * 插入订单
     *
     * */
    public int add(PayOrder payOrder) {
        int id = payOrderMapper.insertSelective(payOrder);
        logger.info("发送消息add--------------->：" + payOrder.getId());
        return payOrder.getId();
    }

    /**
     * 根据消费金额判断是否需要对用户进行自动升级
     * */
    @Override
    public void processAfterConsume(String orderNum) {
        //1、查询消费的总金额
        //2、把总金额和sys_user_level_price表的price表进行比较看是否需要升级
        //3、如果不需要升级则不做任何处理，如果需要升级：一个等级一个等级逐级往上比较
        if (StringUtils.isEmpty(orderNum)){
            return;
        }

        PayOrder payOrder = payOrderMapper.getConsumeSumByOrderNum(orderNum);
        if (payOrder == null){
            return;
        }else {
            Integer totalCocumeFee = payOrder.getTotalFee();//用户消费总金额
            Integer userId = payOrder.getUserId();
            //查询用户当前级别对应的金额
            SysUserLevelConfig levelConfig = new SysUserLevelConfig();
            levelConfig.setBusinessTypeId(Constants.USER_LEVEL_BUSINESS_TYPE_LEVEL_PRICE_ID);
            levelConfig.setUserId(userId);
            SysUserLevelBo userLevelBo = sysUserService.getLevelInfo(levelConfig);//查询用户当前对应的等级
            if (userLevelBo != null){
                int levelPrice = userLevelBo.getPrice();
                if (totalCocumeFee <= levelPrice){//消费总金额未达到等级上限
                    return;
                }else {
                    //根据用户对应的组群类型获取等级列表:根据user_pay_type倒序排序
                    List<SysUserLevelPrice> userLevelPrices = sysUserService.getListByUserId(userId);
                    if(userLevelPrices != null && userLevelPrices.size() > 0){
                        for (SysUserLevelPrice tmp : userLevelPrices){
                            if(totalCocumeFee >= tmp.getPrice()){
                                int id = userLevelBo.getId();
                                int levelId = tmp.getId();
                                sysUserService.updateById(id,levelId);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getCountByUserIdAndStatus(Integer userId, String status) {
        return payOrderMapper.getCountByUserIdAndStatus(userId, status);
    }

    @Override
    public ResultMessage addScanpayOrderPc(PayOrder payOrder, ScanPayReqData reqData) {
        // TODO Auto-generated method stub
        payOrder.setNotifyUrl(ResourceConfig.ALIPAY_NOTIFY_URL);
        ResultMessage resultMessage =  com.sandu.pay.alipay.model.ScanPayUtil.addScanpayOrder(payOrder, reqData);
        resultMessage.setObj(reqData);
        return resultMessage;
    }

    @Override
    public ResultMessage addScanpayOrderPc(PayOrder payOrder, String productName, double totalFee, LoginUser loginUser,
                                           Long userId, ScanPayReqData reqData) throws Exception {
        ResultMessage message = new ResultMessage();
        // TODO Auto-generated method stub
        String flag = "SUCCESS";
        UnifiedOrderService orderService = new UnifiedOrderService();
        // 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
        String timeExpire = Utils.getTimeExpire(30);
		 /*UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(PayProductConstans.RECHARGE_PRODUCT_NAME, payOrder.getOrderNo(),
                 discountPrice, WxTradeType.NATIVE, timeExpire,ResourceConfig.WX_PAY_NOTIFY_URL);*/
        UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(productName, payOrder.getOrderNo(),
                payOrder.getTotalFee(), WxTradeType.NATIVE, timeExpire, ResourceConfig.WX_PAY_NOTIFY_URL);
        UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
        if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
            String prepayId = unifiedOrderResData.getPrepay_id();
            String wx_codeUrl = unifiedOrderResData.getCode_url();
            String codeUrl = QrCodeUtil.generateQrCode(wx_codeUrl, payOrder.getOrderNo());
            /*reqData.setCode_url(FileUploadUtils.RESOURCES_URL + codeUrl);*/

            /*reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));*/
            reqData.setCode_url(wx_codeUrl);//直接返回访问路径，不在返回2维码
            payOrder.setPrepayId(prepayId);
            payOrder.setPayState(PayState.PAYING);
            payOrder.setCodeUrl(codeUrl);
            this.update(payOrder);
            message.setMessage("成功生成在线充值单");
            message.setObj(reqData);
            message.setSuccess(true);

            //FIXME:每次获取充值单就添加一条充值记录
            Date date = new Date();
            MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
            mgrRechargeLog.setUserId(userId.intValue());
            mgrRechargeLog.setRechargeType(2);
            mgrRechargeLog.setRechargeStatus(2);
            mgrRechargeLog.setRechargeAmount(totalFee/100);
            mgrRechargeLog.setAdministratorId(userId.intValue());
            mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
            mgrRechargeLog.setGmtCreate(date);
            mgrRechargeLog.setGmtModified(date);
            mgrRechargeLog.setCreator(loginUser.getLoginName());
            mgrRechargeLog.setModifier(loginUser.getLoginName());
            mgrRechargeLog.setIsDeleted(0);
            mgrRechargeLog.setSysCode(getSysCode());
            if (null != payOrder.getPlatformId()) {
                BasePlatform basePlatform = basePlatformService.get(payOrder.getPlatformId());
                if (null != basePlatform) {
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                }
            }
            //sysSave(mgrRechargeLog,request);
            this.sysSave(payOrder, loginUser);
            mgrRechargeLogService.add(mgrRechargeLog);
        }
        return message;
    }

    private String getSysCode(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return  dtFormat.format(date);
    }

    //5.6版本：创建任务和支付二维码
    @Override
    public ResultMessage sendMessageAndCreateOrderNewPc(Integer productId, String productType, String productName,
                                                        String productDesc, String payType, Long userId, String msgId,
                                                        ResultMessage message, String level, Integer isTurnOn, Integer planId, Integer priority, Integer viewPoint,

                                                        Integer scene, Integer renderingType, String temporaryPic, String type, Integer mode, LoginUser loginUser, String platformCode) {
        logger.info("5.6版本：创建任务和支付二维码:");
        String tradeType = TradeType.SCANCODE;
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
        try {
            int totalFee = 0;
            SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue(type, mode);
            if(sysDictionary == null){
                throw new RuntimeException("------支付类数据字典未找到:type=" + type + ";mode=" + mode);
            }
            String totalFeeStr = sysDictionary.getAtt1();
            String priceInfo = sysDictionary.getName();//金额及尺寸信息
            if(org.apache.commons.lang3.StringUtils.isNotBlank(totalFeeStr)){
                totalFee = Integer.parseInt(totalFeeStr);
            }
            // 金钱处理 ->end
            PayOrder payOrder = this.getOrder(new Double(totalFee).intValue(), payType, productId, productType, productName, productDesc, tradeType);
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            ScanPayReqData reqData = new ScanPayReqData();
            sysSave(payOrder, loginUser);
            if (userId > 0) {
                payOrder.setUserId(userId.intValue());
            }
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            /**
             * if(renderingType != null) {
             *		payOrder.setRenderType(renderingType);//任务类型
             *	}
             * */
            //------------ 创建未支付状态的渲染任务 ->start
            logger.info("创建未支付状态的渲染任务 ->start");
            SysTask sysTask = this.createNonPaymentTaskNew(isTurnOn, planId, priority, viewPoint,
                    scene, renderingType,priceInfo,loginUser);
            if(sysTask.getId() == null){
                message.setMessage(sysTask.getRemark());
                return message;
            }

            payOrder.setTaskId(sysTask.getId());
            logger.info("创建未支付状态的渲染任务 ->end(获取任务id)："+sysTask.getId());
            // 创建未支付状态的渲染任务 ->end

            int id = this.add(payOrder);
            if (id == 0) {
                message.setMessage("保存支付单失败.");
                return message;
            }
            payOrder.setId(id);
            reqData.setOrderId(id);
            reqData.setOrderNo(payOrder.getOrderNo());
            //任务Id
            reqData.setTaskId(sysTask.getId());

            List<Integer> orderIdList = new ArrayList<Integer>();
            List<String> npaidOrderList = new ArrayList<>();
            PayOrder payOrder1 = new PayOrder();
            payOrder1.setNuma1(1);//未发送
            payOrder1.setIsDeleted(1);
            //查询超时未支付订单
            List<PayOrder> list = this.getList(payOrder1 );
            if(list != null && list.size() > 0){
                for (PayOrder temp : list) {
                    if(StringUtils.isNotBlank(temp.getOrderNo())){
                        npaidOrderList.add(temp.getOrderNo());
                        orderIdList.add(temp.getId());
                    }else{
                        logger.error("订单id="+temp.getId()+"订单号为空");
                    }
                }
            }
            reqData.setNpaidOrderList(npaidOrderList);
            if(orderIdList != null && orderIdList.size() > 0){
                this.deleteByIdList(orderIdList, "半小时未支付订单清理通知App端成功", 2);//已发送
            }

            if (payType.equalsIgnoreCase(PayType.WXPAY)) {
                String flag = "SUCCESS";
                UnifiedOrderService orderService = new UnifiedOrderService();
                // 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
                String timeExpire = Utils.getTimeExpire(30);
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(productName, payOrder.getOrderNo(),
                        payOrder.getTotalFee(), WxTradeType.NATIVE, timeExpire, ResourceConfig.WX_PAY_NOTIFY_URL);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    String prepayId = unifiedOrderResData.getPrepay_id();
                    String wx_codeUrl = unifiedOrderResData.getCode_url();
                    String codeUrl = QrCodeUtil.generateQrCode(wx_codeUrl, payOrder.getOrderNo());
                    /*reqData.setCode_url(FileUploadUtils.RESOURCES_URL + codeUrl);*/

                    /*reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));*/
                    reqData.setCode_url(wx_codeUrl);//直接返回访问路径，不在返回2维码
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setCodeUrl(codeUrl);
                    this.update(payOrder);
                    message.setMessage("成功生成扫描预付订单");
                    message.setObj(reqData);
                    message.setSuccess(true);
                }
            }
            if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
                // 设置支付超时时间
                payOrder.setTimeoutExpress("30m");
                payOrder.setNotifyUrl(ResourceConfig.ALIPAY_NOTIFY_URL);
                message = com.sandu.pay.alipay.model.ScanPayUtil.addScanpayOrder(payOrder, reqData);
                message.setObj(reqData);
                if (message.isSuccess()) {
                    message.setMessage("成功生成扫描预付订单");
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setCodeUrl(reqData.getQrCodePath());
                    this.update(payOrder);
                }
                message.setMsgId(msgId);
            }
            //TODO  临时处理余额支付
            if(payType.equalsIgnoreCase(PayType.PREDEPOSIT)){//余额支付
                message.setSuccess(true);
                message.setMessage("余额下订单成功!");
                //message = ScanPayUtil.addScanpayOrder(payOrder, request, reqData);
                message.setObj(reqData);
                payOrder.setPayState(PayState.PAYING);
                payOrder.setCodeUrl("");
                this.update(payOrder);
                message.setMsgId(msgId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMessage("下单异常.");
        }
        return message;
    }
    private void deleteByIdList(List<Integer> orderIdList, String remark, Integer numa1) {
        payOrderMapper.deleteByIdList(orderIdList,remark,numa1);
    }

    /**
     * 	5.6版本：创建一个未付款状态的渲染任务
     * @author yanghz
     */
    public SysTask createNonPaymentTaskNew(Integer isTurnOn, Integer planId, Integer priority,
                                           Integer viewPoint, Integer scene, Integer renderingType, String priceInfo, LoginUser loginUser) {
        logger.info("创建未支付状态的渲染任务 ->start");
        String paramString = String.format(
                "保存渲染任务App端传来的参数：isTurnOn:%d, planId:%s, priority:%d, viewPoint:%d, scene:%d, renderingType:%d",
                isTurnOn, planId, priority, viewPoint, scene, renderingType);
        SysTask sysTask = new SysTask();
        DesignPlan designPlan = sysTaskService.getDesignPlan(planId);
        if(designPlan == null){
            sysTask.setRemark("设计方案没找到");
            return sysTask;
        }
        try {
            JSONObject attribute = new JSONObject();
            attribute.accumulate("viewPoint", viewPoint);
            attribute.accumulate("scene", scene);
            attribute.accumulate("renderingType", renderingType);
            sysTask.setAttribute(attribute.toString());
            sysTask.setBusinessId(planId);
            sysTask.setRenderType(renderingType);
            sysTask.setRenderWay("local");
            String rootDirName = designPlan.getPlanCode() + "_" + Utils.getCurrentDateTime(Utils.DATETIME)
                    + Utils.generateRandomDigitString(6);

            sysTask.setBusinessCode(rootDirName);
            if (priceInfo != null) {
                sysTask.setBusinessName(priceInfo);/* 业务名称 */
            }
            priority = 1000 - renderingType;// 优先级
            this.sysSave(sysTask, loginUser);

            sysTask.setPriority(priority);// 任务优先级
            sysTask.setState(SysTaskStatus.WAITING_RENDER);
            sysTask.setRemark("未支付");
            sysTaskService.add(sysTask);
        } catch (Exception e) {
            logger.error("------新建未支付渲染任务异常"+e);
        }
        logger.info("创建未支付状态的渲染任务 ->end");
        return sysTask;
    }


    /**
     *  购买渲染包年包月订单
     *
     * @param payModelConfigVo
     * @param userId 用户id
     * @param platformId 平台id
     * @return
     */
    @Override
    public ResultMessage addRenderPayModelConfig(PayModelConfigVo payModelConfigVo, Integer userId, Integer platformId) {
        logger.info("购买渲染包年包月订单,用户id：userId:{}" + userId);
        ResultMessage resultMessage = new ResultMessage();
        if (!payModelConfigVo.getPayType().equals(PayType.WXPAY)
                && !payModelConfigVo.getPayType().equals(PayType.ALIPAY)
                && !payModelConfigVo.getPayType().equals(PayType.PREDEPOSIT)) {
            logger.info("支付方式错误");
            resultMessage.setMessage("支付方式错误");
            return resultMessage;
        }
        if (!payModelConfigVo.getTradeType().equals(TradeType.APP)
                && !payModelConfigVo.getTradeType().equals(TradeType.SCANCODE)
                && !payModelConfigVo.getTradeType().equals(TradeType.PREDEPOSIT)) {
            logger.info("交易方式错误");
            resultMessage.setMessage("交易方式错误");
            return resultMessage;
        }
        if (null == payModelConfigVo.getPayModelConfigId()) {
            resultMessage.setMessage("参数payModelConfigId不能为空");
            return resultMessage;
        }
        PayModelConfig payModelConfig = payModelConfigService.get(payModelConfigVo.getPayModelConfigId());
        if (null == payModelConfig || payModelConfig.getIsDeleted() != 0
                || !payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE)) {
            resultMessage.setMessage("渲染包年包月套餐数据为空");
            return resultMessage;
        }
        // 当前如果是包月，那么判断是否有未过期的包年，如果有，那么就不给购买
        List<Map<String, Object>> list = payModelGroupRefService.getUserRefInfoList(userId,PayModelConstantType.RENDER_TYPE,null);
        if (payModelConfig.getTimeType() == 0) {
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).get("timeType").toString().equals("1")) {
                        logger.info("当前用户存在未过期的包年，请在包年过期后购买，用户id：userId:{}" + userId);
                        resultMessage.setMessage("当前用户存在未过期的包年，请在包年过期后购买");
                        return resultMessage;
                    }
                }

            }
        }
        Date endDate = new Date();
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (null != list.get(i).get("expiryTime")) {
                    endDate = (Date)list.get(i).get("expiryTime");
                    break;
                }
            }
        }
        SysUser sysUser = sysUserService.get(userId);
        String tradeType = TradeType.SCANCODE;
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        logger.info("购买渲染包年包月订单的金额是：" + payModelConfig.getPackagePrice());
        String productName =  PayProductConstans.RENDER_PAY_PRODUCT_NAME;
        if (payModelConfig.getTimeType() == 0) {
            productName = productName.replace("@replace_str","包月");
        } else if (payModelConfig.getTimeType() == 1) {
            productName = productName.replace("@replace_str","包年");
        }
        PayOrder payOrder = getOrder(Double.valueOf(payModelConfig.getPackagePrice()).intValue(),payModelConfigVo.getPayType(),
                productName, productName,tradeType);
        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, platformId);
            if (null == payAccount) {
                resultMessage.setMessage("用户度币信息为空，生成订单失败");
                return resultMessage;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        try {
            payOrder.setPlatformId(platformId);
            sysSave(payOrder, loginUser);
            payOrder.setProductType(PayProductConstans.RENDER_PRODUCT_TYPE);
            payOrder.setProductId(payModelConfig.getId());
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            payOrder.setAtt1(String.valueOf(userId)); // 业务id（备用字段1）
            payOrder.setAtt2(String.valueOf(payModelConfig.getId())); // 付款方式表id（备用字段2）
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                logger.info("保存订单失败");
                resultMessage.setMessage("保存订单失败");
                return resultMessage;
            }
            payOrder.setId(id);
            String timeExpire = Utils.getTimeExpire(30);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", payOrder.getId());
            // 支付内容拼接start
            StringBuffer sb = new StringBuffer();
            if (payModelConfig.getTimeType() == 0) {
                // 表示月 remainDay
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(endDate);
                calendar.add(calendar.MONTH, payModelConfig.getTimeUnit());// 把日期往后增加月份.整数往后推,负数往前移动
                endDate = calendar.getTime();
                sb.append("渲染包月服务费，");
                sb.append((payModelConfig.getPackagePrice()/100)/payModelConfig.getTimeUnit() + "元/月，共计" + payModelConfig.getPackagePrice()/100 + "元，");
                sb.append("服务的有效期为" + UtilDate.getStringDate(new Date()));
                sb.append("至" + UtilDate.getStringDate(endDate));
            }  else if (payModelConfig.getTimeType() == 1) {
                // 表示年
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(endDate);
                calendar.add(calendar.YEAR, payModelConfig.getTimeUnit());// 把日期往后增加月份.整数往后推,负数往前移动
                endDate = calendar.getTime();
                sb.append("渲染包年服务费，");
                sb.append((payModelConfig.getPackagePrice()/100)/payModelConfig.getTimeUnit() + "元/年，共计" + payModelConfig.getPackagePrice()/100 + "元，");
                sb.append("服务的有效期为" + UtilDate.getStringDate(new Date()));
                sb.append("至" + UtilDate.getStringDate(endDate));
            }
            logger.info("渲染包年包月用户id：userId:{}" + userId + ",支付内容为：" + sb.toString());
            map.put("payContent", sb.toString());
            // 支付内容拼接end
            if (payModelConfigVo.getPayType().equals(PayType.WXPAY)
                    && payModelConfigVo.getTradeType().equals(TradeType.SCANCODE)) {
                logger.info("微信扫码支付start");
                String flag = "SUCCESS";
                UnifiedOrderService orderService = new UnifiedOrderService();
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(productName, payOrder.getOrderNo(),
                        Double.valueOf(payModelConfig.getPackagePrice()).intValue(), WxTradeType.NATIVE, timeExpire, ResourceConfig.WXPAY_PAY_MODEL_NOTIFY_URL);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    String prepayId = unifiedOrderResData.getPrepay_id();
                    String wx_codeUrl = unifiedOrderResData.getCode_url();
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setCodeUrl(wx_codeUrl);
                    payOrder.setNotifyUrl(ResourceConfig.WXPAY_PAY_MODEL_NOTIFY_URL);
                    this.update(payOrder);
                    map.put("payCodeUrl", wx_codeUrl);
                    logger.info("获取微信二维码成功，payCodeUrl：{}" + wx_codeUrl + ",包年包月渲染的微信回调地址是：" + ResourceConfig.WXPAY_PAY_MODEL_NOTIFY_URL);
                    resultMessage.setObj(map);
                } else {
                    logger.info("获取微信二维码失败，payCodeUrl：{}为空");
                    resultMessage.setMessage("获取微信二维码失败，payCodeUrl：{}为空");
                    return resultMessage;
                }
                logger.info("微信扫码支付end");
            } else if (payModelConfigVo.getPayType().equals(PayType.ALIPAY)
                    && payModelConfigVo.getTradeType().equals(TradeType.SCANCODE)) {
                logger.info("支付宝扫码支付start");
                payOrder.setNotifyUrl(ResourceConfig.ALIPAY_PAY_MODEL_NOTIFY_URL);
                ScanPayReqData reqData = new ScanPayReqData();
                ResultMessage message = ScanPayUtil.addScanpayOrderGetQrCode(payOrder,reqData,Double.valueOf(payModelConfig.getPackagePrice()).intValue());
                if (message.isSuccess()) {
                    map.put("payCodeUrl", reqData.getQrCodePath());
                    logger.info("支付宝扫码接口返回" + gson.toJson(reqData));
                    logger.info("获取支付宝二维码成功，payCodeUrl：{}" + reqData.getQrCodePath() + ",包年包月渲染的支付宝回调地址是：" + ResourceConfig.ALIPAY_PAY_MODEL_NOTIFY_URL );
                    resultMessage.setObj(map);
                } else {
                    logger.info("获取支付宝二维码失败，payCodeUrl：{}为空");
                    resultMessage.setMessage("获取支付宝二维码失败，payCodeUrl：{}为空");
                    return resultMessage;
                }
                payOrder.setPayState(PayState.PAYING);
                payOrder.setCodeUrl(reqData.getQrCodePath());
                this.update(payOrder);
                logger.info("支付宝扫码支付end");
            } else {
                logger.info("支付方式错误");
                resultMessage.setMessage("支付方式错误");
                return resultMessage;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("下单异常");
            resultMessage.setMessage("下单异常");
            return resultMessage;
        }

        resultMessage.setMessage("success");
        resultMessage.setSuccess(true);
        return resultMessage;
    }

    /**
     * 微信扫码支付成功插入数据到付款方式业务关联表
     *
     * @param sbResult
     * @param success
     * @return
     */
    @Override
    public boolean wxNotifyConfigPay(String sbResult, boolean success) {
        logger.info(CLASS_LOG_PREFIX + "微信回调结果:" + sbResult);
        if (sbResult.length() > 0) {
            // 测试回调接口是否会修改渲染任务状态和生成渲染配置文件所以注释,提交解开注释
            ResultNotify result = (ResultNotify) Util.getObjectFromXML(sbResult, ResultNotify.class);
            if (result != null) {
                PayOrder order = new PayOrder();
                String orderNo = result.getOut_trade_no();
                String openId = result.getOpenid();
                String refId = result.getTransaction_id();
                order.setOrderNo(orderNo);
                order.setOpenId(openId);
                order.setRefId(refId);
                order.setPayState(PayState.PAY_ERROR);
                if (result.getResult_code().equalsIgnoreCase(PayState.SUCCESS)
                        && result.getReturn_code().equalsIgnoreCase(PayState.SUCCESS)) {
                    success = true;
                    order.setPayState(PayState.SUCCESS);
                    logger.info("包年包月微信扫码回调更新订单表结束");
                    this.updatePayState(order);
                    logger.info(CLASS_LOG_PREFIX + " 包年包月微信扫码回调发送消息成功");
                    boolean flag =  updateUserFinance(orderNo);
                    logger.info("包年包月微信扫码回调更新用户消费结果为：" + flag);
                    //插入数据到付款方式业务关联表
                    payModelGroupRefService.addPayModelGroupRef(orderNo);
                    //websocket通知
                    logger.info("websocket通知====start");
                    sendRenderPayStateNew(orderNo);
                    logger.info("websocket通知====end");
                }
            }
        }
        return success;
    }

    /**
     * 包年包月生成渲染订单
     *
     * @param platformId  平台id
     * @param userId 用户id
     * @param payModelConfigVo
     * @return
     */
    @Override
    public ResultMessage addRenderPayOrder(Integer platformId, Integer userId, PayModelConfigVo payModelConfigVo) {
        ResultMessage resultMessage = new ResultMessage();
        if (null == platformId || null == userId) {
            resultMessage.setMessage("参数为空：platformId：{}" + platformId);
            logger.info("参数为空：platformId：{}" + platformId);
            return resultMessage;
        }
        if (null == payModelConfigVo.getProductId()) {
            resultMessage.setMessage("产品id为空：productId：{}" + payModelConfigVo.getProductId());
            logger.info("产品id为空：productId：{}" + payModelConfigVo.getProductId());
            return resultMessage;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getProductType())) {
            resultMessage.setMessage("产品类型为空：productType：{}");
            logger.info("产品类型为空：productType：{}");
            return resultMessage;
        }
        if(!StringUtils.equals("common_render", payModelConfigVo.getProductType())&&!StringUtils.equals("panorama_render", payModelConfigVo.getProductType())
                &&!StringUtils.equals("HD_render", payModelConfigVo.getProductType())&&!StringUtils.equals("UHD_render", payModelConfigVo.getProductType())
                &&!StringUtils.equals("video", payModelConfigVo.getProductType())&&!StringUtils.equals("roam720", payModelConfigVo.getProductType())
                ){
            resultMessage.setMessage("产品类型为：productType：{}" + payModelConfigVo.getProductType() + "的产品不能享受包年包月服务");
            logger.info("产品类型为：productType：{}" + payModelConfigVo.getProductType() + "的产品不能享受包年包月服务");
            return resultMessage;
        }
        if (payModelConfigVo.getProductType().contains("panorama_render")
                || payModelConfigVo.getProductType().contains("renderPrice_video")
                || payModelConfigVo.getProductType().contains(" renderPrice_roam720")
                || payModelConfigVo.getProductType().contains("roam720")
                || payModelConfigVo.getProductType().contains("video")) {
            payModelConfigVo.setIsShow("true");
        }
        if (StringUtils.isEmpty(payModelConfigVo.getProductName())) {
            resultMessage.setMessage("产品名称为空：productName：{}");
            logger.info("产品名称为空：productName：{}");
            return resultMessage;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getRenderingType())) {
            resultMessage.setMessage("参数renderingType不能为空");
            logger.info("参数renderingType不能为空");
            return resultMessage;
        }
        if(null == payModelConfigVo.getPlanId()){
            resultMessage.setMessage("planId不能为空!");
            logger.info("planId不能为空!");
            return resultMessage;
        }
        /*优先级，如果等null  那么优先级月底*/
        if( payModelConfigVo.getPriority() == null ){
            payModelConfigVo.setPriority(1);
        }
        /*视角*/
        if(payModelConfigVo.getViewPoint() == null){
            resultMessage.setMessage("参数viewPoint不能为空!");
            logger.info("参数viewPoint不能为空!");
            return resultMessage;
        }
        /*场景 白天黑夜 黄昏？*/
        if(payModelConfigVo.getScene()==null){
            resultMessage.setMessage("参数scene不能为空!");
            logger.info("参数scene不能为空!");
            return resultMessage;
        }
        Map<String, Object> map = new HashMap<>();
        String packageName = "免费渲染";
        String priceInfo = PayModelConstantType.RENDER_BIZ_NAME;
        SysUser sysUser = sysUserService.get(userId);
        String tradeType = TradeType.PAY_MODEL_CONFIG;
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        PayOrder payOrder = getOrder(0, PayType.PAY_MODEL_CONFIG_PAY,
                payModelConfigVo.getProductName(), StringUtils.isEmpty(payModelConfigVo.getProductDesc())
                        == true ? payModelConfigVo.getProductName() : payModelConfigVo.getProductDesc(),tradeType);
        // 处理产品名称start
        payOrder.setProductName(ProductUtil.getProductNameByProductType(payModelConfigVo.getProductType()) + packageName);
        // 处理产品名称start
        SysTask sysTask =null;
        try {
            logger.info("---------- 创建未支付状态的渲染任务 ->start");
            sysTask = this.createNonPaymentTaskNew(payModelConfigVo.getIsTurnOn(), payModelConfigVo.getPlanId(),payModelConfigVo.getPriority(),
                    payModelConfigVo.getViewPoint(),payModelConfigVo.getScene(), Integer.parseInt(payModelConfigVo.getRenderingType()),priceInfo,loginUser);
            if(sysTask == null || sysTask.getId() == null){
                logger.info("创建渲染任务信息：{}" + sysTask.getRemark());
                resultMessage.setMessage(sysTask.getRemark());
                return resultMessage;
            } else {
                if(sysTask.getRenderType() != null){
                    resultMessage.setRenderingType(sysTask.getRenderType()+"");
                }else{
                    logger.error("task id="+sysTask.getId() +" param 'renderType' is null!");
                }
            }
            logger.info("创建未支付状态的渲染任务 ->end");
            int balanceAmount = 0;
            payOrder.setTaskId(sysTask.getId());
            payOrder.setPlatformId(platformId);
            sysSave(payOrder, loginUser);
            payOrder.setProductType(payModelConfigVo.getProductType());
            payOrder.setProductId(payModelConfigVo.getProductId()); // 产品id
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);// 这里是支付成功

            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                logger.info("保存包年包月的渲染订单失败");
                resultMessage.setMessage("保存包年包月的渲染订单失败");
                return resultMessage;
            }
            //------------ 渲染任务状态变更:未支付->待执行 ->start
            this.updateNonPaymentTaskNew(sysTask,loginUser, PayType.PAY_MODEL_CONFIG_PAY,payOrder.getPayState(),null);
            //------------ 渲染任务状态变更:未支付->待执行 ->end
            resultMessage.setOrderNo(payOrder.getOrderNo());
            resultMessage.setTaskId(null != sysTask ? sysTask.getId() : null);
            resultMessage.setRenderingType(payModelConfigVo.getRenderingType());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("保存包年包月的渲染订单异常");
            resultMessage.setMessage("保存包年包月的渲染订单异常");
            return resultMessage;
        }
        map.put("orderNo", payOrder.getOrderNo());
        map.put("taskId", null != sysTask ? sysTask.getId() : null);
        /**
         * 这里不需要验证(超过10个未支付的订单,则无法再次发起订单)和（判断用户是否需要自动升级）
         */
        resultMessage.setObj(map);
        resultMessage.setSuccess(Boolean.TRUE);
        resultMessage.setMessage("生成包年包月渲染订单成功");
        return resultMessage;
    }

    /**
     * 付款成功之后的websocket通知
     *
     * @param orderNo 订单号
     * @return
     */
    @Override
    public boolean sendRenderPayStateNew(String orderNo) {
        boolean flag = true;
        logger.info(CLASS_LOG_PREFIX + "付款完成，给客户端发送支付结果... 订单No：" + orderNo);
        if(StringUtils.isBlank(orderNo) ){
            return false;
        }
        try {
            // 获取订单
            PayOrder payOrder = new PayOrder();
            payOrder.setOrderNo(orderNo);
            List<PayOrder> orderList = this.getList(payOrder);
            if( orderList != null && orderList.size() == 1 ){
                payOrder = orderList.get(0);
            }else{
                logger.error(CLASS_LOG_PREFIX + "没有找到订单No为 " +orderNo+ " 的订单信息！");
                return false;
            }
            // 获取产生订单的客户端
            logger.info(CLASS_LOG_PREFIX + "payOrder.getPayState:"+ payOrder.getPayState());
            if( payOrder.getPayState().equals(PayState.PAY_ERROR) || payOrder.getPayState().equals(PayState.SUCCESS)){
                // 组装发给客户端的信息
                ResponseEnvelope result = new ResponseEnvelope();
                SendStateResult sendStateResult = new SendStateResult();
                sendStateResult.setOrderNo(payOrder.getOrderNo());
                sendStateResult.setPayState(payOrder.getPayState());
                if( payOrder.getPayState().equals(PayState.SUCCESS) ){
                    result.setStatus(true);
                }else if(payOrder.getPayState().equals(PayState.PAY_ERROR)){
                    result.setStatus(false);
                }
                result.setObj(sendStateResult);
                JSONObject jsonObject = JSONObject.fromObject(result);
                // 发送消息
                logger.info(CLASS_LOG_PREFIX + "发送消息：" + jsonObject.toString());
                try {
                    logger.info("websocket地址是：" + ResourceConfig.WEB_SOCKET_SERVER_URL);
                    WebSocketUtils.sendMessage(ResourceConfig.WEB_SOCKET_SERVER_URL, String.valueOf(payOrder.getId()), jsonObject.toString());
                }catch(Exception e){
                    logger.error(CLASS_LOG_PREFIX + "payOrder websocket链接异常:"+e);
                    e.printStackTrace();
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return flag;
    }

    /**
     * 2c度币充值（微信h5支付，支付宝手机网页支付）
     *
     * @param userId
     * @param payOrderModel
     * @return
     */
    @Override
    public ResultMessage rechargeIntegralByH5Pay(Integer userId, PayOrderModel payOrderModel, String platformCode) {
        ResultMessage message = new ResultMessage();
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
        userId = userId == null ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空");
            message.setMessage("用户信息为空");
            return message;
        }
        if (null == payOrderModel) {
            logger.info("支付参数错误");
            message.setMessage("支付参数错误");
            return message;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType()) ||
                (!payOrderModel.getPayType().equals(PayType.WXPAY) && !payOrderModel.getPayType().equals(PayType.ALIPAY))) {
            message.setMessage("支付方式有误，payType:{}");
            return message;
        }
        if (StringUtils.isEmpty(payOrderModel.getRedirectUrl())) {
            message.setMessage("支付跳转的url为空");
            return message;
        }
        String tradeType = "";
        if (payOrderModel.getPayType().equals(PayType.WXPAY)) {
            tradeType = TradeType.H5;
        } else  if (payOrderModel.getPayType().equals(PayType.ALIPAY)) {
            tradeType = TradeType.WAP;
        }
        if (null == payOrderModel.getProductId()) {
            message.setMessage("产品id为空：productId:{}");
            return message;
        }
        SysDictionary sysDictionary = sysDictionaryService.get(payOrderModel.getProductId());
        if (null == sysDictionary) {
            message.setMessage("数据字典中找不到度币信息");
            return message;
        }
        int originalPrice= Utils.getIntValue(sysDictionary.getAtt1());
        int discountPrice = sysDictionary.getValue();
        //这里还是使用原价
        logger.info("原价是：originalPrice：{}" + originalPrice + ",打折后的价钱是：discountPrice：{}" + discountPrice);
        PayOrder payOrder = getOrder(originalPrice, payOrderModel.getPayType(), PayProductConstans.RECHARGE_PRODUCT_NAME, PayProductConstans.RECHARGE_PRODUCT_DESC, tradeType);
        payOrder.setPlatformId(basePlatform.getId());
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        ScanPayReqData reqData = new ScanPayReqData();
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        int balanceAmount = 0;
        if (null == companyFranchiserGroup) {
            // 不具备经销商权限
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        } else {
            // 具备经销商权限
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        }
        try {
            payOrder.setProductType(PayProductConstans.PAY_PRODUCT_TYPE);
            sysSave(payOrder, loginUser);
            payOrder.setUserId(userId);
            payOrder.setProductId(payOrderModel.getProductId());
            payOrder.setBizType(BizType.RECHARGE);
            payOrder.setFinanceType(FinanceType.IN);
            payOrder.setCurrentIntegral((int)(balanceAmount+Double.parseDouble(sysDictionary.getAtt1())));
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                logger.info("保存在线充值单失败");
                message.setMessage("保存在线充值单失败");
                return message;
            }
            payOrder.setId(id);
            reqData.setOrderId(payOrder.getId());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", payOrder.getId());
            logger.info("生成订单成功，订单号是：orderNo:{}" + payOrder.getOrderNo());
            if (payOrderModel.getPayType().equals(PayType.WXPAY)) {
                logger.info("购买度币，微信h5支付start");
                String flag = "SUCCESS";
                UnifiedOrderService orderService = new UnifiedOrderService();
                String timeExpire = Utils.getTimeExpire(30);
                String wxNotifyUrl = ResourceConfig.WX_PAY_NOTIFY_URL;
                payOrder.setNotifyUrl(wxNotifyUrl);
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(PayProductConstans.RECHARGE_PRODUCT_NAME, payOrder.getOrderNo(),
                        discountPrice, WxTradeType.MWEB, timeExpire,wxNotifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    String redirect_url = payOrderModel.getRedirectUrl() + payOrder.getOrderNo();
                    logger.info("购买度币微信h5支付预下单成功，微信回调地址是：wxNotifyUrl：{}" + wxNotifyUrl
                            + ",支付跳转链接:mwebUrl:{}" + unifiedOrderResData.getMweb_url() + ",微信同步的url是：" + redirect_url);
                    map.put("mwebUrl", unifiedOrderResData.getMweb_url()+"&redirect_url=" + java.net.URLEncoder.encode(redirect_url,   "utf-8"));
                    message.setObj(map);
                    message.setMessage("success");
                    message.setSuccess(Boolean.TRUE);
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(2);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount((double) Utils.getIntValue(sysDictionary.getAtt1()));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                    logger.info("购买度币，微信h5支付end");
                    return message;
                }  else {
                    logger.info("微信h5下单失败，订单号为：orderNo:{}" + payOrder.getOrderNo());
                    message.setMessage("微信h5下单失败，订单号为：orderNo:{}" + payOrder.getOrderNo());
                    return message;
                }
            }
            if (payOrderModel.getPayType().equals(PayType.ALIPAY)) {
                String alNotifyUrl = ResourceConfig.ALIPAY_NOTIFY_URL;
                payOrder.setNotifyUrl(alNotifyUrl);
                String returnUrl = payOrderModel.getRedirectUrl() + payOrder.getOrderNo();
                logger.info("购买度币，支付宝手机网页支付start，支付宝同步的url：returnUrl：{}" + returnUrl);
                payOrder.setTotalFee(discountPrice);
                String form = ScanPayUtil.addScanpayOrder(payOrder, alNotifyUrl, returnUrl);
                payOrder.setTotalFee(originalPrice);
                if (StringUtils.isEmpty(form)) {
                    logger.info("支付宝手机网页下单失败，订单号为：orderNo:{}" + payOrder.getOrderNo());
                    message.setMessage("支付宝手机网页下单失败，订单号为：orderNo:{}" + payOrder.getOrderNo());
                    return message;
                } else {
                    logger.info("购买度币支付宝网页支付预下单成功，支付宝回调地址是：alNotifyUrl：{}" + alNotifyUrl);
                    map.put("form", form);
                    message.setObj(map);
                    message.setMessage("success");
                    message.setSuccess(Boolean.TRUE);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(3);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount((double) Utils.getIntValue(sysDictionary.getAtt1()));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                    logger.info("购买度币，支付宝手机网页支付end");
                    return message;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("下单异常");
            message.setMessage("下单异常");
            return message;
        }
        return message;
    }

    /**
     * 包年包月生成渲染订单(移动端专用)
     *
     * @param platformId  平台id
     * @param userId 用户id
     * @param payModelConfigVo
     * @return
     */
    @Override
    public ResultMessage addRenderOrder(Integer platformId, Integer userId, PayModelConfigVo payModelConfigVo) {
        ResultMessage message = new ResultMessage();
        if (null == platformId) {
            logger.info("平台id为空，platformId:{}" + platformId);
            message.setMessage("平台id为空，platformId:{}" + platformId);
            return message;
        }
        userId = null == userId ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            message.setMessage("用户信息为空");
            return message;
        }
        if (null == payModelConfigVo) {
            message.setMessage("生成渲染订单参数为空");
            return message;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getProductType())) {
            message.setMessage("产品类型为空");
            return message;
        }
        String packageName = "免费渲染";
        Double totalFee = 0d; // 订单总金额，单位为分
        String payType = PayType.PAY_MODEL_CONFIG_PAY; // 支付类型
        String productType = payModelConfigVo.getProductType(); // 产品类型
        int balanceAmount = 0;
        try {
            Integer productId = 0;
            String productDesc = "移动端替换产品";
            String tradeType = "";
            // 生成一条订单记录到数据库
            PayOrder payOrder = new PayOrder();
            payOrder.setPlatformId(platformId);
            payOrder.setOrderDate(new Date());
            payOrder.setOrderNo(IdGenerator.generateNo());
            payOrder.setTotalFee(totalFee.intValue());
            payOrder.setPayType(payType);
            payOrder.setProductId(productId);
            payOrder.setProductType(productType);
            payOrder.setProductName(ProductUtil.getProductNameByProductType(productType) + packageName);
            payOrder.setProductDesc(productDesc);
            payOrder.setTradeType(tradeType);
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);
            payOrder.setUserId(userId);
            payOrder.setUserId(sysUser.getId());
            payOrder.setGmtCreate(new Date());
            payOrder.setCreator(sysUser.getModifier());
            payOrder.setModifier(sysUser.getModifier());
            payOrder.setGmtCreate(new Date());
            payOrder.setIsDeleted(0);
            if (payOrder.getSysCode() == null || "".equals(payOrder.getSysCode())) {
                payOrder.setSysCode(
                        Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            }
            payOrder.setGmtModified(new Date());
            payOrder.setModifier(sysUser.getUserName());
            payOrder.setCurrentIntegral(balanceAmount - totalFee.intValue());
            // FIXME: 插入一条渲染失败款的记录到订单表pay_order，稍后修改到支付退款表pay_refund
            if ("refunds".equals(productType)) {
                payOrder.setProductType("recharge");
                payOrder.setProductName("渲染失败退款");
                payOrder.setProductDesc("渲染失败退款");
                payOrder.setBizType(BizType.REFUND);
                payOrder.setFinanceType(FinanceType.IN);
                payOrder.setPayState(PayState.SUCCESS);
                payOrder.setCurrentIntegral(balanceAmount + totalFee.intValue());
            }
            payOrder.setTaskId(-1);
            int id = payOrderMapper.insertSelective(payOrder);
            if (id == 0) {
                logger.info("生成订单失败");
                message.setMessage("生成订单失败");
                return message;
            }
            message.setObj(payOrder.getOrderNo());
            // 因为金额为0，所以不用修改用户余额
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("生成订单异常");
            message.setMessage("生成订单异常");
            return message;
        }
        message.setSuccess(true);
        message.setMessage("生成订单成功");
        return message;
    }
    /**
     * 使用度币共享生成渲染订单接口（移动2b端使用）
     *
     * @param platformId 平台id
     * @param userId 用户id
     * @param mobileProductReplace
     * @return
     */
    @Override
    public ResultMessage addIntegralShareRenderOrder(Integer platformId, Integer userId, MobileProductReplace mobileProductReplace) {
        ResultMessage message = new ResultMessage();
        if (null == platformId) {
            logger.info("平台id为空，platformId:{}" + platformId);
            message.setMessage("平台id为空，platformId:{}" + platformId);
            return message;
        }
        userId = null == userId ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空，sysUser:{}" + sysUser);
            message.setMessage("用户信息为空");
            return message;
        }
        if (null == mobileProductReplace) {
            logger.info("生成渲染订单参数为空，mobileProductReplace:{}" + mobileProductReplace);
            message.setMessage("生成渲染订单参数为空，mobileProductReplace:{}" + mobileProductReplace);
            return message;
        }
        mobileProductReplace.setPayType(PayType.INTEGRAL_SHARE_PAY);
        Double totalFee = mobileProductReplace.getTotalFee(); // 订单总金额，单位为分
        String payType = mobileProductReplace.getPayType(); // 支付类型
        String productType = mobileProductReplace.getProductType(); // 产品类型
        if (StringUtils.isEmpty(productType)) {
            logger.info("渲染订单产品类型为空，productType:{}" + mobileProductReplace.getProductType());
            message.setMessage("渲染订单产品类型为空，productType:{}" + mobileProductReplace.getProductType());
            return message;
        }
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null == companyFranchiserGroup) {
            logger.info("用户不具备度币共享的权限，用户id：userId:{}" + userId);
            message.setMessage("用户不具备度币共享的权限");
            return message;
        }
        Double commonalityIntegral = companyFranchiserGroup.getCommonalityIntegral().doubleValue(); // 公共度币
        if (commonalityIntegral < totalFee) {
            logger.info("公共度币不足，生成订单失败。公共度币：commonalityIntegral：{}" + commonalityIntegral);
            message.setMessage("公共度币不足，生成订单失败");
            return message;
        }
        Integer productId = 0; // 产品id
        String productDesc = "移动端替换产品"; // 产品明细
        String tradeType = ""; // 交易类型
        Integer taskId = -1; // 任务id
        try {
            PayOrder payOrder = new PayOrder();
            payOrder.setPlatformId(platformId); // 平台id
            payOrder.setOrderDate(new Date());
            payOrder.setOrderNo(IdGenerator.generateNo());
            payOrder.setTotalFee(totalFee.intValue());
            payOrder.setPayType(payType);
            payOrder.setProductId(productId);
            payOrder.setProductType(productType);
            payOrder.setProductName(ProductUtil.getProductNameByProductType(productType));
            payOrder.setProductName(payOrder.getProductName() + PayModelConstantType.INTEGRAL_RENDER_PRODUCT_NAME); // 处理产品
            payOrder.setProductDesc(productDesc);
            payOrder.setTradeType(tradeType);
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);
            payOrder.setUserId(userId);
            payOrder.setUserId(sysUser.getId());
            payOrder.setGmtCreate(new Date());
            payOrder.setCreator(sysUser.getUserName());
            payOrder.setIsDeleted(0);
            payOrder.setSysCode(
                    Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            payOrder.setGmtModified(new Date());
            payOrder.setModifier(sysUser.getUserName());
            payOrder.setCurrentIntegral(Double.valueOf((commonalityIntegral - totalFee)).intValue());
            // FIXME: 插入一条渲染失败款的记录到订单表pay_order，稍后修改到支付退款表pay_refund
            if ("refunds".equals(productType)) {
                payOrder.setProductType("recharge");
                payOrder.setProductName("渲染失败退款");
                payOrder.setProductDesc("渲染失败退款");
                payOrder.setBizType(BizType.REFUND);
                payOrder.setFinanceType(FinanceType.IN);
                payOrder.setPayState(PayState.SUCCESS);
                payOrder.setCurrentIntegral(Double.valueOf((commonalityIntegral + totalFee)).intValue());
            }
            payOrder.setTaskId(taskId);
            int id = payOrderMapper.insertSelective(payOrder);
            if (id == 0) {
                logger.info("使用度币共享生成渲染订单失败");
                message.setMessage("使用度币共享生成渲染订单失败");
                return message;
            }
            payOrder.setId(id);
            message.setObj(payOrder.getOrderNo());
            // 更新用户的消费金额start
            this.updateUserFinance(payOrder.getOrderNo());
            // 更新用户的消费金额end
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("使用度币共享生成渲染订单失败");
            message.setMessage("使用度币共享生成渲染订单失败");
            return message;
        }
        message.setSuccess(true);
        message.setMessage("生成订单成功");
        return message;
    }


    /**
     * 使用度币共享生成渲染订单接口（pc端使用）
     *
     * @param platformId 平台id
     * @param userId 用户id
     * @param payModelConfigVo
     * @return
     */
    @Override
    public ResultMessage addIntegralShareRenderOrderPc(Integer platformId, Integer userId, PayModelConfigVo payModelConfigVo) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setMsgId(payModelConfigVo.getMsgId());
        if (null == payModelConfigVo) {
            resultMessage.setMessage("下单参数为空：payModelConfigVo：{}" + payModelConfigVo);
            logger.info("下单参数为空：payModelConfigVo：{}" + payModelConfigVo);
            return resultMessage;
        }
        userId = null == userId ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空，sysUser:{}" + sysUser);
            resultMessage.setMessage("用户信息为空");
            return resultMessage;
        }
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null == companyFranchiserGroup) {
            logger.info("用户不具备度币共享的权限，用户id：userId:{}" + userId);
            resultMessage.setMessage("用户不具备度币共享的权限");
            return resultMessage;
        }
        Double totalFee = payModelConfigVo.getTotalFee();
        Double commonalityIntegral = companyFranchiserGroup.getCommonalityIntegral().doubleValue(); // 公共度币
        if (commonalityIntegral < totalFee) {
            logger.info("公共度币不足，生成订单失败。公共度币：commonalityIntegral：{}" + commonalityIntegral);
            resultMessage.setMessage("公共度币不足，生成订单失败");
            return resultMessage;
        }
        String payType = payModelConfigVo.getPayType();
        String tradeType = TradeType.INTEGRAL_SHARE_PAY_TYPE; // 交易方式
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        PayOrder payOrder = getOrder(totalFee.intValue(),payType,
                payModelConfigVo.getProductName(), StringUtils.isEmpty(payModelConfigVo.getProductDesc())
                        == true ? payModelConfigVo.getProductName() : payModelConfigVo.getProductDesc(),tradeType);
        if (totalFee.intValue() == 0) {
            payOrder.setProductName(ProductUtil.getProductNameByProductType(payModelConfigVo.getProductType())); // 处理产品
        } else {
            payOrder.setProductName(ProductUtil.getProductNameByProductType(payModelConfigVo.getProductType()) + PayModelConstantType.INTEGRAL_RENDER_PRODUCT_NAME); // 处理产品
        }
        SysTask sysTask =null;
        String priceInfo = PayModelConstantType.INTEGRAL_RENDER_BIZ_NAME;
        try {
            logger.info("---------- 创建未支付状态的渲染任务 ->start");
            sysTask = this.createNonPaymentTaskNew(payModelConfigVo.getIsTurnOn(), payModelConfigVo.getPlanId(),payModelConfigVo.getPriority(),
                    payModelConfigVo.getViewPoint(),payModelConfigVo.getScene(), Integer.parseInt(payModelConfigVo.getRenderingType()),priceInfo,loginUser);
            if(sysTask == null || sysTask.getId() == null){
                logger.info("创建渲染任务信息：{}" + sysTask.getRemark());
                resultMessage.setMessage(sysTask.getRemark());
                return resultMessage;
            } else {
                if(sysTask.getRenderType() != null){
                    resultMessage.setRenderingType(sysTask.getRenderType()+"");
                }else{
                    logger.error("task id="+sysTask.getId() +" param 'renderType' is null!");
                }
            }
            logger.info("创建未支付状态的渲染任务 ->end");
            payOrder.setTaskId(sysTask.getId());
            payOrder.setPlatformId(platformId);
            sysSave(payOrder, loginUser);
            payOrder.setProductType(payModelConfigVo.getProductType());
            payOrder.setProductId(payModelConfigVo.getProductId()); // 产品id
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);
            payOrder.setCurrentIntegral(commonalityIntegral.intValue()); // 当前度币
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                logger.info("保存度币共享的渲染订单失败");
                resultMessage.setMessage("保存度币共享的渲染订单失败");
                return resultMessage;
            }
            // 更新账户余额和消费金额
            this.updateUserFinance(payOrder.getOrderNo());
            //------------ 渲染任务状态变更:未支付->待执行 ->start
            this.updateNonPaymentTaskNew(sysTask,loginUser, PayType.PAY_MODEL_CONFIG_PAY,payOrder.getPayState(),null);
            //------------ 渲染任务状态变更:未支付->待执行 ->end
            resultMessage.setOrderNo(payOrder.getOrderNo());
            resultMessage.setTaskId(null != sysTask ? sysTask.getId() : null);
            resultMessage.setRenderingType(payModelConfigVo.getRenderingType());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("使用度币共享生成渲染订单失败");
            resultMessage.setMessage("使用度币共享生成渲染订单失败");
            return resultMessage;
        }
        resultMessage.setSuccess(Boolean.TRUE);
        resultMessage.setMessage("生成度币共享渲染订单成功");
        return resultMessage;
    }

    /**
     * 使用度币共享生成购买户型订单
     *
     * @param userId 用户id
     * @param expandType
     * @param platformId 平台id
     * @return
     */
    @Override
    public ResultMessage addIntegralShareOrder(Integer userId, Integer expandType, Integer platformId) {
        ResultMessage resultMessage = new ResultMessage();
        userId = null == userId ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空，sysUser:{}" + sysUser);
            resultMessage.setMessage("用户信息为空");
            return resultMessage;
        }
        if (null == expandType) {
            logger.info("expandType不能为空，expandType:{}" + expandType);
            resultMessage.setMessage("expandType不能为空，expandType:{}" + expandType);
            return resultMessage;
        }
        SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue(SysDictionaryConstant.EXPAND_HOUSE_TYPE, expandType);
        if (null == sysDictionary) {
            logger.info(CLASS_LOG_PREFIX + "找不到对应数据字典购买户型类型！value = " + expandType + ";type=" + SysDictionaryConstant.EXPAND_HOUSE_TYPE);
            resultMessage.setMessage(CLASS_LOG_PREFIX + "找不到对应数据字典购买户型类型！value = " + expandType + ";type=" + SysDictionaryConstant.EXPAND_HOUSE_TYPE);
            return resultMessage;
        }
        Double expandIntegral = Double.parseDouble(sysDictionary.getAtt1());
        Integer expandNumber =  Integer.parseInt(sysDictionary.getAtt2());
        String productName = sysDictionary.getName();
        String productType =  sysDictionary.getValuekey();
        // 户型的单价小于 0 和支付的度币小于0
        if (expandNumber <= 0 || expandIntegral <= 0) {
            resultMessage.setMessage(CLASS_LOG_PREFIX + "户型消耗度币值异常！value = " + expandType + ";type=" + SysDictionaryConstant.EXPAND_HOUSE_TYPE);
            return resultMessage;
        }
        // (单个度币 * 10) *需要购买的户型数
        double totalFee = expandIntegral * 10;
        logger.info(productName + "需要支付的度币  ==> " + totalFee);
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null == companyFranchiserGroup) {
            logger.info("用户不具备度币共享的权限，用户id：userId:{}" + userId);
            resultMessage.setMessage("用户不具备度币共享的权限");
            return resultMessage;
        }
        Double commonalityIntegral = companyFranchiserGroup.getCommonalityIntegral().doubleValue(); // 公共度币
        if (commonalityIntegral < totalFee) {
            logger.info("公共度币不足，生成订单失败。公共度币：commonalityIntegral：{}" + commonalityIntegral);
            resultMessage.setMessage("公共度币不足，生成订单失败");
            return resultMessage;
        }
        PayOrder payOrder = getOrder((int)totalFee, PayType.INTEGRAL_SHARE_PAY, sysDictionary.getId(),
                productType, productName, PayProductConstans.PAY_EXPEND_HOUSE_DESC, TradeType.INTEGRAL_SHARE_PAY_TYPE);
        LoginUser loginUser = new LoginUser(userId, sysUser.getUserType(), sysUser.getUserName(), sysUser.getMobile());
        try {
            sysSave(payOrder, loginUser);
            payOrder.setPlatformId(platformId);
            payOrder.setUserId(userId);
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);
            payOrder.setCurrentIntegral((int) (commonalityIntegral - totalFee));
            payOrder.setOrderDate(new Date());
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                resultMessage.setMessage("保存支付单失败.");
                return resultMessage;
            }
            if (updateUserFinance(payOrder.getOrderNo())) {
                // 更新用户户型数
                SysUser updateUser = new SysUser();
                updateUser.setId(userId);
                updateUser.setGmtModified(new Date());
                updateUser.setModifier(userId + "");
                Integer usableHouseNumber = sysUser.getUsableHouseNumber();
                logger.info("已有可用户型数 ==>" + usableHouseNumber);
                usableHouseNumber = (usableHouseNumber != null) ? (usableHouseNumber + expandNumber) : expandNumber;
                logger.info("购买后可用户型数 ==>" + usableHouseNumber);
                updateUser.setUsableHouseNumber(usableHouseNumber);
                int i = sysUserService.update(updateUser);
                if (i <= 0) {
                    resultMessage.setMessage("更新用户户型数失败.");
                    return resultMessage;
                }
            } else  {
                PayOrder newPayOrder = new PayOrder();
                newPayOrder.setPayState(PayState.PAY_ERROR);
                newPayOrder.setOrderNo(payOrder.getOrderNo());
                this.updatePayState(newPayOrder);
                resultMessage.setMessage("支付失败.");
                return resultMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("使用度币共享生成购买户型订单失败");
            resultMessage.setMessage("使用度币共享生成购买户型订单失败");
            return resultMessage;
        }
        resultMessage.setSuccess(true);
        resultMessage.setMessage("支付成功.");
        return resultMessage;
    }

    /**
     * 度币共享充值（pc端专用）
     *
     * @param userId 用户id
     * @param platformId 平台id
     * @param payOrderModel
     * @return
     */
    @Override
    public ResultMessage addIntegralShareRechargeOrderPc(Integer userId, Integer platformId, PayOrderModel payOrderModel) {
        ResultMessage resultMessage = new ResultMessage();
        String tradeType = TradeType.SCANCODE;
        userId = userId == null ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空");
            resultMessage.setMessage("用户信息为空");
            return resultMessage;
        }
        if (null == payOrderModel) {
            logger.info("支付参数错误");
            resultMessage.setMessage("支付参数错误");
            return resultMessage;
        }
        if (!payOrderModel.getPayType().equals(PayType.WXPAY)
                && !payOrderModel.getPayType().equals(PayType.ALIPAY)) {
            logger.info("支付方式错误");
            resultMessage.setMessage("支付方式错误");
            return resultMessage;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType()) ||
                (!payOrderModel.getPayType().equals(PayType.WXPAY) && !payOrderModel.getPayType().equals(PayType.ALIPAY))) {
            logger.info("支付方式有误，payType:{}" + payOrderModel.getPayType());
            resultMessage.setMessage("支付方式有误，payType:{}" + payOrderModel.getPayType());
            return resultMessage;
        }
        if (null == payOrderModel.getProductId()) {
            resultMessage.setMessage("产品id为空：productId:{}");
            return resultMessage;
        }
        SysDictionary sysDictionary = sysDictionaryService.get(payOrderModel.getProductId());
        int totalFee = payOrderModel.getTotalFee();
        int adjustFee = payOrderModel.getAdjustFee();
        if (null == sysDictionary) {
            logger.info("数据字典中找不到度币信息，产品id为：productId:{}" + payOrderModel.getProductId());
            resultMessage.setMessage("数据字典中找不到度币信息");
            return resultMessage;
        }
        if(totalFee != sysDictionary.getValue().intValue() ){
            logger.error("实际充值："+totalFee+";实际实际充值："+sysDictionary.getValue());
            resultMessage.setMessage("充值金额与实际充值金额不一致！");
            return resultMessage;
        }
        if(adjustFee != Utils.getIntValue(sysDictionary.getAtt1().trim()) ){
            logger.error("赠送金额："+adjustFee+";实际赠送金额："+sysDictionary.getAtt1());
            resultMessage.setMessage("充值金额与实际充值金额不一致！");
            return resultMessage;
        }
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null == companyFranchiserGroup) {
            logger.info("用户不具备度币共享的权限，用户id：userId:{}" + userId);
            resultMessage.setMessage("用户不具备度币共享的权限");
            return resultMessage;
        }
        Double commonalityIntegral = companyFranchiserGroup.getCommonalityIntegral().doubleValue(); // 公共度币
        PayOrder payOrder = getOrder(totalFee, payOrderModel.getPayType(), PayProductConstans.RECHARGE_PRODUCT_NAME, PayProductConstans.RECHARGE_PRODUCT_DESC, tradeType);
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        try {
            payOrder.setProductType(PayProductConstans.RECHARGE_PRODUCT_TYPE);
            payOrder.setPlatformId(platformId);
            payOrder.setAdjustFee((int)adjustFee);
            sysSave(payOrder, loginUser);
            payOrder.setUserId(userId);
            payOrder.setProductId(payOrderModel.getProductId());
            payOrder.setBizType(BizType.RECHARGE);
            payOrder.setFinanceType(FinanceType.IN);
            payOrder.setCurrentIntegral((int)(commonalityIntegral+Double.parseDouble(sysDictionary.getAtt1())));
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                logger.info("保存在线充值单失败");
                resultMessage.setMessage("保存在线充值单失败");
                return resultMessage;
            }
            payOrder.setId(id);
            logger.info("生成订单成功，订单号是：orderNo:{}" + payOrder.getOrderNo());
            ScanPayReqData reqData = new ScanPayReqData();
            reqData.setOrderId(id);
            if (PayType.WXPAY.equals(payOrderModel.getPayType())){
                logger.info("微信扫码支付start");
                String flag = "SUCCESS";
                String timeExpire = Utils.getTimeExpire(30);
                UnifiedOrderService orderService = new UnifiedOrderService();
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(PayProductConstans.RECHARGE_PRODUCT_NAME, payOrder.getOrderNo(),
                        totalFee, WxTradeType.NATIVE, timeExpire,ResourceConfig.WXPAY_INTEGRAL_SHARE_NOTIFY_URL);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    String prepayId = unifiedOrderResData.getPrepay_id();
                    String wx_codeUrl = unifiedOrderResData.getCode_url();
                    String codeUrl = QrCodeUtil.generateQrCode(wx_codeUrl, payOrder.getOrderNo());
                    payOrder.setCodeUrl(codeUrl);
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setCodeUrl(wx_codeUrl);
                    payOrder.setNotifyUrl(ResourceConfig.WXPAY_INTEGRAL_SHARE_NOTIFY_URL);
                    this.update(payOrder);
                    reqData.setCode_url(wx_codeUrl);//直接返回访问路径，不在返回2维码
                    logger.info("获取微信二维码成功，payCodeUrl：{}" + wx_codeUrl + ",度币共享的微信回调地址是：" + ResourceConfig.WXPAY_INTEGRAL_SHARE_NOTIFY_URL);
                    resultMessage.setObj(reqData);

                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId.intValue());
                    mgrRechargeLog.setRechargeType(2);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount(Double.valueOf(totalFee)/100);
                    mgrRechargeLog.setAdministratorId(userId.intValue());
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    BasePlatform basePlatform = basePlatformService.get(platformId);
                    if (null != basePlatform) {
                        mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    }
                    this.sysSave(payOrder, loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                } else {
                    logger.info("获取微信二维码失败，payCodeUrl：{}为空");
                    resultMessage.setMessage("获取微信二维码失败，payCodeUrl：{}为空");
                    return resultMessage;
                }
                logger.info("微信扫码支付end");
            } else  if (PayType.ALIPAY.equals(payOrderModel.getPayType())) {
                logger.info("支付宝扫码支付start");
                payOrder.setNotifyUrl(ResourceConfig.ALIPAY_INTEGRAL_SHARE_NOTIFY_URL);
                ResultMessage message = com.sandu.pay.alipay.model.ScanPayUtil.addScanpayOrder(payOrder, reqData);
                if (message.isSuccess()) {
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setCodeUrl(reqData.getQrCodePath());
                    this.update(payOrder);
                    logger.info("支付宝扫码接口返回" + gson.toJson(reqData));
                    logger.info("获取支付宝二维码成功，payCodeUrl：{}" + reqData.getQrCodePath() + ",度币共享的支付宝回调地址是：" + ResourceConfig.ALIPAY_INTEGRAL_SHARE_NOTIFY_URL );
                    resultMessage.setObj(reqData);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId.intValue());
                    mgrRechargeLog.setRechargeType(3);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount(Double.valueOf(totalFee)/100);
                    mgrRechargeLog.setAdministratorId(userId.intValue());
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    BasePlatform basePlatform = basePlatformService.get(platformId);
                    if (null != basePlatform) {
                        mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    }
                    this.sysSave(payOrder, loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                } else {
                    logger.info("获取支付宝二维码失败，payCodeUrl：{}为空");
                    resultMessage.setMessage("获取支付宝二维码失败，payCodeUrl：{}为空");
                    return resultMessage;
                }
                payOrder.setPayState(PayState.PAYING);
                payOrder.setCodeUrl(reqData.getQrCodePath());
                this.update(payOrder);
                logger.info("支付宝扫码支付end");
            } else {
                logger.info("支付方式错误");
                resultMessage.setMessage("支付方式错误");
                return resultMessage;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        resultMessage.setSuccess(true);
        resultMessage.setMessage("预下单成功");
        return resultMessage;
    }

    /**
     * 微信扫码支付处理（度币共享的pc端充值）
     *
     * @param sbResult
     * @param success
     * @return
     */
    @Override
    public boolean wxNotifySharePayPc(String sbResult, boolean success) {
        logger.info(CLASS_LOG_PREFIX + "微信回调结果:" + sbResult);
        if (sbResult.length() > 0) {
            // 测试回调接口是否会修改渲染任务状态和生成渲染配置文件所以注释,提交解开注释
            ResultNotify result = (ResultNotify) Util.getObjectFromXML(sbResult, ResultNotify.class);
            if (result != null) {
                PayOrder order = new PayOrder();
                String orderNo = result.getOut_trade_no();
                String openId = result.getOpenid();
                String refId = result.getTransaction_id();
                order.setOrderNo(orderNo);
                order.setOpenId(openId);
                order.setRefId(refId);
                order.setPayState(PayState.PAY_ERROR);
                if (result.getResult_code().equalsIgnoreCase(PayState.SUCCESS)
                        && result.getReturn_code().equalsIgnoreCase(PayState.SUCCESS)) {
                    success = true;
                    order.setPayState(PayState.SUCCESS);
                    logger.info("度币共享充值微信扫码回调更新订单表结束");
                    this.updatePayState(order);
                    logger.info(CLASS_LOG_PREFIX + "度币共享充值微信扫码回调发送消息成功");
                    boolean flag =  updateUserFinance(orderNo);
                    logger.info("度币共享充值微信扫码回调更新用户消费结果为：" + flag);
                    //websocket通知
                    logger.info("websocket通知====start");
                    sendRenderPayStateNew(orderNo);
                    logger.info("websocket通知====end");
                }
            }
        }
        return success;
    }

    /**
     * 度币共享开通移动端（移动2b专用）
     *
     * @param platformId 平台id
     * @param payMobileLoginVo
     * @return
     */
    @Override
    public ResultMessage addIntegralShareLoginPay(Integer platformId, PayMobileLoginVo payMobileLoginVo) {
        ResultMessage resultMessage = new ResultMessage();
        String tradeType = TradeType.INTEGRAL_SHARE_PAY_TYPE;
        Integer userId = payMobileLoginVo == null ? 0 : payMobileLoginVo.getUserId();
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            resultMessage.setMessage("用户信息为空");
            return resultMessage;
        }
        UserJurisdiction userJurisdiction = userJurisdictionService.getMobile2bUserJurisdiction(userId, platformId);
        if (null != userJurisdiction) {
            resultMessage.setMessage("您已开通移动端，请到登录页面进行登录");
            return resultMessage;
        }
        if (!payMobileLoginVo.getPayType().equals(PayType.INTEGRAL_SHARE_PAY)) {
            resultMessage.setMessage("支付方式异常");
            return resultMessage;
        }
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueKey(SysDictionaryConstant.PAY_MOBILE_LOGIN_INTEGRAL_TYPE,
                SysDictionaryConstant.PAY_MOBILE_LOGIN_INTEGRAL_KEY);
        if (null == sysDictionary || null == sysDictionary.getAtt1()) {
            resultMessage.setMessage("找不到数据字典中开通移动端所需要的度币");
            return resultMessage;
        }
        int totalFee = Double.valueOf(sysDictionary.getAtt1()).intValue();
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null == companyFranchiserGroup) {
            resultMessage.setMessage("用户不具备度币共享的权限");
            return resultMessage;
        }
        Double commonalityIntegral = companyFranchiserGroup.getCommonalityIntegral().doubleValue(); // 公共度币
        if (totalFee > commonalityIntegral.intValue()) {
            resultMessage.setMessage("度币不足，请使用微信充值或支付宝充值");
            return resultMessage;
        }
        String productName = PayProductConstans.MOBILE_OPENED_AND_POSTPONE_INTEGRAL_NAME.replaceAll("@integral", String.valueOf(totalFee/10));
        PayOrder payOrder = getOrder(totalFee, payMobileLoginVo.getPayType(), productName,
                productName, tradeType);
        try {
            sysSave(payOrder, loginUser);
            payOrder.setUserId(userId);
            payOrder.setPlatformId(platformId); // 平台id
            payOrder.setProductType(PayProductConstans.MOBILE_OPENED_AND_POSTPONE_TYPE);
            payOrder.setProductId(sysDictionary.getId());
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);
            payOrder.setCurrentIntegral(commonalityIntegral.intValue()); // 当前度币
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                resultMessage.setMessage("保存支付单失败.");
                return resultMessage;
            }
            // 更新用户的移动端开通信息
            this.updateUserMobileInfo(payOrder.getOrderNo());
            // 更新账户余额和消费金额
            updateUserFinance(payOrder.getOrderNo());
        } catch (Exception e) {
            logger.error("度币共享开通手机端登录发生异常.",e);
            resultMessage.setMessage("度币共享开通手机端登录发生异常.");
            return resultMessage;
        }
        resultMessage.setSuccess(true);
        resultMessage.setMessage("支付成功.");
        return resultMessage;
    }

    /**
     * 预付款支付购买产品（度币付款，包含生成渲染订单）
     *
     * @param productId 产品id
     * @param productType 产品类型
     * @param productName 产品名称
     * @param productDesc 产品详情
     * @param totalFee 总价钱
     * @param mode
     * @param type
     * @param isTurnOn
     * @param planId
     * @param viewPoint
     * @param scene
     * @param msgId
     * @param renderingType
     * @param basePlatform 平台对象
     * @param level
     * @param priority
     * @param temporaryPic
     * @param orderNo 订单号
     * @param authorization
     * @param userId 用户id
     * @param PAYINGORDERMAX 未支付订单最大允许数
     * @return
     */
    @Override
    public ResultMessage addpredepositpayPc(Integer productId, String productType, String productName, String productDesc,
                                            Integer totalFee, Integer mode, String type, Integer isTurnOn, Integer planId,
                                            Integer viewPoint, Integer scene, String msgId, String renderingType, BasePlatform basePlatform, String level,
                                            Integer priority, String temporaryPic, String orderNo, String authorization, Integer userId,  final Integer PAYINGORDERMAX) {
        ResultMessage message = new ResultMessage();
        userId = null == userId ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            message.setMessage("用户信息为空");
            return message;
        }
        message.setMsgId(msgId);
        Integer platformId = basePlatform.getId();
        String platformCode = basePlatform.getPlatformCode();
        LoginUser loginUser = new LoginUser(userId.intValue(), sysUser.getUserType(), sysUser.getUserName(), sysUser.getMobile());
        if(!StringUtils.equals("common_render", productType)&&!StringUtils.equals("panorama_render", productType)
                &&!StringUtils.equals("HD_render", productType)&&!StringUtils.equals("UHD_render", productType)
                &&!StringUtils.equals("video", productType)&&!StringUtils.equals("roam720", productType)
                ){
            String payType = PayType.PREDEPOSIT;
//            if (totalFee == 0) {
//                message.setMessage("支付金额为0!");
//                return message;
//            }
            if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
                    || Utils.isBlank(productType)) {
                message.setMessage("传参异常.");
                return message;
            }
            String tradeType = TradeType.PREDEPOSIT;
            // 直接从账户余额中扣除
            PayOrder payOrder = this.getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
//            logger.info("直接从账户余额中扣除:");
//            int balanceAmount = 0;
//            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
//            if (null != companyFranchiserGroup) {
//                balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
//            } else {
//                PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
//                if (null == payAccount) {
//                    message.setMessage("用户度币信息为空，生成订单失败");
//                    return message;
//                }
//                balanceAmount = payAccount.getBalanceAmount().intValue();
//            }
//            if (balanceAmount < totalFee.intValue()) {
//                message.setMessage("当前用户的账户余额不足.");
//                return message;
//            }
            // 插入支付订单
            try {
                this.sysSave(payOrder, loginUser);
                payOrder.setUserId(userId.intValue());
                payOrder.setPlatformId(platformId); // 平台id
                payOrder.setBizType(BizType.BUY);
                payOrder.setFinanceType(FinanceType.OUT);
                payOrder.setPayState(PayState.SUCCESS);
                int id = this.add(payOrder);
                logger.info("int id = payOrderService.add(payOrder)------〉"+id);
                if (id == 0) {
                    message.setMessage("保存支付单失败.");
                    return message;
                }
                // 更新账户余额和消费金额
                //this.updateUserFinance(payOrder.getOrderNo());
                //判断用户是否需要自动升级
                this.processAfterConsume(payOrder.getOrderNo());
                //TODO   余额支付临时处理
                message.setOrderNo(payOrder.getOrderNo());
                message.setSuccess(true);
                message.setMessage("付款成功.");
                return message;
            } catch (Exception e) {
                e.printStackTrace();
                message.setMessage("预付款支付购买产品发生异常.");
                return message;
            }
        } else  {
            logger.info("=======pc端开始渲染=========");
            String payType = PayType.PREDEPOSIT;
            // 高清渲染参数验证 ->start
            if( planId == null ){
                message.setMessage("planId不能为空!");
                return message;
            }
            /*优先级，如果等null  那么优先级月底*/
            if( priority == null ){
                priority = 1;
            }
            /*视角*/
            if(viewPoint==null){
                message.setMessage("参数viewPoint不能为空!");
                return message;
            }
            /*场景 白天黑夜 黄昏？*/
            if(scene==null){
                message.setMessage("参数scene不能为空!");
                return message;
            }
            /*渲染类型   720    照片级？*/
            if(StringUtils.isBlank(renderingType)){
                message.setMessage("参数renderingType不能为空");
                return message;
            }
            if(RenderTypeCode.COMMON_PICTURE_LEVEL != Integer.parseInt(renderingType)){//照片级渲染免费，这俩字段可以不传
                // mode
                if(mode == null){
                    message.setMessage("参数mode不能为空");
                    return message;
                }
                // type
                if(StringUtils.isBlank(type)){
                    message.setMessage("参数type不能为空");
                    return message;
                }
            }
            // 验证(超过10个未支付的订单,则无法再次发起订单) ->start
            int payOrderNonPayment = this.getCountByUserIdAndStatus(userId.intValue(), PayState.PAYING);
            if(payOrderNonPayment > PAYINGORDERMAX){
                message.setMessage("您的未支付订单已超过10个,请五分钟后再渲染");
                return message;
            }
            // 验证(超过10个未支付的订单,则无法再次发起订单) ->end
            if (Utils.isBlank(payType) || Utils.isBlank(productDesc) || Utils.isBlank(productName)
                    || Utils.isBlank(productType)) {
                message.setMessage("传参异常.");
                logger.error("参数 payType,productDesc,productName,productType其中某个或多个为空!");
                return message;
            }
            // 金钱处理 ->start(需要改造成通过renderType得到价格)
            String priceInfo="";
            if(RenderTypeCode.COMMON_PICTURE_LEVEL == Integer.parseInt(renderingType)){
                totalFee=0;
                priceInfo="照片级渲染免费";
            }else{
                totalFee = 1;
                SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue(type, mode);
                if(sysDictionary == null){
                    throw new RuntimeException("------支付类数据字典未找到:type=" + type + ";mode=" + mode);
                }
                String totalFeeStr = sysDictionary.getAtt1();
                priceInfo = sysDictionary.getName();//金额及尺寸信息
                if(org.apache.commons.lang3.StringUtils.isNotBlank(totalFeeStr)){
                    totalFee = Integer.parseInt(totalFeeStr);
                }
            }
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
            if (null == companyFranchiserGroup) {
                logger.info("用户不具备共享度币的权限======");
                // 金钱处理 ->end
                //判断用户是否需要自动升级
                String tradeType = TradeType.PREDEPOSIT;
                // 直接从账户余额中扣除
                PayOrder payOrder = this.getOrder((int)totalFee, payType, productId, productType, productName, productDesc, tradeType);
//                PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
//                if (null == payAccount) {
//                    logger.info("用户id：userId:{}" + userId + ",平台id：platformId:{}" + basePlatform.getId() + "的度币信息为空");
//                    message.setMessage("用户度币信息为空，生成订单失败");
//                    return message;
//                }
//                int balanceAmount = payAccount.getBalanceAmount().intValue();
//                if (balanceAmount < totalFee.intValue()) {
//                    message.setMessage("当前用户的账户余额不足.");
//                    return message;
//                }
                SysTask sysTask =null;
                try {
                    this.sysSave(payOrder, loginUser);
                    payOrder.setUserId(userId.intValue());
                    payOrder.setPlatformId(platformId); // 平台id
                    payOrder.setBizType(BizType.BUY);
                    payOrder.setFinanceType(FinanceType.OUT);
                    payOrder.setPayState(PayState.SUCCESS);
                    //---------- 创建未支付状态的渲染任务 ->start
                    sysTask = this.createNonPaymentTaskNew(isTurnOn, planId,priority, viewPoint,
                            scene, Integer.parseInt(renderingType),priceInfo,loginUser);
                    if(sysTask.getId() == null){
                        message.setMessage(sysTask.getRemark());
                        return message;
                    }
                    payOrder.setTaskId(sysTask.getId());
                    // 创建未支付状态的渲染任务 ->end
                    int id = this.add(payOrder);
                    if (id == 0) {
                        message.setMessage("保存支付单失败.");
                        return message;
                    }
                    // 更新账户余额和消费金额
                    //this.updateUserFinance(payOrder.getOrderNo());
                    message.setSuccess(true);
                    message.setMessage("付款成功.");
                    message.setOrderNo(payOrder.getOrderNo());
                    message.setTaskId(sysTask.getId());
                    //------------ 渲染任务状态变更:未支付->待执行 ->start
                    this.updateNonPaymentTaskNew(sysTask,loginUser,payType,payOrder.getPayState(),null);
                    // 渲染任务状态变更:未支付->待执行 ->end
                    //判断用户是否需要自动升级
                    this.processAfterConsume(payOrder.getOrderNo());
                    //返回渲染类型
                    if(sysTask != null){
                        if(sysTask.getRenderType() != null){
                            message.setRenderingType(sysTask.getRenderType()+"");
                        }else{
                            logger.error("task id="+sysTask.getId() +" param 'renderType' is null!");
                        }
                    }

                } catch (Exception e) {
                    logger.error("error===payorder====>",e);
                    message.setMessage("预付款支付购买产品发生异常.");
                    return message;
                }
            } else {
                logger.info("用户有具备共享度币的权限======");
                PayModelConfigVo payModelConfigVo = new PayModelConfigVo();
                payModelConfigVo.setMsgId(msgId);
                payModelConfigVo.setProductId(productId);
                payModelConfigVo.setProductType(productType);
                payModelConfigVo.setProductName(productName);
                payModelConfigVo.setProductDesc(productDesc);
                payModelConfigVo.setRenderingType(renderingType);
                payModelConfigVo.setPlanId(planId);
                payModelConfigVo.setIsTurnOn(isTurnOn);
                payModelConfigVo.setPriority(priority);
                payModelConfigVo.setViewPoint(viewPoint);
                payModelConfigVo.setScene(scene);
                payModelConfigVo.setTotalFee(Double.valueOf(totalFee));
                payModelConfigVo.setPayType(PayType.INTEGRAL_SHARE_PAY);
                ResultMessage resultMessage =  this.addIntegralShareRenderOrderPc(basePlatform.getId(),userId.intValue(), payModelConfigVo);
                return resultMessage;
            }
        }
        message.setSuccess(true);
        return message;
    }

    /**
     * pc端充值（微信和支付宝扫码支付）
     *
     * @param totalFee 总价钱
     * @param adjustFee 赠送金额
     * @param payType 支付类型
     * @param productId 产品id
     * @param msgId
     * @param userId 用户id
     * @param basePlatform 平台对象
     * @return
     */
    @Override
    public ResultMessage addscanrechargePc(double totalFee, double adjustFee, String payType, Integer productId, String msgId, Integer userId, BasePlatform basePlatform)
            throws Exception {
        ResultMessage message = new ResultMessage();
        if (null == msgId) {
            logger.info("msgId为空，msgId：{}" + msgId);
            message.setMessage("msgId为空，msgId：{}" + msgId);
            return message;
        }
        message.setMsgId(msgId);
        userId = null == userId ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            message.setMessage("用户信息为空");
            return message;
        }
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
        if (null == companyFranchiserGroup) {
            logger.info("用户不具备共享度币的权限======");
            try {
                if (Utils.isBlank(payType)) {
                    message.setMessage("payType传参异常.");
                    return message;
                }
                if ("0".equals(totalFee)) {
                    message.setMessage("支付金额不能为0.");
                    return message;
                }
                String productName = "三度云享家_会员充值";
                String productDesc = "三度云享家_会员充值";
                String productType = "recharge";
                SysDictionary sysDictionary = sysDictionaryService.get(productId);
                if( sysDictionary == null ){
                    message.setMessage("productId传参异常！");
                    return message;
                }
                /*if( (int)totalFee != sysDictionary.getValue().intValue() ){
                    logger.error("实际充值："+totalFee+";实际实际充值："+sysDictionary.getValue());
                    message.setMessage("充值金额与实际充值金额不一致！");
                    return message;
                }
                if( (int)adjustFee != Utils.getIntValue(sysDictionary.getAtt1().trim()) ){
                    logger.error("赠送金额："+adjustFee+";实际赠送金额："+sysDictionary.getAtt1());
                    message.setMessage("充值金额与实际充值金额不一致！");
                    return message;
                }*/
                String tradeType = TradeType.SCANCODE;
                PayOrder payOrder = this.getOrder((int)totalFee, payType, productName, productDesc, tradeType);
                ScanPayReqData reqData = new ScanPayReqData();
                payOrder.setProductType(productType);
                LoginUser loginUser = new LoginUser(userId.intValue(), sysUser.getUserType(), sysUser.getUserName(), sysUser.getMobile());
                // 插入支付订单
                this.sysSave(payOrder, loginUser);
                payOrder.setProductId(productId);
                payOrder.setUserId(userId.intValue());
                payOrder.setPlatformId(basePlatform.getId()); // 平台id
                payOrder.setAdjustFee((int)adjustFee);
                payOrder.setBizType(BizType.RECHARGE);
                payOrder.setFinanceType(FinanceType.IN);
                int id = this.add(payOrder);
                payOrder.setId(id);
                reqData.setOrderId(id);
                if (id == 0) {
                    message.setMessage("保存在线充值单失败.");
                    return message;
                }
                if (payType.equalsIgnoreCase(PayType.WXPAY)) {
                    //获取扫码支付信息(生成二维码信息)
                    message = this.addScanpayOrderPc(payOrder,productName,totalFee,loginUser,userId.longValue(),reqData);
                    if (null != message) {
                        message.setMessage(message.getMessage());
                        message.setObj(message.getObj());
                        message.setSuccess(message.isSuccess());
                        message.setMsgId(msgId);
                    }
                }
                if (payType.equalsIgnoreCase(PayType.ALIPAY)) {
                    message = this.addScanpayOrderPc(payOrder, reqData);
                    if (message.isSuccess()) {
                        message.setSuccess(message.isSuccess());
                        message.setMsgId(msgId);
                        message.setMessage("成功生成在线充值单");
                        payOrder.setPayState(PayState.PAYING);
                        payOrder.setCodeUrl(reqData.getQrCodePath());
                        this.update(payOrder);
                        //FIXME:每次获取充值单就添加一条充值记录
                        Date date = new Date();
                        MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                        mgrRechargeLog.setUserId(userId.intValue());
                        mgrRechargeLog.setRechargeType(3);
                        mgrRechargeLog.setRechargeStatus(2);
                        mgrRechargeLog.setRechargeAmount(totalFee/100);
                        mgrRechargeLog.setAdministratorId(userId.intValue());
                        mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                        mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                        mgrRechargeLog.setGmtCreate(date);
                        mgrRechargeLog.setGmtModified(date);
                        mgrRechargeLog.setCreator(loginUser.getLoginName());
                        mgrRechargeLog.setModifier(loginUser.getLoginName());
                        mgrRechargeLog.setIsDeleted(0);
                        mgrRechargeLog.setSysCode(getSysCode());
                        this.sysSave(payOrder, loginUser);
                        mgrRechargeLogService.add(mgrRechargeLog);
                    } else {
                        message.setMessage(message.getMessage());
                        return message;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                message.setSuccess(false);
                message.setMessage("下单异常.");
                return message;
            }
        } else {
            logger.info("用户有具备共享度币的权限======");
            PayOrderModel payOrderModel = new PayOrderModel();
            payOrderModel.setPayType(payType);
            payOrderModel.setProductId(productId);
            payOrderModel.setTotalFee(Double.valueOf(totalFee).intValue());
            payOrderModel.setAdjustFee(Double.valueOf(adjustFee).intValue());
            ResultMessage resultMessage =  this.addIntegralShareRechargeOrderPc(userId.intValue(),basePlatform.getId(), payOrderModel);
            resultMessage.setMsgId(msgId);
            return resultMessage;
        }
        message.setSuccess(true);
        return message;
    }


    /**
     * 组装系统字段
     * @param model
     * @param loginUser
     */
    public void sysSave(SysTask model, LoginUser loginUser){
        if (model != null) {
            if (model.getId() == null) {
                //	model.setUserId(loginUser.getId());
                model.setGmtCreate(new Date());
                model.setCreator(loginUser.getLoginName());
                model.setIsDeleted(0);
                if (model.getSysCode() == null || "".equals(model.getSysCode())) {
                    model.setSysCode(
                            Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }
            model.setGmtModified(new Date());
            model.setModifier(loginUser.getLoginName());
        }
    }

    /**
     * 获取度币共享的主子账号的消费记录总条数
     *
     * @param payOrderSearch
     * @return
     */
    @Override
    public int getShareCount(PayOrderSearch payOrderSearch) {
        logger.info("获取度币共享的主子账号的消费记录总条数start========");
        return this.payOrderMapper.getShareCount(payOrderSearch);
    }

    /**
     * 获取度币共享的主子账号的消费记录列表
     *
     * @param payOrderSearch
     * @return
     */
    @Override
    public List<PayOrder> getSharePaginatedList(PayOrderSearch payOrderSearch) {
        return this.payOrderMapper.getSharePaginatedList(payOrderSearch);
    }

    /**
     * 获取度币共享的主子账号的消费记录总条数
     *
     * @param payOrderSearch
     * @return
     */
    @Override
    public int getShareCountPc(PayOrderSearch payOrderSearch) {
        return this.payOrderMapper.getShareCountPc(payOrderSearch);
    }

    /**
     * 获取度币共享的主子账号的消费记录列表
     *
     * @param payOrderSearch
     * @return
     */
    @Override
    public List<PayOrder> getSharePaginatedListPc(PayOrderSearch payOrderSearch) {
        List<PayOrder> list = this.payOrderMapper.getSharePaginatedListPc(payOrderSearch);
        for(PayOrder order : list){
            if(BizType.RECHARGE.equals(order.getProductType())){
                if(StringUtils.isNotEmpty(order.getPayType()) && PayType.WXPAY.equals(order.getPayType())){
                    order.setPlanName(com.sandu.pay.order.model.enums.PayType.WEIXIN.getName());
                }else if(StringUtils.isNotEmpty(order.getPayType()) && PayType.ALIPAY.equals(order.getPayType())){
                    order.setPlanName(com.sandu.pay.order.model.enums.PayType.ALIPAY.getName());
                }
            }
        }

        return list;
    }

    /**
     *  购买渲染包年包月订单（经销商用户专用）
     *
     * @param payModelConfigVo
     * @param userId 用户id
     * @param platformId 平台id
     * @return
     */
    @Override
    public ResultMessage addRenderPayModelConfigFranchiser(PayModelConfigVo payModelConfigVo, Integer userId, Integer platformId) {
        ResultMessage resultMessage = new ResultMessage();
        if (!payModelConfigVo.getPayType().equals(PayType.WXPAY)
                && !payModelConfigVo.getPayType().equals(PayType.ALIPAY)
                && !payModelConfigVo.getPayType().equals(PayType.PREDEPOSIT)) {
            resultMessage.setMessage("支付方式错误");
            return resultMessage;
        }
        if (!payModelConfigVo.getTradeType().equals(TradeType.APP)
                && !payModelConfigVo.getTradeType().equals(TradeType.SCANCODE)
                && !payModelConfigVo.getTradeType().equals(TradeType.PREDEPOSIT)) {
            resultMessage.setMessage("交易方式错误");
            return resultMessage;
        }
        if (null == payModelConfigVo.getPayModelConfigId()) {
            resultMessage.setMessage("参数payModelConfigId不能为空");
            return resultMessage;
        }
        PayModelConfig payModelConfig = payModelConfigService.get(payModelConfigVo.getPayModelConfigId());
        if (null == payModelConfig || payModelConfig.getIsDeleted() != 0
                || !payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE_FRANCHISER)) {
            resultMessage.setMessage("渲染包年包月套餐数据为空");
            return resultMessage;
        }
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            resultMessage.setMessage("用户信息为空");
            return resultMessage;
        }
        // 当前如果是包月，那么判断是否有未过期的包年，如果有，那么就不给购买
        List<Map<String, Object>> list = payModelGroupRefService.getUserRefInfoList(sysUser.getFranchiserGroupId(),PayModelConstantType.RENDER_TYPE_FRANCHISER,null);
        if (payModelConfig.getTimeType() == 0) {
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).get("timeType").toString().equals("1")) {
                        logger.info("当前经销商用户存在未过期的包年，请在包年过期后购买，用户id：userId:{}" + userId);
                        resultMessage.setMessage("当前经销商用户存在未过期的包年，请在包年过期后购买");
                        return resultMessage;
                    }
                }

            }
        }
        Date endDate = new Date();
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (null != list.get(i).get("expiryTime")) {
                    endDate = (Date)list.get(i).get("expiryTime");
                    break;
                }
            }
        }
        String tradeType = TradeType.SCANCODE;
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        String productName =  PayProductConstans.RENDER_PAY_PRODUCT_NAME;
        if (payModelConfig.getTimeType() == 0) {
            productName = productName.replace("@replace_str","包月");
        } else if (payModelConfig.getTimeType() == 1) {
            productName = productName.replace("@replace_str","包年");
        }
        PayOrder payOrder = getOrder(Double.valueOf(payModelConfig.getPackagePrice()).intValue(),payModelConfigVo.getPayType(),
                productName, productName,tradeType);
        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, platformId);
            if (null == payAccount) {
                resultMessage.setMessage("用户度币信息为空，生成订单失败");
                return resultMessage;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        try {
            payOrder.setPlatformId(platformId);
            sysSave(payOrder, loginUser);
            payOrder.setProductType(PayProductConstans.RENDER_FRANCHISER);
            payOrder.setProductId(payModelConfig.getId());
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            payOrder.setAtt1(String.valueOf(sysUser.getFranchiserGroupId())); // 业务id（备用字段1）
            payOrder.setAtt2(String.valueOf(payModelConfig.getId())); // 付款方式表id（备用字段2）
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                logger.info("保存订单失败");
                resultMessage.setMessage("保存订单失败");
                return resultMessage;
            }
            payOrder.setId(id);
            String timeExpire = Utils.getTimeExpire(30);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", payOrder.getId());
            // 支付内容拼接start
            StringBuffer sb = new StringBuffer();
            if (payModelConfig.getTimeType() == 0) {
                // 表示月 remainDay
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(endDate);
                calendar.add(calendar.MONTH, payModelConfig.getTimeUnit());// 把日期往后增加月份.整数往后推,负数往前移动
                endDate = calendar.getTime();
                sb.append("渲染包月服务费，");
                sb.append((payModelConfig.getPackagePrice()/100)/payModelConfig.getTimeUnit() + "元/月，共计" + payModelConfig.getPackagePrice()/100 + "元，");
                sb.append("服务的有效期为" + UtilDate.getStringDate(new Date()));
                sb.append("至" + UtilDate.getStringDate(endDate));
            }  else if (payModelConfig.getTimeType() == 1) {
                // 表示年
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(endDate);
                calendar.add(calendar.YEAR, payModelConfig.getTimeUnit());// 把日期往后增加月份.整数往后推,负数往前移动
                endDate = calendar.getTime();
                sb.append("渲染包年服务费，");
                sb.append((payModelConfig.getPackagePrice()/100)/payModelConfig.getTimeUnit() + "元/年，共计" + payModelConfig.getPackagePrice()/100 + "元，");
                sb.append("服务的有效期为" + UtilDate.getStringDate(new Date()));
                sb.append("至" + UtilDate.getStringDate(endDate));
            }
            logger.info("渲染包年包月用户经销商组id：franchiserGroupId:{}" + sysUser.getFranchiserGroupId() + ",支付内容为：" + sb.toString());
            map.put("payContent", sb.toString());
            // 支付内容拼接end
            if (payModelConfigVo.getPayType().equals(PayType.WXPAY)
                    && payModelConfigVo.getTradeType().equals(TradeType.SCANCODE)) {
                logger.info("微信扫码支付start");
                String flag = "SUCCESS";
                UnifiedOrderService orderService = new UnifiedOrderService();
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(productName, payOrder.getOrderNo(),
                        Double.valueOf(payModelConfig.getPackagePrice()).intValue(), WxTradeType.NATIVE, timeExpire, ResourceConfig.WXPAY_PAY_MODEL_NOTIFY_URL);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    String prepayId = unifiedOrderResData.getPrepay_id();
                    String wx_codeUrl = unifiedOrderResData.getCode_url();
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    payOrder.setCodeUrl(wx_codeUrl);
                    payOrder.setNotifyUrl(ResourceConfig.WXPAY_PAY_MODEL_NOTIFY_URL);
                    this.update(payOrder);
                    map.put("payCodeUrl", wx_codeUrl);
                    resultMessage.setObj(map);
                } else {
                    resultMessage.setMessage("获取微信二维码失败，payCodeUrl：{}为空");
                    return resultMessage;
                }
                logger.info("微信扫码支付end");
            } else if (payModelConfigVo.getPayType().equals(PayType.ALIPAY)
                    && payModelConfigVo.getTradeType().equals(TradeType.SCANCODE)) {
                logger.info("支付宝扫码支付start");
                payOrder.setNotifyUrl(ResourceConfig.ALIPAY_PAY_MODEL_NOTIFY_URL);
                ScanPayReqData reqData = new ScanPayReqData();
                ResultMessage message = ScanPayUtil.addScanpayOrderGetQrCode(payOrder,reqData,Double.valueOf(payModelConfig.getPackagePrice()).intValue());
                if (message.isSuccess()) {
                    map.put("payCodeUrl", reqData.getQrCodePath());
                    resultMessage.setObj(map);
                } else {
                    resultMessage.setMessage("获取支付宝二维码失败，payCodeUrl：{}为空");
                    return resultMessage;
                }
                payOrder.setPayState(PayState.PAYING);
                payOrder.setCodeUrl(reqData.getQrCodePath());
                this.update(payOrder);
                logger.info("支付宝扫码支付end");
            } else {
                logger.info("支付方式错误");
                resultMessage.setMessage("支付方式错误");
                return resultMessage;
            }
        } catch (Exception e) {
            logger.error("购买包年包月下单异常，内容是：" ,e);
            resultMessage.setMessage("下单异常");
            return resultMessage;
        }
        resultMessage.setMessage("success");
        resultMessage.setSuccess(true);
        return resultMessage;
    }

    /**
     * 包年包月生成渲染订单（经销商用户专用）
     *
     * @param platformId  平台id
     * @param userId 用户id
     * @param payModelConfigVo
     * @return
     */
    @Override
    public ResultMessage addRenderPayOrderFranchiser(Integer platformId, Integer userId, PayModelConfigVo payModelConfigVo) {
        ResultMessage resultMessage = new ResultMessage();
        if (null == platformId) {
            resultMessage.setMessage("平台id为空");
            return resultMessage;
        }
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            resultMessage.setMessage("用户信息为空");
            return resultMessage;
        }
        if (null == payModelConfigVo.getProductId()) {
            resultMessage.setMessage("产品id为空");
            return resultMessage;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getProductType())) {
            resultMessage.setMessage("产品类型为空");
            return resultMessage;
        }
        if(!StringUtils.equals("common_render", payModelConfigVo.getProductType())&&!StringUtils.equals("panorama_render", payModelConfigVo.getProductType())
                &&!StringUtils.equals("HD_render", payModelConfigVo.getProductType())&&!StringUtils.equals("UHD_render", payModelConfigVo.getProductType())
                &&!StringUtils.equals("video", payModelConfigVo.getProductType())&&!StringUtils.equals("roam720", payModelConfigVo.getProductType())
                ){
            resultMessage.setMessage("产品类型为：productType：{}" + payModelConfigVo.getProductType() + "的产品不能享受包年包月服务");
            return resultMessage;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getProductName())) {
            resultMessage.setMessage("产品名称为空");
            return resultMessage;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getRenderingType())) {
            resultMessage.setMessage("参数renderingType不能为空");
            return resultMessage;
        }
        if(null == payModelConfigVo.getPlanId()){
            resultMessage.setMessage("planId不能为空!");
            return resultMessage;
        }
        /*优先级，如果等null  那么优先级月底*/
        if( payModelConfigVo.getPriority() == null ){
            payModelConfigVo.setPriority(1);
        }
        /*视角*/
        if(payModelConfigVo.getViewPoint() == null){
            resultMessage.setMessage("参数viewPoint不能为空!");
            return resultMessage;
        }
        /*场景 白天黑夜 黄昏？*/
        if(payModelConfigVo.getScene()==null){
            resultMessage.setMessage("参数scene不能为空!");
            return resultMessage;
        }
        Map<String, Object> map = new HashMap<>();
        String packageName = "免费渲染";
        String priceInfo = PayModelConstantType.RENDER_BIZ_NAME;
        String tradeType = TradeType.PAY_MODEL_CONFIG_FRANCHISER;
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        PayOrder payOrder = getOrder(0, PayType.PAY_MODEL_CONFIG_PAY_FRANCHISER,
                payModelConfigVo.getProductName(), StringUtils.isEmpty(payModelConfigVo.getProductDesc())
                        == true ? payModelConfigVo.getProductName() : payModelConfigVo.getProductDesc(),tradeType);
        payOrder.setProductName(ProductUtil.getProductNameByProductType(payModelConfigVo.getProductType()) + packageName);
        SysTask sysTask =null;
        try {
            logger.info("---------- 创建未支付状态的渲染任务 ->start");
            sysTask = this.createNonPaymentTaskNew(payModelConfigVo.getIsTurnOn(), payModelConfigVo.getPlanId(),payModelConfigVo.getPriority(),
                    payModelConfigVo.getViewPoint(),payModelConfigVo.getScene(), Integer.parseInt(payModelConfigVo.getRenderingType()),priceInfo,loginUser);
            if(sysTask == null || sysTask.getId() == null){
                logger.info("创建渲染任务信息：{}" + sysTask.getRemark());
                resultMessage.setMessage(sysTask.getRemark());
                return resultMessage;
            } else {
                if(sysTask.getRenderType() != null){
                    resultMessage.setRenderingType(sysTask.getRenderType()+"");
                }else{
                    logger.error("task id="+sysTask.getId() +" param 'renderType' is null!");
                }
            }
            logger.info("创建未支付状态的渲染任务 ->end");
            int balanceAmount = 0;
            payOrder.setTaskId(sysTask.getId());
            payOrder.setPlatformId(platformId);
            sysSave(payOrder, loginUser);
            payOrder.setProductType(payModelConfigVo.getProductType());
            payOrder.setProductId(payModelConfigVo.getProductId()); // 产品id
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);// 这里是支付成功
            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                logger.info("保存包年包月的渲染订单失败");
                resultMessage.setMessage("保存包年包月的渲染订单失败");
                return resultMessage;
            }
            //------------ 渲染任务状态变更:未支付->待执行 ->start
            this.updateNonPaymentTaskNew(sysTask,loginUser, PayType.PAY_MODEL_CONFIG_PAY_FRANCHISER,payOrder.getPayState(),null);
            //------------ 渲染任务状态变更:未支付->待执行 ->end
            resultMessage.setOrderNo(payOrder.getOrderNo());
            resultMessage.setTaskId(null != sysTask ? sysTask.getId() : null);
            resultMessage.setRenderingType(payModelConfigVo.getRenderingType());
        } catch (Exception e) {
            logger.error("保存包年包月的渲染订单异常，内容是：" , e);
            resultMessage.setMessage("保存包年包月的渲染订单异常");
            return resultMessage;
        }
        map.put("orderNo", payOrder.getOrderNo());
        map.put("taskId", null != sysTask ? sysTask.getId() : null);
        resultMessage.setObj(map);
        resultMessage.setSuccess(Boolean.TRUE);
        resultMessage.setMessage("生成包年包月渲染订单成功");
        return resultMessage;
    }

    /**
     * 包年包月生成渲染订单(运营网站和移动端的经销商用户专用)
     *
     * @param platformId  平台id
     * @param userId 用户id
     * @param payModelConfigVo
     * @return
     */
    @Override
    public ResultMessage addRenderOrderFranchiser(Integer platformId, Integer userId, PayModelConfigVo payModelConfigVo) {
        ResultMessage message = new ResultMessage();
        if (null == platformId) {
            logger.info("平台id为空，platformId:{}" + platformId);
            message.setMessage("平台id为空，platformId:{}" + platformId);
            return message;
        }
        userId = null == userId ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        logger.info("用户号码phone：{}" + sysUser.getMobile() + "，包年包月生成渲染订单start");
        if (null == sysUser) {
            message.setMessage("用户信息为空");
            return message;
        }
        if (null == payModelConfigVo) {
            logger.info("生成渲染订单参数为空，payModelConfigVo:{}" + payModelConfigVo);
            message.setMessage("生成渲染订单参数为空");
            return message;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getProductType())) {
            logger.info("产品类型为空，productType:{}" + payModelConfigVo.getProductType());
            message.setMessage("产品类型为空");
            return message;
        }
        String packageName = "免费渲染";
        Double totalFee = 0d; // 订单总金额，单位为分
        String payType = PayType.PAY_MODEL_CONFIG_PAY_FRANCHISER; // 支付类型
        String productType = payModelConfigVo.getProductType(); // 产品类型
        int balanceAmount = 0;
        try {
            Integer productId = 0;
            String productDesc = "移动端替换产品";
            String tradeType = "";
            // 生成一条订单记录到数据库
            PayOrder payOrder = new PayOrder();
            payOrder.setPlatformId(platformId);
            payOrder.setOrderDate(new Date());
            payOrder.setOrderNo(IdGenerator.generateNo());
            payOrder.setTotalFee(totalFee.intValue());
            payOrder.setPayType(payType);
            payOrder.setProductId(productId);
            payOrder.setProductType(productType);
            payOrder.setProductName(ProductUtil.getProductNameByProductType(productType) + packageName);
            payOrder.setProductDesc(productDesc);
            payOrder.setTradeType(tradeType);
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);
            payOrder.setUserId(userId);
            payOrder.setUserId(sysUser.getId());
            payOrder.setGmtCreate(new Date());
            payOrder.setCreator(sysUser.getModifier());
            payOrder.setModifier(sysUser.getModifier());
            payOrder.setGmtCreate(new Date());
            payOrder.setIsDeleted(0);
            if (payOrder.getSysCode() == null || "".equals(payOrder.getSysCode())) {
                payOrder.setSysCode(
                        Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
            }
            payOrder.setGmtModified(new Date());
            payOrder.setModifier(sysUser.getUserName());
            payOrder.setCurrentIntegral(balanceAmount - totalFee.intValue());
            // FIXME: 插入一条渲染失败款的记录到订单表pay_order，稍后修改到支付退款表pay_refund
            if ("refunds".equals(productType)) {
                payOrder.setProductType("recharge");
                payOrder.setProductName("渲染失败退款");
                payOrder.setProductDesc("渲染失败退款");
                payOrder.setBizType(BizType.REFUND);
                payOrder.setFinanceType(FinanceType.IN);
                payOrder.setPayState(PayState.SUCCESS);
                payOrder.setCurrentIntegral(balanceAmount + totalFee.intValue());
            }
            payOrder.setTaskId(-1);
            int id = payOrderMapper.insertSelective(payOrder);
            if (id == 0) {
                logger.info("生成订单失败");
                message.setMessage("生成订单失败");
                return message;
            }
            message.setObj(payOrder.getOrderNo());
            // 因为金额为0，所以不用修改用户余额
        } catch (Exception e) {
            logger.error("生成订单异常，内容是：",e);
            message.setMessage("生成订单异常");
            return message;
        }
        message.setSuccess(true);
        message.setMessage("生成订单成功");
        return message;
    }

    /**
     * 2b购买包年包月接口（支付宝手机网页支付和微信h5支付）
     *
     * @param payModelConfigVo
     * @param userId 用户id
     * @param platformId 平台id
     * @return
     */
    @Override
    public ResultMessage rechargePayModelByH5Pay(PayModelConfigVo payModelConfigVo, Integer userId, Integer platformId) {
        ResultMessage message = new ResultMessage();
        BasePlatform basePlatform = basePlatformService.get(platformId);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        userId = userId == null ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            message.setMessage("用户信息为空");
            return message;
        }
        if (null == payModelConfigVo) {
            message.setMessage("支付参数错误");
            return message;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getPayType()) ||
                (!payModelConfigVo.getPayType().equals(PayType.WXPAY) && !payModelConfigVo.getPayType().equals(PayType.ALIPAY))) {
            message.setMessage("支付方式有误，payType:{}");
            return message;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getRedirectUrl())) {
            message.setMessage("支付跳转的url为空");
            return message;
        }
        if (null == payModelConfigVo.getPayModelConfigId()) {
            message.setMessage("包年包月套餐id为空");
            return message;
        }
        PayModelConfig payModelConfig = payModelConfigService.get(payModelConfigVo.getPayModelConfigId());
        if (null == payModelConfig) {
            message.setMessage("包年包月套餐信息有误");
            return message;
        }
        boolean flag = false;

        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            flag = true; // 具备度币共享条件
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, platformId);
            if (null == payAccount) {
                logger.info("用户id：userId:{}" + userId + ",平台id：platformId:{}" + platformId + "的度币信息为空");
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        String tradeType = "";
        if (payModelConfigVo.getPayType().equals(PayType.WXPAY)) {
            tradeType = TradeType.H5;
        } else  if (payModelConfigVo.getPayType().equals(PayType.ALIPAY)) {
            tradeType = TradeType.WAP;
        }
        if ((flag && payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE))
                || (!flag && payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE_FRANCHISER))) {
            message.setMessage("当前用户类型暂不能购买此套餐");
            return message;
        }
        if (flag) {
            // 当前如果是包月，那么判断是否有未过期的包年，如果有，那么就不给购买
            List<Map<String, Object>> list = payModelGroupRefService.getUserRefInfoList(sysUser.getFranchiserGroupId(),PayModelConstantType.RENDER_TYPE_FRANCHISER,PayModelConstantType.RANGE_TYPE_FRANCHISER);
            if (payModelConfig.getTimeType() == 0) {
                if (null != list && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).get("timeType").toString().equals("1")) {
                            message.setMessage("当前经销商用户存在未过期的包年，请在包年过期后购买");
                            return message;
                        }
                    }

                }
            }
        } else {
            // 当前如果是包月，那么判断是否有未过期的包年，如果有，那么就不给购买
            List<Map<String, Object>> list = payModelGroupRefService.getUserRefInfoList(userId,PayModelConstantType.RENDER_TYPE,PayModelConstantType.RANGER_TYPE_PERSONAGE);
            if (payModelConfig.getTimeType() == 0) {
                if (null != list && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).get("timeType").toString().equals("1")) {
                            message.setMessage("当前用户存在未过期的包年，请在包年过期后购买");
                            return message;
                        }
                    }

                }
            }
        }
        String productName =  PayProductConstans.RENDER_PAY_PRODUCT_NAME;
        if (payModelConfig.getTimeType() == 0) {
            productName = productName.replace("@replace_str","包月");
        } else if (payModelConfig.getTimeType() == 1) {
            productName = productName.replace("@replace_str","包年");
        }
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        PayOrder payOrder = getOrder(Double.valueOf(payModelConfig.getPackagePrice()).intValue(),payModelConfigVo.getPayType(),
                productName, productName,tradeType);
        try {
            payOrder.setPlatformId(platformId);
            sysSave(payOrder, loginUser);
            payOrder.setProductType(PayProductConstans.RENDER_PRODUCT_TYPE);
            payOrder.setProductId(payModelConfig.getId());
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            if (flag) {
                payOrder.setAtt1(String.valueOf(sysUser.getFranchiserGroupId())); // 业务id（备用字段1）
            } else {
                payOrder.setAtt1(String.valueOf(userId)); // 业务id（备用字段1）
            }
            payOrder.setAtt2(String.valueOf(payModelConfig.getId())); // 付款方式表id（备用字段2）
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                message.setMessage("保存订单失败");
                return message;
            }
            payOrder.setId(id);
            String timeExpire = Utils.getTimeExpire(30);
            String flagStr = "SUCCESS";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", payOrder.getId());
            // 微信h5支付start
            if (payModelConfigVo.getPayType().equals(PayType.WXPAY)) {
                UnifiedOrderService orderService = new UnifiedOrderService();
                String wxNotifyUrl = ResourceConfig.WXPAY_PAY_MODEL_NOTIFY_URL;
                payOrder.setNotifyUrl(wxNotifyUrl);
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(PayProductConstans.RECHARGE_PRODUCT_NAME, payOrder.getOrderNo(),
                        payOrder.getTotalFee(), WxTradeType.MWEB, timeExpire,wxNotifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flagStr)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flagStr)) {
                    String redirect_url = payModelConfigVo.getRedirectUrl() + payOrder.getOrderNo();
                    map.put("mwebUrl", unifiedOrderResData.getMweb_url()+"&redirect_url=" + java.net.URLEncoder.encode(redirect_url,   "utf-8"));
                    message.setObj(map);
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                }
            }
            // 微信h5支付start
            // 支付宝手机网页支付start
            if (payModelConfigVo.getPayType().equals(PayType.ALIPAY)) {
                String alNotifyUrl = ResourceConfig.ALIPAY_PAY_MODEL_NOTIFY_URL;
                payOrder.setNotifyUrl(alNotifyUrl);
                String returnUrl = payModelConfigVo.getRedirectUrl() + payOrder.getOrderNo();
                logger.info("购买度币，支付宝手机网页支付start，支付宝同步的url：returnUrl：{}" + returnUrl);
                String form = ScanPayUtil.addScanpayOrder(payOrder, alNotifyUrl, returnUrl);
                if (StringUtils.isEmpty(form)) {
                    message.setMessage("支付宝手机网页下单失败");
                    return message;
                } else {
                    map.put("form", form);
                    message.setObj(map);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                }
            }
            // 支付宝手机网页支付end
        } catch (Exception e) {
            logger.error("下单异常信息：", e);
            message.setMessage("下单异常");
            return message;
        }
        message.setMessage("success");
        message.setSuccess(Boolean.TRUE);
        return message;
    }

    /**
     * 2b购买包年包月接口（支付宝和微信APP支付）
     *
     * @param payModelConfigVo
     * @param userId 用户id
     * @param platformId 平台id
     * @return
     */
    @Override
    public ResultMessage rechargePayModelByAppPay(PayModelConfigVo payModelConfigVo, Integer userId, Integer platformId) {
        ResultMessage message = new ResultMessage();
        BasePlatform basePlatform = basePlatformService.get(platformId);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        userId = userId == null ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            message.setMessage("用户信息为空");
            return message;
        }
        if (null == payModelConfigVo) {
            message.setMessage("支付参数错误");
            return message;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getPayType()) ||
                (!payModelConfigVo.getPayType().equals(PayType.WXPAY) && !payModelConfigVo.getPayType().equals(PayType.ALIPAY))) {
            message.setMessage("支付方式有误，payType:{}");
            return message;
        }
        if (null == payModelConfigVo.getPayModelConfigId()) {
            message.setMessage("包年包月套餐id为空");
            return message;
        }
        PayModelConfig payModelConfig = payModelConfigService.get(payModelConfigVo.getPayModelConfigId());
        if (null == payModelConfig) {
            message.setMessage("包年包月套餐信息有误");
            return message;
        }
        boolean flag = false;
        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            flag = true; // 具备度币共享条件
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, platformId);
            if (null == payAccount) {
                logger.info("用户id：userId:{}" + userId + ",平台id：platformId:{}" + platformId + "的度币信息为空");
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        String tradeType = TradeType.APP;
        if ((flag && payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE))
                || (!flag && payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE_FRANCHISER))) {
            message.setMessage("当前用户类型暂不能购买此套餐");
            return message;
        }
        if (flag) {
            // 当前如果是包月，那么判断是否有未过期的包年，如果有，那么就不给购买
            List<Map<String, Object>> list = payModelGroupRefService.getUserRefInfoList(sysUser.getFranchiserGroupId(),PayModelConstantType.RENDER_TYPE_FRANCHISER,PayModelConstantType.RANGE_TYPE_FRANCHISER);
            if (payModelConfig.getTimeType() == 0) {
                if (null != list && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).get("timeType").toString().equals("1")) {
                            message.setMessage("当前经销商用户存在未过期的包年，请在包年过期后购买");
                            return message;
                        }
                    }

                }
            }
        } else {
            // 当前如果是包月，那么判断是否有未过期的包年，如果有，那么就不给购买
            List<Map<String, Object>> list = payModelGroupRefService.getUserRefInfoList(userId,PayModelConstantType.RENDER_TYPE,PayModelConstantType.RANGER_TYPE_PERSONAGE);
            if (payModelConfig.getTimeType() == 0) {
                if (null != list && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).get("timeType").toString().equals("1")) {
                            message.setMessage("当前用户存在未过期的包年，请在包年过期后购买");
                            return message;
                        }
                    }

                }
            }
        }
        String productName =  PayProductConstans.RENDER_PAY_PRODUCT_NAME;
        if (payModelConfig.getTimeType() == 0) {
            productName = productName.replace("@replace_str","包月");
        } else if (payModelConfig.getTimeType() == 1) {
            productName = productName.replace("@replace_str","包年");
        }
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        PayOrder payOrder = getOrder(Double.valueOf(payModelConfig.getPackagePrice()).intValue(),payModelConfigVo.getPayType(),
                productName, productName,tradeType);
        try {
            payOrder.setPlatformId(platformId);
            sysSave(payOrder, loginUser);
            payOrder.setProductType(PayProductConstans.RENDER_PRODUCT_TYPE);
            payOrder.setProductId(payModelConfig.getId());
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            if (flag) {
                payOrder.setAtt1(String.valueOf(sysUser.getFranchiserGroupId())); // 业务id（备用字段1）
            } else {
                payOrder.setAtt1(String.valueOf(userId)); // 业务id（备用字段1）
            }
            payOrder.setAtt2(String.valueOf(payModelConfig.getId())); // 付款方式表id（备用字段2）
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                message.setMessage("保存订单失败");
                return message;
            }
            payOrder.setId(id);
            String timeExpire = Utils.getTimeExpire(30);
            String flagStr = "SUCCESS";
            //微信APP start
            if (payModelConfigVo.getPayType().equals(PayType.WXPAY)) {
                UnifiedOrderService orderService = new UnifiedOrderService();
                String wxNotifyUrl = ResourceConfig.WXPAY_PAY_MODEL_NOTIFY_URL;
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(ResourceConfig.WXPAY_APPPAY_APPID,
                        ResourceConfig.WXPAY_APPPAY_MUCHID, ResourceConfig.WXPAY_APPPAY_KEY, productName,
                        payOrder.getOrderNo(), payOrder.getTotalFee(),
                        WxTradeType.APP, timeExpire, wxNotifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flagStr)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flagStr)) {
                    Map<String, Object> packageParams = new HashMap<String, Object>();
                    packageParams.put("appid", ResourceConfig.WXPAY_APPPAY_APPID);
                    packageParams.put("partnerid", ResourceConfig.WXPAY_APPPAY_MUCHID);
                    packageParams.put("prepayid", unifiedOrderResData.getPrepay_id());
                    packageParams.put("package", "Sign=WXPay");
                    packageParams.put("noncestr", System.currentTimeMillis() + "");
                    packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
                    String packageSign = Signature.getSign(packageParams, ResourceConfig.WXPAY_APPPAY_KEY);
                    packageParams.put("sign", packageSign);
                    message.setObj(packageParams);
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setNotifyUrl(wxNotifyUrl);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                    logger.info(CLASS_LOG_PREFIX+"2b移动端成功生成购买包年包月订单");
                } else {
                    message.setMessage("支付失败");
                    return message;
                }
            }
            //微信APP end
            //支付宝APP start
            if (payModelConfigVo.getPayType().equals(PayType.ALIPAY)) {
                Map<String, Object> formMap = new HashMap<String, Object>();
                String notifyUrl =  ResourceConfig.ALIPAY_PAY_MODEL_NOTIFY_URL;
                String form = ScanPayUtil.addScanpayAppOrder(payOrder, notifyUrl);
                payOrder.setNotifyUrl(notifyUrl);
                if (StringUtils.isEmpty(form)) {
                    message.setMessage("支付失败");
                    return message;
                } else {
                    formMap.put("form", form);
                    message.setObj(formMap);
                    message.setOrderNo(String.valueOf(payOrder.getId()));
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                }
            }
            //支付宝APP end
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("下单异常信息：",e);
            message.setMessage("下单异常");
            return message;
        }
        message.setMessage("success");
        message.setSuccess(Boolean.TRUE);
        return message;
    }

    /**
     * 2b度币充值（微信h5支付，支付宝手机网页支付）
     *
     * @param userId 用户id
     * @param payOrderModel
     * @return
     */
    @Override
    public ResultMessage rechargeIntegralByH5Pay2b(Integer userId, PayOrderModel payOrderModel, String platformCode) {
        ResultMessage message = new ResultMessage();
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
        userId = userId == null ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空");
            message.setMessage("用户信息为空");
            return message;
        }
        if (null == payOrderModel) {
            logger.info("支付参数错误");
            message.setMessage("支付参数错误");
            return message;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType()) ||
                (!payOrderModel.getPayType().equals(PayType.WXPAY) && !payOrderModel.getPayType().equals(PayType.ALIPAY))) {
            message.setMessage("支付方式有误，payType:{}");
            return message;
        }
        if (StringUtils.isEmpty(payOrderModel.getRedirectUrl())) {
            message.setMessage("支付跳转的url为空");
            return message;
        }
        String tradeType = "";
        if (payOrderModel.getPayType().equals(PayType.WXPAY)) {
            tradeType = TradeType.H5;
        } else  if (payOrderModel.getPayType().equals(PayType.ALIPAY)) {
            tradeType = TradeType.WAP;
        }
        if (null == payOrderModel.getProductId()) {
            message.setMessage("产品id为空：productId:{}");
            return message;
        }
        SysDictionary sysDictionary = sysDictionaryService.get(payOrderModel.getProductId());
        if (null == sysDictionary) {
            message.setMessage("数据字典中找不到度币信息");
            return message;
        }
        int totalFee = sysDictionary.getValue().intValue(); // 充值的金额
        int adjustFee = Utils.getIntValue(sysDictionary.getAtt1().trim()); // 赠送的金额
        logger.info("2b的度币充值：产品id：productId{}" + payOrderModel.getProductId() + "充值的金额：totalFee{}" + totalFee + "赠送的金额：adjustFee{}" + adjustFee + "用户的id：userId{}" + userId );
        PayOrder payOrder = getOrder(totalFee, payOrderModel.getPayType(), PayProductConstans.RECHARGE_PRODUCT_NAME, PayProductConstans.RECHARGE_PRODUCT_DESC, tradeType);
        payOrder.setPlatformId(basePlatform.getId());
        payOrder.setAdjustFee(adjustFee);
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        int balanceAmount = 0;
        if (null == companyFranchiserGroup) {
            // 不具备经销商权限
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        } else {
            // 具备经销商权限
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        }
        try {
            payOrder.setProductType(PayProductConstans.PAY_PRODUCT_TYPE);
            sysSave(payOrder, loginUser);
            payOrder.setUserId(userId);
            payOrder.setProductId(payOrderModel.getProductId());
            payOrder.setBizType(BizType.RECHARGE);
            payOrder.setFinanceType(FinanceType.IN);
            payOrder.setCurrentIntegral(totalFee + adjustFee + balanceAmount);
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                logger.info("保存在线充值单失败");
                message.setMessage("保存在线充值单失败");
                return message;
            }
            payOrder.setId(id);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", payOrder.getId());
            if (payOrderModel.getPayType().equals(PayType.WXPAY)) {
                logger.info("购买度币，微信h5支付start");
                String flag = "SUCCESS";
                UnifiedOrderService orderService = new UnifiedOrderService();
                String timeExpire = Utils.getTimeExpire(30);
                String wxNotifyUrl = ResourceConfig.WX_PAY_NOTIFY_URL;
                payOrder.setNotifyUrl(wxNotifyUrl);
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(PayProductConstans.RECHARGE_PRODUCT_NAME, payOrder.getOrderNo(),
                        totalFee, WxTradeType.MWEB, timeExpire,wxNotifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {

                    String redirect_url = payOrderModel.getRedirectUrl() + payOrder.getOrderNo();
                    logger.info("购买度币微信h5支付预下单成功，微信回调地址是：wxNotifyUrl：{}" + wxNotifyUrl
                            + ",支付跳转链接:mwebUrl:{}" + unifiedOrderResData.getMweb_url() + ",微信同步的url是：" + redirect_url);
                    map.put("mwebUrl", unifiedOrderResData.getMweb_url()+"&redirect_url=" + java.net.URLEncoder.encode(redirect_url,   "utf-8"));
                    message.setObj(map);
                    message.setMessage("success");
                    message.setSuccess(Boolean.TRUE);
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(2);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount(Double.valueOf(totalFee));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                    logger.info("购买度币，微信h5支付end");
                    return message;
                }  else {
                    logger.info("微信h5下单失败，订单号为：orderNo:{}" + payOrder.getOrderNo());
                    message.setMessage("微信h5下单失败，订单号为：orderNo:{}" + payOrder.getOrderNo());
                    return message;
                }
            }
            if (payOrderModel.getPayType().equals(PayType.ALIPAY)) {
                String alNotifyUrl = ResourceConfig.ALIPAY_NOTIFY_URL;
                payOrder.setNotifyUrl(alNotifyUrl);
                String returnUrl = payOrderModel.getRedirectUrl() + payOrder.getOrderNo();
                String form = ScanPayUtil.addScanpayOrder(payOrder, alNotifyUrl, returnUrl);
                if (StringUtils.isEmpty(form)) {
                    message.setMessage("支付宝手机网页下单失败，订单号为：orderNo:{}" + payOrder.getOrderNo());
                    return message;
                } else {
                    map.put("form", form);
                    message.setObj(map);
                    message.setMessage("success");
                    message.setSuccess(Boolean.TRUE);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(3);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount(Double.valueOf(totalFee));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                    return message;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("2b支付宝手机网页支付或者微信h5支付下单异常,内容是：",e);
            message.setMessage("下单异常");
            return message;
        }
        return message;
    }

    /**
     * 2b度币充值（微信APP支付，支付宝APP支付）
     *
     * @param userId 用户id
     * @param payOrderModel
     * @return
     */
    @Override
    public ResultMessage rechargeIntegralByAppPay2b(Integer userId, PayOrderModel payOrderModel, String platformCode) {
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            message.setMessage("平台编码：platformCode{}为空");
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        userId = userId == null ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            message.setMessage("用户信息为空");
            return message;
        }
        if (null == payOrderModel) {
            message.setMessage("支付参数错误");
            return message;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType()) ||
                (!payOrderModel.getPayType().equals(PayType.WXPAY) && !payOrderModel.getPayType().equals(PayType.ALIPAY))) {
            message.setMessage("支付方式有误，payType:{}");
            return message;
        }
        String tradeType = TradeType.APP;
        if (null == payOrderModel.getProductId()) {
            message.setMessage("产品id为空：productId:{}");
            return message;
        }
        SysDictionary sysDictionary = sysDictionaryService.get(payOrderModel.getProductId());
        if (null == sysDictionary) {
            message.setMessage("数据字典中找不到度币信息");
            return message;
        }
        int totalFee = sysDictionary.getValue().intValue(); // 充值的金额
        int adjustFee = Utils.getIntValue(sysDictionary.getAtt1().trim()); // 赠送的金额
        PayOrder payOrder = getOrder(totalFee, payOrderModel.getPayType(), PayProductConstans.RECHARGE_PRODUCT_NAME, PayProductConstans.RECHARGE_PRODUCT_DESC, tradeType);
        payOrder.setPlatformId(basePlatform.getId());
        payOrder.setAdjustFee(adjustFee);
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        int balanceAmount = 0;
        if (null == companyFranchiserGroup) {
            // 不具备经销商权限
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        } else {
            // 具备经销商权限
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        }
        try {
            payOrder.setProductType(PayProductConstans.PAY_PRODUCT_TYPE);
            sysSave(payOrder, loginUser);
            payOrder.setUserId(userId);
            payOrder.setProductId(payOrderModel.getProductId());
            payOrder.setBizType(BizType.RECHARGE);
            payOrder.setFinanceType(FinanceType.IN);
            payOrder.setCurrentIntegral(totalFee + adjustFee + balanceAmount);
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                logger.info("保存在线充值单失败");
                message.setMessage("保存在线充值单失败");
                return message;
            }
            payOrder.setId(id);
            String timeExpire = Utils.getTimeExpire(30);
            String flag = "SUCCESS";
            if (payOrderModel.getPayType().equals(PayType.WXPAY)) {
                UnifiedOrderService orderService = new UnifiedOrderService();
                String wxNotifyUrl = ResourceConfig.WX_PAY_NOTIFY_URL;
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(ResourceConfig.WXPAY_APPPAY_APPID,
                        ResourceConfig.WXPAY_APPPAY_MUCHID, ResourceConfig.WXPAY_APPPAY_KEY, PayProductConstans.RECHARGE_PRODUCT_NAME,
                        payOrder.getOrderNo(), payOrder.getTotalFee(),
                        WxTradeType.APP, timeExpire, wxNotifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    Map<String, Object> packageParams = new HashMap<String, Object>();
                    packageParams.put("appid", ResourceConfig.WXPAY_APPPAY_APPID);
                    packageParams.put("partnerid", ResourceConfig.WXPAY_APPPAY_MUCHID);
                    packageParams.put("prepayid", unifiedOrderResData.getPrepay_id());
                    packageParams.put("package", "Sign=WXPay");
                    packageParams.put("noncestr", System.currentTimeMillis() + "");
                    packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
                    String packageSign = Signature.getSign(packageParams, ResourceConfig.WXPAY_APPPAY_KEY);
                    packageParams.put("sign", packageSign);
                    message.setObj(packageParams);
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setNotifyUrl(wxNotifyUrl);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(2);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount(Double.valueOf(totalFee));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                } else {
                    message.setMessage("支付失败");
                    return message;
                }
            }
            if (payOrderModel.getPayType().equals(PayType.ALIPAY)) {
                Map<String, Object> formMap = new HashMap<String, Object>();
                String notifyUrl =  ResourceConfig.ALIPAY_NOTIFY_URL;
                String form = ScanPayUtil.addScanpayAppOrder(payOrder, notifyUrl);
                payOrder.setNotifyUrl(notifyUrl);
                if (StringUtils.isEmpty(form)) {
                    message.setMessage("支付失败");
                    return message;
                } else {
                    formMap.put("form", form);
                    message.setObj(formMap);
                    message.setOrderNo(String.valueOf(payOrder.getId()));
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(3);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount(Double.valueOf(totalFee));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                }
            }
        } catch (Exception e) {
            logger.error("2b度币充值APP支付下单异常,内容是：",e);
            message.setMessage("下单异常");
            return message;
        }
        message.setMessage("success");
        message.setSuccess(Boolean.TRUE);
        return message;
    }

    /**
     *
     * 判断pc端渲染参数是否合法
     *
     * @param payModelConfigVo
     * @return
     */
    @Override
    public ResultMessage checkRenderParameter(PayModelConfigVo payModelConfigVo) {
        ResultMessage resultMessage = new ResultMessage();
        payModelConfigVo =  null == payModelConfigVo ? new PayModelConfigVo() : payModelConfigVo;
        if (null == payModelConfigVo.getProductId()) {
            resultMessage.setMessage("产品id为空");
            return resultMessage;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getProductType())) {
            resultMessage.setMessage("产品类型为空");
            return resultMessage;
        }
        if(!StringUtils.equals("common_render", payModelConfigVo.getProductType())&&!StringUtils.equals("panorama_render", payModelConfigVo.getProductType())
                &&!StringUtils.equals("HD_render", payModelConfigVo.getProductType())&&!StringUtils.equals("UHD_render", payModelConfigVo.getProductType())
                &&!StringUtils.equals("video", payModelConfigVo.getProductType())&&!StringUtils.equals("roam720", payModelConfigVo.getProductType())
                ){
            resultMessage.setMessage("产品类型为：productType：{}" + payModelConfigVo.getProductType() + "的产品不能享受包年包月服务");
            return resultMessage;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getProductName())) {
            resultMessage.setMessage("产品名称为空");
            return resultMessage;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getRenderingType())) {
            resultMessage.setMessage("参数renderingType为空");
            return resultMessage;
        }
        if(null == payModelConfigVo.getPlanId()){
            resultMessage.setMessage("planId为空!");
            return resultMessage;
        }
        /*优先级，如果等null  那么优先级月底*/
        if( payModelConfigVo.getPriority() == null ){
            payModelConfigVo.setPriority(1);
        }
        /*视角*/
        if(payModelConfigVo.getViewPoint() == null){
            resultMessage.setMessage("参数viewPoint为空!");
            return resultMessage;
        }
        /*场景 白天黑夜 黄昏？*/
        if(payModelConfigVo.getScene()==null){
            resultMessage.setMessage("参数scene为空!");
            return resultMessage;
        }
        resultMessage.setMessage("success");
        resultMessage.setSuccess(Boolean.TRUE);
        return resultMessage;
    }

    /**
     * pc端品牌商家免费渲染
     *
     * @param basePlatform
     * @param sysUser
     * @param payModelConfigVo
     * @return
     */
    @Override
    public ResultMessage addRenderPayOrderFree(BasePlatform basePlatform, SysUser sysUser, PayModelConfigVo payModelConfigVo) {
        ResultMessage resultMessage = new ResultMessage();
        //校验非空start
        resultMessage = this.checkRenderParameter(payModelConfigVo);
        if (resultMessage.isSuccess() == false) {
            return resultMessage;
        }
        //校验非空end
        Map<String, Object> map = new HashMap<>();
        String priceInfo = PayModelConstantType.RENDER_BIZ_NAME;
        String tradeType = TradeType.PAY_MODEL_COMPANY;
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        PayOrder payOrder = getOrder(0, PayType.PAY_MODEL_COMPANY_PAY,
                payModelConfigVo.getProductName(), StringUtils.isEmpty(payModelConfigVo.getProductDesc())
                        == true ? payModelConfigVo.getProductName() : payModelConfigVo.getProductDesc(),tradeType);
        payOrder.setProductName(ProductUtil.getProductNameByProductType(payModelConfigVo.getProductType()) + "(包月)");
        SysTask sysTask =null;
        try {
            logger.info("---------- 创建未支付状态的渲染任务 ->start");
            sysTask = this.createNonPaymentTaskNew(payModelConfigVo.getIsTurnOn(), payModelConfigVo.getPlanId(),payModelConfigVo.getPriority(),
                    payModelConfigVo.getViewPoint(),payModelConfigVo.getScene(), Integer.parseInt(payModelConfigVo.getRenderingType()),priceInfo,loginUser);
            if(sysTask == null || sysTask.getId() == null){
                resultMessage.setMessage(sysTask.getRemark());
                return resultMessage;
            } else {
                if(sysTask.getRenderType() != null){
                    resultMessage.setRenderingType(sysTask.getRenderType()+"");
                }else{
                    logger.error("task id="+sysTask.getId() +" param 'renderType' is null!");
                }
            }
            logger.info("创建未支付状态的渲染任务 ->end");
            int balanceAmount = 0;
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(sysUser.getId());
            if (null != companyFranchiserGroup) {
                balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
            } else {
                PayAccount payAccount = payAccountService.getPayAccountInfo(sysUser.getId(), basePlatform.getId());
                if (null == payAccount) {
                    resultMessage.setMessage("用户度币信息为空，生成订单失败");
                    return resultMessage;
                }
                balanceAmount = payAccount.getBalanceAmount().intValue();
            }
            payOrder.setTaskId(sysTask.getId());
            payOrder.setPlatformId(basePlatform.getId());
            sysSave(payOrder, loginUser);
            payOrder.setProductType(payModelConfigVo.getProductType());
            payOrder.setProductId(payModelConfigVo.getProductId()); // 产品id
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);// 这里是支付成功
            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                resultMessage.setMessage("保存渲染订单失败");
                return resultMessage;
            }
            //------------ 渲染任务状态变更:未支付->待执行 ->start
            this.updateNonPaymentTaskNew(sysTask,loginUser, PayType.PAY_MODEL_COMPANY_PAY,payOrder.getPayState(),null);
            //------------ 渲染任务状态变更:未支付->待执行 ->end
            resultMessage.setOrderNo(payOrder.getOrderNo());
            resultMessage.setTaskId(null != sysTask ? sysTask.getId() : null);
            resultMessage.setRenderingType(payModelConfigVo.getRenderingType());
            map.put("orderNo", payOrder.getOrderNo());
            map.put("taskId", null != sysTask ? sysTask.getId() : null);
            map.put("isShow", false);
            map.put("showMsg", "");
            resultMessage.setObj(map);
        } catch (Exception e) {
            logger.error("保存渲染订单异常:",e);
            resultMessage.setMessage("保存渲染订单异常");
            return resultMessage;
        }
        resultMessage.setMessage("success");
        resultMessage.setSuccess(Boolean.TRUE);
        return resultMessage;
    }

    /**
     * 移动端品牌商家免费渲染
     *
     * @param basePlatform
     * @param sysUser
     * @param payModelConfigVo
     * @return
     */
    @Override
    public ResultMessage addRenderOrderFree(BasePlatform basePlatform, SysUser sysUser, PayModelConfigVo payModelConfigVo) {
        ResultMessage message = new ResultMessage();
        payModelConfigVo = null == payModelConfigVo ? new PayModelConfigVo() : payModelConfigVo;
        if (StringUtils.isEmpty(payModelConfigVo.getProductType())) {
            message.setMessage("产品类型为空");
            return message;
        }
        String tradeType = TradeType.PAY_MODEL_COMPANY;
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        Double totalFee = 0d; // 订单总金额，单位为分
        PayOrder payOrder = getOrder(totalFee.intValue(), PayType.PAY_MODEL_COMPANY_PAY,
                payModelConfigVo.getProductName(), StringUtils.isEmpty(payModelConfigVo.getProductDesc())
                        == true ? payModelConfigVo.getProductName() : payModelConfigVo.getProductDesc(),tradeType);
        payOrder.setProductName(ProductUtil.getProductNameByProductType(payModelConfigVo.getProductType()) + "(包月)");
        try {
            int balanceAmount = 0;
            Integer productId = 0;
            String productDesc = "移动端替换产品";
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(sysUser.getId());
            if (null != companyFranchiserGroup) {
                balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
            } else {
                PayAccount payAccount = payAccountService.getPayAccountInfo(sysUser.getId(), basePlatform.getId());
                if (null == payAccount) {
                    message.setMessage("用户度币信息为空，生成订单失败");
                    return message;
                }
                balanceAmount = payAccount.getBalanceAmount().intValue();
            }
            payOrder.setProductId(productId);
            payOrder.setProductDesc(productDesc);
            payOrder.setPlatformId(basePlatform.getId());
            sysSave(payOrder, loginUser);
            payOrder.setProductType(payModelConfigVo.getProductType());
            payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);// 这里是支付成功
            payOrder.setTaskId(-1);
            payOrder.setCurrentIntegral(balanceAmount - totalFee.intValue());
            // FIXME: 插入一条渲染失败款的记录到订单表pay_order，稍后修改到支付退款表pay_refund
            if ("refunds".equals(payModelConfigVo.getProductType())) {
                payOrder.setProductType("recharge");
                payOrder.setProductName("渲染失败退款");
                payOrder.setProductDesc("渲染失败退款");
                payOrder.setBizType(BizType.REFUND);
                payOrder.setFinanceType(FinanceType.IN);
                payOrder.setPayState(PayState.SUCCESS);
                payOrder.setCurrentIntegral(balanceAmount + totalFee.intValue());
            }
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                logger.info("生成订单失败");
                message.setMessage("生成订单失败");
                return message;
            }
            message.setObj(payOrder.getOrderNo());
        } catch (Exception e) {
            logger.error("生成订单异常:" , e);
            message.setMessage("生成订单异常");
            return message;
        }
        message.setSuccess(true);
        message.setMessage("生成订单成功");
        return message;
    }

    /**
     * 厂商免费购买户型生成订单
     *
     * @param basePlatform
     * @param sysUser
     * @param expandType
     * @return
     */
    @Override
    public ResultMessage addRenderOrderFree(BasePlatform basePlatform, SysUser sysUser, Integer expandType) {
        ResultMessage message = new ResultMessage();
        if (null == expandType) {
            message.setMessage("参数expandType不能为空");
            return message;
        }
        SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue(SysDictionaryConstant.EXPAND_HOUSE_TYPE, expandType);
        Double expandIntegral;
        Integer expandNumber;
        String productName = "";
        String productType = "";
        if (sysDictionary != null && sysDictionary.getId() != null) {
            productName = sysDictionary.getName();
            productType = sysDictionary.getValuekey();
            expandIntegral = Double.parseDouble(sysDictionary.getAtt1());
            expandNumber = Integer.parseInt(sysDictionary.getAtt2());
        } else {
            logger.info(CLASS_LOG_PREFIX + "找不到对应数据字典购买户型类型！value = " + expandType + ";type=" + SysDictionaryConstant.EXPAND_HOUSE_TYPE);
            message.setMessage(CLASS_LOG_PREFIX + "找不到对应数据字典购买户型类型！value = " + expandType + ";type=" + SysDictionaryConstant.EXPAND_HOUSE_TYPE);
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            return message;
        }
        // 户型的单价小于 0 和支付的度币小于0
        if (expandNumber <= 0 || expandIntegral <= 0) {
            message.setMessage(CLASS_LOG_PREFIX + "户型消耗度币值异常！value = " + expandType + ";type=" + SysDictionaryConstant.EXPAND_HOUSE_TYPE);
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            return message;
        }
        double totalFee = 0d;
        PayOrder payOrder = getOrder((int)totalFee, PayType.PAY_MODEL_COMPANY_PAY, sysDictionary.getId(),
                productType, productName, PayProductConstans.PAY_EXPEND_HOUSE_DESC, TradeType.PAY_MODEL_COMPANY);
        payOrder.setProductName(productName);
        try {
            int balanceAmount = 0;
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(sysUser.getId());
            if (null != companyFranchiserGroup) {
                balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
            } else {
                PayAccount payAccount = payAccountService.getPayAccountInfo(sysUser.getId(), basePlatform.getId());
                if (null == payAccount) {
                    message.setMessage("用户度币信息为空，生成订单失败");
                    return message;
                }
                balanceAmount = payAccount.getBalanceAmount().intValue();
            }
            if (balanceAmount < totalFee) {
                logger.info("账户度币不足，请充值. 用户剩余度币 ==> " + balanceAmount, "，需要支付的度币 ==> " + totalFee);
                message.setStatus(MessageStateType.PAY_INTEGRAL_INSUFFICIENT_STATE);
                message.setMessage("账户度币不足，请充值.");
                return message;
            }
            LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getUserName(), sysUser.getMobile());
            sysSave(payOrder, loginUser);
            payOrder.setUserId(sysUser.getId());
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);
            payOrder.setCurrentIntegral((int) (balanceAmount - totalFee));
            payOrder.setOrderDate(new Date());
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                message.setStatus(MessageStateType.PAY_FAILED_STATE);
                message.setMessage("保存支付单失败.");
                return message;
            }
            SysUser updateUser = new SysUser();
            updateUser.setId(sysUser.getId());
            updateUser.setGmtModified(new Date());
            updateUser.setModifier(sysUser.getId() + "");
            Integer usableHouseNumber = sysUser.getUsableHouseNumber();
            logger.info("已有可用户型数 ==>" + usableHouseNumber);
            usableHouseNumber = (usableHouseNumber != null) ? (usableHouseNumber + expandNumber) : expandNumber;
            logger.info("购买后可用户型数 ==>" + usableHouseNumber);

            updateUser.setUsableHouseNumber(usableHouseNumber);
            int update = sysUserService.update(updateUser);
            if (update <= 0) {
                message.setSuccess(false);
                message.setMessage("更新用户户型数失败.");
                return message;
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            message.setMessage("度币支付发生异常.");
            return message;
        }
        message.setSuccess(true);
        message.setMessage("购买户型成功");
        return message;
    }

    @Override
    public ResultMessage addCompanyPayRenderTask(SysUser sysUser, Integer renderType, Integer planId, Integer houseId, BasePlatform basePlatform) {
        ResultMessage message = new ResultMessage();
        SysDictionary sysDictionary = sysDictionaryService.getSysDictionaryByValue(SysDictionaryConstant.PLATFROM_RENDER_TYPE,renderType);
        logger.info(CLASS_LOG_PREFIX + "度币支付渲染类型数据字典查询:" + gson.toJson(sysDictionary));
        String productName = "";
        String productType = "";
        double totalFee = 0;
        if (sysDictionary != null && sysDictionary.getId() != null) {
            productName = sysDictionary.getName();
            productType = sysDictionary.getValuekey();
        }else{
            logger.info(CLASS_LOG_PREFIX + "找不到对应数据字典渲染类型！value = "+renderType+";type="+ SysDictionaryConstant.PLATFROM_RENDER_TYPE);
            message.setMessage(CLASS_LOG_PREFIX + "找不到对应数据字典渲染类型！value = "+renderType+";type="+ SysDictionaryConstant.PLATFROM_RENDER_TYPE);
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            return message;
        }
        int balanceAmount = 0;
        if (UserConstants.USER_TYPE_TOURIST != sysUser.getUserType().intValue()) {
            //判断户型
            boolean houseIsAbleUse = userFinanceService.checkUserHouseIsAbleUse(sysUser.getId(), houseId);
            if (!houseIsAbleUse) {
                message.setStatus(MessageStateType.HOUSE_INSUFFICIENT_STATE);
                message.setMessage("账户户型不足，请购买户型.");
                return message;
            }
            CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(sysUser.getId());
            if (null != companyFranchiserGroup) {
                balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
            } else {
                PayAccount payAccount = payAccountService.getPayAccountInfo(sysUser.getId(), basePlatform.getId());
                if (null == payAccount) {
                    message.setMessage("用户度币信息为空，生成订单失败");
                    return message;
                }
                balanceAmount  = payAccount.getBalanceAmount().intValue(); // 账户余额
            }
        }
        PayOrder payOrder = getOrder((int)totalFee, PayType.PREDEPOSIT, sysDictionary.getId(), productType, productName, PayProductConstans.PAY_RENDER_PRODUCT_DESC, TradeType.PREDEPOSIT);
        payOrder.setPlanId(planId);
        payOrder.setHouseId(houseId);
        payOrder.setProductName(productName); // 只可以使用此产品名称
        LoginUser loginUser = new LoginUser(sysUser.getId(),sysUser.getUserType(),sysUser.getUserName(),sysUser.getMobile());
        try {
            sysSave(payOrder, loginUser);
            payOrder.setUserId(sysUser.getId());
            payOrder.setPlatformId(basePlatform.getId()); // 平台id
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setPayState(PayState.SUCCESS);
            payOrder.setPayType(PayType.PAY_MODEL_COMPANY_PAY);
            payOrder.setCurrentIntegral((int)(balanceAmount - totalFee));
            payOrder.setOrderDate(new Date());
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                message.setMessage("保存支付单失败.");
                return message;
            }
        } catch (Exception e) {
            logger.error("运营网站企业购买包年渲染异常，内容为：" ,e);
            message.setSuccess(false);
            message.setStatus(MessageStateType.PAY_OTHER_STATE);
            message.setMessage("度币支付发生异常.");
        }
        message.setSuccess(true);
        message.setRenderingType(String.valueOf(renderType));
        message.setOrderNo(payOrder.getOrderNo());
        message.setMessage("度币支付成功");
        return message;
    }

    @Override
    public List<PayModelConfig> getPayModelConfig2C() {
        return payOrderMapper.selectPayModelConfig2C();
    }

    @Override
    public String add2CPackagePayOrder(Integer id, PayModelConfigVo payModelConfigVo, BasePlatform basePlatform) {
        logger.info("包年包月渲染插入订单begin");
        SysUser sysUser = sysUserService.get(id);
        if(payModelConfigVo == null || payModelConfigVo.getProductType() == null){
            throw new BizException(ExceptionCodes.CODE_10010016,"产品类型为空");
        }
        //检验用户的户型套数
        //int houseNum = payOrderMapper.countHouseByPayOrder(sysUser.getId());
//        boolean b = userFinanceService.checkUserHouseIsAbleUse(sysUser.getId(), payModelConfigVo.getHouseId());
//        if(!b){
//            throw  new BizException(ExceptionCodes.CODE_10010018,"户型不足,请购买户型!");
//        }
        //创建订单
        return  this.creatPackagePayOrder(sysUser,payModelConfigVo,basePlatform);
    }

    private String creatPackagePayOrder(SysUser sysUser, PayModelConfigVo payModelConfigVo, BasePlatform basePlatform) {
        logger.info("插入订单=>{}houseId,planId"+payModelConfigVo.getHouseId()+","+payModelConfigVo.getPlanId());
        Double totalFee = 0d;
        PayOrder payOrder = getOrder(totalFee.intValue(),PayType.PAY_MODEL_CONFIG_PAY,
                                     payModelConfigVo.getProductName(),
                                     StringUtils.isEmpty(payModelConfigVo.getProductDesc())== true ?
                                             payModelConfigVo.getProductName() : payModelConfigVo.getProductDesc(),
                                     TradeType.PAY_MODEL_CONFIG);

        payOrder.setProductName(ProductUtil.getProductNameByProductType(payModelConfigVo.getProductType()));

        PayAccount payAccount =payAccountService.getPayAccountInfo(sysUser.getId(),null);
        if(null == payAccount){
            payOrder.setCurrentIntegral(0);
            payOrder.setRemark("用户账户不存在");
        }else{
            payOrder.setCurrentIntegral(payAccount.getBalanceAmount().intValue()); // 当前度币
        }
        payOrder.setProductDesc("C端替换产品");
        payOrder.setProductId(0);
        payOrder.setProductType(payModelConfigVo.getProductType());
        payOrder.setBizType(BizType.BUY); // 业务类型 ---购买产品
        payOrder.setFinanceType(FinanceType.OUT);
        payOrder.setPayState(PayState.SUCCESS);// 这里是支付成功
        payOrder.setPlatformId(basePlatform.getId());
        payOrder.setTaskId(-1);
        payOrder.setHouseId(payModelConfigVo.getHouseId());
        payOrder.setPlanId(payModelConfigVo.getPlanId());
        LoginUser loginUser =
                new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                              sysUser.getMobile());
         int i = this.addPayOrder(payOrder, loginUser);

         if(i < 1){
             throw new BizException(ExceptionCodes.CODE_10010017,"插入订单失败");
            }
         return payOrder.getOrderNo();
    }

    @Override
    public int getExpenseRecordCountV2(PayOrderSearch payOrderSearch) {
        return payOrderMapper.getExpenseRecordCountV2(payOrderSearch);
    }

    @Override
    public List<PayOrder> getExpenseRecordListV2(PayOrderSearch payOrderSearch) {
        List<PayOrder> list = payOrderMapper.selectExpenseRecordListV2(payOrderSearch);
        for(PayOrder order : list){
            if(BizType.RECHARGE.equals(order.getProductType())){
                if(StringUtils.isNotEmpty(order.getPayType()) && PayType.WXPAY.equals(order.getPayType())){
                    order.setPlanName(com.sandu.pay.order.model.enums.PayType.WEIXIN.getName());
                }else if(StringUtils.isNotEmpty(order.getPayType()) && PayType.ALIPAY.equals(order.getPayType())){
                    order.setPlanName(com.sandu.pay.order.model.enums.PayType.ALIPAY.getName());
                }
                if(order.getPlanId() != null){
                    String planName = payOrderMapper.selectPlanNameByPlanId(order.getPlanId());
                    order.setPlanName(planName);
                }
            }
        }
        return list;
    }

    @Override
    public ResultMessage agencyPayModelByAppPay(PayModelConfigVo payModelConfigVo, Integer userId, Integer platformId) {
        ResultMessage message = new ResultMessage();
        BasePlatform basePlatform = basePlatformService.get(platformId);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        userId = userId == null ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            message.setMessage("用户信息为空");
            return message;
        }
        if (null == payModelConfigVo) {
            message.setMessage("支付参数错误");
            return message;
        }
        if (StringUtils.isEmpty(payModelConfigVo.getPayType()) ||
                (!payModelConfigVo.getPayType().equals(PayType.WXPAY) && !payModelConfigVo.getPayType().equals(PayType.ALIPAY))) {
            message.setMessage("支付方式有误，payType:{}");
            return message;
        }
        if (null == payModelConfigVo.getPayModelConfigId()) {
            message.setMessage("包年包月套餐id为空");
            return message;
        }
        PayModelConfig payModelConfig = payModelConfigService.get(payModelConfigVo.getPayModelConfigId());
        if (null == payModelConfig) {
            message.setMessage("包年包月套餐信息有误");
            return message;
        }
        boolean flag = false;
        int balanceAmount = 0;
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        if (null != companyFranchiserGroup) {
            flag = true; // 具备度币共享条件
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        } else {
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, platformId);
            if (null == payAccount) {
                logger.info("用户id：userId:{}" + userId + ",平台id：platformId:{}" + platformId + "的度币信息为空");
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        }
        String tradeType = TradeType.APP;
        if ((flag && payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE))
                || (!flag && payModelConfig.getBizType().equals(PayModelConstantType.RENDER_TYPE_FRANCHISER))) {
            message.setMessage("当前用户类型暂不能购买此套餐");
            return message;
        }
        if (flag) {
            // 当前如果是包月，那么判断是否有未过期的包年，如果有，那么就不给购买
            List<Map<String, Object>> list = payModelGroupRefService.getUserRefInfoList(sysUser.getFranchiserGroupId(),PayModelConstantType.RENDER_TYPE_FRANCHISER,PayModelConstantType.RANGE_TYPE_FRANCHISER);
            if (payModelConfig.getTimeType() == 0) {
                if (null != list && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).get("timeType").toString().equals("1")) {
                            message.setMessage("当前经销商用户存在未过期的包年，请在包年过期后购买");
                            return message;
                        }
                    }

                }
            }
        } else {
            // 当前如果是包月，那么判断是否有未过期的包年，如果有，那么就不给购买
            List<Map<String, Object>> list = payModelGroupRefService.getUserRefInfoList(userId,PayModelConstantType.RENDER_TYPE,PayModelConstantType.RANGER_TYPE_PERSONAGE);
            if (payModelConfig.getTimeType() == 0) {
                if (null != list && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).get("timeType").toString().equals("1")) {
                            message.setMessage("当前用户存在未过期的包年，请在包年过期后购买");
                            return message;
                        }
                    }

                }
            }
        }
        String productName =  PayProductConstans.RENDER_PAY_PRODUCT_NAME;
        if (payModelConfig.getTimeType() == 0) {
            productName = productName.replace("@replace_str","包月");
        } else if (payModelConfig.getTimeType() == 1) {
            productName = productName.replace("@replace_str","包年");
        }
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        PayOrder payOrder = getOrder(Double.valueOf(payModelConfig.getPackagePrice()).intValue(),payModelConfigVo.getPayType(),
                productName, productName,tradeType);
        try {
            payOrder.setPlatformId(platformId);
            sysSave(payOrder, loginUser);
            payOrder.setProductType(PayProductConstans.RENDER_PRODUCT_TYPE);
            payOrder.setProductId(payModelConfig.getId());
            payOrder.setBizType(BizType.BUY);
            payOrder.setFinanceType(FinanceType.OUT);
            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
            if (flag) {
                payOrder.setAtt1(String.valueOf(sysUser.getFranchiserGroupId())); // 业务id（备用字段1）
            } else {
                payOrder.setAtt1(String.valueOf(userId)); // 业务id（备用字段1）
            }
            payOrder.setAtt2(String.valueOf(payModelConfig.getId())); // 付款方式表id（备用字段2）
            int id = this.addPayOrder(payOrder, loginUser);
            if (id == 0) {
                message.setMessage("保存订单失败");
                return message;
            }
            payOrder.setId(id);
            String timeExpire = Utils.getTimeExpire(30);
            String flagStr = "SUCCESS";
            //微信APP start
            if (payModelConfigVo.getPayType().equals(PayType.WXPAY)) {
                UnifiedOrderService orderService = new UnifiedOrderService();
                String wxNotifyUrl = ResourceConfig.WXPAY_PAY_MODEL_NOTIFY_URL;
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(ResourceConfig.AGENCY_WXPAY_APPPAY_APPID,
                        ResourceConfig.AGENCY_WXPAY_APPPAY_MUCHID, ResourceConfig.AGENCY_WXPAY_APPPAY_KEY, productName,
                        payOrder.getOrderNo(), payOrder.getTotalFee(),
                        WxTradeType.APP, timeExpire, wxNotifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flagStr)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flagStr)) {
                    Map<String, Object> packageParams = new HashMap<String, Object>();
                    packageParams.put("appid", ResourceConfig.AGENCY_WXPAY_APPPAY_APPID);
                    packageParams.put("partnerid", ResourceConfig.AGENCY_WXPAY_APPPAY_MUCHID);
                    packageParams.put("prepayid", unifiedOrderResData.getPrepay_id());
                    packageParams.put("package", "Sign=WXPay");
                    packageParams.put("noncestr", System.currentTimeMillis() + "");
                    packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
                    String packageSign = Signature.getSign(packageParams, ResourceConfig.AGENCY_WXPAY_APPPAY_KEY);
                    packageParams.put("sign", packageSign);
                    message.setObj(packageParams);
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setNotifyUrl(wxNotifyUrl);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                    logger.info(CLASS_LOG_PREFIX+"2b移动端成功生成购买包年包月订单");
                } else {
                    message.setMessage("支付失败");
                    return message;
                }
            }
            //微信APP end
            //支付宝APP start
            if (payModelConfigVo.getPayType().equals(PayType.ALIPAY)) {
                Map<String, Object> formMap = new HashMap<String, Object>();
                String notifyUrl =  ResourceConfig.ALIPAY_PAY_MODEL_NOTIFY_URL;
                String form = ScanPayUtil.addScanpayAppOrder(payOrder, notifyUrl);
                payOrder.setNotifyUrl(notifyUrl);
                if (StringUtils.isEmpty(form)) {
                    message.setMessage("支付失败");
                    return message;
                } else {
                    formMap.put("form", form);
                    message.setObj(formMap);
                    message.setOrderNo(String.valueOf(payOrder.getId()));
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                }
            }
            //支付宝APP end
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("下单异常信息：",e);
            message.setMessage("下单异常");
            return message;
        }
        message.setMessage("success");
        message.setSuccess(Boolean.TRUE);
        return message;
    }

    @Override
    public ResultMessage agencyRechargeIntegralByAppPay(Integer userId, PayOrderModel payOrderModel, String platformCode) {
        ResultMessage message = new ResultMessage();
        if (StringUtils.isEmpty(platformCode)) {
            message.setMessage("平台编码：platformCode{}为空");
            return message;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            message.setMessage("平台信息：basePlatform{}为空");
            return message;
        }
        userId = userId == null ? 0 : userId;
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            message.setMessage("用户信息为空");
            return message;
        }
        if (null == payOrderModel) {
            message.setMessage("支付参数错误");
            return message;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType()) ||
                (!payOrderModel.getPayType().equals(PayType.WXPAY) && !payOrderModel.getPayType().equals(PayType.ALIPAY))) {
            message.setMessage("支付方式有误，payType:{}");
            return message;
        }
        String tradeType = TradeType.APP;
        if (null == payOrderModel.getProductId()) {
            message.setMessage("产品id为空：productId:{}");
            return message;
        }
        SysDictionary sysDictionary = sysDictionaryService.get(payOrderModel.getProductId());
        if (null == sysDictionary) {
            message.setMessage("数据字典中找不到度币信息");
            return message;
        }
        int totalFee = sysDictionary.getValue().intValue(); // 充值的金额
        int adjustFee = Utils.getIntValue(sysDictionary.getAtt1().trim()); // 赠送的金额
        PayOrder payOrder = getOrder(totalFee, payOrderModel.getPayType(), PayProductConstans.RECHARGE_PRODUCT_NAME, PayProductConstans.RECHARGE_PRODUCT_DESC, tradeType);
        payOrder.setPlatformId(basePlatform.getId());
        payOrder.setAdjustFee(adjustFee);
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId);
        int balanceAmount = 0;
        if (null == companyFranchiserGroup) {
            // 不具备经销商权限
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId, basePlatform.getId());
            if (null == payAccount) {
                message.setMessage("用户度币信息为空，生成订单失败");
                return message;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        } else {
            // 具备经销商权限
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        }
        try {
            payOrder.setProductType(PayProductConstans.PAY_PRODUCT_TYPE);
            sysSave(payOrder, loginUser);
            payOrder.setUserId(userId);
            payOrder.setProductId(payOrderModel.getProductId());
            payOrder.setBizType(BizType.RECHARGE);
            payOrder.setFinanceType(FinanceType.IN);
            payOrder.setCurrentIntegral(totalFee + adjustFee + balanceAmount);
            int id = this.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                logger.info("保存在线充值单失败");
                message.setMessage("保存在线充值单失败");
                return message;
            }
            payOrder.setId(id);
            String timeExpire = Utils.getTimeExpire(30);
            String flag = "SUCCESS";
            if (payOrderModel.getPayType().equals(PayType.WXPAY)) {
                UnifiedOrderService orderService = new UnifiedOrderService();
                String wxNotifyUrl = ResourceConfig.WX_PAY_NOTIFY_URL;
                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(ResourceConfig.AGENCY_WXPAY_APPPAY_APPID,
                        ResourceConfig.AGENCY_WXPAY_APPPAY_MUCHID, ResourceConfig.AGENCY_WXPAY_APPPAY_KEY, PayProductConstans.RECHARGE_PRODUCT_NAME,
                        payOrder.getOrderNo(), payOrder.getTotalFee(),
                        WxTradeType.APP, timeExpire, wxNotifyUrl);
                UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    Map<String, Object> packageParams = new HashMap<String, Object>();
                    packageParams.put("appid", ResourceConfig.AGENCY_WXPAY_APPPAY_APPID);
                    packageParams.put("partnerid", ResourceConfig.AGENCY_WXPAY_APPPAY_MUCHID);
                    packageParams.put("prepayid", unifiedOrderResData.getPrepay_id());
                    packageParams.put("package", "Sign=WXPay");
                    packageParams.put("noncestr", System.currentTimeMillis() + "");
                    packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
                    String packageSign = Signature.getSign(packageParams, ResourceConfig.AGENCY_WXPAY_APPPAY_KEY);
                    packageParams.put("sign", packageSign);
                    message.setObj(packageParams);
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setNotifyUrl(wxNotifyUrl);
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(2);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount(Double.valueOf(totalFee));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                } else {
                    message.setMessage("支付失败");
                    return message;
                }
            }
            if (payOrderModel.getPayType().equals(PayType.ALIPAY)) {
                Map<String, Object> formMap = new HashMap<String, Object>();
                String notifyUrl =  ResourceConfig.ALIPAY_NOTIFY_URL;
                String form = ScanPayUtil.addScanpayAppOrder(payOrder, notifyUrl);
                payOrder.setNotifyUrl(notifyUrl);
                if (StringUtils.isEmpty(form)) {
                    message.setMessage("支付失败");
                    return message;
                } else {
                    formMap.put("form", form);
                    message.setObj(formMap);
                    message.setOrderNo(String.valueOf(payOrder.getId()));
                    payOrder.setPayState(PayState.PAYING);
                    this.update(payOrder);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId);
                    mgrRechargeLog.setRechargeType(3);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount(Double.valueOf(totalFee));
                    mgrRechargeLog.setAdministratorId(userId);
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                }
            }
        } catch (Exception e) {
            logger.error("2b度币充值APP支付下单异常,内容是：",e);
            message.setMessage("下单异常");
            return message;
        }
        message.setMessage("success");
        message.setSuccess(Boolean.TRUE);
        return message;
    }

}
