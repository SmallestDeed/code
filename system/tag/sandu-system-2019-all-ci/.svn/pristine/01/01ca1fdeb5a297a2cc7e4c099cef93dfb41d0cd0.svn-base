package com.sandu.service.grouppurchase.service.impl.biz;

import com.sandu.activity.model.Coupon;
import com.sandu.activity.model.CouponUser;
import com.sandu.activity.service.CouponService;
import com.sandu.api.grouppurchase.bo.GoodsInfoToOrderBO;
import com.sandu.api.grouppurchase.input.GroupPurchaseOrderNew;
import com.sandu.api.grouppurchase.input.OrderProductNew;
import com.sandu.api.grouppurchase.model.GroupPurchaseOrder;
import com.sandu.api.grouppurchase.output.GroupPurchaseOrderVO;
import com.sandu.api.grouppurchase.service.biz.GroupPurchaseOrderBizService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.tool.UniqueIDGenerater;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.service.BaseCompanyService;
import com.sandu.service.grouppurchase.dao.GroupPurchaseGoodsMapper;
import com.sandu.service.grouppurchase.dao.GroupPurchaseOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/11 10:22
 * @since 1.8
 */

@Slf4j
@Service("groupPurchaseOrderBizService")
public class GroupPurchaseOrderBizServiceImpl implements GroupPurchaseOrderBizService {

    @Resource
    CouponService couponService;

    @Autowired
    SysUserService sysUserService;

    @Resource
    BaseCompanyService baseCompanyService2;

    @Autowired
    GroupPurchaseOrderMapper groupPurchaseOrderMapper;

    @Autowired
    GroupPurchaseGoodsMapper groupPurchaseGoodsMapper;

    @Override
    @Transactional
    public ResponseEnvelope<GroupPurchaseOrderVO> createOrder(GroupPurchaseOrderNew orderNew) {
        //查询用户信息
        SysUser sysUser = sysUserService.get(orderNew.getUserId());
        if (sysUser == null) {
            log.error("拼团下单：通过userId => {}未找到用户信息", orderNew.getUserId());
            return new ResponseEnvelope<>(false, "下单的用户不能存在或被删除");
        }

        // 获取当前的企业ID
        BaseCompany baseCompany = baseCompanyService2.getCompanyByDomainUrl(orderNew.getDomainUrl());
        if (baseCompany == null || baseCompany.getId() == null) {
            log.error("拼团下单：通过domainUrl => {}未获取到该公司信息", orderNew.getDomainUrl());
            return new ResponseEnvelope<>(false, "未获取到该公司");
        }
        orderNew.setAppId(baseCompany.getAppId());
        orderNew.setCompanyId(baseCompany.getId());

        // 购物车的商品
        List<Long> listCartProduct = new ArrayList<>();
        // 总订单金额
        BigDecimal orderAmount = new BigDecimal("0");

        for (OrderProductNew orderProductNew : orderNew.getOrderProductList()) {
            if (orderProductNew.getProductId() == null || orderProductNew.getProductNumber() == null
                    || orderProductNew.getProductNumber() <= 0) {
                log.error("拼团下单：参数异常：productId不能为空");
                return new ResponseEnvelope<>(false, "参数异常：productId不能为空");
            }

            GoodsInfoToOrderBO goodsInfo = groupPurchaseGoodsMapper.getGoodsInfoToOrder(orderProductNew.getProductId());
            if (goodsInfo == null) {
                log.error("通过productId => {}未找到商品信息", orderProductNew.getProductId());
                return new ResponseEnvelope<>(false, "");
            }

            if (goodsInfo.getUseQty() != null) {
                if (goodsInfo.getUseQty().compareTo(orderProductNew.getProductNumber()) < 0) {
                    return new ResponseEnvelope<>(false, goodsInfo.getProductName() + "库存不足");
                }
            } else {
                return new ResponseEnvelope<>(false, goodsInfo.getProductName() + "库存不足");
            }

            if (null == goodsInfo.getActivityPrice() || new BigDecimal(0).equals(goodsInfo.getActivityPrice())) {
                log.error("拼团下单：productId => {}的price => {}异常", orderProductNew.getProductId(), goodsInfo.getActivityPrice());
                return new ResponseEnvelope<>(false, goodsInfo.getProductName() + "产品信息异常");
            }

            orderProductNew.setProductName(goodsInfo.getProductName());
            // 活动价
            orderProductNew.setProductPrice(goodsInfo.getActivityPrice());
            orderProductNew.setAttribute(goodsInfo.getAttribute() == null || "".equals(goodsInfo.getAttribute())
                    ? "默认" : goodsInfo.getAttribute());
            if (goodsInfo.getTransportationExpenses() == null) {
                orderProductNew.setTransportationExpenses(new BigDecimal(0));
            } else {
                orderProductNew.setTransportationExpenses(goodsInfo.getTransportationExpenses()
                        .multiply(new BigDecimal(orderProductNew.getProductNumber())));
            }

            // 产品价格 + 物流费
            orderAmount = orderAmount.add(goodsInfo.getActivityPrice().multiply(new BigDecimal(orderProductNew.getProductNumber())))
                    .add(orderProductNew.getTransportationExpenses() == null ? new BigDecimal(0) : orderProductNew.getTransportationExpenses());

            // 购物车
            listCartProduct.add(orderProductNew.getProductId().longValue());
            // baseGoodsSKUService.changeInventory(goodsInfo.getSkuId(), 0 - orderProductNew.getProductNumber());
            // baseGoodsSPUService.updateTotalInventoryBySkuId(goodsInfo.getSkuId(), 0 - orderProductNew.getProductNumber());
        }

        // 生成订单编号
        UniqueIDGenerater uniqueIDGenerater = new UniqueIDGenerater();
        long orderCode = uniqueIDGenerater.nextId();

        // 订单处理
        GroupPurchaseOrder order = new GroupPurchaseOrder();
        order.setOrderCode(orderCode + "");
        order.setOrderAmount(orderAmount);

        // 生成订单
        groupPurchaseOrderMapper.createOrder(order);

        // 优惠价处理
        handlerCoupon(orderNew);

        // 订单产品处理

        // 操作日志处理

        // 查询订单详细信息

//        MallBaseOrder order = new MallBaseOrder();
//        order.setId(orderId);
//        order.setIsDeleted(0);
//        order.setReceiveNo(mallBaseOrder.getReceiveNo());
//        MallBaseOrder orderDetail = mallBaseOrderService.getOrderByOrderId(order);

        return null;
    }

