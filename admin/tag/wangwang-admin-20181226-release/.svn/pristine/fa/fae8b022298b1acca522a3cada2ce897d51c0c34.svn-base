package com.sandu.api.solution.input;

import com.sandu.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("企业收益列表搜索条件")
public class CompanyIncomeQuery extends BaseQuery implements Serializable {

    @ApiModelProperty("方案编号")
    private String planCode;

    @ApiModelProperty("筛选开始时间段")
    private String startTime;

    @ApiModelProperty("筛选结束时间段")
    private String endTime;

    @ApiModelProperty("提现状态")
    private Integer withdrawStatus;

    @ApiModelProperty("企业id")
    @NotNull(message = "companyId is null")
    private Long companyId;

    private String msgId;
}
