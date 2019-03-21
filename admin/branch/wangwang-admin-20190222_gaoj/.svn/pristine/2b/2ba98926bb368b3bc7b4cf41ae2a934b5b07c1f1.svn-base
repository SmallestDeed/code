package com.sandu.api.decoratecustomer.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sandu.common.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-10-20 14:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DecorateCustomerQuery extends BaseQuery implements Serializable {

	@JsonIgnore
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private String cityCode;

    private Integer decorateType;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date revisitTime;


	private String userName;

	private String mobile;

	private String revisitTimeStr;

	public void setRevisitTimeStr(String revisitTimeStr) {
		try {
			this.revisitTime = format.parse(revisitTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.revisitTimeStr = revisitTimeStr;
	}
}
