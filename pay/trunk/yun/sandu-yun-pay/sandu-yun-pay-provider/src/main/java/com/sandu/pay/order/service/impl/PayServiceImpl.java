package com.sandu.pay.order.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.common.util.Utils;
import com.sandu.config.ResourceConfig;
import com.sandu.pay.alipay.ScanPayUtil;
import com.sandu.pay.alipay.util.AlipayNotify;
import com.sandu.pay.mgrRecharge.model.MgrRechargeLog;
import com.sandu.pay.mgrRecharge.model.ReChargeStatus;
import com.sandu.pay.mgrRecharge.model.ReChargeType;
import com.sandu.pay.order.metadata.PayState;
import com.sandu.pay.order.metadata.ScanPayReqData;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.order.model.enums.GoodsType;
import com.sandu.pay.order.model.enums.PayType;
import com.sandu.pay.order.service.MgrRechargeLogService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay.order.service.PayService;
import com.sandu.pay.wexin.metadata.WxTradeType;
import com.sandu.pay.wexin.protocol.UnifiedOrderReqData;
import com.sandu.pay.wexin.protocol.UnifiedOrderResData;
import com.sandu.pay.wexin.service.UnifiedOrderService;
import com.sandu.user.model.LoginUser;

/**
 * Created by Administrator on 2017/9/20.
 */
@Service("payService")
@Transactional
public class PayServiceImpl implements PayService{

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private MgrRechargeLogService mgrRechargeLogService;
    
    /**
     * 支付主入口
     * @param payOrder 订单信息
     */
    @Override
    public ResultMessage processPay(PayOrder payOrder, LoginUser loginUser){
        ResultMessage message = new ResultMessage();
        // 1、创建订单
        int i = payOrderService.addPayOrder(payOrder, loginUser);

        // 3、调用微信/支付宝创建支付订单接口
        if( i > 0 ){
            if( PayType.ALIPAY.toString().equalsIgnoreCase(payOrder.getPayType()) ){
                message = createAlipayOrder(payOrder);
            }else if( PayType.WEIXIN.toString().equalsIgnoreCase(payOrder.getPayType()) ){
                message = createWeixinOrder(payOrder);
            }
        }else{
            message.setMessage("创建订单失败");
        }

        // 4、根据商品类型判断需要做什么
        if( GoodsType.OPM_CHARGE.toString().equalsIgnoreCase(payOrder.getProductType()) ){   // 运营网站充值订单,需要添加充值记录
            addMgrRechargeLog(payOrder, loginUser);
        }else{}

        return message;
    }

    /**
     * 调用支付宝创建支付订单接口
     * @param payOrder
     * @return
     */
    public ResultMessage createAlipayOrder(PayOrder payOrder){
        ResultMessage message = new ResultMessage();
        ScanPayReqData reqData = new ScanPayReqData();
        reqData.setOrderId(payOrder.getId());

        message = ScanPayUtil.addScanpayOrder(payOrder, reqData,ResourceConfig.IMAGE_SERVER_URL,ResourceConfig.UPLOAD_ROOT);
        message.setObj(reqData);
        if (message.isSuccess()) {
            payOrder.setPayState(PayState.PAYING);
            payOrderService.update(payOrder);
            message.setMessage("成功");
            message.setSuccess(true);
            message.setOrderNo(payOrder.getOrderNo());
        }
        return message;
    }

    /**
     * 调用微信创建支付订单接口
     * @param payOrder
     * @return
     */
    public ResultMessage createWeixinOrder(PayOrder payOrder){
        ResultMessage message = new ResultMessage();
        ScanPayReqData reqData = new ScanPayReqData();
        String flag = "SUCCESS";
        try {
            UnifiedOrderService orderService = new UnifiedOrderService();
            // 获取当前时候延迟30分钟后的时间并格式化为"yyyyMMddHHmmss"格式
            String timeExpire = Utils.getTimeExpire(30);
            UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(payOrder.getProductName(), payOrder.getOrderNo(),
                    payOrder.getTotalFee(), WxTradeType.NATIVE, timeExpire,ResourceConfig.WX_PAY_NOTIFY_URL);
            UnifiedOrderResData unifiedOrderResData = orderService.request(unifiedOrderReqData);
            if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                    && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                String prepayId = unifiedOrderResData.getPrepay_id();
                String wx_codeUrl = unifiedOrderResData.getCode_url();
                reqData.setCode_url(wx_codeUrl);//直接返回访问路径，不在返回2维码
                payOrder.setPrepayId(prepayId);
                payOrder.setPayState(PayState.PAYING);
                payOrderService.update(payOrder);
                message.setMessage("成功");
                message.setObj(reqData);
                message.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("创建微信支付订单异常");
        }
        return message;
    }

