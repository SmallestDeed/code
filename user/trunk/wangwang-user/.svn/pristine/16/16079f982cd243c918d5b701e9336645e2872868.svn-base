package com.sandu.api.user.model;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserJurisdiction implements Serializable {
	private static final long serialVersionUID = 1L;
	/**已开通**/
	public static final int JURISDICTION_STATUS_OPEN = 1; 
	/**已禁用**/
	public static final int JURISDICTION_STATUS_FORBID = 2; 
	/**已结束**/
	public static final int  JURISDICTION_STATUS_CLOSE = 3; 
	private Long id;
	/**用户id **/
	private Long userId;
	/**平台id **/
	private Long platformId;
	/**权限状态：1(已开通)、2(已禁用)、3(已结束) **/
	private Integer jurisdictionStatus;
	/**权限生效时间 **/
	private Date jurisdictionEffectiveTime;
	/**权限到期时间 **/
	private Date jurisdictionFailureTime;
	/**last操作人id **/
	private Long modifierUserId;
	/**系统编码 **/
	private String sysCode;
	/**创建者 **/
	private String creator;
	/**创建时间 **/
	private Date gmtCreate;
	/**修改人 **/
	private String modifier;
	/**修改时间 **/
	private Date gmtModified;
	/**是否删除：0,未删除；1,已删除 **/
	private Integer isDeleted;
	/**备注 **/
	private String remark;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserJurisdiction)) return false;
		return this.getPlatformId().equals(((UserJurisdiction) obj).getPlatformId());
	}

	@Override
	public int hashCode() {
		return (Objects.toString(this.getUserId(), "")
				+ Objects.toString(this.getPlatformId(), "")).hashCode();
	}
}
