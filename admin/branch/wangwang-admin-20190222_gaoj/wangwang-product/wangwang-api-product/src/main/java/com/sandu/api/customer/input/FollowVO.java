package com.sandu.api.customer.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class FollowVO implements Serializable {
    private static final long serialVersionUID = -4302066739072028779L;
    @ApiModelProperty("跟进详情的id")
    private Long id;
    /**
     * 公司id
     */
    @ApiModelProperty("公司id(登录人所属的企业id)(需要写入跟进记录)")
    private Integer companyId;
    /**
     * 用户id
     */
    @ApiModelProperty("跟进用户id(列表的userid")
    private Integer followUserId;
    /**
     * 跟进结果
     */
    @ApiModelProperty("跟进结果")
    private String followUpResult;

    /**
     * 跟进状态
     */
    @ApiModelProperty("跟进状态")
    private Integer alotStatus;

}