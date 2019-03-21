package com.sandu.systemutil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sandu
 * @ClassName BaseQueryModel
 * @date 2018/11/6
 */
@Data
public class BaseQueryModel implements Serializable {
	@ApiModelProperty("页码")
	@NotNull(message = "请输入正确的页码")
	@Min(value = 1, message = "请输入正确的页码")
	private Integer page;

	@ApiModelProperty("页面数量")
	@NotNull(message = "请输入正确的页面数量")
	@Min(value = 1, message = "请输入正确的页面数量")
	private Integer limit;

	@ApiModelProperty("排序方式 ")
	private String orderMethod;

	@ApiModelProperty("排序规则")
	private String orderField;


}
