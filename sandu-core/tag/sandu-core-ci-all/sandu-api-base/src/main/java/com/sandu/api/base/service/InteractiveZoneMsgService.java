package com.sandu.api.base.service;

import com.sandu.api.base.model.InteractiveZoneMsg;
import com.sandu.api.base.output.InteractiveZoneMsgVo;
import com.sandu.api.user.model.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InteractiveZoneMsgService {
    Integer getListCount(InteractiveZoneMsg interactiveZoneMsg);

    List<InteractiveZoneMsg> getList(InteractiveZoneMsg interactiveZoneMsg, Integer curPage, Integer pageSize);

    List<InteractiveZoneMsgVo> transToVo(List<InteractiveZoneMsg> interactiveZoneMsgList);

    Integer readMessages(List<Long> messageIdList);

    Integer insert(InteractiveZoneMsg interactiveZoneMsg);

    void sendMessage(SysUser user);

    Integer addMessage(Integer operateType, Long id, Integer blockType, Integer businessType, Long sourceUserId, Long targetUserId);
}
