package com.sandu.service.base.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface BaseBrandDao {
   
	/**
	 * 通过品牌名称查出id
	 * @param brandName
	 * @return
	 */
	int getIdByBrandName(String brandName);
}
