package com.sandu.rendermachine.service.system;

import com.sandu.rendermachine.model.system.SysDicitonaryOptimize;
import com.sandu.rendermachine.model.system.SysDictionary;
import com.sandu.rendermachine.model.system.SysDictionarySearch;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: SysDictionaryService.java
 * @Package com.nork.system.service
 * @Description:系统管理-数据字典Service
 * @createAuthor pandajun
 * @CreateDate 2015-05-26 11:45:04
 */
public interface SysDictionaryService {
    /**
     * 新增数据
     *
     * @param sysDictionary
     * @return int
     */
    public int add(SysDictionary sysDictionary);

    /**
     * 更新数据
     *
     * @param sysDictionary
     * @return int
     */
    public int update(SysDictionary sysDictionary);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    public int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return SysDictionary
     */
    public SysDictionary get(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return SysDictionary
     */
    public SysDictionary getFromCache(Integer id);

    /**
     * 所有数据
     *
     * @param sysDictionary
     * @return List<SysDictionary>
     */
    public List<SysDictionary> getList(SysDictionary sysDictionary);


    /**
     * 所有数据
     *
     * @param type
     * @return List<SysDictionary>
     */
    public List<SysDictionary> getList(String type);


    /**
     * 获取数据数量
     *
     * @param sysDictionarySearch
     * @return int
     */
    public int getCount(SysDictionarySearch sysDictionarySearch);

    /**
     * 分页获取数据
     *
     * @param sysDictionarytSearch
     * @return List<SysDictionary>
     */
    public List<SysDictionary> getPaginatedList(SysDictionarySearch sysDictionarytSearch);


    /**
     * 根据类型和value获取类型名称数据
     *
     * @param type
     * @return List<SysDictionary>
     */
    public SysDictionary getSysDictionaryByValue(String type, Integer value);

    public SysDictionary getNameByType(SysDictionary sysDictionary);

    /**
     * 根据类型获取value最大值
     *
     * @param type
     * @return int
     */
    public int getValueMax(String type);

    /**
     * 根据类型获取ordering最大值
     *
     * @param type
     * @return int
     */
    public int getOrderingMax(String type);

    /**
     * 根据valueKey查找SysDictionary
     *
     * @return
     */
    public SysDictionary findOneByValueKeyInCache(String valueKey);

    public SysDictionary getParentByValueKey(String type);

    public List<SysDictionary> getList(Integer value, String type, String att3);

    public SysDictionary checkType(SysDictionary sysDictionary);

    /**
     * 通过type和value查找sysDictionary
     *
     * @param type
     * @param value
     * @return
     * @author huangsongbo
     */
    public SysDictionary findOneByTypeAndValue(String type, Integer value);

    /**
     * 通过type和typeKey查找sysDictionary
     *
     * @param type
     * @param valueKey
     * @return
     * @author huangsongbo
     */
    public SysDictionary findOneByTypeAndValueKey(String type, String valueKey);


    List<SysDicitonaryOptimize> getListOptimize(SysDictionarySearch sysDictionarySearch);

    public int getCountOptimize(SysDictionarySearch sysDictionarySearch);

    /**
     * 根据类型和value获取数据字典对象
     *
     * @return List<SysDictionary>
     */
    public SysDictionary getSysDictionary(String type, Integer value);

    /**
     * 根据type查找sysDictionary
     *
     * @param type
     * @return
     * @author huangsongbo
     */
    public List<SysDictionary> findAllByType(String type);

    /**
     * 根据type和att1查找SysDictionary
     *
     * @return
     * @author huangsongbo
     */
    public List<SysDictionary> findAllByTypeAndAtt1(String type, String att1);

    /**
     * 查找所有产品小类的valueKey
     *
     * @return
     * @author huangsongbo
     */
    public List<String> findAllSmallTypeValueKey();

    /**
     * 通过type查找所有valueKey
     *
     * @param type
     * @return
     * @author huangsongbo
     */
    public List<String> findValueKeyByType(String type);

    /**
     * 通过大小类value查找小类Object
     *
     * @param map
     * @return
     * @author xiaoxc
     */
    public SysDictionary selectSmallTypeObj(Map<String, Object> map);

    /**
     * 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）*按产品小类排序用到
     *
     * @param bigSd
     * @param bmSmallSd
     * @return
     */
    public SysDictionary dealWithInconformity(SysDictionary bigSd, SysDictionary bmSmallSd);

    /**
     * 白模分类valuekey和对应的产品分类valuekey不一致判断（isInconformity）
     *
     * @param bigValuekey
     * @param smallValuekey
     * @param sdAtt3
     * @return
     */
    public SysDictionary dealWithInconformity(String bigValuekey, String smallValuekey, String sdAtt3);

    /**
     * 得到value:valuekey类型的map
     *
     * @param bigTypeList
     * @return
     * @author huangsongbo
     */
    public Map<Integer, String> getValueValueKeyMapByTypeAndValueKey(String string, List<String> bigTypeList);

    /**
     * 得到valueList
     *
     * @param bigType
     * @param smallTypeList
     * @return
     * @author huangsongbo
     */
    public List<Integer> getValueByTypeAndValueKeylist(String bigType, List<String> smallTypeList);

    /**
     * eg:baimo_mengk获得mengk的value
     *
     * @param smallTypeValue
     * @return
     * @author huangsongbo
     */
    public Integer getProductValueByBaimoValue(Integer typeValue, Integer smallTypeValue);

    public SysDictionary findOneByTypeAndValueAndValue(String string, Integer productTypeValue, Integer smallTypeValue);

    List<SysDicitonaryOptimize> findAll(SysDictionarySearch sysDictionarySearch);

    public SysDictionary findOneByTypeAndValue(String string, Integer systemType, boolean b);

    public SysDictionary findOneByValueKey(String smallTypeValuekey);

    public boolean isSpecialProductType(String type, String value);

    /**
     * 通过ValueKeys  和 type  查列表
     *
     * @param type
     * @return
     */
    public List<SysDictionary> getListByValueKeys(String type, List<String> ValueKeys);

    /**
     * 通过通过Value  和 type  查列表
     *
     * @param type
     * @param Value
     * @return
     */
    List<SysDictionary> getListByValueType(String type, List<String> Value);

    /**
     * 通过Value查户型空间面积列表
     *
     * @param typeValue
     * @return
     * @author xiaoxc
     */
    public List<SysDictionary> findSpaceAreaList(Integer typeValue);

    /**
     * 通过valuekeyList找到valueList
     *
     * @param smallTypeValueKeyList
     * @return
     * @author huangsongbo
     */
    public List<Integer> getValueListBySmallTypeValueKeyList(List<String> smallTypeValueKeyList);

}
