package com.sandu.api.base.service;

import com.sandu.api.base.input.UserDecorationPlanVo;
import com.sandu.api.base.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SysUserService {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    String getUserDefaultPic(int i);

    List<UserDecorationPlanVo> getAllDemandPlanAndHouse(String ownerUserSessionId, Long userId);

    List<UserDecorationPlanVo> getAllMsgPlanAndHouse(String ownerUserSessionId, String loginUserSessionId);

    List<UserDecorationPlanVo> getDemandHouse(@Param("currentChatUserSessionId") String currentChatUserSessionId, @Param("userId")Integer userId);

    Map<Long, String> id2headPic(List<Long> ids);
}
