package com.sandu.service.notify.wx;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.sandu.api.applyHouse.model.BaseHouseApply;
import com.sandu.api.applyHouse.service.BaseHouseApplyService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.company.model.BaseCompanyMiniProgramConfig;
import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;
import com.sandu.api.company.service.BaseCompanyMiniProgramConfigService;
import com.sandu.api.company.service.BaseCompanyMiniProgramTemplateMsgService;
import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.notify.wx.bo.FormIdInfoBo;
import com.sandu.api.notify.wx.bo.TemplateMsgReqParam;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.commons.gson.GsonUtil;
import com.sandu.commons.http.HttpClientUtil;
import com.sandu.service.redis.RedisService;

@Service("templateMsgService")
public class TemplateMsgServiceImpl  implements TemplateMsgService{

	private Logger logger = LoggerFactory.getLogger(TemplateMsgServiceImpl.class);
	
    @Autowired
    private BaseCompanyMiniProgramConfigService miniProgramConfigService;
    
    @Autowired
    private BaseCompanyMiniProgramTemplateMsgService miniProgramTemplateMsgService;
    
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Resource
    private RedisService redisService;
    
    @Autowired
    private SysUserService sysUserService;

	@Autowired
	private BaseHouseApplyService baseHouseApplyService;

    private static final String REDIS_ACCESS_TOKEN_KEY_PREFIX="template_message:access_token:";
    private static final String REDIS_USER_RENDER_KEY_PREFIX="template_message:user_render_plan:";
    
    private static final String REDIS_MINI_USER_FORM_ID_PREFIX="template_message:user_form_id:";
    
    private static Map<Integer,String> RENDER_TYPE_MAP = new HashMap<Integer,String>();
    static {
    	RENDER_TYPE_MAP.put(0, "normal_render"); //装进我家
    	RENDER_TYPE_MAP.put(1, "replace_render"); //替换渲染
    }
    
    private static Map<Integer,String> RENDER_LEVEL_MAP = new HashMap<Integer,String>();
    static {
    	RENDER_LEVEL_MAP.put(1, "common_render");
        RENDER_LEVEL_MAP.put(4, "panorama_render");
        RENDER_LEVEL_MAP.put(6, "video");
        RENDER_LEVEL_MAP.put(8, "roam720");
    }


    /*****************************************发送模板消息公用方法***************************************************************/
    @Override
	public void collectMiniUserFormId(String openId, List<String> formIdList) {
    	List<String> formIdInfolist = new ArrayList<String>();
    	//微信formid有效期是7天,这里保存6天23小时
    	Date now = new Date();
    	Calendar expireDate = Calendar.getInstance();
        expireDate.setTime(now);
        expireDate.add(Calendar.HOUR_OF_DAY, 23); 
        expireDate.add(Calendar.DAY_OF_YEAR,6);
        
    	for(String formId:formIdList) {
    		//因为我们是在开发者工具中测试，所以得到的formId值为the formId is a mock one
    		//简单数据验证
    		if(formId!=null && !formId.contains("formId") && formId.length()>10) {
    			String formIdInfoJson = GsonUtil.toJson(new FormIdInfoBo(formId,dateFormat.format(expireDate.getTime())));
        		formIdInfolist.add(formIdInfoJson);
    		}
    		
    	}
    	redisService.rightPushAll(REDIS_MINI_USER_FORM_ID_PREFIX+openId, formIdInfolist);
	}
    
	@Override
	public TemplateMsgReqParam buildTemplateReqParam(SysUser user,Integer msgType, Map templateDate, Object... pageParams) {
		logger.info("----------------进入构造参数值的方法----------------");
    	String openId = user.getOpenId();
    	logger.info("----------------openId="+openId);
		String appId = user.getAppId();
		logger.info("----------------appId="+appId);
		String appSecret = this.getMiniProgramConfig(appId).getAppSecret();
		logger.info("----------------appSecret="+appSecret);
		String formId = this.getValidFormId(openId);
		logger.info("----------------formId="+formId);
		BaseCompanyMiniProgramTemplateMsg templateMsg = this.getMiniProgramTemplateMsg(appId, msgType);
		String templateId = templateMsg.getTemplateId();
		logger.info("----------------templateId="+templateId);
		String page = String.format(templateMsg.getPage()==null?"":templateMsg.getPage(), pageParams); /// page/aaa?aa=%1$s&bb=%2$s
		logger.info("----------------page="+page);
		return new TemplateMsgReqParam(openId, appId, appSecret, formId, msgType, templateId, templateDate, page);
	}



