package com.sandu.company.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author WangHaiLin
 * @date 2018/9/29  17:29
 */
@Data
public class ProprietorInfo implements Serializable {
    /**自增id**/
    private Long id;
    /**类型（0：0元设计，1：装修报价;3-店铺预约）**/
    private Integer type;
    /**姓名**/
    private String userName;
    /**手机号码**/
    private String mobile;
    /**业务类型(0-业主信息;1-设计师信息;2-设计装修公司信息)**/
    private Integer businessType;
    /**公司类型(0-设计公司;1-装修公司)**/
    private Integer companyType;
    /**公司名称**/
    private String companyName;
    /**省编码**/
    private String provinceCode;
    /**市编码**/
    private String cityCode;
    /**小区名称**/
    private String areaName;
    /**房屋面积(平米)**/
    private String houseAcreage;
    /**'来源类型（0：PC，1：小程序）**/
    private Integer sourceType;
    /**户型**/
    private String houseType;
    /**处理结果（0：未处理，1：已处理）**/
    private Integer process;
    /**创建人**/
    private String creator;
    /**创建时间**/
    private Date gmtCreated;
    /**修改人**/
    private String modifier;
    /**修改时间**/
    private Date gmtModified;
    /**是否删除（0：未删除，1：已删除）**/
    private Integer isDeleted;
    /**是否初始化数据（0：原始数据，1：新数据）**/
    private Integer isInit;
    /**预约店铺Id(business_type为0时且type为3时必填)**/
    private Long shopId;
    /**服务类型(1:验房预约;2:量房预约;3:设计预约;4:店铺预约)business_type为0时且type为3时必填**/
    private Integer servicesType;
    /**预约店铺Id**/
    private Integer  designplanId;
    /**预约用户Id**/
    private Integer appointUserId;

}
