package com.sandu.pay.controller;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sandu.common.LoginContext;
import com.sandu.common.constant.PlatformConstants;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.forward.service.PayService;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.gateway.pay.input.TransfersParam;
import com.sandu.order.MallBaseOrder;
import com.sandu.order.service.MallBaseOrderService;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BasePlatformService;
import com.sandu.pay.common.exception.BizException;
import com.sandu.pay.order.metadata.BizType;
import com.sandu.pay.order.metadata.FinanceType;
import com.sandu.pay.order.metadata.PayState;
import com.sandu.pay.order.metadata.PayType;
import com.sandu.pay.order.metadata.TradeType;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;

@Controller
@RequestMapping("/v1/web/pay/order")
public class OrderController {

    private static Logger logger = LogManager.getLogger(OrderController.class);

  //  @Autowired
    private MallBaseOrderService mallBaseOrderService;
    
    @Resource(name="miniProPay")
    private PayService payService;
    
    @Resource
    private PayOrderService payOrderService;
    
    @Resource
    private BasePlatformService basePlatformService;
    
    @Resource
    private SysUserService sysUserService;
    
    private DateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 小程序商城订单支付(demo)
     *
     * @param request
     * @param orderNo
     * @param payMethod
     * @return
     */
 //   @RequestMapping(value = "/mallOrderPaying",method = RequestMethod.POST)
    @ResponseBody
    public Object mallOrderPaying(HttpServletRequest request, String orderNo, String payMethod) {
        if (StringUtils.isBlank(orderNo)) {
            return new ResponseEnvelope(true, "orderNo不能为空", null, false);
        }
        String platformCode = request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        if (StringUtils.isBlank(platformCode)) {
            return new ResponseEnvelope(true, "平台编码不能为空", null, false);
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        SysUser user = sysUserService.get(loginUser.getId().intValue());
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        Integer payOrderId = null;
        try {
        	PayParam param = buildPayParam(orderNo,loginUser.getId(),platformCode);
        	payOrderId = this.createMallOrderPayOrder(basePlatform.getId(), user, orderNo,param);
        	logger.info("调用支付接口参数:{}",param.toString());
        	String result = payService.doPay(param);
        	logger.info("调用支付接口结果:{}",result);
            //记录交易流水
            /*PayOrder payOrder = payOrderService.getOrderByOrderNo(orderNo);
            if (payOrder != null) {
              payOrderService.deleteByOrderNo(orderNo);
            }*/
            Map resultMap = GsonUtil.fromJson(result, Map.class);
            String prepayId = "";
            if(resultMap.get("package")!=null) {
            	int index = resultMap.get("package").toString().indexOf("prepay_id=");
            	if(index!=-1) {
            		prepayId = resultMap.get("package").toString().substring(index+10);
            	}
            }
            modifyMallOrderPayInfo(payOrderId,prepayId,param.getIntenalTradeNo());
            
            return new ResponseEnvelope(result, null, true);
        } catch (BizException e) {
        	modifyMallOrderErrorInfo(payOrderId,e.getMessage());
            return new ResponseEnvelope(true, e.getMessage(), null, false);
        } catch (Exception e) {
            logger.error("系统繁忙:", e);
            return new ResponseEnvelope(true, "系统繁忙", null, false);
        }
    }
    
    private int createMallOrderPayOrder(Integer platformId, SysUser user, 
    		String orderNo, PayParam param) {
        PayOrder payOrder = new PayOrder();
        payOrder.setUserId(user.getId());
        payOrder.setProductId(null);
        payOrder.setOrderNo(orderNo);
        payOrder.setTotalFee(param.getTotalFee().intValue());
        payOrder.setPayType(PayType.WXPAY);
        payOrder.setPayState(PayState.PAYING);
        payOrder.setOpenId(user.getOpenId());
        payOrder.setOrderDate(new Date());
        payOrder.setTradeType(TradeType.MINIPAY);
        payOrder.setFinanceType(FinanceType.OUT);
        payOrder.setBizType(BizType.BUY);
        payOrder.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        payOrder.setCreator(user.getMobile());
        payOrder.setGmtCreate(new Date());
        payOrder.setGmtModified(new Date());
        payOrder.setModifier(user.getMobile());
        payOrder.setIsDeleted(0);
        payOrder.setPlatformId(platformId);
        payOrder.setTradeNo(param.getIntenalTradeNo());
        return payOrderService.add(payOrder);
    }
    
    private void modifyMallOrderPayInfo(Integer payOrderId,String prepayId,String tradeNo) {
    	if(payOrderId!=null) {
	        PayOrder payOrder = new PayOrder();
	        payOrder.setId(payOrderId);
	        payOrder.setPrepayId(prepayId);
	        payOrder.setTradeNo(tradeNo);
	        payOrderService.update(payOrder);
    	}
    }
    
    private PayParam buildPayParam(String orderNo,Integer userId,String platformCode) {
    	MallBaseOrder baseOrder = mallBaseOrderService.getOrderByOrderNo(orderNo);
    	if(baseOrder == null) {
    		throw new BizException("订单不存在!");
    	}
    	PayParam payParam = new PayParam();
    	payParam.setIntenalTradeNo(getTradeNo(userId));
    	payParam.setTradeDesc("订单支付");
    	Long totalFee = Double.valueOf(baseOrder.getOrderAmount().doubleValue() * 100).longValue();
    	payParam.setTotalFee(totalFee);
    	payParam.setPayMethod(PayParam.PAY_METHOD_WX_MINI_PAY);
    	payParam.setIp(getLocalIP());
    	payParam.setNotifyUrl("https://zhifu.sanduspace.com/v1/web/pay/order/callback/miniPay/notify");
    	payParam.setOperator(userId.longValue());
    	payParam.setPlatformCode(platformCode);
    	payParam.setSource(PayParam.SOURCE_ORDER);
    	return payParam;
    }
    
    private String getTradeNo(Integer userId) {
    	String nowStr = df.format(new Date());
    	return nowStr+(10000000000L-userId);
    }
    
    private String getLocalIP() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        return ip;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }
    
