package com.sandu.api.decorateorder.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.decorateorder.input.*;
import com.sandu.api.decorateorder.model.DecorateOrder;
import com.sandu.api.sysusermessage.model.SysUserMessage;
import com.sandu.api.user.model.User;
import com.sandu.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * decorate_order
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
public interface DecorateOrderBizService {

    /**
     * 创建
     *
     * @param input
     * @return
     */
    int create(DecorateOrderAdd input);

    /**
     * 更新
     *
     * @param input
     * @return
     */
    int update(DecorateOrderUpdate input);

    /**
     * 删除
     */
    int delete(String decorate_orderId);

    /**
     * 通过ID获取详情
     *
     * @param decorate_orderId
     * @return
     */
    DecorateOrder getById(int decorate_orderId);

    /**
     * 查询列表
     *
     * @param query
     * @return
     */
    PageInfo<DecorateOrder> query(DecorateOrderQuery query);

    /**
     * 合同确定上传
     * @param input
     * @param userId
     * @return
     */
    public int uploadContract(DecorateOrderUpdate input, Integer userId,Integer orderUserId);


	public SysUserMessage insertSysUserMessage(
			String title, String content, Integer createUserId, Integer businessId, Integer userId);
    /**
     * 更改财务收款状态
     * @param input
     * @param id
     * @return
     */
	int updateReceipt(DecorateOrderReceiptUpdate input, Integer id);

	/**
	 * 退款操作处理
	 * @param input
	 * @param id
	 * @return
	 */
	int updateRefund(DecorateOrderRefundUpdate input, Integer id,DecorateOrder order) throws ServiceException;


	/**
	 * 查询已签约列表信息
	 * @param query
	 * @return
	 */
	List<DecorateOrder> queryContractOrderList(DecorateOrderQuery query);

	/**
	 * 查询已退款列表导出
	 * @param query
	 * @return
	 */
	List<DecorateOrder> queryRefundOrderList(DecorateOrderQuery query);

	/**
	 * 根据公司扣费
	 *
	 * @param companyId
	 * @param price
	 * @return
	 */
	Map<User, Boolean> doDeductionWithCompany(Integer companyId, Integer price);

	DecorateOrder findOrderByPriceId(Integer id);
}
