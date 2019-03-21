package com.sandu.api.shop.service;


import com.sandu.api.shop.input.ProjectCaseAdd;
import com.sandu.api.shop.input.ProjectCaseQuery;
import com.sandu.api.shop.input.ProjectCaseUpdate;
import com.sandu.api.shop.model.ProjectCase;
import com.sandu.api.shop.output.ProjectCaseDetailsVO;
import com.sandu.api.shop.output.ProjectCaseVO;
import com.sandu.commons.LoginUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 工程案例-service接口
 *
 * @auth xiaoxc
 * @data 2018-06-04
 */
@Component
public interface ProjectCaseService {

    /**
     * 新增
     * @param caseAdd 新增对象
     * @param loginUser 当前用户对象
     * @return int 新增数据Id
     */
    int add(ProjectCaseAdd caseAdd, LoginUser loginUser);

    /**
     * 删除
     * @param id 主键Id
     * @param loginUser 当前用户对象
     * @return 删除数量
     */
    int delete(Integer id, LoginUser loginUser, Integer shopType);

    /**
     * 根据店铺Id批量删除
     * @param shopId 主键Id
     * @param loginUser 当前用户对象
     * @return 删除数量
     */
    int batchDelByShopId(Integer shopId, LoginUser loginUser);

    /**
     * 更新
     * @param caseUpdate 修改对象
     * @param loginUser 当前用户对象
     * @return 更新数量
     */
    int update(ProjectCaseUpdate caseUpdate, LoginUser loginUser);

    /**
     * 更新
     * @param projectCase 修改对象
     * @param loginUser 当前用户对象
     * @return 更新数量
     */
    int updateCase(ProjectCase projectCase, LoginUser loginUser, Integer shopType);

    /**
     * 通过ID查询信息
     * @param id 主键ID
     * @return ProjectCase对象
     */
    ProjectCase findById(Integer id);

    /**
     * 通过ID查询信息
     * @param id 主键ID
     * @return vo对象
     */
    ProjectCaseDetailsVO getDetails(Integer id);

    /**
     * 获取满足条件的数据数量
     * @param query 查询条件
     * @return int 满足条件数据数量
     */
    int getCount(ProjectCaseQuery query);

    /**
     * 获取满足条件的数据列表
     * @param query 查询条件
     * @return  集合列表
     */
    List<ProjectCaseVO> findList(ProjectCaseQuery query);

    /**
     * 通过店铺Id删除属于店铺的工程案例
     * @param shopIds 店铺Id
     * @return list
     */
    Integer deleteCaseByShopId(List<Integer> shopIds,String loginUserName);

    /**
     * 通过店铺Id查找属于店铺的工程案例数量
     * @param shopIds 店铺Id
     * @return list
     */
    Integer getCaseByShopId(List<Integer> shopIds);

    /**
     * 修改企业店铺的工程案例状态
     * @param mainShopId
     * @param shopId
     * @param status
     * @return
     */
    Integer updateMainShopCaseStatus(Integer mainShopId, Integer shopId, Integer status);
}
