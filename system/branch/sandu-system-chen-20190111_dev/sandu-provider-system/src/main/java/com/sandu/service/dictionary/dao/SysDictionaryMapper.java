package com.sandu.service.dictionary.dao;


import com.sandu.api.dictionary.input.DictionaryTypeListQuery;
import com.sandu.api.dictionary.input.SysDictionaryQuery;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author chenqiang
 * @Description 数据字典 dao 持久层
 * @Date 2018/5/31 0031 18:18
 * @Modified By
 */
@Repository
public interface SysDictionaryMapper {

    /**
     * 根据主键id 物理删除数据字典信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键id 逻辑删除数据字典信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteLogicByPrimaryKey(@Param("id") Long id, @Param("loginName")String loginName);

    /**
     * 根据数据字典基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 数据字典基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int insertSelective(SysDictionary record);

    /**
     * 根据主键id 查询 数据字典基础信息
     * @author chenqiang
     * @param id 数据字典主键id
     * @return 数据字典基础实体类
     * @date 2018/5/31 0031 18:21
     */
    SysDictionary selectByPrimaryKey(Long id);

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 数据字典基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int updateByPrimaryKeySelective(SysDictionary record);
    
    List<SysDictionary> listByType(@Param("types") List<String> types);

    SysDictionary getByTypeAndValue(@Param("type") String type, @Param("value") Integer value);

    List<SysDictionary> getProductTypeValueAndSmallBySmallValueKey(@Param("valueKey") String valueKey);

    SysDictionary getByValueKey(@Param("smallTypeCode") String smallTypeCode);

    /**
     * 根据类型获取数据字典列表信息
     * @author chenqiang
     * @param query DictionaryTypeListQuery 对象
     * @return DictionaryTypeListVO 列表
     * @date 2018/5/31 0031 18:21
     */
    List<DictionaryTypeListVO> selectDictionaryByType(DictionaryTypeListQuery query);

    /**
     * 通过类型和values获取名称
     * @auth xiaoxc_2018-06-13
     * @param type
     * @param valueList
     * @return
     */
    String findNameByTypeOrValues(@Param("type") String type, @Param("valueList") List<Integer> valueList);

    /**
     * 通过类型和values获取名称
     * @auth chenqiang
     * @param type
     * @param valueList
     * @return DictionaryTypeListVO 列表
     */
    List<DictionaryTypeListVO> selectListByTypeOrValues(@Param("type") String type, @Param("valueList") List<Integer> valueList);

    /**
     * wangHaiLin
     * 模糊查询经销商所属行业
     * @param list 厂商所属行业value集合
     * @param name 行业名称
     * @return list
     */
    List<DictionaryTypeListVO> getFranchiserCategoryList(@Param("list") List<Integer> list, @Param("name") String name);



    /**
     * 条件查询
     * @param sysDictionaryQuery
     * @return
     */
    List<SysDictionary> queryByOption(SysDictionaryQuery sysDictionaryQuery);

    List<SysDictionary> getByTypeAndValues(@Param("type") String type, @Param("typeValue") Integer typeValue);

    List<DictionaryTypeListVO> selectByTypeAndValue(@Param("type") String industry, @Param("companyIndustrys") List<Long> companyIndustrys);
}