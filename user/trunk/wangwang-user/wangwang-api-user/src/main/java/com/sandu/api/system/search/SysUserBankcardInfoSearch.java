package com.sandu.api.system.search;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysUserBankcardInfoSearch implements Serializable {

	private static final long serialVersionUID = -5971714213148039416L;

	/**
	 * 搜索条件: where user_id = #{userId}
	 */
	private Long userId;
	
	/**
	 * 搜索条件: where is_deleted = #{isDeleted}
	 */
	private Integer isDeleted;
	
}
