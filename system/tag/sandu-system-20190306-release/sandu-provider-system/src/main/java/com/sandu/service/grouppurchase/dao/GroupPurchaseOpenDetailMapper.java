package com.sandu.service.grouppurchase.dao;

import com.sandu.api.grouppurchase.bo.UserPurchaseListBO;
import com.sandu.api.grouppurchase.input.GroupPurchaseOpenQuery;
import com.sandu.api.grouppurchase.model.GroupPurchaseOpenDetail;
import com.sandu.api.user.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GroupPurchaseOpenDetailMapper {
	int deleteByPrimaryKey(Long id);

	int insert(GroupPurchaseOpenDetail record);

	int insertSelective(GroupPurchaseOpenDetail record);

	GroupPurchaseOpenDetail selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(GroupPurchaseOpenDetail record);

	int updateByPrimaryKey(GroupPurchaseOpenDetail record);

	List<UserPurchaseListBO> bizQuery(GroupPurchaseOpenQuery query);

    List<GroupPurchaseOpenDetail> getOpenDetailByOpenId(GroupPurchaseOpenQuery query);

	GroupPurchaseOpenDetail getByOrderId(@Param("payTradeNo") String payTradeNo);

	List<GroupPurchaseOpenDetail> listByPurchaseOpenIds(@Param("purchaseOpenIds") List<Long> purchaseOpenIds);

	List<GroupPurchaseOpenDetail> listByGroupPurchaseOpenIds(@Param("failedOpenIds") Set<Long> failedOpenIds);

	List<GroupPurchaseOpenDetail> checkOpenDetailsWithUserIdAndOpenId(@Param("userId") Long userId, @Param("purchaseActivityId") Long purchaseActivityId);

    List<SysUser> listGroupUserDetail(List<Long> listUserID);
}