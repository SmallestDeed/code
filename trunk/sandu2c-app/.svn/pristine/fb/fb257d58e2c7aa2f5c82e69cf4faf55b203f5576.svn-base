package com.sandu.designplan.service.impl;

import com.sandu.designplan.dao.DesignPlanRenderSceneMapper;
import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.model.DesignPlanRenderScene;
import com.sandu.designplan.service.DesignPlanRenderSceneService;
import com.sandu.designplan.vo.MydecorationPlanVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @version V1.0
 * @Title: DesignPlanServiceImpl.java
 * @Package com.sandu.design.service.impl
 * @Description:设计模块-设计方案ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-07-03 17:09:51
 */
@Service("designPlanRenderSceneService")
public class DesignPlanRenderSceneServiceImpl implements DesignPlanRenderSceneService {

    private static Logger logger = LoggerFactory.getLogger(DesignPlanRenderSceneServiceImpl.class);

    @Autowired
    private DesignPlanRenderSceneMapper designPlanRenderSceneMapper;

    @Override
    public int add(DesignPlanRenderScene designPlanEctype) {
        designPlanRenderSceneMapper.insert(designPlanEctype);
        return designPlanEctype.getId();
    }

    @Override
    public void update(DesignPlanRenderScene designPlanEctype) {
        if (designPlanEctype.getId() == null) {
            return;
        }
        designPlanRenderSceneMapper.update(designPlanEctype);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public DesignPlanRenderScene get(Integer id) {
        return designPlanRenderSceneMapper.get(id);
    }

    /**
     * 解析固定格式字符串
     */
    private Map<String, String> readFileDesc(String fileDesc) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(fileDesc)) {
            String[] strs = fileDesc.split(";");
            for (String str : strs) {
                if (str.split(":").length == 2) {
                    map.put(str.split(":")[0].trim(), str.split(":")[1].trim());
                }
            }
        }
        return map;
    }

    /**
     * 通过产品品牌  和 授权码用户id 查询 设计方案
     *
     * @return
     */
    public int getVendorCount(DesignPlanRenderScene designPlanRenderScene) {
        return designPlanRenderSceneMapper.getVendorCount(designPlanRenderScene);
    }

    /**
     * 通过产品品牌  和 授权码用户id 查询 设计方案
     *
     * @return
     */
    public List<DesignPlanRenderScene> getVendorList(DesignPlanRenderScene designPlanRenderScene) {
        return designPlanRenderSceneMapper.getVendorList(designPlanRenderScene);
    }

	@Override
	public int selectVendorCountV2(DesignPlanRenderScene designPlanRenderScene) {
		return designPlanRenderSceneMapper.getVendorCountV2(designPlanRenderScene);
	}

	@Override
	public List<DesignPlanRenderScene> selectVendorListV2(DesignPlanRenderScene designPlanRenderScene) {

		return designPlanRenderSceneMapper.getVendorListV2(designPlanRenderScene);
	}

    @Override
    public MydecorationPlanVo selectDesignPlanInfo(Integer planId) {
        return designPlanRenderSceneMapper.selectDesignPlanInfo(planId);
    }

    @Override
    public List<MydecorationPlanVo> selectDesignPlanInfoList(List<Integer> signleSpaceScenePlanIds) {
        return designPlanRenderSceneMapper.selectDesignPlanInfoList(signleSpaceScenePlanIds);
    }


}
