package com.sandu.api.customer.input.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class CustomerAlotLogQuery  implements Serializable {
    private static final long serialVersionUID = 6958877592091855856L;
    /** 自增长ID */
    private Integer id;
    /** 企业区域分配ID */
    private Integer customerAlotZoneId;
    /** 用户ID */
    private String userId;
    /** 创建者 */
    private String creator;
    /** 创建时间 */
    private LocalDateTime gmtCreate;
    /** 修改人 */
    private String modifier;
    /** 修改时间 */
    private LocalDateTime gmtModified;
    /** 是否删除 */
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