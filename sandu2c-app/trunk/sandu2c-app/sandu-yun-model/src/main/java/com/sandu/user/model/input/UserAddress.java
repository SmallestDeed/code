package com.sandu.user.model.input;

import lombok.Data;
import java.io.Serializable;

/**
 * @author oxq
 * @Title: 更新用户地址
 * @Package
 * @Description:
 * @date 2018/7/31
 */
@Data
public class UserAddress implements Serializable {
	private static final long serialVersionUID = -7570426701430489077L;
	private String province;
	private String city;
	private String area;
	private String street;
	private String address;
}
