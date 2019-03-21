package com.sandu.system.service.impl;

import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;
import com.sandu.system.service.ResourceCacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * 系统资源缓存层
 * @author qiu.jun
 * @date 2016-05-10
 */
@Service("resourceCacherService")
public class ResourceCacherServiceImpl implements ResourceCacherService {

    @Autowired
    private ResPicService resPicService;

    @Override
    public ResPic getPic(int id) {
        return resPicService.get(id);
    }
}
