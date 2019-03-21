package com.sandu.api.notify.wx;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sandu.api.notify.wx.bo.TemplateMsgReqParam;
import com.sandu.api.user.model.SysUser;

@Component
public interface TemplateMsgService{
	
	/**
	 * 收集用户formId
	 * @param openId
	 * @param formIdList
	 */
	public void collectMiniUserFormId(String openId,List<String> formIdList);

	
	/**
	 * 构建模板消息参数
	 * @param user
	 * @param templateDate
	 * @param pageParams
	 * @return
	 */
	TemplateMsgReqParam buildTemplateReqParam(SysUser user, Integer msgType,Map templateData, Object[] pageParams);
	
	
	/**
	 * 发送模板消息
	 * @param param
	 */
	public void sendTemplateMsg(TemplateMsgReqParam param);
	
	/**
	 * redis存储用户渲染方案对应的formId
	 * @param userId
	 * @param designPlanId
	 * @param formId
	 * @param forwardPage
	 * @param renderType 1为普通渲染 2替换渲染
	 * @param renderLevel 渲染级别 1:普通照片级 4:720单点 6.视频 8:720多点
	 */
	public boolean saveUserRenderFormId(Long userId,Long designPlanId,String formId,String forwardPage,Integer renderType,Integer renderLevel);
	
	/**
	 * 发送渲染模板消息
	 * @param userId
	 * @param designPlanId
	 * @param designPlanName
	 * @param renderResult
	 * @param renderType 1为普通渲染 2替换渲染
	 * @param renderLevel 渲染级别 1:普通照片级 4:720单点 6.视频 8:720多点
	 */
	void sendRenderTemplateMsg(Long userId, Long designPlanId,String designPlanName,String renderResult,Integer renderType,Integer renderLevel);

	/**
	 * 获取小程序access token
	 *
	 * @param appid
	 * @param secret
	 * @return
	 */
	String getAccessToken(String appid, String secret);

    Map<String,Object> sendHandlerApplyHouseMsg(Integer applyHouseId);
}
