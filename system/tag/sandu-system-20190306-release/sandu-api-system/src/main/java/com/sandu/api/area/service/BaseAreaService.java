package com.sandu.api.area.service;

import com.sandu.api.area.input.BaseAreaListQuery;
import com.sandu.api.area.model.BaseArea;
import com.sandu.api.area.model.BaseAreaQuery;
import com.sandu.api.area.output.BaseAreaBo;
import com.sandu.api.area.output.BaseAreaListVO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author chenqiang
 * @Description 行政区域 基础 业务层
 * @Date 2018/6/1 0001 10:08
 * @Modified By
 */
@Component
public interface BaseAreaService {

    /**
     * 根据主键id 物理删除系统区域信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键id 逻辑删除系统区域信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int deleteLogicByPrimaryKey(Long id, String loginName);

    /**
     * 根据系统区域基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 系统区域基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int insertSelective(BaseArea record);

    /**
     * 根据主键id 查询 系统区域基础信息
     * @author chenqiang
     * @param id 系统区域主键id
     * @return 系统区域基础实体类
     * @date 2018/5/31 0031 18:21
     */
    BaseArea selectByPrimaryKey(Long id);

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 系统区域基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    int updateByPrimaryKeySelective(BaseArea record);

    /**
     * 根据区域code查询下级区域list
     * @author chenqiang
     * @param query 系统区入参实体类 对象
     * @return 行政区域 BaseAreaListVO list
     * @date 2018/5/31 0031 18:21
     */
    List<BaseAreaListVO> getAreaListByCode(BaseAreaListQuery query);

    /**
   	 * 通过地区编码查询一个对应的省市区三级
   	 * @param areaCode  地区编码
   	 * @return String 对应地址
   	 */
   	 String getProviceCityAreaByCode(String areaCode);
   	  
    /**
     * 获取企业 行政区域 名称
     * @author chenqiang
     * @param codeList code list 入参
     * @return 行政区域名称
     * @date 2018/5/31 0031 18:21
     */
    String getAreaNames(List<String> codeList);

    /**
     * 校验参数 真实性
     * @author chenqiang
     * @param areaCode 省
     * @param cityCode 市
     * @param areaCode 区
     * @param streetCode 街道
     * @return Map(success,true/false)、Map(message,‘’)
     * @date 2018/5/31 0031 18:21
     */
    Map<String,String> checkAreaCode(String provinceCode,String cityCode,String areaCode,String streetCode);

    /**
     * 条件查询
     * @param baseAreaQuery
     * @return
     */
    List<BaseArea> queryByOption(BaseAreaQuery baseAreaQuery);

    /**
     * 根据code查询下面所有code集合
     * @param code
     * @return
     */
    Set<String> queryAllNextLevelByCode(String code);

    /**
     * 根据code查上级
     * @return
     */
    BaseAreaBo queryAreaInfoByCode(String code);

    /**
     * 返回区域code,名称集合
     * @return
     */
    Map<String,String> codeAndNameMap();

    /**
     *
     * 通过区域编码，插叙区域名称
     * @param code 区域编码
     * @return BaseAreaListVO
     */
    BaseAreaListVO queryAreaByCode(String code);

    Integer getLivingId(String areaCode, String livingInfo);
    
    /**
     * 根据省市区街道查询编码
     * @param provinceName
     * @param cityName
     * @param areaName
     * @param streetName
     * @return
     */
    public BaseAreaBo getAreaCodeByName(String provinceName,String cityName,String areaName,String streetName);

    Map<String, String> code2Name(HashSet<String> strings);
}
