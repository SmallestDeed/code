package com.sandu.service.grouppurchase.dao;

import com.sandu.api.grouppurchase.model.MallBaseOrder;
import com.sandu.api.grouppurchase.model.MallOrderProductRef;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MallBaseOrderMapper {


	int deleteByPrimaryKey(Integer id);

	int insert(MallBaseOrder record);

	int insertSelective(MallBaseOrder record);

	MallBaseOrder selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(MallBaseOrder record);

	int updateByPrimaryKey(MallBaseOrder record);

	List<MallBaseOrder> dynamicQueryOrder(MallBaseOrder order);

	List<MallBaseOrder> getOrderDetail(MallBaseOrder order);

	List<MallBaseOrder> getOrderByOrderUserId(MallBaseOrder order);

	MallBaseOrder getOrderByOrderNo(String orderNo);

	Integer updateBaseOrderByOrderId(MallBaseOrder id);

	List<MallBaseOrder> selectByOrderIdList(List<Integer> idList);


	int updateOrderStatusByOrderList(MallBaseOrder order);

	int countUserOrder(@Param("userId") Integer userId, @Param("payStatus") int payStatus);

	int updateOrderAmount(@Param("id") Integer id, @Param("orderAmount") BigDecimal orderAmount);

	MallOrderProductRef getMallOrderProductBySkuId(Long skuId);

	void insertOrderProductRel(MallOrderProductRef mprf);
}