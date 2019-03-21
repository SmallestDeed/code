package com.sandu.api.grouppurchase.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 * @ClassName PurchaseInnerUserInfo
 * @date 2018/11/6
 */
@Data
public class PurchaseInnerUserInfo implements Serializable {
	private Long userId;
	private Long picId;
	private String picPath;
	private String userName;
	private Long isMaster;

	private Date joinTime;

	private Long joinStatus;
}
