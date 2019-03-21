package com.sandu.api.category.service;

import com.sandu.api.category.input.ProCategoryListQuery;
import com.sandu.api.category.model.ProCategory;
import com.sandu.api.category.output.CategoryListVO;
import com.sandu.api.category.output.ProCategoryListVO;
import com.sandu.api.dictionary.output.FranchiserCategoryListVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author chenqiang
 * @Description 产品分类 基础 业务层
 * @Date 2018/6/1 0001 10:26
 * @Modified By
 */
@Component
public interface ProCategoryService {

    /**
     * 根据主键id 物理删除产品分类信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键id 逻辑删除产品分类信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteLogicByPrimaryKey(Long id, String loginName);

    /**
     * 根据产品分类基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 产品分类基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int insertSelective(ProCategory record);

    /**
     * 根据主键id 查询 产品分类基础信息
     * @author chenqiang
     * @param id 产品分类主键id
     * @return 产品分类基础实体类
     * @date 2018/5/31 0031 18:21
     */
    ProCategory selectByPrimaryKey(Long id);

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 产品分类基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int updateByPrimaryKeySelective(ProCategory record);

    /**
     * 根据分类id 与层级 查询 下级 分类列表
     * @author chenqiang
     * @param query ProCategoryListQuery 对象
     * @return ProCategoryListVO 列表
     * @date 2018/5/31 0031 18:21
     */
    List<ProCategoryListVO> getCategoryListByPidLevel(ProCategoryListQuery query);

    /**
     * 根据id集合 查询 名称 字符串
     * @author chenqiang
     * @param idList id集合
     * @return 分类名称字符串
     * @date 2018/5/31 0031 18:21
     */
    String getCategoryNames(List<Integer> idList);

    /**
     * 根据id集合 查询 父级ID 集合
     * @auth xiaoxc
     * @date 2018-06-09
     * @param idList id集合
     * @return 父级ID集合
     */
    List<Integer> getParentId(List<Integer> idList);


    /**
     * 根据idList 获取 上级 id 集合
     * @author chenqiang
     * @param idList id集合
     * @return 上级 id 集合
     * @date 2018/5/31 0031 18:21
     */
    List<Integer> getPCategoryListByIdList(List<Integer> idList);

    /**
     * 根据pid 与 idList 获取下级 集合
     * @author chenqiang
     * @param idList 下级id集合
     * @param pid 父id
     * @return 下级 分类 集合
     * @date 2018/5/31 0031 18:21
     */
    List<ProCategoryListVO> getListByIdListPid(List<Integer> idList,Integer pid);

    /**
     * 根据 idList 集合
     * @author chenqiang
     * @param idList 下级id集合
     * @return 分类 集合
     * @date 2018/6/12
     */
    List<CategoryListVO> getListByIdList(List<Integer> idList);

    /**
     * 根据参数 查询 是否存在这些分类id
     * @author chenqiang
     * @param categoryIds id集合
     * @param level 级别
     * @return true:没问题
     * @date 2018/6/12
     */
    boolean checkCategoryIds(String categoryIds,String level);

    /**
     * wangHl
     * 根据产品分类编码集合,等级 查询产品分类集合
     * @param codeList 产品分类编码集合
     * @param level
     * @return
     */
    List<CategoryListVO> getListByCodeList(List<String> codeList,String level);

    /**
     * wangHl
     * 通过Pid查询可见产品范围
     * @param pId pid
     * @return 可见产品集合
     */
    List<CategoryListVO> getCategoryByPId(Long pId);

}
