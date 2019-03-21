package com.sandu.service.category.dao;

import com.sandu.api.category.input.ProCategoryListQuery;
import com.sandu.api.category.model.ProCategory;
import com.sandu.api.category.output.CategoryListVO;
import com.sandu.api.category.output.ProCategoryListVO;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.dictionary.output.FranchiserCategoryListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author chenqiang
 * @Description 产品分类 dao 持久层
 * @Date 2018/5/31 0031 18:18
 * @Modified By
 */
@Repository
public interface ProCategoryMapper {
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
    int deleteLogicByPrimaryKey(@Param("id") Long id, @Param("loginName")String loginName);

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
     * 根据分类id 查询 下级 分类列表
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
    String selectCategoryNames(@Param("idList") List<Integer> idList);

    /**
     * 根据id集合 查询 父级ID 集合
     * @auth xiaoxc
     * @date 2018-06-09
     * @param idList id集合
     * @return 父级ID集合
     */
    List<Integer> findParentId(@Param("idList") List<Integer> idList);

    /**
     * 根据idList 获取 上级 id 集合
     * @author chenqiang
     * @param idList id集合
     * @return 上级 id 集合
     * @date 2018/5/31 0031 18:21
     */
    List<Integer> selectPCategoryListByIdList(@Param("idList") List<Integer> idList);

    /**
     * 根据pid 与 idList 获取下级 集合
     * @author chenqiang
     * @param idList 下级id集合
     * @param pid 父id
     * @return 下级 分类 集合
     * @date 2018/5/31 0031 18:21
     */
    List<ProCategoryListVO> selectListByIdListPid(@Param("idList") List<Integer> idList,@Param("pid") Integer pid);

    /**
     * 根据 idList 集合
     * @author chenqiang
     * @param idList id集合
     * @return 分类 集合
     * @date 2018/6/12
     */
    List<CategoryListVO> selectListByIdList(@Param("idList") List<Integer> idList);

    /**
     * 根据参数 查询 是否存在这些分类id
     * @author chenqiang
     * @param idList id集合
     * @param level 级别
     * @return 数量
     * @date 2018/6/12
     */
    int selectCountByIdList(@Param("idList") List<Integer> idList,@Param("level")Integer level);


    List<CategoryListVO> selectListByCodeList(@Param("codeList") List<String> codeList,@Param("level")Integer level);

    /**
     * 通过pid查询可见商品列表
     * @param pId 可见商品Pid
     * @return List
     */
    List<CategoryListVO> selectCategoryByPid(@Param("pId")Long pId);


}