package com.sandu.api.goods.service;

import com.sandu.api.goods.input.GoodsListQuery;
import com.sandu.api.goods.input.GoodsSPUAdd;
import com.sandu.api.goods.model.bo.PutAwayBO;
import com.sandu.api.goods.model.BaseGoodsSPU;
import com.sandu.api.goods.output.GoodsDetailVO;
import com.sandu.api.goods.output.GoodsTypeVO;
import com.sandu.api.goods.output.GoodsVO;
import com.sandu.common.ReturnData;

import java.util.List;

public interface BaseGoodsSPUService
{
    /**
     * @Author: zhangchengda
     * @Description: 通过ID查询商品信息
     * @Date: 16:54 2018/5/25
     */
    BaseGoodsSPU selectByPrimaryKey(Integer id);

    /**
     * @Author: zhangchengda
     * @Description: 获取商品列表
     * @Date: 11:32 2018/5/23
     */
    List<GoodsVO> getGoodsList(GoodsListQuery goodsListQuery);

    /**
     * @Author: zhangchengda
     * @Description: 商品批量上架
     * @Date: 11:33 2018/5/23
     */
    Integer goodsPutawayUp(PutAwayBO putAwayBO);

    /**
     * @Author: zhangchengda
     * @Description: 商品批量下架
     * @Date: 11:36 2018/5/23
     */
    Integer goodsPutawayDown(List<Integer> ids);

    /**
     * @Author: zhangchengda
     * @Description: 获取商品详情
     * @Date: 12:49 2018/5/23
     */
    GoodsDetailVO getGoodsInfo(Integer id);

    /**
     * @Author: zhangchengda
     * @Description: 更新商品信息
     * @Date: 16:23 2018/5/23
     */
    ReturnData updateGoods(GoodsSPUAdd goodsSPUAdd);

    /**
     * @Author: zhangchengda
     * @Description: 查询企业有的分类
     * @Date: 16:35 2018/5/23
     */
    List<GoodsTypeVO> getGoodsType(Integer companyId);

    /**
     * @Author: zhangchengda
     * @Description: 查询商品总条数
     * @Date: 20:28 2018/5/28
     */
    Integer totalCount(GoodsListQuery goodsListQuery);

    /**
     * @Author: zhangchengda
     * @Description: 查询单条数据
     * @Date: 15:52 2018/8/3
     */
    BaseGoodsSPU get(Integer id);
}
