package com.nork.design.service.impl;

import com.nork.design.dao.DecorateDesignPlanImgMapper;
import com.nork.design.model.DecorateDesignPlanImgInfo;
import com.nork.design.service.DecorateDesignPlanImgService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 预约--方案图片信息
 * @author WangHaiLin
 * @date 2018/10/20  17:58
 */
@Service("decorateDesignPlanImgService")
public class DecorateDesignPlanImgServiceImpl implements DecorateDesignPlanImgService {

    private static Logger LOGGER = Logger.getLogger(DecorateDesignPlanImgServiceImpl.class);

    private final static String LOGPREFIX = "[查询方案图片]:";

    @Autowired
    private DecorateDesignPlanImgMapper mapper;
    /**
     *  通过Id查询效果图方案封面图
     * @param planId 方案Id
     * @return
     */
    @Override
    public DecorateDesignPlanImgInfo getRecommendedById(Long planId) {
        LOGGER.info(LOGPREFIX+ "效果图方案查询  参数Id: " +planId);
        return  mapper.getRecommendedById(planId);
    }

    /**
     *  通过Id查询全屋方案封面图
     * @param planId 方案Id
     * @return
     */
    @Override
    public DecorateDesignPlanImgInfo getWholeHouseById(Long planId) {
        LOGGER.info(LOGPREFIX+ "全屋方案查询  参数Id: " +planId);
        return mapper.getWholeHouseById(planId);
    }


}
