package com.sandu.api.fullhouse.service;

import java.util.List;

import com.sandu.api.base.common.exception.BizExceptionEE;
import com.sandu.api.designTemplet.model.DesignTemplet;
import com.sandu.api.designplan.model.DesignPlanRecommended;
import com.sandu.api.fullhouse.input.FullHouseDesignPlanQuery;
import com.sandu.api.fullhouse.model.FullHouseDesignPlan;
import com.sandu.api.fullhouse.output.FullHouseDesignPlanVO;
import com.sandu.api.fullhouse.output.MatchInfoVO;

/**
 * created by zhangchengda
 * 2018/8/15 18:28
 * 全屋方案表单表查询服务
 */
public interface FullHouseDesignPlanService {
    /**
     * created by zhangchengda
     * 2018/8/15 18:28
     * 根据主键ID删除数据
     * @param id 全屋方案表id
     * @return 返回影响条数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * created by zhangchengda
     * 2018/8/15 18:28
     * 插入一条全屋方案
     * @param record 插入的数据
     * @return 返回影响条数
     */
    int insert(FullHouseDesignPlan record);

    /**
     * created by zhangchengda
     * 2018/8/15 18:29
     * 动态插入一条全屋方案
     * @param record 插入的数据
     * @return 返回影响条数
     */
    int insertSelective(FullHouseDesignPlan record);

    /**
     * created by zhangchengda
     * 2018/8/15 18:32
     * 根据主键查询数据
     * @param id 全屋方案表id
     * @return 返回一行数据
     */
    FullHouseDesignPlan selectByPrimaryKey(Integer id);

    /**
     * created by zhangchengda
     * 2018/8/15 18:33
     * 根据主键动态更新数据
     * @param record 更新的数据
     * @return 返回影响条数
     */
    int updateByPrimaryKeySelective(FullHouseDesignPlan record);

    /**
     * created by zhangchengda
     * 2018/8/15 18:33
     * 根据主键更新数据
     * @param record 更新的数据
     * @return 返回影响条数
     */
    int updateByPrimaryKey(FullHouseDesignPlan record);

    /**
     * 获取全屋一件装修的详细装修信息(哪个推荐方案装进哪个样板房)
     * 
     * @author huangsongbo
     * @param houseId 户型id
     * @param fullHousePlanId 全屋装修方案id
     * @return
     * @throws BizExceptionEE 
     */
	List<MatchInfoVO> getMatchInfo(Integer houseId, Integer fullHousePlanId) throws BizExceptionEE;

	/**
	 * 全屋匹配,输入样板房list(同户型)和推荐方案list(同全屋装修包),得到匹配信息(样板房id对应推荐方案id)
	 * 
	 * @author huangsongbo
	 * @param designTempletList
	 * @param designPlanRecommendList
	 * @return
	 * @throws BizExceptionEE 
	 */
	List<MatchInfoVO> getMatchInfo(List<DesignTemplet> designTempletList,
			List<DesignPlanRecommended> designPlanRecommendList) throws BizExceptionEE;

    /**
     * created by zhangchengda
     * 2018/8/17 11:05
     * 查询当前登录用户制作的符合查询条件的全屋方案
     * @param query 查询参数
     * @return 全屋方案集合
     */
    List<FullHouseDesignPlanVO> selectFullHouseDesignPlan(FullHouseDesignPlanQuery query);

    /**
     * created by zhangchengda
     * 2018/8/17 11:05
     * 查询当前登录用户制作的符合查询条件的全屋方案总数
     * @param query 查询参数
     * @return 全屋方案总数
     */
    Integer selectFullHouseDesignPlanCount(FullHouseDesignPlanQuery query);

    /**
     * created by zhangchengda
     * 2018/8/18 14:14
     * 通过UUID查询全屋方案
     * @param uuid 全屋方案的UUID
     * @return 全屋方案
     */
    FullHouseDesignPlan selectFullHouseDesignPlanByUuid(String uuid);

    /**
     * created by zhangchengda
     * 2018/8/18 19:06
     * 逻辑删除全屋方案，is_deleted = 1
     * @param id 将要删除的全屋方案ID
     * @return 返回影响条数
     */
    int deleteFullHouseDesignPlan(Integer id);
}