    /**
     * 获取有效的formId
     * @param openId
     * @return
     */
    private String getValidFormId(String openId) {
    	List<String> formIdList = redisService.range(REDIS_MINI_USER_FORM_ID_PREFIX+openId, 0, -1);

    	String validFormId = "";
    	int trimStart = 0;
    	Date now = new Date();
    	int size;
    	for (int i = 0; i < (size = formIdList.size()); i++) {
    		try {
	    		FormIdInfoBo formIdInfoBo = GsonUtil.fromJson(formIdList.get(i), FormIdInfoBo.class);
	    		if(dateFormat.parse(formIdInfoBo.getExpire()).compareTo(now)>0) {
	    			validFormId = formIdInfoBo.getFormId();
	    			trimStart = i + 1;
	    			break;
	    		}
    		}catch(Exception ex) {
    			logger.error("getValidFormId error:",ex);
    		}
    	}

    	// 移除本次使用的和已过期的
    	redisService.trim(REDIS_MINI_USER_FORM_ID_PREFIX + openId, trimStart == 0 ? size : trimStart, -1);

    	return validFormId;
    }
    
    @Override
    public void sendTemplateMsg(TemplateMsgReqParam param) {
		logger.info("----------------进入发送消息的方法----------------");
    	//0.验证输入参数
		checkTemplateMsgParam(param);
        //1.获取accessToken
        String accessToken = this.getAccessToken(param.getAppId(), param.getAppSecret());
        logger.info("openid:"+param.getOpenId()+",accessToken:"+accessToken);
        if(StringUtils.isBlank(accessToken)) {
        	 throw new RuntimeException("获取accessToken失败");
        }
        
        //2.发送模板消息   
        String reqUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token={0}";
        Map<String, String> paramsMap = this.getTemplateMsgParam(param.getOpenId(), param.getTemplateId(), param.getPage(), param.getFormId(), param.getTemplateData()); 
        String paramsJson = GsonUtil.toJson(paramsMap);
        logger.info("发送模板消息参数:"+paramsJson);
        String resultStr = HttpClientUtil.doPostJsonMethod(MessageFormat.format(reqUrl,accessToken),paramsJson);
       
        //3.解析返回结果
        Map<String, Object> map = GsonUtil.fromJson(resultStr, Map.class);
        if (map != null) {
        	Double errcode = (Double)map.get("errcode");
            if (errcode==null || errcode.intValue()!=0) {
                logger.error("发送模板消息失败param:{},result:{}" ,param.toString(), resultStr);
                throw new RuntimeException("发送模板消息失败");
            }
        }
	}
    
