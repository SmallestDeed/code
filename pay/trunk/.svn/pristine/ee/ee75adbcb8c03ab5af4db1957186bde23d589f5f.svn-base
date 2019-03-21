package com.sandu.common.objectconvert.payOrder;

import com.sandu.common.util.UtilDate;
import com.sandu.pay.order.metadata.BizType;
import com.sandu.pay.order.metadata.PayType;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.PayProductConstans;
import com.sandu.pay.order.vo.ExpenseRecordVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 20171018
 * @desc 消费记录对象
 * @auth xiaoxc
 */
public class ExpenseRecordObject {

    /**
     * 消费记录列表，过滤无用值
     *
     * @param payOrderList 个人用户消费记录list
     * @return
     */
    public static List<ExpenseRecordVo> parseToExpenseRecordVoByPayOrder(List<PayOrder> payOrderList) {

        List<ExpenseRecordVo> expenseRecordVoList = null;
        if (null != payOrderList) {
            //初始化对象
            expenseRecordVoList = new ArrayList<>(payOrderList.size());
            ExpenseRecordVo expenseRecordVo = null;
            for (PayOrder payOrder : payOrderList) {
                expenseRecordVo = new ExpenseRecordVo();
                //装入数据
                expenseRecordVo.setProductName(payOrder.getProductName());
                expenseRecordVo.setOrderOn(payOrder.getOrderNo());
                expenseRecordVo.setOrderDate(payOrder.getOrderDate() == null? "":UtilDate.ConverToString(payOrder.getOrderDate()));
                expenseRecordVo.setCurrentBalance(Double.valueOf(payOrder.getCurrentIntegral()) / 10);
                expenseRecordVo.setFinanceType(payOrder.getFinanceType());
                expenseRecordVo.setPlanName(payOrder.getPlanName());
                if ("mobile_postpone".equals(payOrder.getProductType()) &&
                        (PayType.ALIPAY.equals(payOrder.getPayType()) || PayType.WXPAY.equals(payOrder.getPayType()))) {
                    // 开通移动端
                    // 单位应为元
                    expenseRecordVo.setTotalFee(Double.valueOf(payOrder.getTotalFee()) / 100);
                } else if (PayProductConstans.RENDER_PRODUCT_TYPE.equals(payOrder.getProductType()) || PayProductConstans.RENDER_FRANCHISER.equals(payOrder.getProductType())) {
                    //购买包年包月
                    // 单位应为元
                    expenseRecordVo.setTotalFee(Double.valueOf(payOrder.getTotalFee()) / 100);
                } else {
                    // 单位应为分
                    expenseRecordVo.setTotalFee(Double.valueOf(payOrder.getTotalFee()) / 10 + Double.valueOf(payOrder.getAdjustFee()) / 10);
                }
                expenseRecordVo.setProductType(payOrder.getProductType()); // 产品类型
                expenseRecordVo.setPayType(payOrder.getPayType()); // 支付类型
                expenseRecordVoList.add(expenseRecordVo);
            }
        }
        return expenseRecordVoList;
    }
}
