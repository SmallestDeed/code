package com.sandu.service.banner.dao;

import com.sandu.api.banner.input.BaseBannerPositionIsExist;
import com.sandu.api.banner.model.BaseBannerPosition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 广告-位置 Dao层接口
 * @author WangHaiLin
 * @date 2018/5/16  14:46
 */
@Repository
public interface BaseBannerPositionMapper {
    /**
     * 添加广告位置
     * @param position 添加广告位置入参
     * @return int 新添加数据Id
     */
    int insertBannerPosition(BaseBannerPosition position);

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
    BaseBannerPosition getPositionById(@Param("positionId") Integer positionId);

    /**
     *通过位置类型查询位置信息列表
     * @param type 位置类型
     * @return List<BaseBannerPosition> 位置信息集合
     */
    List<BaseBannerPosition> getPositionListByType(@Param("type") Integer type);

    /**
     * 修改位置信息
     * @param position 修改操作入参
     * @return int 修改操作结果
     */
    int updateBannerPosition(BaseBannerPosition position);

    /**
     * 删除位置信息
     * @param position 删除的位置
     * @return int 删除操作结果
     */
    int deletePosition(BaseBannerPosition position);
}
