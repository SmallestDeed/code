package com.sandu.service.pointmall.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.pointmall.model.query.GiftQuery;
import com.sandu.api.pointmall.model.vo.GiftSingleVo;
import com.sandu.api.pointmall.model.vo.GiftVo;

@Repository("GiftDao2")
public interface GiftDao  extends CrudDao<GiftDao,GiftQuery,GiftVo> {
    GiftVo  get(@Param("giftId") int giftId);

    GiftSingleVo getGiftAndImgsById(@Param("giftId") int giftId);

    int updateInventory(@Param("inventory") int inventory,@Param("id") int id);
}
