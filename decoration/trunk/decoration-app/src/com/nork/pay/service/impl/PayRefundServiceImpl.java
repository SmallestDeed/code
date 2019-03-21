package com.nork.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.pay.dao.PayOrderMapper;
import com.nork.pay.dao.PayRefundMapper;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.PayRefund;
import com.nork.pay.model.search.PayRefundSearch;
import com.nork.pay.service.PayRefundService;
import com.nork.sandu.model.dto.TRefund;

/**   
 * @Title: PayRefundServiceImpl.java 
 * @Package com.nork.pay.service.impl
 * @Description:支付-支付退款ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-09-22 14:41:47
 * @version V1.0   
 */
@Service("payRefundService")
public class PayRefundServiceImpl implements PayRefundService {
	private PayOrderMapper payOrderMapper;
	private PayRefundMapper payRefundMapper;

	@Autowired
	public void setPayRefundMapper(PayOrderMapper payOrderMapper,PayRefundMapper payRefundMapper) {
		this.payOrderMapper = payOrderMapper;
		this.payRefundMapper = payRefundMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param payRefund
	 * @return  int 
	 */
	@Override
	public int add(PayRefund payRefund) {
		payRefundMapper.insertSelective(payRefund);
		return payRefund.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param payRefund
	 * @return  int 
	 */
	@Override
	public int update(PayRefund payRefund) {
		return payRefundMapper
				.updateByPrimaryKeySelective(payRefund);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return payRefundMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  PayRefund 
	 */
	@Override
	public PayRefund get(Integer id) {
		return payRefundMapper.selectByPrimaryKey(id);
	}

	public List<TRefund> findList(PayRefundSearch payRefundSearch){
		return payRefundMapper.findList(payRefundSearch);
	}
	/**
	 * 所有数据
	 * 
	 * @param  payRefund
	 * @return   List<PayRefund>
	 */
	@Override
	public List<PayRefund> getList(PayRefund payRefund) {
	    return payRefundMapper.selectList(payRefund);
	}
	
	public int findCount(PayRefundSearch payRefundSearch){
		return payRefundMapper.findCount(payRefundSearch);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  payRefund
	 * @return   int
	 */
	@Override
	public int getCount(PayRefundSearch payRefundSearch){
		return  payRefundMapper.selectCount(payRefundSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  payRefund
	 * @return   List<PayRefund>
	 */
	@Override
	public List<PayRefund> getPaginatedList(
			PayRefundSearch payRefundSearch) {
		return payRefundMapper.selectPaginatedList(payRefundSearch);
	}

	@Override
	public int updateAuditState(PayRefund refund) {
		// TODO Auto-generated method stub
		return payRefundMapper.updateAuditState(refund);
	}

	@Override
	public int updateRefundState(PayRefund refund) {
		// TODO Auto-generated method stub
		return payRefundMapper.updateRefundState(refund);
	}

	@Override
	public void syncPayOrder(String refundNo,String payState) {
		// TODO Auto-generated method stub
		PayRefund payRefund=new PayRefund();
		payRefund.setRefundNo(refundNo);
		List<PayRefund> lstRefund=getList(payRefund);
		if(lstRefund!=null && lstRefund.size()>0){
			PayRefund refund=lstRefund.get(0);
			PayOrder order=new PayOrder();
			order.setId(refund.getOrderId());
			order.setPayState(payState);
			payOrderMapper.updateByPrimaryKeySelective(order);
		}
	}

	/**
	 * 其他
	 * 
	 */


}
