package com.sandu.common;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandu.common.constant.ResponseEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * HTTP响应 bean
 *
 * @author Yoco (yocome@gmail.com)
 * @datetime 2017/12/11 23:00
 */

@Getter
@Setter
@ToString
public class ReturnData<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "前端接口标识")
	private String msgId;
	
	/**
	 * 处理是否成功：true 成功，false 失败
	 */
	@ApiModelProperty(value = "处理是否成功：true 成功，false 失败")
	private Boolean status;

	/**
	 * 返回消息
	 */
	@ApiModelProperty(value = "返回消息")
	private String message;

	/**
	 * 响应码
	 */
	@ApiModelProperty(value = "响应码")
	private String code;

	/**
	 * 响应数据
	 */
	@JsonInclude(JsonInclude.Include.ALWAYS)
	@ApiModelProperty(value = "响应数据")
	private T data;

	/**
	 * 数据列表
	 */
	@JsonInclude(JsonInclude.Include.ALWAYS)
	@ApiModelProperty(value = "结果集")
	private List<T> dataList;

	/**
	 * 数据总共数量
	 */
	@ApiModelProperty(value = "总记录数")
	private Long total;
	
	/**
	 * TODO 资源地址
	 */
	private String resourceUrl;

	private ReturnData() {
	}

	public static ReturnData builder() {
		return new ReturnData();
	}

	public ReturnData status(Boolean isSuccess) {
		this.status = isSuccess;
		return this;
	}

	public ReturnData code(ResponseEnum response) {
		this.code = response.getCode();
		this.message = response.getMessage();
		return this;
	}

	public ReturnData message(String message) {
		this.message = message;
		return this;
	}

	public ReturnData data(T data) {
		this.data = data;
		return this;
	}

	public ReturnData list(List<T> list) {
		this.dataList = list;
		return this;
	}

	public ReturnData total(long total) {
		this.total = total;
		return this;
	}

	public ReturnData msgId(String msgId) {
		this.msgId = msgId;
		return this;
	}
}
