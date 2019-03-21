package com.sandu.api.solution.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * DesignPlanDeliverBO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
public class DesignPlanDeliverVO implements Serializable {
    /**
     * 接收公司
     */
    @ApiModelProperty("接收公司")
    private String receiveCompanyId;
    /**
     * 接收公司名称
     */
    @ApiModelProperty("接收公司名称")
    private String receiveCompanyName;

    /**
     * 接收时间
     */
    @ApiModelProperty("接收时间")
    private Date receiveDate;
}
