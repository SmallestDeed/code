package com.sandu.supplydemand.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author Administrator
 */
@Data
public class BaseSupplyDemand implements Serializable {
    private static final long serialVersionUID = 842010556872720252L;

    private Integer id;


    @Max(value = 2, message = "需求为2")
    @Min(value = 1, message = "供应为1")
    private Integer type;

    private Integer creatorId;

    private Integer creatorTypeValue;


    @Size(min = 1, message = "信息分类不能为空")
    private String supplyDemandCategoryId;


    @Size(min = 1, max = 16, message = "省份名字长度必须在1和16之间")
    private String province;


    @Size(min = 1, max = 16, message = "城市名字长度必须在1和16之间")
    private String city;


    @Size(min = 1, max = 16, message = "地区名字长度必须在1和16之间")
    private String district;


    private String street;


    @Size(min = 0, max = 50, message = "地区名字长度必须在0和50之间")
    private String address;

    @Size(min = 1, message = "图片ID不能为空")
    private String coverPicId;


    @Size(min = 1, max = 50, message = "标题长度必须在1和50之间")
    private String title;

    @Size(min = 1, max = 2000, message = "描述长度必须在1和2000之间")
    private String description;


    private String descriptionPicId;


    @Max(value = 1, message = "装修公司是否可见最大为1")
    @Min(value = 0, message = "装修公司是否可见最小为0")
    private Integer decorationCompany;


    @Max(value = 1, message = "设计师是否可见最大为1")
    @Min(value = 0, message = "设计师是否可见最小为0")
    private Integer designer;


    @Max(value = 1, message = "建材门店是否可见最大为1")
    @Min(value = 0, message = "建材门店是否可见最小为0")
    private Integer materialShop;


    @Max(value = 1, message = "业主是否可见最大为1")
    @Min(value = 0, message = "业主是否可见最小为0")
    private Integer proprietor;


    @Max(value = 1, message = "施工单位是否可见最大为1")
    @Min(value = 0, message = "施工单位是否可见最小为0")
    private Integer builder;

    private Integer pushStatus;

    private Integer businessStatus;

    private Date gmtPublish;

    private String sysCode;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String remark;

    private String provinceAddress;

    private String cityAddress;

    private String districtAddress;

    private String streetName;

    @Size(min = 1, max = 10, message = "联系人长度必须在1和10之间")
    private String contact;


    private String phone;

    private String typeName;

    private String supplyDemandCategoryName;

    private Integer viewNum;

    private String contractFile;

    private String quotationFile;

    private String picPath;

    private String provinceName;

    private String cityName;

    private String districtName;

    private String supplyDemandCategoryPicPath;
    //供求信息所有图片路径集合
    private List<String> supplyDemandPicPath;

    private String publisher;


    //供求信息小类名称
    private String supplyDemandSmallCategoryName;
    //供求信息图片信息
    private List<SupplyDemandPic> supplyDemandPicList;
    //供求信息默认图片信息
    private SupplyDemandPic supplyDemandCoverPic;

    private String nickName;

    private String userPicPath;

    private String userHeadPic;

    private Integer sex;

    private String sessionId;

    private Long recommendedTime;


    private Integer planType;//方案类型,1:单空间推荐方案.2:全屋单推荐方案,3:单空间我的设计方案,4:全屋我的设计方案

    private Integer planId;

    private Integer houseId;

    private List<String> picPathList;


    private Integer reviewsCount;
}