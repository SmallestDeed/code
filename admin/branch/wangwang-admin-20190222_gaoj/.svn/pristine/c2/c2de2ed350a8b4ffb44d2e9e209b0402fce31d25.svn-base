package com.sandu.api.platform.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 */
@Data
public class Platform implements Serializable {
    public static final String PLATFORM_CHANNEL = "channel";
    public static final String PLATFORM_ONLINE = "online";
    public static final String PLATFORM_LIBRARY = "library";
    public static final String BUSINESS_TYPE_2B = "2b";
    public static final String BUSINESS_TYPE_2C = "2c";
    public static final String DO_ALLOT = "doAllot";
    public static final String CANCEL_ALLOT = "cancelAllot";
    public static final Integer PUT_STATUS_DOWN = 0;
    public static final Integer PUT_STATUS_UP = 1;
    private Long id;

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 平台类型(前台/后台)
     */
    private String platformType;

    /**
     * 媒介类型
     */
    private String mediaType;

    /**
     * 平台编码
     */
    private String platformCode;

    /**
     * 所属平台分类(2b/2c/sandu)
     */
    private String platformBussinessType;

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
     * 修改人
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
     * 字符备用1
     */
    private String att1;

    /**
     * 字符备用2
     */
    private String att2;

    /**
     * 整数备用1  上架平台标志
     */
    private Integer numa1;

    /**
     * 整数备用2
     */
    private Integer numa2;

    /**
     * 备注
     */
    private String remark;
}
