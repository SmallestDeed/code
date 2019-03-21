package com.sandu.order.service;

import com.sandu.order.MallBaseOrder;
import com.sandu.useraddress.MallUserAddress;

import java.util.List;


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

	int addUserAddress(MallUserAddress mallUserAddress);

	int updateUserAddress(MallUserAddress mallUserAddress);

	/**
	 * 通过订单号查询订单详情
	 * @param orderNo
	 * @return
	 */
	MallBaseOrder getOrderByOrderNo(String orderNo);


	Integer updateBaseOrderByOrderId(Integer id);

	/**
	 * 回调服务,修改订单状态
	 * @param id
	 * @return
	 */
	Integer updateBaseOrderByOrderIdAndCallBackStatus(Integer orderId,String resultCode,String returnCode);





}
