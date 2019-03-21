package com.sandu.api.solution.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class DesignPlanUpDown implements Serializable {
	
	@ApiModelProperty(value = "方案id集合")
	@Size(min = 1, message = "方案ID最少为一个")
	private List<Integer> planIds;

	@ApiModelProperty(value = "OPEN(公开方案),ONEKEY(一键方案),TEMPLATE(样板方案),2c平台ids")
	private String platformIds;

	@ApiModelProperty(value = "方案类型(1:普通方案;2-智能方案；3:全屋方案)")
	@Size(min = 1, message = "请选择方案类型")
	private String designPlanType;

}
