package com.sandu.goods.dao;

import java.util.List;
import com.sandu.common.persistence.CrudDao;
import com.sandu.goods.model.Goods;
import com.sandu.goods.model.query.GoodsQuery;
import com.sandu.goods.model.vo.GoodsVo;

public interface GoodsDao extends CrudDao<Goods, GoodsQuery, GoodsVo>{
}
