package com.sandu.common.objectconvert.payOrder;

import com.sandu.common.util.UtilDate;
import com.sandu.pay.order.model.PayOrder;
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
                expenseRecordVo.setOrderDate(UtilDate.ConverToString(payOrder.getOrderDate()));
                expenseRecordVo.setTotalFee(payOrder.getTotalFee()/10 + payOrder.getAdjustFee()/10);
                expenseRecordVo.setCurrentBalance(payOrder.getCurrentIntegral()/10);
                expenseRecordVo.setFinanceType(payOrder.getFinanceType());
                expenseRecordVo.setPlanName(payOrder.getPlanName());
                expenseRecordVoList.add(expenseRecordVo);
            }
        }
        return expenseRecordVoList;
    }
}
