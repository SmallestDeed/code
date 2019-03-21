package com.sandu.api.grouppurchase.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/7 16:27
 * @since 1.8
 */

@Setter
@Getter
public class GroupPurchaseGatherVO implements Serializable {

    @ApiModelProperty("凑团发起用户ID")
    Long userId;

    @ApiModelProperty("凑团发起用户昵称")
    String nickName;

    @ApiModelProperty("凑团发起用户手机号")
    String telephone;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @ApiModelProperty("凑团发起用户头像")
    String userPhoto;

    @ApiModelProperty("活动ID")
    Long activityId;

    @ApiModelProperty("凑团ID")
    Long purchaseOpenId;

    @ApiModelProperty("商品SPU_ID")
    Long spuId;

    @ApiModelProperty("成团人数")
    Integer totalNumber;

    @ApiModelProperty("已参团人数")
    Integer joinNumber;

    @ApiModelProperty("待参团人数")
    Integer unJoinNumber;

    @ApiModelProperty("凑团开始时间")
    Date openDate;

    @ApiModelProperty("距离凑团开始时间还有多久")
    Long howLongOpenDate;

    @ApiModelProperty("凑团结束时间")
    Date expireDate;

    @ApiModelProperty("距离凑团结束时间还有多久")
    Long howLongExpireDate;
}
