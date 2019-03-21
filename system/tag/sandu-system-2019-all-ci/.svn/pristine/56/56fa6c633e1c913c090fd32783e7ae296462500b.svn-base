package com.sandu.api.banner.service;

import com.sandu.api.banner.input.ResBannerPicUpdate;
import com.sandu.api.banner.model.ResBannerPic;
import com.sandu.commons.LoginUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 广告-图片资源Service接口
 * @author WangHaiLin
 * @date 2018/5/16  20:51
 */
@Component
public interface ResBannerPicService {
    /**
     * 新增广告图片资源
     * @param picAdd 新增入参
     * @return int 新增数据Id
     */
    int addResBannerPic(ResBannerPic picAdd, LoginUser loginUser);

    /**
     * 删除广告图片
     * @param picId 广告图片Id
     * @return int删除操作结果
     */
    int deleteBannerPic(Integer picId,LoginUser loginUser);

    /**
     * 修改广告图片信息
     * @param update 修改操作入参
     * @return int 修改操作结果
     */
    int updateBannerPic(ResBannerPicUpdate update,LoginUser loginUser);

    /**
     * 通过图片Id查询图片信息
     * @param picId 图片Id
     * @return ResBannerPic
     */
    ResBannerPic getPicById(Integer picId);


    List<ResBannerPic> getBannerPicList();

}
