package com.nork.pano.dao;

import com.nork.pano.model.DesignPlanStoreRelease;
import com.nork.pano.model.output.DesignPlanStoreReleaseVo;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignPlanStoreReleaseMapper {

    int updateByPrimaryKeySelective(DesignPlanStoreRelease record);

    /**
     * 根据uuid查询720分享主图
     * @param uuid
     * @return
     */
    DesignPlanStoreReleaseVo getDesignPlanStoreRelease(String uuid);

    /**
     * 根据Id查询720分享信息
     * @param id
     * @return
     */
    DesignPlanStoreReleaseVo selectByPrimaryKey(Integer id);

/*
    DesignPlanStoreReleaseVo selectDesignPlanStoreReleaseVoById(Integer id);
*/
}
