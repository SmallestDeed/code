package com.sandu.service.system.impl;

import com.sandu.api.system.model.ResVersion;
import com.sandu.api.system.model.SysDictionary;
import com.sandu.api.system.model.SysVersion;
import com.sandu.api.system.model.SysVersionSearch;
import com.sandu.api.system.service.ResVersionService;
import com.sandu.api.system.service.SysDictionaryService;
import com.sandu.api.system.service.SysVersionService;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.collections.Lists;

import com.sandu.service.system.dao.SysVersionDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("sysVersionService")
public class SysVersionServiceImpl implements SysVersionService{

    @Value("${app.resources.url}")
    private String RESOURCESURL;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Value("${sys.version.type}")
    private String sysVersionType;

    @Value("${app.server.url}")
    private String SERVREURL;

    @Autowired
    private SysVersionDao sysVersionDao;

    @Autowired
    private ResVersionService resVersionService;

    Logger logger = LoggerFactory.getLogger(SysVersionServiceImpl.class);

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

    @Override
    public Object getLatestVersionInfo(String msgId, Integer systemType, Integer systemTypeDigits, Integer isNeedShell) {
            String serverUrl = RESOURCESURL;
            /** 参数校验 begin**/
            Map<String,String> map= this.versionCheckVerifyParams(msgId,systemType);
            if(Objects.equals("false", map.get("success"))) {
                return new ResponseEnvelope<SysVersion>(false, map.get("msg"), msgId);
            }
            if(Objects.equals("false", map.get("success"))) {
                return new ResponseEnvelope<SysVersion>(false, map.get("msg"), msgId);
            }

            SysDictionary sysDictionary=sysDictionaryService.getSysDictionary("systemType", systemType);
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
            if(StringUtils.isEmpty(sysVersionType)){
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
            logger.error("========================================"+ (shellflag == needShell.isNeed));
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
            SysDictionary typeDictionary = new SysDictionary();
            typeDictionary.setType("contactInformation");
            List<SysDictionary> dicList = sysDictionaryService.getList(typeDictionary);
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
            sysVersion.setNewUrl(SERVREURL);
            ResponseEnvelope_ = new ResponseEnvelope<>(sysVersion, msgId, true);
            return ResponseEnvelope_;
    }

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

		//String returnPath = Utils.getPropertyName("app","app.server.url","") + Utils.getPropertyName("app","app.server.siteName","app/") + "jsp/download.htm?downloadName=" + path;
		String returnPath = SERVREURL+"app/"+"jsp/download.htm?downloadName=" + path;
        return returnPath;
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
        SysDictionary sysDictionary=sysDictionaryService.findByTypeAndValueKey("versionStatus","versionStatus_official");
        if(sysDictionary==null||sysDictionary.getId()==null){
            throw new RuntimeException("数据字典中未添加版本状态:正式发布,type=versionStatus,valueKey=versionStatus_official");
        }
        sysVersionSearch.setStatus(sysDictionary.getValue());
        List<SysVersion> sysVersions=sysVersionDao.selectPaginatedList(sysVersionSearch);
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
        if(StringUtils.isEmpty(msgId)){
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
}
