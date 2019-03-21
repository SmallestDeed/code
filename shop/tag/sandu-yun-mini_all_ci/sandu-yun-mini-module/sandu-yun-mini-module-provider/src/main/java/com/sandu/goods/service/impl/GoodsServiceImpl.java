package com.sandu.goods.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.common.utils.DigitalUtils;
import com.sandu.goods.dao.GoodsDao;
import com.sandu.goods.model.query.GoodsQuery;
import com.sandu.goods.model.vo.GoodsVo;
import com.sandu.goods.service.GoodsService;
import com.sandu.matadata.Page;
import com.sandu.sys.model.vo.SysDictionaryVo;
import com.sandu.sys.service.SysDictionaryService;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{
	@Autowired
	private GoodsDao goodsDao; 
	 
	@Override
	public Page<GoodsVo> getList(GoodsQuery query) {
		Page<GoodsVo> page = new Page<GoodsVo>();
		long total = goodsDao.findFontCount(query);
		page.setCount(total);
		List<GoodsVo> lstVo = goodsDao.findFontPageList(query);
		if (lstVo != null && lstVo.size() > 0) {
			page.setList(lstVo);
		}
		return page;
	}

}
