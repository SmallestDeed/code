package com.nork.decorateOrder.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * DecoratePriceController.submitPrice 提交报价接口参数接受bean
 * 
 * @author huangsongbo
 *
 */
@Data
public class DecoratePriceSubmitPriceParamDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 报价订单id
	 */
	@NotNull(message = "参数 id 不能为空")
	private Long id;
	
	/**
	 * 材料费(元)
	 */
	@NotNull(message = "参数 materialFee 不能为空")
	private Integer materialFee;
	
	/**
	 * 质检费(元)
	 */
	@NotNull(message = "参数 checkFee 不能为空")
	private Integer checkFee;
	
	/**
	 * 人工费(元)
	 */
	@NotNull(message = "参数 labourFee 不能为空")
	private Integer labourFee;
	
	/**
	 * 设计费(元)
	 */
	@NotNull(message = "参数 designFee 不能为空")
	private Integer designFee;
	
}
