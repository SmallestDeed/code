package com.sandu.user.model.view;

import com.sandu.user.model.OrderManage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CommisionDetailsBO implements Serializable{

    @ApiModelProperty(value = "用户头像路径")
    private String userPicPath;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户企业来源")
    private String sourceCompany;

    @ApiModelProperty(value = "总返佣")
    private Integer totalCommision;

    @ApiModelProperty(value = "邀请人数")
    private Integer invitationPeople;

    @ApiModelProperty(value = "邀请列表")
    private List<OrderManage> orderManages;
}
