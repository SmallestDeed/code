package com.sandu.api.user.input;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Api(value = "用户管理列表搜索")
public class UserManageSearch implements Serializable {

    @ApiModelProperty(value = "登录名")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "套餐名称")
    private String servicesName;

    @ApiModelProperty(value = "套餐过期时间区间start")
    private Date effectiveBegin;

    @ApiModelProperty(value = "套餐过期时间区间end")
    private Date effectiveEnd;

    @ApiModelProperty(value = "经销商id")
    private Long franchiserId;

    @ApiModelProperty(value = "调用对象")
    @NotNull(message = "企业类型不能为空")
    private String userMethod;

    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "当前页不能为空")
    private Integer page;

    @ApiModelProperty(value = "每页显示条数")
    @NotNull(message = "每页显示条数不能为空")
    private Integer limit;

    @ApiModelProperty(value = "企业id")
    private Integer companyId;

    @ApiModelProperty(value = "账号类型 0.试用,1.正式")
    private Integer useType;

}
