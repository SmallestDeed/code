package com.sandu.home.service.impl;


import com.sandu.common.util.Utils;
import com.sandu.design.model.DesignTemplet;
import com.sandu.design.model.search.DesignTempletSearch;
import com.sandu.designtemplate.service.DesignTempletService;
import com.sandu.home.dao.HouseSpaceNewMapper;
import com.sandu.home.dao.SpaceCommonMapper;
import com.sandu.home.model.BaseHouse;
import com.sandu.home.model.HouseSpaceResult;
import com.sandu.home.model.SpaceCommon;
import com.sandu.home.service.SpaceCommonService;
import com.sandu.system.model.ResHousePic;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResHousePicService;
import com.sandu.system.service.ResPicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.sandu.util.Commoner.isEmpty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;


/**
 * @version V1.0
 * @Title: SpaceCommonServiceImpl.java
 * @Package com.sandu.home.service.impl
 * @Description:户型房型-通用空间表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 15:48:39
 */
@Service("spaceCommonService")
public class SpaceCommonServiceImpl implements SpaceCommonService {

    @Autowired
    private SpaceCommonMapper spaceCommonMapper;
    @Autowired
    private DesignTempletService designTempletService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private HouseSpaceNewMapper houseSpaceNewMapper;
    @Autowired
    private ResHousePicService resHousePicService;

    /**
     * 新增数据
     *
     * @param spaceCommon
     * @return int
     */
    @Override
    public int add(SpaceCommon spaceCommon) {
        spaceCommonMapper.insertSelective(spaceCommon);
        return spaceCommon.getId();
    }

    /**
     * 更新数据
     *
     * @param spaceCommon
     * @return int
     */
    @Override
    public int update(SpaceCommon spaceCommon) {
        return spaceCommonMapper.updateByPrimaryKeySelective(spaceCommon);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return spaceCommonMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return SpaceCommon
     */
    @Override
    public SpaceCommon get(Integer id) {
        return spaceCommonMapper.selectByPrimaryKey(id);
    }

    @Override
    public int getSpaceSearchCount(SpaceCommon spaceCommon) {
        int realCount = spaceCommonMapper.spaceSearchCount(spaceCommon);
        return realCount;
    }

    @Override
    public List<SpaceCommon> getSpaceSearchList(SpaceCommon spaceCommon) {
        return spaceCommonMapper.spaceSearchList(spaceCommon);
    }

    @Override
    public int getHouseSpaceListCount(SpaceCommon spaceCommon) {
        int realCount = spaceCommonMapper.getHouseSpaceListCount(spaceCommon);
        return realCount;
    }

    @Override
    public List<HouseSpaceResult> getPaginatedHouseSpaceList(SpaceCommon spaceCommon) {
        return spaceCommonMapper.getPaginatedHouseSpaceList(spaceCommon);
    }

    public SpaceCommon setPicParams(SpaceCommon spaceCommon) {
        DesignTempletSearch designTempletSearch = new DesignTempletSearch();
        designTempletSearch.setStart(0);
        designTempletSearch.setLimit(1);
        designTempletSearch.setLevelLimitCount(-1);
        designTempletSearch.setSpaceCommonId(spaceCommon.getId());
        designTempletSearch.setIsRecommend(new Integer(1));
        designTempletSearch.setIsDeleted(0);
        /*logger.error("------临时log:spaceCommon.getId():" + spaceCommon.getId());*/
        List<DesignTemplet> list = designTempletService.getPaginatedList(designTempletSearch);
        if (list == null || list.size() == 0)
            return null;
        DesignTemplet designTemplet = list.get(0);
		/*logger.error("------临时log:designTemplet.getId():" + designTemplet.getId());*/
		/* 平面图 */
        String effectPlanIdsStr = designTemplet.getEffectPlanIds();
		/*logger.error("------临时log:designTemplet.getEffectPlanIds():" + designTemplet.getEffectPlanIds());*/
        if (StringUtils.isNotBlank(effectPlanIdsStr)) {
            if (effectPlanIdsStr.indexOf(",") != -1)
                effectPlanIdsStr = effectPlanIdsStr.substring(0,
                        effectPlanIdsStr.indexOf(","));
            Integer effectPlanId = Integer.valueOf(effectPlanIdsStr);
            ResPic resPic = resPicService.get(effectPlanId);
            if (resPic != null) {
				/*logger.error("------临时log:resPic.getId():" + resPic.getId());*/
                spaceCommon.setViewPlanPath(resPic.getPicPath());
                // Utils.getSmallPicId(resHousePic, "ipad")
                Integer effectPlanSmallId = Utils.getSmallPicId(resPic, "ipad");
                if (effectPlanSmallId != null) {
                    ResPic resPicSmall = resPicService.get(effectPlanSmallId);
                    if (resPicSmall != null)
                        spaceCommon.setViewPlanSmallPath(resPicSmall
                                .getPicPath());
                }
            }
        }
		/* 平面图->end */
        return spaceCommon;
    }

    @Override
    public HouseSpaceResult getFullHouseSpace(Integer houseId) {

        HouseSpaceResult fullHouseSpace = new HouseSpaceResult();
        //获取全屋空间类型id，并赋值
        Integer fullHouseSpaceFunctionId = houseSpaceNewMapper.getFullHouseSpaceFunctionId();
        fullHouseSpace.setSpaceFunctionId(fullHouseSpaceFunctionId);

        //获取户型图片并赋值
        BaseHouse baseHouse = spaceCommonMapper.getHousePicById(houseId);

        // 取户型的缩略图和大图
        if (baseHouse.getSnapPicId() != null && baseHouse.getSnapPicId() > 0) {
            // 截图图片处理 v3.9.4(draw-v1.0.2)
            ResHousePic resHousePic = resHousePicService.get(baseHouse.getSnapPicId());
            baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
            baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getPicPath() : "");
        } else if (baseHouse.getPicRes1Id() != null && baseHouse.getPicRes1Id() > 0) {
            ResHousePic resHousePic = resHousePicService.get(baseHouse.getPicRes1Id());
            baseHouse.setThumbnailPath(resHousePic != null ? resHousePic.getThumbnailPath() : "");
            baseHouse.setLargeThumbnailPath(resHousePic != null ? resHousePic.getLargeThumbnailPath() : "");
        } else {
            baseHouse.setThumbnailPath("/default/huxing_bg_pic_no.png");
            baseHouse.setLargeThumbnailPath("/default/huxing_bg_pic_no.png");
        }

        fullHouseSpace.setViewPlanPath(baseHouse.getLargeThumbnailPath());
        fullHouseSpace.setViewPlanSmallPath(baseHouse.getThumbnailPath());

        return fullHouseSpace;
    }

    @Override
    public Map<Integer, Integer> idAndSpaceTypeMap(List<Integer> spaceCommonIds) {
        spaceCommonIds = spaceCommonIds.stream().filter(Objects::nonNull).distinct().collect(toList());
        if (isEmpty(spaceCommonIds)) {
            return Collections.emptyMap();
        }
        List<SpaceCommon> spaceCommons = spaceCommonMapper.listByIds(spaceCommonIds);
        return spaceCommons.stream().collect(toMap(SpaceCommon::getId, SpaceCommon::getSpaceFunctionId));
    }
}
