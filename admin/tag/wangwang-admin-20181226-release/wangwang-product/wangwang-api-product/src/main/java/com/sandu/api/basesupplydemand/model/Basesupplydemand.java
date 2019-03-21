package com.sandu.api.basesupplydemand.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * supply_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-10-20 10:46
 */
@Data
public class Basesupplydemand implements Serializable {

    
    /**  */
    private Integer id;
    /** 信息类别(1:供应，2:需求) */
    private Integer type;
    /** 创建者id */
    private Integer creatorId;
    /** 创建者类型() */
    private Integer creatorTypeValue;
    /** 信息分类 */
    private String supplyDemandCategoryId;
    /**
     * 分类名称
     */
    private String supplyDemandCategoryName;
    /** 供求的省份 */
    private String province;
    /** 供求的城市 */
    private String city;
    /** 供求的地区 */
    private String district;
    /** 街道 */
    private String street;
    /** 供求的详细地址 */
    private String address;
    /** 封面图 */
    private String coverPicId;
    /** 封面图路径 */
    private String coverPicPath;
    /** 封面图路径 */
    private List<String> coverPicPaths;
    /** 信息标题 */
    private String title;
    /** 信息描述 */
    private String description;
    /** 描述图片 */
    private String descriptionPicId;
    /** 装修公司是否可见(0:否，1:是) */
    private Integer decorationCompany;
    /** 设计师是否可见(0:否，1:是) */
    private Integer designer;
    /** 建材门店是否可见(0:否，1:是) */
    private Integer materialShop;
    /** 业主是否可见(0:否，1:是) */
    private Integer proprietor;
    /** 施工单位是否可见(0:否，1:是) */
    private Integer builder;
    /** 信息状态(0:上架中,1:已下架) */
    private Integer pushStatus;
    /** 信息状态(0:待报价,1:待签约,2:待支付,3:已完成) */
    private Integer businessStatus;
    /** 发布时间 */
    private Date gmtPublish;
    /** 系统编码 */
    private String sysCode;
    /** 创建者 */
    private String creator;
    /** 创建时间 */
    private Date gmtCreate;
    /** 修改人 */
    private String modifier;
    /** 修改时间 */
    private Date gmtModified;
    /** 是否删除（0:否，1:是） */
    private Integer isDeleted;
    /** 备注 */
    private String remark;
    /** 联系人 */
    private String contact;
    /** 手机号 */
    private String phone;
    /** 浏览次数 */
    private Integer viewNum;
    /** 「推荐」 */
    private Long recommendedTime;

    /** 「推荐」 */
    private Date recommendedDate;

    private Integer[] categoryId;
}
