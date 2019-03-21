package com.sandu.api.gift.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.gift.model.Gift;
import com.sandu.api.gift.model.GiftImage;
import com.sandu.api.gift.model.GiftPO;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/26 15:49
 */
public interface GiftService {

    /**
     * 插入
     *
     * @param gift
     * @return
     */
    int insert(Gift gift);

    /**
     * 更新
     *
     * @param gift
     * @return
     */
    int update(Gift gift);

    /**
     * 修改上架状态
     * @param ids
     * @param putaway
     * @return
     */
    int updatePutaway(Set<Integer> ids,int putaway,String modifier);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    int delete(Set<Integer> ids,String modifier);

    /**
     * 通过ID获取节点内容详情
     *
     * @param id
     * @return
     */
    Gift getById(int id);

    /**
     * 查询节点内容列表
     *
     * @return
     */
    PageInfo<Gift> findAll(GiftPO query);

    /**
     * 插入图片
     * @param model
     * @return
     */
    int insertGiftImage(GiftImage model);

    /**
     * 获取图片
     * @return
     */
    List<GiftImage> getGiftImages(int id);

    /**
     * 删除图片
     * @param id
     * @return
     */
    int deleteGiftImageById(int id,String modifier);

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

    /**
     * 通过礼品code查询礼品，获取code最大的礼品
     * @param code code 前缀
     * @return code
     */
    String getGiftByCode(String code);

}
