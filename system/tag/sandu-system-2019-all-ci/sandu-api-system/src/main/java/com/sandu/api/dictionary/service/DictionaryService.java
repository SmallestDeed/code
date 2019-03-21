package com.sandu.api.dictionary.service;

import com.sandu.api.company.output.BaseCompanyDetailsVO;
import com.sandu.api.dictionary.input.DictionaryTypeListQuery;
import com.sandu.api.dictionary.input.SysDictionaryQuery;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

/**
 * @Author chenqiang
 * @Description 数据字典 基础 业务层
 * @Date 2018/6/1 0001 10:30
 * @Modified By
 */
@Component
public interface DictionaryService {

	 List<SysDictionary> listByType(String type);

	 SysDictionary getByTypeAndValue(String type, Integer typeValue);

	 List<SysDictionary> getByTypeAndValues(String type, Integer typeValue);

    /**
     * value作为key  name 作为value
     * @param type 类型
     * @return map
     */
    Map<Integer, String> valueAndName2Map(String type);

    /**
     *  用于查询产品大小类value
     * @param valueKey          三级节点的code
     * @return { type: "value", small:"value"}
     */
    Map<String, SysDictionary> getProductTypeValueAndSmallBySmallValueKey(String valueKey);

    List<SysDictionary> getProductType();

    SysDictionary getByValueKey(String smallTypeCode);

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
    int deleteLogicByPrimaryKey( Long id, String loginName);

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

    /**
     * 根据类型获取数据字典列表信息
     * @author chenqiang
     * @param query DictionaryTypeListQuery 对象
     * @return DictionaryTypeListVO 列表
     * @date 2018/5/31 0031 18:21
     */
    List<DictionaryTypeListVO> getDictionaryListByType(DictionaryTypeListQuery query);

    /**
     * 通过类型和values获取名称
     * @auth xiaoxc_2018-06-13
     * @param type
     * @param valueList
     * @return
     */
    String getNameByTypeOrValues(String type, List<Integer> valueList);

    /**
     * 通过类型和values获取名称
     * @auth chenqiang
     * @param type
     * @param valueList
     * @return DictionaryTypeListVO 列表
     */
    List<DictionaryTypeListVO> getListByTypeOrValues(String type, List<Integer> valueList);


    /**
     * wanghailin
     * 模糊查询经销商所属行业
     * @param pCategory 经销商所属厂商所属行业String型集合
     * @param name 名字
     * @return list
     */
    List<DictionaryTypeListVO> getFranchiserCategoryList(String pCategory, String name);

    /**
     * 条件查询数据字典
     * @auth lipeiyuan
     * @return sysDictionaryList
     */
    List<SysDictionary> queryByOption(SysDictionaryQuery sysDictionaryQuery);
    /**
     * 通过类型查询列表
     * @auth lipeiyuan
     * @return sysDictionaryList
     */
    List<SysDictionary> queryByType(String type);

    BaseCompanyDetailsVO getCompanyIndustrys(Long companyId);
}