    private void checkTemplateMsgParam(TemplateMsgReqParam param) {
    	if(param==null) {
    		throw new RuntimeException("发送模板消息参数不能为空");
    	}
    	
    	if(StringUtils.isBlank(param.getAppId())){
    		throw new RuntimeException("appid不能为空");
    	}
    	if(StringUtils.isBlank(param.getAppSecret())){
    		throw new RuntimeException("appSecret不能为空");
    	}
    	if(StringUtils.isBlank(param.getOpenId())){
    		throw new RuntimeException("openid不能为空");
    	}
    	if(StringUtils.isBlank(param.getFormId())){
    		throw new RuntimeException("formId不能为空");
    	}
    	if(param.getMsgType()==null){
    		throw new RuntimeException("msgType不能为空");
    	}
    	if(StringUtils.isBlank(param.getTemplateId())){
    		throw new RuntimeException("templateId不能为空");
    	}
    	if(param.getTemplateData()==null){
    		throw new RuntimeException("templateData不能为空");
    	}
    	
    }
    
    
    
    
    /*****************************************发送渲染模板消息(旧方法,未改造)***************************************************************/
    
    
    @Override
    public boolean saveUserRenderFormId(Long userId,Long designPlanId,String formId,String forwardPage,Integer renderType,Integer renderLevel) {
    	SysUser user = sysUserService.get(userId.intValue());
    	if(user!=null) {
    		Map<String,String> userMap = new HashMap<String,String>();
        	userMap.put("appid", user.getAppId());
        	userMap.put("openid", user.getOpenId());
        	userMap.put("page", forwardPage);
        	userMap.put("formId", formId);
        	String key = REDIS_USER_RENDER_KEY_PREFIX+RENDER_TYPE_MAP.get(renderType)+":"+RENDER_LEVEL_MAP.get(renderLevel)+":"+userId.toString()+"-"+designPlanId.toString();
        	redisService.set(key, GsonUtil.toJson(userMap), Long.valueOf(7*24*60*60)); //7天有效,跟微信的过期时间保持一致
        	logger.info("formId保存成功:"+key);
        	return true;
    	}
    	return false;
    	
    }
    
    
    @Override
    public synchronized void sendRenderTemplateMsg(Long userId, Long designPlanId,String designPlanName,String renderResult,Integer renderType,Integer renderLevel) {
    	String key = REDIS_USER_RENDER_KEY_PREFIX+RENDER_TYPE_MAP.get(renderType)+":"+RENDER_LEVEL_MAP.get(renderLevel)+":"+userId.toString()+"-"+designPlanId.toString();
    	Object obj = redisService.get(key);
    	if(obj!=null) {
    		Map userMap = GsonUtil.fromJson(obj.toString(),Map.class);
    		//获取模块数据
    		Map data = this.getRenderTempalteData(designPlanName,renderResult);
    		//发送消息
        	send((String)userMap.get("appid"), (String)userMap.get("openid"), 
        			(String)userMap.get("page"), (String)userMap.get("formId"),BaseCompanyMiniProgramTemplateMsg.TEMPLATE_TYPE_RENDER,data);
        	redisService.remove(key);
    	}else {
    		logger.info("渲染模板消息通知失败:未找到用户渲染方案对应的formId:"+key);
    		throw new RuntimeException("渲染模板消息通知失败:未找到用户渲染方案对应的formId:"+key);
    	}
    }
    
    /**
	 * 获取模板数据,替换模板里面的配置参数
	 * @return
	 */
	private Map getRenderTempalteData(String designPlanName,String renderResult) {
		Map<String, Map> keywordsMap = new LinkedHashMap<String, Map>();
		Map<String, String> keywordTempMap = null;
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", designPlanName);
		keywordsMap.put("keyword1", keywordTempMap);
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", renderResult);
		keywordsMap.put("keyword2", keywordTempMap);
		
		return keywordsMap;
	}
	
	
	
	
	/**
	 * 调用微信发送模板消息接口
	 * @param appId
	 * @param openId
	 * @param templateId
	 * @param page
	 * @param formId
	 * @param data
	 */
	private void send(String appId,String openId, String page, String formId, Integer msgType,Map data) {
		String appSecret = this.getMiniProgramConfig(appId).getAppSecret();
        String templateId = this.getMiniProgramTemplateMsg(appId, msgType).getTemplateId();
        //1.获取accessToken
        String accessToken = this.getAccessToken(appId, appSecret);
        logger.info("openid:"+openId+",accessToken:"+accessToken);
        if(StringUtils.isBlank(accessToken)) {
        	 throw new RuntimeException("获取accessToken失败");
        }
        
        //2.发送模板消息   
        String reqUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token={0}";
        Map<String, String> paramsMap = this.getTemplateMsgParam(openId, templateId, page, formId, data); 
        String paramsJson = GsonUtil.toJson(paramsMap);
        logger.info("发送模板消息参数:"+paramsJson);
        String resultStr = HttpClientUtil.doPostJsonMethod(MessageFormat.format(reqUrl,accessToken),paramsJson);
       
        //3.解析返回结果
        Map<String, Object> map = GsonUtil.fromJson(resultStr, Map.class);
        if (map != null) {
        	Double errcode = (Double)map.get("errcode");
            if (errcode==null || errcode.intValue()!=0) {
                logger.error("发送模板消息返回:" + resultStr);
                throw new RuntimeException("发送模板消息失败");
            }
        }
	}
	

	private BaseCompanyMiniProgramConfig getMiniProgramConfig(String appId) {
		BaseCompanyMiniProgramConfig config = miniProgramConfigService.getMiniProgramConfig(appId);
        if (config == null || StringUtils.isBlank(config.getAppSecret())) {
            logger.error("appid错误或者服务器未配置secret:" + appId);
            throw new RuntimeException("appid错误或者服务器未配置secret");
        }
        return config;
	}
	

