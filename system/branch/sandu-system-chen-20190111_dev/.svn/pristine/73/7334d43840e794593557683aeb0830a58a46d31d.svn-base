package com.sandu.api.banner.service;

import com.sandu.api.banner.input.BaseBannerPositionAdd;
import com.sandu.api.banner.input.BaseBannerPositionIsExist;
import com.sandu.api.banner.input.BaseBannerPositionUpdate;
import com.sandu.api.banner.model.BaseBannerPosition;
import com.sandu.commons.LoginUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 广告-位置 Service
 * @author WangHaiLin
 * @date 2018/5/16  14:24
 */
@Component
public interface BaseBannerPositionService {
    /**
     * 添加广告位置
     * @param positionAdd 添加广告位置入参
     * @return int 新添加数据Id
     */
    int addBannerPosition(BaseBannerPositionAdd positionAdd, LoginUser loginUser);

    /**
     * 删除位置信息
     * @param positionId 位置Id
     * @return int 删除操作结果
     */
    int deletePosition(Integer positionId,LoginUser loginUser);

    /**
     * 修改位置信息
     * @param positionUpdate 修改操作入参
     * @return int 修改操作结果
     */
    int updateBannerPosition(BaseBannerPositionUpdate positionUpdate,LoginUser loginUser);

    /**
     * 查询位置是否存在(code 和 type 确保位置的唯一性)
     * @param isExist 查询操作入参
     * @return BaseBannerPosition 查询返回结果
     */
    BaseBannerPosition getPositionIsExist(BaseBannerPositionIsExist isExist);

    /**
     * 通过位置Id查询位置信息
     * @param positionId 位置Id
     * @return BaseBannerPosition
     */
    BaseBannerPosition getPositionById(Integer positionId);

    /**
     *通过位置类型查询位置信息列表
     * @param type 位置类型
     * @return List<BaseBannerPosition> 位置信息集合
     */
    List<BaseBannerPosition> getPositionListByType(Integer type);



}
