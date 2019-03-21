package com.sandu.goods.dao;


import com.sandu.goods.model.BO.GoodsBO;
import com.sandu.goods.model.BO.GoodsDetailBO;
import com.sandu.goods.model.BO.GoodsInfoToOrderBO;
import com.sandu.goods.model.BO.SpuAttributeBO;
import com.sandu.goods.model.BaseGoodsSPU;
import com.sandu.goods.model.PO.GoodsListPO;
import com.sandu.goods.output.ActivityVO;
import com.sandu.goods.output.GoodsDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseGoodsSPUMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BaseGoodsSPU record);

    int insertSelective(BaseGoodsSPU record);

    BaseGoodsSPU selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseGoodsSPU record);

    int updateByPrimaryKey(BaseGoodsSPU record);

    List<Integer> getGoodsSpuIdList(GoodsListPO goodsListPO);

    List<GoodsBO> getGoodsSpuList(List<Integer> ids);

    GoodsDetailBO getGoodsDetail(Integer id);

    List<SpuAttributeBO> getSpuAttrList(Integer id);

    GoodsInfoToOrderBO getGoodsInfoToOrder(Integer productId);

    Integer updateTotalInventoryBySkuId(@Param("id") Integer id, @Param("num") Integer num);

    List<GoodsDetailVO> getSpecialSaleGoods(@Param("list") List<Integer> specialSaleProductIdList);

    List<Integer> getSpuIdByProduct(@Param("list") List<String> productModelNumberList);

    List<GoodsDetailBO> getSpecialOfferGoods(GoodsListPO goodsListPO);

    List<GoodsDetailBO> getPresellGoods(GoodsListPO goodsListPO);

    List<ActivityVO> listGoodsActivity(List<GoodsBO> listGoodsBO);
}