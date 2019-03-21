package com.sandu.api.banner.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告-位置 model
 * @author WangHaiLin
 * @date 2018/5/16  14:17
 */
@Data
public class BaseBannerPosition implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 位置编码:system:module:page:positon
     */
    private String code;
    /**
     * 位置名称
     */
    private String name;
    /**
     *备注
     */
    private String remark;
    /**
     *在哪使用(1:平台--选装网,同城联盟;2:企业--企业小程序;3.店铺)
     */
    private Integer type;
    /**
     *系统编码
     */
    private String sysCode;
    /**
     *创建者
     */
    private String creator;
    /**
     *创建时间
     */
    private Date gmtCreate;
    /**
     *修改人
     */
    private String modifier;
    /**
     *修改时间
     */
    private Date gmtModified;
    /**
     *是否删除
     */
    private Integer isDeleted;
}
