package com.sandu.api.banner.model;

import com.sandu.api.banner.output.BaseBannerAdVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础-广告 model
 * @author WangHaiLin
 * @date 2018/5/16  14:16
 */
@Data
public class BaseBannerAd implements Serializable {
    /**
     * 主键Id
     */
    private Integer id;
    /**
     * 所在位置
     */
    private Integer positionId;
    /**
     * banner名称
     */
    private String name;
    /**
     * 资源Id
     */
    private Integer resBannerPicId;
    /**
     *如果位置类型为店铺,则此处为店铺id, 如果为企业,则为企业id,如果为平台,则为平台id
     */
    private Integer refModelId;
    /**
     * 状态(0:关闭;1:开启)
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sequence;
    /**
     * 系统编码
     */
    private String sysCode;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改者
     */
    private String modifier;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 跳转路径
     */
    private String url;

    public BaseBannerAdVO getBannerVoFromBanner(BaseBannerAd banner){
        BaseBannerAdVO bannerVO=new BaseBannerAdVO();
        bannerVO.setBannerId(banner.getId());
        bannerVO.setBannerName(banner.getName());
        bannerVO.setPositionId(banner.getPositionId());
        bannerVO.setResBannerPicId(banner.getResBannerPicId());
        bannerVO.setRefModelId(banner.getRefModelId());
        bannerVO.setBannerSequence(banner.getSequence());
        bannerVO.setBannerStatus(banner.getStatus());
        bannerVO.setBannerSysCode(banner.getSysCode());
        bannerVO.setCreator(banner.getCreator());
        bannerVO.setGmtCreate(banner.getGmtCreate());
        bannerVO.setModifier(banner.getModifier());
        bannerVO.setGmtModified(banner.getGmtModified());
        bannerVO.setIsDeleted(banner.getIsDeleted());
        return bannerVO;
    }
}
