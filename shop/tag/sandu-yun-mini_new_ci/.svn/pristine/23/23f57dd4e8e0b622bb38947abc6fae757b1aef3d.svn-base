package com.sandu.sys.dao;

import java.util.List;
import com.sandu.common.persistence.CrudDao;
import com.sandu.sys.model.SysDictionary;
import com.sandu.sys.model.query.SysDictionaryQuery;
import com.sandu.sys.model.vo.SysDictionaryVo;
import org.apache.ibatis.annotations.Param;

public interface SysDictionaryDao extends 
CrudDao<SysDictionary,SysDictionaryQuery,SysDictionaryVo>{

    List<SysDictionaryVo> findListByType(SysDictionaryQuery query);

    /**
     * 通过type和valuelist查询列表
     * @param query
     * @return
     */
    List<SysDictionaryVo> findListByTypeOrValues(SysDictionaryQuery query);

    /**
     * 根据类型和value获取数据字典项
     * @param query
     * @return
     */
    SysDictionaryVo findDictionory(SysDictionaryQuery query);


    /**
     * 通过数据字典类型获取数据字典列表
     * @param type 数据字典类型
     * @return list
     */
    List<SysDictionary> getListByType(@Param("type")String type,@Param("start")Integer start,@Param("pageSize")Integer pageSize);


}