   //(demo) 
 //   @RequestMapping(value = "/mallOrderRefunding",method = RequestMethod.POST)
    @ResponseBody
    public Object mallOrderRefunding(HttpServletRequest request, String orderNo) {
    	Integer payOrderId = null;
		try {
			if (StringUtils.isBlank(orderNo)) {
				return new ResponseEnvelope(true, "orderNo不能为空", null, false);
			}
			String platformCode = request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
			if (StringUtils.isBlank(platformCode)) {
				return new ResponseEnvelope(true, "平台编码不能为空", null, false);
			}
			LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			SysUser user = sysUserService.get(loginUser.getId().intValue());
			if(user==null) {
				return new ResponseEnvelope(true, "用户不能为空", null, false);
			}
			
			RefundParam refundParam = this.buildRefundParam(orderNo, loginUser.getId(), platformCode);
			BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
			payOrderId = this.createMallOrderRefundInfo(basePlatform.getId(), user,orderNo,refundParam.getInternalRefundNo());
			String result = payService.doRefund(refundParam);
			Map resultMap = GsonUtil.fromJson(result, Map.class);
			modifyMallOrderRefundInfo(payOrderId,resultMap);
			return new ResponseEnvelope("申请成功!", null, true);
		} catch (BizException e) {
			modifyMallOrderErrorInfo(payOrderId,e.getMessage());
			return new ResponseEnvelope(true, e.getMessage(), null, false);
		} catch (Exception e) {
			logger.error("系统繁忙:", e);
			return new ResponseEnvelope(true, "系统繁忙", null, false);
		}
    }
    
    private int createMallOrderRefundInfo(Integer platformId, SysUser user,String orderNo,String refundNo) {
    	PayOrder historyPayOrder = getHistoryPayOrder(orderNo);
    	if(historyPayOrder==null) {
    		throw new BizException("订单未支付!");
    	}
        PayOrder payOrder = new PayOrder();
        payOrder.setUserId(user.getId());
        payOrder.setProductId(null);
        payOrder.setOrderNo(orderNo);
        payOrder.setTotalFee(historyPayOrder.getTotalFee());
        payOrder.setPayType(PayType.WXPAY);
        payOrder.setPayState(PayState.REFUND);
        payOrder.setOpenId(user.getOpenId());
        payOrder.setOrderDate(new Date());
        payOrder.setTradeType(TradeType.MINIPAY);
        payOrder.setFinanceType(FinanceType.IN);
        payOrder.setBizType(BizType.REFUND);
        payOrder.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        payOrder.setCreator(user.getMobile());
        payOrder.setGmtCreate(new Date());
        payOrder.setGmtModified(new Date());
        payOrder.setModifier(user.getMobile());
        payOrder.setIsDeleted(0);
        payOrder.setPlatformId(platformId);
        payOrder.setTradeNo(historyPayOrder.getTradeNo());
        payOrder.setPayTradeNo(historyPayOrder.getPayTradeNo());
        payOrder.setRefundNo(refundNo);
        return payOrderService.add(payOrder);
    }
    
