package com.nork.design.controller.web;

import com.nork.common.util.Utils;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.RenderFailPayOrder;
import com.nork.pay.model.common.PayProductConstans;
import com.nork.pay.service.PayOrderService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xiaoxc on 2018/3/2 0002.
 */
@Controller
@RequestMapping("/{style}/web/design/autoRender")
public class RenderAutoRefundController {

    private static Logger logger = Logger.getLogger(WebDesignPlanAutoRenderController.class);

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private SysUserService sysUserService;


    public void renderFailureAutoRefund() {
        logger.info("开始执行渲染失败自动退款！");
        //查询渲染失败的数据（支付成功，保存图片失败）
        Integer times = Integer.parseInt(Utils.getValue("render.fail.auto.refund.times","30").trim());
        List<RenderFailPayOrder> renderFailList = payOrderService.findRenderFailList(times);
        logger.info("渲染失败数据条数："+ renderFailList==null?0:renderFailList.size());
        for (RenderFailPayOrder renderFail : renderFailList) {
            PayOrder payOrder = payOrderService.get(renderFail.getId());
            if (payOrder == null || payOrder.getId() == null) {
                continue;
            }
            // 退款更新账户余额
            SysUser sysUser = new SysUser();
            sysUser.setId(payOrder.getUserId());
            Double totalFee = new Double(payOrder.getTotalFee());// 渲染扣除金额（分）
            sysUser.setBalanceAmount(totalFee/10);
//            sysUser.setConsumAmount(-totalFee/10);
            sysUserService.updateFinance(sysUser);
            logger.info("退款账户金额完成,待创建退款订单！");
            PayOrder refundPayOrder = new PayOrder();
            refundPayOrder.setProductType(PayProductConstans.PAY_PRODUCT_TYPE);
            refundPayOrder.setProductId(payOrder.getId());
            refundPayOrder.setProductName("");
        }

    }




}
