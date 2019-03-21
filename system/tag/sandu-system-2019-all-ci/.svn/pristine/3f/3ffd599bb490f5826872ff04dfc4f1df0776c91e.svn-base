package com.sandu.api.gift.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.gift.input.GiftAdd;
import com.sandu.api.gift.input.GiftQuery;
import com.sandu.api.gift.input.GiftUpdate;
import com.sandu.api.gift.model.Gift;
import com.sandu.api.gift.model.GiftImage;


import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/28 17:40
 */
public interface GiftBizService {
    /**
     * 创建新节点内容
     *
     * @param input
     * @return
     */
    int create(GiftAdd input);

    /**
     * 更新节点内容
     *
     * @param input
     * @return
     */
    int update(GiftUpdate input);

    /**
     * 修改上架状态
     * @param ids
     * @param putaway
     * @return
     */
    int updatePutaway(String ids, int putaway,String modifier);

    /**
     * 删除
     */
    int delete(String id,String modifier);

    /**
     * 通过ID获取详情
     *
     * @param id
     * @return
     */
    Gift getDetailById(int id);

    /**
     * 查询列表
     *
     * @param query
     * @return
     */
    PageInfo<Gift> queryList(GiftQuery query);

    /**
     * 插入图片
     * @param model
     * @return
     */
    int createGiftImage(GiftImage model);

    /**
     * 删除图片
     * @param id
     * @return
     */
    int deleteGiftImageById(int id,String modifier);

    /**
     * 获取图片
     * @return
     */
    List<GiftImage> getGiftImages(int id);

    /**
     * 修改封面
     * @return
     */
    int updateCover(int id,int giftId,String modifier);

    /**
     * 修改缩略图
     * @return
     */
    int updateSmall(int id,int giftId,String modifier);
}