    /**
     * @param orderNew
     */
    private void handlerCoupon(GroupPurchaseOrderNew orderNew) {
        if (null == orderNew.getReceiveNo() || orderNew.getCouponId() == null) {
            log.info("拼团下单：用户没有使用优惠价");
            return;
        }

        BigDecimal orderAmount = orderNew.getOrderAmount();
        CouponUser couponUser = couponService.getCouponByCouponNo(orderNew.getReceiveNo(), 0);
        log.info("查询activity_coupon_user表结果 {}", couponUser);

        if (couponUser != null) {
            Coupon coupon = couponService.getCouponByCouponId(couponUser.getCouponId());
            log.info("查询activity_coupon表结果", coupon);
            // 固定金额时：订单金额=原订单金额-固定金额
            if (1 == coupon.getDiscountMode()) {
                BigDecimal discountAmount = new BigDecimal(coupon.getDiscountAmount());
                log.info("固定金额优惠时：优惠金额:{}", discountAmount);
                orderNew.setOrderAmount(orderAmount.subtract(discountAmount));
            } else {
                // 折扣系数时：订单金额=原订金额-(原订单金额*折扣系数)
                BigDecimal rebateFactor = new BigDecimal(1 - (coupon.getRebateFactor() / 10));
                BigDecimal discountAmount = orderAmount.multiply(rebateFactor);
                log.info("折扣优惠时：优惠金额:{}", discountAmount);
                orderNew.setOrderAmount(orderAmount.subtract(discountAmount).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            }

            // 有使用优惠卷时，为“优惠卷编码”和“优惠卷使用时间”赋值
            orderNew.setCouponNo(couponUser.getReceiveNo());
            orderNew.setCouponUsedTime(new Date());
        }
    }
}
