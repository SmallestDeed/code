package com.sandu.api.income.model;

import com.sandu.common.annotation.HSSFColumn;
import com.sandu.common.annotation.HSSFStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CompanyDesignPlanIncomeWithdrawRecord implements Serializable {

    private Long id;

    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @ApiModelProperty(value = "企业名")
    private String companyName;

    @ApiModelProperty(value = "申请人id")
    private Long applyUserId;

    @ApiModelProperty(value = "申请人电话")
    private String applyUserMobile;

    @ApiModelProperty(value = "申请度币")
    @HSSFColumn(title = "涉及度币", autoWidth = true)
    private Double applyDubi;

    @ApiModelProperty(value = "申请金额")
    private Double applyAmount;

    @ApiModelProperty(value = "实际提现金额")
    @HSSFColumn(title = "实际提现金额", autoWidth = true)
    private Double realWithdrawAmount;

    @ApiModelProperty(value = "提现方式:0.个人,1.公司")
    @HSSFColumn(title = "提现方式", autoWidth = true, switcher = "{0:个人, 1:公司}")
    private Integer withdrawType;

    @ApiModelProperty(value = "提现状态:0.提现中,1.已提现")
    @HSSFColumn(title = "提现状态", autoWidth = true, switcher = "{0:提现中, 1:已提现}")
    private Integer withdrawStatus;

    @ApiModelProperty(value = "收款方id")
    private Long revceiveUserId;

    @ApiModelProperty(value = "收款方名称")
    @HSSFColumn(title = "收款人", autoWidth = true)
    private String revceiveUserName;

    @ApiModelProperty(value = "申请人名称")
    @HSSFColumn(title = "申请人", autoWidth = true)
    private String applyUserName;

    @ApiModelProperty(value = "申请时间")
    @HSSFColumn(title = "申请时间", autoWidth = true, style = @HSSFStyle(dataFormat = "yyyy-m-d h:mm:ss"))
    private Date applyTime;

    @ApiModelProperty(value = "审批人id")
    private Long approvedUserId;

    @ApiModelProperty(value = "审批人名称")
    private String approvedUserName;

    @ApiModelProperty(value = "审批时间")
    private Date approvedTime;

    private Integer isDeleted;
    
    @ApiModelProperty(value = "银行卡信息id")
    private Long bankcardInfoId;
}
