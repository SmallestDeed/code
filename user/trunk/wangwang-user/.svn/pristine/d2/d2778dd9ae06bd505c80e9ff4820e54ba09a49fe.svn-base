package com.sandu.api.system.service;

import com.sandu.api.system.input.SysDictionarySearch;
import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.output.SysDictionaryVO;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictionaryService {

    /**
     * 所有数据
     *
     * @param sysDictionary
     * @return List<SysDictionary>
     */
    public List<SysDictionary> getList(SysDictionary sysDictionary);

    /**
     * 根据类型和value获取数据字典对象
     *
     * @param type
     * @return List<SysDictionary>
     */
    public SysDictionary getSysDictionary(String type, Integer value);


    /**
     * 通过数据字典类型，查询数据字典列表
     * @param type 类型
     * @return list 数据字典集合
     */
    List<SysDictionary> getListByType(String type);

    SysDictionary findByTypeAndValueKey(String versionStatus, String versionStatus_official);

    /**
     * 适用于获取下拉框
     * select * from sys_dictionary where type = #{type}
     * 我加入了缓存功能
     * 
     * @author huangsongbo
     * @param type 过滤条件 sys_dictionary.type
     * @return
     */
	public List<SysDictionaryVO> getSysDictionaryVOList(String type);

	/**
	 * 
	 * @author huangsongbo
	 * @param sysDictionarySearch
	 * @return
	 */
	List<SysDictionary> getList(SysDictionarySearch sysDictionarySearch);

    List<SysDictionary> getListByTypeAndValues(String userType, List<Integer> userTypeValue);
}
