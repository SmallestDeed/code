package com.sandu.panorama.dao;

import com.sandu.panorama.model.DesignPlanStoreRelease;
import com.sandu.panorama.model.input.DesignPlanStoreReleaseSearch;
import com.sandu.panorama.model.output.DesignPlanStoreReleaseVo;
import com.sandu.system.model.ResPic;

import com.sandu.system.model.po.ResPicPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignPlanStoreReleaseMapper {

    int insertSelective(DesignPlanStoreRelease record);

    int updateByPrimaryKeySelective(DesignPlanStoreRelease record);

    int deleteByPrimaryKey(Integer id);

    DesignPlanStoreRelease selectByPrimaryKey(Integer id);

    /**
     * 根据uuid查询720分享主图
     * @param uuid
     * @return
     */
    DesignPlanStoreReleaseVo getDesignPlanStoreRelease(String uuid);

    /**
     * 通过用户ID获取分享总数
     * @param search
     * @return
     */
    int getCountByUserId(DesignPlanStoreReleaseSearch search);

    /**
     * 通过用户ID获取分享列表
     * @param search
     * @return
     */
    List<DesignPlanStoreReleaseVo> getListByUserId(DesignPlanStoreReleaseSearch search);

    /**
     * 获取效果图方案封面 TEMP
     * @param mainDesignPlanId
     * @return picPath
     */
    String getCoverPicPath(Integer mainDesignPlanId);

    /**
     * TEMP
     * @param resPic
     * @return
     */
	int createResPic(ResPicPO resPic);

    /**
     * 回填资源表 businessId
     * @param resPic
     * @return
     */
	int updateResPic(ResPicPO resPic);
}
