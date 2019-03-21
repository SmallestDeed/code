package com.sandu.api.product.input;

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
public class ProductUpDown implements Serializable {
	@ApiModelProperty(value = "产品id集合")
	@Size(min = 1, message = "产品ID最少为一个")
	private List<Integer> productIds;

	@ApiModelProperty(value = "线上产品上下架操作时，需上架的平台ID,多选以逗号分隔,未传的平台ID视为下架操作")
	@Pattern(regexp = "^([1-9]\\d{0,11})?(,[1-9]\\d{0,11})*$", message = "请输入有效的ID集合")
	private String platformIds;
}