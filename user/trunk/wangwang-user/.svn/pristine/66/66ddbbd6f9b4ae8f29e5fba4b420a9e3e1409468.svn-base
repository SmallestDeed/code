package com.sandu.api.income.model;

import com.sandu.common.annotation.HSSFColumn;
import com.sandu.common.annotation.HSSFStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业方案收益统计表
 */
@Data
public class CompanyDesignPlanIncomeTransferRecord implements Serializable {

    private Long id;

    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @ApiModelProperty(value = "企业名")
    private String companyName;

    @ApiModelProperty(value = "转出人id")
    private Long transferUserId;

    @ApiModelProperty(value = "转出度币")
    @HSSFColumn(title = "转出度币", autoWidth = true)
    private Double transferDubi;

    @ApiModelProperty(value = "转出转态:0.失败,1.成功")
    @HSSFColumn(title = "转出转态", autoWidth = true, switcher = "{0:失败, 1:成功}")
    private Integer transferStatus;

    @ApiModelProperty(value = "转入人名称")
    @HSSFColumn(title = "收款方", autoWidth = true)
    private String receiveUserName;

    @ApiModelProperty(value = "'转出时间'")
    @HSSFColumn(title = "操作时间", autoWidth = true, style = @HSSFStyle(dataFormat = "yyyy-m-d h:mm:ss"))
    private Date transferTime;

    @ApiModelProperty(value = "转出人名称")
    @HSSFColumn(title = "操作人", autoWidth = true)
    private String transferUserName;

    @ApiModelProperty(value = "转出金额")
    private Double transferAmount;

    @ApiModelProperty(value = "转入人id")
    private Long receiveUserId;

    @ApiModelProperty(value = "转入人时间")
    private Date receiveTime;

    private Integer isDeleted;
}
