package com.sandu.service.proprietorinfo.dao;


import com.sandu.api.area.model.BaseArea;
import com.sandu.api.area.output.BaseAreaListVO;
import com.sandu.api.proprietorinfo.input.ProprietorInfoUpdate;
import com.sandu.api.proprietorinfo.model.ProprietorInfo;
import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.user.model.SysUser;
import com.sandu.system.model.SysDictionary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProprietorInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(ProprietorInfo record);

    int insertSelective(ProprietorInfo record);

    ProprietorInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProprietorInfo record);

    int updateByPrimaryKey(ProprietorInfo record);

    List<ProprietorInfo> getList(@Param("proprietorInfoQuery") ProprietorInfo proprietorInfoQuery,
                                 @Param("start") Integer start,
                                 @Param("limit") Integer limit);

    List<BaseArea> getBaseAreaListByLevelId(Integer levelId);

    Integer updateProprietorInfo(ProprietorInfoUpdate proprietorInfoUpdate);

	List<ProprietorInfo> selectByIds(@Param("ids")List<Integer> ids);

    String getRenderScenePlanPic(Integer designplanId);

    String getFullHousePlanPic(Integer designplanId);

    CompanyShop getShopById(Integer id);

    List<SysDictionary> findAllByType(String type);

    SysDictionary findOneByTypeAndValue(@Param("type") String type, @Param("value") Integer value);

    BaseAreaListVO queryAreaByCode(String code);

    List<ProprietorInfo> listWithCustomerType(@Param("types") List<Integer> types);

    Integer getListCount(ProprietorInfo proprietorInfoQuery);

    SysUser getSysUserById(Integer id);

    Integer deleteByIds(@Param("ids") List<Integer> ids);
}