package com.sandu.service.gift.dao;

import com.sandu.api.gift.model.Gift;
import com.sandu.api.gift.model.GiftImage;
import com.sandu.api.gift.model.GiftInout;
import com.sandu.api.gift.model.GiftPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author djc
 * @datetime 2018/6/23 18:41
 */
@Repository
public interface GiftDao {

    /**
     * 插入
     *
     * @param model
     * @return
     */
    int insert(Gift model);

    /**
     * 更新
     *
     * @param model
     * @return
     */
    int update(Gift model);

    /**
     * 修改上架状态
     * @param ids
     * @param isPutaway
     * @return
     */
    int updatePutaway(@Param(value="ids") Set<Integer> ids,@Param(value="isPutaway") int isPutaway,@Param(value="modifier") String modifier);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    int delete(@Param("ids") Set<Integer> ids,@Param(value="modifier") String modifier);

    /**
     * 通过ID获取详情
     *
     * @param id
     * @return
     */
    Gift getById(int id);

    /**
     * 查询列表
     *
     * @param query
     * @return
     */
    List<Gift> findAll(GiftPO query);

    /**
     * 插入图片信息
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
    int deleteGiftImageById(@Param("id") int id,@Param(value="modifier") String modifier);

    /**
     * 修改封面
     * @return
     */
    int updateCover(@Param("id") int id,@Param(value="modifier") String modifier);

    int updateCoverDefault(@Param("giftId") int giftId,@Param(value="modifier") String modifier);

    /**
     * 修改缩略图
     * @return
     */
    int updateSmall(@Param("id") int id,@Param(value="modifier") String modifier);

    int updateSmallDefault(@Param("giftId") int giftId,@Param(value="modifier") String modifier);

    /**
     * 记录礼品日志
     * @param model
     * @return
     */
    int addImallGiftInout(GiftInout model);

    /**
     * 更新礼品库存
     * @param id
     * @param count
     * @return
     */
    int addGiftInventory(@Param("id") int id,@Param("count") int count);

    /**
     * 查询code最大的礼品
     * @param code code前缀
     * @return code
     */
    String getGiftByCode(String code);
}
