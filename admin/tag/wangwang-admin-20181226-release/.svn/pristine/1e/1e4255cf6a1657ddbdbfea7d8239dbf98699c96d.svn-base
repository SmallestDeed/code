package com.sandu.service.goods.dao;

import com.sandu.api.goods.model.bo.BizGoodsBO;
import com.sandu.api.goods.model.po.BizGoodsQueryPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseGoodsBizMapper {

    List<BizGoodsBO> getMainPageGoodsBOList(BizGoodsQueryPO bizGoodsQueryPO);

    Integer updateMainPageState(@Param("type") Integer type, @Param("spuId")Integer spuId, @Param("state")Integer state);
}
