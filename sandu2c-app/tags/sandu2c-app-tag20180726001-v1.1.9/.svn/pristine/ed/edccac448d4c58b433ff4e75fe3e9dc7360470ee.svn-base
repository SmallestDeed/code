package com.sandu.order.service.impl;

import com.google.gson.Gson;

import com.nork.common.model.LoginUser;
import com.sandu.cart.MallBaseCart;
import com.sandu.cart.service.MallBaseCartService;
import com.sandu.cart.service.MallCartProductRefService;
import com.sandu.common.constant.OrderConstant;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.tool.UniqueIDGenerater;
import com.sandu.common.util.Utils;
import com.sandu.goods.model.BO.GoodsInfoToOrderBO;
import com.sandu.goods.model.BaseGoodsSKU;
import com.sandu.goods.service.BaseGoodsSKUService;
import com.sandu.goods.service.BaseGoodsSPUService;
import com.sandu.order.MallBaseOrder;
import com.sandu.order.MallOrderAction;
import com.sandu.order.OrderProduct;
import com.sandu.order.dao.MallBaseOrderMapper;
import com.sandu.order.dao.MallOrderActionMapper;
import com.sandu.order.dao.OrderProductMapper;
import com.sandu.order.service.MallBaseOrderService;
import com.sandu.pay.order.metadata.PayState;
import com.sandu.product.model.BaseProduct;
import com.sandu.product.service.BaseProductService;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import com.sandu.useraddress.MallUserAddress;
import com.sandu.useraddress.dao.MallUserAddressMapper;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @version V1.0
 * @Title: BaseAreaController.java
 * @Package com.sandu.system.controller
 * @Description:系统-行政区域Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 15:31:09
 */

@Service("mallBaseOrderService")
public class MallBaseOrderServiceImpl implements MallBaseOrderService{

    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[订单服务]:";
    private final static Logger logger = LoggerFactory.getLogger(MallBaseOrderServiceImpl.class);

    @Autowired
    private SysUserService sysUserService ;
    @Autowired
    private MallBaseOrderMapper mallBaseOrderMapper;
    @Autowired
    private OrderProductMapper orderProductMapper;
    @Autowired
    private MallOrderActionMapper mallOrderActionMapper;

    @Autowired
    private MallUserAddressMapper mallUserAddressMapper;
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private BaseGoodsSPUService baseGoodsSPUService;
    @Autowired
    private MallBaseCartService mallBaseCartService;
    @Autowired
    private MallCartProductRefService mallCartProductRefService;
    @Autowired
    private BaseGoodsSKUService baseGoodsSKUService;
    
	@Override
	public List<MallBaseOrder> dynamicQueryOrder(MallBaseOrder order)
	{
		List<MallBaseOrder> mallBaseOrders = mallBaseOrderMapper.getOrderDetail(order);
		for (MallBaseOrder mallBaseOrder : mallBaseOrders)
		{
			mallBaseOrder.setFullAddress(mallBaseOrder.getProvince()+mallBaseOrder.getCity()+mallBaseOrder.getDistrict());
		}
		return mallBaseOrders;
	}
	
	@Override
	public MallBaseOrder getOrderByOrderId(MallBaseOrder order)
	{
		MallBaseOrder mallBaseOrder = mallBaseOrderMapper.getOrderDetail(order).get(0);
		mallBaseOrder.setFullAddress(mallBaseOrder.getProvince()+mallBaseOrder.getCity()+mallBaseOrder.getDistrict());
		BigDecimal spuTotalPrice = new BigDecimal("0");
		BigDecimal totalTransportCost = new BigDecimal("0");
        for (OrderProduct orderProduct : mallBaseOrder.getOrderProductList())
        {
            orderProduct.setProductPrice(orderProduct.getProductPrice().setScale(2,BigDecimal.ROUND_DOWN));
            orderProduct.setTransportationExpenses(orderProduct.getTransportationExpenses().setScale(2,BigDecimal.ROUND_DOWN));
            spuTotalPrice = spuTotalPrice.add(orderProduct.getProductPrice());
            totalTransportCost = totalTransportCost.add(orderProduct.getTransportationExpenses());
        }
        mallBaseOrder.setSpuTotalPrice(spuTotalPrice.setScale(2,BigDecimal.ROUND_DOWN));
        mallBaseOrder.setTotalTransportCost(totalTransportCost.setScale(2,BigDecimal.ROUND_DOWN));
        return mallBaseOrder;
	}
	
	@Override
	public List<MallUserAddress> getAddressByUserId(Integer userId)
	{
		return mallUserAddressMapper.getAddressByUserId(userId);
	}

