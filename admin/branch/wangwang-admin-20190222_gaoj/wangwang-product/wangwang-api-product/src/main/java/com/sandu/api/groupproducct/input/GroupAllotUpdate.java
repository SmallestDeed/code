package com.sandu.api.groupproducct.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * wangwang-product
 *
 * @author Sandu 
 * @datetime 2018/3/21 16:22
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GroupAllotUpdate implements Serializable{
    @ApiModelProperty(value = "组合id集合", required = true)
    @NotEmpty(message = "组合id不能为空")
    private List<Integer> groupIds;

    @ApiModelProperty(value = "平台类型:渠道:2b,线上:2c; 可同时勾选,以逗号分隔", required = true)
    @Pattern(regexp = "^([2][bc])?(,[2][bc])?$", message = "平台类型有错误")
    private String platformType;
}
