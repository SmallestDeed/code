package com.sandu.service.banner.impl.biz;

import com.google.common.collect.Lists;
import com.sandu.api.banner.input.BaseBannerPositionIsExist;
import com.sandu.api.banner.input.BaseBannerListQuery;
import com.sandu.api.banner.model.BaseBannerAd;
import com.sandu.api.banner.model.BaseBannerPosition;
import com.sandu.api.banner.model.ResBannerPic;
import com.sandu.api.banner.output.BaseBannerWebListVO;
import com.sandu.api.banner.service.BaseBannerAdService;
import com.sandu.api.banner.service.BaseBannerPositionService;
import com.sandu.api.banner.service.ResBannerPicService;
import com.sandu.api.banner.service.biz.BaseBannerBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基础-广告 前端对外服务实现类
 * @author WangHaiLin
 * @date 2018/5/26  17:04
 */
@Service("baseBannerBizService")
public class BaseBannerBizServiceImpl implements BaseBannerBizService {
    @Autowired
    private BaseBannerAdService baseBannerAdService;
    @Autowired
    private BaseBannerPositionService positionService;
    @Autowired
    private ResBannerPicService picService;
    @Override
    public List<BaseBannerWebListVO> getBannerImgs(Integer type, String positionCode, Integer objId) {
        List<BaseBannerWebListVO> list= Lists.newArrayList();
        BaseBannerPositionIsExist isExist=new BaseBannerPositionIsExist();
        isExist.setCode(positionCode);
        isExist.setType(type);
        // 1.通过positionCode和type 查询position得到positionId
        BaseBannerPosition position = positionService.getPositionIsExist(isExist);
        if (null!=position){
            //构造第2步查询入参
            Integer positionId=position.getId();
            BaseBannerListQuery listQuery=new BaseBannerListQuery();
            listQuery.setPositionId(positionId);
            listQuery.setRefModelId(objId);
            //2.通过positionId和odjId查询的得到Banner集合
            List<BaseBannerAd> bannerLists = baseBannerAdService.getBannerByModelPosition(listQuery);
            if (null!=bannerLists){
                //3.遍历集合得到picId(第4步入参)
                for (BaseBannerAd banner:bannerLists){
                    Integer picId = banner.getResBannerPicId();
                    //处理输出对象
                    BaseBannerWebListVO bannerVo=new BaseBannerWebListVO();
                    bannerVo.setPositionId(positionId);
                    bannerVo.setPositionCode(position.getCode());
                    bannerVo.setPositionName(position.getName());
                    bannerVo.setType(position.getType());
                    bannerVo.setBannerId(banner.getId());
                    bannerVo.setBannerName(banner.getName());
                    bannerVo.setRefModelId(banner.getRefModelId());
                    bannerVo.setSkipPath(banner.getUrl());
                    if (null!=picId){
                        //4.通过picId查询得到resBannerPic
                        ResBannerPic resBannerPic = picService.getPicById(picId);
                        bannerVo.setRefPicId(resBannerPic.getId());
                        bannerVo.setPicPath(resBannerPic.getPicPath());
                        list.add(bannerVo);
                    }
                }
                return list;
            }
        }
        return null;
    }
}
