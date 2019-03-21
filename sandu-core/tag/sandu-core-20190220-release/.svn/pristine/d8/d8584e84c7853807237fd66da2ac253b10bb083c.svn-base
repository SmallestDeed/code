package com.sandu.api.designplan.service.biz;

import com.sandu.api.fullhouse.input.DesignPlanQuery;
import com.sandu.api.fullhouse.output.DesignPlanVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * created by zhangchengda
 * 2018/8/16 13:57
 * 推荐方案业务服务
 */
@Component
public interface DesignPlanRecommendedBizService {
    /**
     * created by zhangchengda
     * 2018/8/16 14:00
     * 查询当前登录用户的已审核通过的方案组合的主方案（空间类型为厨房时则查询一键方案），按方案创建时间降序排列
     *
     * @param query 查询参数
     * @return 返回方案的集合
     */
    List<DesignPlanVO> selectGroupPrimaryDesignPlan(DesignPlanQuery query);

    /**
     * created by zhangchengda
     * 2018/8/16 16:02
     * 查询当前登录用户的已审核通过的方案组合的主方案（空间类型为厨房时则查询一键方案）总数
     *
     * @param query 查询参数
     * @return 返回方案的总数
     */
    Integer selectGroupPrimaryDesignPlanCount(DesignPlanQuery query);

    /**
     * created by zhangchengda
     * 2018/11/29 19:13
     * 获取所有的推荐方案ID
     *
     * @return
     */
    List<Integer> getAllRecommendedId();

    /**
     * created by zhangchengda
     * 2018/11/30 10:08
     * 增加node_info_detail表的value
     *
     * @param nodeId
     * @param detailType
     * @param num
     * @return
     */
    Integer increase(Integer nodeId, Byte detailType, Integer num);

    /**
     * created by zhangchengda
     * 2018/11/30 14:40
     * 获取所有的全屋方案ID
     *
     * @return
     */
    List<Integer> getAllFullHouseId();

    /**
     * 获取所有置顶的推荐方案
     * @return
     */
    List<Integer> getAllSuperiorRecommendedId();

    /**
     * 获取所有置顶的全屋方案
     * @return
     */
    List<Integer> getAllSuperiorFullHouseId();

    List<Integer> getShopIdListByNameList(List<String> nameList);

    List<Integer> getShopIdList(List<Integer> idList);
}
