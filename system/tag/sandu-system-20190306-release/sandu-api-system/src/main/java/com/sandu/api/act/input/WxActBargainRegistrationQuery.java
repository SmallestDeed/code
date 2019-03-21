package com.sandu.api.act.input;

import com.sandu.common.BaseQuery;
import lombok.Data;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * registration
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-20 14:31
 */
@Data
public class WxActBargainRegistrationQuery implements Serializable {

	private Integer pageNum = 1;
	private Integer pageSize = 20;
	private String openId;
	private String nickname;
	private Integer status;
	private Integer decorateStatus;
	private String actId;

}
