/**
 * 礼品服务接口
 *
 */
package com.sandu.api.pointmall.service;

import com.sandu.api.pointmall.model.vo.GiftImageVo;

import java.util.List;

public interface GiftImageService {

    /**
     * 获取礼品图片列表(仅获取图片名称List)
     * @return
     */
    List<String> getGiftImageList(int giftId);

    /**
     * 获取礼品图片(参数不用可为null)
     * @param id Id
     * @param giftId 礼品Id
     * @param cover 0不是封面1封面
     * @return
     */
    GiftImageVo getGiftImageById(int id, int giftId, int cover);
}
