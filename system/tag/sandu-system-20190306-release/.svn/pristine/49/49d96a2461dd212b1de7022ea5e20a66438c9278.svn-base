package com.sandu.service.servicepurchase.dao;

import com.sandu.api.servicepurchase.input.query.ServicesRoleRelQuery;
import com.sandu.api.servicepurchase.model.ServicesRoleRel;
import com.sandu.api.servicepurchase.model.bo.ServicesPurchaseListBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ServicesRoleRelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServicesRoleRel record);

    int insertSelective(ServicesRoleRel record);


    ServicesRoleRel selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(ServicesRoleRel record);

    int updateByPrimaryKey(ServicesRoleRel record);

    List<ServicesRoleRel> getByServiceId(@Param("id") Long id);

    List<ServicesPurchaseListBO> getBePurchasedServices(@Param("companyId") Integer companyId);

    List<ServicesRoleRel> queryByOption(ServicesRoleRelQuery servicesRoleRelQuery);

    /**
     * 根据套餐id删除角色
     * @param servicesId
     * @return
     */
    int deleteByServicesId(@Param("servicesId") String servicesId);

    /**
     * 根据套餐id和角色删除关联
     * @param servicesId
     * @param roleId
     * @return
     */
    int deleteByServicesIdAndRoleId(@Param("servicesId")Integer servicesId,@Param("roleId") Integer roleId);

    Set<Integer> selectPlatformIdsByRoleId(@Param("ids") Set<Integer> ids);

    void insertUserPlatformRole(@Param("platformId") Integer id, @Param("servicesId")Integer servicesId);

    Set<Integer> getRoleIdsByServicesId(@Param("servicesId") Integer servicesId);

    Set<Integer> getPackageUserByServicesId(@Param("servicesId") Integer servicesId);

    void delUserJurisdictionByUserIdANDPlatformIds(@Param("userid")Integer userid, @Param("platformIds")Set<Integer> platformIds);

    void delPackageUserPlatformRole(@Param("servicesId") Integer servicesId, @Param("platformIds") Set<Integer> platformIds);
}