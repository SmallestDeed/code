package com.sandu.design.service.impl;

import com.sandu.design.dao.DesignRenderRoamMapper;
import com.sandu.design.model.DesignRenderRoam;
import com.sandu.design.service.DesignRenderRoamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("designRenderRoamService")
@Transactional
public class DesignRenderRoamServiceImpl implements DesignRenderRoamService {

    @Autowired
    private DesignRenderRoamMapper designRenderRoamMapper;

    /**
     * 通过封面图获取
     * @return
     */
    @Override
    public DesignRenderRoam selectByScreenShotId(Integer screenShotId) {
        return designRenderRoamMapper.selectByScreenShotId(screenShotId);
    }
}
