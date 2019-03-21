package com.sandu.api.designplan.service;

import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.common.exception.BizExceptionEE;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.designplan.common.objectconvert.DesignPlanRecommend;
import com.sandu.api.designplan.input.PlanRecommendedQuery;
import com.sandu.api.designplan.model.DesignPlanRecommended;
import com.sandu.api.designplan.output.DesignPlanRecommendedVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by kono on 2018/5/31 0031.
 */
@Component
public interface DesignPlanRecommendedService {

    /**
     * 微信小程序方案推荐列表数据
     *
     * @param Query
     * @return
     */
    ResponseEnvelope getPlanRecommendedList(PlanRecommendedQuery Query, String companyCode, BaseCompany baseCompany);

    /**
     * 推荐方案总数
     * @param designPlanRecommended
     * @return
     */
    Integer getPlanRecommendedCount(DesignPlanRecommended designPlanRecommended);

    /**
     * 推荐方案列表
     * @param designPlanRecommended
     * @return
     */
    List<DesignPlanRecommendedVo> getPlanRecommendedList(DesignPlanRecommended designPlanRecommended);

    /**
     * 根据不同条件获取方案数量
     * @param query
     * @param companyCode
     * @param baseCompany
     * @return
     */
    Integer recommendedPlanCount(PlanRecommendedQuery query, String companyCode, BaseCompany baseCompany);

	List<DesignPlanRecommended> getListByFullHouseId(Integer fullHousePlanId) throws BizExceptionEE;

	Map<Integer, List<DesignPlanRecommended>> getDesignPlanRecommendedMap(
			List<DesignPlanRecommended> designPlanRecommendList) throws BizExceptionEE;

	/**
	 * 方案包一键装修样板房,选择最适配的推荐方案
	 * 选择最适配的逻辑:
	 * 面积相等/面积最接近(小于)
	 * 
	 * @author huangsongbo
	 * @param areaValue 样板房的空间面积
	 * @param groupPrimaryId 推荐方案包主方案id
	 * @return 最适配的推荐方案id
	 * @throws BizExceptionEE 
	 */
	Integer getMatchId(Integer areaValue, Integer groupPrimaryId) throws BizExceptionEE;

	List<DesignPlanRecommended> getListByGroupPrimaryId(Integer groupPrimaryId) throws BizExceptionEE;

    /**
     * created by zhangchengda
     * 2018/8/17 14:57
     * 通过主键查询推荐方案
     * @param id 主键
     * @return 返回推荐方案
     */
    DesignPlanRecommended selectByPrimaryKey(Integer id);
}
