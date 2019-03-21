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
	
//	/**
//	 * 最新版本检测接口
//	 * @author huangsongbo
//	 * @param systemType 平台类型
//	 * @param version 版本号
//	 * @return
//	 */
//	@RequestMapping("/versionCheckWeb")
//	@ResponseBody
//	public Object versionCheck(String msgId,Integer systemType){
//		Map<String,String> map=sysVersionService.versionCheckVerifyParams(msgId,systemType);
//		if(StringUtils.equals("false", map.get("success")))
//			return new ResponseEnvelope<>(false, map.get("msg"), msgId);
//		/*得到最新版本*/
//		/**SysVersion sysVersion=sysVersionService.getLatestVersion(systemType);*/
//		SysVersion sysVersion=SysVersionCacher.getLatestVersion(systemType);
//		
//		return new ResponseEnvelope<>(sysVersion, msgId, true);
//	}
	
	/**
	 * 最新版本检测接口
	 * @author huangsongbo
	 * @param systemType 平台类型
	 * @return
	 */
//	@RequestMapping("/versionCheck_old")
//	@ResponseBody
//	public Object versionCheck_old(String msgId,Integer systemType){
//		String serverUrl = Utils.getValue("app.resources.url", "http://res.sanduspace.cn");
//		/*systemType转换*/
//		SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValue("systemType", systemType);
//		if(sysDictionary==null)
//			return new RuntimeException("数据字典中未配置type=systemType,value="+systemType+"的数据字典");
//		if(StringUtils.isBlank(sysDictionary.getAtt1()))
//			return new RuntimeException("数据字典:type=systemType,value="+systemType+"未配置att1属性,请配置(对应媒介类型的value)");
//		systemType=Integer.valueOf(sysDictionary.getAtt1());
//		/*systemType转换->end*/
//		Map<String,String> map=sysVersionService.versionCheckVerifyParams(msgId,systemType);
//		if(StringUtils.equals("false", map.get("success")))
//			return new ResponseEnvelope<>(false, map.get("msg"), msgId);
//		/*得到最新版本 和 壳*/
//		SysVersion sysVersion = sysVersionService.getLatestVersion(systemType,"version");
//		SysVersion sysShellVersion = sysVersionService.getLatestVersion(systemType,"shell");
////		if(StringUtils.equals("2", cacheEnable)){
////			sysVersion=SysVersionCacher.getLatestVersion(systemType);
////		}else{
////			sysVersion=sysVersionService.getLatestVersion(systemType);
////		}
//		if(sysVersion==null||sysVersion.getId()==null){
//			/*返回初始版本(0.4.0)*/
//			SysVersion initSysVersion=new SysVersion();
//			initSysVersion.setVersion("0.4.0");
//			initSysVersion.setUpgradeMethod(0);
//			return new ResponseEnvelope<>(initSysVersion, msgId, true);
//		}
//		List<SysDictionary> dicList = sysDictionaryService.findAllByType("contactInformation");
//		if( Lists.isEmpty(dicList) ){
//			return new RuntimeException("数据字典中未配置type=contactInformation的数据字典");
//		}else{
//			for (SysDictionary dictionary : dicList) {
//				if( "telephone".equals(dictionary.getValuekey()) ){
//					sysVersion.setTelephone(dictionary==null?"0755—23895307":dictionary.getAtt1());
//					sysVersion.setTelephoneName(dictionary==null?"客服电话":dictionary.getName());
//					continue;
//				}
//				if( "officialWebsitePath".equals(dictionary.getValuekey()) ){
//					sysVersion.setOfficialWebsitePath(dictionary==null?"http://www.sanduspace.cn/":dictionary.getAtt1());
//					sysVersion.setOfficialWebsiteName(dictionary==null?"访问官网":dictionary.getName());
//					continue;
//				}
//				if( "shellCommonProblemPath".equals(dictionary.getValuekey()) ){
//					sysVersion.setShellCommonProblemPath(dictionary==null?"http://www.sanduspace.cn/static/faq/quest1.htm":dictionary.getAtt1());
//					sysVersion.setShellCommonProblemName(dictionary==null?"常见问题":dictionary.getName());
//					continue;
//				}
//				if( "appCommonProblemPath".equals(dictionary.getValuekey()) ){
//					sysVersion.setAppCommonProblemPath(dictionary==null?"http://www.sanduspace.cn/static/faq/quest2.htm":dictionary.getAtt1());
//					sysVersion.setAppCommonProblemName(dictionary==null?"常见问题":dictionary.getName());
//					continue;
//				}
//			}
//		}
//		/*处理返回的更新安装包版本*/
//		if(StringUtils.isNotBlank(sysVersion.getIosFileUrl())){
//			/*ios端地址*/
//			sysVersion.setAppPathInfo(sysVersion.getIosFileUrl());
//		}else{
//			//文件下载地址
//			if(sysVersion.getPatchFileId()!=null&&sysVersion.getPatchFileId()>0){
//				ResFile resFile=resFileService.get(sysVersion.getPatchFileId());
//				if(resFile!=null){
//					sysVersion.setVerZipPath(serverUrl+resFile.getFilePath());
//					sysVersion.setAppPath(sysVersionService.getAppPathInfo(resFile.getFilePath()));
//				}else{
//					logger.error("resfile is null ! pathfileid = " + sysVersion.getPatchFileId());
//					return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//				}
//				logger.info("=========================AppPath : "+sysVersion.getVerZipPath());
//			}
//			if(sysVersion.getVerExeFileId() != null && sysVersion.getVerExeFileId() > 0){
//				ResFile resFile=resFileService.get(sysVersion.getVerExeFileId());
//				if(resFile!=null){
//					sysVersion.setVerExePath(serverUrl+resFile.getFilePath());
//				}else{
//					logger.error("resfile is null ! pathfileid = " + sysVersion.getPatchFileId());
//					return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//				}
//				logger.info("=========================VerExePath : "+sysVersion.getVerExePath());
//			}
//		}
//		if(sysShellVersion != null){
//			//文件下载地址
//			if(sysShellVersion.getPatchFileId()!=null&&sysShellVersion.getPatchFileId()>0){
//				ResFile resFile=resFileService.get(sysShellVersion.getPatchFileId());
//				if(resFile!=null){
//					sysVersion.setShellZipPath(serverUrl+resFile.getFilePath());
//				}else{
//					logger.error("resfile is null ! pathfileid = " + sysShellVersion.getPatchFileId());
//					return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//				}
//				logger.info("=========================ShellZipPath : "+sysVersion.getShellZipPath());
//			}
//			//壳文件路径
//			if (sysShellVersion.getShellExeFileId() != null && sysShellVersion.getShellExeFileId() > 0) {
//				ResFile resFile=resFileService.get(sysShellVersion.getShellExeFileId());
//				if (resFile != null) {
//					sysVersion.setShellExePath(serverUrl+resFile.getFilePath());
//				}else{
//					logger.error("resfile is null ! pathfileid = " + sysVersion.getShellExeFileId());
//					return new ResponseEnvelope<>(false, "更新壳失败，将使用本地版本运行!",msgId);
//				}
//				logger.info("=========================ShellExePath : "+sysVersion.getShellExePath());
//			}
//			if( StringUtils.isNotBlank(sysShellVersion.getVersion()) ){
//				sysVersion.setShellVersion(sysShellVersion.getVersion());
//			}
//		}
//		return new ResponseEnvelope<>(sysVersion, msgId, true);
//	}
	
	/**
	 * 最新版本检测接口
	 * @author zhaobl
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
// 
//		String serverUrl = Utils.getValue("app.resources.url", "http://show.dev.sanduspace.com");
//		/*systemType转换*/
//		SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValue("systemType", systemType);
//		if(sysDictionary==null)
//			return new RuntimeException("数据字典中未配置type=systemType,value="+systemType+"的数据字典");
//		if(StringUtils.isBlank(sysDictionary.getAtt1()))
//			return new RuntimeException("数据字典:type=systemType,value="+systemType+"未配置att1属性,请配置(对应媒介类型的value)");
//		systemType=Integer.valueOf(sysDictionary.getAtt1());
//		/*systemType转换->end*/
//		Map<String,String> map=sysVersionService.versionCheckVerifyParams(msgId,systemType);
//		if(StringUtils.equals("false", map.get("success")))
//			return new ResponseEnvelope<>(false, map.get("msg"), msgId);
//		/*得到最新版本 和 壳*/
//		 if(systemTypeDigits==null){
//			 systemTypeDigits = 64;
//		 }
//		 if(systemTypeDigits.intValue()!=32&&systemTypeDigits.intValue()!=64){
//			 return new ResponseEnvelope<>(false, "参数错误：systemTypeDigits!",msgId);
//		 }
//		 if(isNeedShell == null) {
//		   isNeedShell = NeedShell.isNeed.getValue();
//		 }
//		 if(!isNeedShell.equals(NeedShell.isNeed.getValue()) && !isNeedShell.equals(NeedShell.IsNotNeed.getValue())) {
//		   return new ResponseEnvelope<>(false, "参数错误：isNeedShell!",msgId);
//		 }
//		 /* 1为正式  2  为内部    默认为正式*/
//		 
//		 String  sysVersionType = app.getString("sys.version.type");
//		 if(StringUtils.isBlank(sysVersionType)){
//			 sysVersionType = "1" ;
//		 }
//		
//		SysVersion sysVersion = sysVersionService.getLatestVersion(systemType,"version");
//		SysVersion sysVersion_internal = sysVersionService.getLatestVersion(systemType,"version_internal");
//  		SysVersion sysShellVersion = sysVersionService.getLatestVersion(systemType,"shell");
//  		SysVersion sysShellVersion_internal = sysVersionService.getLatestVersion(systemType,"shell_internal");
//		if("1".equals(sysVersionType)){
//			if(sysVersion==null||sysVersion.getId()==null){/*返回初始版本(0.4.0)*/
//				SysVersion initSysVersion=new SysVersion();
//				initSysVersion.setVersion("0.4.0");
//				initSysVersion.setUpgradeMethod(0);
//				return new ResponseEnvelope<>(initSysVersion, msgId, true);
//			}
//			if(isNeedShell.equals(NeedShell.isNeed.getValue())) {
//    			if(sysShellVersion==null||sysShellVersion.getId()==null){/*返回初始版本(0.4.0)*/
//    				SysVersion initSysVersion=new SysVersion();
//    				initSysVersion.setVersion("0.4.0");
//    				initSysVersion.setUpgradeMethod(0);
//    				return new ResponseEnvelope<>(initSysVersion, msgId, true);
//    			}
//			}
//		}
//		if("2".equals(sysVersionType)){
//			if(sysVersion_internal==null||sysVersion_internal.getId()==null){/*返回初始版本(0.4.0)*/
//				SysVersion initSysVersion=new SysVersion();
//				initSysVersion.setVersion("0.4.0");
//				initSysVersion.setUpgradeMethod(0);
//				return new ResponseEnvelope<>(initSysVersion, msgId, true);
//			}
//			if(isNeedShell.equals(NeedShell.isNeed.getValue())) {
//    			if(sysShellVersion_internal==null||sysShellVersion_internal.getId()==null){/*返回初始版本(0.4.0)*/
//    				SysVersion initSysVersion=new SysVersion();
//    				initSysVersion.setVersion("0.4.0");
//    				initSysVersion.setUpgradeMethod(0);
//    				return new ResponseEnvelope<>(initSysVersion, msgId, true);
//    			}
//			}
//		}
// 
//		List<SysDictionary> dicList = sysDictionaryService.findAllByType("contactInformation");
//		if(Lists.isEmpty(dicList) ){
//			return new RuntimeException("数据字典中未配置type=contactInformation的数据字典");
//		}
//		if(!Lists.isEmpty(dicList) ){
//			if("1".equals(sysVersionType)){
//				paddingData(sysVersion,dicList);
//			}
//			if("2".equals(sysVersionType)){
//				paddingData(sysVersion_internal,dicList);
//			}
//		}
//
//		
//		/*处理返回的更新安装包版本*/
//		if( "1".equals(sysVersionType) && StringUtils.isNotBlank(sysVersion.getIosFileUrl())){
//			sysVersion.setAppPathInfo(sysVersion.getIosFileUrl());/*ios端地址*/
//		}else{
//			Integer patchFileId = null;
//			 if("1".equals(sysVersionType)){
//				 patchFileId = sysVersion.getPatchFileId();
//			 }
//			 if("2".equals(sysVersionType)){
//				 patchFileId = sysVersion_internal.getInternalPatchFileId();
//			 }
//			/*文件下载地址*/
//			if(patchFileId!=null&&patchFileId.intValue()>0){
//
//				ResVersion resFile = null;
//				ResVersion exeFile = null;
//				ResVersion rarFile = null;
//				resFile = resVersionService.get(patchFileId);
//				if(resFile!=null){
//					String ids = resFile.getCompressFileIds();
//					if(StringUtils.isBlank(ids)){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//
//					if("1".equals(sysVersionType)){
//						ResVersion resFile_tmp1  = new ResVersion();
//						resFile_tmp1.setBusinessId(sysVersion.getId());
//						resFile_tmp1.setIsDeleted(0);
//						if(systemTypeDigits.intValue()==64){
//							resFile_tmp1.setFileKey("system.sysVersion.patchFile.exe");
//						}
//						if(systemTypeDigits.intValue()==32){
//							resFile_tmp1.setFileKey("system.sysVersion.patchFile.32.exe");
//						}
//						resFile_tmp1.setOrders(" id desc ");
//						List<ResVersion>list_tmp1=resVersionService.getList(resFile_tmp1);
//						if(list_tmp1!=null&&list_tmp1.size()>0){
//							exeFile = list_tmp1.get(0);
//						}
//					}
//					if("2".equals(sysVersionType)){
//						ResVersion resFile_tmp1  = new ResVersion();
//						resFile_tmp1.setBusinessId(sysVersion_internal.getId());
//						resFile_tmp1.setIsDeleted(0);
//						resFile_tmp1.setFileKey("system.sysVersion.internalPatchFile.exe");
//						resFile_tmp1.setOrders(" id desc ");
//						List<ResVersion>list_tmp1=resVersionService.getList(resFile_tmp1);
//						if(list_tmp1!=null&&list_tmp1.size()>0){
//							exeFile = list_tmp1.get(0);
//						}
//					}
//
//
//					if("1".equals(sysVersionType)){
//						ResVersion resFile_tmp2  = new ResVersion();
//						resFile_tmp2.setBusinessId(sysVersion.getId());
//						resFile_tmp2.setIsDeleted(0);
//						if(systemTypeDigits.intValue()==64){
//							resFile_tmp2.setFileKey("system.sysVersion.patchFile.rar");
//						}
//						if(systemTypeDigits.intValue()==32){
//							resFile_tmp2.setFileKey("system.sysVersion.patchFile.32.rar");
//						}
//						resFile_tmp2.setOrders(" id desc ");
//						List<ResVersion>list_tmp2=resVersionService.getList(resFile_tmp2);
//						if(list_tmp2!=null&&list_tmp2.size()>0){
//							rarFile = list_tmp2.get(0);
//						}
//					}
//					if("2".equals(sysVersionType)){
//						ResVersion resFile_tmp2  = new ResVersion();
//						resFile_tmp2.setBusinessId(sysVersion_internal.getId());
//						resFile_tmp2.setIsDeleted(0);
//						resFile_tmp2.setFileKey("system.sysVersion.internalPatchFile.rar");
//						resFile_tmp2.setOrders(" id desc ");
//						List<ResVersion>list_tmp2=resVersionService.getList(resFile_tmp2);
//						if(list_tmp2!=null&&list_tmp2.size()>0){
//							rarFile = list_tmp2.get(0);
//						}
//					}
//
//					if(exeFile==null||rarFile==null){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//
//					String verZipPath = serverUrl+rarFile.getFilePath();
//					String verExePath = serverUrl+exeFile.getFilePath();
//					verZipPath= verZipPath.replace("\\", "/").replace("\\\\", "/");
//					verExePath= verExePath.replace("\\", "/").replace("\\\\", "/");
//					if("1".equals(sysVersionType)){
//						sysVersion.setVerZipPath(verZipPath);
//						sysVersion.setVerExePath(verExePath);/*获取exe 文件*/
//						sysVersion.setAppPath(sysVersionService.getAppPathInfo(rarFile.getFilePath()));
//					}
//					if("2".equals(sysVersionType)){
//						sysVersion_internal.setVerZipPath(verZipPath);
//						sysVersion_internal.setVerExePath(verExePath);/*获取exe 文件*/
//						sysVersion_internal.setAppPath(sysVersionService.getAppPathInfo(rarFile.getFilePath()));
//					}
//				}else{
//					if("1".equals(sysVersionType)){
//						logger.error("resfile is null ! pathfileid = " + sysVersion.getPatchFileId());
//					}
//					if("2".equals(sysVersionType)){
//						logger.error("resfile is null ! pathfileid = " + sysVersion_internal.getInternalPatchFileId());
//					}
//					return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//				}
//				if("1".equals(sysVersionType)){
//					logger.info("=========================AppPath : "+sysVersion.getVerZipPath());
//				}
//				if("2".equals(sysVersionType)){
//					logger.info("=========================AppPath : "+sysVersion_internal.getVerZipPath());
//				}
//			}
//		}
//		
//		
//		
//		if(sysShellVersion != null){
//			Integer patchFileId = null;
//			 if("1".equals(sysVersionType)){
//				 patchFileId = sysShellVersion.getPatchFileId();
//			 }
//			 if("2".equals(sysVersionType)){
//				 patchFileId = sysShellVersion_internal.getInternalPatchFileId();
//			 }
//			 
//			//文件下载地址
//			if(patchFileId!=null&&patchFileId.intValue()>0){
//				ResVersion resFile = null;
//				ResVersion exeFile = null;
//				ResVersion rarFile = null;
//				resFile = resVersionService.get(patchFileId);
//				if(resFile!=null){
//					//att4...
//					String ids = resFile.getCompressFileIds();
//					if(StringUtils.isBlank(ids)){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
// 
//					 
//					if("1".equals(sysVersionType)){
//						ResVersion resFile_tmp1  = new ResVersion();
//						resFile_tmp1.setBusinessId(sysShellVersion.getId());
//						resFile_tmp1.setIsDeleted(0);
//						if(systemTypeDigits.intValue()==64){
//							resFile_tmp1.setFileKey("system.sysVersion.patchFile.exe");
//						}
//						if(systemTypeDigits.intValue()==32){
//							resFile_tmp1.setFileKey("system.sysVersion.patchFile.32.exe");
//						}
//						resFile_tmp1.setOrders(" id desc ");
//						List<ResVersion>list_tmp1=resVersionService.getList(resFile_tmp1);
//						if(list_tmp1!=null&&list_tmp1.size()>0){
//							exeFile = list_tmp1.get(0);
//						}
//					}
//					if("2".equals(sysVersionType)){
//						ResVersion resFile_tmp1  = new ResVersion();
//						resFile_tmp1.setBusinessId(sysShellVersion_internal.getId());
//						resFile_tmp1.setIsDeleted(0);
//						resFile_tmp1.setFileKey("system.sysVersion.internalPatchFile.exe");
//						resFile_tmp1.setOrders(" id desc ");
//						List<ResVersion>list_tmp1=resVersionService.getList(resFile_tmp1);
//						if(list_tmp1!=null&&list_tmp1.size()>0){
//							exeFile = list_tmp1.get(0);
//						}
//					}
//					 
//					 
//					if("1".equals(sysVersionType)){
//						ResVersion resFile_tmp2  = new ResVersion();
//						resFile_tmp2.setBusinessId(sysShellVersion.getId());
//						resFile_tmp2.setIsDeleted(0);
//						if(systemTypeDigits.intValue()==64){
//							resFile_tmp2.setFileKey("system.sysVersion.patchFile.rar");
//						}
//						if(systemTypeDigits.intValue()==32){
//							resFile_tmp2.setFileKey("system.sysVersion.patchFile.32.rar");
//						}
//						resFile_tmp2.setOrders(" id desc ");
//						List<ResVersion>list_tmp2=resVersionService.getList(resFile_tmp2);
//						if(list_tmp2!=null&&list_tmp2.size()>0){
//							rarFile = list_tmp2.get(0);
//						}
//					}
//					if("2".equals(sysVersionType)){
//						ResVersion resFile_tmp2  = new ResVersion();
//						resFile_tmp2.setBusinessId(sysShellVersion_internal.getId());
//						resFile_tmp2.setIsDeleted(0);
//						resFile_tmp2.setFileKey("system.sysVersion.internalPatchFile.rar");
//						resFile_tmp2.setOrders(" id desc ");
//						List<ResVersion>list_tmp2=resVersionService.getList(resFile_tmp2);
//						if(list_tmp2!=null&&list_tmp2.size()>0){
//							rarFile = list_tmp2.get(0);
//						}
//					}
//
//					if(exeFile==null||rarFile==null){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//					
//					String shellZipPath = serverUrl+rarFile.getFilePath();
//					String shellExePath = serverUrl+exeFile.getFilePath();
//					shellZipPath= shellZipPath.replace("\\", "/").replace("\\\\", "/");
//					shellExePath= shellExePath.replace("\\", "/").replace("\\\\", "/");
//					if("1".equals(sysVersionType)){
//						sysVersion.setShellZipPath(shellZipPath);
//						sysVersion.setShellExePath(shellExePath);
//						if( StringUtils.isNotBlank(sysShellVersion.getVersion()) ){
//							sysVersion.setShellVersion(sysShellVersion.getVersion());
//						}
//					}
//					if("2".equals(sysVersionType)){
//						sysVersion_internal.setShellZipPath(shellZipPath);
//						sysVersion_internal.setShellExePath(shellExePath);
//						if( StringUtils.isNotBlank(sysShellVersion_internal.getVersion()) ){
//							sysVersion_internal.setShellVersion(sysShellVersion_internal.getVersion());
//						}
//					} 
//				}
//			}
//		}
//		
//		ResponseEnvelope ResponseEnvelope_ = null;
//		if("1".equals(sysVersionType)){
//			sysVersion.setNewUrl(app.getString("app.server.url"));
//			ResponseEnvelope_ = new  ResponseEnvelope<>(sysVersion, msgId, true);
//		}
//		if("2".equals(sysVersionType)){
//			sysVersion_internal.setNewUrl(app.getString("app.server.url"));
//			ResponseEnvelope_ = new  ResponseEnvelope<>(sysVersion_internal, msgId, true);
//		}
//		return ResponseEnvelope_;
//	
	
    }
 
	
	
	/**
	 * 版本检测接口 (弃用)
	 * 版本资源存储表由 res_file 改为 res_version
	 * @author zhaobl
	 * @param systemType 平台类型
	 * @return
	 */
