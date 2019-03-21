package com.sandu.base.dao;

import com.sandu.base.model.BaseArea;
import com.sandu.base.model.search.BaseAreaSearch;
import com.sandu.base.model.vo.BaseAreaVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @version V1.0
 * @Title: BaseAreaMapper.java
 * @Package com.sandu.system.dao
 * @Description:系统-行政区域Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 15:31:09
 */
@Repository
public interface BaseAreaMapper {
    int insertSelective(BaseArea record);

    int updateByPrimaryKeySelective(BaseArea record);

    int deleteByPrimaryKey(Integer id);

    BaseArea selectByPrimaryKey(Integer id);

    BaseArea selectCityName(BaseAreaSearch baseAreaSearch);

    int selectCount(BaseAreaSearch baseAreaSearch);

    List<BaseArea> selectPaginatedList(
            BaseAreaSearch baseAreaSearch);

    List<BaseArea> selectList(BaseArea baseArea);

    List<BaseArea> selectCityList();

	String selectCodeName(String code);

	List<BaseArea> selectAllCity(BaseArea baseArea);

    List<String>  selectThreeArea(int levelId);

    Integer insertBaseAreaList(List<BaseArea> baseAreaList);

    List<BaseAreaVo> selectAreaList(BaseArea baseArea);

    BaseArea selectBaseAreaByCode(String province);

    List<BaseArea> selectList2(BaseArea baseArea);

	List<BaseArea> selectBaseAreaByAreaName(@Param("areaName")String areaName, @Param("levelId")String levelId, @Param("pid")String pid);
}
