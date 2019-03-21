package com.sandu.service.area.impl;

import com.sandu.api.area.input.BaseAreaListQuery;
import com.sandu.api.area.model.BaseArea;
import com.sandu.api.area.model.BaseAreaQuery;
import com.sandu.api.area.model.BaseAreaQueryBean;
import com.sandu.api.area.output.BaseAreaBo;
import com.sandu.api.area.output.BaseAreaListVO;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.service.area.dao.BaseAreaMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * @Author chenqiang
 * @Description 行政区域 基础 业务层
 * @Date 2018/6/1 0001 10:08
 * @Modified By
 */
@Service("baseAreaService")
public class BaseAreaServiceImpl implements BaseAreaService {

    @Autowired
    private BaseAreaMapper baseAreaMapper;

    /**
     * 根据主键id 物理删除系统区域信息
     *
     * @param id 主键id
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    public int deleteByPrimaryKey(Long id) {
        return baseAreaMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键id 逻辑删除系统区域信息
     *
     * @param id        主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int deleteLogicByPrimaryKey(Long id, String loginName) {
        return baseAreaMapper.deleteLogicByPrimaryKey(id, loginName);
    }

    /**
     * 根据系统区域基础实体类 选择性 新增数据
     *
     * @param record 系统区域基础实体类 对象
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public int insertSelective(BaseArea record) {
        int count = baseAreaMapper.insertSelective(record);
        if (count > 0)
            return record.getId().intValue();
        else
            return 0;
    }

    /**
     * 根据主键id 查询 系统区域基础信息
     *
     * @param id 系统区域主键id
     * @return 系统区域基础实体类
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public BaseArea selectByPrimaryKey(Long id) {
        return baseAreaMapper.selectByPrimaryKey(id);
    }


    /**
     * 根据主键 选择性 修改数据
     *
     * @param record 系统区域基础实体类 对象
     * @return 受影响的行数
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    public int updateByPrimaryKeySelective(BaseArea record) {
        return baseAreaMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据区域code查询下级区域list
     *
     * @param query 系统区入参实体类 对象
     * @return 行政区域 BaseAreaListVO list
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public List<BaseAreaListVO> getAreaListByCode(BaseAreaListQuery query) {

        List<BaseAreaListVO> areaList = baseAreaMapper.selectAreaListByCode(query);

        return areaList != null ? areaList : new ArrayList<>();
    }


    @Override
    public String getProviceCityAreaByCode(String areaCode) {

        if (StringUtils.isEmpty(areaCode) || areaCode.trim().length() != 6) {
            return null;
        }
        BaseAreaQueryBean baseAreaQueryBean = new BaseAreaQueryBean(areaCode);
        List<BaseArea> list = baseAreaMapper.getProviceCityAreaByCode(baseAreaQueryBean);
        if (list == null || list.size() <= 0)
            return null;
        StringBuffer buffer = new StringBuffer();
        for (BaseArea area : list) {
            buffer.append(area.getAreaName());
        }
        return buffer.toString();
    }

    /**
     * 获取企业 行政区域 名称
     *
     * @param codeList code list 入参
     * @return 行政区域名称
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public String getAreaNames(List<String> codeList) {
        return baseAreaMapper.selectAreaNames(codeList);
    }

    /**
     * 校验参数 真实性
     *
     * @param areaCode   省
     * @param cityCode   市
     * @param areaCode   区
     * @param streetCode 街道
     * @return Map(success, true / false)、Map(message,‘’)
     * @author chenqiang
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public Map<String, String> checkAreaCode(String provinceCode, String cityCode, String areaCode, String streetCode) {
        Map<String, String> map = new HashMap<>();
        Boolean bool = true;
        String message = "无误";
        int count = 0;

        if (StringUtils.isNotEmpty(provinceCode)) {
            count = baseAreaMapper.selectCodeCount(provinceCode, "0");
            if (count != 1) {
                bool = false;
                message = "省编码有误";
            }
            if (bool && StringUtils.isNotEmpty(cityCode)) {
                count = baseAreaMapper.selectCodeCount(cityCode, provinceCode);
                if (count != 1) {
                    bool = false;
                    message = "市编码有误";
                }

                if (bool && StringUtils.isNotEmpty(areaCode)) {
                    count = baseAreaMapper.selectCodeCount(areaCode, cityCode);
                    if (count != 1) {
                        bool = false;
                        message = "区编码有误";
                    }

                    if (bool && StringUtils.isNotEmpty(streetCode)) {
                        count = baseAreaMapper.selectCodeCount(streetCode, areaCode);
                        if (count != 1) {
                            bool = false;
                            message = "街道编码有误";
                        }
                    }
                }
            }
        }

        map.put("success", bool.toString());
        map.put("message", message);
        return map;
    }

    @Override
    public List<BaseArea> queryByOption(BaseAreaQuery baseAreaQuery) {
        return baseAreaMapper.queryByOption(baseAreaQuery);
    }

    /**
     * 区域最小level
     */
    private static final Integer MIN_LEVEL = 4;

