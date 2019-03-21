package com.nork.decorateOrder.service;

import java.util.List;

import com.nork.decorateOrder.model.output.NotBeenSelectedOrderVO;
import com.nork.decorateOrder.model.search.DecorateOrderSearch;
import com.nork.system.model.SysUser;
import com.nork.decorateOrder.model.DecorateOrder;
import com.nork.decorateOrder.model.DecoratePrice;
import com.nork.decorateOrder.model.DecorateSeckillOrder;
import com.nork.decorateOrder.model.input.DecorateCustomerListQuery;
import com.nork.decorateOrder.model.output.DecorateCustomerDetail;
import com.nork.decorateOrder.model.output.DecorateCustomerVO;

public interface DecorateOrderService {

	/**
	 * 装企首页: 我的客户数量
	 * 
	 * @author huangsongbo
	 * @param userId
	 * @return
	 */
	int getMyClientCount(Long userId);

	/**
	 * getCount公共方法
	 * 
	 * @author huangsongbo
	 * @param decorateOrderSearch
	 * @return
	 */
	int getCount(DecorateOrderSearch decorateOrderSearch);

    /**
     * 按条件，查询我的客户数量
	 * @author : WangHaiLin
     * @param query 查询条件
     * @return int 满足条件的客户数量
     */
    int getCount (DecorateCustomerListQuery query);

    /**
     * 查询我的客户列表
	 * @author : WangHaiLin
     * @param query 查询入参
     * @return list 满足条件的客户列表
     */
     List<DecorateCustomerVO> getList(DecorateCustomerListQuery query);


    /**
     * 查看客户订单详情
	 * @author : WangHaiLin
     * @param orderId 订单Id
     * @return
     */
     DecorateCustomerDetail getCustomerDetail(Integer orderId);

     /**
     * @description 修改客户信息
     * @author : WangHaiLin
     * @date : 2018/10/19 15:31
     * @param update 入参
     * @return boolean
     *
     */
     boolean updateOrder(DecorateOrder update);

     /**
      * 根据decorateSeckill数据添加DecorateOrder信息
      * 
      * @author huangsongbo
      * @param decorateSeckill
      * @param sysUser
     * @return 
      */
     DecorateOrder add(DecorateSeckillOrder decorateSeckillorder, SysUser sysUser);
     
     /**
      * add decorateOrder
      * 
      * @author huangsongbo
      * @param decorateOrder
      * @return
      */
	 Long add(DecorateOrder decorateOrder);

	 /**
	  * 根据decoratePrice数据添加DecorateOrder信息
	  * @param decoratePrice
	 * @return 
	  */
	 DecorateOrder add(DecoratePrice decoratePrice, SysUser sysUser);

	 /**
	  * 获取平台派单订单的支付状态
	  * 
	  * @author huangsongbo
	  * @param orderId
	  * @return
	  */
	 DecorateOrder findOneByPriceId(Long orderId);

	 /**
	  * 获取限时快抢订单的支付状态
	  * 
	  * @param decorateSeckillId 限时快抢id
	  * @return
	  */
	DecorateOrder findOneBySeckillId(Long decorateSeckillId);

	/**
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	DecorateOrder get(Long id);

	/**
	 * 变更订单状态(来源于平台派单)
	 * 
	 * @param orderStatus
	 * @param decoratePriceId
	 * @return 
	 */
	int updateOrderStatusByDecoratePriceId(Integer orderStatus, Long decoratePriceId);

	int updateOrderStatusByDecorateSeckillId(Integer orderStatus,
			Long decorateSeckillId);

	/**
	 * 更新id = #{id} 的装修订单(decorate_order) 的状态(order_status)
	 * 
	 * @author huangsongbo
	 * @param orderStatus
	 * @param id
	 * @return 
	 */
	int updateOrderStatusById(Integer orderStatus, Long id);

	/**
	 * 获取未支付的bean信息(显示快抢订单id + 支付超时时间)List
	 * 
	 * @author huangsongbo
	 * @param decorateOrderSearch
	 * @return
	 */
	List<DecorateOrder> getDecorateSeckillIdAndOrderOverTimeList(DecorateOrderSearch decorateOrderSearch);

	/**
	 * 对超时订单更新/做对应处理
	 * 1.更新decorateOrder状态为"支付超时"
	 * 2.如果是限时抢单的订单,则copy一条限时抢单的数据重新加入抢单
	 * 
	 * @author huangsongbo
	 */
	void updateOverTimeOrder();

	/**
	 * 获取所有的超时订单信息
	 * 
	 * @author huangsongbo
	 * @return
	 */
	List<DecorateOrder> getOverTimeOrderList();


	/**
	* 查询未被选中的装企订单信息
	* @author : WangHaiLin
	* @date : 2018/11/14 16:28
	* @param customerId 客户Id
	* @return java.util.List<com.nork.decorateOrder.model.output.NotBeenSelectedOrderVO>
	*
	*/
	List<NotBeenSelectedOrderVO> getNotBeenSelectedOrder(Long customerId);

}
