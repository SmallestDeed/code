package com.sandu.api.customer.input.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class CustomerOperateLogQuery implements Serializable {
    private static final long serialVersionUID = -958048020659778019L;
    private Long id;

    private Long userId;

    private Integer operateType;

    private Double score;

    private Integer businessId;

    private String requestUrl;

    private String ipAddress;

    private String creator;

    private LocalDateTime gmtCreate;

    private String modifier;

    private LocalDateTime gmtModified;

    private Integer isDeleted;
    /**
     * 当前第几页
     */
    @ApiModelProperty("当前第几页")
    private Integer start;
    /**
     * 每页显示多少条
     */
    @ApiModelProperty("每页显示多少条")
    private Integer limit;

}