    @Override
    public Set<String> queryAllNextLevelByCode(String code) {
        BaseAreaQuery baseAreaQuery = new BaseAreaQuery();

        baseAreaQuery.setAreaCode(code);
        baseAreaQuery.setIsDeleted(0);
        BaseArea baseArea = queryByOption(baseAreaQuery).get(0);
        Integer level = baseArea.getLevelId();

        Set<String> allCodeSet = new HashSet<>();
        Set<String> codeSet = new HashSet<>();
        codeSet.add(code);
        for (int i = 0; i < MIN_LEVEL - level; i++) {
            getCodeSet(allCodeSet, codeSet);
        }

        return allCodeSet;
    }

    @Override
    public BaseAreaBo queryAreaInfoByCode(String code) {

        BaseAreaQuery baseAreaQuery = new BaseAreaQuery();
        baseAreaQuery.setAreaCode(code);
        baseAreaQuery.setIsDeleted(0);
        List<BaseArea> baseAreaList = queryByOption(baseAreaQuery);
        if (CollectionUtils.isEmpty(baseAreaList)) {
            return null;
        }
        BaseArea baseArea = baseAreaList.get(0);
        String[] codes = baseArea.getLongCode().split("\\.");

        Set<String> codeSet = new HashSet<>();
        for (String cod : codes) {
            codeSet.add(cod);
        }
        if (codeSet == null || codeSet.size() < 0) {
            return null;
        }

        baseAreaQuery = new BaseAreaQuery();
        baseAreaQuery.setPidSet(codeSet);
        baseAreaQuery.setIsDeleted(0);
        List<BaseArea> baseAreaList1 = queryByOption(baseAreaQuery);
        if (CollectionUtils.isEmpty(baseAreaList1)) {
            return null;
        }

        BaseAreaBo baseAreaBo = new BaseAreaBo();
        baseAreaList1.forEach(baseArea1 -> {
            switch (baseArea1.getLevelId()) {
                case 1:
                    baseAreaBo.setProvinceCode(baseArea1.getAreaCode());
                    baseAreaBo.setProvinceCode(baseArea1.getAreaName());
                    break;
                case 2:
                    baseAreaBo.setCityCode(baseArea1.getAreaCode());
                    baseAreaBo.setCityName(baseArea1.getAreaName());
                    break;
                case 3:
                    baseAreaBo.setAreaCode(baseArea1.getAreaCode());
                    baseAreaBo.setAreaName(baseArea1.getAreaName());
                    break;
                case 4:
                    baseAreaBo.setStreetCode(baseArea1.getAreaCode());
                    baseAreaBo.setStreetName(baseArea1.getAreaName());
                    break;
                default:
                    break;
            }

        });

        return baseAreaBo;
    }

    /**
     * 查询下一级 code
     *
     * @param allCodeSet
     * @param codeSet
     */
    private void getCodeSet(Set<String> allCodeSet, Set<String> codeSet) {
        BaseAreaQuery baseAreaQuery = new BaseAreaQuery();
        baseAreaQuery.setPidSet(codeSet);
        baseAreaQuery.setIsDeleted(0);
        List<BaseArea> baseAreaList = queryByOption(baseAreaQuery);
        if (!baseAreaList.isEmpty()) {
            baseAreaList.forEach(baseArea -> allCodeSet.add(baseArea.getAreaCode()));
            codeSet.clear();
            baseAreaList.forEach(baseArea -> codeSet.add(baseArea.getAreaCode()));
        }
    }

    @Override
    public Map<String, String> codeAndNameMap() {
        List<BaseArea> areas = baseAreaMapper.queryCodeAndNameMap();
        return areas.stream().collect(toMap(BaseArea::getAreaCode, BaseArea::getAreaName));
    }

    @Override
    public BaseAreaListVO queryAreaByCode(String code) {
        return baseAreaMapper.queryAreaByCode(code);
    }

    @Override
    public Integer getLivingId(String areaCode, String livingInfo) {
        return baseAreaMapper.getLivingId(areaCode,livingInfo);
    }
    
    
    public BaseAreaBo getAreaCodeByName(String provinceName,String cityName,String areaName,String streetName) {
        return baseAreaMapper.getAreaCodeByName(provinceName,cityName,areaName,streetName);
    }

    @Override
    public Map<String, String> code2Name(HashSet<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return Collections.emptyMap();
        }
        List<BaseArea> list = baseAreaMapper.listByCodes(strings);
        return list.stream().collect(Collectors.toMap(BaseArea::getAreaCode, BaseArea::getAreaName));
    }
}
