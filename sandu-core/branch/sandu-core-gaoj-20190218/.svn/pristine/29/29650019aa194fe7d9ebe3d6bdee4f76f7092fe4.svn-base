package com.sandu.api.fullhouse.service;

import com.sandu.api.fullhouse.model.FullHouseDesignPlanDetail;
import com.sandu.api.fullhouse.output.DesignPlanVO;

import java.util.List;

/**
 * created by zhangchengda
 * 2018/8/15 17:52
 * 全屋方案详情表单表查询服务
 */
public interface FullHouseDesignPlanDetailService {
    /**
     * created by zhangchengda
     * 2018/8/15 17:55
     * 根据主键ID删除数据
     *
     * @param id 全屋方案详情表id
     * @return 返回影响条数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * created by zhangchengda
     * 2018/8/15 17:57
     * 插入一条全屋方案详情数据
     *
     * @param record 插入的数据
     * @return 返回影响条数
     */
    int insert(FullHouseDesignPlanDetail record);

    /**
     * created by zhangchengda
     * 2018/8/15 17:59
     * 动态插入一条全屋方案详情数据
     *
     * @param record 插入的数据
     * @return 返回影响条数
     */
    int insertSelective(FullHouseDesignPlanDetail record);

    /**
     * created by zhangchengda
     * 2018/8/15 17:59
     * 根据主键查询数据
     *
     * @param id 全屋方案详情表id
     * @return 返回一行数据
     */
    FullHouseDesignPlanDetail selectByPrimaryKey(Integer id);

    /**
     * created by zhangchengda
     * 2018/8/15 18:00
     * 根据主键动态更新数据
     *
     * @param record 更新的数据
     * @return 返回影响条数
     */
    int updateByPrimaryKeySelective(FullHouseDesignPlanDetail record);

    /**
     * created by zhangchengda
     * 2018/8/15 18:01
     * 根据主键更新数据
     *
     * @param record 更新的数据
     * @return 返回影响条数
     */
    int updateByPrimaryKey(FullHouseDesignPlanDetail record);

    /**
     * created by zhangchengda
     * 2018/8/18 16:41
     * 插入多条数据
     *
     * @param detailList 插入的数据集合
     * @return 返回插入条数
     */
    int insertList(List<FullHouseDesignPlanDetail> detailList);

    /**
     * created by zhangchengda
     * 2018/8/18 19:11
     * 根据全屋方案ID逻辑删除全屋方案详情，is_deleted = 1
     *
     * @param id 全屋方案ID
     * @return 影响条数
     */
    int deleteByFullHouseDesignPlanId(Integer id);

    /**
     * created by zhangchengda
     * 2018/8/20 9:47
     * 根据全屋方案ID查询单空间方案列表
     *
     * @param fullHouseId 全屋方案ID
     * @return 单空间方案列表
     */
    List<DesignPlanVO> selectSingleSpaceDesignPlanList(Integer fullHouseId);

    /**
     * created by zhangchengda
     * 2018/8/20 17:02
     * 逻辑删除某个全屋方案下的所有方案详情数据
     *
     * @param fullHouseDesignPlanId 全屋方案ID
     * @return 影响条数
     */
    int logicDeletedByFullHouseDesignPlanId(Integer fullHouseDesignPlanId);

    /**
     * created by zhangchengda
     * 2018/8/20 17:25
     * 更新全屋方案详情数据，根据fullHouseDesignPlanId,spaceType,priorityLevel定位数据位置
     *
     * @param updateData 需要更新的数据
     * @return 返回影响条数
     */
    int updateDetail(FullHouseDesignPlanDetail updateData);

    /**
     * created by zhangchengda
     * 2018/9/20 9:34
     * 查询全屋方案下的所有详情数据
     *
     * @param fullHouseId 全屋方案ID
     * @return 全屋方案详情数据集合
     */
    List<FullHouseDesignPlanDetail> selectListByFullHouseDesignPlanId(Integer fullHouseId);
}
