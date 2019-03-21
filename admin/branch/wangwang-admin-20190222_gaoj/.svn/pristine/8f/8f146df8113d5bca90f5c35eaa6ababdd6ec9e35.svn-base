package com.sandu.service.solution.dao;

import com.sandu.api.solution.model.DesignPlanDeliveryLog;
import com.sandu.api.solution.model.bo.DesignPlanDeliverBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * DesignPlanDeliveryLogMapper class
 *
 * @author bvvy
 * @date 2018/04/02
 */
public interface DesignPlanDeliveryLogMapper {

    /**
     * delete
     *
     * @param id id
     * @return 1, 0
     */
    int deleteByPrimaryKey(Long id);

    /**
     * inset
     *
     * @param record record
     * @return 1, 9
     */
    int insertSelective(DesignPlanDeliveryLog record);

    /**
     * get one
     *
     * @param id id
     * @return one
     */
    DesignPlanDeliveryLog selectByPrimaryKey(Long id);

    /**
     * update
     *
     * @param record record
     * @return 1, 0
     */
    int updateByPrimaryKeySelective(DesignPlanDeliveryLog record);


    /**
     * 获取全部
     *
     * @param designPlanDeliveryLog query
     * @return list
     */
    List<DesignPlanDeliveryLog> selectListSelective(DesignPlanDeliveryLog designPlanDeliveryLog);

    /**
     * 通过交付人删除
     *
     * @param delivererId    交付人
     * @param designPlanType 方案类型
     */
    void deleteByDeliverId(@Param("delivererId") Integer delivererId, @Param("designPlanType") Integer designPlanType);

    /**
     * 获取某人的交付
     *
     * @param delivererId    人
     * @param designPlanType 方案
     * @return list
     */
    List<DesignPlanDeliveryLog> listByDelivererId(@Param("delivererId") Integer delivererId, @Param("designPlanType") Integer designPlanType);


    /**
     * 获取某方案交付过的公司
     *
     * @param deliverCompanyId 交付公司
     * @param planId           方案id
     * @return 交付过的公司
     */
    @Select("select receive_company_id from design_plan_delivery_log t where t.delivery_company_id = #{deliverCompanyId} and t.design_plan_id = #{planId}")
    List<Integer> listDeliveredCompanys(@Param("deliverCompanyId") Integer deliverCompanyId, @Param("planId") Integer planId);

    /**
     * 获取方案的交付信息
     *
     * @param planId 方案
     * @return 交付信息
     */
    @Select("SELECT " +
            "  dpdl.receive_company_id," +
            "  bc.company_name AS receiveCompanyName," +
            "  dpdl.gmt_create AS receiveDate" +
            " FROM design_plan_delivery_log dpdl" +
            "  LEFT JOIN base_company bc ON bc.id = dpdl.receive_company_id" +
            " WHERE dpdl.design_plan_id = #{planId}")
    List<DesignPlanDeliverBO> listDesignPlanDeliveredLog(@Param("planId") Integer planId);

    void deleteByTargetCompanyAndSource(@Param("receiveCompanyId") Integer deliveredCId, @Param("designPlanId") Integer designPlanId);

}