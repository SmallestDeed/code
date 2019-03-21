package com.sandu.service.decoratecustomer.dao;


import com.sandu.api.decoratecustomer.model.DecorateSeckill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DecorateSeckillMapper {
	int deleteByPrimaryKey(Long id);

	int insert(DecorateSeckill record);

	int insertSelective(DecorateSeckill record);

	DecorateSeckill selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(DecorateSeckill record);

	int updateByPrimaryKey(DecorateSeckill record);


	List<Integer> findSedKillCustomerIds(@Param("ids") List<Integer> ids);

	List<DecorateSeckill> findSedKillByCustomerIds(@Param("ids") List<Integer> ids);
}