//	@SuppressWarnings("rawtypes")
//	@RequestMapping("/versionCheck_old_2")
//	@ResponseBody
//	public Object versionCheck_old_2(String msgId,Integer systemType,Integer systemTypeDigits){
// 
//		String serverUrl = Utils.getValue("app.resources.url", "http://res.sanduspace.cn");
//		/*systemType转换*/
//		SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValue("systemType", systemType);
//		if(sysDictionary==null)
//			return new RuntimeException("数据字典中未配置type=systemType,value="+systemType+"的数据字典");
//		if(StringUtils.isBlank(sysDictionary.getAtt1()))
//			return new RuntimeException("数据字典:type=systemType,value="+systemType+"未配置att1属性,请配置(对应媒介类型的value)");
//		systemType=Integer.valueOf(sysDictionary.getAtt1());
//		/*systemType转换->end*/
//		Map<String,String> map=sysVersionService.versionCheckVerifyParams(msgId,systemType);
//		if(StringUtils.equals("false", map.get("success")))
//			return new ResponseEnvelope<>(false, map.get("msg"), msgId);
//		/*得到最新版本 和 壳*/
//		 if(systemTypeDigits==null){
//			 systemTypeDigits = 64;
//		 }
//		 if(systemTypeDigits.intValue()!=32&&systemTypeDigits.intValue()!=64){
//			 return new ResponseEnvelope<>(false, "参数错误：systemTypeDigits!",msgId);
//		 }
//		 /* 1为正式  2  为内部    默认为正式*/
//		 
//		 String  sysVersionType = app.getString("sys.version.type");
//		 if(StringUtils.isBlank(sysVersionType)){
//			 sysVersionType = "1" ;
//		 }
//		 
//		SysVersion sysVersion = sysVersionService.getLatestVersion(systemType,"version");
//		SysVersion sysShellVersion = sysVersionService.getLatestVersion(systemType,"shell");
//		SysVersion sysVersion_internal = sysVersionService.getLatestVersion(systemType,"version_internal");
//		SysVersion sysShellVersion_internal = sysVersionService.getLatestVersion(systemType,"shell_internal");
//		
//		if("1".equals(sysVersionType)){
//			if(sysVersion==null||sysVersion.getId()==null){/*返回初始版本(0.4.0)*/
//				SysVersion initSysVersion=new SysVersion();
//				initSysVersion.setVersion("0.4.0");
//				initSysVersion.setUpgradeMethod(0);
//				return new ResponseEnvelope<>(initSysVersion, msgId, true);
//			}
//			if(sysShellVersion==null||sysShellVersion.getId()==null){/*返回初始版本(0.4.0)*/
//				SysVersion initSysVersion=new SysVersion();
//				initSysVersion.setVersion("0.4.0");
//				initSysVersion.setUpgradeMethod(0);
//				return new ResponseEnvelope<>(initSysVersion, msgId, true);
//			}
//		}
//		if("2".equals(sysVersionType)){
//			if(sysVersion_internal==null||sysVersion_internal.getId()==null){/*返回初始版本(0.4.0)*/
//				SysVersion initSysVersion=new SysVersion();
//				initSysVersion.setVersion("0.4.0");
//				initSysVersion.setUpgradeMethod(0);
//				return new ResponseEnvelope<>(initSysVersion, msgId, true);
//			}
//			 
//			if(sysShellVersion_internal==null||sysShellVersion_internal.getId()==null){/*返回初始版本(0.4.0)*/
//				SysVersion initSysVersion=new SysVersion();
//				initSysVersion.setVersion("0.4.0");
//				initSysVersion.setUpgradeMethod(0);
//				return new ResponseEnvelope<>(initSysVersion, msgId, true);
//			}
//		}
// 
//		List<SysDictionary> dicList = sysDictionaryService.findAllByType("contactInformation");
//		if(Lists.isEmpty(dicList) ){
//			return new RuntimeException("数据字典中未配置type=contactInformation的数据字典");
//		}
//		if(!Lists.isEmpty(dicList) ){
//			if("1".equals(sysVersionType)){
//				paddingData(sysVersion,dicList);
//			}
//			if("2".equals(sysVersionType)){
//				paddingData(sysVersion_internal,dicList);
//			}
//		}
//
//		
//		/*处理返回的更新安装包版本*/
//		if(StringUtils.isNotBlank(sysVersion.getIosFileUrl())){
//			sysVersion.setAppPathInfo(sysVersion.getIosFileUrl());/*ios端地址*/
//		}else{
//			Integer patchFileId = null;
//			 if("1".equals(sysVersionType)){
//				 patchFileId = sysVersion.getPatchFileId();
//			 }
//			 if("2".equals(sysVersionType)){
//				 patchFileId = sysVersion_internal.getInternalPatchFileId();
//			 }
//			/*文件下载地址*/
//			if(patchFileId!=null&&patchFileId.intValue()>0){
//				
//				ResFile resFile = null;
//				ResFile exeFile = null;
//				ResFile rarFile = null;
//				resFile = resFileService.get(patchFileId);
//				if(resFile!=null){
//					String ids = resFile.getAtt4();
//					if(StringUtils.isBlank(ids)){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//  
//					if("1".equals(sysVersionType)){
//						ResFile resFile_tmp1  = new ResFile();
//						resFile_tmp1.setBusinessId(sysVersion.getId());
//						resFile_tmp1.setIsDeleted(0);
//						if(systemTypeDigits.intValue()==64){
//							resFile_tmp1.setFileKey("system.sysVersion.patchFile.exe");
//						}
//						if(systemTypeDigits.intValue()==32){
//							resFile_tmp1.setFileKey("system.sysVersion.patchFile.32.exe");
//						}
//						List<ResFile>list_tmp1=resFileService.getList(resFile_tmp1);
//						if(list_tmp1!=null&&list_tmp1.size()==1){
//							exeFile = list_tmp1.get(0);
//						}
//					}
//					if("2".equals(sysVersionType)){
//						ResFile resFile_tmp1  = new ResFile();
//						resFile_tmp1.setBusinessId(sysVersion_internal.getId());
//						resFile_tmp1.setIsDeleted(0);
//						resFile_tmp1.setFileKey("system.sysVersion.internalPatchFile.exe");
//						List<ResFile>list_tmp1=resFileService.getList(resFile_tmp1);
//						if(list_tmp1!=null&&list_tmp1.size()==1){
//							exeFile = list_tmp1.get(0);
//						}
//					}
//					 
//					 
//					if("1".equals(sysVersionType)){
//						ResFile resFile_tmp2  = new ResFile();
//						resFile_tmp2.setBusinessId(sysVersion.getId());
//						resFile_tmp2.setIsDeleted(0);
//						if(systemTypeDigits.intValue()==64){
//							resFile_tmp2.setFileKey("system.sysVersion.patchFile.rar");
//						}
//						if(systemTypeDigits.intValue()==32){
//							resFile_tmp2.setFileKey("system.sysVersion.patchFile.32.rar");
//						}
//						List<ResFile>list_tmp2=resFileService.getList(resFile_tmp2);
//						if(list_tmp2!=null&&list_tmp2.size()==1){
//							rarFile = list_tmp2.get(0);
//						}
//					}
//					if("2".equals(sysVersionType)){
//						ResFile resFile_tmp2  = new ResFile();
//						resFile_tmp2.setBusinessId(sysVersion_internal.getId());
//						resFile_tmp2.setIsDeleted(0);
//						resFile_tmp2.setFileKey("system.sysVersion.internalPatchFile.rar");
//						List<ResFile>list_tmp2=resFileService.getList(resFile_tmp2);
//						if(list_tmp2!=null&&list_tmp2.size()==1){
//							rarFile = list_tmp2.get(0);
//						}
//					}
//					 
//					if(exeFile==null||rarFile==null){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//					
//					String verZipPath = serverUrl+rarFile.getFilePath();
//					String verExePath = serverUrl+exeFile.getFilePath();
//					verZipPath= verZipPath.replace("\\", "/").replace("\\\\", "/");
//					verExePath= verExePath.replace("\\", "/").replace("\\\\", "/");
//					if("1".equals(sysVersionType)){
//						sysVersion.setVerZipPath(verZipPath);
//						sysVersion.setVerExePath(verExePath);/*获取exe 文件*/ 
//						sysVersion.setAppPath(sysVersionService.getAppPathInfo(rarFile.getFilePath()));
//					}
//					if("2".equals(sysVersionType)){
//						sysVersion_internal.setVerZipPath(verZipPath);
//						sysVersion_internal.setVerExePath(verExePath);/*获取exe 文件*/ 
//						sysVersion_internal.setAppPath(sysVersionService.getAppPathInfo(rarFile.getFilePath()));
//					}
//				}else{
//					logger.error("resfile is null ! pathfileid = " + sysVersion.getPatchFileId());
//					return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//				}
//				logger.info("=========================AppPath : "+sysVersion.getVerZipPath());
//			}
//		}
//		
//		
//		
//		if(sysShellVersion != null){
//			Integer patchFileId = null;
//			 if("1".equals(sysVersionType)){
//				 patchFileId = sysShellVersion.getPatchFileId();
//			 }
//			 if("2".equals(sysVersionType)){
//				 patchFileId = sysShellVersion_internal.getInternalPatchFileId();
//			 }
//			 
//			//文件下载地址
//			if(patchFileId!=null&&patchFileId.intValue()>0){
//				ResFile resFile = null;
//				ResFile exeFile = null;
//				ResFile rarFile = null;
//				resFile = resFileService.get(patchFileId);
//				if(resFile!=null){
//					String ids = resFile.getAtt4();
//					if(StringUtils.isBlank(ids)){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
// 
//					 
//					if("1".equals(sysVersionType)){
//						ResFile resFile_tmp1  = new ResFile();
//						resFile_tmp1.setBusinessId(sysShellVersion.getId());
//						resFile_tmp1.setIsDeleted(0);
//						if(systemTypeDigits.intValue()==64){
//							resFile_tmp1.setFileKey("system.sysVersion.patchFile.exe");
//						}
//						if(systemTypeDigits.intValue()==32){
//							resFile_tmp1.setFileKey("system.sysVersion.patchFile.32.exe");
//						}
//						List<ResFile>list_tmp1=resFileService.getList(resFile_tmp1);
//						if(list_tmp1!=null&&list_tmp1.size()==1){
//							exeFile = list_tmp1.get(0);
//						}
//					}
//					if("2".equals(sysVersionType)){
//						ResFile resFile_tmp1  = new ResFile();
//						resFile_tmp1.setBusinessId(sysShellVersion_internal.getId());
//						resFile_tmp1.setIsDeleted(0);
//						resFile_tmp1.setFileKey("system.sysVersion.internalPatchFile.exe");
//						List<ResFile>list_tmp1=resFileService.getList(resFile_tmp1);
//						if(list_tmp1!=null&&list_tmp1.size()==1){
//							exeFile = list_tmp1.get(0);
//						}
//					}
//					 
//					 
//					if("1".equals(sysVersionType)){
//						ResFile resFile_tmp2  = new ResFile();
//						resFile_tmp2.setBusinessId(sysShellVersion.getId());
//						resFile_tmp2.setIsDeleted(0);
//						if(systemTypeDigits.intValue()==64){
//							resFile_tmp2.setFileKey("system.sysVersion.patchFile.rar");
//						}
//						if(systemTypeDigits.intValue()==32){
//							resFile_tmp2.setFileKey("system.sysVersion.patchFile.32.rar");
//						}
//						List<ResFile>list_tmp2=resFileService.getList(resFile_tmp2);
//						if(list_tmp2!=null&&list_tmp2.size()==1){
//							rarFile = list_tmp2.get(0);
//						}
//					}
//					if("2".equals(sysVersionType)){
//						ResFile resFile_tmp2  = new ResFile();
//						resFile_tmp2.setBusinessId(sysShellVersion_internal.getId());
//						resFile_tmp2.setIsDeleted(0);
//						resFile_tmp2.setFileKey("system.sysVersion.internalPatchFile.rar");
//						List<ResFile>list_tmp2=resFileService.getList(resFile_tmp2);
//						if(list_tmp2!=null&&list_tmp2.size()==1){
//							rarFile = list_tmp2.get(0);
//						}
//					}
//				
//					if(exeFile==null||rarFile==null){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//				
//					String shellZipPath = serverUrl+rarFile.getFilePath();
//					String shellExePath = serverUrl+exeFile.getFilePath();
//					shellZipPath= shellZipPath.replace("\\", "/").replace("\\\\", "/");
//					shellExePath= shellExePath.replace("\\", "/").replace("\\\\", "/");
//					if("1".equals(sysVersionType)){
//						sysVersion.setShellZipPath(shellZipPath);
//						sysVersion.setShellExePath(shellExePath);
//						if( StringUtils.isNotBlank(sysShellVersion.getVersion()) ){
//							sysVersion.setShellVersion(sysShellVersion.getVersion());
//						}
//					}
//					if("2".equals(sysVersionType)){
//						sysVersion_internal.setShellZipPath(shellZipPath);
//						sysVersion_internal.setShellExePath(shellExePath);
//						if( StringUtils.isNotBlank(sysShellVersion_internal.getVersion()) ){
//							sysVersion_internal.setShellVersion(sysShellVersion_internal.getVersion());
//						}
//					} 
//				}
//			}
//		}
//		
//		ResponseEnvelope ResponseEnvelope_ = null;
//		if("1".equals(sysVersionType)){
//			ResponseEnvelope_ = new  ResponseEnvelope<>(sysVersion, msgId, true);
//		}
//		if("2".equals(sysVersionType)){
//			ResponseEnvelope_ = new  ResponseEnvelope<>(sysVersion_internal, msgId, true);
//		}
//		return ResponseEnvelope_;
//	}
// 
	
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
=======
	 * @author zhaobl
	 * @param systemType 平台类型
	 * @return
	 */
