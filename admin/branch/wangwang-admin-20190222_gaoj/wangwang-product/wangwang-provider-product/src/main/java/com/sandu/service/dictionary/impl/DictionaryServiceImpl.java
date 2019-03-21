package com.sandu.service.dictionary.impl;

import com.google.common.base.Strings;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.constant.Punctuation;
import com.sandu.service.dictionary.dao.DictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Sandu
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryDao dictionaryDao;



    @Override
    public List<Dictionary> listByType(String type) {
        return dictionaryDao.listByType(Collections.singletonList(type));
    }

    @Override
    public Dictionary getByTypeAndValue(String type, Integer value) {
        return dictionaryDao.getByTypeAndValue(type, value);
    }

    @Override
    public Map<Integer, String> valueAndName2Map(String type) {
        List<Dictionary> dictionaries = listByType(type);
        return dictionaries.stream().collect(Collectors.toMap(Dictionary::getValue, Dictionary::getName));
    }


    @Override
    public Map<String, Dictionary> getProductTypeValueAndSmallBySmallValueKey(String valueKey) {
        Map<String, Dictionary> ret = new HashMap<>();
        List<Dictionary> list = dictionaryDao.getProductTypeValueAndSmallBySmallValueKey(valueKey);
        for (Dictionary dictionary : list) {
            if ("productType".equalsIgnoreCase(dictionary.getType())) {
                ret.put("type", dictionary);
            } else {
                ret.put("smallType", dictionary);
            }
        }
//        list.forEach(item -> {
//                    if ("productType".equalsIgnoreCase(item.getType())) {
//                        ret.put("type", item);
//                    } else {
//                        ret.put("smallType", item);
//                    }
//                });
        return ret;
    }

    @Override
    public List<Dictionary> getProductType() {
        List<Dictionary> productType = dictionaryDao.listByType(Collections.singletonList("productType"));
        if (productType.isEmpty()) {
            return productType;
        }
        List<Dictionary> productSmallType = dictionaryDao.listByType(productType.stream().map(Dictionary::getValuekey).collect(Collectors.toList()));
        productType.addAll(productSmallType);
        return productType;
    }

    @Override
    public Dictionary getByValueKey(String smallTypeCode) {
        return dictionaryDao.getByValueKey(smallTypeCode);
    }

    @Override
    public List<Dictionary> getByValueKeys(List<String> strings) {
        if(strings.isEmpty()){
            return Collections.emptyList();
        }
        return dictionaryDao.getByValueKeys(strings);
    }


    public String getProductTypeNameInfos(String smallType,String type){
        if(Strings.isNullOrEmpty(smallType)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        this.getByValueKeys(Arrays.asList(smallType,type))
                .stream().sorted(Comparator.comparingLong(Dictionary::getId))
                .forEach(item -> sb.append(item.getName()).append(Punctuation.SLASH));
        return (sb.substring(0, sb.length() - 1));
    }

    @Override
    public List<Dictionary> getByTypeAndName(String type, String name) {
        return dictionaryDao.getByTypeAndName(type,name);
    }

    @Override
    public List<DictionaryTypeListVO> getListByTypeOrValues(String industry, List<Integer> integerList) {
        List<DictionaryTypeListVO> dictionaryList = dictionaryDao.selectListByTypeOrValues(industry, integerList);


        return dictionaryList != null ? dictionaryList : new ArrayList<>();
    }
}
