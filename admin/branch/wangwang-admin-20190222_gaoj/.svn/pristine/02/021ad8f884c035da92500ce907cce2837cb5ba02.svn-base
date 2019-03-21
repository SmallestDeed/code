package com.sandu.api.goods.service.biz;

import com.sandu.api.goods.common.constant.GoodsTypeEnum;

import java.util.List;

public interface BaseGoodsBizService {
    /**
     * @Author: zhangchengda
     * @Description: 获取首页特卖/新品列表
     * @Date: 16:06 2018/7/21
     */
    List<Object> getMainPageGoodsList(GoodsTypeEnum goodsType, Integer companyId);

    /**
     * @Author: zhangchengda
     * @Description: 获取特卖/新品列表
     * @Date: 16:09 2018/7/21
     */
    List<Object> getBizGoodsList(GoodsTypeEnum goodsType, Integer companyId);

    /**
     * @Author: zhangchengda
     * @Description: 修改特卖/新品首页显示状态
     * @Date: 16:09 2018/7/21
     */
    Integer updateMainPageState(GoodsTypeEnum goodsType, Integer spuId, Integer state);
}
