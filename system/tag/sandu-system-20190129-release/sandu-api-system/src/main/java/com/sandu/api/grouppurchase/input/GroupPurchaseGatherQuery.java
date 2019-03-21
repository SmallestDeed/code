package com.sandu.api.grouppurchase.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/7 16:19
 * @since 1.8
 */

@Setter
@Getter
@ToString
public class GroupPurchaseGatherQuery implements Serializable {

    @ApiModelProperty("活动ID")
    Long activityId;

    Integer offset;
    Integer limit;
}
