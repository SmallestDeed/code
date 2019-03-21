package com.sandu.api.servicepurchase.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: Yuxc
 * @Description:
 * @Date: 2019/1/3
 */
@Data
public class ServicesBaseInfoUpdate implements Serializable{

    @ApiModelProperty("套餐id")
    @NotNull(message = "套餐id不能为空")
    private Integer id;

    @ApiModelProperty("套餐名称")
    @NotNull(message = "套餐名称不能为空")
    private String servicesName;

    @ApiModelProperty("套餐描述")
    private String serviceDesc;

    @ApiModelProperty("用户类型")
    @NotNull(message = "用户类型不能为空")
    private String userScope;

    @ApiModelProperty("销售渠道")
    @NotNull(message = "销售渠道不能为空")
    private String saleChannel;


    /**
     * 赠送天数
     */
    private List<Integer> giveDuration;

    /**
     * 价格年限
     */
    private List<Integer> duration;

    /**
     * 套餐价格
     */
    private List<Float> price;

    /**
     * 套餐 年-月-日
     */
    private List<Integer> priceUnit;

    /**
     * 免费渲染时间
     */
    private List<Integer> freeRenderDuration;

    /**
     * 赠送度币
     */
    private List<Integer> sanduCurrency;

    /**
     * 价格id
     */
    private List<Integer> priceId;

    @ApiModelProperty("企业id")
    private String companyId;

    @ApiModelProperty("套餐类型")
    private Integer servicesType;

    /**
     * 套餐价格集合
     */
    private List<ServicesPriceAdd> prices;

    private String creator;

    /**
     * 套餐备注
     */
    private String remark;

    @ApiModelProperty("修改者")
    @NotNull(message = "修改者不能为空")
    private String modifier;
}
