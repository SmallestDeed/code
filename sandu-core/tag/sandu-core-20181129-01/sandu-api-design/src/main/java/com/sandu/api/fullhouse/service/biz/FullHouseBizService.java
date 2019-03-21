package com.sandu.api.fullhouse.service.biz;

import com.sandu.api.base.common.exception.BizExceptionEE;
import com.sandu.api.fullhouse.common.exception.BizException;
import com.sandu.api.fullhouse.input.*;
import com.sandu.api.fullhouse.output.*;

import java.util.List;

/**
 * created by zhangchengda
 * 2018/11/26 17:13
 * 全屋方案业务服务
 */
public interface FullHouseBizService {
    /**
     * created by zhangchengda
     * 2018/8/16 17:22
     * 查询任意空间类型下所有的方案风格
     *
     * @param query 查询参数
     * @return 该空间类型下的所有方案风格
     */
    List<DesignPlanStyleVO> selectDesignPlanStyle(DesignPlanStyleQuery query);

    /**
     * created by zhangchengda
     * 2018/8/17 10:52
     * 查询当前登录用户制作的符合查询条件的全屋方案
     *
     * @param query 查询参数
     * @return 全屋方案集合
     */
    List<FullHouseDesignPlanVO> selectFullHouseDesignPlan(FullHouseDesignPlanQuery query);

    /**
     * created by zhangchengda
     * 2018/8/17 11:32
     * 查询当前登录用户制作的符合查询条件的全屋方案总数
     *
     * @param query 查询参数
     * @return 全屋方案总数
     */
    Integer selectFullHouseDesignPlanCount(FullHouseDesignPlanQuery query);

    /**
     * created by zhangchengda
     * 2018/8/17 13:56
     * 制作全屋方案
     *
     * @param fullHouseDesignPlanAdd 制作全屋方案参数
     */
    String addFullHouseDesignPlan(FullHouseDesignPlanAdd fullHouseDesignPlanAdd);

    /**
     * created by zhangchengda
     * 2018/8/20 12:11
     * 更新全屋方案
     *
     * @param obj 更新参数
     * @throws BizException
     */
    String updateFullHouseDesignPlan(Object obj);

    /**
     * 获取全屋一件装修的详细装修信息(哪个推荐方案装进哪个样板房)
     *
     * @param houseId         户型id
     * @param fullHousePlanId 全屋装修方案id
     * @return
     * @throws BizExceptionEE
     * @author huangsongbo
     */
    List<MatchInfoVO> getMatchInfo(Integer houseId, Integer fullHousePlanId) throws BizExceptionEE;
}
