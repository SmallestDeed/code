package com.sandu.api.income.model;

import com.sandu.common.annotation.HSSFColumn;
import com.sandu.common.annotation.HSSFStyle;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CompanyDesignPlanIncome implements Serializable{

    private Long id;

    private Integer planId;

    @HSSFColumn(title = "平台", autoWidth = true)
    private String platformName;

    @HSSFColumn(title = "方案编码", autoWidth = true)
    private String planCode;

    private Long buyerId;

    @HSSFColumn(title = "方案创建者", autoWidth = true)
    private String planCreator;

    @HSSFColumn(title = "购买用户", autoWidth = true)
    private String buyerName;

    private Integer platformId;

    private Integer planCompanyId;

    private Integer useType;

    @HSSFColumn(title = "购买途径", autoWidth = true, switcher = "{0:售卖方案, 1:版权方案}")
    private Integer buyType;

    @HSSFColumn(title = "涉及度币", autoWidth = true)
    private Double payDubi;

    @HSSFColumn(title = "购买时间", autoWidth = true, style = @HSSFStyle(dataFormat = "yyyy-m-d h:mm:ss"))
    private Date payTime;

    private Integer isDeleted;

    private Integer planType;
}
