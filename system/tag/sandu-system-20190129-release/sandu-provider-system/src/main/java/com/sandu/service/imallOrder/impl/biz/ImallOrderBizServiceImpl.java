package com.sandu.service.imallOrder.impl.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.imallOrder.model.*;
import com.sandu.api.imallOrder.output.ImallOrderVO;
import com.sandu.api.imallOrder.service.ImallOrderBizService;
import com.sandu.api.imallOrder.service.ImallOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("imallOrderBizService")
@Slf4j
public class ImallOrderBizServiceImpl implements ImallOrderBizService {

    @Autowired
    private ImallOrderService imallOrderService;

    /**
     * 查询订单分页
     * @param query
     * @return
     */
    @Override
    public PageInfo<ImallOrderVO> queryList(ImallOrderPO query) {
        return imallOrderService.findAll(query);
    }

    /**
     * 订单明细
     * @param id
     * @return
     */
    @Override
    public ImallOrder getImallOrderById(int id) {
        return imallOrderService.getImallOrderById(id);
    }

    /**
     * 确认，减库存
     * @param orderId
     * @param modifier
     * @return
     */
    @Override
    public String affirm(int orderId, String modifier) {
        return imallOrderService.affirm(orderId,modifier);
    }


    @Override
    public String complete(int orderId, String modifier) {
        return imallOrderService.complete(orderId,modifier);
    }

    /**
     * 发货
     * @param orderId
     * @param expressCompay
     * @param expressNo
     * @param modifier
     * @return
     */
    @Override
    public String send(int orderId, String expressCompay, String expressNo, String modifier) {
        return imallOrderService.send(orderId,expressCompay,expressNo,modifier);
    }

    /**
     * 修改收货地址
     * @param model
     * @return
     */
    @Override
    public String updateAddress(ImallOrderAddress model) {
        return imallOrderService.updateAddress(model);
    }

    /**
     * 退积分
     * @param orderId
     * @param modifier
     * @return
     */
    @Override
    public String refundment(int orderId, String modifier) {
        return imallOrderService.refundment(orderId,modifier);
    }

    /**
     * 订单汇总状态数量
     * @return
     */
    @Override
    public List<Map<String,Integer>> collectStatusCount(){
        return imallOrderService.collectStatusCount();
    }
}
