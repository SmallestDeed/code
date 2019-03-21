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
public class ServicesBaseInfoAdd implements Serializable{

    @ApiModelProperty("套餐名称")
    @NotNull(message = "套餐名称不能为空")
    private String servicesName;

    @ApiModelProperty("套餐描述")
    private String serviceDesc;

    @ApiModelProperty("用户类型")
    @NotNull(message = "用户类型不能为空")
    private String userScope;

    /**
     * 套餐价格集合
     */
    private List<ServicesPriceAdd> prices;

    /**
     * 套餐价格
     */
    private List<Float> price;

    /**
     * 价格年限
     */
    private List<Integer> duration;

    /**
     * 套餐赠送天数
     */
    private List<Integer> giveDuration;

    /**
     * 免费渲染时间
     */
    private List<Integer> freeRenderDuration;

    /**
     * 赠送度币
     */
    private List<Integer> sanduCurrency;

    @ApiModelProperty("企业id")
    private String companyId;

    /**
     * 套餐购买 年-月-日
     */
    private List<Integer> priceUnit;

    @ApiModelProperty("销售渠道")
    @NotNull(message = "销售渠道不能为空")
    private String saleChannel;

    @ApiModelProperty("套餐备注")
    private String remark;

    @ApiModelProperty("套餐类型")
    @NotNull(message = "套餐类型不能为空")
    private Integer servicesType;

    @ApiModelProperty("套餐创建者")
    @NotNull(message = "套餐创建者不能为空")
    private String creator;

    @ApiModelProperty("修改者")
    private String modifier;
}
