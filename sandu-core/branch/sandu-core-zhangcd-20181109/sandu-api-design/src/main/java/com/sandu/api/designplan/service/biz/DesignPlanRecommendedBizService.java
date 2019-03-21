package com.sandu.api.designplan.service.biz;

import com.sandu.api.fullhouse.input.DesignPlanQuery;
import com.sandu.api.fullhouse.output.DesignPlanVO;

import java.util.List;

/**
 * created by zhangchengda
 * 2018/8/16 13:57
 * 推荐方案业务服务
 */
public interface DesignPlanRecommendedBizService {
    /**
     * created by zhangchengda
     * 2018/8/16 14:00
     * 查询当前登录用户的已审核通过的方案组合的主方案（空间类型为厨房时则查询一键方案），按方案创建时间降序排列
     * @param query 查询参数
     * @return 返回方案的集合
     */
    List<DesignPlanVO> selectGroupPrimaryDesignPlan(DesignPlanQuery query);
    /**
     * created by zhangchengda
     * 2018/8/16 16:02
     * 查询当前登录用户的已审核通过的方案组合的主方案（空间类型为厨房时则查询一键方案）总数
     * @param query 查询参数
     * @return 返回方案的总数
     */
    Integer selectGroupPrimaryDesignPlanCount(DesignPlanQuery query);
}
