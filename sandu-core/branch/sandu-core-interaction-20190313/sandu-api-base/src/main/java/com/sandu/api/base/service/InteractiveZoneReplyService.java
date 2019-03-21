package com.sandu.api.base.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.base.input.InteractiveZoneReplyAdd;
import com.sandu.api.base.input.InteractiveZoneReplyQuery;
import com.sandu.api.base.model.InteractiveZoneReply;
import com.sandu.api.base.output.InteractiveZoneReplyVO;
import com.sandu.api.user.model.SysUser;
import org.springframework.stereotype.Component;

/**
 * @ClassName: InteractiveZoneReplyService
 * @Auther: gaoj
 * @Date: 2019/3/13 17:30
 * @Description:
 * @Version 1.0
 */
@Component
public interface InteractiveZoneReplyService {
    Integer add(InteractiveZoneReplyAdd interactiveZoneReplyAdd, SysUser sysUser);

    Integer update(InteractiveZoneReplyAdd interactiveZoneReplyAdd, SysUser sysUser);

	PageInfo<InteractiveZoneReplyVO> query(InteractiveZoneReplyQuery query);

    InteractiveZoneReply get(Long id);
}
