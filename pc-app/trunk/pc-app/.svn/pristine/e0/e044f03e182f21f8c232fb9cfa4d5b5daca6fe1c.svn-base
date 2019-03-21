package com.nork.system.controller.web;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResVersion;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysVersion;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResVersionService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysVersionService;
//import com.nork.system.service.impl.SysVersionServiceImpl.versionType;

@Controller
@RequestMapping("/{style}/web/system/sysVersion")
public class WebSysVersionController {
	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	Logger logger = Logger.getLogger(WebSysVersionController.class);

	@Autowired
	private SysVersionService sysVersionService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private ResFileService resFileService;
	
	@Autowired
	private ResVersionService resVersionService;
	
	/**
	 * 最新版本检测接口
	 * @author zhaobl
	 * update by chenm
     * 版本资源存储表从 res_file 换成 res_version
     * 户型绘制版本包需不要壳进行自动更新,所以增加参数 isNeedShell 
	 * @param systemType 平台类型
	 * @param systemTypeDigits 系统类型 32/64 默认为64
	 * @param isNeedShell 是否需要校验壳  1(需要)，0 （不需要），为空则默认为需要
	 * @return
	 */
	@RequestMapping("/versionCheck")
	@ResponseBody
	public Object versionCheck(String msgId,Integer systemType,Integer systemTypeDigits,@RequestParam(value = "isNeedShell",required = false)Integer isNeedShell){
	  
	  return sysVersionService.getLatestVersionInfo(msgId, systemType, systemTypeDigits, isNeedShell);
    }

	/**
	 * 填充数据
	 * @param sysVersion
	 * @param dicList
	 */
	public void paddingData(SysVersion  sysVersion ,List<SysDictionary>  dicList){
		for (SysDictionary dictionary : dicList) {
			if( "telephone".equals(dictionary.getValuekey()) ){
				sysVersion.setTelephone(dictionary==null?"0755—23895307":dictionary.getAtt1());
				sysVersion.setTelephoneName(dictionary==null?"客服电话":dictionary.getName());
				continue;
			}
			if( "officialWebsitePath".equals(dictionary.getValuekey()) ){
				sysVersion.setOfficialWebsitePath(dictionary==null?"http://www.sanduspace.cn/":dictionary.getAtt1());
				sysVersion.setOfficialWebsiteName(dictionary==null?"访问官网":dictionary.getName());
				continue;
			}
			if( "shellCommonProblemPath".equals(dictionary.getValuekey()) ){
				sysVersion.setShellCommonProblemPath(dictionary==null?"http://www.sanduspace.cn/static/faq/quest1.htm":dictionary.getAtt1());
				sysVersion.setShellCommonProblemName(dictionary==null?"常见问题":dictionary.getName());
				continue;
			}
			if( "appCommonProblemPath".equals(dictionary.getValuekey()) ){
				sysVersion.setAppCommonProblemPath(dictionary==null?"http://www.sanduspace.cn/static/faq/quest2.htm":dictionary.getAtt1());
				sysVersion.setAppCommonProblemName(dictionary==null?"常见问题":dictionary.getName());
				continue;
			}
			if( "noticePath".equals(dictionary.getValuekey()) ){
				sysVersion.setNoticePath(dictionary==null?"http://www.sanduspace.cn/notice":dictionary.getAtt1());
				sysVersion.setNoticeName(dictionary==null?"系统公告":dictionary.getName());
				continue;
			}
		}
	}

	/**
	 * 最新版本检测接口
	 * @author huangsongbo
	 * @param systemType 平台类型
	 * @return
	 */
	@RequestMapping("/versionCheck2")
	@ResponseBody
	public Object versionCheck2(String msgId,Integer systemType){
		return sysVersionService.getVersionFileInfo(msgId, systemType);
	}
	
}
