package com.sandu.order.service;

import java.util.List;

import com.sandu.common.model.ResponseEnvelope;
import com.sandu.order.MallBaseOrder;
import com.sandu.useraddress.MallUserAddress;


public interface MallBaseOrderService {

    int createOrder(MallBaseOrder mallBaseOrder);


	/**
     * 通过用户id获取订单列表
     * @param order
     * @return
     */
	List<MallBaseOrder> dynamicQueryOrder(MallBaseOrder order);

	/**
     * 通过订单id查询订单详细信息
     * @param order
     * @return
     */
	MallBaseOrder getOrderByOrderId(MallBaseOrder order);

	 /**
     * 通过用户id查询用户地址
     * @param userId
     * @return
     */
	List<MallUserAddress> getAddressByUserId(Integer userId);

	/**
	*通过地址Id获取地址信息
	* @author : WangHaiLin
	* @date 2018/11/17 14:24
	* @param: [addressId]
	* @return: com.sandu.useraddress.MallUserAddress
	*
	*/
	MallUserAddress getAddressById(Integer addressId);

	int addUserAddress(MallUserAddress mallUserAddress);

	int updateUserAddress(MallUserAddress mallUserAddress);

	/**
	 * 通过订单号查询订单详情
	 * @param orderNo
	 * @return
	 */
	MallBaseOrder getOrderByOrderNo(String orderNo);


	/**
	 * 通过订单ID修改订单状态
	 * @param id
	 * @return
	 */
	Integer updateBaseOrderByOrderId(Integer id);



	/**
	 * 回调服务,修改订单状态
	 * @param id
	 * @return
	 */
	Integer updateBaseOrderByOrderIdAndCallBackStatus(Integer orderId,String resultCode,String returnCode);


    List<MallBaseOrder> getOrderByOrderList(List<Integer> idList);


	ResponseEnvelope updateOrderStatusByOrderList(MallBaseOrder mallBaseOrder);

    int delUserAddress(Integer addressId);

    /**
     * 统计用户付款成功的订单信息
     * @param userId
     * @param i
     * @return
     */
	int countUserOrder(Integer userId, int i);
}
