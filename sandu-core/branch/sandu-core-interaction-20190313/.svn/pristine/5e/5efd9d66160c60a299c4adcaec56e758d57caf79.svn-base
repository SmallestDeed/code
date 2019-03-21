package com.sandu.api.fullhouse.service.biz;

import com.sandu.api.fullhouse.input.DesignPlanQuery;
import com.sandu.api.fullhouse.output.DesignPlanVO;
import com.sandu.api.fullhouse.output.FullHouseDesignPlanEditPageVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FullHousePCBizService extends FullHouseBizService {
    /**
     * created by zhangchengda
     * 2018/8/16 12:52
     * 查询用于全屋方案制作的组合方案主方案/一键方案（空间类型为厨房时）
     *
     * @param query 查询参数
     * @return 可选方案集合
     */
    List<DesignPlanVO> selectSingleDesignPlan(DesignPlanQuery query);

    /**
     * created by zhangcheng
     * 2018/8/16 16:18
     * 查询用于全屋方案制作的组合方案主方案/一键方案（空间类型为厨房时）总数
     *
     * @param query 查询参数
     * @return 方案总数
     */
    Integer selectSingleDesignPlanCount(DesignPlanQuery query);

    /**
     * created by zhangchengda
     * 2018/8/18 18:57
     * 删除全屋方案
     *
     * @param id 要删除的全屋方案ID
     */
    void deleteFullHouseDesignPlan(Integer id);

    /**
     * created by zhangchengda
     * 2018/8/20 9:38
     * 查询全屋方案更换方案时显示的单空间方案列表
     *
     * @param fullHouseId 全屋方案ID
     * @return 全屋方案更换方案页面的数据
     */
    FullHouseDesignPlanEditPageVO selectFullHouseDesignPlanDetailList(Integer fullHouseId);
}
