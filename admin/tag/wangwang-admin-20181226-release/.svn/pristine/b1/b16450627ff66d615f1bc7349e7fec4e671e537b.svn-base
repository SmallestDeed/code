package com.sandu.service.goods.dao;

import com.sandu.api.goods.input.GoodsSPUAdd;
import com.sandu.api.goods.model.po.GoodsListQueryPO;
import com.sandu.api.goods.model.BaseGoodsSPU;
import com.sandu.api.goods.output.GoodsDetailVO;
import com.sandu.api.goods.output.GoodsTypeVO;
import com.sandu.api.goods.output.GoodsVO;
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

    List<GoodsVO> getGoodsList(GoodsListQueryPO goodsListQueryPO);

    Integer goodsPutawayUp(List<Integer> ids);

    Integer goodsPutawayDown(List<Integer> ids);

    List<GoodsTypeVO> getGoodsType(Integer companyId);

    GoodsDetailVO getGoodsDetail(Integer id);

    Integer getTotalCount(GoodsListQueryPO goodsListQueryPO);

    List<BaseGoodsSPU> getByIds(List<Integer> ids);

    Integer updateSpu(GoodsSPUAdd goodsSPUAdd);

    Integer updateSpuSaleInfo(GoodsSPUAdd goodsSPUAdd);

    Integer insertSpuSaleInfo(GoodsSPUAdd goodsSPUAdd);
}