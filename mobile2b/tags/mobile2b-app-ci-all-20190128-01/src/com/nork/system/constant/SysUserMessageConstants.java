package com.nork.system.constant;

import java.io.Serializable;

public class SysUserMessageConstants implements Serializable {
	
	/**
	 * sys_user_message.is_read = 0: 该消息未读
	 */
	public final static Integer SYSUSERMESSAGE_ISREAD_NO = 0;
	
	/**
	 * sys_user_message.status = 1: 成功
	 */
	public final static Integer SYSUSERMESSAGE_STATUS_SUCCESS = 1;
	
	/**
	 * sys_user_message.platform_id = 1: 2B-移动端
	 */
	public final static Integer SYSUSERMESSAGE_PLATFORMID_2BMOBILE = 1;

	/**
	 * sys_user_message.message_type = 2: 系统消息
	 */
	public static final Integer SYSUSERMESSAGE_MESSAGETYPE_SYSTEM = 2;
	
}