    @Override
    public int addUserAddress(MallUserAddress mallUserAddress) {
        //查询用户信息
        SysUser sysUser = sysUserService.get(mallUserAddress.getUserId());
        if(sysUser==null){
            return 0;
        }
        Date date = new Date();
	    //封装地址数据
        mallUserAddress.setGmtCreate(date);
        mallUserAddress.setCreator(sysUser.getUserName());
        mallUserAddress.setModifier(sysUser.getUserName());
        mallUserAddress.setGmtModified(date);
        mallUserAddress.setIsDeleted(0);
        mallUserAddress.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
        return mallUserAddressMapper.insertSelective(mallUserAddress);
    }

    @Override
    public int updateUserAddress(MallUserAddress mallUserAddress) {
        //查询用户信息
        SysUser sysUser = sysUserService.get(mallUserAddress.getUserId());
        if(sysUser==null){
            return 0;
        }
        Date date = new Date();
        //封装地址数据
        mallUserAddress.setModifier(sysUser.getUserName());
        mallUserAddress.setGmtModified(date);
        return mallUserAddressMapper.updateByPrimaryKeySelective(mallUserAddress);
    }

    @Override
    public MallBaseOrder getOrderByOrderNo(String orderNo) {
        return mallBaseOrderMapper.getOrderByOrderNo(orderNo);
    }

    @Override
    public Integer updateBaseOrderByOrderId(Integer id) {
	    Integer status = 0;
        //修改订单表状态
        MallBaseOrder mallBaseOrder= new MallBaseOrder();
        mallBaseOrder.setId(id);
        mallBaseOrder.setPayStatus(OrderConstant.PAY_IN_PAY);
        status = mallBaseOrderMapper.updateBaseOrderByOrderId(mallBaseOrder);
        if(status<=0){
            return status;
        }
        //修改订单日志表状态
        MallOrderAction mallOrderAction = new MallOrderAction();
        mallOrderAction.setId(id);
        mallOrderAction.setPayStatus(OrderConstant.PAY_IN_PAY);
        status = mallOrderActionMapper.updateBaseOrderByOrderId(mallOrderAction);

        return status;
    }

    @Override
    @Transactional
    public Integer updateBaseOrderByOrderIdAndCallBackStatus(Integer id,String resultCode,String returnCode) {
	    if(resultCode.equalsIgnoreCase(PayState.SUCCESS)
                && returnCode.equalsIgnoreCase(PayState.SUCCESS)){
            //修改订单表状态
            MallBaseOrder mallBaseOrder= new MallBaseOrder();
            mallBaseOrder.setId(id);
            mallBaseOrder.setPayStatus(OrderConstant.PAY_IS_PAY);
            logger.info("修改订单状态开始:mallBaseOrder=>{}"+gson.toJson(mallBaseOrder));
            Integer orderId = mallBaseOrderMapper.updateBaseOrderByOrderId(mallBaseOrder);
            logger.info("修改订单状态完成:mallBaseOrder=>{}"+gson.toJson(mallBaseOrder));
            if(orderId<=0){
                return orderId;
            }
            //修改订单日志表状态
            MallOrderAction mallOrderAction = new MallOrderAction();
            mallOrderAction.setId(id);
            mallOrderAction.setPayStatus(OrderConstant.PAY_IS_PAY);
            Integer orderActionId = mallOrderActionMapper.updateBaseOrderByOrderId(mallOrderAction);

            return orderActionId;
        }else{
            //修改订单表状态
            MallBaseOrder mallBaseOrder= new MallBaseOrder();
            mallBaseOrder.setId(id);
            mallBaseOrder.setPayStatus(OrderConstant.PAY_UN_PAY);
            Integer orderId = mallBaseOrderMapper.updateBaseOrderByOrderId(mallBaseOrder);
            if(orderId<=0){
                return orderId;
            }
            //修改订单日志表状态
            MallOrderAction mallOrderAction = new MallOrderAction();
            mallOrderAction.setId(id);
            mallOrderAction.setPayStatus(OrderConstant.PAY_UN_PAY);
            Integer orderActionId = mallOrderActionMapper.updateBaseOrderByOrderId(mallOrderAction);

            return orderActionId;
        }



    }

    @Override
    public List<MallBaseOrder> getOrderByOrderList(List<Integer> idList) {

        return mallBaseOrderMapper.selectByOrderIdList(idList);
    }

