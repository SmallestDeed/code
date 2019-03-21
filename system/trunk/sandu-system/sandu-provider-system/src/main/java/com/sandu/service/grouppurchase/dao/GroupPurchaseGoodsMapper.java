package com.sandu.service.grouppurchase.dao;


import com.sandu.api.grouppurchase.bo.*;
import com.sandu.api.grouppurchase.input.GoodsSkuQuery;
import com.sandu.api.grouppurchase.input.GroupPurchaseGatherQuery;
import com.sandu.api.grouppurchase.model.GroupPurchaseGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPurchaseGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupPurchaseGoods record);

    int insertSelective(GroupPurchaseGoods record);

    GroupPurchaseGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupPurchaseGoods record);

    int updateByPrimaryKey(GroupPurchaseGoods record);

    GroupPurchaseGoodsDetailBO getGoodsDetail(
            @Param("spuId") Long spuId,
            @Param("purchaseActivityId") Long purchaseActivityId);

    GroupGoodsActivityBO getGoodsActivity(
            @Param("purchaseActivityId") Long purchaseActivityId);

    List<SpuAttributeBO> getSpuAttrList(@Param("id") Long id,
                                        @Param("activityId") Long activityId);

    List<SkuPriceAndAttrBO> getSkuPriceAndAttr(@Param("id") Long id,
                                               @Param("activityId") Long activityId);

    List<GoodsSkuBO> getSkusBySpuId(GoodsSkuQuery query);

    Long countGather(GroupPurchaseGatherQuery query);

    List<GroupPurchaseGatherBO> listGather(GroupPurchaseGatherQuery query);

    List<GoodsSkuBO> getActivitySkuBySpuId(Integer spuId);

    List<GroupPurchaseGoods> listByOption(GroupPurchaseGoods groupPurchaseGoods);

    GoodsInfoToOrderBO getGoodsInfoToOrder(Integer productId);

    List<GroupPurchaseGoods> getSkusBySpuIdAndActivityId(@Param("purchaseActivityId") Integer purchaseActivityId,
                                                         @Param("spuId") Integer spuId);

    Integer removeByActivityId(@Param("purchaseActivityId") Long purchaseActivityId);

    List<PurchaseGoodSkuBO> getSkuInfoBySpuId(@Param("spuId") Long spuId, @Param("activeId") Long activeId);

    int isGroupOverflow(Long groupId);
}