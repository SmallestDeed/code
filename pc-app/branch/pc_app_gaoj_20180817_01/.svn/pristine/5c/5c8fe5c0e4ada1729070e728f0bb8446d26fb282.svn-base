package com.nork.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.system.controller.web.WebSysVersionController;
import com.nork.system.dao.SysVersionMapper;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResVersion;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysVersion;
import com.nork.system.model.search.SysVersionSearch;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResVersionService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysVersionService;

/**   
 * @Title: SysVersionServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-版本管理ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-05-05 14:18:01
 * @version V1.0   
 */
@Service("sysVersionService")
public class SysVersionServiceImpl implements SysVersionService {

	private SysVersionMapper sysVersionMapper;

	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
	Logger logger = Logger.getLogger(SysVersionServiceImpl.class);
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
//	@Autowired
//	private ResFileService resFileService;
	
	@Autowired
	private ResVersionService resVersionService;
	
	@Autowired
	public void setSysVersionMapper(
			SysVersionMapper sysVersionMapper) {
		this.sysVersionMapper = sysVersionMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysVersion
	 * @return  int 
	 */
	@Override
	public int add(SysVersion sysVersion) {
		sysVersionMapper.insertSelective(sysVersion);
		return sysVersion.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysVersion
	 * @return  int 
	 */
	@Override
	public int update(SysVersion sysVersion) {
		return sysVersionMapper
				.updateByPrimaryKeySelective(sysVersion);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		/*删除关联文件*/
		SysVersion sysVersion=get(id);
		if(sysVersion.getFileId()!=null&&sysVersion.getFileId()>0){
//			resFileService.delete(id);
		}
		return sysVersionMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysVersion 
	 */
	@Override
	public SysVersion get(Integer id) {
		return sysVersionMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysVersion
	 * @return   List<SysVersion>
	 */
	@Override
	public List<SysVersion> getList(SysVersion sysVersion) {
	    return sysVersionMapper.selectList(sysVersion);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysVersion
	 * @return   int
	 */
	@Override
	public int getCount(SysVersionSearch sysVersionSearch){
		return  sysVersionMapper.selectCount(sysVersionSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysVersion
	 * @return   List<SysVersion>
	 */
	@Override
	public List<SysVersion> getPaginatedList(
			SysVersionSearch sysVersionSearch) {
		return sysVersionMapper.selectPaginatedList(sysVersionSearch);
	}

	/**
	 * 查找所有版本号名称(用于下拉菜单)
	 * @author huangsongbo
	 * @return
	 */
	public List<String> findAllVersionName() {
		return sysVersionMapper.findAllVersionName();
	}

	/**
	 * 得到最新版本信息
	 * @author huangsongbo
	 * @param systemType 平台类型
	 * @return
	 */
	public SysVersion getLatestVersion(Integer systemType,String verType) {
		SysVersionSearch sysVersionSearch=new SysVersionSearch();
		sysVersionSearch.setLimit(1);
		sysVersionSearch.setStart(0);
		sysVersionSearch.setSystemType(""+systemType);
		sysVersionSearch.setVerType(verType);
		sysVersionSearch.setOrder(" release_date");
		sysVersionSearch.setOrderNum("desc");
		sysVersionSearch.setIsDeleted(0);
		/*设置status为正式发布*/
		SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValueKey("versionStatus","versionStatus_official");
 		if(sysDictionary==null||sysDictionary.getId()==null){
			throw new RuntimeException("数据字典中未添加版本状态:正式发布,type=versionStatus,valueKey=versionStatus_official");
		}
		sysVersionSearch.setStatus(sysDictionary.getValue());
		List<SysVersion> sysVersions=getPaginatedList(sysVersionSearch);
		if(sysVersions!=null&&sysVersions.size()>0)
			return sysVersions.get(0);
		return null;
	}

	/**
	 * versionCheck接口验证参数
	 * @author huangsongbo
	 * @param msgId
	 * @param systemType
	 * @return
	 */
	public Map<String, String> versionCheckVerifyParams(String msgId, Integer systemType) {
		String success="false";
		String msg="";
		Map<String,String> map=new HashMap<String,String>();
		if(StringUtils.isBlank(msgId)){
			msg="参数msgId不能为空";
		}else if(systemType==null){
			msg="参数systemType不能为空";
		}else{
			success="true";
		}
		map.put("success", success);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 验证版本名唯一性
	 * @author huangsongbo
	 * @param version
	 * @param id
	 * @return
	 */
	public boolean verifyVersionName(String version, Integer systemType, Integer id) {
		SysVersionSearch sysVersionSearch=new SysVersionSearch();
		sysVersionSearch.setLimit(2);
		sysVersionSearch.setStart(0);
		sysVersionSearch.setVersion(version);
		sysVersionSearch.setSystemType(""+systemType);
		List<SysVersion> sysVersions=getPaginatedList(sysVersionSearch);
		if(sysVersions==null||sysVersions.size()==0){
			return true;
		}else if(sysVersions.size()==1){
			if(id==null||id<1){
				/*新增*/
				return false;
			}else{
				/*编辑*/
				if(id.equals(sysVersions.get(0).getId()))
					return true;
				return false;
			}
		}else{
			return false;
		}
	}

	/**
	 * 回填附件信息(businessId)
	 * @author huangsongbo
	 * @param sysVersion
	 */
//	public void setFileAttribute(SysVersion sysVersion) {
//		if(sysVersion.getFileId()!=null&&sysVersion.getFileId()>0){
//			ResFile resFile=resFileService.get(sysVersion.getFileId());
//			if(resFile!=null){
//				resFile.setBusinessId(sysVersion.getId());
//				resFileService.update(resFile);
//			}
//		}
//	}

	/**
	 * 得到版本下载地址
	 * @author huangsongbo
	 * @param path 
	 * @return
	 */
	public String getAppPathInfo(String path) {
		/*SysDictionary sysDictionaryDomainName=sysDictionaryService.findOneByTypeAndValueKey("domainName", "domainName_domainName");
		SysDictionary sysDictionaryProjectName=sysDictionaryService.findOneByTypeAndValueKey("domainName", "domainName_projectName");
		if(sysDictionaryDomainName==null||sysDictionaryDomainName.getId()==null)
			throw new RuntimeException("数据字典中未添加域名:域名,type=domainName,valueKey=domainName_domainName");
		if(sysDictionaryProjectName==null||sysDictionaryProjectName.getId()==null)
			throw new RuntimeException("数据字典中未添加域名:域名,type=domainName,valueKey=domainName_projectName");
		String returnPath="http://"+sysDictionaryDomainName.getName()+"/"+sysDictionaryProjectName.getName()+"/jsp/download.htm?downloadName="+path;*/
		String returnPath = Utils.getPropertyName("app","app.server.url","") + Utils.getPropertyName("app","app.server.siteName","app/") + "jsp/download.htm?downloadName=" + path;
		return returnPath;
	}

    @Override
    public Object getLatestVersionInfo(String msgId, Integer systemType, Integer systemTypeDigits,
        Integer isNeedShell) {
        String serverUrl = Utils.getValue("app.resources.url", "http://show.dev.sanduspace.com");
        /** 参数校验 begin**/
        Map<String,String> map= this.versionCheckVerifyParams(msgId,systemType);
        if(StringUtils.equals("false", map.get("success"))) {
          return new ResponseEnvelope<SysVersion>(false, map.get("msg"), msgId);
        }
        if(StringUtils.equals("false", map.get("success"))) {
            return new ResponseEnvelope<SysVersion>(false, map.get("msg"), msgId);
        }
        
        SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValue("systemType", systemType);
        if(sysDictionary==null) {
            return new ResponseEnvelope<SysVersion>(false,"数据字典中未配置type=systemType,value="+systemType+"的数据字典");
        }
        if(StringUtils.isBlank(sysDictionary.getAtt1())) {
            return new ResponseEnvelope<SysVersion>(false,"数据字典:type=systemType,value="+systemType+"未配置att1属性,请配置(对应媒介类型的value)");
        }
        needShell shellflag = null;
        if(isNeedShell == null) {
          shellflag = needShell.isNeed;
        }else if(isNeedShell == 1) {
          shellflag = needShell.isNeed;
        }else if (isNeedShell == 0) {
          shellflag = needShell.IsNotNeed;
        }else {
          return new ResponseEnvelope<SysVersion>(false,"参数错误,isNeedShell="+isNeedShell);
        }
        /** 参数校验 end**/
        
        systemType=Integer.valueOf(sysDictionary.getAtt1());
        
        /* 环境类型 1为正式  2  为内部    默认为正式*/
        String  sysVersionType = app.getString("sys.version.type");
        if(StringUtils.isBlank(sysVersionType)){
          sysVersionType = "1" ;
        }
        versionType type = null; 
        
        if("2".equals(sysVersionType)){
              type = versionType.internal;
        }
        if("1".equals(sysVersionType)){
              type = versionType.external;
        }
        if(systemTypeDigits==null){
            systemTypeDigits = 64;
        }
        if(systemTypeDigits.intValue()!=32&&systemTypeDigits.intValue()!=64 && systemTypeDigits.intValue() != -1 ){
            return new ResponseEnvelope<SysVersion>(false, "参数错误：systemTypeDigits!",msgId);
        }
        SysVersion sysVersion = null;
        SysVersion sysShellVersion = null;
        sysVersion = this.getLatestVersion(systemType,type.getValue());
        if(sysVersion==null||sysVersion.getId()==null){/*返回初始版本(0.4.0)*/
          SysVersion initSysVersion=new SysVersion();
          initSysVersion.setVersion("0.4.0");
          initSysVersion.setUpgradeMethod(0);
          return new ResponseEnvelope<>(initSysVersion, msgId, true);
        }
        //壳版本
        if(shellflag == needShell.isNeed) {
          if(type == versionType.external) {
            sysShellVersion = this.getLatestVersion(systemType, "shell");
          }else if(type == versionType.internal) {
            sysShellVersion = this.getLatestVersion(systemType, "shell_internal");
          }
          if(sysShellVersion == null || sysShellVersion.getId() == null) {
            if(sysShellVersion==null||sysShellVersion.getId()==null){/*返回初始版本(0.4.0)*/
              SysVersion initSysVersion=new SysVersion();
              initSysVersion.setVersion("0.4.0");
              initSysVersion.setUpgradeMethod(0);
              return new ResponseEnvelope<>(initSysVersion, msgId, true);
            }
          }
        }
        /** 填充壳界面信息 begin **/
        List<SysDictionary> dicList = sysDictionaryService.findAllByType("contactInformation");
        if(Lists.isEmpty(dicList) ){
            return new RuntimeException("数据字典中未配置type=contactInformation的数据字典");
        }
        if(!Lists.isEmpty(dicList) ){
                this.paddingData(sysVersion,dicList);
        }
        /** 填充壳界面信息 end**/
        
        if((type == versionType.external) && StringUtils.isNotBlank(sysVersion.getIosFileUrl())){
          sysVersion.setAppPathInfo(sysVersion.getIosFileUrl());/*ios端地址*/
      }else{
           Integer patchFileId =  null;
           if(type == versionType.external) {
             patchFileId = sysVersion.getPatchFileId();
           }else if(type == versionType.internal) {
             patchFileId = sysVersion.getInternalPatchFileId();
           }
           ResVersion resFile = null;
           ResVersion exeFile = null;
           ResVersion rarFile = null;
           if(patchFileId == null || patchFileId.intValue() < 1) {
             logger.error("patchFileId is null!");
             return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
           }
           resFile = resVersionService.get(patchFileId);
           if(resFile == null || StringUtils.isBlank(resFile.getCompressFileIds())) {
             logger.error("compressFileIds is null,patchFileId = " + patchFileId);
             return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
           }
           ResVersion exeFile_search = new ResVersion();
           exeFile_search.setBusinessId(sysVersion.getId());
           exeFile_search.setIsDeleted(0);
           exeFile_search.setOrders("  id desc ");
           if(type == versionType.external) {
             if(systemTypeDigits.equals(64)) {
               exeFile_search.setFileKey("system.sysVersion.patchFile.exe");
             }else if(systemTypeDigits.equals(32)) {
               exeFile_search.setFileKey("system.sysVersion.patchFile.32.exe");
             }
           }else if(type == versionType.internal) {
             exeFile_search.setFileKey("system.sysVersion.internalPatchFile.exe");
           }
           List<ResVersion> exeFileList = resVersionService.getList(exeFile_search);
           if(exeFileList != null && exeFileList.size() > 0) {
             exeFile = exeFileList.get(0);
           }
           
          ResVersion rarFile_search = new ResVersion(); 
          rarFile_search.setBusinessId(sysVersion.getId());
          rarFile_search.setIsDeleted(0);
          rarFile_search.setOrders(" id desc");
          if(type.equals(versionType.external)) {
            if(systemTypeDigits.equals(64)) {
              rarFile_search.setFileKey("system.sysVersion.patchFile.rar");
            }else if(systemTypeDigits.equals(32)) {
              rarFile_search.setFileKey("system.sysVersion.patchFile.32.rar");
            }
          }else if(type.equals(versionType.internal)) {
            rarFile_search.setFileKey("system.sysVersion.internalPatchFile.rar");
          }
          List<ResVersion> rarFileList = resVersionService.getList(rarFile_search);
          if(rarFileList != null && rarFileList.size() > 0) {
            rarFile = rarFileList.get(0);
          }
          if(exeFile == null || rarFile == null) {
            logger.error("versionFile is null!");
            return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
          }
          
          String zipPath = null;
          String exePath = null;
          zipPath = (serverUrl + rarFile.getFilePath()).replace("\\", "/").replaceAll("\\\\", "/");
          exePath = serverUrl + exeFile.getFilePath();
          
          sysVersion.setVerZipPath(zipPath);
          sysVersion.setVerExePath(exePath);
          sysVersion.setAppPath(this.getAppPathInfo(rarFile.getFilePath())); //不明白有啥用
    
          //补充壳的地址
          if(sysShellVersion != null) {
            Integer shellPatchFileId = null;
            if(sysVersionType.equals(versionType.external.getKey()+"")) {
              shellPatchFileId = sysShellVersion.getPatchFileId();
            }else if(sysVersionType.equals(versionType.internal.getKey()+"")) {
              shellPatchFileId = sysShellVersion.getInternalPatchFileId();
            }
            ResVersion shellPatchFile = resVersionService.get(shellPatchFileId);
            if(shellPatchFile == null || StringUtils.isBlank(shellPatchFile.getCompressFileIds())) {
              logger.error("shellPatchFile is null");
              return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行!",msgId);
            }
            ResVersion shellExeFile = new ResVersion();
            ResVersion shellRarFile = new ResVersion();
            
            ResVersion shellExeFile_search = new ResVersion();
            shellExeFile_search.setBusinessId(sysShellVersion.getId());
            shellExeFile_search.setIsDeleted(0);
            shellExeFile_search.setOrders("  id desc ");
            ResVersion shellRarFile_search = new ResVersion();
            shellRarFile_search.setBusinessId(sysShellVersion.getId());
            shellRarFile_search.setIsDeleted(0);
            shellRarFile_search.setOrders(" id desc ");
            
            if(type.equals(versionType.external)) {
              if("64".equals(systemTypeDigits+"")) {
                shellExeFile_search.setFileKey("system.sysVersion.patchFile.exe");
                shellRarFile_search.setFileKey("system.sysVersion.patchFile.rar");
              }else if("32".equals(systemTypeDigits+"")) {
                shellExeFile_search.setFileKey("system.sysVersion.patchFile.32.exe");
                shellRarFile_search.setFileKey("system.sysVersion.patchFile.32.rar");
              }
              
            }else if(type.equals(versionType.internal)) {
              shellExeFile_search.setFileKey("system.sysVersion.internalPatchFile.exe");
              shellRarFile_search.setFileKey("system.sysVersion.internalPatchFile.rar");
            }
            List<ResVersion> shellExeFileList = resVersionService.getList(shellExeFile_search);
            if(shellExeFileList != null && shellExeFileList.size() > 0) {
              shellExeFile = shellExeFileList.get(0);
            }
            
            List<ResVersion> shellRarFileList = resVersionService.getList(shellRarFile_search);
            if(shellRarFileList != null && shellRarFileList.size() > 0) {
              shellRarFile = shellRarFileList.get(0);
            }
          
            if(shellExeFile == null || shellRarFile == null) {
              logger.error("shellfile is null ! pathfileid = " + patchFileId);
              return new ResponseEnvelope<>(false, "下载新版本失败，将使用本地版本运行！", msgId);
            }
            
            String shellZipPath = serverUrl+shellRarFile.getFilePath();
            String shellExePath = serverUrl+shellExeFile.getFilePath();
            shellZipPath= shellZipPath.replace("\\", "/").replace("\\\\", "/");
            shellExePath= shellExePath.replace("\\", "/").replace("\\\\", "/");
            sysVersion.setShellZipPath(shellZipPath);
            sysVersion.setShellExePath(shellExePath);
            if( StringUtils.isNotBlank(sysShellVersion.getVersion()) ){
                sysVersion.setShellVersion(sysShellVersion.getVersion());
            }
          }
          
          
          
      }
      ResponseEnvelope ResponseEnvelope_ = null;
      sysVersion.setNewUrl(app.getString("app.server.url"));
      ResponseEnvelope_ = new  ResponseEnvelope<>(sysVersion, msgId, true);
       return ResponseEnvelope_;
    }
	
    
    /**
     * 系统类型
     * @author 
     * external 外部 1
     * internal 内部 2
     */
    public enum versionType {

      external(1,"version"),internal(2 , "version_internal" );

      private int key;
      private String value;

      private versionType( int key,String value) {
      this.value = value;
      this.key = key;
      }

      public String getValue() {
      return value;
      }

      public void setValue(String value) {
      this.value = value;
      }

      public int getKey() {
      return key;
      }

      public void setKey(int key) {
        this.key = key;
      }
    }
    
    /**
     * 是否需要壳
     * 1(需要)，0 （不需要），为空则默认为需要
     * @author chenm
     * 2018.05.28
     */
    public enum needShell 
    {
         
        isNeed(1), IsNotNeed(0);
        int value;
  
        public int getValue() {
            return value;
        }
    
        needShell(int value) {  
            this.value = value;  
        }  
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
     * @param msgId
     * @param systemType
     * @return
     */
    @Override
    public Object getVersionFileInfo(String msgId, Integer systemType) {
      /*systemType转换*/
          SysDictionary sysDictionary=sysDictionaryService.findOneByTypeAndValue("systemType", systemType,false);
          if(sysDictionary==null)
              return new RuntimeException("数据字典中未配置type=systemType,value="+systemType+"的数据字典");
          if(StringUtils.isBlank(sysDictionary.getAtt1()))
              return new RuntimeException("数据字典:type=systemType,value="+systemType+"未配置att1属性,请配置(对应媒介类型的value)");
          systemType=Integer.valueOf(sysDictionary.getAtt1());
          /*systemType转换->end*/
          Map<String,String> map=this.versionCheckVerifyParams(msgId,systemType);
          if(StringUtils.equals("false", map.get("success")))
              return new ResponseEnvelope<>(false, map.get("msg"), msgId);
          /*得到最新版本*/
          String version = null;
           String  sysVersionType = app.getString("sys.version.type").trim();
           if(StringUtils.isBlank(sysVersionType)){
               sysVersionType = "1" ;
           }
           if("2".equals(sysVersionType)){
               version = "version_internal";
           }
           if("1".equals(sysVersionType)){
               version = "version";
           }
          SysVersion sysVersion=this.getLatestVersion(systemType,version);

          if(sysVersion==null||sysVersion.getId()==null)
              return new ResponseEnvelope<>(false, "未发现该平台类型下的版本,请通知管理员新建版本", msgId);
          
          String mediaType = null;
          SysDictionary dictionary = sysDictionaryService.findOneByTypeAndValue("mediaType", systemType);
          if(dictionary != null && !Utils.isBlank(dictionary.getType())) {
            mediaType = dictionary.getValuekey();
          }
          
          if( sysVersion != null ){
   
                Integer fileId = null;
                Integer fileId32 = null;
                Integer internalFileId = null;
                Integer testFileId = null;
                String path = null;
                String path32 = null;
                String internalPath = null;
                String testPath = null;
                
                fileId = sysVersion.getFileId();
                fileId32 = sysVersion.getFileId32();
                internalFileId =  sysVersion.getInternalFileId();
                testFileId = sysVersion.getTestPatchFileId();

                if(fileId!=null){
                    ResVersion resFile = resVersionService.get(fileId);
                    if(resFile!=null){
                         path = resFile.getFilePath();
                         sysVersion.setFilePath(path);
                    }
                }
                if(fileId32!=null){
                    ResVersion resFile32 = resVersionService.get(fileId32);
                    if(resFile32!=null){
                         path32 = resFile32.getFilePath();
                         sysVersion.setFilePath32(path32);
                    }
                }
                if(internalFileId!=null){
                    ResVersion internalResFile = resVersionService.get(internalFileId);
                    if(internalResFile!=null){
                        internalPath = internalResFile.getFilePath();
                        sysVersion.setInternalFilePath(internalPath);
                    }
                }
                if(testFileId!=null){
                    ResVersion testResFile = resVersionService.get(testFileId);
                    if(testResFile!=null){
                         testPath = testResFile.getFilePath();
                         sysVersion.setTestFilePath(testPath);
                    }
                }   
//              }
          }
          sysVersion.setSysVersionType(sysVersionType);
          if(Utils.isEmpty(sysVersion.getAppPath())) {
            sysVersion.setAppPath(sysVersion.getFilePath());
          }
          return new ResponseEnvelope<>(sysVersion, msgId, true);
    }
      
}
