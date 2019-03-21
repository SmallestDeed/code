package com.sandu.api.company.model.bo;

import com.sandu.api.company.model.Company;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Sandu
 * @date 2017/12/18
 */
@Data
public class CompanyBO implements Serializable {
    /** */
    private Long id;

    /** */
    private String sysCode;

    /** */
    private String companyCode;

    /** */
    private String companyName;
    /** */
    private Integer companyLogo;

    private String logoPath;

    /** 经营类型*/
    private Integer businessType;

    /** 公司品牌网站域名*/
    private String companyDomainName;

    /** 客服qq*/
    private String companyCustomerQq;

    /** 分类Ids*/
    private String categoryIds;

    private String brandNames;

    private List<String> categoryNames;
}
