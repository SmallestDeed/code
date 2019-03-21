package com.sandu.service.decoratecustomer.dao;

import com.sandu.api.decoratecustomer.model.DecoratePrice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * decorate
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-23 14:24
 */
@Repository
public interface DecoratePriceMapper {
	int insert(DecoratePrice decoratePrice);


	int updateByPrimaryKey(DecoratePrice decoratePrice);

	int deleteByPrimaryKey(@Param("decoratePriceIds") Set<Integer> decoratePriceIds);

	DecoratePrice selectByPrimaryKey(@Param("decoratePriceId") int decoratePriceId);

	List<DecoratePrice> listByCustomerIds(@Param("ids") Set<Integer> ids);

	List<Integer> listCompanyIdForDispatchPrice(@Param("proprietorInfoId") Integer proprietorInfoId, @Param("limit") int limit);

	List<Integer> findDispatchCustomerIds(@Param("ids") List<Integer> ids);

}
