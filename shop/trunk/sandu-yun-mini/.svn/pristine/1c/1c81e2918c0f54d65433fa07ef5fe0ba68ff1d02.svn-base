package com.sandu.company.dao;

import org.springframework.stereotype.Repository;

import com.sandu.common.persistence.CrudDao;
import com.sandu.company.model.CompanyShopActivity;
import com.sandu.company.model.query.ShopActivityQuery;
import com.sandu.company.model.vo.ShopActivityDetailVo;
import com.sandu.company.model.vo.ShopActivityVo;

@Repository
public interface ShopActivityDao extends CrudDao<CompanyShopActivity,ShopActivityQuery,ShopActivityVo>{
	ShopActivityDetailVo getDetail(long id);
}
