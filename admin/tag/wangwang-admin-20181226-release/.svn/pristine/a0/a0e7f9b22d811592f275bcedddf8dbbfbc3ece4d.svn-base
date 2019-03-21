package com.sandu.api.companyshop.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/11/29 14:10
 * @since 1.8
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Companyshopoffline implements Serializable {

    /**
     * 认证ID
     */
    private Integer id;
    /**
     * 厂商企业ID
     */
    private Integer companyId;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 省编码
     */
    private String provinceCode;
    /**
     * 市编码
     */
    private String cityCode;
    /**
     * 区编码
     */
    private String areaCode;
    /**
     * 街道编码
     */
    private String streetCode;
    /**
     * 地区长编码
     */
    private String longAreaCode;
    /**
     * 地区长编码名称
     */
    private String longAreaName;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 联系人地址
     */
    private String shopAddress;
    /**
     * 店铺log图片
     */
    private Integer logoPicId;
    /**
     * 店铺log图片路径
     */
    private String logoPicPath;
    /**
     * 店铺封面图
     */
    private String coverPicId;

    private List<String> coverPicPath;
    /**
     * 认领状态
     */
    private Integer claimStatus;
    /**
     * 认领时间
     */
    private Date claimTime;
    /**
     * 认领人
     */
    private Integer claimUserId;
    /**
     * 认领经销商企业ID
     */
    private Integer claimCompanyId;
    /**
     * 是否删除
     */
    private Integer isDeleted;
    /**
     * 是否发布(0-未发布;1-已发布)
     */
    private Integer isRelease;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 门店介绍
     */
    private Integer introducedFileId;

    private String shopIntroduced;

    @AllArgsConstructor
    @Getter
    public enum ClaimStatus {
        UNCLAIMED(0, "未认领"),
        CLAIMED(1, "已认领");

        int status;
        String remark;
    }
}
