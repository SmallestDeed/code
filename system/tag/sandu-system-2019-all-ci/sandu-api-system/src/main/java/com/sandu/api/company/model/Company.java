package com.sandu.api.company.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 企业表
 *
 * @author Sandu
 * @date 2017/12/14
 */
@Data
public class Company implements Serializable{

	/**
	 * 厂商
	 */
    public  static  final  Integer COMPANY_TYPE = 1;
    /**
     * 设计公司
     */
    public  static  final  Integer DESIGNER_TYPE = 4;
    
    /**
     * 装修公司
     */
    public  static  final  Integer DECORATION_TYPE = 5;

    private static final long serialVersionUID = 1L;
    /** */
    private Long id;

    /** */
    private String sysCode;

    /** */
    private String companyCode;

    /** */
    private String companyName;

    /** */
    private String companyUrl;

    /** */
    private String companyDesc;

    /** */
    private String companyAddress;

    /** */
    private String creator;

    /** */
    private Date gmtCreate;

    /** */
    private String modifier;

    /** */
    private Date gmtModified;

    /** */
    private Integer isDeleted;

    /** */
    private String att1;

    /** */
    private String att2;

    /** */
    private String att3;

    /** */
    private String att4;

    /** */
    private String att5;

    /** */
    private String att6;

    /** */
    private Date dateAtt1;

    /** */
    private Date dateAtt2;

    /** */
    private Integer companyLogo;

    /** */
    private Integer numAtt2;

    /** */
    private Double numAtt3;

    /** */
    private Double numAtt4;

    /** */
    private String companyIdentify;

    /** */
    private Integer industry;

    /** */
    private String smallType;

    /** */
    private Integer numAtt1;

    /** 经营类型*/
    private Integer businessType;

    /** 公司品牌网站域名*/
    private String companyDomainName;

    /** 客服qq*/
    private String companyCustomerQq;

    /** 分类Ids*/
    private String categoryIds;

    /** */
    private String remark;}
