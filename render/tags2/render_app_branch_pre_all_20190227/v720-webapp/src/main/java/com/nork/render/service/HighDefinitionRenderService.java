package com.nork.render.service;

import com.nork.common.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.render.model.input.RenderPayInput;
import com.nork.render.model.vo.RenderPayVo;

/**
 * @ClassName HighDefinitionRenderService
 * @Description 付费渲染订单 Service
 * @Author chenm
 * @Date 2019/1/15 17:35
 * @Version 1.0
 **/
public interface HighDefinitionRenderService {


    RenderPayVo getHDRenderPayInfo(RenderPayInput payInput, LoginUser loginUser) throws BizException;

    /**
     * 返回高清渲染任务不同支付方式付费信息
     * @author: chenm
     * @date: 2019/1/15 17:40
     * @param payInput
     * @return: com.nork.pay.model.out.RenderPayVo
     */
    RenderPayVo getRenderPayPriceInfo(RenderPayInput payInput, LoginUser loginUser) throws BizException;

    /**
     * 生成高清渲染支付凭证,得到订单号
     * @author: chenm
     * @date: 2019/1/15 19:37
     * @param renderPaySearch
     * @param renderPayVo
     * @param loginUser
     * @return: java.lang.String
     */
    String getOrderNoByRenderOrder(RenderPayInput renderPaySearch, RenderPayVo renderPayVo, LoginUser loginUser);

    /**
     * 使用度币支付高清渲染
     * @author: chenm
     * @date: 2019/1/15 17:41
     * @param payInput
     * @return: boolean
     */
    boolean saveRenderOrderWithDuBi(RenderPayInput payInput, LoginUser loginUser) throws BizException;

    /**
     * 修改订单状态(成功/失败)
     * @author: chenm
     * @date: 2019/1/15 17:41
     * @param orderNo 订单号
     * @param payState 支付状态
     * @param payTradeNo 支付网关生成的交易号
     * @param info 支付结果描述
     * @return: int
     */
    int updatePayOrderState(String orderNo,String payState,String payTradeNo,String info);

    /**
     * 创建高清渲染任务并回填pay_order
     * @author: chenm
     * @date: 2019/1/18 16:02
     * @param orderNo
     * @return: boolean
     */
    boolean  createHDRenderTaskWithOrder(String orderNo , String platfromCode);
    /**
     * 发送支付专业渲染费用成功的消息
     * @author: chenm
     * @date: 2019/2/15 16:34
     * @param intenalTradeNo
     * @return: void
     */
    void sendMessageForCreateHDRenderTask(String intenalTradeNo) throws BizException;

    /**
     * 渲染失败时记录失败原因并退款
     * @author: chenm
     * @date: 2019/2/25 14:37
     * @param taskState
     * @return: void
     */
    void recordHDRendertaskFailInfo(AutoRenderTaskState taskState) throws BizException;

}
