package com.sandu.service.solution.dao;

import com.sandu.api.solution.model.DesignPlanBrand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * DesignPlanBrandMapper class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Repository
public interface DesignPlanBrandMapper {
    /**
     * 删除
     * @param id id
     * @return 1 0
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 添加
     * @param record record
     * @return 1, 0
     */
    int insertSelective(DesignPlanBrand record);

    /**
     * 单个
     * @param id id
     * @return result
     */
    DesignPlanBrand selectByPrimaryKey(Long id);

    /**
     * 更新
     * @param record record
     * @return 1 , 0
     */
    int updateByPrimaryKeySelective(DesignPlanBrand record);


    /**
     * 列表
     * @param record query
     * @return r
     */
    List<DesignPlanBrand> selectListSelective(DesignPlanBrand record);

    /**
     * 方案和公司id 删除关联品牌
     * @param planId 方案
     * @param companyId 公司
     * @return 1, 0
     */
    int deleteByPlanIdAndCompanyId(@Param("planId") Integer planId,@Param("companyId") Integer companyId);

    DesignPlanBrand getBrandInfo(@Param("planId") Integer planId, @Param("brandId") Integer brandId, @Param("companyId") Integer companyId);

    int removePlanBrand(@Param("planId") Integer planId);

    List<DesignPlanBrand> listByIds(@Param("planIds") List<Long> planIds);

	List<DesignPlanBrand> listByIdsCompany(@Param("planIds")List<Long> planIds, @Param("companyId")Integer companyId);

    List<DesignPlanBrand> queryProblemData();

    int updateByPlanId(DesignPlanBrand designPlanBrand);

    /**
     * 修复数据
     * @param planIds
     * @return
     */
    int fixByPlanIdAndCompanyId(@Param("planIds") List<Integer> planIds);
}