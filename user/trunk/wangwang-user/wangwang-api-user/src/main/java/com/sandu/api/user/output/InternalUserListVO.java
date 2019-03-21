package com.sandu.api.user.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 内部账户列表展示输出实体
 * @author WangHaiLin
 * @date 2018/6/4  13:57
 */
@Data
public class InternalUserListVO  implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "业务类型:0为普通生成账号,1为套餐购买生成的账号")
    private Integer servicesFlag;

    @ApiModelProperty(value = "是否有登录过:0,未登录;1,已经登录过")
    private Integer isLoginBefore;

    @ApiModelProperty(value = "拥有服务")
    List<UserServiceVO> serviceList;




}