	private BaseCompanyMiniProgramTemplateMsg getMiniProgramTemplateMsg(String appId, Integer msgType) {
		BaseCompanyMiniProgramTemplateMsg templateMsg = miniProgramTemplateMsgService.getMiniProgramTempateMsg(appId,msgType);
		if (templateMsg == null || StringUtils.isBlank(templateMsg.getTemplateId())) {
			logger.error("未配置渲染模板消息id" + appId);
			throw new RuntimeException("未配置渲染模板消息id");
		}
		return templateMsg;
	}
	
	/**
	 * 获取accessToken:先从缓存拿,拿不到再去请求微信接口.获取到的token有效期是2个小时
	 * @param appId
	 * @param secret
	 * @return
	 */
	@Override
	public String getAccessToken(String appid, String secret) {
		Object obj = redisService.get(REDIS_ACCESS_TOKEN_KEY_PREFIX+appid);
		if(obj==null) {
			return callToGetAccessToken(appid,secret);
		}else {
			//验证token是否有效
			String accessToken = obj.toString();
			String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + accessToken;
			String resultStr = HttpClientUtil.doGetMethod(url);
			if (resultStr!=null && resultStr.contains("ip_list")) {
				return accessToken;
			}else {
				logger.info("无效token,重新取token:"+appid);
				return callToGetAccessToken(appid,secret);
			}
		}
	}
	
	private String callToGetAccessToken(String appId,String secret) {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
		String resultStr = HttpClientUtil.doGetMethod(MessageFormat.format(url,appId,secret));
		Map<String, Object> map = GsonUtil.fromJson(resultStr, Map.class);
		if (map != null) {
            String accessToken = (String) map.get("access_token");
            if (StringUtils.isBlank(accessToken)) {
                logger.error("获取accessToken返回:" + resultStr);
                return null;
            }
            redisService.set(REDIS_ACCESS_TOKEN_KEY_PREFIX+appId,accessToken,Long.valueOf(1800));//官方描述2个小时有效,实际却没有.这里改成半小时.
            return accessToken;
        }
		return null;
	}
	
	public static void main(String[] args) { 
		//String page = String.format("/page/aaa?aa=%1$s&bb=%2$s", 12,"b"); ///page/aaa?aa=%1$s&bb=%2$s
		System.out.println(String.format("pages/cutprice/cutprice?actId=%1$s&regId=%2$s", "",2));
		//System.out.println(page);
        
		Map<String, Map> dataMap = new HashMap<String, Map>();
		Map<String, Map> keywordsMap = new LinkedHashMap<String, Map>();
		Map<String, String> keywordTempMap = null;
		
		dataMap.put("data", keywordsMap);
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", "aaa");
		keywordsMap.put("keyword1", keywordTempMap);
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", "bbb");
		keywordsMap.put("keyword2", keywordTempMap);
		
	 
		 Map paramsMap = new HashMap();
		 paramsMap.put("touser", "o-D9N5UU24UYBRmLdxvoTntHalJQ");
		 paramsMap.put("template_id", "abc"); 
		 paramsMap.put("page", "/pages/my-tasks/my-tasks");
		 
		 paramsMap.put("form_id", "1532670076365");
		 paramsMap.put("data", dataMap);
		
		 System.out.println(GsonUtil.toJson(dataMap));
	       //2.发送模板消息   
      //  String reqUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token={0}";
       
       // String resultStr = HttpClientUtil.doPostJsonMethod(MessageFormat.format(reqUrl,"12_wbsKqHRknUz3zYSnceqUdz78zVqLlq8g-t4BIPv_3h9y8WX9D_M0H-ENnG4FxmvqnyeDdApdPsGEtIOwctmXBVSpWh3OgqqUEF9Z8krGUs_xlJodZYBAtz7aS8Q3zfS-tYv5sNXCRORpuQLINPHaAFAWYQ"),GsonUtil.toJson(paramsMap));
       // System.out.println(resultStr);
		//String url = "https://system.ci.sanduspace.com/v1/notify/wx/sendRenderTemplateMsg";
		/*Map<String, String> paramsMap = new HashMap<String,String>();
		paramsMap.put("userId","111");
		 paramsMap.put("formId","aaa");
	        String resultStr = HttpClientUtil.doPostMethod(  url,paramsMap);
		 */
		//System.out.println(resultStr);
	}
	
