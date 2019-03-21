/**
 * 礼品服务接口
 *
 */
package com.sandu.api.pointmall.service;

import com.sandu.api.pointmall.model.ImallGiftInout;
import com.sandu.api.pointmall.model.vo.GiftSingleVo;
import com.sandu.api.pointmall.model.vo.GiftVo;


import java.util.List;

public interface GiftService {

    /**
     * 获取上架的礼品列表
     * @return
     */
    List<GiftVo> getGiftList();

    /**
     * 获取礼品(带对应的封面图)
     * @param giftId 礼品Id
     * @return
     */
    GiftVo getGiftById(int giftId);

    /**
     * 获取礼品(带对应的图片集合)
     * @param giftId 礼品Id
     * @return
     */
    GiftSingleVo getGiftAndImgsById(int giftId);

    /***
     * 更新库存
     * @param inventory
     * @param id
     * @return
     */
    int updateInventory(int inventory,int id);

    /***
     * 礼品卖出记录
     * @param record
     * @return
     */
    int insertGiftInoutDaoSelective(ImallGiftInout record);
}
