package com.nork.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.Utils;
import com.nork.system.dao.SysVersionMapper;
import com.nork.system.model.ResFile;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysVersion;
import com.nork.system.model.search.SysVersionSearch;
import com.nork.system.service.ResFileService;
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

	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private ResFileService resFileService;
	
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
			resFileService.delete(id);
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
	public void setFileAttribute(SysVersion sysVersion) {
		if(sysVersion.getFileId()!=null&&sysVersion.getFileId()>0){
			ResFile resFile=resFileService.get(sysVersion.getFileId());
			if(resFile!=null){
				resFile.setBusinessId(sysVersion.getId());
				resFileService.update(resFile);
			}
		}
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
		String returnPath = Utils.getPropertyName("app","app.render.server.url","") + Utils.getPropertyName("app","app.server.siteName","app/") + "jsp/download.htm?downloadName=" + path;
		return returnPath;
	}
	
}
