package com.sandu.api.banner.input;

import com.sandu.api.banner.model.po.BannerPO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础-广告列表查询入参实体
 * @author WangHaiLin
 * @date 2018/5/17  14:08
 */
@Data
public class BaseBannerQueryList implements Serializable {

    @ApiModelProperty(value="广告名称")
    private String bannerName;

    @ApiModelProperty(value="位置名称")
    private String positionName;

    @ApiModelProperty(value="图片名称")
    private String picName;

    @ApiModelProperty(value="每页数量:默认10")
    private Integer limit;

    @ApiModelProperty(value="当前页:默认第一页")
    private Integer page;

    public BannerPO getBannerPOFromQueryList(BaseBannerQueryList queryList){
        BannerPO bannerPO=new BannerPO();
        bannerPO.setBannerName(queryList.getBannerName());
        bannerPO.setPositionName(queryList.getPositionName());
        bannerPO.setPicName(queryList.getPicName());
       /* //不传的时候设置默认值
        bannerPO.setStart(queryList.getLimit()==null?0:queryList.getLimit());
        bannerPO.setPageSize(queryList.getPage()==null?10:queryList.getPage());*/
        return bannerPO;
    }

}