//	@RequestMapping("/versionCheck")
//	@ResponseBody
//	public Object versionCheck(String msgId,Integer systemType){
//		String serverUrl = Utils.getValue("app.resources.url", "http://res.sanduspace.cn");
//		/*systemType转换*/
//		SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValue("systemType", systemType);
//		if(sysDictionary==null)
//			return new RuntimeException("数据字典中未配置type=systemType,value="+systemType+"的数据字典");
//		if(StringUtils.isBlank(sysDictionary.getAtt1()))
//			return new RuntimeException("数据字典:type=systemType,value="+systemType+"未配置att1属性,请配置(对应媒介类型的value)");
//		systemType=Integer.valueOf(sysDictionary.getAtt1());
//		/*systemType转换->end*/
//		Map<String,String> map=sysVersionService.versionCheckVerifyParams(msgId,systemType);
//		if(StringUtils.equals("false", map.get("success")))
//			return new ResponseEnvelope<>(false, map.get("msg"), msgId);
//		/*得到最新版本 和 壳*/
//		SysVersion sysVersion = sysVersionService.getLatestVersion(systemType,"version");
//		SysVersion sysShellVersion = sysVersionService.getLatestVersion(systemType,"shell");
//		if(sysVersion==null||sysVersion.getId()==null){
//			/*返回初始版本(0.4.0)*/
//			SysVersion initSysVersion=new SysVersion();
//			initSysVersion.setVersion("0.4.0");
//			initSysVersion.setUpgradeMethod(0);
//			return new ResponseEnvelope<>(initSysVersion, msgId, true);
//		}
//		List<SysDictionary> dicList = sysDictionaryService.findAllByType("contactInformation");
//		if( Lists.isEmpty(dicList) ){
//			return new RuntimeException("数据字典中未配置type=contactInformation的数据字典");
//		}else{
//			for (SysDictionary dictionary : dicList) {
//				if( "telephone".equals(dictionary.getValuekey()) ){
//					sysVersion.setTelephone(dictionary==null?"0755—23895307":dictionary.getAtt1());
//					sysVersion.setTelephoneName(dictionary==null?"客服电话":dictionary.getName());
//					continue;
//				}
//				if( "officialWebsitePath".equals(dictionary.getValuekey()) ){
//					sysVersion.setOfficialWebsitePath(dictionary==null?"http://www.sanduspace.cn/":dictionary.getAtt1());
//					sysVersion.setOfficialWebsiteName(dictionary==null?"访问官网":dictionary.getName());
//					continue;
//				}
//				if( "shellCommonProblemPath".equals(dictionary.getValuekey()) ){
//					sysVersion.setShellCommonProblemPath(dictionary==null?"http://www.sanduspace.cn/static/faq/quest1.htm":dictionary.getAtt1());
//					sysVersion.setShellCommonProblemName(dictionary==null?"常见问题":dictionary.getName());
//					continue;
//				}
//				if( "appCommonProblemPath".equals(dictionary.getValuekey()) ){
//					sysVersion.setAppCommonProblemPath(dictionary==null?"http://www.sanduspace.cn/static/faq/quest2.htm":dictionary.getAtt1());
//					sysVersion.setAppCommonProblemName(dictionary==null?"常见问题":dictionary.getName());
//					continue;
//				}
//			}
//		}
//		
//		 /* 1为正式  2  为内部    默认为正式*/
//		 Integer patchFileId = null;
// 
//		 String sysVersionType = null;
//		 sysVersionType = app.getString("sys.version.type");
//		 if(StringUtils.isBlank(sysVersionType)){
//			 sysVersionType = "1" ;
//		 }
//		  
//		/*处理返回的更新安装包版本*/
//		if(StringUtils.isNotBlank(sysVersion.getIosFileUrl())){
//			/*ios端地址*/
//			sysVersion.setAppPathInfo(sysVersion.getIosFileUrl());
//		}else{
//			 if("1".equals(sysVersionType)){
//				 patchFileId = sysVersion.getPatchFileId();
//			 }
//			 if("2".equals(sysVersionType)){
//				 patchFileId = sysVersion.getInternalPatchFileId();
//			 }
//			/*文件下载地址*/
//			if(patchFileId!=null&&patchFileId.intValue()>0){
//				ResFile resFile = null;
//				ResFile exeFile = null;
//				ResFile rarFile = null;
//				resFile = resFileService.get(patchFileId);
//				if(resFile!=null){
//					String ids = resFile.getAtt4();
//					if(StringUtils.isBlank(ids)){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//					
//					/*获取 exe文件  和 rar压缩更新文件*/
//					/*JSONObject  jasonObject =  JSONObject.fromObject(ids);*/
//					
//					/*获取rar压缩更新文件*/
//					/*Object exeIdObj = jasonObject.get("exe");
//					Object rarIdObj = jasonObject.get("rar");
//					String exeId = exeIdObj.toString();
//					String rarId = rarIdObj.toString();*/
// 
//					/*if(StringUtils.isBlank(exeId)||StringUtils.isBlank(rarId)){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//					exeFile=resFileService.get(Integer.parseInt(exeId));
//					rarFile=resFileService.get(Integer.parseInt(rarId));*/
//					
//					ResFile resFile_tmp1  = new ResFile();
//					if("1".equals(sysVersionType)){
//						resFile_tmp1.setFileKey("system.sysVersion.patchFile.exe");
//					}
//					if("2".equals(sysVersionType)){
//						resFile_tmp1.setFileKey("system.sysVersion.internalPatchFile.exe");
//					}
//					resFile_tmp1.setBusinessId(sysVersion.getId());
//					resFile_tmp1.setIsDeleted(0);
//					List<ResFile>list_tmp1=resFileService.getList(resFile_tmp1);
//					if(list_tmp1!=null&&list_tmp1.size()==1){
//						exeFile = list_tmp1.get(0);
//					}
//					
//					ResFile resFile_tmp2  = new ResFile();
//					if("1".equals(sysVersionType)){
//						resFile_tmp2.setFileKey("system.sysVersion.patchFile.rar");
//					}
//					if("2".equals(sysVersionType)){
//						resFile_tmp2.setFileKey("system.sysVersion.internalPatchFile.rar");
//					}
//					resFile_tmp2.setBusinessId(sysVersion.getId());
//					resFile_tmp2.setIsDeleted(0);
//					List<ResFile>list_tmp2=resFileService.getList(resFile_tmp2);
//					if(list_tmp2!=null&&list_tmp2.size()==1){
//						rarFile = list_tmp2.get(0);
//					}
//					
//					if(exeFile==null||rarFile==null){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//					
//					String verZipPath = serverUrl+rarFile.getFilePath();
//					String verExePath = serverUrl+exeFile.getFilePath();
//					verZipPath= verZipPath.replace("\\", "/").replace("\\\\", "/");
//					verExePath= verExePath.replace("\\", "/").replace("\\\\", "/");
//					sysVersion.setVerZipPath(verZipPath);
//					sysVersion.setVerExePath(verExePath);/*获取exe 文件*/ 
//					sysVersion.setAppPath(sysVersionService.getAppPathInfo(rarFile.getFilePath()));
//				}else{
//					logger.error("resfile is null ! pathfileid = " + sysVersion.getPatchFileId());
//					return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//				}
//				logger.info("=========================AppPath : "+sysVersion.getVerZipPath());
//			}
//		}
//		if(sysShellVersion != null){
//			 if("1".equals(sysVersionType)){
//				 patchFileId = sysShellVersion.getPatchFileId();
//			 }
//			 if("2".equals(sysVersionType)){
//				 patchFileId = sysShellVersion.getInternalPatchFileId();
//			 }
//			 
//			//文件下载地址
//			if(patchFileId!=null&&patchFileId.intValue()>0){
//				ResFile resFile = null;
//				ResFile exeFile = null;
//				ResFile rarFile = null;
//				resFile = resFileService.get(patchFileId);
//				if(resFile!=null){
//					String ids = resFile.getAtt4();
//					if(StringUtils.isBlank(ids)){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//					
//					/*获取 exe文件  和 rar压缩更新文件*/
//					/*JSONObject  jasonObject =  JSONObject.fromObject(ids);*/
//					
//					/*获取rar压缩更新文件*/
//					/*Object exeIdObj = jasonObject.get("exe");
//					Object rarIdObj = jasonObject.get("rar");
//					String exeId = exeIdObj.toString();
//					String rarId = rarIdObj.toString();
//					
//					if(StringUtils.isBlank(exeId)||StringUtils.isBlank(rarId)){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}
//					exeFile=resFileService.get(Integer.parseInt(exeId));
//					rarFile=resFileService.get(Integer.parseInt(rarId));
//					if(exeFile==null||rarFile==null){
//						logger.error("resfile is null ! pathfileid = " + patchFileId);
//						return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
//					}*/
//					
//					ResFile resFile_tmp1  = new ResFile();
//					if("1".equals(sysVersionType)){
//						resFile_tmp1.setFileKey("system.sysVersion.patchFile.exe");
//					}
//					if("2".equals(sysVersionType)){
//						resFile_tmp1.setFileKey("system.sysVersion.internalPatchFile.exe");
//					}
//					resFile_tmp1.setBusinessId(sysVersion.getId());
//					resFile_tmp1.setIsDeleted(0);
//					List<ResFile>list_tmp1=resFileService.getList(resFile);
//					if(list_tmp1!=null&&list_tmp1.size()==1){
//						exeFile = list_tmp1.get(0);
//					}
//					
//					ResFile resFile_tmp2  = new ResFile();
//					if("1".equals(sysVersionType)){
//						resFile_tmp2.setFileKey("system.sysVersion.patchFile.rar");
//					}
//					if("2".equals(sysVersionType)){
//						resFile_tmp2.setFileKey("system.sysVersion.internalPatchFile.rar");
//					}
//					resFile_tmp2.setBusinessId(sysVersion.getId());
//					resFile_tmp2.setIsDeleted(0);
//					List<ResFile>list_tmp2=resFileService.getList(resFile);
//					if(list_tmp2!=null&&list_tmp2.size()==1){
//						rarFile = list_tmp2.get(0);
//					}
//					
//					String shellZipPath = serverUrl+rarFile.getFilePath();
//					String shellExePath = serverUrl+exeFile.getFilePath();
//					shellZipPath= shellZipPath.replace("\\", "/").replace("\\\\", "/");
//					shellExePath= shellExePath.replace("\\", "/").replace("\\\\", "/");
//					sysVersion.setShellZipPath(shellZipPath);
//					sysVersion.setShellExePath(shellExePath);
//					if( StringUtils.isNotBlank(sysShellVersion.getVersion()) ){
//						sysVersion.setShellVersion(sysShellVersion.getVersion());
//					}
//				}
//			}
//		}
//		return new ResponseEnvelope<>(sysVersion, msgId, true);
//	}
	
	
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
