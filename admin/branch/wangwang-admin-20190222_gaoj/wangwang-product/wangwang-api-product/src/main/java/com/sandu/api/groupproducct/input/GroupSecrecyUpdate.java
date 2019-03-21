package com.sandu.api.groupproducct.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * wangwang-product
 *
 * @author Sandu
 * @datetime 2018/3/29 16:19
 */
@Data
public class GroupSecrecyUpdate implements Serializable {
    @ApiModelProperty(value = "组合id集合", required = true)
    @NotNull(message = "组合id不能为空")
    @Size(min = 1, message = "组合ID最少为一个")
    private List<Integer> groupIds;

    @ApiModelProperty(value = "组合公开状态:0非公开,1公开", required = true)
    @NotNull(message = "组合公开状态不能为空")
    @Range(max = 1, min = 0, message = "请输入正确的公开状态参数")
    private Integer secrecy;
}
