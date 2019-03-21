package com.sandu.service.shop.dao;

import com.sandu.api.shop.input.ProjectCaseQuery;
import com.sandu.api.shop.model.ProjectCase;
import com.sandu.api.shop.output.ProjectCaseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 工程案例--接口持久层
 *
 * @auth xiaoxc
 * @data 2018-06-19
 */
@Repository
public interface ProjectCaseDao {

    /**
     * 新增
     * @param projectCase 新增对象
     * @return int 新增数据Id
     */
    int insert(ProjectCase projectCase);

    /**
     * 删除
     * @param projectCase 主键Id
     * @return 删除数量
     */
    int delete(ProjectCase projectCase);

    /**
     * 根据店铺ID批量删除
     * @param projectCase 主键Id
     * @return 删除数量
     */
    int batchDelByShopId(ProjectCase projectCase);

    /**
     * 更新
     * @param projectCase 修改对象
     * @return 更新数量
     */
    int update(ProjectCase projectCase);

    /**
     * 通过ID查询信息
     * @param id 主键ID
     * @return ProjectCase对象
     */
    ProjectCase findById(Integer id);

    /**
     * 获取满足条件的数据数量
     * @param query 查询条件店
     * @return int 满足条件数据数量
     */
    int findCount(ProjectCaseQuery query);

    /**
     * 获取满足条件的数据列表
     * @param query 查询条件
     * @return  集合列表
     */
    List<ProjectCaseVO> findList(ProjectCaseQuery query);

    /**
     * add by WangHL
     * 通过店铺Ids删除属于店铺的工程案例
     * @param shopIds 店铺Id
     * @return list
     */
    Integer deleteCaseByShopId(@Param("shopIds")List<Integer> shopIds,@Param("loginUserName")String loginUserName);

    /**
     * 通过店铺Id查找属于店铺的工程案例数量
     * @param shopIds 店铺Id
     * @return list
     */
    Integer getCaseByShopId(@Param("shopIds")List<Integer> shopIds);

    Integer getMainShopCaseId(@Param("caseId") Integer caseId,
                              @Param("mainShopId") Integer mainShopId);

    ProjectCase get(Integer id);

    Integer updateMainShopCaseStatus(@Param("mainShopId") Integer mainShopId,
                                     @Param("shopId") Integer shopId,
                                     @Param("status") Integer status);
}