    @Override
    @Transactional
    public ResponseEnvelope updateOrderStatusByOrderList(MallBaseOrder mallBaseOrder) {
        ResponseEnvelope res = new ResponseEnvelope();
	    //修改订单状态
        int orderStatus = mallBaseOrderMapper.updateOrderStatusByOrderList(mallBaseOrder);
        if(orderStatus<=0){
            res.setStatus(false);
            res.setMessage("修改订单状态失败");
        }
        // modified by zcd start
        if (mallBaseOrder.getOrderStatus() == 2) {
            MallBaseOrder order = new MallBaseOrder();
            order.setId(mallBaseOrder.getId());
            order = getOrderByOrderId(order);
            List<OrderProduct> orderProductList = order.getOrderProductList();
            List<Integer> productIdList = new ArrayList<>();
            for (OrderProduct orderProduct : orderProductList) {
                productIdList.add(orderProduct.getProductId());
            }
            List<BaseGoodsSKU> skuList = baseGoodsSKUService.getListByProductIdList(productIdList);
            for (OrderProduct orderProduct : orderProductList) {
                for (BaseGoodsSKU baseGoodsSKU : skuList) {
                    if (baseGoodsSKU.getProductId().equals(orderProduct.getProductId())) {
                        baseGoodsSKUService.changeInventory(baseGoodsSKU.getId(), orderProduct.getProductNumber());
                        baseGoodsSPUService.updateTotalInventoryBySkuId(baseGoodsSKU.getId(), orderProduct.getProductNumber());
                    }
                }
            }
        }
        // modified by zcd end

        //修改订单日志表状态
        int orderActionStatus =mallOrderActionMapper.updateOrderActionStatusByOrderList(mallBaseOrder);
        if(orderActionStatus<=0){
            res.setStatus(false);
            res.setMessage("修改订单日志表状态失败");
        }
        res.setStatus(true);
        res.setMessage("取消订单成功");

        return res;
    }

    @Override
    public int delUserAddress(Integer addressId) {
        return mallUserAddressMapper.deleteByPrimaryKey(addressId);
    }

