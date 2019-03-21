package com.sandu.designplan.dao;

import com.sandu.design.model.search.DesignPlanSearch;
import com.sandu.designplan.model.DesigPlanWithResult;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.designplan.model.DesignPlanModel;
import com.sandu.designplan.model.DesignPlanResult;
import com.sandu.designplan.product.model.ProductDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: DesignPlanMapper.java
 * @Package com.sandu.design.dao
 * @Description:设计方案-设计方案表Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-06-26 11:01:53
 */
@Repository
public interface DesignPlanMapper {
    int insertSelective(DesignPlan record);

    int updateByPrimaryKeySelective(DesignPlan record);

    int deleteByPrimaryKey(Integer id);

    DesignPlan selectByPrimaryKey(Integer id);

    int selectCount(DesignPlanSearch designPlanSearch);

    List<DesignPlan> selectPaginatedList(
            DesignPlanSearch designPlanSearch);

    List<DesignPlan> selectList(DesignPlan designPlan);

    /**
     * 其他
     */
    List<DesignPlanResult> designList(DesignPlan designPlan);

    List<DesignPlan> getPlanList(DesignPlan designPlan);

    List<DesignPlan> getDesignList(DesignPlan designPlan);

    List<DesignPlan> getPlanListByAreas(DesignPlan designPlan);

    int getPlanCount(DesignPlan designPlan);

    int getTemplateProductId(Map map);

    List<Map> getUserMaxPlan(Map map);

    /**
     * 通过plan_id获得设计方案状态
     *
     * @param designPlanId
     * @return
     */
    int getDesignPlanState(Integer designPlanId);

    List<DesigPlanWithResult> selectDesignList(DesignPlan designPlan);

    DesignPlanModel selectDesignPlanInfo(Integer id);

    List<ProductDTO> getProductDTOList(Integer designPlanId);

    List<DesignPlan> getPlansBySpaceInfo(@Param("spaceAreas") String spaceAreas, @Param("spaceFunctionId") Integer spaceFunctionId);

    DesignPlan getSceneModifiedById(int designPlanId);

    List<DesignPlan> getTempDesignPaln4RenderBakSceneTask();

    void batchDelTempDesign(@Param("delPlanIdList") List<Integer> delPlanIdList);

    /**
     * getTempDesignPalnBySceneId(返回只包含id，其余数据没返回，请注意使用)
     *
     * @param designPlan
     * @return DesignPlan    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    DesignPlan getTempDesignPalnBySceneId(DesignPlan designPlan);

}
