package com.sandu.api.base.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.base.input.InteractiveZoneTopicAdd;
import com.sandu.api.base.input.InteractiveZoneTopicQuery;
import com.sandu.api.base.input.InteractiveZoneTopicUpdate;
import com.sandu.api.base.input.TransportWriteInput;
import com.sandu.api.base.model.InteractiveZoneTopic;
import com.sandu.api.base.output.InteractiveZoneTopicVO1;
import com.sandu.api.base.output.InteractiveZoneTopicDetail;
import com.sandu.api.user.model.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @ClassName: InteractiveZoneTopicService
 * @Auther: gaoj
 * @Date: 2019/3/13 09:59
 * @Description:
 * @Version 1.0
 */
@Component
public interface InteractiveZoneTopicService {

    PageInfo<InteractiveZoneTopicVO1> list(InteractiveZoneTopicQuery interactiveZoneTopicQuery);

    Integer getBlockTypeValue(String blockTypeValueKey);

    Integer totalCount(InteractiveZoneTopicQuery interactiveZoneTopicQuery);

    Integer add(InteractiveZoneTopicAdd interactiveZoneTopicAdd, SysUser sysUser);

    Integer update(InteractiveZoneTopicUpdate interactiveZoneTopicUpdate, SysUser sysUser);

    InteractiveZoneTopic get(Long id);

    boolean transportShopWriteHandle(List<TransportWriteInput> inputs, SysUser loginUser);

    InteractiveZoneTopicDetail getDetails(Integer id);
}