    @Transactional
    @Override
    public int createOrder(MallBaseOrder mallBaseOrder) {
        // modified by zcd start
        List<Integer> cartProductIdList = new ArrayList<>();
        // modified by zcd end
	    //统计订单金额
        BigDecimal orderAmount = new BigDecimal("0");
        for(OrderProduct orderProduct :mallBaseOrder.getOrderProductList()){
            if(orderProduct.getProductId()==null||orderProduct.getProductNumber()==null){
                return 0;
            }
            /*Integer baseProductPrice = baseProductService.getProductPlatformPrice(orderProduct.getProductId());
            Double salePrice;
            if(baseProductPrice==null && baseProductPrice != 0){
                salePrice = 0d;
            }else{
                salePrice = baseProductPrice.doubleValue();
            }

            orderProduct.setProductPrice(new BigDecimal(salePrice));
            orderAmount += orderProduct.getProductNumber()*salePrice;*/

            // modified by zcd start
            GoodsInfoToOrderBO goodsInfo = baseGoodsSPUService.getGoodsInfoToOrder(orderProduct.getProductId());
            if(goodsInfo != null)
            {
                if (goodsInfo.getInventory() != null){
                    if (goodsInfo.getInventory().compareTo(orderProduct.getProductNumber()) < 0){
                        throw new RuntimeException(goodsInfo.getProductName()+"库存不足");
                    }
                }else {
                    throw new RuntimeException(goodsInfo.getProductName()+"库存不足");
                }
                orderProduct.setProductName(goodsInfo.getProductName());
                orderProduct.setProductPrice(goodsInfo.getPrice());
                //orderProduct.setProductPicPath(goodsInfo.getPicPath());
                orderProduct.setAttribute(goodsInfo.getAttribute() == null || "".equals(goodsInfo.getAttribute())?"默认":goodsInfo.getAttribute());
                if (goodsInfo.getTransportationExpenses() == null)
                {
                    orderProduct.setTransportationExpenses(new BigDecimal(0));
                }else
                {
                    orderProduct.setTransportationExpenses(goodsInfo.getTransportationExpenses().multiply(new BigDecimal(orderProduct.getProductNumber())));
                }
                orderAmount = orderAmount.add(orderProduct.getProductPrice().multiply(new BigDecimal(orderProduct.getProductNumber())))
                        .add(orderProduct.getTransportationExpenses());
            }
            cartProductIdList.add(orderProduct.getProductId());
            baseGoodsSKUService.changeInventory(goodsInfo.getSkuId(),0 - orderProduct.getProductNumber());
            baseGoodsSPUService.updateTotalInventoryBySkuId(goodsInfo.getSkuId(),0 - orderProduct.getProductNumber());
            // modified by zcd end
        }
        //生成订单编号
        UniqueIDGenerater uniqueIDGenerater = new UniqueIDGenerater();
        long orderCode = uniqueIDGenerater.nextId();

        //查询用户信息
        SysUser sysUser = sysUserService.get(mallBaseOrder.getUserId());
           if(sysUser==null){
               return 0;
           }

        Date date = new Date();
        //向订单表插入数据
        mallBaseOrder.setOrderAmount(orderAmount);
        mallBaseOrder.setOrderCode(orderCode+"");
        mallBaseOrder.setOrderStatus(OrderConstant.ORDER_UN_CONFIRM);
        mallBaseOrder.setShippingStatus(OrderConstant.SHIPPING_UN_SEND);
        mallBaseOrder.setPayStatus(OrderConstant.PAY_UN_PAY);
        mallBaseOrder.setGmtCreate(date);
        mallBaseOrder.setCreator(sysUser.getUserName());
        mallBaseOrder.setModifier(sysUser.getUserName());
        mallBaseOrder.setGmtModified(date);
        mallBaseOrder.setIsDeleted(0);
        mallBaseOrder.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
        logger.info("向订单表插入数据开始:mallBaseOrder=>{}"+gson.toJson(mallBaseOrder));
        mallBaseOrderMapper.insertSelective(mallBaseOrder);
        int orderId = mallBaseOrder.getId();
        logger.info("向订单表插入数据完成:mallBaseOrder=>{}"+gson.toJson(mallBaseOrder));
        if(orderId<=0){
            logger.info(CLASS_LOG_PREFIX + "向订单表插入数据失败.", gson.toJson(mallBaseOrder));
            return 0;
        }
        //向订单产品中间表插入数据
        List<OrderProduct> orderProductList = mallBaseOrder.getOrderProductList();
        for(OrderProduct orderProduct:orderProductList){
            orderProduct.setCreator(sysUser.getUserName());
            orderProduct.setTransportationExpenses(orderProduct.getTransportationExpenses()==null?
                    new BigDecimal(0):orderProduct.getTransportationExpenses());
            orderProduct.setGmtCreate(date);
            orderProduct.setGmtModified(date);
            orderProduct.setModifier(sysUser.getUserName());
            orderProduct.setOrderId(orderId);
            orderProduct.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
            orderProduct.setIsDeleted(0);
        }
        logger.info("向订单产品中间表插入数据开始:orderProductList=>{}"+gson.toJson(orderProductList));
        int orderProductId = orderProductMapper.insertOrderProductList(orderProductList);
        logger.info("向订单产品中间表插入数据完成:orderProductList=>{}"+gson.toJson(orderProductList));
        if(orderProductId<=0){
            logger.info(CLASS_LOG_PREFIX + "向订单产品中间表插入数据失败.", gson.toJson(orderProductList));
            return 0;
        }


        // modified by zcd start
        // 生成订单后删除购物车内相关产品
        if (mallBaseOrder.getIsCart() != null && mallBaseOrder.getIsCart() == 1) {
            mallCartProductRefService.deleteByProductIds(cartProductIdList, mallBaseOrder.getUserId());
        }
        //modified by zcd end

        //向订单日志表中插入信息
        MallOrderAction mallOrderAction = new MallOrderAction();
        mallOrderAction.setOrderId(orderId);
        mallOrderAction.setActionUserId(sysUser.getId());
        mallOrderAction.setOrderStatus(OrderConstant.ORDER_UN_CONFIRM);
        mallOrderAction.setShippingStatus(OrderConstant.SHIPPING_UN_SEND);
        mallOrderAction.setPayStatus(OrderConstant.PAY_UN_PAY);
        mallOrderAction.setGmtCreate(date);
        mallOrderAction.setGmtModified(date);
        mallOrderAction.setModifier(sysUser.getUserName());
        mallOrderAction.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
        mallOrderAction.setIsDeleted(0);
        logger.info("向订单日志表中插入信息开始:mallOrderAction=>{}"+gson.toJson(mallOrderAction));
        int orderActionId = mallOrderActionMapper.insertSelective(mallOrderAction);
        logger.info("向订单日志表中插入信息完成:mallOrderAction=>{}"+gson.toJson(mallOrderAction));
        if(orderActionId<=0){
            logger.info(CLASS_LOG_PREFIX + "向订单日志表插入数据失败.", gson.toJson(mallOrderAction));
            return  0;
        }

        return orderId;
    }
}
