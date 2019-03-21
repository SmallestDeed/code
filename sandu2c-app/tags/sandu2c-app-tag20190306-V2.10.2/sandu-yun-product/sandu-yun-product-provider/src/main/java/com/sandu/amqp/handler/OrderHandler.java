package com.sandu.amqp.handler;

import com.sandu.amqp.RabbitService;
import com.sandu.amqp.model.OrderMessage;
import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.notify.wx.bo.TemplateMsgReqParam;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.constant.RabbitConstant;
import com.sandu.common.util.StringUtils;
import com.sandu.goods.model.BO.GoodsInfoToOrderBO;
import com.sandu.goods.service.BaseGoodsSKUService;
import com.sandu.goods.service.BaseGoodsSPUService;
import com.sandu.order.MallBaseOrder;
import com.sandu.order.OrderProduct;
import com.sandu.order.service.MallBaseOrderService;
import com.sandu.product.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OrderHandler {
    private final static String CLASS_LOG_PRE = "【订单消息处理】";

    @Autowired
    private MallBaseOrderService mallBaseOrderService;
    @Autowired
    private BaseGoodsSPUService baseGoodsSPUService;
    @Autowired
    private BaseGoodsSKUService baseGoodsSKUService;
    @Autowired
    private TemplateMsgService templateMsgService;
    @Autowired
    private RabbitService rabbitService;
    @Resource(name = "systemSysUserService")
    private SysUserService sysUserService;

    public void handler(String message) {
        if (StringUtils.isNotBlank(message)) {
            log.info(CLASS_LOG_PRE + "获取消息数据, message:{}", message);
            OrderMessage orderMessage = JsonUtil.fromJson(message, OrderMessage.class);
            if (orderMessage != null && orderMessage.getOrderId() != null && orderMessage.getAction() != null) {
                MallBaseOrder mallBaseOrder = new MallBaseOrder();
                mallBaseOrder.setId(orderMessage.getOrderId());
                mallBaseOrder = mallBaseOrderService.getOrderByOrderId(mallBaseOrder);
                SysUser sysUser = sysUserService.get(mallBaseOrder.getUserId());
                if (orderMessage.getAction() == 1) {
                    // 支付提醒
                    this.notice(orderMessage, mallBaseOrder, sysUser);
                } else if (orderMessage.getAction() == 2) {
                    // 取消订单
                    this.cancel(orderMessage, mallBaseOrder, sysUser);
                }
            }
        }
    }

    private void notice(OrderMessage orderMessage, MallBaseOrder mallBaseOrder, SysUser sysUser) {
        if (mallBaseOrder.getPayStatus() == 0) {
            try {
                Map<String, Map<String, String>> templateData = this.buildData(mallBaseOrder.getOrderCode(),
                        mallBaseOrder.getOrderAmount().toString(),
                        "待支付",
                        "未支付订单只能继续为你保存12小时，请尽快支付",
                        "点击查看详情，进入订单详情页完成支付");
                this.sendTemplateMessage(sysUser, 12, templateData, new Object[]{mallBaseOrder.getId()});
            } catch (Exception e) {
                log.info(CLASS_LOG_PRE + "发送模板消息失败!", e);
            }
            orderMessage.setAction(2);
            rabbitService.sendExpireMsg(RabbitConstant.ORDER_EXPIRE_EXCHANGE, RabbitConstant.ORDER_EXPIRE_ROUTING_KEY, orderMessage, 1000 * 60 * 60 * 12);
        }
    }

    private void cancel(OrderMessage orderMessage, MallBaseOrder mallBaseOrder, SysUser sysUser) {
        // 订单超时还没付款，自动取消订单，释放库存
        if (mallBaseOrder.getPayStatus() == 0) {
            mallBaseOrder.setOrderStatus(2);
            mallBaseOrder.setGmtModified(new Date());
            int i = mallBaseOrderService.updateOrderStatusById(mallBaseOrder);
            if (i > 0) {
                List<OrderProduct> productList = mallBaseOrder.getOrderProductList();
                productList.stream().forEach(orderProduct -> {
                    if (orderProduct.getProductId() != null && orderProduct.getProductNumber() != null) {
                        GoodsInfoToOrderBO goodsInfo = baseGoodsSPUService.getGoodsInfoToOrder(orderProduct.getProductId());
                        if (goodsInfo != null) {
                            baseGoodsSKUService.changeInventory(goodsInfo.getSkuId(), orderProduct.getProductNumber());
                            baseGoodsSPUService.updateTotalInventoryBySkuId(goodsInfo.getSkuId(), orderProduct.getProductNumber());
                        }
                    }
                });
                try {
                    Map<String, Map<String, String>> templateData = this.buildData(mallBaseOrder.getOrderCode(),
                            mallBaseOrder.getOrderAmount().toString(),
                            "已关闭",
                            "你可以重新下单，请在24小时内完成支付");
                    this.sendTemplateMessage(sysUser, 13, templateData, new Object[]{mallBaseOrder.getId()});
                } catch (Exception e) {
                    log.info(CLASS_LOG_PRE + "发送模板消息失败!", e);
                }
            }
        }
    }

    private void sendTemplateMessage(SysUser user, Integer msgType, Map<String, Map<String, String>> templateData, Object[] urlParams) {
        try {
            TemplateMsgReqParam param = templateMsgService.buildTemplateReqParam(user, msgType, templateData, urlParams);
            templateMsgService.sendTemplateMsg(param);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private Map<String, Map<String, String>> buildData(String... strings) {
        Map<String, Map<String, String>> map = new HashMap<>();
        for (int i = 1; i <= strings.length; i++) {
            Map<String, String> valueMap = new HashMap<>();
            String string = strings[i - 1];
            valueMap.put("value", string);
            map.put("keyword" + i, valueMap);
        }
        return map;
    }
}
