package com.sandu.api.company.model.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class CompanyBrandBo implements Serializable {
    /**
     * 品牌ID
     */
    private Integer brandId;
    /**
     * 公司id
     */
    private Integer companyId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司域名
     */
    private String companyDomain;
}
