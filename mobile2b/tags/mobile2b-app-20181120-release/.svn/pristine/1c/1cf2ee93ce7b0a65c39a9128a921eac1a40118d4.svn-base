package com.nork.product.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nork.product.model.small.CompanyDispatchInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.util.Constants;
import com.nork.common.util.Utils;
import com.nork.product.dao.BaseCompanyMapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseCompany;
import com.nork.product.model.search.BaseCompanySearch;
import com.nork.product.service.AuthorizedConfigService;
import com.nork.product.service.BaseCompanyService;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysDictionaryService;

/**   
 * @Title: BaseCompanyServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-企业表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:45
 * @version V1.0   
 */
@Service("baseCompanyService")
@Transactional
public class BaseCompanyServiceImpl implements BaseCompanyService {
	
	@Autowired
	private BaseCompanyMapper baseCompanyMapper;
	@Autowired
	private AuthorizedConfigService authorizedConfigService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private ResPicService resPicService;
	
	/**
	 * 新增数据
	 *
	 * @param baseCompany
	 * @return  int 
	 */
	@Override
	public int add(BaseCompany baseCompany) {
		baseCompanyMapper.insertSelective(baseCompany);
		return baseCompany.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseCompany
	 * @return  int 
	 */
	@Override
	public int update(BaseCompany baseCompany) {
		return baseCompanyMapper
				.updateByPrimaryKeySelective(baseCompany);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseCompanyMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseCompany 
	 */
	@Override
	public BaseCompany get(Integer id) {
		return baseCompanyMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseCompany
	 * @return   List<BaseCompany>
	 */
	@Override
	public List<BaseCompany> getList(BaseCompany baseCompany) {
	    return baseCompanyMapper.selectList(baseCompany);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseCompany
	 * @return   int
	 */
	@Override
	public int getCount(BaseCompanySearch baseCompanySearch){
		return  baseCompanyMapper.selectCount(baseCompanySearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  baseCompany
	 * @return   List<BaseCompany>
	 */
	@Override
	public List<BaseCompany> getPaginatedList(
			BaseCompanySearch baseCompanySearch) {
		return baseCompanyMapper.selectPaginatedList(baseCompanySearch);
	}

	/**
	 * 获得用户类型:(序列号->公司->小类)
	 * @param id
	 * @return
	 */
	public Set<String> getTypeSetByUserId(Integer userId) {
		Set<String> set=new HashSet<String>();
		List<AuthorizedConfig> authorizedConfigList=authorizedConfigService.findAllByUserId(userId);
		for(AuthorizedConfig authorizedConfig:authorizedConfigList){
			if(StringUtils.isBlank(authorizedConfig.getCompanyId()))
				continue;
			List<String> strList=Utils.getListFromStr(authorizedConfig.getCompanyId());
			for(String str:strList){
				BaseCompany baseCompany=get(Integer.valueOf(str));
				if(baseCompany!=null&&StringUtils.isNotBlank(baseCompany.getSmallType())){
					String smallType=baseCompany.getSmallType();
					List<String> smallTypeList=Utils.getListFromStr(smallType);
					for(String smallTypeStr:smallTypeList){
						SysDictionary sysDictionaryType=null;
						if(baseCompany.getIndustry()!=null&&baseCompany.getIndustry()>0)
							sysDictionaryType=sysDictionaryService.findOneByTypeAndValue("industry", baseCompany.getIndustry());
						if(sysDictionaryType!=null){
							SysDictionary sysDictionarySmallType=sysDictionaryService.findOneByTypeAndValue(sysDictionaryType.getValuekey(), Integer.parseInt(smallTypeStr));
							if(sysDictionarySmallType!=null)
								set.add(sysDictionarySmallType.getValuekey());
						}
					}
				}
			}
		}
		return set;
	}

	@Override
	public Map<String,Object> getCategoryList(Integer companyId) {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		List<String> bigTypeValueKeyList = new ArrayList<String>();
		List<Integer> smallTypeValueList = new ArrayList<Integer>();
		BaseCompany baseCompany = this.get(companyId);
		if(baseCompany != null){
			if(baseCompany.getIndustry() != null && StringUtils.isNotBlank(baseCompany.getSmallType())){
				List<Integer> smallTypeList = Utils.getIntegerListFromStringList(baseCompany.getSmallType());
				for(Integer smallType : smallTypeList){
					SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValueAndValue("industry", baseCompany.getIndustry(), smallType);
					if(sysDictionary == null){
						continue;
					}
					if(StringUtils.isNotBlank(sysDictionary.getAtt1())){
						List<String> smallTypeValuekeyList = Utils.getListFromStr(sysDictionary.getAtt1());
						for(String smallTypeValuekey : smallTypeValuekeyList){
							SysDictionary sysDictionarySmallType = sysDictionaryService.findOneByValueKey(smallTypeValuekey);
							if(sysDictionarySmallType != null){
								bigTypeValueKeyList.add(sysDictionarySmallType.getType());
								smallTypeValueList.add(sysDictionarySmallType.getValue());
							}
						}
					}
				}
			}
		}
		returnMap.put("bigTypeValueKeyList", bigTypeValueKeyList);
		returnMap.put("smallTypeValueList", smallTypeValueList);
		return returnMap;
	}

	/**
	 * 获取用户企业LOGO
	 * @param userId
	 * @return
	 */
	@Override
	public String getCompanyLogoByAuthorizedConfig(Integer userId){
		// 获取用户公司
		String companyLogo = "";
		AuthorizedConfig authorizedConfig = new AuthorizedConfig();
		authorizedConfig.setUserId(userId);
		authorizedConfig.setIsDeleted(0);
		authorizedConfig.setState(1);
		List<AuthorizedConfig> authorizedConfigs = authorizedConfigService.getList(authorizedConfig);
		if( authorizedConfigs == null || authorizedConfigs.size() == 0 ){
			return null;
		}
		authorizedConfig = authorizedConfigs.get(0);
		String companyId = authorizedConfig.getCompanyId();
		if( com.nork.common.util.StringUtils.isBlank(companyId) ){
			return null;
		}
		BaseCompany baseCompany = this.get(Integer.valueOf(companyId));
		if( baseCompany == null ){
			return null;
		}

		// 获取用户公司LOGO
		if( baseCompany.getCompanyLogo() != null ){
			ResPic resPic = resPicService.get(baseCompany.getCompanyLogo());
			if( resPic != null ){
				/*File file = new File(Constants.UPLOAD_ROOT + resPic.getPicPath());*/
				File file = new File(Utils.getAbsolutePath(resPic.getPicPath(), Utils.getAbsolutePathType.encrypt));
				if( file.exists() ){
					companyLogo = file.getPath();
				}
			}
		}
		return companyLogo;
	}

	public BaseCompany getCompanyByDomainName(String companyDomainName) {
		if (companyDomainName == null || "".equals(companyDomainName)) {
			return null;
		}
		return baseCompanyMapper.getCompanyByDomainName(companyDomainName);
	}

	@Override
	public BaseCompany getCompanyByCompanyCode(String c0000071) {
		return baseCompanyMapper.selectCompanyByCompanyCode(c0000071);
	}

	@Override
	public List<Integer> getbrandIdByCompanyId(Integer id) {
		return baseCompanyMapper.selectbrandIdByCompanyId(id);
	}


	@Override
	public CompanyDispatchInfo getDispatchInfo(Integer id) {
		return baseCompanyMapper.getDispatchInfo(id);
	}
}
