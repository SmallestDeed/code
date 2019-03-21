package com.sandu.service.dictionary.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.output.BaseCompanyDetailsVO;
import com.sandu.api.company.service.biz.BaseCompanyBizService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.dictionary.input.DictionaryTypeListQuery;
import com.sandu.api.dictionary.input.SysDictionaryQuery;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.service.dictionary.dao.SysDictionaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author chenqiang
 * @Description 数据字典 基础 业务层
 * @Date 2018/6/1 0001 10:30
 * @Modified By
 */
@Slf4j
@Service("sysDictionaryService")
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    @Autowired
    private BaseCompanyBizService baseCompanyBizService;

    @Override
    public List<SysDictionary> listByType(String type) {
        return sysDictionaryMapper.listByType(Collections.singletonList(type));
    }

    @Override
    public SysDictionary getByTypeAndValue(String type, Integer value) {
        return sysDictionaryMapper.getByTypeAndValue(type, value);
    }

    @Override
    public List<SysDictionary> getByTypeAndValues(String type, Integer typeValue) {
        return sysDictionaryMapper.getByTypeAndValues(type, typeValue);
    }

    @Override
    public Map<Integer, String> valueAndName2Map(String type) {
        List<SysDictionary> dictionaries = listByType(type);
        return dictionaries.stream().collect(Collectors.toMap(SysDictionary::getValue, SysDictionary::getName));
    }


    @Override
    public Map<String, SysDictionary> getProductTypeValueAndSmallBySmallValueKey(String valueKey) {
        Map<String, SysDictionary> ret = new HashMap<>();
        List<SysDictionary> list = sysDictionaryMapper.getProductTypeValueAndSmallBySmallValueKey(valueKey);
        for (SysDictionary dictionary : list) {
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
    public List<SysDictionary> getProductType() {
        List<SysDictionary> productType = sysDictionaryMapper.listByType(Collections.singletonList("productType"));
        if (productType.isEmpty()) {
            return productType;
        }
        List<SysDictionary> productSmallType = sysDictionaryMapper.listByType(productType.stream().map(SysDictionary::getValuekey).collect(Collectors.toList()));
        productType.addAll(productSmallType);
        return productType;
    }

    @Override
    public SysDictionary getByValueKey(String smallTypeCode) {
        return sysDictionaryMapper.getByValueKey(smallTypeCode);
    }


    /**
     * 根据主键id 物理删除数据字典信息
     *
     * @param id 主键id
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysDictionaryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键id 逻辑删除数据字典信息
     *
     * @param id        主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int deleteLogicByPrimaryKey(Long id, String loginName) {
        return sysDictionaryMapper.deleteLogicByPrimaryKey(id, loginName);
    }

    /**
     * 根据数据字典基础实体类 选择性 新增数据
     *
     * @param record 数据字典基础实体类 对象
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int insertSelective(SysDictionary record) {
        int count = sysDictionaryMapper.insertSelective(record);
        if (count > 0)
            return record.getId().intValue();
        else
            return 0;
    }

    /**
     * 根据主键id 查询 数据字典基础信息
     *
     * @param id 数据字典主键id
     * @return 数据字典基础实体类
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public SysDictionary selectByPrimaryKey(Long id) {
        return sysDictionaryMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键 选择性 修改数据
     *
     * @param record 数据字典基础实体类 对象
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int updateByPrimaryKeySelective(SysDictionary record) {
        return sysDictionaryMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据类型获取数据字典列表信息
     *
     * @param query DictionaryTypeListQuery 对象
     * @return DictionaryTypeListVO 列表
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public List<DictionaryTypeListVO> getDictionaryListByType(DictionaryTypeListQuery query) {

        List<DictionaryTypeListVO> dictionaryList = sysDictionaryMapper.selectDictionaryByType(query);

        return dictionaryList != null ? dictionaryList : new ArrayList<>();
    }

    @Override
    public String getNameByTypeOrValues(String type, List<Integer> valueList) {
        return sysDictionaryMapper.findNameByTypeOrValues(type, valueList);
    }

    /**
     * 通过类型和values获取名称
     *
     * @param type
     * @param valueList
     * @return DictionaryTypeListVO 列表
     * @auth chenqiang
     */
    @Override
    public List<DictionaryTypeListVO> getListByTypeOrValues(String type, List<Integer> valueList) {

        List<DictionaryTypeListVO> dictionaryList = sysDictionaryMapper.selectListByTypeOrValues(type, valueList);


        return dictionaryList != null ? dictionaryList : new ArrayList<>();
    }


    /**
     * wangHaiLin
     * 模糊查询经销商所属行业
     *
     * @param pCategory 所属行业String型集合
     * @param name      所属行业模糊名称
     * @return list
     */
    @Override
    public List<DictionaryTypeListVO> getFranchiserCategoryList(String pCategory, String name) {
        List<Integer> list = StringUtil.getListByString(pCategory);
        return sysDictionaryMapper.getFranchiserCategoryList(list, name);
    }

    @Override
    public List<SysDictionary> queryByOption(SysDictionaryQuery sysDictionaryQuery) {
        return sysDictionaryMapper.queryByOption(sysDictionaryQuery);
    }

    @Override
    public List<SysDictionary> queryByType(String type) {
        SysDictionaryQuery sysDictionaryQuery = new SysDictionaryQuery();
        sysDictionaryQuery.setType(type);
        sysDictionaryQuery.setIsDeleted(0);
        return queryByOption(sysDictionaryQuery);
    }

    @Override
    public BaseCompanyDetailsVO getCompanyIndustrys(Long companyId) {
        BaseCompanyDetailsVO companyInfoDetails = baseCompanyBizService.getCompanyInfoDetails(companyId);
        if (companyInfoDetails != null) {
            if (StringUtils.isEmpty(companyInfoDetails.getCompanyIndustrys())){
                return null;
            }
            List<Long> companyIndustrys = Arrays.stream(companyInfoDetails.getCompanyIndustrys().split(",")).map(m -> Long.parseLong(m)).collect(Collectors.toList());
            List<DictionaryTypeListVO> dictionaryTypeListVOS = sysDictionaryMapper.selectByTypeAndValue("industry",companyIndustrys);
            StringBuffer sb =new StringBuffer();
            if (dictionaryTypeListVOS != null && !dictionaryTypeListVOS.isEmpty()){
                dictionaryTypeListVOS.forEach(f->{
                    sb.append(f.getName());
                    sb.append(",");
                });
                companyInfoDetails.setCompanyIndustryNames(sb.toString());
                return companyInfoDetails;
            }
        }
        return null;
    }
}
