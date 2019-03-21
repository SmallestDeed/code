package com.sandu.base.service.impl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.sandu.banner.input.BaseBannerListQuery;
import com.sandu.banner.input.BaseBannerPositionQuery;
import com.sandu.banner.model.BaseBannerAd;
import com.sandu.banner.model.BaseBannerPosition;
import com.sandu.banner.model.ResBannerPic;
import com.sandu.banner.output.BaseBannerListVO;
import com.sandu.system.service.BaseBannerBizService;
import com.sandu.system.service.BaseBannerPositionService;
import com.sandu.system.service.BaseBannerAdService;
import com.sandu.system.service.ResBannerPicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基础-广告-对外服务接口实现
 * @author WangHaiLin
 * @date 2018/5/15  11:33
 */
@Service("baseBannerBizService")
public class BaseBannerBizServiceImpl implements BaseBannerBizService {
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[基础-广告-对外服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseBannerBizServiceImpl.class);

    @Autowired
    private BaseBannerAdService baseBannerAdService;
    @Autowired
    private BaseBannerPositionService positionService;
    @Autowired
    private ResBannerPicService picService;

    @Override
    public List<BaseBannerListVO> getBannerImgs(Integer type, String positionCode, Integer objId) {
        List<BaseBannerListVO> list= Lists.newArrayList();
        BaseBannerPositionQuery query=new BaseBannerPositionQuery();
        query.setCode(positionCode);
        query.setType(type);
        // 1.查询position得到positionId
        logger.debug(CLASS_LOG_PREFIX,"查询位置信息入参：",gson.toJson(query));
        BaseBannerPosition position = positionService.getPosition(query);
        logger.debug(CLASS_LOG_PREFIX,"查询位置信息结果：",gson.toJson(position));
        if (null!=position){
            //构造第2步查询入参
            Integer positionId=position.getId();
            BaseBannerListQuery listQuery=new BaseBannerListQuery();
            listQuery.setPositionId(positionId);
            listQuery.setRefModelId(objId);
            //2.通过positionId和odjId查询的得到Banner集合
            List<BaseBannerAd> bannerLists = baseBannerAdService.getBannerByPosition(listQuery);
            logger.debug(CLASS_LOG_PREFIX,"查询Banner集合结果：",gson.toJson(bannerLists));
            if (null!=bannerLists){
                //3.遍历集合得到picId(第4步入参)
                for (BaseBannerAd banner:bannerLists){
                    Integer picId = banner.getResBannerPicId();
                    //处理输出对象
                    BaseBannerListVO bannerVo=new BaseBannerListVO();
                    bannerVo.setPositionId(positionId);
                    bannerVo.setPositionCode(position.getCode());
                    bannerVo.setPositionName(position.getName());
                    bannerVo.setType(position.getType());
                    bannerVo.setBannerId(banner.getId());
                    bannerVo.setBannerName(banner.getName());
                    bannerVo.setRefModelId(banner.getRefModelId());
                    if (null!=picId){
                        //4.通过picId查询得到resBannerPic
                        logger.debug(CLASS_LOG_PREFIX,"查询图片资源入参：",gson.toJson(picId));
                        ResBannerPic resBannerPic = picService.getBannerPicById(picId);
                        logger.debug(CLASS_LOG_PREFIX,"查询图片资源结果：",gson.toJson(resBannerPic));
                        bannerVo.setRefPicId(resBannerPic.getId());
                        bannerVo.setPicPath(resBannerPic.getPicPath());
                        list.add(bannerVo);
                    }
                }
                logger.debug(CLASS_LOG_PREFIX,"获取Banner列表通用方法返回结果：",gson.toJson(list));
                return list;
            }
        }
        return null;
    }
}
