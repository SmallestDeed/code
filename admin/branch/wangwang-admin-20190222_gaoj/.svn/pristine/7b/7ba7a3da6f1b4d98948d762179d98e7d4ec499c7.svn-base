package com.sandu.service.dictionary.dao;

import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 */
@Repository
public interface DictionaryDao {

    int insertSelective(Dictionary record);

    int updateByPrimaryKeySelective(Dictionary record);

    int deleteByPrimaryKey(Integer id);

    Dictionary selectByPrimaryKey(Integer id);

    List<Dictionary> listByType(@Param("types") List<String> types);

    Dictionary getByTypeAndValue(@Param("type") String type, @Param("value") Integer value);

    List<Dictionary> getProductTypeValueAndSmallBySmallValueKey(@Param("valueKey") String valueKey);

    Dictionary getByValueKey(@Param("smallTypeCode") String smallTypeCode);

    List<Dictionary> getByValueKeys(List<String> strings);

    List<Dictionary> getByTypeAndName(@Param("type") String type, @Param("name") String name);

    List<Dictionary> listByAloneType(@Param("type") String type);

    List<DictionaryTypeListVO> selectListByTypeOrValues(@Param("industry") String industry,@Param("integerList") List<Integer> integerList);
}
