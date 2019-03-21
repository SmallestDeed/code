package com.sandu.service.pointmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.pointmall.model.query.GiftQuery;
import com.sandu.api.pointmall.model.vo.GiftImageVo;
import com.sandu.api.pointmall.model.vo.GiftVo;

@Repository
public interface GiftImageDao extends CrudDao<GiftImageDao, GiftQuery, GiftVo> {

    GiftImageVo getGiftImageById(@Param("id") int id, @Param("giftId") int giftId, @Param("cover") int cover);

    List<String> getGiftImageList(@Param("giftId") int giftId);
}
