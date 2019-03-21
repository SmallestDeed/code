package com.sandu.api.dictionary.service;

import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;

import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 */
public interface DictionaryService {
    List<Dictionary> listByType(String type);

    Dictionary getByTypeAndValue(String type, Integer typeValue);

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
    Map<String, Dictionary> getProductTypeValueAndSmallBySmallValueKey(String valueKey);

    List<Dictionary> getProductType();

    Dictionary getByValueKey(String smallTypeCode);

    List<Dictionary> getByValueKeys(List<String> strings);

     String getProductTypeNameInfos(String smallType,String type);

    /**
     * 根据type和name查询空间类型信息
     */
    List<Dictionary> getByTypeAndName(String type, String name);

    List<DictionaryTypeListVO> getListByTypeOrValues(String industry, List<Integer> integerList);
}
