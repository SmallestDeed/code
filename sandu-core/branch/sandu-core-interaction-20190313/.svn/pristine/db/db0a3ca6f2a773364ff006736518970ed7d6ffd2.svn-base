package com.sandu.service.base.impl;

import com.sandu.api.base.common.util.EntityCopyUtils;
import com.sandu.api.base.model.*;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.base.output.InteractiveZoneMsgVo;
import com.sandu.api.base.service.InteractiveZoneMsgService;
import com.sandu.api.base.service.ResPicService;
import com.sandu.api.user.service.SysUserService;
import com.sandu.service.base.dao.InteractiveZoneMsgMapper;
import com.sandu.service.base.dao.InteractiveZoneReplyMapper;
import com.sandu.service.base.dao.InteractiveZoneTopicMapper;
import com.sandu.websocket.SocketIOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("interactiveZoneMsgService")
public class InteractiveZoneMsgServiceImpl implements InteractiveZoneMsgService {
    private final String CLASS_LOG_PREFIX = "【互动信息服务】";
    @Autowired
    private InteractiveZoneMsgMapper interactiveZoneMsgMapper;
    @Autowired
    private InteractiveZoneReplyMapper interactiveZoneReplyMapper;
    @Autowired
    private InteractiveZoneTopicMapper interactiveZoneTopicMapper;
    @Autowired
    private ResPicService resPicService;
    @Resource(name = "wangWangSysUserService")
    private SysUserService sysUserService;

    @Override
    public Integer getListCount(InteractiveZoneMsg interactiveZoneMsg) {
        return interactiveZoneMsgMapper.getListCount(interactiveZoneMsg);
    }

    @Override
    public List<InteractiveZoneMsg> getList(InteractiveZoneMsg interactiveZoneMsg, Integer curPage, Integer pageSize) {
        Integer start = null;
        Integer limit = null;
        if (curPage != null && pageSize != null) {
            start = (curPage - 1) * pageSize;
            limit = pageSize;
        }
        return interactiveZoneMsgMapper.getList(interactiveZoneMsg, start, limit);
    }

    @Override
    public List<InteractiveZoneMsgVo> transToVo(List<InteractiveZoneMsg> interactiveZoneMsgList) {
        List<InteractiveZoneMsgVo> voList = new ArrayList<>();
        for (InteractiveZoneMsg interactiveZoneMsg : interactiveZoneMsgList) {
            InteractiveZoneMsgVo interactiveZoneMsgVo = new InteractiveZoneMsgVo();
            EntityCopyUtils.copyData(interactiveZoneMsg, interactiveZoneMsgVo);

            Long userId = Long.parseLong(interactiveZoneMsg.getCreator());
            SysUser user = sysUserService.getUserById(userId);
            ResPic pic = resPicService.selectByPrimaryKey(user.getPicId().intValue());

            interactiveZoneMsgVo.setName(user.getNickName());
            interactiveZoneMsgVo.setHeadPic(pic.getPicPath());
            if (interactiveZoneMsgVo.getOperateType() == 0) {
                interactiveZoneMsgVo.setMyProblemContent(interactiveZoneTopicMapper.selectByPrimaryKey(interactiveZoneMsgVo.getTopicId()).getContent());
            } else if (interactiveZoneMsgVo.getOperateType() == 1) {
                interactiveZoneMsgVo.setMyReplyContent(interactiveZoneReplyMapper.selectByPrimaryKey(interactiveZoneMsgVo.getReplyId()).getContent());
            }
        }
        return voList;
    }

    @Override
    public Integer insert(InteractiveZoneMsg interactiveZoneMsg) {
        return interactiveZoneMsgMapper.insertSelective(interactiveZoneMsg);
    }

    @Override
    public void sendMessage(SysUser user) {
        PushMessageInfo pushMessageInfo = new PushMessageInfo();
        pushMessageInfo.setTargetSessionId(user.getUuid());
        pushMessageInfo.setMsgCode(SocketIOUtil.IM_PUSH_INTERACTIVE_MSG_CODE);
        pushMessageInfo.setMsgContent("邱土强喊你看消息!!!");
        SocketIOUtil.sendEventMessage(SocketIOUtil.IM_PUSH_MSG_EVENT, pushMessageInfo);
    }

    @Override
    public Integer addMessage(Integer operateType, Long id, Integer blockType, Integer businessType, Long sourceUserId, Long targetUserId) {
        InteractiveZoneMsg msg = new InteractiveZoneMsg();
        if (operateType == 0) {
            msg.setTopicId(id);
        } else if (operateType == 1) {
            msg.setReplyId(id);
        }
        msg.setPublishUserId(targetUserId.intValue());
        msg.setOperateType(operateType);
        msg.setBlockType(blockType);
        msg.setBusinessType(businessType);
        msg.setIsRead(0);
        msg.setOperateUserId(sourceUserId.intValue());
        msg.setCreator(sourceUserId + "");
        msg.setGmtCreate(new Date());
        msg.setModifier(sourceUserId + "");
        msg.setGmtModified(new Date());
        msg.setIsDeleted(0);
        return this.insert(msg);
    }

    @Override
    public Integer readMessages(List<Long> messageIdList) {
        int num = 0;
        Date date = new Date();
        for (Long id : messageIdList) {
            InteractiveZoneMsg msgUpdateParam = new InteractiveZoneMsg();
            msgUpdateParam.setId(id);
            msgUpdateParam.setIsRead(1);
            msgUpdateParam.setGmtModified(date);
            if (interactiveZoneMsgMapper.updateByPrimaryKeySelective(msgUpdateParam) == 1) {
                num ++;
            }
        }
        return num;
    }
}
