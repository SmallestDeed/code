package com.sandu.api.notify.wx.bo;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TemplateMsgReqParam implements Serializable {

	private String openId;
	
	private String appId;
	/**
	 * 小程序secret
	 */
	private String appSecret;
	/**
	 * 前端提交的formId
	 */
	private String formId;
	/**
	 * 模板消息类型
	 */
	private Integer msgType;
	/**
	 * 模板消息id
	 */
	private String templateId;
	/**
	 * 模板数据
	 */
	private Map templateData;
	/**
	 * 跳转页
	 */
	private String page;
    
}
