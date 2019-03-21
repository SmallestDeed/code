package com.sandu.api.customer.input;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class CustomerBaseInfoVO implements Serializable {
    private static final long serialVersionUID = 5680829087185058262L;
    @ApiModelProperty("自增长id")
    private Long id;
    /**
     * 公司id
     */
    @ApiModelProperty("公司id")
    private Integer companyId;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String mobile;
    /**
     * 客户等级(8-活跃客户;15-潜在客户;25-忠实客户)
     */
    @ApiModelProperty("客户等级(8-活跃客户;15-潜在客户;25-忠实客户)")
    private Integer level;
    /**
     *分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)
     */
    @ApiModelProperty("分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)")
    private Integer alotStatus;
    /**
     * 分配操作人(自动分配写1,人工分配写登录人id)
     */
    @ApiModelProperty("分配操作人(自动分配写1,人工分配写登录人id)")
    private Integer alotUserId;
    /**
     * 分配经销商企业ID
     */
    @ApiModelProperty("分配经销商企业ID")
    private Integer channelCompanyId;
    /**
     * 分配时间
     */
    @ApiModelProperty("分配时间")
    private Integer alotTime;
    /**
     * 分配类型(0-自动;1-手动)
     */
    @ApiModelProperty("分配类型(0-自动;1-手动)")
    private Integer alotType;
    /**
     * 是否删除(0:正常，1：删除)
     */
    @ApiModelProperty("是否删除(0:正常，1：删除)")
    private Integer isDeleted;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String creator;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String modifier;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private LocalDateTime gmtModified;

    /**
     * 省code
     */
    @ApiModelProperty("省code")
    private String provinceCode;
    /**
     * 市code
     */
    @ApiModelProperty("市code")
    private String cityCode;
    /**
     * 区code
     */
    @ApiModelProperty("区code")
    private String areaCode;
    /**
     * 街道code
     */
    @ApiModelProperty("街道code")
    private String streetCode;
    /**
     * 街道code
     */
    @ApiModelProperty("街道code")
    private String address;
    /**
     * 跟进人id
     */
    @ApiModelProperty("跟进人id")
    private Integer followUpUserId;
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

    @ApiModelProperty("是否有手机号")
    private Boolean phoneFlag;
    
    /**
     *导出类型(0-客户管理导出;1-我的客户导出) 
     */
    @JsonIgnore
    private Integer exportType;
}