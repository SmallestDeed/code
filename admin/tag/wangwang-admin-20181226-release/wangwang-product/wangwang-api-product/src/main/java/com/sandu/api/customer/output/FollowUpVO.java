package com.sandu.api.customer.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class FollowUpVO implements Serializable {

    private static final long serialVersionUID = -406241461463582096L;
    @ApiModelProperty("id")
    @JsonInclude()
    private Long id;

    /**
     * 用户手机号或微信号
     */
    @ApiModelProperty("用户手机号或微信号")
    @JsonInclude()
    private String userInfo;
    /**
     * 分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)
     */
    @ApiModelProperty("分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)")
    @JsonInclude()
    private Integer alotStatus;

    /**
     * 跟进结果
     */
    @ApiModelProperty("跟进结果")
    @JsonInclude()
    private String followUpResult;


    /**
     * 分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)
     */
    @ApiModelProperty("分配状态名称(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)")
    @JsonInclude()
    private String alotStatusName;

}
