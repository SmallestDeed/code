package com.sandu.service.grouppurchase.dao;

import com.sandu.api.grouppurchase.model.ActivityCoupon;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityCouponMapper {
	int deleteByPrimaryKey(Long id);

	int insert(ActivityCoupon record);

	int insertSelective(ActivityCoupon record);

	ActivityCoupon selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ActivityCoupon record);

	int updateByPrimaryKey(ActivityCoupon record);
}