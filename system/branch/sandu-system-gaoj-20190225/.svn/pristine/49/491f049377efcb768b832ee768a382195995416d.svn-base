package com.sandu.service.servicepurchase.dao;


import com.sandu.api.user.model.UserJurisdiction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserJurisdictionMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);
    int deleteLogicByPrimaryKey(Integer id);
    int delUserJurisdictionByUserId(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(UserJurisdiction record);

    /**
     *
     * @mbggenerated
     */
    UserJurisdiction selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserJurisdiction record);


    List<UserJurisdiction> selectListSelective(UserJurisdiction record);

    Integer selectFranchiserJUris(@Param("id") Integer id, @Param("type") String type);

    Integer getFranchiserRoleCountByTypeAndId(@Param("id") Integer id, @Param("type") String type);

    /**
     * 批量保存用户平台数据
     * @auth xiaoxc-2018-04-23
     * @param userJurisdictionList
     */
    void insertUserPlatformList(@Param("userJurisdictionList") List<UserJurisdiction> userJurisdictionList);

    /**
     * 根据企业ID和平台code查询用户数量
     * @auth xiaoxc-2018-04-24
     * @param companyId
     * @param type
     * @return
     */
    Integer selectCompanyUserCount(@Param("companyId") Integer companyId, @Param("type") String type);
}