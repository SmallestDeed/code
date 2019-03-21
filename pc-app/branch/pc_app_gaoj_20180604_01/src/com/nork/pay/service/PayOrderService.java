package com.nork.pay.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.LoginUser;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.RenderFailPayOrder;
import com.nork.pay.model.ResultMessage;
import com.nork.pay.model.search.PayOrderSearch;
import com.nork.sandu.model.dto.TOrder;
import com.nork.task.model.SysTask;

/**   
 * @Title: PayOrderService.java 
 * @Package com.nork.pay.service
 * @Description:支付-支出凭证Service
 * @createAuthor pandajun 
 * @CreateDate 2016-09-19 16:17:40
 * @version V1.0   
 */
public interface PayOrderService {
	/**
	 * 新增数据
	 *
	 * @param payOrder
	 * @return  int 
	 */
	public int add(PayOrder payOrder);

	/**
	 *    更新数据
	 *
	 * @param payOrder
	 * @return  int 
	 */
	public int update(PayOrder payOrder);
    
    public int updatePayState(PayOrder order);
	
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
	 * @return  PayOrder 
	 */
	public PayOrder get(Integer id);
	
	public PayOrder get(String orderNo);

	/**
	 * 所有数据
	 * 
	 * @param  payOrder
	 * @return   List<PayOrder>
	 */
	public List<PayOrder> getList(PayOrder payOrder);

	public int findCount(PayOrderSearch payOrderSearch);
	/**
	 *    获取数据数量
	 *
	 * @param  payOrder
	 * @return   int
	 */
	public int getCount(PayOrderSearch payOrderSearch);

	public List<TOrder> findList(PayOrderSearch payOrderSearch);
	
	/**
	 *    分页获取数据
	 *
	 * @param  payOrder
	 * @return   List<PayOrder>
	 */
	public List<PayOrder> getPaginatedList(
				PayOrderSearch payOrdertSearch);

	/**
	 * 其他
	 * 
	 */

	/**
	 * 给客户端发送订单支付状态信息
	 * @param orderNo
	 * @return
	 */
	public boolean sendPayState(String orderNo);
	
	/**
	 * 5.6版本:给客户端发送订单支付状态信息
	 * @param orderNo
	 * @return
	 */
	public boolean sendPayStateNew(String orderNo);

	public ResultMessage sendMessageAndCreateOrder(Integer productId, String productType, String productName,
			String productDesc, String payType, Integer userId, String msgId, HttpServletRequest request,
			ResultMessage message, String level, Integer isTurnOn, Integer planId, String params, Integer priority,
			Integer viewPoint, Integer scene, Integer renderingType, String temporaryPic, String type, Integer mode, Integer renderChanne);
	
	/**
	 * 5.6版本渲染创建任务和支付二维码
	 * @param productId
	 * @param productType
	 * @param productName
	 * @param productDesc
	 * @param payType
	 * @param userId
	 * @param msgId
	 * @param request
	 * @param message
	 * @param level
	 * @param isTurnOn
	 * @param planId
	 * @param priority
	 * @param viewPoint
	 * @param scene
	 * @param renderingType
	 * @param temporaryPic
	 * @param type
	 * @param mode
	 * @return
	 */
	public ResultMessage sendMessageAndCreateOrderNew(Integer productId, String productType, String productName,
			String productDesc, String payType, Integer userId, String msgId,
			ResultMessage message, String level, Integer isTurnOn, Integer planId,Integer priority,
			Integer viewPoint, Integer scene, Integer renderingType, String temporaryPic, String type, Integer mode,LoginUser loginUser);

	/**
	 * 通过orderNo查找PayOrder
	 * @author huangsongbo
	 * @param orderNo
	 * @return
	 */
	public PayOrder findOneByOrderNo(String orderNo);

	/**
	 * 得到未支付订单的数量，自动过滤取最近5分钟
	 * @author huangsongbo
	 * @param id
	 * @param paying
	 * @return
	 */
	public int getCountByUserIdAndStatus(Integer userId, String status);

	/**
	 * 通过taskId查找PayOrder
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public PayOrder findOneByTaskId(Integer id);

	/**
	 * 获取支付二维码信息
	 * @author yanghz
	 * @param loginUser 
	 * @param productDesc 
	 * @param productName 
	 * @param productType 
	 * @param productId 
	 * @param payType 
	 * @param totalFee 
	 * @param msgId 
	 * @return
	 */
	public ResultMessage getPayScanOrderUrlInfo(Integer totalFee, String payType, Integer productId, String productType, String productName, String productDesc, LoginUser loginUser, String msgId);
	
	/**
	 * 5.6版本：渲染失败退款到账户余额
	 * @param sysTask
	 */
	public void renderRefund(SysTask sysTask,String renderErroMsg);

	/**
	 * add by yanghz
	 * 通过订单号获取该用户的消费总金额和用户id
	 * @param orderNum
	 * @return
	 */
    PayOrder getConsumeSumByOrderNum(String orderNum);

	/**
	 * add by xiaoxc
	 * 查询支付成功渲染失败的数据
	 * @return
	 */
	List<RenderFailPayOrder> findRenderFailList(Integer times);
}
