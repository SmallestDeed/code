package com.sandu.service.banner.impl.manage;

import com.sandu.api.banner.input.BaseBannerListQuery;
import com.sandu.api.banner.model.BaseBannerAd;
import com.sandu.api.banner.model.ResBannerPic;
import com.sandu.api.banner.output.MiniProgramBannerVO;
import com.sandu.api.banner.service.BaseBannerAdService;
import com.sandu.api.banner.service.ResBannerPicService;
import com.sandu.api.banner.service.manage.BaseBannerManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WangHaiLin
 * @date 2018/6/22  17:49
 */
@Service("baseBannerManageService")
public class BaseBannerManageServiceImpl implements BaseBannerManageService {

    @Autowired
    private BaseBannerAdService baseBannerAdService;

    @Autowired
    private ResBannerPicService resBannerPicService;

    /**
     * 查询逻辑
     * 1.通过refModelId和positionId 查询base_banner_ad表，得到Banner列表
     * 2.循环遍历Banner列表，得到Banner图片Id
     * 3.通过图片Id查询res_banner_pic表，得到图片地址
     * 4.构造输出实体并返回
     *
     * @param refModelId 企业Id
     * @param positionId 位置Id
     * @return
     */
    @Override
    public List<MiniProgramBannerVO> getBannerList(Integer refModelId, Integer positionId) {
        BaseBannerListQuery baseBannerListQuery =new BaseBannerListQuery();
        baseBannerListQuery.setPositionId(positionId);
        baseBannerListQuery.setRefModelId(refModelId);
        List<BaseBannerAd> baseBannerAdList = baseBannerAdService.getBannerByModelPosition(baseBannerListQuery);
        if (baseBannerAdList!=null&&baseBannerAdList.size()>0){
            List<MiniProgramBannerVO> list=new ArrayList<>();
            for (BaseBannerAd banner:baseBannerAdList) {
                MiniProgramBannerVO miniProgramBannerVO=new MiniProgramBannerVO();
                miniProgramBannerVO.setSkipPath(banner.getUrl());
                miniProgramBannerVO.setBannerAdId(banner.getId());
                miniProgramBannerVO.setName(banner.getName());
                Integer picId = banner.getResBannerPicId();
                ResBannerPic pic= resBannerPicService.getPicById(picId);
                if (null!=pic){
                    miniProgramBannerVO.setImageId(picId);
                    miniProgramBannerVO.setImagePath(pic.getPicPath());
                    list.add(miniProgramBannerVO);
                }
            }
            return list;
        }
        return null;
    }

}
