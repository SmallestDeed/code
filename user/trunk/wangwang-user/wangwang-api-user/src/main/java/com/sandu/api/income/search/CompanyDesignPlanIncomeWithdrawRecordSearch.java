package com.sandu.api.income.search;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class CompanyDesignPlanIncomeWithdrawRecordSearch implements Serializable {

	private static final long serialVersionUID = -8700357879825330860L;

	/**
	 * where apply_time > #{applyTimeAfter}
	 */
	private Date applyTimeAfter;
	
	/**
	 * where is_deleted = #{isDeleted}
	 */
	private Integer isDeleted;
	
	/**
	 * where apply_user_id = #{applyUserId}
	 */
	private Long applyUserId;
	
	/**
	 * where company_id = #{companyId}
	 */
	private Long companyId;
	
}
