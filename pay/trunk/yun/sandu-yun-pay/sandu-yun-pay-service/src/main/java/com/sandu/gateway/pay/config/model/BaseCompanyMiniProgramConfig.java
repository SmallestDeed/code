package com.sandu.gateway.pay.config.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class BaseCompanyMiniProgramConfig implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键Id
     */
    private Long id;

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
     * 企业id
     */
    private Long companyId;

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
     * 小程序名称
     */
    private String minProName;
    
    /**
     * api 证书路径
     */
    private String apiCertPath;
    
}
