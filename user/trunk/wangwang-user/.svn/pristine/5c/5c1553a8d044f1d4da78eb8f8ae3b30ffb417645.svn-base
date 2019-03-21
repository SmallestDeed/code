package com.sandu.api.user.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 将过期账号查询输出
 * @author WangHaiLin
 * @date 2018/8/15  18:11
 */
@Data
public class UserWillExpireVO implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "电话号码")
    private String mobile;

    @ApiModelProperty(value = "失效时间")
    private Date  failureTime;

    @ApiModelProperty(value = "账号剩余天数")
    private Integer remainDays;
}
