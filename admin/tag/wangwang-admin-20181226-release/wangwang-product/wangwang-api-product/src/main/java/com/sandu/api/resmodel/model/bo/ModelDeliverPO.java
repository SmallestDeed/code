package com.sandu.api.resmodel.model.bo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author by bvvy
 * @date 2018/4/12
 * <p>
 * 模型交付bo
 */
@Data
public class ModelDeliverPO implements Serializable {
    /**
     * 交付给的公司id
     */
    private List<Integer> toCompanyId;
    /**
     * 交付人的id
     */
    @NotNull(message = "交付人不能为空")
    @Min(value = 1, message = "交付人的id不合法")
    private Integer userId;
    /**
     * 交付的模型di
     */
    @NotEmpty(message = "模型不能为空")
    private List<Long> modelId;
    /**
     * 从这个公司交付
     */
    @NotNull(message = "来自的公司不能为空")
    @Min(value = 1, message = "来自的公司不合法")
    private Long fromCompanyId;
}
