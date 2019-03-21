package com.sandu.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.base.dao.BaseCompanyDao;
import com.sandu.base.model.query.BaseCompanyQuery;
import com.sandu.base.model.vo.BaseCompanyVo;
import com.sandu.base.service.BaseCompanyService;
import com.sandu.common.utils.CompanyUtils;
import com.sandu.common.utils.DigitalUtils;
import com.sandu.matadata.Page;

@Service("baseCompanyService")
@Transactional(readOnly = true)
public class BaseCompanyServiceImpl implements BaseCompanyService{
	@Autowired
    private BaseCompanyDao baseCompanyDao;
	
	@Override
	public Page<BaseCompanyVo> getList(BaseCompanyQuery query) {
    	Page<BaseCompanyVo> page=new Page<BaseCompanyVo>();
    	long count=baseCompanyDao.findFontCount(query);
    	List<BaseCompanyVo> lstVo=baseCompanyDao.findFontPageList(query);
    	if(lstVo!=null && lstVo.size()>0) {
    		for(BaseCompanyVo vo:lstVo) {
    			vo.setPraiseRatePercent(DigitalUtils.FloatToPercent(vo.getPraiseRate()));
    			vo.setBusinessTypeName(CompanyUtils.getBusinessTypeName(vo.getBusinessType()));
    		}
    	}
    	page.setCount(count);
    	page.setList(lstVo);
		return page;
	}

	@Override
	public BaseCompanyVo get(long companyid) {
		BaseCompanyVo detail = baseCompanyDao.getdetail(companyid);
		if(detail!=null) {
			detail.setBusinessTypeName(CompanyUtils.getBusinessTypeName(detail.getBusinessType()));
		}
		return detail;
	}

}
