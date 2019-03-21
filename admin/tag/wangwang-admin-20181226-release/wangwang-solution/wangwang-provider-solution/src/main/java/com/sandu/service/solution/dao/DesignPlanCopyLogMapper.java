package com.sandu.service.solution.dao;

import com.sandu.api.solution.model.DesignPlanCopyLog;
import com.sandu.api.solution.model.bo.DesignPlanDeliverBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DesignPlanCopyLogMapper class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Repository
public interface DesignPlanCopyLogMapper {
    /**
     * delete
     *
     * @param id id
     * @return 1,  0
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * inset
     *
     * @param record record
     * @return 1,  0
     */
    int insertSelective(DesignPlanCopyLog record);

    /**
     * get one
     *
     * @param id id
     * @return one
     */
    DesignPlanCopyLog selectByPrimaryKey(Integer id);

    /**
     * list
     *
     * @return list
     */
    List<DesignPlanCopyLog> selectListSelective();

    /**
     * 根据目标id删除
     *
     * @param targetId 目标
     * @param kind     目标类型
     */
    void deleteByPlanId(@Param("planId") long targetId, @Param("kind") byte kind);

    /**
     * 根据源目标删除
     *
     * @param planId planid
     * @param kind   kind
     */
    void deleteBySourceId(@Param("sourceId") long planId, @Param("kind") byte kind, @Param("planType") Integer planType);

    /**
     * 获取某方案交付过的公司
     *
     * @param deliverCompanyId 交付公司
     * @param planId           方案id
     * @return 交付过的公司
     */
    @Select("select target_company_id from design_plan_copy_log t " +
            "  where t.source_company_id = #{deliverCompanyId} and t.source_id = #{planId} and kind = #{kind} and plan_type = #{planType}")
    List<Integer> listCopiedCompanys(@Param("deliverCompanyId") Integer deliverCompanyId, @Param("planId") Integer planId, @Param("kind") Byte kind,@Param("planType") Integer planType);

    /**
     * 获取方案的交付信息
     *
     * @param planId 方案
     * @return 交付信息
     */
    @Select("SELECT " +
            "  dpdl.target_company_id as receiveCompanyId," +
            "  bc.company_name AS receiveCompanyName," +
            "  dpdl.gmt_create AS receiveDate" +
            " FROM design_plan_copy_log dpdl" +
            "  LEFT JOIN base_company bc ON bc.id = dpdl.target_company_id" +
            " WHERE dpdl.source_id = #{planId} and kind = #{kind}")
    List<DesignPlanDeliverBO> listDesignPlanCopiedLog(@Param("planId") Integer planId, @Param("kind") Byte kind);


    /**
     * 通过targetcompnay和source 和kind删除
     *
     * @param deliveredCId 交付的公司
     * @param designPlanId 方案
     * @param kind         种类
     */
    void deleteByTargetCompanyAndSource(@Param("targetCompanyId") Integer deliveredCId, @Param("designPlanId") Integer designPlanId, @Param("kind") Byte kind, @Param("planType") Integer planType);

    @Select("select target_id from design_plan_copy_log where source_id = #{designPlanId} and kind = #{kind} and plan_type = #{planType}")
    List<Integer> listDeliveredPlanIds(@Param("designPlanId") Integer deliveredCId, @Param("kind") Byte kind, @Param("planType") Integer planType);

    List<Integer> listDeliveredPlanId(@Param("cid") Integer deliveredCId, @Param("designPlanId") Integer designPlanId, @Param("kind") Byte kind, @Param("planType") Integer planType);

    List<DesignPlanCopyLog> queryLog(DesignPlanCopyLog query);

    int removeLog(@Param("targetId") Integer targetId,@Param("planType") Integer planType);

    List<DesignPlanCopyLog> getDeliverDetail(@Param("planIds") List<Integer> planIds, @Param("companyId") Integer companyId);

    List<DesignPlanCopyLog> getCopyLogBySource(@Param("sourceId") Integer id, @Param("companyId") Integer companyId);

    int deleteByPlanIdAndCompanyId(@Param("planIds") List<Integer> planIds);
}