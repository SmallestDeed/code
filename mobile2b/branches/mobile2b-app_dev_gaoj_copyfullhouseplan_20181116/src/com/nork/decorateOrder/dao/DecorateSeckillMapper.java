package com.nork.decorateOrder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nork.decorateOrder.model.DecorateSeckill;
import com.nork.decorateOrder.model.search.DecorateSeckillSearch;

@Repository
public interface DecorateSeckillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DecorateSeckill record);

    int insertSelective(DecorateSeckill record);

    DecorateSeckill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DecorateSeckill record);

    int updateByPrimaryKey(DecorateSeckill record);

	DecorateSeckill selectDetailsById(@Param("id") Long id);

	List<DecorateSeckill> selectListBySearch(DecorateSeckillSearch decorateSeckillSearch);

	int selectCountBySearch(DecorateSeckillSearch decorateSeckillSearch);

	int updateByIdAndStatus(@Param("decorateSeckill") DecorateSeckill decorateSeckill, @Param("whereId")Long id, @Param("whereStatus") Integer status);

}