	/**
	 * 获取调用微信发送模板消息方法参数
	 * @param openid
	 * @param templateId
	 * @param page
	 * @param formId
	 * @param data
	 * @return
	 */
	private Map<String, String> getTemplateMsgParam(String openid,String templateId,String page,String formId,Map data){
		 Map paramsMap = new HashMap();
		 paramsMap.put("touser", openid);
		 paramsMap.put("template_id", templateId);
		 if(StringUtils.isNotBlank(page)) {
			 paramsMap.put("page", page);
		 }
		 paramsMap.put("form_id", formId);
		 paramsMap.put("data", data);
		 return paramsMap;
	}

	@Override
	public Map<String, Object> sendHandlerApplyHouseMsg(Integer applyHouseId) {
	    logger.info("begin to build templateMsg info =>{}" + applyHouseId);

		//获取申请户型信息
		BaseHouseApply baseHouseApply = baseHouseApplyService.selectByPrimaryKey(applyHouseId);

		if (Objects.isNull(baseHouseApply)){
			logger.error("未找到户型申请记录" + applyHouseId);
            throw new RuntimeException("未找到户型申请记录");
		}

		//获取用户信息
		SysUser sysUser = sysUserService.get(baseHouseApply.getUserId());

		if (Objects.isNull(sysUser)){
			logger.error("获取用户信息失败" + baseHouseApply.getUserId());
			throw new RuntimeException("获取用户信息失败");
		}

		Map<String,Object> dataMap = new HashMap<>();

		dataMap.put("house",baseHouseApply);
		dataMap.put("sysUser",sysUser);
		dataMap.put("msgData",buildSendApplyHouseMsgData(baseHouseApply,sysUser.getNickName()));//构建模板消息信息

		return dataMap;
	}


	private Map<String, Map> buildSendApplyHouseMsgData(BaseHouseApply baseHouseApply,String nickName) {
	    logger.info("houseStatus =>{}" + baseHouseApply.getStatus());
		StringBuilder ReplyContent = new StringBuilder();
		String handlerStatus = "";
		if (StringUtils.isNotEmpty(nickName)){
			ReplyContent.append(nickName);
		}
		ReplyContent.append("您好,");
		switch (baseHouseApply.getStatus()){
			case 0:
                handlerStatus = "录入中";
                ReplyContent.append(baseHouseApply.getLivingInfo());
                ReplyContent.append("户型正在录入中，我们将会在两个工作日内完成录入，并微信通知您");
                break;
            case 1:
				handlerStatus = "已录入";
				ReplyContent.append("我们已经录入了");
				ReplyContent.append("【");
				ReplyContent.append(baseHouseApply.getLivingInfo());
				ReplyContent.append("】");
				ReplyContent.append("小区的户型");
				ReplyContent.append(",快去看看有没有您的家吧");
				break;
			case 2:
				handlerStatus = "无此小区";
				ReplyContent.append("我们未找到您的小区");
				ReplyContent.append("【");
				ReplyContent.append(baseHouseApply.getLivingInfo());
				ReplyContent.append("】");
				ReplyContent.append(",请重新录入您的小区信息");
				break;
			case 3:
				handlerStatus = "未找到户型资料";
				ReplyContent.append("我们未找到您小区");
				ReplyContent.append("【");
				ReplyContent.append(baseHouseApply.getLivingInfo());
				ReplyContent.append("】");
				ReplyContent.append("的户型,请上传户型图");
				break;
		}

		Map<String, Map> dataMap = new HashMap<>();
		Map<String, String> keywordTempMap = null;

		keywordTempMap = new HashMap<>();
		keywordTempMap.put("value",handlerStatus);
		dataMap.put("keyword1", keywordTempMap);

		keywordTempMap = new HashMap<>();
		keywordTempMap.put("value",ReplyContent.toString());
		dataMap.put("keyword2", keywordTempMap);

		keywordTempMap = new HashMap<>();
		keywordTempMap.put("value",dateFormat.format(baseHouseApply.getGmtModified()));
		dataMap.put("keyword3", keywordTempMap);

		return dataMap;

	}
}
