package com.sandu.api.customer.input.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class CustomerFollowLogQuery implements Serializable {
    private static final long serialVersionUID = -5372552974239006917L;
    private Long id;

    private Integer companyId;

    private Integer userId;

    private Set<Integer> userIds;

    private Integer followUpUserId;

    private String followUpResult;

    private String remark;

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