package com.sandu.api.proprietorinfo.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProprietorInfoDetailVo implements Serializable {
    /**
     * 业主信息ID
     */
    private Integer id;
    /**
     * 需求类型
     */
    private String requirementType;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 客户姓名
     */
    private String userName;
    /**
     * 省编码
     */
    private String provinceCode;
    /**
     * 省
     */
    private String province;
    /**
     * 市编码
     */
    private String cityCode;
    /**
     * 市
     */
    private String city;
    /**
     * 省+市
     */
    private String provinceCity;
    /**
     * 小区
     */
    private String houseEstate;
    /**
     * 详细地址
     */
    private String address;
    /**
     * N室
     */
    private Byte bedroomNum;
    /**
     * N厅
     */
    private Byte livingRoomNum;
    /**
     * N卫
     */
    private Byte toiletNum;
    /**
     * 户型:N室N厅N卫
     */
    private String house;
    /**
     * 户型面积
     */
    private String houseAcreage;
    /**
     * 装修方式
     */
    private String decorateType;
    /**
     * 装修方式(取字典类型decorateType的value)
     */
    private Integer decorateTypeValue;
    /**
     * 装修预算
     */
    private String decorateBudget;
    /**
     * 装修预算(取字典类型decorateBudget的value)
     */
    private Integer decorateBudgetValue;
    /**
     * 倾向风格
     */
    private String goodStyle;
    /**
     * 装修风格
     */
    private Integer goodStyleValue;
    /**
     * 装修类型
     */
    private String decorateHouseType;
    /**
     * 装修类型(0-新房装修;1-旧房改造)
     */
    private Integer decorateHouseTypeKey;
    /**
     * 方案Id
     */
    private Integer designPlanId;
    /**
     * 设计方案封面图
     */
    private String designPlanPic;
    /**
     * 公司名称
     */
    private String companyName;


    private String shopName;
    /**
     * 客户类型
     */
    private String customerType;
    /**
     * 客户类型(取字典类型customerType的value)
     */
    private Integer customerTypeValue;
    /**
     * 备注
     */
    private String remark;
    /**
     * 上次回访时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date lastTime;
    /**
     * 下次回访时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date nextTime;

    private Boolean deleteFunc;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:SS", timezone = "GMT+8")
    private Date initTime;

}
