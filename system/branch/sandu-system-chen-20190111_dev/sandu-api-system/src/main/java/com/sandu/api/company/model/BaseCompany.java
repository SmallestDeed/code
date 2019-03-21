package com.sandu.api.company.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author chenqiang
 * @Description 企业 基础 模型类
 * @Date 2018/5/31 0031 18:18
 * @Modified By
 */
@Data
public class BaseCompany implements Serializable {
	
    public  static  final  Integer COMPANY_TYPE = 1;
    public  static  final  Integer DESIGNER_TYPE = 4;
    public  static  final  Integer DECORATION_TYPE = 5;
	
    /**
     * 主键id
     */
    private Long id;

    /**
     * 系统编码
     */
    private String sysCode;

    /**
     * 企业/经销商编码
     */
    private String companyCode;

    /**
     * 企业/经销名称
     */
    private String companyName;

    /**
     * 企业路径
     */
    private String companyUrl;

    /**
     * 企业/经销商介绍
     */
    private String companyDesc;

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
     * 企业/经销商详细地址
     */
    private String companyAddress;

    /**
     * 创建人
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
     * 是否删除：0未删除、1已删除
     */
    private Integer isDeleted;

    /**
     *
     */
    private String att1;

    /**
     *
     */
    private String att2;

    /**
     *
     */
    private String att3;

    /**
     *
     */
    private String att4;

    /**
     *
     */
    private String att5;

    /**
     *
     */
    private String att6;

    /**
     *
     */
    private Date dateAtt1;

    /**
     *
     */
    private Date dateAtt2;

    /**
     * 企业logo id
     */
    private Integer companyLogo;

    /**
     *
     */
    private Integer numAtt2;

    /**
     *
     */
    private BigDecimal numAtt3;

    /**
     *
     */
    private BigDecimal numAtt4;

    /**
     *
     */
    private String companyIdentify;

    /**
     *
     */
    private Integer industry;

    /**
     *
     */
    private String smallType;

    /**
     *
     */
    private Integer numAtt1;

    /**
     * 公司类型:1：厂商、2：经销商、3：门店、4：设计公司、5：装修公司
     */
    private Integer businessType;

    /**
     * 公司品牌网站域名
     */
    private String companyDomainName;

    /**
     * 客服qq号码
     */
    private String companyCustomerQq;

    /**
     * 分类Ids
     */
    private String categoryIds;

    /**
     * 企业经营范围
     */
    private String businessScope;

    /**
     * 企业可见产品范围
     */
    private String productVisibilityRange;

    /**
     * 企业可开通PC端数量
     */
    private Integer pcCount;

    /**
     * 企业可开通移动端数量
     */
    private Integer mobileCount;

    /**
     * 企业内部用户数量
     */
    private Integer internalUserCount;

    /**
     * 合同生效时间
     */
    private Date contractEffectiveTime;

    /**
     * 合同到期时间
     */
    private Date contractFailureTime;

    /**
     * 管理员账号
     */
    private Integer adminUserId;

    /**
     * 经销商所属企业id
     */
    private Integer pid;

    /**
     * 经销商所属品牌ids
     */
    private String brandId;

    /**
     * 联系人电话
     */
    private String phone;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 合同号
     */
    private String contractNumber;

    /**
     *
     */
    private String companyMainCategory;

    /**
     * 企业微信appid
     */
    private String appId;

    /**
     * 企业微信app_secret
     */
    private String appSecret;

    /**
     * 企业微信商户id
     */
    private String mchId;

    /**
     * key
     */
    private String mchKey;

    /**
     * 会员认证年数
     */
    private Integer memberYear;

    /**
     * 认证等级1是一级2是二级
     */
    private Integer authGrade;

    /**
     * 保证金：元
     */
    private BigDecimal deposit;

    /**
     * 访问数量
     */
    private Integer visitCount;

    /**
     * 点赞率
     */
    private BigDecimal praiseRate;

    /**
     *
     */
    private String remark;

    /** added by wanghl
    企业所属行业集合*/
    private String companyIndustrys;

}