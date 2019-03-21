package com.sandu.service.area.dao;

import com.sandu.api.area.input.BaseAreaListQuery;
import com.sandu.api.area.model.BaseArea;
import com.sandu.api.area.model.BaseAreaQuery;
import com.sandu.api.area.model.BaseAreaQueryBean;
import com.sandu.api.area.output.BaseAreaBo;
import com.sandu.api.area.output.BaseAreaListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

/**
 * @Author chenqiang
 * @Description 系统区域 dao 持久层
 * @Date 2018/5/31 0031 18:18
 * @Modified By
 */
@Repository
public interface BaseAreaMapper {
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
    int deleteLogicByPrimaryKey(@Param("id") Long id, @Param("loginName")String loginName);

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

    
    List<BaseArea> getProviceCityAreaByCode(BaseAreaQueryBean baseAreaQueryBean);

    
    /**
     * 根据区域code查询下级区域list
     * @author chenqiang
     * @param query 系统区入参实体类 对象
     * @return 行政区域 BaseAreaListVO list
     * @date 2018/5/31 0031 18:21
     */
    List<BaseAreaListVO> selectAreaListByCode(BaseAreaListQuery query);

    /**
     * 获取企业 行政区域 名称
     * @author chenqiang
     * @param codeList code list 入参
     * @return 行政区域名称
     * @date 2018/5/31 0031 18:21
     */
    String selectAreaNames(@Param("codeList")List<String> codeList);

    /**
     * 校验参数 真实性
     * @author chenqiang
     * @param areaCode 校验参数
     * @param pidAreaCode 校验参数
     * @return 数据量
     * @date 2018/5/31 0031 18:21
     */
    int selectCodeCount(@Param("areaCode")String areaCode,@Param("pidAreaCode")String pidAreaCode);

    /**
     * 条件查询
     * @param baseAreaQuery
     * @return
     */
    List<BaseArea> queryByOption(BaseAreaQuery baseAreaQuery);

    /**
     * 查询所有编码和名称集合
     * @return
     */
    List<BaseArea> queryCodeAndNameMap();


    BaseAreaListVO queryAreaByCode(@Param("code") String code);

    Integer getLivingId(@Param("areaCode")String areaCode, @Param("livingInfo")String livingInfo);

	BaseAreaBo getAreaCodeByName(@Param("provinceName")String provinceName, @Param("cityName")String cityName, @Param("areaName")String areaName, @Param("streetName")String streetName);
    List<BaseArea> listByCodes(@Param("set") HashSet<String> strings);
}