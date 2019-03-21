package com.sandu.api.act3.input;

import com.sandu.api.act3.output.LuckyWheelPrizeListBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 修改活动奖品
 * @author WangHaiLin
 * @date 2019/1/16  19:18
 */
@Data
public class LuckyWheelPrizeUpdate implements Serializable {

    @ApiModelProperty(value = "活动Id",required = true)
    @NotNull(message = "活动Id不能为空")
    private String actId;

    @ApiModelProperty(value = "活动奖品")
    private List<LuckyWheelPrizeListBO> prizeList;

}