    /**
     * 添加充值记录
     * @param payOrder
     * @param loginUser
     */
    public void addMgrRechargeLog(PayOrder payOrder, LoginUser loginUser){
        MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
        mgrRechargeLog.setUserId(loginUser.getId());
        if( PayType.WEIXIN.toString().equalsIgnoreCase(payOrder.getPayType()) ){
            mgrRechargeLog.setRechargeType(ReChargeType.WEIXIN);
        }else if( PayType.ALIPAY.toString().equalsIgnoreCase(payOrder.getPayType()) ){
            mgrRechargeLog.setRechargeType(ReChargeType.ALIPAY);
        }
        mgrRechargeLog.setRechargeStatus(ReChargeStatus.RECHARGE_PAYING);
        mgrRechargeLog.setRechargeAmount(payOrder.getTotalFee().doubleValue());
        mgrRechargeLog.setAdministratorId(loginUser.getId());
        mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
        mgrRechargeLogService.sysSave(mgrRechargeLog, loginUser);
        mgrRechargeLogService.add(mgrRechargeLog);
    }

    /**
     * 支付宝回调入口
     * @param orderNo   商户订单号
     * @param tradeNo   支付宝交易号
     * @param tradeStatus   交易状态
     * @param notify_id 异步通知ID
     * @return
     */
    public String aliPayCallback(Map<String, String> params, String orderNo, String tradeNo, String tradeStatus, String notify_id, String sign){
        String message = "";
        if (notify_id != "" && notify_id != null) {//// 判断接受的post通知中有无notify_id，如果有则是异步通知。
            if (AlipayNotify.verifyResponse(notify_id).equals("true")){ // 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
                PayOrder order = payOrderService.getOrderByOrderNo(orderNo);
                if( order == null ){
                    message = "order lost";
                    return message;
                }
                synchronized (orderNo){
                    processCallBack(params, order, tradeNo, tradeStatus, sign);
                }
            }
            // 验证是否来自支付宝的通知失败
            else {
                message = "response fail";
            }
        } else {
            message = "no notify message";
        }
        return message;
    }

    /**
     * 处理支付回调
     * @param tradeStatus
     */
    public String processCallBack(Map<String, String> params, PayOrder order, String tradeNo, String tradeStatus, String sign){
        String message = "";
        if (AlipayNotify.getSignVeryfy(params, sign)){ // 使用支付宝公钥验签
            PayOrder updateOrder = new PayOrder();
            updateOrder.setOrderNo(order.getOrderNo());
            updateOrder.setPayState(PayState.PAY_ERROR);
            updateOrder.setRefId(tradeNo);
            if (tradeStatus.equals("TRADE_FINISHED")) {

            } else if (tradeStatus.equals("TRADE_SUCCESS")) {
                // 1、更新订单状态
                updateOrder.setPayState(PayState.SUCCESS);
                payOrderService.updatePayState(updateOrder);

                // 2、通过商品类型判断需要做什么操作
                if( GoodsType.OPM_CHARGE.toString().equalsIgnoreCase(order.getProductType()) ){     // 运营网站充值订单
                    // 2.1、更新充值状态
                    MgrRechargeLog mgr = mgrRechargeLogService.getReChargeByOrderNo(order.getOrderNo());
                    if( mgr != null ){
                        MgrRechargeLog updateMgr = new MgrRechargeLog();
                        updateMgr.setId(mgr.getId());
                        updateMgr.setOrderNo(order.getOrderNo());
                        updateMgr.setRechargeStatus(ReChargeStatus.RECHARGE_SUCCESS);
                        mgrRechargeLogService.update(updateMgr);

                        // 2.2、变更用户余额
                    }
                }

                        /*

                        // 给客户端发送支付状态消息
                        updateUserFinance(orderNo);

                        //判断用户是否需要自动升级
                        sysResLevelCfgService.processAfterConsume(orderNo);*/
            }
            //给客户端发送订单支付状态信息,更新任务状态，退款
//                    payOrderService.sendPayStateNew(orderNo);
            message = "success";
        }
        // 验证签名失败
        else {
            message = "sign fail";
//                        payOrderService.updatePayState(updateOrder);
            // 给客户端发送支付状态消息
//                    payOrderService.sendPayStateNew(orderNo);
        }
        return message;
    }
}
