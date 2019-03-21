package com.sandu.service.fullhouse.impl;

import com.sandu.api.fullhouse.model.FullHouseDesignPlanDetail;
import com.sandu.api.fullhouse.output.DesignPlanVO;
import com.sandu.api.fullhouse.service.FullHouseDesignPlanDetailService;
import com.sandu.service.fullhouse.dao.FullHouseDesignPlanDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by zhangchengda
 * 2018/8/15 17:52
 * 全屋方案详情表单表查询服务
 */
@Service("fullHouseDesignPlanDetailService")
public class FullHouseDesignPlanDetailServiceImpl implements FullHouseDesignPlanDetailService {
    @Autowired
    private FullHouseDesignPlanDetailMapper fullHouseDesignPlanDetailMapper;

    /**
     * created by zhangchengda
     * 2018/8/15 17:55
     * 根据主键ID删除数据
     * @param id 全屋方案详情表id
     * @return 返回影响条数
     */
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return fullHouseDesignPlanDetailMapper.deleteByPrimaryKey(id);
    }

    /**
     * created by zhangchengda
     * 2018/8/15 17:57
     * 插入一条全屋方案详情数据
     * @param record 插入的数据
     * @return 返回影响条数
     */
    @Override
    public int insert(FullHouseDesignPlanDetail record) {
        return fullHouseDesignPlanDetailMapper.insert(record);
    }

    /**
     * created by zhangchengda
     * 2018/8/15 17:59
     * 动态插入一条全屋方案详情数据
     * @param record 插入的数据
     * @return 返回影响条数
     */
    @Override
    public int insertSelective(FullHouseDesignPlanDetail record) {
        return fullHouseDesignPlanDetailMapper.insertSelective(record);
    }

    /**
     * created by zhangchengda
     * 2018/8/15 17:59
     * 根据主键查询数据
     * @param id 全屋方案详情表id
     * @return 返回一行数据
     */
    @Override
    public FullHouseDesignPlanDetail selectByPrimaryKey(Integer id) {
        return fullHouseDesignPlanDetailMapper.selectByPrimaryKey(id);
    }

    /**
     * created by zhangchengda
     * 2018/8/15 18:00
     * 根据主键动态更新数据
     * @param record 更新的数据
     * @return 返回影响条数
     */
    @Override
    public int updateByPrimaryKeySelective(FullHouseDesignPlanDetail record) {
        return fullHouseDesignPlanDetailMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * created by zhangchengda
     * 2018/8/15 18:01
     * 根据主键更新数据
     * @param record 更新的数据
     * @return 返回影响条数
     */
    @Override
    public int updateByPrimaryKey(FullHouseDesignPlanDetail record) {
        return fullHouseDesignPlanDetailMapper.updateByPrimaryKey(record);
    }

    /**
     * created by zhangchengda
     * 2018/8/18 16:41
     * 插入多条数据
     * @param detailList 插入的数据集合
     * @return 返回插入条数
     */
    @Override
    public int insertList(List<FullHouseDesignPlanDetail> detailList) {
        return fullHouseDesignPlanDetailMapper.insertList(detailList);
    }

    /**
     * created by zhangchengda
     * 2018/8/18 19:11
     * 根据全屋方案ID逻辑删除全屋方案详情，is_deleted = 1
     * @param id 全屋方案ID
     * @return 影响条数
     */
    @Override
    public int deleteByFullHouseDesignPlanId(Integer id) {
        return fullHouseDesignPlanDetailMapper.logicDeleteByFullHouseDesignPlanId(id);
    }

    /**
     * created by zhangchengda
     * 2018/8/20 9:47
     * 根据全屋方案ID查询单空间方案列表
     * @param fullHouseId 全屋方案ID
     * @return 单空间方案列表
     */
    @Override
    public List<DesignPlanVO> selectSingleSpaceDesignPlanList(Integer fullHouseId) {
        return fullHouseDesignPlanDetailMapper.selectSingleSpaceDesignPlanList(fullHouseId);
    }

    /**
     * created by zhangchengda
     * 2018/8/20 17:02
     * 逻辑删除某个全屋方案下的所有方案详情数据
     *
     * @param fullHouseDesignPlanId 全屋方案ID
     * @return 影响条数
     */
    @Override
    public int logicDeletedByFullHouseDesignPlanId(Integer fullHouseDesignPlanId) {
        return fullHouseDesignPlanDetailMapper.logicDeletedByFullHouseDesignPlanId(fullHouseDesignPlanId);
    }
    /**
     * created by zhangchengda
     * 2018/8/20 17:25
     * 更新全屋方案详情数据，根据fullHouseDesignPlanId,spaceType,priorityLevel定位数据位置
     *
     * @param updateData 需要更新的数据
     * @return 返回影响条数
     */
    @Override
    public int updateDetail(FullHouseDesignPlanDetail updateData) {
        return fullHouseDesignPlanDetailMapper.updateDetail(updateData);
    }
}
