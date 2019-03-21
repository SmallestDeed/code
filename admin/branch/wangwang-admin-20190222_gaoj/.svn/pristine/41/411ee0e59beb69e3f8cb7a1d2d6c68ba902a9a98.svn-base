package com.sandu.api.solution.model.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * CopyShareDesignPlanPO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
@ApiModel("复制分享方案进自己方案库")
public class CopyShareDesignPlanPO implements Serializable {

    @NotEmpty(message = "方案不能为空")
    private List<Integer> sourceDesignPlanIds;

    @NotNull(message = "公司不能为空")
    @Min(value = 1,message = "公司不合法")
    private Integer targetCompanyId;
}