    private void modifyMallOrderRefundInfo(Integer payOrderId,Map resultMap) {
    	String payRefundNo = resultMap.get("payRefundNo").toString();
        PayOrder payOrder = new PayOrder();
        payOrder.setId(payOrderId);
        payOrder.setPayRefundNo(payRefundNo);
        payOrder.setGmtModified(new Date());
        payOrderService.update(payOrder);
    }
    
    private void modifyMallOrderErrorInfo(Integer payOrderId,String errorInfo) {
    	if(payOrderId!=null) {
	        PayOrder payOrder = new PayOrder();
	        payOrder.setId(payOrderId);
	        payOrder.setRemark(errorInfo);
	        payOrder.setGmtModified(new Date());
	        payOrderService.update(payOrder);
    	}
    }
    
    private PayOrder getHistoryPayOrder(String orderNo) {
    	if(StringUtils.isBlank(orderNo)) {
    		return null;
    	}
    	PayOrder payOrder = new PayOrder();
    	payOrder.setIsDeleted(0);
    	payOrder.setOrderNo(orderNo);
    	payOrder.setPayState(PayState.SUCCESS);
    	List<PayOrder> list = payOrderService.getList(payOrder);
    	if(list!=null && list.size()>0) {
    		return list.get(0);
    	}
    	return null;
    }
    
    private RefundParam buildRefundParam(String orderNo,Integer userId,String platformCode) {
    	RefundParam refundParam = new RefundParam();
    	//找到之前的支付凭证
    	PayOrder historyPayOrder = getHistoryPayOrder(orderNo);
    	if(historyPayOrder==null) {
    		throw new BizException("未找到支付凭证!");
    	}
    	Long totalFee = historyPayOrder.getTotalFee().longValue();
    	refundParam.setOriginInternalTradeNo(historyPayOrder.getTradeNo());
    	refundParam.setInternalRefundNo(getRefundNo(userId));
    	refundParam.setTotalFee(totalFee);
    	refundParam.setRefundFee(totalFee);
    	refundParam.setRefundDesc("拼团失败退款");
    	refundParam.setIp(getLocalIP());
    	refundParam.setNotifyUrl("https://zhifu.sanduspace.com/v1/gateway/pay/callback/test????/notify");
    	refundParam.setOperator(userId.longValue());
    	refundParam.setPlatformCode(platformCode);
    	refundParam.setSource(PayParam.SOURCE_ORDER);
    	return refundParam;
    }

    private String getRefundNo(Integer userId) {
    	String nowStr = df.format(new Date());
    	return nowStr+(10000000000L-userId);
    }
    
    
    @RequestMapping(value = "/doTransfersTest",method = RequestMethod.POST)
    @ResponseBody
    public Object doTransfersTest(HttpServletRequest request, String orderNo) {
    	Integer payOrderId = null;
		try {
			
			String platformCode = request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
			if (StringUtils.isBlank(platformCode)) {
				return new ResponseEnvelope(true, "平台编码不能为空", null, false);
			}
			//LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
			//SysUser user = sysUserService.get(loginUser.getId().intValue());
			SysUser user = sysUserService.get(32885);
			if(user==null) {
				return new ResponseEnvelope(true, "用户不能为空", null, false);
			}
			
			TransfersParam transfersParam = this.buildTransfersParam(orderNo, user, platformCode);
			BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
			//payOrderId = this.createMallOrderRefundInfo(basePlatform.getId(), user,orderNo,refundParam.getInternalRefundNo());
			String result = payService.doTransfers(transfersParam);
			logger.info("企业付款结果:", result);
			logger.info("transfersParam参数:{}",transfersParam.getIp());
			Map resultMap = GsonUtil.fromJson(result, Map.class);
			//modifyMallOrderRefundInfo(payOrderId,resultMap);
			return new ResponseEnvelope("企业付款成功!", null, true);
		} catch (BizException e) {
			modifyMallOrderErrorInfo(payOrderId,e.getMessage());
			return new ResponseEnvelope(true, e.getMessage(), null, false);
		} catch (Exception e) {
			logger.error("系统繁忙:", e);
			return new ResponseEnvelope(true, "系统繁忙", null, false);
		}
    }

	private TransfersParam buildTransfersParam(String orderNo, SysUser user, String platformCode) {
		TransfersParam transfersParam = new TransfersParam();
		transfersParam.setIntenalTradeNo("test001");
		transfersParam.setOpenId(user.getOpenId());
		transfersParam.setAmount(50L);
		transfersParam.setTradeDesc("企业付款测试");
//		transfersParam.setIp(getLocalIP());
		transfersParam.setIp("114.119.11.232");
		transfersParam.setOperator(user.getId().longValue());
		transfersParam.setPlatformCode(platformCode);
		transfersParam.setSource(PayParam.SOURCE_ORDER);
    	return transfersParam;
	}

   
}
