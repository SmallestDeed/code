package com.sandu.service.pointmall.dao;

import org.springframework.stereotype.Repository;

import com.sandu.api.pointmall.model.ImallGiftInout;
import com.sandu.api.pointmall.model.query.GiftQuery;
import com.sandu.api.pointmall.model.vo.GiftVo;

@Repository
public interface GiftInoutDao extends CrudDao<GiftInoutDao,GiftQuery,GiftVo> {
   int insertGiftInoutDaoSelective(ImallGiftInout record);
}
