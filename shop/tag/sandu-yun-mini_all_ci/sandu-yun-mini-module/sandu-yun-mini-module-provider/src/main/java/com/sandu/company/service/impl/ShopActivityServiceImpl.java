package com.sandu.company.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.company.dao.ShopActivityDao;
import com.sandu.company.model.query.ShopActivityQuery;
import com.sandu.company.model.vo.ShopActivityDetailVo;
import com.sandu.company.model.vo.ShopActivityVo;
import com.sandu.company.service.ShopActivityService;
import com.sandu.matadata.Page;

@Service("shopActivityService")
@Transactional(readOnly = true)
public class ShopActivityServiceImpl implements ShopActivityService {
	private static final Logger logger = LoggerFactory.getLogger(CompanyShopServiceImpl.class.getName());
    @Autowired
    private ShopActivityDao shopActivityDao;
	@Override
	public ShopActivityDetailVo get(long shopId) {
		ShopActivityDetailVo detailVo = shopActivityDao.getDetail(shopId);
		return detailVo;
	}

	@Override
	public Page<ShopActivityVo> list(ShopActivityQuery query) {
		Page<ShopActivityVo> page=new Page<ShopActivityVo>();
		long count=shopActivityDao.findFontCount(query);
		List<ShopActivityVo> lstVo=shopActivityDao.findFontPageList(query);
		if(lstVo!=null && lstVo.size()>0) {
			page.setList(lstVo);
		}
		page.setCount(count);
		return page;
	}

}
