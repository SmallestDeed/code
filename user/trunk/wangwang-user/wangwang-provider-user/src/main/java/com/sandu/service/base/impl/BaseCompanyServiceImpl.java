package com.sandu.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.base.service.BaseCompanyCustomLoginConfigService;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.base.service.BasePlatformService;
import com.sandu.api.user.model.bo.CompanyInfoBO;
import com.sandu.common.exception.BizException;
import com.sandu.common.exception.ExceptionCodes;
import com.sandu.service.base.dao.BaseCompanyDao;

@Service("baseCompanyService")
public class BaseCompanyServiceImpl implements BaseCompanyService {
	
	@Autowired
	private BaseCompanyDao baseCompanyDao;

    @Autowired
    private BasePlatformService basePlatformService;
    
    
	@Override
	public BaseCompany queryById(Long id) {
		// TODO Auto-generated method stub
		return baseCompanyDao.selectById(id);
	}

	@Override
	public List<CompanyInfoBO> queryFranchiserCompanys(String mobile, String password,String platformCode,int isForLogin) {
		BasePlatform platform = basePlatformService.queryByPlatformCode(platformCode);
	    if (platform == null) {
	         throw new BizException(ExceptionCodes.CODE_10010001, "平台类型不正确");
	    }
		return baseCompanyDao.selectFranchiserCompanys(mobile, password,platform.getId(),isForLogin);
	}

	@Override
	public List<CompanyInfoBO> queryFranchiserCompanys(String mobile, String password, String platformCode) {
		BasePlatform platform = basePlatformService.queryByPlatformCode(platformCode);
		if (platform == null) {
			throw new BizException(ExceptionCodes.CODE_10010001, "平台类型不正确");
		}
		return baseCompanyDao.selectFCompanys(mobile, password,platform.getId());
	}

	@Override
	public BaseCompany queryByAppId(String appId) {
		// TODO Auto-generated method stub
		return baseCompanyDao.selectByAppId(appId);
	}

	@Override
	public String getcompanyCode() {
		try{
			List<Integer> businessTypeList = new ArrayList<Integer>();
			businessTypeList.add(2);
			StringBuffer commanyCodePrefix = new StringBuffer();//前缀
			StringBuffer commanyCodeSuffix = new StringBuffer();//后缀

			commanyCodePrefix.append("C");

			String companyCodeMax = "";
			companyCodeMax = baseCompanyDao.createCompanyCode(commanyCodePrefix.toString()+"%",null,businessTypeList);  /**获取当前前缀的最大序列号**/

			Integer commanyCode = 0;
			if(StringUtils.isNotEmpty(companyCodeMax)){
				commanyCode = Integer.parseInt(companyCodeMax.substring(1,companyCodeMax.length()));
				commanyCode++;
				commanyCodeSuffix.append(commanyCode);
			}else{
				commanyCodeSuffix.append("1");
			}
			int k = commanyCodeSuffix.length();
			for(int i = 0 ; i < 7 - k ; i++){
				commanyCodeSuffix.insert(0,"0");
			}
			return commanyCodePrefix+""+commanyCodeSuffix;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int save(BaseCompany baseCompany) {
		return baseCompanyDao.insertSelective(baseCompany);
	}

	@Override
	public int delByPrimaryKey(Long id) {
		return baseCompanyDao.delByPrimaryKey(id);
	}

	@Override
	public int updatePid(Long id, Long companyId) {
		return baseCompanyDao.updatePid(id,companyId);
	}

	@Override
	public String createCompanyCode(String commanyCodePrefix,List<Integer> businessTypeList,List<Integer> businessTypeNotList) {
        return baseCompanyDao.createCompanyCode(commanyCodePrefix,businessTypeList,businessTypeNotList);
	}

	@Override
	public BaseCompany getCompanyByCode(String companyCode) {
		// TODO Auto-generated method stub
		return baseCompanyDao.selectByCode(companyCode);
	}

	@Override
	public List<CompanyInfoBO> queryCompany(String companyName) {
		return baseCompanyDao.queryCompany(companyName);
	}

	@Override
	public List<CompanyInfoBO> getFranchiserCompany(Long companyId) {
		return baseCompanyDao.getFranchiserCompany(companyId);
	}
}
