package com.nork.pay.service;

import java.util.List;

import com.nork.pay.model.PayRefund;
import com.nork.pay.model.search.PayRefundSearch;

/**   
 * @Title: PayRefundService.java 
 * @Package com.nork.pay.service
 * @Description:支付-支付退款Service
 * @createAuthor pandajun 
 * @CreateDate 2016-09-22 14:41:47
 * @version V1.0   
 */
public interface PayRefundService {
	/**
	 * 新增数据
	 *
	 * @param payRefund
	 * @return  int 
	 */
	public int add(PayRefund payRefund);

	/**
	 *    更新数据
	 *
	 * @param payRefund
	 * @return  int 
	 */
	public int update(PayRefund payRefund);

	public int updateAuditState(PayRefund refund);
    
    public int updateRefundState(PayRefund refund);
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  PayRefund 
	 */
	public PayRefund get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  payRefund
	 * @return   List<PayRefund>
	 */
	public List<PayRefund> getList(PayRefund payRefund);
	
	public int findCount(PayRefundSearch payRefundSearch);

	/**
	 *    获取数据数量
	 *
	 * @param  payRefund
	 * @return   int
	 */
	public int getCount(PayRefundSearch payRefundSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  payRefund
	 * @return   List<PayRefund>
	 */
	public List<PayRefund> getPaginatedList(
				PayRefundSearch payRefundtSearch);

	/**
	 * 其他
	 * 
	 */
    public void syncPayOrder(String refundNo,String payState);
}
