package com.sandu.api.servicepurchase.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sandu.api.user.model.SysUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 * @ClassName ServiceInnerUserListVO
 * @date 2018/11/6
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInnerUserListVO implements Serializable {
	private String companyName;

	private String loginName;

	private String nickName;

	private String mobile;

	@JsonFormat(pattern = "yyyy-MM-dd-mm")
	private Date expireTime;

	@JsonIgnore
	private SysUser user;
}
