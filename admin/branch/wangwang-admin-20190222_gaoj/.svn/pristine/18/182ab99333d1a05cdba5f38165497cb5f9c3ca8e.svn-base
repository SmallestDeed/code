package com.sandu.api.customer.output;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandu.annotation.ExportRow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class CustomerVO implements Serializable {

    @ApiModelProperty("id")
    @JsonInclude()
    private Long id;
    
    @ApiModelProperty("用户id")
    @JsonInclude()
    private Integer userId;
    
    @ApiModelProperty("用户等级")
    @JsonInclude()
    private Integer level;
    
    @ApiModelProperty("分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)")
    @JsonInclude()
    private Integer alotStatus;
    
    @ApiModelProperty("分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)")
    @JsonInclude()
    private String alotStatusName;
    
    @ApiModelProperty("分配人id")
    @JsonInclude()
    private Integer alotUserId;
    
    @ApiModelProperty("分配人名称")
    @JsonInclude()
    private String alotUserName;
    
    @JsonInclude()
    @ApiModelProperty("分配经销商企业ID")
    private Integer channelCompanyId;
    
    @ApiModelProperty("分配经销商企业名称")
    @JsonInclude()
    private String channelCompanyName;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @JsonInclude()
    private String userName;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @JsonInclude()
    private String nickName;
    /**
     * 微信号
     */
    @ApiModelProperty("微信号")
    @JsonInclude()
    private String openId;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @JsonInclude()
    private String mobile;

    /**
     * 区域
     */
    @ApiModelProperty("区域")
    @JsonInclude()
    private Integer areaId;
    
    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    @JsonInclude()
    private String address;

    /**
     * 跟进人id
     */
    @ApiModelProperty("跟进人id")
    @JsonInclude()
    private Integer followUpUserId;
    /**
     * 跟进人名称
     */
    @ApiModelProperty("跟进人名称")
    @JsonInclude()
    private String followUpUserName;

    /**
     * 跟进结果
     */
    @ApiModelProperty("跟进结果")
    @JsonInclude()
    private String followUpResult;
    
    @ApiModelProperty("客户积分")
    private Double score;
    
    private String levelName;

}