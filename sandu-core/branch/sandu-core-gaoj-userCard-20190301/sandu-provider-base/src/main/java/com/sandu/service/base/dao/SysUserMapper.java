package com.sandu.service.base.dao;

import com.sandu.api.base.input.UserDecorationPlanVo;
import com.sandu.api.base.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    String selectUserDefaultPic(int i);

    List<UserDecorationPlanVo> selectAllDemandPlanAndHouse(@Param("ownerUserSessionId") String ownerUserSessionId,@Param("userId") Long userId);

    List<UserDecorationPlanVo> selectAllMsgPlanAndHouse(@Param("ownerUserSessionId") String ownerUserSessionId, @Param("loginUserSessionId") String loginUserSessionId);

    List<UserDecorationPlanVo> getDemandHouse(@Param("currentChatUserSessionId") String currentChatUserSessionId, @Param("userId")Integer userId);
}