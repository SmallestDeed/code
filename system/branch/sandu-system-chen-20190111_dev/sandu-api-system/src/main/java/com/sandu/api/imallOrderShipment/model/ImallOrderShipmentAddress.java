package com.sandu.api.imallOrderShipment.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分订单发货单地址
 */
@Data
public class ImallOrderShipmentAddress implements Serializable {
    /**
     * id
     */
    private long id;

    /**
     * 发货单id
     */
    private long shipmentId;

    /**
     * 国家
     */
    private int country;

    /**
     * 省份
     */
    private int province;

    /**
     * 城市
     */
    private int city;

    /**
     * 区域
     */
    private int area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮编
     */
    private String zipcode;

    /**
     * 收件人
     */
    private String recvName;

    /**
     * 联系电话
     */
    private String mobile;

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
    private int isDeleted;

}
